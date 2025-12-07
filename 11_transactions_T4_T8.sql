
-- T4: 추천 알고리즘 실행 및 결과 저장
DECLARE
    v_user_id Users.user_id%TYPE := 'jangh';
BEGIN
    SAVEPOINT sp_t4;

    FOR rec IN (
        SELECT
            r.product_id,
            AVG(r.rating) AS score
        FROM Review r
        GROUP BY r.product_id
        -- 필요하면 여기서 HAVING / ORDER BY / FETCH FIRST 1 ROW ONLY 등 추가
    ) LOOP
        INSERT INTO RecommendationLog (
            rec_id,
            user_id,
            product_id,
            algo_version,
            rule_explain,
            score,
            created_at
        ) VALUES (
            SEQ_REC_ID.NEXTVAL,        -- ✅ 시퀀스는 VALUES 안에서만 사용
            v_user_id,
            rec.product_id,
            'V1',
            '리뷰 평균 평점 기반 추천',
            rec.score,
            SYSDATE
        );
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T4 완료: RecommendationLog에 추천 기록 저장');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t4;
        DBMS_OUTPUT.PUT_LINE('T4 실패: ' || SQLERRM);
END;
/

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



