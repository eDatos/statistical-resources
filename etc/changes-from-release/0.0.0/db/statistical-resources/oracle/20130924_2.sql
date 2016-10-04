

ALTER TABLE	TB_QUERIES_VERSIONS modify DATASET_VERSION_FK NUMBER(19) NULL;
alter table	TB_QUERIES_VERSIONS add  DATASET_FK NUMBER(19);

ALTER TABLE TB_QUERIES_VERSIONS ADD CONSTRAINT QUERY_DATASET CHECK ( NOT ( DATASET_FK IS NULL AND DATASET_VERSION_FK IS NULL ) );

-- RUN THIS QUERY YOU'LL GET A LIST OF UPDATES, THEN RUN THEM

SELECT 'UPDATE TB_QUERIES_VERSIONS SET DATASET_FK = '||datasetVersion.dataset_fk||', DATASET_VERSION_FK = null, STATUS = ''ACTIVE'' where id = '||queryVersion.id||';' as EXECUTE_THIS_SENTENCE 
FROM  tb_queries_versions queryVersion, 
      tb_datasets_versions datasetVersion,
      tb_stat_resources stat
WHERE queryversion.dataset_version_fk = datasetVersion.ID
AND datasetversion.siemac_resource_fk =  stat.ID
and stat.last_version = 1;


SELECT 'UPDATE TB_QUERIES_VERSIONS SET STATUS = ''DISCONTINUED'' where id = '||queryVersion.id||';' as EXECUTE_THIS_SENTENCE
FROM  tb_queries_versions queryVersion, 
      tb_datasets_versions datasetVersion,
      tb_stat_resources stat
WHERE queryversion.dataset_version_fk = datasetVersion.ID
AND datasetversion.siemac_resource_fk =  stat.ID
and stat.last_version = 0;