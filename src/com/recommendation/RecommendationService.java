package com.recommendation;

import com.domain.Product;
import com.domain.UserPreferences;
import com.domain.UserProfile;
import com.repository.ProductIngredientRepository;
import com.repository.ProductRepository;
import com.repository.RecommendationLogRepository;
import com.repository.SocialRepository;
import com.repository.UserPreferenceRepository;
import com.repository.UserProfileRepository;

import java.util.*;

public class RecommendationService {

    private final UserProfileRepository userProfileRepository;
    private final ProductRepository productRepository;
    private final SocialRepository socialRepository;
    private final RecommendationLogRepository logRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final ProductIngredientRepository productIngredientRepository;

    public RecommendationService(UserProfileRepository userProfileRepository,
                                 ProductRepository productRepository,
                                 SocialRepository socialRepository,
                                 RecommendationLogRepository logRepository,
                                 UserPreferenceRepository userPreferenceRepository,
                                 ProductIngredientRepository productIngredientRepository) {
        this.userProfileRepository = userProfileRepository;
        this.productRepository = productRepository;
        this.socialRepository = socialRepository;
        this.logRepository = logRepository;
        this.userPreferenceRepository = userPreferenceRepository;
        this.productIngredientRepository = productIngredientRepository;
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

        // 2) 사용자 성분 선호(P/A) 조회
        UserPreferences preferences = userPreferenceRepository.findByUserId(userId);

        // 3) 후보 상품 조회
        List<Product> products;
        if (categoryFilter == null || categoryFilter.isBlank()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.findByCategory(categoryFilter);
        }

        // 4) 소셜 정보 조회 (팔로우한 유저들의 Favorite)
        Map<Integer, List<String>> socialFavUsers =
                socialRepository.getFollowedFavoriteUsers(userId);

        // 5) key 기준 dedupe + 점수 계산
        Map<String, ProductScore> scoreMap = new HashMap<>();

        for (Product p : products) {
            // 해당 제품의 성분 ID들
            Set<Integer> ingrIds =
                    productIngredientRepository.findIngredientIdsByProductId(p.getProductId());

            double score = calculateScore(profile, p, socialFavUsers, preferences, ingrIds);
            String explanation = buildExplanation(profile, p, score, socialFavUsers,
                    preferences, ingrIds);

            ProductScore newScore = new ProductScore(p, score, explanation);
            String key = buildProductKey(p);

            ProductScore existing = scoreMap.get(key);
            if (existing == null || newScore.getScore() > existing.getScore()) {
                scoreMap.put(key, newScore);
            }
        }

        // 6) 정렬 + topN 자르기
        List<ProductScore> scored = new ArrayList<>(scoreMap.values());
        Collections.sort(scored); // ProductScore.compareTo → 점수 내림차순

        if (scored.size() > topN) {
            scored = new ArrayList<>(scored.subList(0, topN));
        }

        // 7) 추천 로그 저장
        for (ProductScore ps : scored) {
            logRepository.saveLog(
                    userId,
                    ps.getProduct().getProductId(),
                    ps.getScore()
            );
        }

        return scored;
    }

    /** 동일 제품을 식별하기 위한 key (브랜드+이름+카테고리+용량) */
    private String buildProductKey(Product p) {
        return p.getBrandName() + "|" +
                p.getProductName() + "|" +
                p.getCategory() + "|" +
                p.getCapacity();
    }

