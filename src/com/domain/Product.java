package com.domain;

public class Product {

    private final int productId;
    private final int brandId;
    private final String brandName;   // JOIN Brand
    private final String productName;
    private final String category;
    private final String capacity;
    private final long price;
    private final int reviewCount;

    public Product(int productId,
                   int brandId,
                   String brandName,
                   String productName,
                   String category,
                   String capacity,
                   long price,
                   int reviewCount) {
        this.productId = productId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.productName = productName;
        this.category = category;
        this.capacity = capacity;
        this.price = price;
        this.reviewCount = reviewCount;
    }

    public int getProductId() {
        return productId;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public String getCapacity() {
        return capacity;
    }

    public long getPrice() {
        return price;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", brandName='" + brandName + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", capacity='" + capacity + '\'' +
                ", price=" + price +
                ", reviewCount=" + reviewCount +
                '}';
    }
}
