package com.repository;

import com.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

/**
 * 팔로우 관계 + 즐겨찾기 정보를 조회하는 리포지토리
 *  - 특정 사용자(userId)가 팔로우하고 있는 유저들이
 *    어떤 제품(product_id)을 favorite 했는지 (닉네임 기준) 조회한다.
 */
public class SocialRepository {

    private static final String SQL_FOLLOWED_FAVORITES =
            "WITH followed AS ( " +
                    "    SELECT followee_id AS user_id " +
                    "    FROM FollowUser " +
                    "    WHERE follower_id = ? " +
                    ") " +
                    "SELECT f.product_id, u.nickname " +
                    "FROM Favorite f " +
                    "JOIN followed fo ON f.user_id = fo.user_id " +
                    "JOIN Users u ON u.user_id = f.user_id";

    /**
     * key  : product_id
     * value: 내가 팔로우하는 사람들 중, 해당 제품을 즐겨찾기한 유저 닉네임 목록
     */
    public Map<Integer, List<String>> getFollowedFavoriteUsers(String userId) {
        Map<Integer, List<String>> result = new HashMap<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FOLLOWED_FAVORITES)) {

            ps.setString(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    String nickname = rs.getString("nickname");

                    result.computeIfAbsent(productId, k -> new ArrayList<>())
                            .add(nickname);
                }
            }

        } catch (SQLSyntaxErrorException e) {
            // ORA-00942: 팔로우 / 즐겨찾기 테이블이 아직 없을 때
            if (e.getErrorCode() == 942) { // ORA-00942
                return result; // 빈 map → social 영향 0
            }
            throw new RuntimeException("팔로우 유저들의 favorite 정보 조회 실패: " + userId, e);
        } catch (SQLException e) {
            throw new RuntimeException("팔로우 유저들의 favorite 정보 조회 실패: " + userId, e);
        }

        return result;
    }
}
