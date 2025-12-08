package com.repository;

import com.db.DBUtil;
import com.domain.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private static final String SQL_FIND_ALL =
            "SELECT p.product_id, p.brand_id, b.brand_name, " +
                    "       p.product_name, p.category, p.capacity, p.price, " +
                    "       NVL(COUNT(r.review_id), 0) AS review_count " +
                    "FROM Product p " +
                    "JOIN Brand b ON p.brand_id = b.brand_id " +
                    "LEFT JOIN Review r ON p.product_id = r.product_id " +
                    "GROUP BY p.product_id, p.brand_id, b.brand_name, " +
                    "         p.product_name, p.category, p.capacity, p.price";

    private static final String SQL_FIND_BY_CATEGORY =
            "SELECT p.product_id, p.brand_id, b.brand_name, " +
                    "       p.product_name, p.category, p.capacity, p.price, " +
                    "       NVL(COUNT(r.review_id), 0) AS review_count " +
                    "FROM Product p " +
                    "JOIN Brand b ON p.brand_id = b.brand_id " +
                    "LEFT JOIN Review r ON p.product_id = r.product_id " +
                    "WHERE p.category = ? " +
                    "GROUP BY p.product_id, p.brand_id, b.brand_name, " +
                    "         p.product_name, p.category, p.capacity, p.price";


    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("전체 상품 조회 실패", e);
        }

        return result;
    }

    public List<Product> findByCategory(String category) {
        List<Product> result = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_CATEGORY)) {

            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("카테고리별 상품 조회 실패: " + category, e);
        }

        return result;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        int productId = rs.getInt("product_id");
        int brandId = rs.getInt("brand_id");
        String brandName = rs.getString("brand_name");
        String productName = rs.getString("product_name");
        String category = rs.getString("category");
        String capacity = rs.getString("capacity");
        long price = rs.getLong("price");
        int reviewCount = rs.getInt("review_count");

        return new Product(
                productId,
                brandId,
                brandName,
                productName,
                category,
                capacity,
                price,
                reviewCount
        );
    }
}
