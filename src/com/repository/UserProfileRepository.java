package com.repository;

import com.db.DBUtil;
import com.domain.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileRepository {

    private static final String SQL_FIND_PROFILE=
            "SELECT user_id, gender, tone_no, personal_color, age_band " +
                    "FROM UserProfile " +
                    "WHERE user_id = ?";

    private static final String SQL_FIND_SKIN_TYPES=
            "SELECT skin_type_id " +
                    "FROM UserSkinType_REL " +
                    "WHERE user_id = ? " +
                    "ORDER BY skin_type_id";

    public UserProfile findByUserId(String userId) {
        try (Connection conn = DBUtil.getConnection()) {

            // 1) 기본 프로필 정보 조회 (뷰)
            String gender;
            int toneNo;
            String personalColor;
            String ageBand;

            try (PreparedStatement ps = conn.prepareStatement(SQL_FIND_PROFILE)) {
                ps.setString(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        // 해당 user_id에 대한 프로필 없음
                        return null;
                    }
                    gender = rs.getString("gender");
                    toneNo = rs.getInt("tone_no");
                    personalColor = rs.getString("personal_color");
                    ageBand = rs.getString("age_band");
                }
            }

            // 2) 스킨 타입 목록 조회
            List<Integer> skinTypes = new ArrayList<>();
            try (PreparedStatement ps2 = conn.prepareStatement(SQL_FIND_SKIN_TYPES)) {
                ps2.setString(1, userId);

                try (ResultSet rs2 = ps2.executeQuery()) {
                    while (rs2.next()) {
                        skinTypes.add(rs2.getInt("skin_type_id"));
                    }
                }
            }

            // 3) 첫 번째/두 번째 스킨타입을 main/sub으로 간주
            Integer mainSkinTypeId = null;
            Integer subSkinTypeId = null;

            if (!skinTypes.isEmpty()) {
                mainSkinTypeId = skinTypes.get(0);
            }
            if (skinTypes.size() > 1) {
                subSkinTypeId = skinTypes.get(1);
            }

            // 4) 도메인 객체로 묶어서 리턴
            return new UserProfile(
                    userId,
                    gender,
                    toneNo,
                    personalColor,
                    ageBand,
                    mainSkinTypeId,
                    subSkinTypeId
            );

        } catch (SQLException e) {
            throw new RuntimeException("UserProfile 조회 실패: " + userId, e);
        }
    }
}
