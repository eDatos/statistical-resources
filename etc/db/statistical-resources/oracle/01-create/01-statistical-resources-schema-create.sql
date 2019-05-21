

-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    
 	
CREATE sequence SEQ_I18NSTRS;
 	
CREATE sequence SEQ_EXTERNAL_ITEMS;
 	
CREATE sequence SEQ_STAT_RESOURCES;
 	
CREATE sequence SEQ_DATASETS;
 	
CREATE sequence SEQ_STAT_OFFICIALITY;
 	
CREATE sequence SEQ_DATASETS_VERSIONS;
 	
CREATE sequence SEQ_PUBLICATIONS;
 	
CREATE sequence SEQ_PUBLICATIONS_VERSIONS;
 	
CREATE sequence SEQ_QUERIES;
 	
CREATE sequence SEQ_QUERIES_VERSIONS;
 	
CREATE sequence SEQ_MULTIDATASETS;
 	
CREATE sequence SEQ_MULTIDATASETS_VERSIONS;
 	
CREATE sequence SEQ_RELATED_RESOURCES;
 	
CREATE sequence SEQ_VERSION_RATIONALE_TYPE;
 	
CREATE sequence SEQ_L10NSTRS;
 	
CREATE sequence SEQ_TRANSLATIONS;
 	
CREATE sequence SEQ_ATTRIBUTE_VALUES;
 	
CREATE sequence SEQ_CATEGORISATIONS;
 	
CREATE sequence SEQ_CATEGORISATION_SEQUENCES;
 	
CREATE sequence SEQ_CODE_DIMENSIONS;
 	
CREATE sequence SEQ_DATASOURCES;
 	
CREATE sequence SEQ_DIM_REPR_MAPPING;
 	
CREATE sequence SEQ_TEMP_CODES;
 	
CREATE sequence SEQ_MD_CUBES;
 	
CREATE sequence SEQ_CUBES;
 	
CREATE sequence SEQ_ELEMENTS_LEVELS;
 	
CREATE sequence SEQ_CHAPTERS;
 	
CREATE sequence SEQ_QUERY_SELECTION_ITEMS;
 	
CREATE sequence SEQ_CODE_ITEMS;
 	
CREATE sequence SEQ_TASKS;
 	


-- Create normal entities
    
