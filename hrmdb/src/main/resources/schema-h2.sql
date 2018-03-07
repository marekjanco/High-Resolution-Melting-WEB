CREATE TABLE NUMBER_ARRAY (
  ID          NUMBER          NOT NULL,
  NAME        VARCHAR2(100)   NOT NULL,
  NUMBERS     varchar(5000)   NOT NULL
  );

create sequence SEQ_GLOBAL_HIBERNATE start with 120 increment by 1;