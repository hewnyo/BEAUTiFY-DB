CREATE TABLE Ingredient (
    ingr_id     NUMBER(5)       PRIMARY KEY,
    inci_name   VARCHAR2(200)    UNIQUE NOT NULL,
    ingr_name   VARCHAR2(200)    UNIQUE NOT NULL,
    ewg_level   VARCHAR2(10)
);

CREATE TABLE RiskIngredient (
    ingr_id     number(5)   PRIMARY KEY,
    inci_name   VARCHAR(200) UNIQUE NOT NULL,

    CONSTRAINT fk_RiskIngredient_id
        FOREIGN KEY (ingr_id)
        REFERENCES Ingredient(ingr_id),
    
    CONSTRAINT fk_RiskIngredient_name
        FOREIGN KEY (inci_name)
        REFERENCES Ingredient(inci_name)
);

CREATE TABLE AllergyIngredient (
    ingr_id     number(5)   PRIMARY KEY,
    inci_name   VARCHAR(200) UNIQUE NOT NULL,
    
    CONSTRAINT fk_AllergyIngredient_id
        FOREIGN KEY (ingr_id)
        REFERENCES Ingredient(ingr_id),
    
    CONSTRAINT fk_AllergyIngredient_name
        FOREIGN KEY (inci_name)
        REFERENCES Ingredient(inci_name)
);