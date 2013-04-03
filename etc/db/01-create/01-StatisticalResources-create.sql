

-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    
 	
CREATE sequence SEQ_STAT_RESOURCES;
 	
CREATE sequence SEQ_I18NSTRS;
 	
CREATE sequence SEQ_RELATED_RESOURCES;
 	
CREATE sequence SEQ_EXTERNAL_ITEMS;
 	
CREATE sequence SEQ_VERSION_RATIONALE_TYPE;
 	
CREATE sequence SEQ_L10NSTRS;
 	
CREATE sequence SEQ_DATASETS;
 	
CREATE sequence SEQ_STAT_OFFICIALITY;
 	
CREATE sequence SEQ_DATASETS_VERSIONS;
 	
CREATE sequence SEQ_DATASOURCES;
 	
CREATE sequence SEQ_CUBES;
 	
CREATE sequence SEQ_PUBLICATIONS;
 	
CREATE sequence SEQ_PUBLICATIONS_VERSIONS;
 	
CREATE sequence SEQ_ELEMENTS_LEVELS;
 	
CREATE sequence SEQ_CHAPTERS;
 	
CREATE sequence SEQ_QUERIES;
 	
CREATE sequence SEQ_QUERY_SELECTION_ITEMS;
 	
CREATE sequence SEQ_CODE_ITEMS;
 	


-- Create normal entities
    
CREATE TABLE TB_STAT_RESOURCES (
  ID NUMBER(19) NOT NULL,
  UPDATE_DATE_TZ VARCHAR2(50),
  UPDATE_DATE TIMESTAMP ,
  UUID VARCHAR2(36) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50),
  LAST_UPDATED_TZ VARCHAR2(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50),
  VERSION NUMBER(19) NOT NULL,
  STAT_RESOURCE_TYPE VARCHAR2(255) NOT NULL,
  CODE VARCHAR2(255),
  URI VARCHAR2(255),
  URN VARCHAR2(255),
  TITLE_FK NUMBER(19),
  DESCRIPTION_FK NUMBER(19),
  VERSION_LOGIC VARCHAR2(255),
  NEXT_VERSION_DATE_TZ VARCHAR2(50),
  NEXT_VERSION_DATE TIMESTAMP ,
  VALID_FROM_TZ VARCHAR2(50),
  VALID_FROM TIMESTAMP ,
  VALID_TO_TZ VARCHAR2(50),
  VALID_TO TIMESTAMP ,
  IS_LAST_VERSION NUMBER(1,0),
  VERSION_RATIONALE_FK NUMBER(19),
  NEXT_VERSION VARCHAR2(255),
  CREATION_DATE_TZ VARCHAR2(50),
  CREATION_DATE TIMESTAMP ,
  CREATION_USER VARCHAR2(255),
  PRODUCTION_VALIDATION_DATE_TZ VARCHAR2(50),
  PRODUCTION_VALIDATION_DATE TIMESTAMP ,
  PRODUCTION_VALIDATION_USER VARCHAR2(255),
  DIFFUSION_VALIDATION_DATE_TZ VARCHAR2(50),
  DIFFUSION_VALIDATION_DATE TIMESTAMP ,
  DIFFUSION_VALIDATION_USER VARCHAR2(255),
  REJECT_VALIDATION_DATE_TZ VARCHAR2(50),
  REJECT_VALIDATION_DATE TIMESTAMP ,
  REJECT_VALIDATION_USER VARCHAR2(255),
  INTERNAL_PUBLICATION_DATE_TZ VARCHAR2(50),
  INTERNAL_PUBLICATION_DATE TIMESTAMP ,
  INTERNAL_PUBLICATION_USER VARCHAR2(255),
  EXTERNAL_PUBLICATION_DATE_TZ VARCHAR2(50),
  EXTERNAL_PUBLICATION_DATE TIMESTAMP ,
  EXTERNAL_PUBLICATION_USER VARCHAR2(255),
  EXTERNAL_PUBLICATION_FAILED NUMBER(1,0),
  EXTERNAL_PUBLICATION_FAIL39_TZ VARCHAR2(50),
  EXTERNAL_PUBLICATION_FAIL39 TIMESTAMP ,
  REPLACES_VERSION_FK NUMBER(19),
  IS_REPLACED_BY_VERSION_FK NUMBER(19),
  PROC_STATUS VARCHAR2(255),
  RESOURCE_CREATED_DATE_TZ VARCHAR2(50),
  RESOURCE_CREATED_DATE TIMESTAMP ,
  LAST_UPDATE_TZ VARCHAR2(50),
  LAST_UPDATE TIMESTAMP ,
  NEWNESS_UNTIL_DATE_TZ VARCHAR2(50),
  NEWNESS_UNTIL_DATE TIMESTAMP ,
  COPYRIGHTED_DATE_TZ VARCHAR2(50),
  COPYRIGHTED_DATE TIMESTAMP ,
  LANGUAGE_FK NUMBER(19),
  STAT_OPERATION_FK NUMBER(19),
  STAT_OPERATION_INST_FK NUMBER(19),
  SUBTITLE_FK NUMBER(19),
  TITLE_ALTERNATIVE_FK NUMBER(19),
  ABSTRACT_FK NUMBER(19),
  MAINTAINER_FK NUMBER(19),
  CREATOR_FK NUMBER(19),
  CONFORMS_TO_FK NUMBER(19),
  CONFORMS_TO_INTERNAL_FK NUMBER(19),
  REPLACES_FK NUMBER(19),
  IS_REPLACED_BY_FK NUMBER(19),
  RIGHTS_HOLDER_FK NUMBER(19),
  LICENSE_FK NUMBER(19),
  ACCESS_RIGHTS_FK NUMBER(19),
  TYPE VARCHAR2(255)
);