CREATE TABLE TB_INTERNATIONAL_STRINGS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_EXTERNAL_ITEMS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255 CHAR) NOT NULL,
  CODE_NESTED VARCHAR2(255 CHAR),
  URI VARCHAR2(4000 CHAR) NOT NULL,
  URN VARCHAR2(4000 CHAR),
  URN_PROVIDER VARCHAR2(4000 CHAR),
  MANAGEMENT_APP_URL VARCHAR2(4000 CHAR),
  VERSION NUMBER(19) NOT NULL,
  TITLE_FK NUMBER(19),
  TYPE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_STAT_RESOURCES (
  ID NUMBER(19) NOT NULL,
  UPDATE_DATE_TZ VARCHAR2(50 CHAR),
  UPDATE_DATE TIMESTAMP ,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  STAT_OPERATION_FK NUMBER(19),
  STAT_RESOURCE_TYPE VARCHAR2(255) NOT NULL,
  CODE VARCHAR2(255 CHAR),
  URN VARCHAR2(255 CHAR),
  TITLE_FK NUMBER(19),
  DESCRIPTION_FK NUMBER(19),
  VERSION_LOGIC VARCHAR2(255 CHAR),
  NEXT_VERSION_DATE_TZ VARCHAR2(50 CHAR),
  NEXT_VERSION_DATE TIMESTAMP ,
  VALID_FROM_TZ VARCHAR2(50 CHAR),
  VALID_FROM TIMESTAMP ,
  VALID_TO_TZ VARCHAR2(50 CHAR),
  VALID_TO TIMESTAMP ,
  VERSION_RATIONALE_FK NUMBER(19),
  NEXT_VERSION VARCHAR2(255 CHAR),
  CREATION_DATE_TZ VARCHAR2(50 CHAR),
  CREATION_DATE TIMESTAMP ,
  CREATION_USER VARCHAR2(255 CHAR),
  PRODUCTION_VALIDATION_DATE_TZ VARCHAR2(50 CHAR),
  PRODUCTION_VALIDATION_DATE TIMESTAMP ,
  PRODUCTION_VALIDATION_USER VARCHAR2(255 CHAR),
  DIFFUSION_VALIDATION_DATE_TZ VARCHAR2(50 CHAR),
  DIFFUSION_VALIDATION_DATE TIMESTAMP ,
  DIFFUSION_VALIDATION_USER VARCHAR2(255 CHAR),
  REJECT_VALIDATION_DATE_TZ VARCHAR2(50 CHAR),
  REJECT_VALIDATION_DATE TIMESTAMP ,
  REJECT_VALIDATION_USER VARCHAR2(255 CHAR),
  PUBLICATION_DATE_TZ VARCHAR2(50 CHAR),
  PUBLICATION_DATE TIMESTAMP ,
  PUBLICATION_USER VARCHAR2(255 CHAR),
  LAST_VERSION NUMBER(1,0),
  REPLACES_VERSION_FK NUMBER(19),
  IS_REPLACED_BY_VERSION_FK NUMBER(19),
  MAINTAINER_FK NUMBER(19),
  PROC_STATUS VARCHAR2(255 CHAR),
  PUBLICATION_STREAM_STATUS VARCHAR2(255 CHAR),
  USER_MODIFIED_KEYWORDS NUMBER(1,0),
  RESOURCE_CREATED_DATE_TZ VARCHAR2(50 CHAR),
  RESOURCE_CREATED_DATE TIMESTAMP ,
  LAST_UPDATE_TZ VARCHAR2(50 CHAR),
  LAST_UPDATE TIMESTAMP ,
  NEWNESS_UNTIL_DATE_TZ VARCHAR2(50 CHAR),
  NEWNESS_UNTIL_DATE TIMESTAMP ,
  COPYRIGHTED_DATE NUMBER(10),
  LANGUAGE_FK NUMBER(19),
  SUBTITLE_FK NUMBER(19),
  TITLE_ALTERNATIVE_FK NUMBER(19),
  ABSTRACT_FK NUMBER(19),
  KEYWORDS_FK NUMBER(19),
  COMMON_METADATA_FK NUMBER(19),
  CREATOR_FK NUMBER(19),
  CONFORMS_TO_FK NUMBER(19),
  CONFORMS_TO_INTERNAL_FK NUMBER(19),
  REPLACES_FK NUMBER(19),
  IS_REPLACED_BY_FK NUMBER(19),
  ACCESS_RIGHTS_FK NUMBER(19),
  TYPE VARCHAR2(255 CHAR)
);


CREATE TABLE TB_DATASETS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  IDENTIFIABLE_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_LIS_STAT_OFFICIALITY (
  ID NUMBER(19) NOT NULL,
  IDENTIFIER VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DESCRIPTION_FK NUMBER(19)
);


CREATE TABLE TB_DATASETS_VERSIONS (
  ID NUMBER(19) NOT NULL,
  DATE_START_TZ VARCHAR2(50 CHAR),
  DATE_START TIMESTAMP ,
  DATE_END_TZ VARCHAR2(50 CHAR),
  DATE_END TIMESTAMP ,
  DATASET_REPOSITORY_ID VARCHAR2(255 CHAR),
  KEEP_ALL_DATA NUMBER(1,0) NOT NULL,
  FORMAT_EXTENT_OBSERVATIONS NUMBER(19),
  FORMAT_EXTENT_DIMENSIONS NUMBER(10),
  DATE_NEXT_UPDATE_TZ VARCHAR2(50 CHAR),
  DATE_NEXT_UPDATE TIMESTAMP ,
  USER_MODIFIED_DATE_NEXT_U02 NUMBER(1,0),
  VERSION NUMBER(19) NOT NULL,
  DATASET_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL,
  RELATED_DSD_FK NUMBER(19),
  UPDATE_FREQUENCY_FK NUMBER(19),
  STAT_OFFICIALITY_FK NUMBER(19),
  BIBLIOGRAPHIC_CITATION_FK NUMBER(19)
);


