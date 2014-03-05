
    
-- ###########################################
-- # Drop
-- ###########################################
-- Drop index
DROP INDEX IX_TB_STAT_RESOURCES_STAT_RE84;


-- Drop many to many relations
    
DROP TABLE TB_SR_VRATIONALE_TYPES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_RR_HAS_PART CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_STAT_OPER_INSTANCES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_STATISTICAL_UNIT CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_PUB_CONTRIBUTORS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_PUBLISHERS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_MEDIATORS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_LANGUAGES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EI_CONTRIBUTORS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DV_TEMP_GRANULARITY CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DV_TEMP_COVERAGE CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DV_MEASURE_COVERAGE CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DV_GEO_GRANULARITY CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DV_GEO_COVERAGE CASCADE CONSTRAINTS PURGE;

-- Drop normal entities
    
DROP TABLE TB_TASKS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CODE_ITEMS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_QUERY_SELECTION_ITEMS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CHAPTERS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_ELEMENTS_LEVELS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CUBES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_TEMP_CODES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DATASOURCES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CODE_DIMENSIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CATEGORISATION_SEQUENCES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_CATEGORISATIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_ATTRIBUTE_VALUES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_TRANSLATIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_LOCALISED_STRINGS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_VERSION_RATIONALE_TYPE CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_RELATED_RESOURCES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_QUERIES_VERSIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_QUERIES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_PUBLICATIONS_VERSIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_PUBLICATIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DATASETS_VERSIONS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_LIS_STAT_OFFICIALITY CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_DATASETS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_STAT_RESOURCES CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_EXTERNAL_ITEMS CASCADE CONSTRAINTS PURGE;

DROP TABLE TB_INTERNATIONAL_STRINGS CASCADE CONSTRAINTS PURGE;


-- Drop pk sequence
    
 	
drop sequence SEQ_I18NSTRS;
 	
drop sequence SEQ_EXTERNAL_ITEMS;
 	
drop sequence SEQ_STAT_RESOURCES;
 	
drop sequence SEQ_DATASETS;
 	
drop sequence SEQ_STAT_OFFICIALITY;
 	
drop sequence SEQ_DATASETS_VERSIONS;
 	
drop sequence SEQ_PUBLICATIONS;
 	
drop sequence SEQ_PUBLICATIONS_VERSIONS;
 	
drop sequence SEQ_QUERIES;
 	
drop sequence SEQ_QUERIES_VERSIONS;
 	
drop sequence SEQ_RELATED_RESOURCES;
 	
drop sequence SEQ_VERSION_RATIONALE_TYPE;
 	
drop sequence SEQ_L10NSTRS;
 	
drop sequence SEQ_TRANSLATIONS;
 	
drop sequence SEQ_ATTRIBUTE_VALUES;
 	
drop sequence SEQ_CATEGORISATIONS;
 	
drop sequence SEQ_CATEGORISATION_SEQUENCES;
 	
drop sequence SEQ_CODE_DIMENSIONS;
 	
drop sequence SEQ_DATASOURCES;
 	
drop sequence SEQ_TEMP_CODES;
 	
drop sequence SEQ_CUBES;
 	
drop sequence SEQ_ELEMENTS_LEVELS;
 	
drop sequence SEQ_CHAPTERS;
 	
drop sequence SEQ_QUERY_SELECTION_ITEMS;
 	
drop sequence SEQ_CODE_ITEMS;
 	
drop sequence SEQ_TASKS;
 	


	