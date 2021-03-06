CREATE TABLE LOCATIONS(
                          LOC_ID INT PRIMARY KEY NOT NULL,
                          LOC_CODE VARCHAR(10) NOT NULL,
                          LOC_NAME VARCHAR(50) NOT NULL,
                          LOC_CMP_ID INT REFERENCES COMPANIES (CMP_ID) NOT NULL,
                          LOC_ADR_ID INT REFERENCES ADDRESSES (ADR_ID) NOT NULL,
                          LOC_WRH_ID INT REFERENCES WORKING_HOURS (WRH_ID) NOT NULL,
                          LOC_REMOVED BOOLEAN DEFAULT FALSE NOT NULL,
                          CONSTRAINT LOC_CODE_UNIQUE UNIQUE (LOC_CODE),
                          CONSTRAINT LOC_NAME_UNIQUE UNIQUE (LOC_NAME)
);

COMMENT ON COLUMN LOCATIONS.LOC_ID IS 'Location ID (primary key)';
COMMENT ON COLUMN LOCATIONS.LOC_CODE IS 'Location business code (unique)';
COMMENT ON COLUMN LOCATIONS.LOC_NAME IS 'Location name (unique)';
COMMENT ON COLUMN LOCATIONS.LOC_CMP_ID IS 'Location company (foreign key)';
COMMENT ON COLUMN LOCATIONS.LOC_ADR_ID IS 'Location address (foreign key)';
COMMENT ON COLUMN LOCATIONS.LOC_WRH_ID IS 'Location working hours (foreign key)';
COMMENT ON COLUMN LOCATIONS.LOC_REMOVED IS 'Location soft delete';

CREATE SEQUENCE LOC_SEQ
    START WITH 1
    INCREMENT BY 50
    MINVALUE 1
    MAXVALUE 100000000
    CYCLE;