    /** 점수 계산: 인기 + 가격 + 스킨 + 톤 + 퍼컬 + 소셜 + 성분 */
    private double calculateScore(UserProfile profile,
                                  Product p,
                                  Map<Integer, List<String>> socialFavUsers,
                                  UserPreferences preferences,
                                  Set<Integer> productIngrIds) {

        // 1) 인기 점수 (리뷰수)
        double popularityScore = Math.log(p.getReviewCount() + 1);

        // 2) 가격 점수
        double priceWeight = getPriceWeightByAgeBand(profile.getAgeBand());
        double normalizedPrice = normalizePrice(p.getPrice());
        double priceScore = (1.0 - normalizedPrice) * priceWeight;

        // 3) 피부타입/톤/퍼컬 점수
        double skinScore  = calcSkinMatch(profile, p);
        double toneScore  = calcToneMatch(profile, p);
        double colorScore = calcPersonalColorMatch(profile, p);

        // 4) 소셜 즐겨찾기 점수
        List<String> fans = socialFavUsers.getOrDefault(
                p.getProductId(),
                Collections.emptyList()
        );
        int favCnt = fans.size();
        double socialScore = Math.log(favCnt + 1); // 0→0, 1→0.69 ...

        // 5) 성분 선호도 점수
        double ingredientScore = calcIngredientScore(preferences, productIngrIds);

        // 6) 가중치
        double wSkin   = 1.5;
        double wTone   = 1.0;
        double wColor  = 0.8;
        double wSocial = 1.2;
        double wIngr   = 1.0;

        return popularityScore + priceScore
                + wSkin   * skinScore
                + wTone   * toneScore
                + wColor  * colorScore
                + wSocial * socialScore
                + wIngr   * ingredientScore;
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

    /** "SPRING_WARM" -> "SPRING" 같은 식으로 시즌 추출 */
    private String extractSeason(String personalColor) {
        if (personalColor == null) return null;
        personalColor = personalColor.toUpperCase();

        if (personalColor.startsWith("SPRING")) return "SPRING";
        if (personalColor.startsWith("SUMMER")) return "SUMMER";
        if (personalColor.startsWith("AUTUMN") || personalColor.startsWith("FALL")) return "AUTUMN";
        if (personalColor.startsWith("WINTER")) return "WINTER";

        return null;
    }

    /** 성분 선호도 점수 계산 */
    private double calcIngredientScore(UserPreferences preferences,
                                       Set<Integer> productIngrIds) {
        if (preferences == null || !preferences.hasAnyPreference()
                || productIngrIds == null || productIngrIds.isEmpty()) {
            return 0.0;
        }

        int preferMatches = 0;
        int avoidMatches  = 0;

        for (Integer ingrId : productIngrIds) {
            if (preferences.getPreferredIngredientIds().contains(ingrId)) {
                preferMatches++;
            }
            if (preferences.getAvoidedIngredientIds().contains(ingrId)) {
                avoidMatches++;
            }
        }

        if (preferMatches == 0 && avoidMatches == 0) {
            return 0.0;
        }

        // 선호 성분 하나당 +0.4, 회피 성분 하나당 -0.7 (필요하면 조정)
        double score = preferMatches * 0.4 - avoidMatches * 0.7;

        // 너무 극단적이지 않게 자르기
        if (score > 2.0) score = 2.0;
        if (score < -2.0) score = -2.0;

        return score;
    }

    /** 줄바꿈 포함 추천 설명 */
    private String buildExplanation(UserProfile profile,
                                    Product p,
                                    double score,
                                    Map<Integer, List<String>> socialFavUsers,
                                    UserPreferences preferences,
                                    Set<Integer> productIngrIds) {

        StringBuilder sb = new StringBuilder();

        List<String> fans = socialFavUsers.getOrDefault(
                p.getProductId(),
                Collections.emptyList()
        );
        int favCnt = fans.size();

        sb.append("인기, 가격, 피부/톤/퍼스널컬러, 소셜 정보");
        if (preferences != null && preferences.hasAnyPreference()) {
            sb.append(", 성분 선호도");
        }
        sb.append("를 함께 고려한 추천입니다.\n");

        sb.append("- 사용자 나이대: ").append(profile.getAgeBand()).append("대\n");
        sb.append("- 상품 카테고리: ").append(p.getCategory()).append("\n");
        sb.append("- 리뷰 수: ").append(p.getReviewCount()).append("개\n");
        sb.append("- 가격: ").append(p.getPrice()).append("원\n");

        // 소셜 정보
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

        // 성분 선호/회피 상세
        if (preferences != null && productIngrIds != null && !productIngrIds.isEmpty()) {
            List<String> matchedPreferNames = new ArrayList<>();
            List<String> matchedAvoidNames  = new ArrayList<>();

            for (Integer ingrId : productIngrIds) {
                if (preferences.getPreferredIngredientIds().contains(ingrId)) {
                    String name = preferences.getIngredientName(ingrId);
                    matchedPreferNames.add(name != null ? name : ("성분#" + ingrId));
                }
                if (preferences.getAvoidedIngredientIds().contains(ingrId)) {
                    String name = preferences.getIngredientName(ingrId);
                    matchedAvoidNames.add(name != null ? name : ("성분#" + ingrId));
                }
            }

            if (!matchedPreferNames.isEmpty()) {
                sb.append("- 선호 성분 포함: ")
                        .append(String.join(", ", matchedPreferNames))
                        .append(" (가산점 반영)\n");
            }
            if (!matchedAvoidNames.isEmpty()) {
                sb.append("- 피하고 싶은 성분 포함: ")
                        .append(String.join(", ", matchedAvoidNames))
                        .append(" (감점 반영)\n");
            }
        }

        sb.append("- 최종 점수: ").append(String.format("%.2f", score)).append("\n");

        return sb.toString();
    }
}
