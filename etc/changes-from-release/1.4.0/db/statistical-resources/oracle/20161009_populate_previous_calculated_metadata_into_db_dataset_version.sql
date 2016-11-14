DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  -- Dataset_versions_FK
  FOR relateds IN
  (SELECT stat.id source,
    related.dataset_version_fk related_dataset_version_fk,
    datasetVersion.id datasetVersion_fk
  FROM tb_stat_resources stat,
    tb_related_resources related,
    TB_DATASETS_VERSIONS datasetVersion
  WHERE related.type                    = 'DATASET_VERSION'
  AND stat.replaces_version_fk          = related.ID
  AND datasetVersion.SIEMAC_RESOURCE_FK = stat.id
  )
  LOOP
    -- DBMS_OUTPUT.put_line (' source: ' || relateds.source ||  ' related: ' || relateds.related_dataset_version_fk ||  ' datasetversio: ' || relateds.datasetVersion_fk);
    -- INSERTAR NUEVO RELATED RESOURCE
    new_related_id := SEQ_RELATED_RESOURCES.NEXTVAL;
    INSERT
    INTO TB_RELATED_RESOURCES
      (
        ID,
        VERSION,
        DATASET_VERSION_FK,
        DATASET_FK,
        PUBLICATION_VERSION_FK,
        PUBLICATION_FK,
        QUERY_VERSION_FK,
        QUERY_FK,
        TYPE
      )
      VALUES
      (
        new_related_id,
        '0',
        relateds.datasetVersion_fk,
        NULL,
        NULL,
        NULL,
        NULL,
        NULL,
        'DATASET_VERSION'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM tb_stat_resources stat_2,
      TB_DATASETS_VERSIONS datasetVersion_2,
      tb_related_resources related_2
    WHERE related_2.type                    = 'DATASET_VERSION'
    AND datasetVersion_2.SIEMAC_RESOURCE_FK = stat_2.id
    AND datasetVersion_2.id                 = relateds.related_dataset_version_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_VERSION_FK = new_related_id
    WHERE ID                      = stat_2_update_is_replaced;
  END LOOP;
END;

commit;