package com.recommendation;

import com.domain.Product;
import com.domain.UserProfile;
import com.repository.ProductRepository;
import com.repository.UserProfileRepository;

import java.util.*;

/**
 * BEAUTiFY 콘솔 추천 서비스
 * - userId 기준으로 프로필을 조회하고
 * - Product 목록을 가져와서
 * - 점수 계산 + 정렬 + product_id 기준 dedupe 후 상위 N개 반환
 */
public class RecommendationService {

    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;

    public RecommendationService(UserProfileRepository userProfileRepository,
                                 ProductRepository productRepository) {
        this.userProfileRepository = userProfileRepository;
        this.productRepository = productRepository;
    }

    /**
     * 특정 userId에 대한 상위 N개 추천
     *
     * @param userId        추천 대상 사용자
     * @param topN          상위 몇 개까지 뽑을지
     * @param categoryFilter null 이 아니면 해당 카테고리만 (예: "eyeliner")
     */
    public List<ProductScore> recommendForUser(String userId, int topN, String categoryFilter) {
        // 1) 유저 프로필 조회
        UserProfile profile = userProfileRepository.findByUserId(userId);
        if (profile == null) {
            throw new IllegalArgumentException("해당 userId의 프로필이 없습니다: " + userId);
        }

        // 2) 후보 상품 조회
        List<Product> products;
        if (categoryFilter == null || categoryFilter.isBlank()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategory(categoryFilter);
        }

        // 3) product_id 기준으로 dedupe 하면서 점수 계산
        Map<Integer, ProductScore> scoreMap = new HashMap<>();

        for (Product p : products) {
            double score = calculateScore(profile, p);
            String explanation = buildExplanation(profile, p, score);

            ProductScore newScore = new ProductScore(p, score, explanation);
            int productId = p.getProductId();

            // 같은 product_id가 여러 번 들어온 경우, 더 높은 점수만 유지
            ProductScore existing = scoreMap.get(productId);
            if (existing == null || newScore.getScore() > existing.getScore()) {
                scoreMap.put(productId, newScore);
            }
        }

        // 4) Map → List로 변환 후 정렬
        List<ProductScore> scored = new ArrayList<>(scoreMap.values());
        Collections.sort(scored); // ProductScore가 Comparable 구현(점수 내림차순)

        // 5) 상위 topN개만 반환
        if (scored.size() > topN) {
            return new ArrayList<>(scored.subList(0, topN));
        } else {
            return scored;
        }
    }

    /**
     * 점수 계산 로직 (간단한 버전)
     *  - 인기 점수: log(review_count + 1)
     *  - 가격 점수: 나이대에 따른 가격 민감도(가중치)를 곱한 뒤, 저렴할수록 높게
     */
    private double calculateScore(UserProfile profile, Product p) {
        // 1) 인기 점수 (리뷰 수 기반)
        double popularityScore = Math.log(p.getReviewCount() + 1); // 리뷰 0 → 0, 1 → ~0.69, 10 → ~2.4 ...

        // 2) 나이대에 따른 가격 민감도
        double priceWeight = getPriceWeightByAgeBand(profile.getAgeBand());

        // 3) 가격 정규화 (0~200000 사이로 가정, 비율을 0~1로)
        double normalizedPrice = normalizePrice(p.getPrice());

        // 가격이 저렴할수록 점수 ↑
        double priceScore = (1.0 - normalizedPrice) * priceWeight;

        // 필요하면 여기다가 스킨타입/톤/퍼컬 등 추가 가중치도 더할 수 있음
        return popularityScore + priceScore;
    }

    /**
     * 나이대별 가격 민감도 가중치
     *  - "10" / "20" : 0.8
     *  - "30"       : 0.5
     *  - 그 외      : 0.3
     */
    private double getPriceWeightByAgeBand(String ageBand) {
        if ("10".equals(ageBand) || "20".equals(ageBand)) {
            return 0.8;
        } else if ("30".equals(ageBand)) {
            return 0.5;
        } else {
            return 0.3;
        }
    }

    /**
     * 가격 정규화 (0 ~ 200,000원 구간을 0~1로 매핑)
     *  - 0원  → 0.0
     *  - 200,000원 이상 → 1.0
     */
    private double normalizePrice(long price) {
        double max = 200_000.0;
        double v = price / max;
        if (v < 0) v = 0;
        if (v > 1) v = 1;
        return v;
    }

    /**
     * 설명 문구 생성
     * - 리포트/로그용: 왜 이 제품이 이 점수를 받았는지 간단하게 서술
     */
    private String buildExplanation(UserProfile profile, Product p, double score) {
        StringBuilder sb = new StringBuilder();

        sb.append("인기 점수 & 가격을 함께 고려한 추천입니다. ");
        sb.append("사용자 나이대: ").append(profile.getAgeBand()).append("대, ");
        sb.append("상품 카테고리: ").append(p.getCategory()).append(", ");
        sb.append("리뷰 수: ").append(p.getReviewCount()).append("개, ");
        sb.append("가격: ").append(p.getPrice()).append("원. ");
        sb.append("최종 점수: ").append(String.format("%.2f", score));

        return sb.toString();
    }
}
