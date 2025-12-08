package com.repository;

import com.db.DBUtil;
import com.domain.UserPreferences;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UserPreferenceRepository {

    private static final String SQL_FIND_BY_USER =
            "SELECT up.ingr_id, up.preference_type, i.ingr_name " +
                    "FROM UserPreference up " +
                    "JOIN Ingredient i ON up.ingr_id = i.ingr_id " +
                    "WHERE up.user_id = ?";

    public UserPreferences findByUserId(String userId) {
        Set<Integer> preferred = new HashSet<>();
        Set<Integer> avoided = new HashSet<>();
        Map<Integer, String> nameMap = new HashMap<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USER)) {

            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ingrId = rs.getInt("ingr_id");
                    String type = rs.getString("preference_type");
                    String ingrName = rs.getString("ingr_name");

                    nameMap.put(ingrId, ingrName);

                    if ("P".equalsIgnoreCase(type)) {
                        preferred.add(ingrId);
                    } else if ("A".equalsIgnoreCase(type)) {
                        avoided.add(ingrId);
                    }
                }
            }
        } catch (SQLSyntaxErrorException e) {
            // UserPreference 테이블이 아직 없으면(ORA-00942) → 선호도 없이 진행
            if (e.getErrorCode() == 942) {
                System.out.println("[INFO] UserPreference 테이블이 없어 성분 기반 선호도는 반영하지 않습니다.");
                return UserPreferences.empty();
            }
            throw new RuntimeException("사용자 성분 선호 조회 실패: " + userId, e);
        } catch (SQLException e) {
            throw new RuntimeException("사용자 성분 선호 조회 실패: " + userId, e);
        }

        if (preferred.isEmpty() && avoided.isEmpty()) {
            return UserPreferences.empty();
        }

        return new UserPreferences(preferred, avoided, nameMap);
    }
}
