package com.recommendation;

import com.domain.Product;
import com.domain.UserProfile;
import com.repository.ProductRepository;
import com.repository.SocialRepository;
import com.repository.UserProfileRepository;

import java.util.*;

/**
 * BEAUTiFY 콘솔 추천 서비스
 */
public class RecommendationService {

    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;
    private final SocialRepository socialRepository;

    public RecommendationService(UserProfileRepository userProfileRepository,
                                 ProductRepository productRepository,
                                 SocialRepository socialRepository) {
        this.userProfileRepository = userProfileRepository;
        this.productRepository = productRepository;
        this.socialRepository = socialRepository;
    }

    /**
     * 특정 userId에 대한 상위 N개 추천
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

        // 3) 소셜 정보 조회
        Map<Integer, List<String>> socialFavUsers =
                socialRepository.getFollowedFavoriteUsers(userId);

        // 4) key 기준 dedupe + 점수 계산
        Map<String, ProductScore> scoreMap = new HashMap<>();

        for (Product p : products) {
            double score = calculateScore(profile, p, socialFavUsers);
            String explanation = buildExplanation(profile, p, score, socialFavUsers);

            ProductScore newScore = new ProductScore(p, score, explanation);
            String key = buildProductKey(p);

            ProductScore existing = scoreMap.get(key);
            if (existing == null || newScore.getScore() > existing.getScore()) {
                scoreMap.put(key, newScore);
            }
        }

        // 5) 정렬 + topN 자르기
        List<ProductScore> scored = new ArrayList<>(scoreMap.values());
        Collections.sort(scored); // ProductScore가 Comparable 구현 (score 내림차순)

        if (scored.size() > topN) {
            return new ArrayList<>(scored.subList(0, topN));
        } else {
            return scored;
        }
    }

    /** 동일 제품을 식별하기 위한 key */
    private String buildProductKey(Product p) {
        return p.getBrandName() + "|" +
                p.getProductName() + "|" +
                p.getCategory() + "|" +
                p.getCapacity();
    }

    /** 점수 계산: 인기 + 가격 + 스킨 + 톤 + 퍼컬 + 소셜 */
    private double calculateScore(UserProfile profile,
                                  Product p,
                                  Map<Integer, List<String>> socialFavUsers) {

        // 1) 인기 점수
        double popularityScore = Math.log(p.getReviewCount() + 1);

        // 2) 가격 점수
        double priceWeight = getPriceWeightByAgeBand(profile.getAgeBand());
        double normalizedPrice = normalizePrice(p.getPrice());
        double priceScore = (1.0 - normalizedPrice) * priceWeight;

        // 3) 스킨 / 톤 / 퍼컬
        double skinScore  = calcSkinMatch(profile, p);
        double toneScore  = calcToneMatch(profile, p);
        double colorScore = calcPersonalColorMatch(profile, p);

        // 4) 소셜 즐겨찾기 점수
        List<String> fans = socialFavUsers.getOrDefault(
                p.getProductId(),
                Collections.emptyList()
        );
        int favCnt = fans.size();
        double socialScore = Math.log(favCnt + 1); // 0→0, 1→0.69, 2→1.10...

        // 5) 가중치
        double wSkin   = 1.5;
        double wTone   = 1.0;
        double wColor  = 0.8;
        double wSocial = 1.2;

        return popularityScore + priceScore
                + wSkin   * skinScore
                + wTone   * toneScore
                + wColor  * colorScore
                + wSocial * socialScore;
    }


    private double getPriceWeightByAgeBand(String ageBand) {
        if ("10".equals(ageBand) || "20".equals(ageBand)) {
            return 0.8;
        } else if ("30".equals(ageBand)) {
            return 0.5;
        } else {
            return 0.3;
        }
    }

    private double normalizePrice(long price) {
        double max = 200_000.0;
        double v = price / max;
        if (v < 0) v = 0;
        if (v > 1) v = 1;
        return v;
    }

