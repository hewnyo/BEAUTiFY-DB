package com.app;

import com.domain.UserProfile;
import com.recommendation.ProductScore;
import com.recommendation.RecommendationService;
import com.repository.ProductRepository;
import com.repository.SocialRepository;
import com.repository.UserProfileRepository;

import java.util.List;
import java.util.Scanner;

public class BeautifyConsoleApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        UserProfileRepository userProfileRepository = new UserProfileRepository();
        ProductRepository productRepository = new ProductRepository();
        SocialRepository socialRepository = new SocialRepository();

        // 🔥 RecommendationService 생성자에 3개 인자 넘기는 버전
        RecommendationService recommendationService =
                new RecommendationService(userProfileRepository,
                        productRepository,
                        socialRepository);

        System.out.println("===============================================");
        System.out.println("         BEAUTiFY 스마트 추천 콘솔 프로그램");
        System.out.println("===============================================");
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine().trim();

        UserProfile profile = userProfileRepository.findByUserId(userId);
        if (profile == null) {
            System.out.println("❌ 해당 사용자 ID의 프로필을 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n[사용자 프로필 정보]");
        System.out.println(" - 유저 ID       : " + profile.getUserId());
        System.out.println(" - 나이대        : " + profile.getAgeBand() + "대");
        System.out.println(" - 피부타입      : main=" + profile.getMainSkinTypeId() +
                ", sub=" + profile.getSubSkinTypeId());
        System.out.println(" - 톤 번호       : " + profile.getToneNo());
        System.out.println(" - 퍼스널컬러    : " + profile.getPersonalColor());
        System.out.println("-----------------------------------------------");

        System.out.print("특정 카테고리만 추천 받고 싶으면 입력하세요 (예: eyeliner). 없으면 Enter: ");
        String category = scanner.nextLine().trim();
        if (category.isBlank()) category = null;

        System.out.print("추천받을 제품 개수를 입력하세요 (기본값 5): ");
        String countStr = scanner.nextLine().trim();
        int topN = 5;
        try {
            if (!countStr.isBlank()) topN = Integer.parseInt(countStr);
        } catch (NumberFormatException e) {
            System.out.println("숫자가 아니라 기본값 5로 진행합니다.");
        }

        System.out.println("\n추천 분석 중...\n");

        try {
            List<ProductScore> recommendations =
                    recommendationService.recommendForUser(userId, topN, category);

            System.out.println("===============================================");
            System.out.println("        🎯 추천 결과 (상위 " + recommendations.size() + "개)");
            System.out.println("===============================================\n");

            if (recommendations.isEmpty()) {
                System.out.println("추천할 제품이 없습니다.");
                return;
            }

            int rank = 1;
            for (ProductScore ps : recommendations) {

                System.out.println("[" + rank++ + "] " + ps.getProduct().getBrandName() +
                        " - " + ps.getProduct().getProductName());
                System.out.println("    카테고리 : " + ps.getProduct().getCategory());
                System.out.println("    용량     : " + ps.getProduct().getCapacity());
                System.out.println("    가격     : " + ps.getProduct().getPrice() + "원");
                System.out.println("    리뷰 수  : " + ps.getProduct().getReviewCount());

                System.out.println("    🔍 추천 이유:");
                System.out.println("       " + ps.getExplanation());
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("추천 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("===============================================");
        System.out.println("      BEAUTiFY 추천 프로그램을 종료합니다.");
        System.out.println("===============================================");
    }
}
