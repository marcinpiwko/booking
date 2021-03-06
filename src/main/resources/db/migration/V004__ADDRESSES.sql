CREATE TABLE ADDRESSES(
                         ADR_ID INT PRIMARY KEY NOT NULL,
                         ADR_STREET VARCHAR(50) NOT NULL,
                         ADR_CITY VARCHAR(50) NOT NULL,
                         ADR_COUNTRY VARCHAR(50) NOT NULL,
                         ADR_POSTAL_CODE VARCHAR(6) NOT NULL,
                         ADR_EMAIL VARCHAR(30),
                         ADR_PHONE_NUMBER VARCHAR(9)
);

COMMENT ON COLUMN ADDRESSES.ADR_ID IS 'Address ID (primary key)';
COMMENT ON COLUMN ADDRESSES.ADR_STREET IS 'Address street';
COMMENT ON COLUMN ADDRESSES.ADR_CITY IS 'Address city';
COMMENT ON COLUMN ADDRESSES.ADR_COUNTRY IS 'Address country';
COMMENT ON COLUMN ADDRESSES.ADR_POSTAL_CODE IS 'Address postal code';
COMMENT ON COLUMN ADDRESSES.ADR_EMAIL IS 'Address email (optional)';
COMMENT ON COLUMN ADDRESSES.ADR_PHONE_NUMBER IS 'Address phone number (optional)';

CREATE SEQUENCE ADR_SEQ
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 100000000
    CYCLE;