--- T8: 특정 상품의 리뷰 개수 갱신 트랜잭션
BEGIN
    SAVEPOINT sp_t8;

    UPDATE Product p
    SET p.review_count = (
        SELECT COUNT(*)
        FROM Review r
        WHERE r.product_id = p.product_id
    )
    WHERE p.product_id = 100;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T8 완료: 리뷰 개수 갱신');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('T8 실패: ' || SQLERRM);
        ROLLBACK TO sp_t8;
END;
/
