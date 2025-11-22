CREATE TABLE Ingredient (
    ingr_id     NUMBER(5)       PRIMARY KEY,
    inci_name   VARCHAR2(40)    UNIQUE NOT NULL,
    ingr_name   VARCHAR2(20)    UNIQUE NOT NULL,
    ewg_level   VARCHAR2(5),
    allergy     char(1)         NOT NULL,
    
    CONSTRAINT chk_ingredient_allergy
        CHECK (allergy IN ('Y', 'N'))
);

CREATE TABLE RiskIngredient (
    ingr_id     number(5)   NOT NULL,
    inci_name   VARCHAR(20) NOT NULL,

    CONSTRAINT pk_RiskIngredient PRIMARY KEY (ingr_id),

    CONSTRAINT fk_RiskIngredient_id
        FOREIGN KEY (ingr_id)
        REFERENCES Ingredient(ingr_id),
    
    CONSTRAINT fk_RiskIngredient_name
        FOREIGN KEY (inci_name)
        REFERENCES Ingredient(inci_name)
);