

-- ###########################################
-- # Create
-- ###########################################
-- Create pk sequence
    
 	
CREATE sequence SEQ_I18NSTRS;
 	
CREATE sequence SEQ_EXTERNAL_ITEMS;
 	
CREATE sequence SEQ_STAT_RESOURCES;
 	
CREATE sequence SEQ_STAT_OFFICIALITY;
 	
CREATE sequence SEQ_DATASETS;
 	
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
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL
);


CREATE TABLE TB_EXTERNAL_ITEMS (
  ID BIGINT NOT NULL,
  CODE VARCHAR(255) NOT NULL,
  CODE_NESTED VARCHAR(255),
  URI VARCHAR(4000) NOT NULL,
  URN VARCHAR(4000),
  URN_PROVIDER VARCHAR(4000),
  MANAGEMENT_APP_URL VARCHAR(4000),
  VERSION BIGINT NOT NULL,
  TITLE_FK BIGINT,
  TYPE VARCHAR(255) NOT NULL
);


CREATE TABLE TB_STAT_RESOURCES (
  ID BIGINT NOT NULL,
  UPDATE_DATE_TZ VARCHAR(50),
  UPDATE_DATE TIMESTAMP,
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  STAT_OPERATION_FK BIGINT,
  STAT_RESOURCE_TYPE VARCHAR(255) NOT NULL,
  CODE VARCHAR(255),
  URN VARCHAR(255),
  TITLE_FK BIGINT,
  DESCRIPTION_FK BIGINT,
  VERSION_LOGIC VARCHAR(255),
  NEXT_VERSION_DATE_TZ VARCHAR(50),
  NEXT_VERSION_DATE TIMESTAMP,
  VALID_FROM_TZ VARCHAR(50),
  VALID_FROM TIMESTAMP,
  VALID_TO_TZ VARCHAR(50),
  VALID_TO TIMESTAMP,
  VERSION_RATIONALE_FK BIGINT,
  NEXT_VERSION VARCHAR(255),
  CREATION_DATE_TZ VARCHAR(50),
  CREATION_DATE TIMESTAMP,
  CREATION_USER VARCHAR(255),
  PRODUCTION_VALIDATION_DATE_TZ VARCHAR(50),
  PRODUCTION_VALIDATION_DATE TIMESTAMP,
  PRODUCTION_VALIDATION_USER VARCHAR(255),
  DIFFUSION_VALIDATION_DATE_TZ VARCHAR(50),
  DIFFUSION_VALIDATION_DATE TIMESTAMP,
  DIFFUSION_VALIDATION_USER VARCHAR(255),
  REJECT_VALIDATION_DATE_TZ VARCHAR(50),
  REJECT_VALIDATION_DATE TIMESTAMP,
  REJECT_VALIDATION_USER VARCHAR(255),
  PUBLICATION_DATE_TZ VARCHAR(50),
  PUBLICATION_DATE TIMESTAMP,
  PUBLICATION_USER VARCHAR(255),
  LAST_VERSION BOOLEAN,
  REPLACES_VERSION_FK BIGINT,
  IS_REPLACED_BY_VERSION_FK BIGINT,
  MAINTAINER_FK BIGINT,
  PROC_STATUS VARCHAR(255),
  PUBLICATION_STREAM_STATUS VARCHAR(255),
  USER_MODIFIED_KEYWORDS BOOLEAN,
  RESOURCE_CREATED_DATE_TZ VARCHAR(50),
  RESOURCE_CREATED_DATE TIMESTAMP,
  LAST_UPDATE_TZ VARCHAR(50),
  LAST_UPDATE TIMESTAMP,
  NEWNESS_UNTIL_DATE_TZ VARCHAR(50),
  NEWNESS_UNTIL_DATE TIMESTAMP,
  COPYRIGHTED_DATE INTEGER,
  LANGUAGE_FK BIGINT,
  SUBTITLE_FK BIGINT,
  TITLE_ALTERNATIVE_FK BIGINT,
  ABSTRACT_FK BIGINT,
  KEYWORDS_FK BIGINT,
  COMMON_METADATA_FK BIGINT,
  CREATOR_FK BIGINT,
  CONFORMS_TO_FK BIGINT,
  CONFORMS_TO_INTERNAL_FK BIGINT,
  REPLACES_FK BIGINT,
  IS_REPLACED_BY_FK BIGINT,
  ACCESS_RIGHTS_FK BIGINT,
  TYPE VARCHAR(255)
);


