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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 22;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'AUTUMN_WARM_MUTE';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 1;  -- 지성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 5; -- 민감성
BEGIN
    SAVEPOINT sp_t1;
    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 21;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'SPRING_WARM_LIGHT';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 4;  -- 수부지
BEGIN
    SAVEPOINT sp_t1;
    --------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 22;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'SPRING_WARM';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 5; -- 민감성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 22;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'AUTUMN_WARM_DEEP';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
BEGIN
    SAVEPOINT sp_t1;
  
    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 21;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'SUMMER_COOL_MUTE';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
    v_skin_type_id_2   SkinType.skin_type_id%TYPE := 4; -- 수부지
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'F';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := 19;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= 'SUMMER_COOL_LIGHT';

    -- 스킨타입
    v_skin_type_id_1  SkinType.skin_type_id%TYPE := 3;  -- 복합성
BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

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
    v_gender        UserProfile_BASE.gender%TYPE         := 'M';
    v_tone_no       UserProfile_BASE.tone_no%TYPE        := NULL;
    v_personal_color UserProfile_BASE.personal_color%TYPE:= NULL;

BEGIN
    SAVEPOINT sp_t1;

    --------------------------------------------------------
    -- 1) Users 등록
    --------------------------------------------------------
    INSERT INTO Users (user_id, password, name, nickname, age)
    VALUES (v_user_id, v_password, v_name, v_nickname, v_age);

    --------------------------------------------------------
    -- 2) UserProfile_BASE 등록
    --------------------------------------------------------
    INSERT INTO UserProfile_BASE (user_id, gender, tone_no, personal_color)
    VALUES (v_user_id, v_gender, v_tone_no, v_personal_color);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('T1 완료: 회원/프로필/스킨타입 등록 - 김도형');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK TO sp_t1;
        DBMS_OUTPUT.PUT_LINE('T1 실패: ' || SQLERRM);
END;
/


