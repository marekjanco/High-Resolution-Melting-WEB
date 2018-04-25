CREATE TABLE REFERENCE_CURVE (
  ID          NUMBER          NOT NULL,
  NAME        VARCHAR2(100)   NOT NULL,
  ACRONYM     VARCHAR2(10),
  NOTE        VARCHAR2(100),
  VALUES      ARRAY           NOT NULL,
  constraint REFERENCE_CURVE_PK primary key (ID),
  constraint  REFERENCE_CURVE_UK unique (NAME)
  );

CREATE TABLE ERROR_MARGIN (
  ID            NUMBER          NOT NULL,
  REF_CURVE_ID  NUMBER          NOT NULL,
  VALUES        ARRAY           NOT NULL,

  constraint ERROR_MARGIN_PK primary key (ID),
  constraint ERROR_MARGIN_REF_CURVE_FK foreign key (REF_CURVE_ID)
  references REFERENCE_CURVE (ID)
  );

create sequence SEQ_GLOBAL_HIBERNATE start with 120 increment by 1;