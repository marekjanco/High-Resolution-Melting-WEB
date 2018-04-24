CREATE TABLE REFERENCE_CURVE (
  ID          NUMBER          NOT NULL,
  NAME        VARCHAR2(100)   NOT NULL,
  ACRONYM     VARCHAR2(10),
  NOTE        VARCHAR2(100),
  VALUES      ARRAY           NOT NULL,
  constraint REFERENCE_CURVE_PK primary key (ID),
  constraint  REFERENCE_CURVE_UK unique (NAME)
  );

create sequence SEQ_GLOBAL_HIBERNATE start with 120 increment by 1;