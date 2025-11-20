CREATE TABLE Brand (
    brand_id    NUMBER(5)   PRIMARY KEY,
    brand_name  VARCHAR2(20)
);

CREATE TABLE Product (
    product_id      NUMBER(5)       PRIMARY KEY,
    brand_id        NUMBER(5)       NOT NULL,
    product_name    VARCHAR2(50)    NOT NULL,
    category        VARCHAR2(20)    NOT NULL,
    capacity        number(5),
    price           number(10)      NOT NULL,

    CONSTRAINT fk_product_brand
        FOREIGN KEY (brand_id)
        REFERENCES Brand(brand_id),

    CONSTRAINT fk_category
        FOREIGN KEY (category)
        REFERENCES ProductCategory(category_name)
);

CREATE TABLE ProductCategory (
    category_id     number(5)   PRIMARY KEY,
    category_name   VARCHAR2(20) UNIQUE NOT NULL
);