-- --------------------------------------------------------------------------------------------------
-- EDATOS-2974 - Error al definir el periodo de inicio para una consulta de tipo autoincremental
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_QUERIES_VERSIONS DROP COLUMN LATEST_TEMPORAL_CODE_CREATION;

commit;