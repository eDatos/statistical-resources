-- --------------------------------------------------------------------------------------------------
-- METAMAC-2866 - Poder especificar que un dataset tiene como datasource una vista
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_DATASETS_VERSIONS ADD (DATE_LAST_TIME_DATA_IMPORT_TZ VARCHAR2(50 CHAR), DATE_LAST_TIME_DATA_IMPORT TIMESTAMP);

commit;