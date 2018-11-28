-- --------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS ADD DATA_SOURCE_TYPE VARCHAR2(255 CHAR);

UPDATE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS SET DATA_SOURCE_TYPE = 'FILE';

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS ADD CONSTRAINT CK_DATA_SOURCE_TYPE_NOT_NULL CHECK(DATA_SOURCE_TYPE IS NOT NULL);

commit;