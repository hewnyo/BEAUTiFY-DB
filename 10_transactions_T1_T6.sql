------------------------------------------------------------
-- T1. 회원 가입 / 프로필 / 스킨타입 등록
------------------------------------------------------------
DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'ynna';
    v_password  Users.password%TYPE  := 'ynna321';
    v_name      Users.name%TYPE      := '김윤나';
    v_nickname  Users.nickname%TYPE  := '융나';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 22;
    v_personal_color UserProfile.personal_color%TYPE:= 'AUTUMN_WARM_MUTE';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 1;  -- 지성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 5; -- 민감성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_2);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 김윤나');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'na0729';
    v_password  Users.password%TYPE  := 'kkkny2004!';
    v_name      Users.name%TYPE      := '김나연';
    v_nickname  Users.nickname%TYPE  := '나욘';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 21;
    v_personal_color UserProfile.personal_color%TYPE:= 'SPRING_WARM_LIGHT';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 4;  -- 수부지
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 김나연');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'yeun';
    v_password  Users.password%TYPE  := 'yeun0921';
    v_name      Users.name%TYPE      := '민예은';
    v_nickname  Users.nickname%TYPE  := '예은';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 22;
    v_personal_color UserProfile.personal_color%TYPE:= 'SPRING_WARM';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 5; -- 민감성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_2);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 민예은');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'jangh';
    v_password  Users.password%TYPE  := 'hyewon0120!';
    v_name      Users.name%TYPE      := '장혜원';
    v_nickname  Users.nickname%TYPE  := '혜원';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 22;
    v_personal_color UserProfile.personal_color%TYPE:= 'AUTUMN_WARM_DEEP';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 장혜원');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'zero';
    v_password  Users.password%TYPE  := 'zer025';
    v_name      Users.name%TYPE      := '정태영';
    v_nickname  Users.nickname%TYPE  := '영이';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 21;
    v_personal_color UserProfile.personal_color%TYPE:= 'SUMMER_COOL_MUTE';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 4; -- 수부지
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_2);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 정태영');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'cherrie';
    v_password  Users.password%TYPE  := 'cherriesss';
    v_name      Users.name%TYPE      := '이민서';
    v_nickname  Users.nickname%TYPE  := '밍덩이';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'F';
    v_tone_no       UserProfile.tone_no%TYPE        := 19;
    v_personal_color UserProfile.personal_color%TYPE:= 'SUMMER_COOL_LIGHT';
    v_age_band      UserProfile.age_band%TYPE;

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    --------------------------------------------------------
    -- 3) UserSkinType_REL 등록 (한 사용자가 여러 스킨타입을 가질 수 있음)
    --------------------------------------------------------
    INSERT INTO UserSkinType_REL (user_id, skin_type_id)
    VALUES (v_user_id, v_skin_type_id_1);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 이민서');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/

DECLARE
    -- 회원 기본 정보
    v_user_id   Users.user_id%TYPE   := 'ddabong';
    v_password  Users.password%TYPE  := 'ddabong12';
    v_name      Users.name%TYPE      := '김도형';
    v_nickname  Users.nickname%TYPE  := '엄지';
    v_age       Users.age%TYPE       := 22;

    -- 프로필 정보
    v_gender        UserProfile.gender%TYPE         := 'M';
    v_tone_no       UserProfile.tone_no%TYPE        := NULL;
    v_personal_color UserProfile.personal_color%TYPE:= NULL;
    v_age_band      UserProfile.age_band%TYPE;

BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 0) 연령대(age_band) 계산 (10, 20, 30, 40, 50, 60_PLUS)
    --------------------------------------------------------
    IF v_age < 20 THEN
        v_age_band := '10';
    ELSIF v_age < 30 THEN
        v_age_band := '20';
    ELSIF v_age < 40 THEN
        v_age_band := '30';
    ELSIF v_age < 50 THEN
        v_age_band := '40';
    ELSIF v_age < 60 THEN
        v_age_band := '50';
    ELSE
        v_age_band := '60_PLUS';
    END IF;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile 등록
    --------------------------------------------------------
    INSERT INTO UserProfile (user_id, gender, tone_no, personal_color, age_band)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color, v_age_band);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 김도형');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/
