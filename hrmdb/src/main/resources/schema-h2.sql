CREATE TABLE NUMBER_ARRAY (
  ID          NUMBER          NOT NULL,
  NAME        VARCHAR2(100)   NOT NULL,
  ACRONYM     VARCHAR2(10)    NOT NULL,
  NOTE        VARCHAR2(100)   NOT NULL,
  NUMBERS     varchar(10000)   NOT NULL
  );

create sequence SEQ_GLOBAL_HIBERNATE start with 120 increment by 1;