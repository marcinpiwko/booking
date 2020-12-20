CREATE TABLE COMPANIES(
                                CMP_ID INT PRIMARY KEY NOT NULL,
                                CMP_CODE VARCHAR(10) NOT NULL,
                                CMP_NAME VARCHAR(50) NOT NULL,
                                CMP_CANCELLATION_TIME INT NOT NULL,
                                CMP_REGISTRATION_DATE TIMESTAMP DEFAULT now() NOT NULL,
                                CMP_MODIFICATION_DATE TIMESTAMP,
                                CMP_REMOVED BOOLEAN DEFAULT FALSE NOT NULL,
                                CONSTRAINT CMP_CODE_UNIQUE UNIQUE (CMP_CODE),
                                CONSTRAINT CMP_NAME_UNIQUE UNIQUE (CMP_NAME)
);

COMMENT ON COLUMN COMPANIES.CMP_ID IS 'Company ID (primary key)';
COMMENT ON COLUMN COMPANIES.CMP_CODE IS 'Company business code (unique)';
COMMENT ON COLUMN COMPANIES.CMP_NAME IS 'Company name (unique)';
COMMENT ON COLUMN COMPANIES.CMP_CANCELLATION_TIME IS 'Company reservation cancellation time';
COMMENT ON COLUMN COMPANIES.CMP_REGISTRATION_DATE IS 'Company registration date';
COMMENT ON COLUMN COMPANIES.CMP_MODIFICATION_DATE IS 'Company last modification date';
COMMENT ON COLUMN COMPANIES.CMP_REMOVED IS 'Company soft delete';

CREATE SEQUENCE CMP_SEQ
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 100000000
    CYCLE;