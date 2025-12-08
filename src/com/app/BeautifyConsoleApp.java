package com.app;

import com.domain.UserProfile;
import com.recommendation.ProductScore;
import com.recommendation.RecommendationService;
import com.repository.ProductRepository;
import com.repository.UserProfileRepository;

import java.util.List;
import java.util.Scanner;

public class BeautifyConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserProfileRepository userProfileRepository = new UserProfileRepository();
        ProductRepository productRepository = new ProductRepository();
        RecommendationService recommendationService =
                new RecommendationService(userProfileRepository, productRepository);

        System.out.println("=== BEAUTiFY 콘솔 추천 프로그램 ===");
        System.out.print("사용자 ID를 입력하세요: ");
        String userId = scanner.nextLine().trim();

        System.out.print("특정 카테고리만 보고 싶으면 입력(예: 'TONER'), 없으면 그냥 Enter: ");
        String category = scanner.nextLine().trim();
        if (category.isBlank()) {
            category = null;
        }

        try {
            List<ProductScore> recommendations =
                    recommendationService.recommendForUser(userId, 5, category);

            System.out.println();
            System.out.println("=== 추천 결과 (상위 " + recommendations.size() + "개) ===");
            for (int i = 0; i < recommendations.size(); i++) {
                ProductScore ps = recommendations.get(i);
                System.out.println("[" + (i + 1) + "] "
                        + ps.getProduct().getBrandName() + " - "
                        + ps.getProduct().getProductName());
                System.out.println("    카테고리 : " + ps.getProduct().getCategory());
                System.out.println("    용량     : " + ps.getProduct().getCapacity());
                System.out.println("    가격     : " + ps.getProduct().getPrice() + "원");
                System.out.println("    리뷰 수  : " + ps.getProduct().getReviewCount());
                System.out.println("    설명     : " + ps.getExplanation());
                System.out.println();
            }

        } catch (Exception e) {
            System.err.println("추천 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("프로그램을 종료합니다.");
    }
}
