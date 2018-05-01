/*CREATE TABLE ERROR_MARGIN (
  ID                NUMBER            NOT NULL,
  VALUES            ARRAY             NOT NULL,
  constraint ERROR_MARGIN_PK primary key (ID)
  );

CREATE TABLE REFERENCE_CURVE (
  ID                NUMBER            NOT NULL,
  NAME              VARCHAR2(100)     NOT NULL,
  ACRONYM           VARCHAR2(10),
  NOTE              VARCHAR2(100),
  VALUES            ARRAY             NOT NULL,
  ERROR_MARGIN_ID   NUMBER,

  constraint REFERENCE_CURVE_PK primary key (ID),
  constraint REFERENCE_CURVE_UK unique (NAME),
  constraint ERROR_MARGIN_FK foreign key (ERROR_MARGIN_ID)
  references ERROR_MARGIN (ID)
  );
*/
create sequence SEQ_GLOBAL_HIBERNATE start with 120 increment by 1;