CREATE TABLE TB_LIS_STAT_OFFICIALITY (
  ID BIGINT NOT NULL,
  IDENTIFIER VARCHAR(255) NOT NULL,
  VERSION BIGINT NOT NULL,
  DESCRIPTION_FK BIGINT
);


CREATE TABLE TB_DATASETS (
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  IDENTIFIABLE_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_DATASETS_VERSIONS (
  ID BIGINT NOT NULL,
  DATE_LAST_TIME_DATA_IMPORT_TZ VARCHAR(50),
  DATE_LAST_TIME_DATA_IMPORT TIMESTAMP,
  DATE_START_TZ VARCHAR(50),
  DATE_START TIMESTAMP,
  DATE_END_TZ VARCHAR(50),
  DATE_END TIMESTAMP,
  DATASET_REPOSITORY_ID VARCHAR(255),
  KEEP_ALL_DATA BOOLEAN NOT NULL,
  FORMAT_EXTENT_OBSERVATIONS BIGINT,
  FORMAT_EXTENT_DIMENSIONS INTEGER,
  DATE_NEXT_UPDATE_TZ VARCHAR(50),
  DATE_NEXT_UPDATE TIMESTAMP,
  USER_MODIFIED_DATE_NEXT_U02 BOOLEAN,
  VERSION BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL,
  RELATED_DSD_FK BIGINT,
  UPDATE_FREQUENCY_FK BIGINT,
  STAT_OFFICIALITY_FK BIGINT,
  BIBLIOGRAPHIC_CITATION_FK BIGINT,
  DATASET_FK BIGINT NOT NULL,
  DATA_SOURCE_TYPE VARCHAR(255) NOT NULL
);


CREATE TABLE TB_PUBLICATIONS (
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  IDENTIFIABLE_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_PUBLICATIONS_VERSIONS (
  ID BIGINT NOT NULL,
  FORMAT_EXTENT_RESOURCES INTEGER,
  VERSION BIGINT NOT NULL,
  PUBLICATION_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_QUERIES (
  ID BIGINT NOT NULL,
  LATEST_DATA_NUMBER INTEGER,
  VERSION BIGINT NOT NULL,
  IDENTIFIABLE_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_QUERIES_VERSIONS (
  ID BIGINT NOT NULL,
  LATEST_DATA_NUMBER INTEGER,
  VERSION BIGINT NOT NULL,
  QUERY_FK BIGINT NOT NULL,
  LIFECYCLE_RESOURCE_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT,
  DATASET_FK BIGINT,
  STATUS VARCHAR(255) NOT NULL,
  TYPE VARCHAR(255) NOT NULL
);


CREATE TABLE TB_MULTIDATASETS (
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  IDENTIFIABLE_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_MULTIDATASETS_VERSIONS (
  ID BIGINT NOT NULL,
  FORMAT_EXTENT_RESOURCES INTEGER,
  VERSION BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL,
  MULTIDATASET_FK BIGINT NOT NULL,
  FILTERING_DIMENSION_FK BIGINT
);


CREATE TABLE TB_RELATED_RESOURCES (
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT,
  DATASET_FK BIGINT,
  PUBLICATION_VERSION_FK BIGINT,
  PUBLICATION_FK BIGINT,
  QUERY_VERSION_FK BIGINT,
  QUERY_FK BIGINT,
  MULTIDATASET_VERSION_FK BIGINT,
  MULTIDATASET_FK BIGINT,
  TYPE VARCHAR(255) NOT NULL
);


CREATE TABLE TB_VERSION_RATIONALE_TYPE (
  ID BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  VALUE VARCHAR(255) NOT NULL
);


CREATE TABLE TB_LOCALISED_STRINGS (
  ID BIGINT NOT NULL,
  LABEL VARCHAR(4000) NOT NULL,
  LOCALE VARCHAR(255) NOT NULL,
  IS_UNMODIFIABLE BOOLEAN,
  VERSION BIGINT NOT NULL,
  INTERNATIONAL_STRING_FK BIGINT NOT NULL
);


CREATE TABLE TB_TRANSLATIONS (
  ID BIGINT NOT NULL,
  CODE VARCHAR(255) NOT NULL,
  TITLE_FK BIGINT NOT NULL
);


CREATE TABLE TB_ATTRIBUTE_VALUES (
  ID BIGINT NOT NULL,
  DSD_COMPONENT_ID VARCHAR(255) NOT NULL,
  IDENTIFIER VARCHAR(255) NOT NULL,
  TITLE VARCHAR(4000) NOT NULL,
  VERSION BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_CATEGORISATIONS (
  ID BIGINT NOT NULL,
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  CATEGORY_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL,
  VERSIONABLE_RESOURCE_FK BIGINT NOT NULL,
  MAINTAINER_FK BIGINT NOT NULL
);


CREATE TABLE TB_CATEGORISATION_SEQUENCES (
  ID BIGINT NOT NULL,
  MAINTAINER_URN VARCHAR(255) NOT NULL,
  ACTUAL_SEQUENCE BIGINT NOT NULL
);


CREATE TABLE TB_CODE_DIMENSIONS (
  ID BIGINT NOT NULL,
  DSD_COMPONENT_ID VARCHAR(255) NOT NULL,
  IDENTIFIER VARCHAR(255) NOT NULL,
  TITLE VARCHAR(4000) NOT NULL,
  VERSION BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_DATASOURCES (
  ID BIGINT NOT NULL,
  DATE_NEXT_UPDATE_TZ VARCHAR(50),
  DATE_NEXT_UPDATE TIMESTAMP,
  SOURCE_NAME VARCHAR(255) NOT NULL,
  VERSION BIGINT NOT NULL,
  STATISTICAL_RESOURCE_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT
);


CREATE TABLE TB_DIM_REPR_MAPPING (
  ID BIGINT NOT NULL,
  DATASOURCE_FILENAME VARCHAR(255) NOT NULL,
  MAPPING VARCHAR(4000) NOT NULL,
  VERSION BIGINT NOT NULL,
  DATASET_FK BIGINT
);


CREATE TABLE TB_TEMP_CODES (
  ID BIGINT NOT NULL,
  IDENTIFIER VARCHAR(255) NOT NULL,
  TITLE VARCHAR(4000) NOT NULL,
  VERSION BIGINT NOT NULL
);


CREATE TABLE TB_MD_CUBES (
  ID BIGINT NOT NULL,
  IDENTIFIER VARCHAR(255) NOT NULL,
  ORDER_IN_MULTIDATASET BIGINT NOT NULL,
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  STATISTICAL_RESOURCE_FK BIGINT NOT NULL,
  DATASET_FK BIGINT,
  QUERY_FK BIGINT,
  MULTIDATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_CUBES (
  ID BIGINT NOT NULL,
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  STATISTICAL_RESOURCE_FK BIGINT NOT NULL,
  DATASET_FK BIGINT,
  QUERY_FK BIGINT,
  MULTIDATASET_FK BIGINT
);


CREATE TABLE TB_ELEMENTS_LEVELS (
  ID BIGINT NOT NULL,
  ORDER_IN_LEVEL BIGINT NOT NULL,
  VERSION BIGINT NOT NULL,
  PARENT_FK BIGINT,
  CHAPTER_FK BIGINT,
  TABLE_FK BIGINT,
  PUBLICATION_VERSION_ALL_FK BIGINT NOT NULL,
  PUBLICATION_VERSION_FIRST_FK BIGINT
);


CREATE TABLE TB_CHAPTERS (
  ID BIGINT NOT NULL,
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  STATISTICAL_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_QUERY_SELECTION_ITEMS (
  ID BIGINT NOT NULL,
  DIMENSION VARCHAR(255) NOT NULL,
  VERSION BIGINT NOT NULL,
  QUERY_FK BIGINT NOT NULL
);


CREATE TABLE TB_CODE_ITEMS (
  ID BIGINT NOT NULL,
  CODE VARCHAR(255) NOT NULL,
  TITLE VARCHAR(4000) NOT NULL,
  VERSION BIGINT NOT NULL,
  QUERY_SELECTION_ITEM_FK BIGINT NOT NULL
);


CREATE TABLE TB_TASKS (
  ID BIGINT NOT NULL,
  JOB VARCHAR(4000) NOT NULL,
  EXTENSION_POINT VARCHAR(4000),
  CREATED_DATE_TZ VARCHAR(50),
  CREATED_DATE TIMESTAMP,
  CREATED_BY VARCHAR(50),
  LAST_UPDATED_TZ VARCHAR(50),
  LAST_UPDATED TIMESTAMP,
  LAST_UPDATED_BY VARCHAR(50),
  VERSION BIGINT NOT NULL,
  STATUS VARCHAR(255) NOT NULL
);



-- Create many to many relations
    
CREATE TABLE TB_DV_GEO_COVERAGE (
  GEO_CODE_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_DV_GEO_GRANULARITY (
  GEOGRAPHIC_GRANULARITY_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_DV_MEASURE_COVERAGE (
  MEASURE_CODES_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_DV_TEMP_COVERAGE (
  TEMP_CODE_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_DV_TEMP_GRANULARITY (
  TEMPORAL_GRANULARITY_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_CONTRIBUTORS (
  CONTRIBUTOR_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_LANGUAGES (
  LANGUAGE_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_MEDIATORS (
  MEDIATOR_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_PUBLISHERS (
  PUBLISHER_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_PUB_CONTRIBUTORS (
  PUBLISHER_CONT_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_STATISTICAL_UNIT (
  STATISTICAL_UNIT_FK BIGINT NOT NULL,
  DATASET_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_EI_STAT_OPER_INSTANCES (
  STAT_OPERATION_INSTANCE_FK BIGINT NOT NULL,
  SIEMAC_RESOURCE_FK BIGINT NOT NULL
);


CREATE TABLE TB_RR_HAS_PART (
  HAS_PART_FK BIGINT NOT NULL,
  PUB_VERSION_FK BIGINT NOT NULL
);


CREATE TABLE TB_SR_VRATIONALE_TYPES (
  VERSION_RATIONALE_TYPE BIGINT NOT NULL,
  TB_STAT_RESOURCES BIGINT NOT NULL
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

ALTER TABLE TB_LIS_STAT_OFFICIALITY ADD CONSTRAINT PK_TB_LIS_STAT_OFFICIALITY
	PRIMARY KEY (ID)
;

ALTER TABLE TB_DATASETS ADD CONSTRAINT PK_TB_DATASETS
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

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_STAT_OPERATION_FK
	FOREIGN KEY (STAT_OPERATION_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_LIS_STAT_OFFICIALITY ADD CONSTRAINT FK_TB_LIS_STAT_OFFICIALITY_DESCRIPTION_FK
	FOREIGN KEY (DESCRIPTION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASETS ADD CONSTRAINT FK_TB_DATASETS_IDENTIFIABLE_RESOURCE_FK
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_RELATED_DSD_FK
	FOREIGN KEY (RELATED_DSD_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_UPDATE_FREQUENCY_FK
	FOREIGN KEY (UPDATE_FREQUENCY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_STAT_OFFICIALITY_FK
	FOREIGN KEY (STAT_OFFICIALITY_FK) REFERENCES TB_LIS_STAT_OFFICIALITY (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_BIBLIOGRAPHIC_CITATION_FK
	FOREIGN KEY (BIBLIOGRAPHIC_CITATION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASETS_VERSIONS ADD CONSTRAINT FK_TB_DATASETS_VERSIONS_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_PUBLICATIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_IDENTIFIABLE_RESOURCE_FK
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_PUBLICATION_FK
	FOREIGN KEY (PUBLICATION_FK) REFERENCES TB_PUBLICATIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_PUBLICATIONS_VERSIONS ADD CONSTRAINT FK_TB_PUBLICATIONS_VERSIONS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERIES ADD CONSTRAINT FK_TB_QUERIES_IDENTIFIABLE_RESOURCE_FK
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_LIFECYCLE_RESOURCE_FK
	FOREIGN KEY (LIFECYCLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT FK_TB_QUERIES_VERSIONS_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_MULTIDATASETS ADD CONSTRAINT FK_TB_MULTIDATASETS_IDENTIFIABLE_RESOURCE_FK
	FOREIGN KEY (IDENTIFIABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS_MULTIDATASET_FK
	FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS_FILTERING_DIMENSION_FK
	FOREIGN KEY (FILTERING_DIMENSION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_PUBLICATION_VERSION_FK
	FOREIGN KEY (PUBLICATION_VERSION_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_PUBLICATION_FK
	FOREIGN KEY (PUBLICATION_FK) REFERENCES TB_PUBLICATIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_QUERY_VERSION_FK
	FOREIGN KEY (QUERY_VERSION_FK) REFERENCES TB_QUERIES_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_MULTIDATASET_VERSION_FK
	FOREIGN KEY (MULTIDATASET_VERSION_FK) REFERENCES TB_MULTIDATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RELATED_RESOURCES ADD CONSTRAINT FK_TB_RELATED_RESOURCES_MULTIDATASET_FK
	FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT FK_TB_LOCALISED_STRINGS_INTERNATIONAL_STRING_FK
	FOREIGN KEY (INTERNATIONAL_STRING_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_TRANSLATIONS ADD CONSTRAINT FK_TB_TRANSLATIONS_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_ATTRIBUTE_VALUES ADD CONSTRAINT FK_TB_ATTRIBUTE_VALUES_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_CATEGORY_FK
	FOREIGN KEY (CATEGORY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_VERSIONABLE_RESOURCE_FK
	FOREIGN KEY (VERSIONABLE_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_CATEGORISATIONS ADD CONSTRAINT FK_TB_CATEGORISATIONS_MAINTAINER_FK
	FOREIGN KEY (MAINTAINER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_CODE_DIMENSIONS ADD CONSTRAINT FK_TB_CODE_DIMENSIONS_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_STATISTICAL_RESOURCE_FK
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DATASOURCES ADD CONSTRAINT FK_TB_DATASOURCES_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DIM_REPR_MAPPING ADD CONSTRAINT FK_TB_DIM_REPR_MAPPING_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_STATISTICAL_RESOURCE_FK
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_DATASET_FK
	FOREIGN KEY (DATASET_FK) REFERENCES TB_DATASETS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_MD_CUBES ADD CONSTRAINT FK_TB_MD_CUBES_MULTIDATASET_VERSION_FK
	FOREIGN KEY (MULTIDATASET_VERSION_FK) REFERENCES TB_MULTIDATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_STATISTICAL_RESOURCE_FK
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

  
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PARENT_FK
	FOREIGN KEY (PARENT_FK) REFERENCES TB_ELEMENTS_LEVELS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_CHAPTER_FK
	FOREIGN KEY (CHAPTER_FK) REFERENCES TB_CHAPTERS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_TABLE_FK
	FOREIGN KEY (TABLE_FK) REFERENCES TB_CUBES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLICATION_VERSION_ALL_FK
	FOREIGN KEY (PUBLICATION_VERSION_ALL_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_ELEMENTS_LEVELS ADD CONSTRAINT FK_TB_ELEMENTS_LEVELS_PUBLICATION_VERSION_FIRST_FK
	FOREIGN KEY (PUBLICATION_VERSION_FIRST_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CHAPTERS ADD CONSTRAINT FK_TB_CHAPTERS_STATISTICAL_RESOURCE_FK
	FOREIGN KEY (STATISTICAL_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT FK_TB_QUERY_SELECTION_ITEMS_QUERY_FK
	FOREIGN KEY (QUERY_FK) REFERENCES TB_QUERIES_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT FK_TB_CODE_ITEMS_QUERY_SELECTION_ITEM_FK
	FOREIGN KEY (QUERY_SELECTION_ITEM_FK) REFERENCES TB_QUERY_SELECTION_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;

  
  
  

ALTER TABLE TB_DV_GEO_COVERAGE ADD CONSTRAINT FK_TB_DV_GEO_COVERAGE_GEO_CODE_FK
	FOREIGN KEY (GEO_CODE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_GEO_COVERAGE ADD CONSTRAINT FK_TB_DV_GEO_COVERAGE_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_GEOGRAPHIC_GRANULARITY_FK
	FOREIGN KEY (GEOGRAPHIC_GRANULARITY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_GEO_GRANULARITY ADD CONSTRAINT FK_TB_DV_GEO_GRANULARITY_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_MEASURE_COVERAGE ADD CONSTRAINT FK_TB_DV_MEASURE_COVERAGE_MEASURE_CODES_FK
	FOREIGN KEY (MEASURE_CODES_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_MEASURE_COVERAGE ADD CONSTRAINT FK_TB_DV_MEASURE_COVERAGE_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_TEMP_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMP_COVERAGE_TEMP_CODE_FK
	FOREIGN KEY (TEMP_CODE_FK) REFERENCES TB_TEMP_CODES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_TEMP_COVERAGE ADD CONSTRAINT FK_TB_DV_TEMP_COVERAGE_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_TEMPORAL_GRANULARITY_FK
	FOREIGN KEY (TEMPORAL_GRANULARITY_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_DV_TEMP_GRANULARITY ADD CONSTRAINT FK_TB_DV_TEMP_GRANULARITY_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_CONTRIBUTORS_CONTRIBUTOR_FK
	FOREIGN KEY (CONTRIBUTOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_CONTRIBUTORS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_LANGUAGES ADD CONSTRAINT FK_TB_EI_LANGUAGES_LANGUAGE_FK
	FOREIGN KEY (LANGUAGE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_LANGUAGES ADD CONSTRAINT FK_TB_EI_LANGUAGES_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_MEDIATORS ADD CONSTRAINT FK_TB_EI_MEDIATORS_MEDIATOR_FK
	FOREIGN KEY (MEDIATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_MEDIATORS ADD CONSTRAINT FK_TB_EI_MEDIATORS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_PUBLISHERS ADD CONSTRAINT FK_TB_EI_PUBLISHERS_PUBLISHER_FK
	FOREIGN KEY (PUBLISHER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_PUBLISHERS ADD CONSTRAINT FK_TB_EI_PUBLISHERS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_PUB_CONTRIBUTORS_PUBLISHER_CONT_FK
	FOREIGN KEY (PUBLISHER_CONT_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_PUB_CONTRIBUTORS ADD CONSTRAINT FK_TB_EI_PUB_CONTRIBUTORS_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_EI_STATISTICAL_UNIT_STATISTICAL_UNIT_FK
	FOREIGN KEY (STATISTICAL_UNIT_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_STATISTICAL_UNIT ADD CONSTRAINT FK_TB_EI_STATISTICAL_UNIT_DATASET_VERSION_FK
	FOREIGN KEY (DATASET_VERSION_FK) REFERENCES TB_DATASETS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_EI_STAT_OPER_INSTANCES ADD CONSTRAINT FK_TB_EI_STAT_OPER_INSTANCES_STAT_OPERATION_INSTANCE_FK
	FOREIGN KEY (STAT_OPERATION_INSTANCE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_EI_STAT_OPER_INSTANCES ADD CONSTRAINT FK_TB_EI_STAT_OPER_INSTANCES_SIEMAC_RESOURCE_FK
	FOREIGN KEY (SIEMAC_RESOURCE_FK) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_RR_HAS_PART ADD CONSTRAINT FK_TB_RR_HAS_PART_HAS_PART_FK
	FOREIGN KEY (HAS_PART_FK) REFERENCES TB_RELATED_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_RR_HAS_PART ADD CONSTRAINT FK_TB_RR_HAS_PART_PUB_VERSION_FK
	FOREIGN KEY (PUB_VERSION_FK) REFERENCES TB_PUBLICATIONS_VERSIONS (ID) DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_VERSION_RATIONALE_TYPE
	FOREIGN KEY (VERSION_RATIONALE_TYPE) REFERENCES TB_VERSION_RATIONALE_TYPE (ID) DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_SR_VRATIONALE_TYPES ADD CONSTRAINT FK_TB_SR_VRATIONALE_TYPES_TB_STAT_RESOURCES
	FOREIGN KEY (TB_STAT_RESOURCES) REFERENCES TB_STAT_RESOURCES (ID) DEFERRABLE initially IMMEDIATE
;

  

  
  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_FK
	FOREIGN KEY (TITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_DESCRIPTION_FK
	FOREIGN KEY (DESCRIPTION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_VERSION_RATIONALE_FK
	FOREIGN KEY (VERSION_RATIONALE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_LANGUAGE_FK
	FOREIGN KEY (LANGUAGE_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_SUBTITLE_FK
	FOREIGN KEY (SUBTITLE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_TITLE_ALTERNATIVE_FK
	FOREIGN KEY (TITLE_ALTERNATIVE_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ABSTRACT_FK
	FOREIGN KEY (ABSTRACT_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_KEYWORDS_FK
	FOREIGN KEY (KEYWORDS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_COMMON_METADATA_FK
	FOREIGN KEY (COMMON_METADATA_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CREATOR_FK
	FOREIGN KEY (CREATOR_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORMS_TO_FK
	FOREIGN KEY (CONFORMS_TO_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_CONFORMS_TO_INTERNAL_FK
	FOREIGN KEY (CONFORMS_TO_INTERNAL_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACES_FK
	FOREIGN KEY (REPLACES_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPLACED_BY_FK
	FOREIGN KEY (IS_REPLACED_BY_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_ACCESS_RIGHTS_FK
	FOREIGN KEY (ACCESS_RIGHTS_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID)  DEFERRABLE initially IMMEDIATE
;

  
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_REPLACES_VERSION_FK
	FOREIGN KEY (REPLACES_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPLACED_BY_VERSION_FK
	FOREIGN KEY (IS_REPLACED_BY_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_MAINTAINER_FK
	FOREIGN KEY (MAINTAINER_FK) REFERENCES TB_EXTERNAL_ITEMS (ID)  DEFERRABLE initially IMMEDIATE
;

  


-- Index
CREATE INDEX IX_TB_STAT_RESOURCES_STAT_RESOURCE_TYPE
    ON TB_STAT_RESOURCES (STAT_RESOURCE_TYPE ASC)
;


	