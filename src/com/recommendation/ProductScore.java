package com.recommendation;

import com.domain.Product;

public class ProductScore implements Comparable<ProductScore>{

    private final Product product;
    private final double score;
    private final String explanation;

    public ProductScore(Product product, double score, String explanation) {
        this.product = product;
        this.score = score;
        this.explanation = explanation;
    }

    public Product getProduct() {
        return product;
    }

    public double getScore() {
        return score;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public int compareTo(ProductScore o) {
        // 점수 내림차순 정렬
        return Double.compare(o.score, this.score);
    }
}
