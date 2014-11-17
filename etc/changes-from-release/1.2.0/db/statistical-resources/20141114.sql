-- -------------------------------------------------------------------------------------------------------------------
-- METAMAC-1979 - En las importaciones, almacenar mapeo de dimensiones
-- -------------------------------------------------------------------------------------------------------------------

CREATE sequence SEQ_DIM_REPR_MAPPING;

CREATE TABLE TB_DIM_REPR_MAPPING (
  ID NUMBER(19) NOT NULL,
  DATASOURCE_FILENAME VARCHAR2(255 CHAR) NOT NULL,
  MAPPING VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);

ALTER TABLE TB_DIM_REPR_MAPPING ADD CONSTRAINT PK_TB_DIM_REPR_MAPPING
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DIM_REPR_MAPPING ADD CONSTRAINT FK_TB_DIM_REPR_MAPPING_DATAS50
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

ALTER TABLE TB_DIM_REPR_MAPPING
	ADD CONSTRAINT UQ_TB_DIM_REPR_MAPPING UNIQUE (ID)
;