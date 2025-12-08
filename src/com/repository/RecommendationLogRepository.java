package com.repository;

import com.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RecommendationLogRepository {

    private static final String SQL_INSERT_LOG =
            "INSERT INTO RecommendationLog (rec_id, user_id, product_id, score) " +
                    "VALUES (SEQ_REC_ID.NEXTVAL, ?, ?, ?)";

    public void saveLog(String userId, int productId, double score) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT_LOG)) {

            ps.setString(1, userId);
            ps.setInt(2, productId);
            ps.setDouble(3, score);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("추천 로그 저장 실패", e);
        }
    }
}