CREATE TABLE TB_PUBLICATIONS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  IDENTIFIABLE_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_PUBLICATIONS_VERSIONS (
  ID NUMBER(19) NOT NULL,
  FORMAT_EXTENT_RESOURCES NUMBER(10),
  VERSION NUMBER(19) NOT NULL,
  PUBLICATION_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_QUERIES (
  ID NUMBER(19) NOT NULL,
  LATEST_DATA_NUMBER NUMBER(10),
  VERSION NUMBER(19) NOT NULL,
  IDENTIFIABLE_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_QUERIES_VERSIONS (
  ID NUMBER(19) NOT NULL,
  LATEST_DATA_NUMBER NUMBER(10),
  LATEST_TEMPORAL_CODE_CREATION VARCHAR2(255 CHAR),
  VERSION NUMBER(19) NOT NULL,
  QUERY_FK NUMBER(19) NOT NULL,
  LIFECYCLE_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19),
  DATASET_FK NUMBER(19),
  STATUS VARCHAR2(255 CHAR) NOT NULL,
  TYPE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_MULTIDATASETS (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  IDENTIFIABLE_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_MULTIDATASETS_VERSIONS (
  ID NUMBER(19) NOT NULL,
  FORMAT_EXTENT_RESOURCES NUMBER(10),
  VERSION NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL,
  MULTIDATASET_FK NUMBER(19) NOT NULL,
  FILTERING_DIMENSION_FK NUMBER(19)
);


CREATE TABLE TB_RELATED_RESOURCES (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19),
  DATASET_FK NUMBER(19),
  PUBLICATION_VERSION_FK NUMBER(19),
  PUBLICATION_FK NUMBER(19),
  QUERY_VERSION_FK NUMBER(19),
  QUERY_FK NUMBER(19),
  MULTIDATASET_VERSION_FK NUMBER(19),
  MULTIDATASET_FK NUMBER(19),
  TYPE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_VERSION_RATIONALE_TYPE (
  ID NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  VALUE VARCHAR2(255 CHAR) NOT NULL
);


CREATE TABLE TB_LOCALISED_STRINGS (
  ID NUMBER(19) NOT NULL,
  LABEL VARCHAR2(4000 CHAR) NOT NULL,
  LOCALE VARCHAR2(255 CHAR) NOT NULL,
  IS_UNMODIFIABLE NUMBER(1,0),
  VERSION NUMBER(19) NOT NULL,
  INTERNATIONAL_STRING_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_TRANSLATIONS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255 CHAR) NOT NULL,
  TITLE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_ATTRIBUTE_VALUES (
  ID NUMBER(19) NOT NULL,
  DSD_COMPONENT_ID VARCHAR2(255 CHAR) NOT NULL,
  IDENTIFIER VARCHAR2(255 CHAR) NOT NULL,
  TITLE VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_CATEGORISATIONS (
  ID NUMBER(19) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  CATEGORY_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL,
  VERSIONABLE_RESOURCE_FK NUMBER(19) NOT NULL,
  MAINTAINER_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_CATEGORISATION_SEQUENCES (
  ID NUMBER(19) NOT NULL,
  MAINTAINER_URN VARCHAR2(255 CHAR) NOT NULL,
  ACTUAL_SEQUENCE NUMBER(19) NOT NULL
);


CREATE TABLE TB_CODE_DIMENSIONS (
  ID NUMBER(19) NOT NULL,
  DSD_COMPONENT_ID VARCHAR2(255 CHAR) NOT NULL,
  IDENTIFIER VARCHAR2(255 CHAR) NOT NULL,
  TITLE VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DATASOURCES (
  ID NUMBER(19) NOT NULL,
  DATE_NEXT_UPDATE_TZ VARCHAR2(50 CHAR),
  DATE_NEXT_UPDATE TIMESTAMP ,
  FILENAME VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19)
);


CREATE TABLE TB_DIM_REPR_MAPPING (
  ID NUMBER(19) NOT NULL,
  DATASOURCE_FILENAME VARCHAR2(255 CHAR) NOT NULL,
  MAPPING VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  DATASET_FK NUMBER(19)
);


CREATE TABLE TB_TEMP_CODES (
  ID NUMBER(19) NOT NULL,
  IDENTIFIER VARCHAR2(255 CHAR) NOT NULL,
  TITLE VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL
);


CREATE TABLE TB_MD_CUBES (
  ID NUMBER(19) NOT NULL,
  IDENTIFIER VARCHAR2(255 CHAR) NOT NULL,
  ORDER_IN_MULTIDATASET NUMBER(19) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_FK NUMBER(19),
  QUERY_FK NUMBER(19),
  MULTIDATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_CUBES (
  ID NUMBER(19) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL,
  DATASET_FK NUMBER(19),
  QUERY_FK NUMBER(19),
  MULTIDATASET_FK NUMBER(19)
);


CREATE TABLE TB_ELEMENTS_LEVELS (
  ID NUMBER(19) NOT NULL,
  ORDER_IN_LEVEL NUMBER(19) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  PARENT_FK NUMBER(19),
  CHAPTER_FK NUMBER(19),
  TABLE_FK NUMBER(19),
  PUBLICATION_VERSION_ALL_FK NUMBER(19) NOT NULL,
  PUBLICATION_VERSION_FIRST_FK NUMBER(19)
);


CREATE TABLE TB_CHAPTERS (
  ID NUMBER(19) NOT NULL,
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  STATISTICAL_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_QUERY_SELECTION_ITEMS (
  ID NUMBER(19) NOT NULL,
  DIMENSION VARCHAR2(255 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  QUERY_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_CODE_ITEMS (
  ID NUMBER(19) NOT NULL,
  CODE VARCHAR2(255 CHAR) NOT NULL,
  TITLE VARCHAR2(4000 CHAR) NOT NULL,
  VERSION NUMBER(19) NOT NULL,
  QUERY_SELECTION_ITEM_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_TASKS (
  ID NUMBER(19) NOT NULL,
  JOB VARCHAR2(4000 CHAR) NOT NULL,
  EXTENSION_POINT VARCHAR2(4000 CHAR),
  CREATED_DATE_TZ VARCHAR2(50 CHAR),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR2(50 CHAR),
  LAST_UPDATED_TZ VARCHAR2(50 CHAR),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR2(50 CHAR),
  VERSION NUMBER(19) NOT NULL,
  STATUS VARCHAR2(255 CHAR) NOT NULL
);



-- Create many to many relations
    
CREATE TABLE TB_DV_GEO_COVERAGE (
  GEO_CODE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_GEO_GRANULARITY (
  GEOGRAPHIC_GRANULARITY_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_MEASURE_COVERAGE (
  MEASURE_CODES_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_TEMP_COVERAGE (
  TEMP_CODE_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_DV_TEMP_GRANULARITY (
  TEMPORAL_GRANULARITY_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_CONTRIBUTORS (
  CONTRIBUTOR_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_LANGUAGES (
  LANGUAGE_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_MEDIATORS (
  MEDIATOR_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_PUBLISHERS (
  PUBLISHER_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_PUB_CONTRIBUTORS (
  PUBLISHER_CONT_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_STATISTICAL_UNIT (
  STATISTICAL_UNIT_FK NUMBER(19) NOT NULL,
  DATASET_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_EI_STAT_OPER_INSTANCES (
  STAT_OPERATION_INSTANCE_FK NUMBER(19) NOT NULL,
  SIEMAC_RESOURCE_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_RR_HAS_PART (
  HAS_PART_FK NUMBER(19) NOT NULL,
  PUB_VERSION_FK NUMBER(19) NOT NULL
);


CREATE TABLE TB_SR_VRATIONALE_TYPES (
  VERSION_RATIONALE_TYPE NUMBER(19) NOT NULL,
  TB_STAT_RESOURCES NUMBER(19) NOT NULL
);



-- Primary keys
    
ALTER TABLE TB_INTERNATIONAL_STRINGS ADD CONSTRAINT PK_TB_INTERNATIONAL_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT PK_TB_EXTERNAL_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT PK_TB_STAT_RESOURCES
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

ALTER TABLE TB_PUBLICATIONS ADD CONSTRAINT PK_TB_PUBLICATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT PK_TB_PUBLICATIONS_VERSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_QUERIES ADD CONSTRAINT PK_TB_QUERIES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT PK_TB_QUERIES_VERSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_MULTIDATASETS ADD CONSTRAINT PK_TB_MULTIDATASETS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT PK_TB_MULTIDATASETS_VERSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT PK_TB_RELATED_RESOURCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_VERSION_RATIONALE_TYPE ADD CONSTRAINT PK_TB_VERSION_RATIONALE_TYPE
	PRIMARY KEY (ID)
;

ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT PK_TB_LOCALISED_STRINGS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_TRANSLATIONS ADD CONSTRAINT PK_TB_TRANSLATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_ATTRIBUTE_VALUES ADD CONSTRAINT PK_TB_ATTRIBUTE_VALUES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT PK_TB_CATEGORISATIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CATEGORISATION_SEQUENCES ADD CONSTRAINT PK_TB_CATEGORISATION_SEQUENCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CODE_DIMENSIONS ADD CONSTRAINT PK_TB_CODE_DIMENSIONS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATASOURCES ADD CONSTRAINT PK_TB_DATASOURCES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DIM_REPR_MAPPING ADD CONSTRAINT PK_TB_DIM_REPR_MAPPING
	PRIMARY KEY (ID)
;

ALTER TABLE TB_TEMP_CODES ADD CONSTRAINT PK_TB_TEMP_CODES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_MD_CUBES ADD CONSTRAINT PK_TB_MD_CUBES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CUBES ADD CONSTRAINT PK_TB_CUBES
	PRIMARY KEY (ID)
;

ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT PK_TB_ELEMENTS_LEVELS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CHAPTERS ADD CONSTRAINT PK_TB_CHAPTERS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT PK_TB_QUERY_SELECTION_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT PK_TB_CODE_ITEMS
	PRIMARY KEY (ID)
;

ALTER TABLE TB_TASKS ADD CONSTRAINT PK_TB_TASKS
	PRIMARY KEY (ID)
;

    
ALTER TABLE TB_DV_GEO_COVERAGE ADD CONSTRAINT PK_TB_DV_GEO_COVERAGE
	PRIMARY KEY (GEO_CODE_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT PK_TB_DV_GEO_GRANULARITY
	PRIMARY KEY (GEOGRAPHIC_GRANULARITY_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_DV_MEASURE_COVERAGE ADD CONSTRAINT PK_TB_DV_MEASURE_COVERAGE
	PRIMARY KEY (MEASURE_CODES_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_DV_TEMP_COVERAGE ADD CONSTRAINT PK_TB_DV_TEMP_COVERAGE
	PRIMARY KEY (TEMP_CODE_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT PK_TB_DV_TEMP_GRANULARITY
	PRIMARY KEY (TEMPORAL_GRANULARITY_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_EI_CONTRIBUTORS ADD CONSTRAINT PK_TB_EI_CONTRIBUTORS
	PRIMARY KEY (CONTRIBUTOR_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_EI_LANGUAGES ADD CONSTRAINT PK_TB_EI_LANGUAGES
	PRIMARY KEY (LANGUAGE_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_EI_MEDIATORS ADD CONSTRAINT PK_TB_EI_MEDIATORS
	PRIMARY KEY (MEDIATOR_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_EI_PUBLISHERS ADD CONSTRAINT PK_TB_EI_PUBLISHERS
	PRIMARY KEY (PUBLISHER_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_EI_PUB_CONTRIBUTORS ADD CONSTRAINT PK_TB_EI_PUB_CONTRIBUTORS
	PRIMARY KEY (PUBLISHER_CONT_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_EI_STATISTICAL_UNIT ADD CONSTRAINT PK_TB_EI_STATISTICAL_UNIT
	PRIMARY KEY (STATISTICAL_UNIT_FK, DATASET_VERSION_FK)
;

ALTER TABLE TB_EI_STAT_OPER_INSTANCES ADD CONSTRAINT PK_TB_EI_STAT_OPER_INSTANCES
	PRIMARY KEY (STAT_OPERATION_INSTANCE_FK, SIEMAC_RESOURCE_FK)
;

ALTER TABLE TB_RR_HAS_PART ADD CONSTRAINT PK_TB_RR_HAS_PART
	PRIMARY KEY (HAS_PART_FK, PUB_VERSION_FK)
;

ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT PK_TB_SR_VRATIONALE_TYPES
	PRIMARY KEY (VERSION_RATIONALE_TYPE, TB_STAT_RESOURCES)
;


-- Unique constraints
    






























ALTER TABLE TB_TASKS
	ADD CONSTRAINT UQ_TB_TASKS UNIQUE (JOB)
;



-- Foreign key constraints
    

  
  
ALTER TABLE TB_EXTERNAL_ITEMS ADD CONSTRAINT FK_TB_EXTERNAL_ITEMS_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_STAT_OP64
	FOREIGN KEY (STAT_OPERATION_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASETS ADD CONSTRAINT FK_TB_DATASETS_IDENTIFIABLE_08
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_LIS_STAT_OFFICIALITY ADD CONSTRAINT FK_TB_LIS_STAT_OFFICIALITY_D51
	FOREIGN KEY (DESCRIPTION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_DATA05
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_SIEM72
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_RELA64
	FOREIGN KEY (RELATED_DSD_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_UPDA81
	FOREIGN KEY (UPDATE_FREQUENCY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_STAT99
	FOREIGN KEY (STAT_OFFICIALITY_FK) REFERENCES TB_LIS_STAT_OFFICIALITY (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_BIBL82
	FOREIGN KEY (BIBLIOGRAPHIC_CITATION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_PUBLICATIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_IDENTIFIA64
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_99
	FOREIGN KEY (PUBLICATION_FK) REFERENCES TB_PUBLICATIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_40
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERIES ADD CONSTRAINT FK_TB_QUERIES_IDENTIFIABLE_R77
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_QUERY34
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_LIFEC07
	FOREIGN KEY (LIFECYCLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_DATAS75
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_DATAS62
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_MULTIDATASETS ADD CONSTRAINT FK_TB_MULTIDATASETS_IDENTIFI39
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS17
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS57
	FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS11
	FOREIGN KEY (FILTERING_DIMENSION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_DATA74
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_DATA41
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_PUBL58
	FOREIGN KEY (PUBLICATION_VERSION_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_PUBL17
	FOREIGN KEY (PUBLICATION_FK) REFERENCES TB_PUBLICATIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_QUER82
	FOREIGN KEY (QUERY_VERSION_FK) REFERENCES TB_QUERIES_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_QUER39
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_MULT53
	FOREIGN KEY (MULTIDATASET_VERSION_FK) REFERENCES TB_MULTIDATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_MULT58
	FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_INTE13
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_TRANSLATIONS ADD CONSTRAINT FK_TB_TRANSLATIONS_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_ATTRIBUTE_VALUES ADD CONSTRAINT FK_TB_ATTRIBUTE_VALUES_DATAS40
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_CATEGO93
	FOREIGN KEY (CATEGORY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_DATASE72
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_VERSIO68
	FOREIGN KEY (VERSIONABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_MAINTA79
	FOREIGN KEY (MAINTAINER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_CODE_DIMENSIONS ADD CONSTRAINT FK_TB_CODE_DIMENSIONS_DATASE68
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_STATISTICA52
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_DATASET_VE01
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DIM_REPR_MAPPING ADD CONSTRAINT FK_TB_DIM_REPR_MAPPING_DATAS07
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_STATISTICAL_R70
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_MULTIDATASET_28
	FOREIGN KEY (MULTIDATASET_VERSION_FK) REFERENCES TB_MULTIDATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_STATISTICAL_RESO24
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_MULTIDATASET_FK
	FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PARENT65
	FOREIGN KEY (PARENT_FK) REFERENCES TB_ELEMENTS_LEVELS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_CHAPTE84
	FOREIGN KEY (CHAPTER_FK) REFERENCES TB_CHAPTERS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_TABLE_FK
	FOREIGN KEY (TABLE_FK) REFERENCES TB_CUBES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLIC70
	FOREIGN KEY (PUBLICATION_VERSION_ALL_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLIC29
	FOREIGN KEY (PUBLICATION_VERSION_FIRST_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CHAPTERS ADD CONSTRAINT FK_TB_CHAPTERS_STATISTICAL_R98
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT FK_TB_QUERY_SELECTION_ITEMS_18
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT FK_TB_CODE_ITEMS_QUERY_SELEC09
	FOREIGN KEY (QUERY_SELECTION_ITEM_FK) REFERENCES TB_QUERY_SELECTION_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  

ALTER TABLE TB_DV_GEO_COVERAGE ADD CONSTRAINT FK_TB_DV_GEO_COVERAGE_GEO_CO92
	FOREIGN KEY (GEO_CODE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_GEO_COVERAGE ADD CONSTRAINT FK_TB_DV_GEO_COVERAGE_DATASE28
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_GEO99
	FOREIGN KEY (GEOGRAPHIC_GRANULARITY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_DAT86
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_MEASURE_COVERAGE ADD CONSTRAINT FK_TB_DV_MEASURE_COVERAGE_ME67
	FOREIGN KEY (MEASURE_CODES_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_MEASURE_COVERAGE ADD CONSTRAINT FK_TB_DV_MEASURE_COVERAGE_DA79
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_TEMP_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMP_COVERAGE_TEMP_00
	FOREIGN KEY (TEMP_CODE_FK) REFERENCES TB_TEMP_CODES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_TEMP_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMP_COVERAGE_DATAS43
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_TE61
	FOREIGN KEY (TEMPORAL_GRANULARITY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_DA69
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_CONTRIBUTORS_CONTRI90
	FOREIGN KEY (CONTRIBUTOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_CONTRIBUTORS_SIEMAC12
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_LANGUAGES ADD CONSTRAINT FK_TB_EI_LANGUAGES_LANGUAGE_FK
	FOREIGN KEY (LANGUAGE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_LANGUAGES ADD CONSTRAINT FK_TB_EI_LANGUAGES_SIEMAC_RE57
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_MEDIATORS ADD CONSTRAINT FK_TB_EI_MEDIATORS_MEDIATOR_FK
	FOREIGN KEY (MEDIATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_MEDIATORS ADD CONSTRAINT FK_TB_EI_MEDIATORS_SIEMAC_RE08
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_PUBLISHERS ADD CONSTRAINT FK_TB_EI_PUBLISHERS_PUBLISHE66
	FOREIGN KEY (PUBLISHER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_PUBLISHERS ADD CONSTRAINT FK_TB_EI_PUBLISHERS_SIEMAC_R33
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_PUB_CONTRIBUTORS_PU76
	FOREIGN KEY (PUBLISHER_CONT_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_PUB_CONTRIBUTORS_SI82
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_EI_STATISTICAL_UNIT_ST77
	FOREIGN KEY (STATISTICAL_UNIT_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_EI_STATISTICAL_UNIT_DA92
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_STAT_OPER_INSTANCES ADD CONSTRAINT FK_TB_EI_STAT_OPER_INSTANCES85
	FOREIGN KEY (STAT_OPERATION_INSTANCE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_STAT_OPER_INSTANCES ADD CONSTRAINT FK_TB_EI_STAT_OPER_INSTANCES08
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_RR_HAS_PART ADD CONSTRAINT FK_TB_RR_HAS_PART_HAS_PART_FK
	FOREIGN KEY (HAS_PART_FK) REFERENCES TB_RELATED_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RR_HAS_PART ADD CONSTRAINT FK_TB_RR_HAS_PART_PUB_VERSIO01
	FOREIGN KEY (PUB_VERSION_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_VE86
	FOREIGN KEY (VERSION_RATIONALE_TYPE) REFERENCES TB_VERSION_RATIONALE_TYPE (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_TB30
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  

  
  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_DESCRIP80
	FOREIGN KEY (DESCRIPTION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_VERSION32
	FOREIGN KEY (VERSION_RATIONALE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_LANGUAG04
	FOREIGN KEY (LANGUAGE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_SUBTITL88
	FOREIGN KEY (SUBTITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_A74
	FOREIGN KEY (TITLE_ALTERNATIVE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ABSTRAC54
	FOREIGN KEY (ABSTRACT_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_KEYWORD02
	FOREIGN KEY (KEYWORDS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_COMMON_43
	FOREIGN KEY (COMMON_METADATA_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CREATOR96
	FOREIGN KEY (CREATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORM15
	FOREIGN KEY (CONFORMS_TO_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORM59
	FOREIGN KEY (CONFORMS_TO_INTERNAL_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACE61
	FOREIGN KEY (REPLACES_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL23
	FOREIGN KEY (IS_REPLACED_BY_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ACCESS_34
	FOREIGN KEY (ACCESS_RIGHTS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACE24
	FOREIGN KEY (REPLACES_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL46
	FOREIGN KEY (IS_REPLACED_BY_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_MAINTAI00
	FOREIGN KEY (MAINTAINER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;

  


-- Index
CREATE INDEX IX_TB_STAT_RESOURCES_STAT_RE84
    ON TB_STAT_RESOURCES (STAT_RESOURCE_TYPE ASC)
;


	