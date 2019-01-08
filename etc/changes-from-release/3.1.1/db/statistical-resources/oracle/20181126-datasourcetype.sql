-- --------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS ADD DATA_SOURCE_TYPE VARCHAR2(255 CHAR);

UPDATE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS SET DATA_SOURCE_TYPE = 'FILE';

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS MODIFY (DATA_SOURCE_TYPE NOT NULL);

commit;