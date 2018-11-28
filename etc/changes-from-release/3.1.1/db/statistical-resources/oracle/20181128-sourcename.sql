-- --------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_DATASOURCES RENAME COLUMN FILENAME TO SOURCE_NAME;

commit;