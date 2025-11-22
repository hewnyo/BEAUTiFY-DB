CREATE TABLE Users (
    user_id     VARCHAR2(10)    PRIMARY KEY,
    password    VARCHAR2(20)    NOT NULL,
    name        VARCHAR2(10)    NOT NULL,
    nickname    VARCHAR2(10)    NOT NULL,
    age         NUMBER(3),

    CONSTRAINT uq_users_nickname UNIQUE (nickname),
    CONSTRAINT chk_users_age CHECK (age BETWEEN 0 AND 120)
);

CREATE TABLE UserProfile (
    user_id         VARCHAR2(10)    PRIMARY KEY,
    gender          char(1),
    tone_no         NUMBER(2),
    personal_color  VARCHAR2(20),
    age_band        varchar2(10),

    CONSTRAINT fk_user_profile_user
        FOREIGN KEY (user_id)
        REFERENCES Users (user_id),

    CONSTRAINT chk_user_profile_gender
        CHECK (gender IN ('F', 'M')),

    CONSTRAINT chk_user_profile_tone_no
        CHECK (tone_no BETWEEN 0 AND 30),
    
    CONSTRAINT chk_user_profile_personal_color
        CHECK(personal_color IN (
            'WARM', 'COOL', 'SPRING_WARM', 'SUMMER_COOL', 'AUTUMN_WARM', 'WINTER_COOL',
            'SPRING_WARM_LIGHT', 'SPRING_WARM_BRIGHT', 'SUMMER_COOL_LIGHT', 'SUMMER_COOL_MUTE', 'AUTUMN_WARM_MUTE', 'AUTUMN_WARM_DEEP', 'WINTER_COOL_BRIGHT', 'WINTER_COOL_DEEP'
        )),
    
    CONSTRAINT chk_user_profile_age_band
        CHECK(age_band IN (
            '10', '20', '30', '40', '50', '60_PLUS'
        ))
);

CREATE TABLE SkinType (
    skin_type_id NUMBER(5) PRIMARY KEY,
    type_name    VARCHAR2(20) UNIQUE NOT NULL
);