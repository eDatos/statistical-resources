-- --------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS ADD VERSIONABLE NUMBER(1,0);

UPDATE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS SET VERSIONABLE = 1;

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASETS_VERSIONS MODIFY (VERSIONABLE NOT NULL);

commit;