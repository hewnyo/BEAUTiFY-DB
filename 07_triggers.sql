CREATE OR REPLACE TRIGGER trg_review_one_per_product
BEFORE INSERT ON Review
FOR EACH ROW
DECLARE
    v_cnt   NUMBER;
BEGIN
    SELECT COUNT(*)
    FROM Review
    WHERE user_id=:NEW.user_id
    AND product_id=:NEW.product_id;

    IF v_cnt > 0 THEN
        RAISE_APPLICATION_ERROR(
            -200001,
            '같은 제품에 대해서는 리뷰를 한 번만 작성할 수 있습니다.'
        );
    END IF;

    IF :NEW.created_at IS NULL THEN
        :NEW.created_at := SYSDATE;
    END IF;
END;
/