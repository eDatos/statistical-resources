-- --------------------------------------------------------------------------------------------------
-- METAMAC-2913 - Modificación del versionado de datasets
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS ADD KEEP_ALL_DATA NUMBER(1,0);

UPDATE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS SET KEEP_ALL_DATA = 1;

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS MODIFY (KEEP_ALL_DATA NOT NULL);

commit;