package com.domain;

public class UserProfile {
    private final String userId;
    private final String gender;
    private final int toneNo;
    private final String personalColor;
    private final String ageBand;

    // 관계 테이블(UserSkinType_REL)에서 가져오는 값
    private final Integer mainSkinTypeId;  // 첫 번째 스킨타입
    private final Integer subSkinTypeId;   // 두 번째 스킨타입 (없을 수도)

    public UserProfile(String userId, String gender, int toneNo,
                       String personalColor, String ageBand,
                       Integer mainSkinTypeId, Integer subSkinTypeId) {
        this.userId = userId;
        this.gender = gender;
        this.toneNo = toneNo;
        this.personalColor = personalColor;
        this.ageBand = ageBand;
        this.mainSkinTypeId = mainSkinTypeId;
        this.subSkinTypeId = subSkinTypeId;
    }

    public String getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }

    public int getToneNo() {
        return toneNo;
    }

    public String getPersonalColor() {
        return personalColor;
    }

    public String getAgeBand() {
        return ageBand;
    }

    public Integer getMainSkinTypeId() {
        return mainSkinTypeId;
    }

    public Integer getSubSkinTypeId() {
        return subSkinTypeId;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", gender='" + gender + '\'' +
                ", toneNo=" + toneNo +
                ", personalColor='" + personalColor + '\'' +
                ", ageBand='" + ageBand + '\'' +
                ", mainSkinTypeId=" + mainSkinTypeId +
                ", subSkinTypeId=" + subSkinTypeId +
                '}';
    }
}
