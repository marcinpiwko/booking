CREATE TABLE VIRTUAL_USERS(
                      VSR_ID INT PRIMARY KEY NOT NULL,
                      VSR_EMAIL VARCHAR(30) NOT NULL,
                      VSR_FIRST_NAME VARCHAR(50) NOT NULL,
                      VSR_LAST_NAME VARCHAR(50) NOT NULL,
                      VSR_BIRTH_DATE DATE,
                      VSR_GENDER VARCHAR(1),
                      VSR_PHONE_NUMBER VARCHAR(9),
                      CONSTRAINT VSR_EMAIL_UNIQUE UNIQUE (VSR_EMAIL)
);

COMMENT ON COLUMN VIRTUAL_USERS.VSR_ID IS 'User ID (primary key)';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_EMAIL IS 'User email (username)';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_FIRST_NAME IS 'User first name';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_LAST_NAME IS 'User last name';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_BIRTH_DATE IS 'User birth date';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_GENDER IS 'User gender';
COMMENT ON COLUMN VIRTUAL_USERS.VSR_PHONE_NUMBER IS 'User phone number';

CREATE SEQUENCE VSR_SEQ
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 100000000
    CYCLE;