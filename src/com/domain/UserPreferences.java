package com.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class UserPreferences {

    // P 선호 성분
    private final Set<Integer> preferredIngredientIds;
    // A 회피 성분
    private final Set<Integer> avoidedIngredientIds;
    // 성분 이름 조회용 (ingr_id -> ingr_name)
    private final Map<Integer, String> ingredientNamesById;

    public UserPreferences(Set<Integer> preferredIngredientIds,
                           Set<Integer> avoidedIngredientIds,
                           Map<Integer, String> ingredientNamesById) {
        this.preferredIngredientIds = preferredIngredientIds;
        this.avoidedIngredientIds = avoidedIngredientIds;
        this.ingredientNamesById = ingredientNamesById;
    }

    public static UserPreferences empty() {
        return new UserPreferences(
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptyMap()
        );
    }

    public Set<Integer> getPreferredIngredientIds() {
        return preferredIngredientIds;
    }

    public Set<Integer> getAvoidedIngredientIds() {
        return avoidedIngredientIds;
    }

    public String getIngredientName(Integer ingrId) {
        return ingredientNamesById.get(ingrId);
    }

    public boolean hasAnyPreference() {
        return !preferredIngredientIds.isEmpty() || !avoidedIngredientIds.isEmpty();
    }
}
