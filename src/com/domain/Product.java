package com.domain;

public class Product {

    // 기존 필드
    private final int productId;
    private final int brandId;
    private final String brandName;
    private final String productName;
    private final String category;
    private final String capacity;
    private final long price;
    private final int reviewCount;

    // 🔥 추가 필드: 스킨타입, 톤, 퍼스널 컬러 기반 추천용
    // 지금은 DB에 없어도 되니까 null 가능하게 Integer / String 으로 둠
    private final Integer mainSkinTypeId;    // 이 제품의 주 타겟 피부타입
    private final Integer subSkinTypeId;     // 이 제품의 서브 피부타입(선택)
    private final Integer minToneNo;         // 권장 최소 톤 번호
    private final Integer maxToneNo;         // 권장 최대 톤 번호
    private final String forPersonalColor;   // 권장 퍼스널 컬러 (SPRING_WARM 등)

    // ---- 생성자들 ----

    // 1) 기존 코드용 생성자 (스킨/톤/퍼컬 정보 없이 쓰는 경우)
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

        // 추가 필드는 일단 null로 초기화
        this.mainSkinTypeId = null;
        this.subSkinTypeId = null;
        this.minToneNo = null;
        this.maxToneNo = null;
        this.forPersonalColor = null;
    }

    // 2) 나중에 DB에 컬럼/관계가 생기면 이 생성자를 쓰면 됨
    public Product(int productId,
                   int brandId,
                   String brandName,
                   String productName,
                   String category,
                   String capacity,
                   long price,
                   int reviewCount,
                   Integer mainSkinTypeId,
                   Integer subSkinTypeId,
                   Integer minToneNo,
                   Integer maxToneNo,
                   String forPersonalColor) {
        this.productId = productId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.productName = productName;
        this.category = category;
        this.capacity = capacity;
        this.price = price;
        this.reviewCount = reviewCount;

        this.mainSkinTypeId = mainSkinTypeId;
        this.subSkinTypeId = subSkinTypeId;
        this.minToneNo = minToneNo;
        this.maxToneNo = maxToneNo;
        this.forPersonalColor = forPersonalColor;
    }

    // ---- getter ----

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

    // 🔥 RecommendationService 에서 쓰는 추가 getter 들

    public Integer getMainSkinTypeId() {
        return mainSkinTypeId;
    }

    public Integer getSubSkinTypeId() {
        return subSkinTypeId;
    }

    public Integer getMinToneNo() {
        return minToneNo;
    }

    public Integer getMaxToneNo() {
        return maxToneNo;
    }

    public String getForPersonalColor() {
        return forPersonalColor;
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