CREATE TABLE TB_INTERNATIONAL_STRINGS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_RELATED_RESOURCES (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255) NOT NULL,
  URN VARCHAR2(4000) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  TITLE_FK NUMBER(19),
  TYPE VARCHAR2(255) NOT NULL
);


CREATE TABLE TB_EXTERNAL_ITEMS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255) NOT NULL,
  URI VARCHAR2(4000) NOT NULL,
  URN VARCHAR2(4000) NOT NULL,
  MANAGEMENT_APP_URL VARCHAR2(4000),
  VERSION NUMBER(19) NOT NULL,
  TITLE_FK NUMBER(19),
  TYPE VARCHAR2(255) NOT NULL
);


CREATE TABLE TB_VERSION_RATIONALE_TYPE (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  VALUE VARCHAR2(255) NOT NULL
);


CREATE TABLE TB_LOCALISED_STRINGS (
  ID NUMBER(19) NOT NULL,
  LABEL VARCHAR2(4000) NOT NULL,
  LOCALE VARCHAR2(255) NOT NULL,
  IS_UNMODIFIABLE NUMBER(1,0),
  VERSION NUMBER(19) NOT NULL,
  INTERNATIONAL_STRING_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DATASETS (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_LIS_STAT_OFFICIALITY (
  ID NUMBER(19) NOT NULL,
  IDENTIFIER VARCHAR2(255) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DESCRIPTION NUMBER(19)
);


CREATE TABLE TB_DATASETS_VERSIONS (
  ID NUMBER(19) NOT NULL,
  DATE_START_TZ VARCHAR2(50),
  DATE_START TIMESTAMP ,
  DATE_END_TZ VARCHAR2(50),
  DATE_END TIMESTAMP ,
  FORMAT_EXTENT_OBSERVATIONS NUMBER(10),
  FORMAT_EXTENT_DIMENSIONS NUMBER(10),
  DATE_NEXT_UPDATE_TZ VARCHAR2(50),
  DATE_NEXT_UPDATE TIMESTAMP ,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  RELATED_DSD_FK NUMBER(19),
  UPDATE_FREQUENCY_FK NUMBER(19),
  STAT_OFFICIALITY_FK NUMBER(19),
  BIBLIOGRAPHIC_CITATION_FK NUMBER(19),
  DATASET_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DATASOURCES (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19)
);


CREATE TABLE TB_CUBES (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50),
  LAST_UPDATED_TZ VARCHAR2(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50),
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_PUBLICATIONS (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_PUBLICATIONS_VERSIONS (
  ID NUMBER(19) NOT NULL,
  FORMAT_EXTENT_RESOURCES NUMBER(10),
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  PUBLICATION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_ELEMENTS_LEVELS (
  ID NUMBER(19) NOT NULL,
  ORDER_IN_LEVEL NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  PARENT_FK NUMBER(19),
  CHAPTER_FK NUMBER(19),
  TABLE_FK NUMBER(19),
  PUBLICATION_VERSION_ALL_FK NUMBER(19) NOT NULL,
  PUBLICATION_VERSION_FIRST_FK NUMBER(19)
);


CREATE TABLE TB_CHAPTERS (
  ID NUMBER(19) NOT NULL,
  UUID VARCHAR2(36) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50),
  LAST_UPDATED_TZ VARCHAR2(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50),
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_QUERIES (
  ID NUMBER(19) NOT NULL,
  LATEST_DATA_NUMBER NUMBER(10),
  UUID VARCHAR2(36) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL,
  STATUS VARCHAR2(255) NOT NULL,
  TYPE VARCHAR2(255) NOT NULL
);


CREATE TABLE TB_QUERY_SELECTION_ITEMS (
  ID NUMBER(19) NOT NULL,
  DIMENSION VARCHAR2(255) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  QUERY_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_CODE_ITEMS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  QUERY_SELECTION_ITEM_FK NUMBER(19) NOT NULL
);



-- Create many to many relations
    
CREATE TABLE DATASET_TB_PUBLICATIONS_VER (
  DATASET NUMBER(19) NOT NULL,
  TB_PUBLICATIONS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_GEOGRAPHIC_COVERAGE (
  GEOGRAPHIC_COVERAGE NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_GEO_GRANULARITY (
  GEOGRAPHIC_GRANULARITY NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_MEASURES (
  MEASURE NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_STATISTICAL_UNIT (
  STATISTICAL_UNIT NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_TEMPORAL_COVERAGE (
  TEMPORAL_COVERAGE NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_TEMP_GRANULARITY (
  TEMPORAL_GRANULARITY NUMBER(19) NOT NULL,
  TB_DATASETS_VERSIONS NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_CONTRIBUTORS (
  CONTRIBUTOR_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_HAS_PART (
  HAS_PART_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_IS_PART_OF (
  IS_PART_OF_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_IS_REQUIRED_BY (
  IS_REQUIRED_BY_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_LANGUAGES (
  LANGUAGE NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_MEDIATORS (
  MEDIATOR_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_PUBLISHERS (
  PUBLISHER_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_PUB_CONTRIBUTORS (
  PUBLISHER_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_REQUIRES (
  REQUIRES_FK NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_VRATIONALE_TYPES (
  VERSION_RATIONALE_TYPE NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);



-- Primary keys
    
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT PK_TB_STAT_RESOURCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_INTERNATIONAL_STRINGS ADD CONSTRAINT PK_TB_INTERNATIONAL_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT PK_TB_RELATED_RESOURCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT PK_TB_EXTERNAL_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_VERSION_RATIONALE_TYPE ADD CONSTRAINT PK_TB_VERSION_RATIONALE_TYPE
	PRIMARY KEY (ID)
;

ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT PK_TB_LOCALISED_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATASETS ADD CONSTRAINT PK_TB_DATASETS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_LIS_STAT_OFFICIALITY ADD CONSTRAINT PK_TB_LIS_STAT_OFFICIALITY
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT PK_TB_DATASETS_VERSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATASOURCES ADD CONSTRAINT PK_TB_DATASOURCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CUBES ADD CONSTRAINT PK_TB_CUBES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_PUBLICATIONS ADD CONSTRAINT PK_TB_PUBLICATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT PK_TB_PUBLICATIONS_VERSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT PK_TB_ELEMENTS_LEVELS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CHAPTERS ADD CONSTRAINT PK_TB_CHAPTERS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_QUERIES ADD CONSTRAINT PK_TB_QUERIES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT PK_TB_QUERY_SELECTION_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT PK_TB_CODE_ITEMS
	PRIMARY KEY (ID)
;

    
ALTER TABLE DATASET_TB_PUBLICATIONS_VER ADD CONSTRAINT PK_DATASET_TB_PUBLICATIONS_VER
	PRIMARY KEY (DATASET, TB_PUBLICATIONS_VERSIONS)
;

ALTER TABLE TB_DV_GEOGRAPHIC_COVERAGE ADD CONSTRAINT PK_TB_DV_GEOGRAPHIC_COVERAGE
	PRIMARY KEY (GEOGRAPHIC_COVERAGE, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT PK_TB_DV_GEO_GRANULARITY
	PRIMARY KEY (GEOGRAPHIC_GRANULARITY, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_DV_MEASURES ADD CONSTRAINT PK_TB_DV_MEASURES
	PRIMARY KEY (MEASURE, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_DV_STATISTICAL_UNIT ADD CONSTRAINT PK_TB_DV_STATISTICAL_UNIT
	PRIMARY KEY (STATISTICAL_UNIT, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_DV_TEMPORAL_COVERAGE ADD CONSTRAINT PK_TB_DV_TEMPORAL_COVERAGE
	PRIMARY KEY (TEMPORAL_COVERAGE, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT PK_TB_DV_TEMP_GRANULARITY
	PRIMARY KEY (TEMPORAL_GRANULARITY, TB_DATASETS_VERSIONS)
;

ALTER TABLE TB_SR_CONTRIBUTORS ADD CONSTRAINT PK_TB_SR_CONTRIBUTORS
	PRIMARY KEY (CONTRIBUTOR_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_HAS_PART ADD CONSTRAINT PK_TB_SR_HAS_PART
	PRIMARY KEY (HAS_PART_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_IS_PART_OF ADD CONSTRAINT PK_TB_SR_IS_PART_OF
	PRIMARY KEY (IS_PART_OF_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_IS_REQUIRED_BY ADD CONSTRAINT PK_TB_SR_IS_REQUIRED_BY
	PRIMARY KEY (IS_REQUIRED_BY_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_LANGUAGES ADD CONSTRAINT PK_TB_SR_LANGUAGES
	PRIMARY KEY (LANGUAGE, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_MEDIATORS ADD CONSTRAINT PK_TB_SR_MEDIATORS
	PRIMARY KEY (MEDIATOR_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_PUBLISHERS ADD CONSTRAINT PK_TB_SR_PUBLISHERS
	PRIMARY KEY (PUBLISHER_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_PUB_CONTRIBUTORS ADD CONSTRAINT PK_TB_SR_PUB_CONTRIBUTORS
	PRIMARY KEY (PUBLISHER_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_REQUIRES ADD CONSTRAINT PK_TB_SR_REQUIRES
	PRIMARY KEY (REQUIRES_FK, TB_STAT_RESOURCES)
;

ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT PK_TB_SR_VRATIONALE_TYPES
	PRIMARY KEY (VERSION_RATIONALE_TYPE, TB_STAT_RESOURCES)
;


-- Unique constraints
    

ALTER TABLE TB_STAT_RESOURCES
    ADD CONSTRAINT UQ_TB_STAT_RESOURCES UNIQUE (UUID)
;






ALTER TABLE TB_VERSION_RATIONALE_TYPE
    ADD CONSTRAINT UQ_TB_VERSION_RATIONALE_TYPE UNIQUE (UUID)
;




ALTER TABLE TB_DATASETS
    ADD CONSTRAINT UQ_TB_DATASETS UNIQUE (UUID)
;



ALTER TABLE TB_LIS_STAT_OFFICIALITY
    ADD CONSTRAINT UQ_TB_LIS_STAT_OFFICIALITY UNIQUE (UUID)
;



ALTER TABLE TB_DATASETS_VERSIONS
    ADD CONSTRAINT UQ_TB_DATASETS_VERSIONS UNIQUE (UUID)
;



ALTER TABLE TB_DATASOURCES
    ADD CONSTRAINT UQ_TB_DATASOURCES UNIQUE (UUID)
;



ALTER TABLE TB_CUBES
    ADD CONSTRAINT UQ_TB_CUBES UNIQUE (UUID)
;



ALTER TABLE TB_PUBLICATIONS
    ADD CONSTRAINT UQ_TB_PUBLICATIONS UNIQUE (UUID)
;



ALTER TABLE TB_PUBLICATIONS_VERSIONS
    ADD CONSTRAINT UQ_TB_PUBLICATIONS_VERSIONS UNIQUE (UUID)
;



ALTER TABLE TB_ELEMENTS_LEVELS
    ADD CONSTRAINT UQ_TB_ELEMENTS_LEVELS UNIQUE (UUID)
;



ALTER TABLE TB_CHAPTERS
    ADD CONSTRAINT UQ_TB_CHAPTERS UNIQUE (UUID)
;



ALTER TABLE TB_QUERIES
    ADD CONSTRAINT UQ_TB_QUERIES UNIQUE (UUID)
;



ALTER TABLE TB_QUERY_SELECTION_ITEMS
	ADD CONSTRAINT UQ_TB_QUERY_SELECTION_ITEMS UNIQUE (ID)
;



ALTER TABLE TB_CODE_ITEMS
	ADD CONSTRAINT UQ_TB_CODE_ITEMS UNIQUE (ID)
;



-- Foreign key constraints
    

  
  
  
  
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_TITL87
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT FK_TB_EXTERNAL_ITEMS_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
  
  
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_INTE13
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
  
  
ALTER TABLE TB_LIS_STAT_OFFICIALITY ADD CONSTRAINT FK_TB_LIS_STAT_OFFICIALITY_D21
	FOREIGN KEY (DESCRIPTION) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_STAT85
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_RELA64
	FOREIGN KEY (RELATED_DSD_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_UPDA81
	FOREIGN KEY (UPDATE_FREQUENCY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_STAT99
	FOREIGN KEY (STAT_OFFICIALITY_FK) REFERENCES TB_LIS_STAT_OFFICIALITY (ID)
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_BIBL82
	FOREIGN KEY (BIBLIOGRAPHIC_CITATION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_DATA05
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID)
;

  
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_STATISTICA52
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_DATASET_VE01
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_STATISTICAL_RESO24
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;

  
  
  
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_83
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_99
	FOREIGN KEY (PUBLICATION_FK) REFERENCES TB_PUBLICATIONS (ID)
;

  
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PARENT65
	FOREIGN KEY (PARENT_FK) REFERENCES TB_ELEMENTS_LEVELS (ID)
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_CHAPTE84
	FOREIGN KEY (CHAPTER_FK) REFERENCES TB_CHAPTERS (ID)
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_TABLE_FK
	FOREIGN KEY (TABLE_FK) REFERENCES TB_CUBES (ID)
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLIC70
	FOREIGN KEY (PUBLICATION_VERSION_ALL_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID)
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLIC29
	FOREIGN KEY (PUBLICATION_VERSION_FIRST_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID)
;

  
ALTER TABLE TB_CHAPTERS ADD CONSTRAINT FK_TB_CHAPTERS_STATISTICAL_R98
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_QUERIES ADD CONSTRAINT FK_TB_QUERIES_STATISTICAL_RE28
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID)
;
ALTER TABLE TB_QUERIES ADD CONSTRAINT FK_TB_QUERIES_DATASET_VERSIO13
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT FK_TB_QUERY_SELECTION_ITEMS_18
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID)
;

  
ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT FK_TB_CODE_ITEMS_QUERY_SELEC09
	FOREIGN KEY (QUERY_SELECTION_ITEM_FK) REFERENCES TB_QUERY_SELECTION_ITEMS (ID)
;

  

ALTER TABLE DATASET_TB_PUBLICATIONS_VER ADD CONSTRAINT FK_DATASET_TB_PUBLICATIONS_V06
	FOREIGN KEY (DATASET) REFERENCES TB_DATASETS_VERSIONS (ID)
;
ALTER TABLE DATASET_TB_PUBLICATIONS_VER ADD CONSTRAINT FK_DATASET_TB_PUBLICATIONS_V32
	FOREIGN KEY (TB_PUBLICATIONS_VERSIONS) REFERENCES TB_PUBLICATIONS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_GEOGRAPHIC_COVERAGE ADD CONSTRAINT FK_TB_DV_GEOGRAPHIC_COVERAGE69
	FOREIGN KEY (GEOGRAPHIC_COVERAGE) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_GEOGRAPHIC_COVERAGE ADD CONSTRAINT FK_TB_DV_GEOGRAPHIC_COVERAGE09
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_GEO47
	FOREIGN KEY (GEOGRAPHIC_GRANULARITY) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_TB_41
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_MEASURES ADD CONSTRAINT FK_TB_DV_MEASURES_MEASURE
	FOREIGN KEY (MEASURE) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_MEASURES ADD CONSTRAINT FK_TB_DV_MEASURES_TB_DATASET64
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_DV_STATISTICAL_UNIT_ST47
	FOREIGN KEY (STATISTICAL_UNIT) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_DV_STATISTICAL_UNIT_TB91
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_TEMPORAL_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMPORAL_COVERAGE_T21
	FOREIGN KEY (TEMPORAL_COVERAGE) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_TEMPORAL_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMPORAL_COVERAGE_T00
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_TE81
	FOREIGN KEY (TEMPORAL_GRANULARITY) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_TB06
	FOREIGN KEY (TB_DATASETS_VERSIONS) REFERENCES TB_DATASETS_VERSIONS (ID)
;

  
ALTER TABLE TB_SR_CONTRIBUTORS ADD CONSTRAINT FK_TB_SR_CONTRIBUTORS_CONTRI51
	FOREIGN KEY (CONTRIBUTOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_SR_CONTRIBUTORS ADD CONSTRAINT FK_TB_SR_CONTRIBUTORS_TB_STA75
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_HAS_PART ADD CONSTRAINT FK_TB_SR_HAS_PART_HAS_PART_FK
	FOREIGN KEY (HAS_PART_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_SR_HAS_PART ADD CONSTRAINT FK_TB_SR_HAS_PART_TB_STAT_RE19
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_IS_PART_OF ADD CONSTRAINT FK_TB_SR_IS_PART_OF_IS_PART_96
	FOREIGN KEY (IS_PART_OF_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_SR_IS_PART_OF ADD CONSTRAINT FK_TB_SR_IS_PART_OF_TB_STAT_35
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_IS_REQUIRED_BY ADD CONSTRAINT FK_TB_SR_IS_REQUIRED_BY_IS_R12
	FOREIGN KEY (IS_REQUIRED_BY_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_SR_IS_REQUIRED_BY ADD CONSTRAINT FK_TB_SR_IS_REQUIRED_BY_TB_S39
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_LANGUAGES ADD CONSTRAINT FK_TB_SR_LANGUAGES_LANGUAGE
	FOREIGN KEY (LANGUAGE) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_SR_LANGUAGES ADD CONSTRAINT FK_TB_SR_LANGUAGES_TB_STAT_R12
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_MEDIATORS ADD CONSTRAINT FK_TB_SR_MEDIATORS_MEDIATOR_FK
	FOREIGN KEY (MEDIATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_SR_MEDIATORS ADD CONSTRAINT FK_TB_SR_MEDIATORS_TB_STAT_R05
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_PUBLISHERS ADD CONSTRAINT FK_TB_SR_PUBLISHERS_PUBLISHE71
	FOREIGN KEY (PUBLISHER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_SR_PUBLISHERS ADD CONSTRAINT FK_TB_SR_PUBLISHERS_TB_STAT_54
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_SR_PUB_CONTRIBUTORS_PU30
	FOREIGN KEY (PUBLISHER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_SR_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_SR_PUB_CONTRIBUTORS_TB85
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_REQUIRES ADD CONSTRAINT FK_TB_SR_REQUIRES_REQUIRES_FK
	FOREIGN KEY (REQUIRES_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_SR_REQUIRES ADD CONSTRAINT FK_TB_SR_REQUIRES_TB_STAT_RE57
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_VE86
	FOREIGN KEY (VERSION_RATIONALE_TYPE) REFERENCES TB_VERSION_RATIONALE_TYPE (ID)
;
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_TB30
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID)
;

  

  
  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_DESCRIP80
	FOREIGN KEY (DESCRIPTION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_VERSION32
	FOREIGN KEY (VERSION_RATIONALE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACE24
	FOREIGN KEY (REPLACES_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL46
	FOREIGN KEY (IS_REPLACED_BY_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_LANGUAG04
	FOREIGN KEY (LANGUAGE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_STAT_OP64
	FOREIGN KEY (STAT_OPERATION_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_STAT_OP27
	FOREIGN KEY (STAT_OPERATION_INST_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_SUBTITL88
	FOREIGN KEY (SUBTITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_A74
	FOREIGN KEY (TITLE_ALTERNATIVE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ABSTRAC54
	FOREIGN KEY (ABSTRACT_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_MAINTAI00
	FOREIGN KEY (MAINTAINER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CREATOR96
	FOREIGN KEY (CREATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORM15
	FOREIGN KEY (CONFORMS_TO_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORM59
	FOREIGN KEY (CONFORMS_TO_INTERNAL_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACE61
	FOREIGN KEY (REPLACES_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL23
	FOREIGN KEY (IS_REPLACED_BY_FK) REFERENCES TB_RELATED_RESOURCES (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_RIGHTS_72
	FOREIGN KEY (RIGHTS_HOLDER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_LICENSE17
	FOREIGN KEY (LICENSE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ACCESS_34
	FOREIGN KEY (ACCESS_RIGHTS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)
;

  


-- Index
CREATE INDEX IX_TB_STAT_RESOURCES_STAT_RE84
    ON TB_STAT_RESOURCES (STAT_RESOURCE_TYPE ASC)
;


	