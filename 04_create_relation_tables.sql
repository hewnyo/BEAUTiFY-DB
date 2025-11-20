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
        REFERENCES Users(skin_type_id)
);

-- 제품 카테고리 관계 테이블
CREATE TABLE ProdctCategory_REL (
    product_id      NUMBER(5)   NOT NULL,
    caregory_id     NUMBER(5)   NOT NULL,

    CONSTRAINT pk_product_category_rel PRIMARY KEY (product_id, caregory_id),

    CONSTRAINT fk_product_id
        FOREIGN KEY (product_id)
        REFERENCES Product(product_id),

    CONSTRAINT fk_category_id
        FOREIGN KEY (category_id)
        REFERENCES ProductCategory (caregory_id)
);