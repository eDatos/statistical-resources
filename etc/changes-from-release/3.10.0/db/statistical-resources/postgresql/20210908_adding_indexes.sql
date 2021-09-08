-- ---------------------------------------------------------------------------------------------------
-- EDATOS-3406 - Migración de datos a Postgresql
-- ---------------------------------------------------------------------------------------------------
-- Se crean índices para la base de datos PostgreSQL de cara a mejorar el rendimiento de la siguiente
-- consulta: 
-- https://git.arte-consultores.com/istac/metamac-statistical-resources/-/blob/develop/metamac-statistical-resources-core/src/main/java/org/siemac/metamac/statistical/resources/core/dataset/repositoryimpl/DatasetVersionRepositoryImpl.java#L170
-- 
-- En Oracle no existían estos índices de forma explícita debido a que los crea de forma automática 
-- en memoria a medida que detecta la necesidad.
-- ---------------------------------------------------------------------------------------------------

CREATE INDEX tb_code_dimensions_dataset_version_fk_idx ON metamac_statistical_resources_bd.tb_code_dimensions USING btree (dataset_version_fk);
CREATE INDEX tb_code_dimensions_dsd_component_id_idx ON metamac_statistical_resources_bd.tb_code_dimensions USING btree (dsd_component_id);

commit;