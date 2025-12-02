-- 관계 테이블은 이름 뒤에 _REL을 붙임
-- 한 사람이 여러 개의 스킨타입을 가질 수 있도록 관계 테이블 생성
CREATE TABLE UserSkinType_REL (
    user_id         VARCHAR2(10)    NOT NULL,
    skin_type_id    NUMBER(5)       NOT NULL,

    CONSTRAINT pk_user_skin_type PRIMARY KEY (user_id, skin_type_id),

    CONSTRAINT fk_user_skin_type_user
        FOREIGN KEY (user_id)
        REFERENCES Users(user_id),

    CONSTRAINT fk_user_skin_type_master
        FOREIGN KEY (skin_type_id)
        REFERENCES SkinType(skin_type_id)
);

CREATE TABLE ProductIngredient_REL (
    product_id      NUMBER(5)      NOT NULL,
    ingr_id         NUMBER(5)       NOT NULL,

    CONSTRAINT pk_product_ingredient
        PRIMARY KEY (product_id, ingr_id),

    CONSTRAINT fk_product_ingredient_ingredient
        FOREIGN KEY (ingr_id)
        REFERENCES Ingredient (ingr_id),

    CONSTRAINT fk_product_ingredient_product
        FOREIGN KEY (product_id)
        REFERENCES Product (product_id)
);

CREATE TABLE Review (
    review_id       NUMBER(10)      PRIMARY KEY,
    user_id         VARCHAR2(10)    NOT NULL,
    product_id      NUMBER(5)       NOT NULL,
    rating          NUMBER(1)       NOT NULL,
    trouble         CHAR(1),               -- 트러블 여부 (Y/N)
    repurchase      CHAR(1),               -- 재구매 의사 (Y/N)
    review_text     VARCHAR2(1000),
    created_at      DATE            DEFAULT SYSDATE,

    CONSTRAINT fk_review_user
        FOREIGN KEY (user_id)
        REFERENCES Users (user_id),

    CONSTRAINT fk_review_product
        FOREIGN KEY (product_id)
        REFERENCES Product (product_id),

    CONSTRAINT chk_review_rating 
        CHECK (rating BETWEEN 1 AND 5),

    CONSTRAINT chk_review_trouble
        CHECK(trouble IN ('Y', 'N')),
    
    CONSTRAINT chk_review_repurchase
        CHECK(repurchase IN ('Y', 'N'))
);

CREATE TABLE ReviewTag (
    tag_id      NUMBER(5)       PRIMARY KEY,
    tag_name    VARCHAR2(20)    UNIQUE NOT NULL
);

CREATE TABLE ReviewTag_REL (
    review_id   NUMBER(10),
    tag_id      NUMBER(5),

    CONSTRAINT pk_review_tag_rel PRIMARY KEY (review_id, tag_id),

    CONSTRAINT fk_review_id
        FOREIGN KEY (review_id)
        REFERENCES Review (review_id),
    
    CONSTRAINT fk_tag_id
        FOREIGN KEY (tag_id)
        REFERENCES ReviewTag (tag_id)
);

CREATE TABLE FollowUser (
    follower_id     VARCHAR2(10)    NOT NULL,
    followee_id     VARCHAR2(10)    NOT NULL,

    CONSTRAINT pk_follow_user
        PRIMARY KEY (follower_id, followee_id),

    CONSTRAINT fk_follow_user_follower
        FOREIGN KEY (follower_id)
        REFERENCES Users (user_id),

    CONSTRAINT fk_follow_user_followee
        FOREIGN KEY (followee_id)
        REFERENCES Users (user_id),

    CONSTRAINT chk_follow_user_self
        CHECK (followee_id <> follower_id)
);

------------------------------------------------------------
-- 관심 제품 (즐겨찾기) (User N : N Product)
------------------------------------------------------------
CREATE TABLE Favorite (
    user_id     VARCHAR2(10)    NOT NULL,
    product_id  NUMBER(5)       NOT NULL,

    CONSTRAINT pk_favorite
        PRIMARY KEY (user_id, product_id),

    CONSTRAINT fk_favorite_user
        FOREIGN KEY (user_id)
        REFERENCES Users (user_id),

    CONSTRAINT fk_favorite_product
        FOREIGN KEY (product_id)
        REFERENCES Product (product_id)
);

------------------------------------------------------------
-- 추천 로그 (RecommendationLog)
------------------------------------------------------------
CREATE TABLE RecommendationLog (
    rec_id          NUMBER(10)      PRIMARY KEY,
    user_id         VARCHAR2(10)    NOT NULL,
    product_id      NUMBER(5)       NOT NULL,
    algo_version    VARCHAR2(20),
    rule_explain    VARCHAR2(400),
    score           NUMBER(5,2),
    created_at      DATE            DEFAULT SYSDATE,

    CONSTRAINT fk_reclog_user
        FOREIGN KEY (user_id)
        REFERENCES Users (user_id),

    CONSTRAINT fk_reclog_product
        FOREIGN KEY (product_id)
        REFERENCES Product (product_id)
);