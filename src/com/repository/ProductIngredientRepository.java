package com.repository;

import com.db.DBUtil;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ProductIngredientRepository {

    private static final String SQL_FIND_INGR_IDS =
            "SELECT ingr_id FROM ProductIngredient_REL WHERE product_id = ?";

    /**
     * 해당 product_id에 포함된 성분 ID 목록 조회
     */
    public Set<Integer> findIngredientIdsByProductId(int productId) {
        Set<Integer> result = new HashSet<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_INGR_IDS)) {

            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getInt("ingr_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("제품 성분 조회 실패: product_id=" + productId, e);
        }

        return result;
    }
}