    /** 피부 타입 매칭 점수 (0.0 ~ 1.0) */
    private double calcSkinMatch(UserProfile profile, Product p) {
        Integer userMain = profile.getMainSkinTypeId();
        Integer userSub  = profile.getSubSkinTypeId();
        Integer prodMain = p.getMainSkinTypeId();
        Integer prodSub  = p.getSubSkinTypeId();

        if (prodMain == null && prodSub == null) {
            return 0.0;
        }

        double score = 0.0;

        if (userMain != null && prodMain != null && userMain.equals(prodMain)) {
            score += 0.7;
        }
        if (userSub != null && prodSub != null && userSub.equals(prodSub)) {
            score += 0.3;
        }

        return Math.min(score, 1.0);
    }

    /** 톤 번호 매칭 점수 (0.0 ~ 1.0) */
    private double calcToneMatch(UserProfile profile, Product p) {
        Integer minTone = p.getMinToneNo();
        Integer maxTone = p.getMaxToneNo();
        int userTone = profile.getToneNo();

        if (minTone == null || maxTone == null) {
            return 0.0;
        }

        if (userTone >= minTone && userTone <= maxTone) {
            return 1.0;
        }

        int diff;
        if (userTone < minTone) {
            diff = minTone - userTone;
        } else {
            diff = userTone - maxTone;
        }

        double maxDiff = 10.0;
        double score = 1.0 - (diff / maxDiff);
        if (score < 0.0) score = 0.0;

        return score;
    }

    /** 퍼스널 컬러 매칭 점수 (0.0 ~ 1.0) */
    private double calcPersonalColorMatch(UserProfile profile, Product p) {
        String userColor = profile.getPersonalColor();
        String prodColor = p.getForPersonalColor();

        if (prodColor == null || userColor == null) {
            return 0.0;
        }

        if (prodColor.equalsIgnoreCase(userColor)) {
            return 1.0;
        }

        String userSeason = extractSeason(userColor);
        String prodSeason = extractSeason(prodColor);

        if (userSeason != null && userSeason.equals(prodSeason)) {
            return 0.6;
        }

        return 0.0;
    }

    /** "SPRING_WARM" -> "SPRING" */
    private String extractSeason(String personalColor) {
        if (personalColor == null) return null;
        personalColor = personalColor.toUpperCase();

        if (personalColor.startsWith("SPRING")) return "SPRING";
        if (personalColor.startsWith("SUMMER")) return "SUMMER";
        if (personalColor.startsWith("AUTUMN") || personalColor.startsWith("FALL")) return "AUTUMN";
        if (personalColor.startsWith("WINTER")) return "WINTER";

        return null;
    }

    /** 설명 문구 생성 */
    private String buildExplanation(UserProfile profile,
                                    Product p,
                                    double score,
                                    Map<Integer, List<String>> socialFavUsers) {

        StringBuilder sb = new StringBuilder();

        List<String> fans = socialFavUsers.getOrDefault(
                p.getProductId(),
                Collections.emptyList()
        );
        int favCnt = fans.size();

        sb.append("인기, 가격, 피부/톤/퍼스널컬러, 소셜 정보를 함께 고려한 추천입니다.\n");

        sb.append("- 사용자 나이대: ").append(profile.getAgeBand()).append("대\n");
        sb.append("- 상품 카테고리: ").append(p.getCategory()).append("\n");
        sb.append("- 리뷰 수: ").append(p.getReviewCount()).append("개\n");
        sb.append("- 가격: ").append(p.getPrice()).append("원\n");

        // 🔥 소셜 정보 (있는 경우만)
        if (favCnt > 0) {
            sb.append("- 팔로우 중 즐겨찾기: ");

            if (favCnt <= 3) {
                sb.append(String.join(", ", fans)).append("\n");
            } else {
                List<String> firstThree = fans.subList(0, 3);
                int others = favCnt - 3;
                sb.append(String.join(", ", firstThree))
                        .append(" 외 ").append(others).append("명\n");
            }
        }

        sb.append("- 최종 점수: ").append(String.format("%.2f", score)).append("\n");

        return sb.toString();
    }

}
