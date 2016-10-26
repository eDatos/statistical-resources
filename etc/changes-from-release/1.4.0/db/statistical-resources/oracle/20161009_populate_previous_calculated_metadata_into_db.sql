DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  FOR relateds IN
  (SELECT stat.id source,
    related.PUBLICATION_VERSION_FK related_version_fk,
    pubVersion.id pubVersion_fk
  FROM tb_stat_resources stat,
    tb_related_resources related,
    TB_PUBLICATIONS_VERSIONS pubVersion
  WHERE related.type                = 'PUBLICATION_VERSION'
  AND stat.replaces_version_fk      = related.ID
  AND pubVersion.SIEMAC_RESOURCE_FK = stat.id
  )
  LOOP
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
        NULL,
        NULL,
        relateds.pubVersion_fk,
        NULL,
        NULL,
        NULL,
        'PUBLICATION_VERSION'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM tb_stat_resources stat_2,
      TB_PUBLICATIONS_VERSIONS pubVersion_2,
      tb_related_resources related_2
    WHERE related_2.type                = 'PUBLICATION_VERSION'
    AND pubVersion_2.SIEMAC_RESOURCE_FK = stat_2.id
    AND pubVersion_2.id                 = relateds.related_version_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_VERSION_FK = new_related_id
    WHERE ID                      = stat_2_update_is_replaced;
  END LOOP;
END;
----- MISMO SCRIPT PARA LOS QUERY
DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  FOR relateds IN
  (SELECT stat.id source,
    related.QUERY_VERSION_FK related_query_version_fk,
    queriesVersion.id queriesVersion_fk
  FROM tb_stat_resources stat,
    tb_related_resources related,
    TB_QUERIES_VERSIONS queriesVersion
  WHERE related.type                       = 'QUERY_VERSION'
  AND stat.replaces_version_fk             = related.ID
  AND queriesVersion.LIFECYCLE_RESOURCE_FK = stat.id
  )
  LOOP
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
        NULL,
        NULL,
        NULL,
        NULL,
        relateds.queriesVersion_fk,
        NULL,
        'QUERY_VERSION'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM tb_stat_resources stat_2,
      TB_QUERIES_VERSIONS queriesVersion_2,
      tb_related_resources related_2
    WHERE related_2.type                       = 'QUERY_VERSION'
    AND queriesVersion_2.LIFECYCLE_RESOURCE_FK = stat_2.id
    AND queriesVersion_2.id                    = relateds.related_query_version_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_VERSION_FK = new_related_id
    WHERE ID                      = stat_2_update_is_replaced;
  END LOOP;
END;
---------------------------------
-- Mismo script para los datasets
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
--------------------------------------------------------------
------ CAMPO IS_REPLACED_BY
--------------------------------------------------------------
-- PUBLICATIONS
DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  FOR relateds IN
  (SELECT stat.id source,
    related.PUBLICATION_FK related_fk,
    pub.id pub_fk
  FROM tb_stat_resources stat,
    tb_related_resources related,
    TB_PUBLICATIONS pub
  WHERE related.type               = 'PUBLICATION'
  AND stat.replaces_fk             = related.ID
  AND pub.IDENTIFIABLE_RESOURCE_FK = stat.id
  )
  LOOP
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
        NULL,
        NULL,
        NULL,
        relateds.pub_fk,
        NULL,
        NULL,
        'PUBLICATION'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM tb_stat_resources stat_2,
      TB_PUBLICATIONS pub_2,
      tb_related_resources related_2
    WHERE related_2.type               = 'PUBLICATION'
    AND pub_2.IDENTIFIABLE_RESOURCE_FK = stat_2.id
    AND pub_2.id                       = relateds.related_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_FK = new_related_id
    WHERE ID              = stat_2_update_is_replaced;
  END LOOP;
END;
---------------------------------
-- DATASETS
DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  FOR relateds IN
  (SELECT stat.id source,
    related.DATASET_FK related_fk,
    dataset.id dataset_fk
  FROM TB_STAT_RESOURCES stat,
    TB_RELATED_RESOURCES related,
    TB_DATASETS dataset
  WHERE related.type                   = 'DATASET'
  AND stat.replaces_fk                 = related.ID
  AND dataset.IDENTIFIABLE_RESOURCE_FK = stat.id
  )
  LOOP
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
        NULL,
        relateds.dataset_fk,
        NULL,
        NULL,
        NULL,
        NULL,
        'DATASET'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM TB_STAT_RESOURCES stat_2,
      TB_DATASETS datasets_2,
      TB_RELATED_RESOURCES related_2
    WHERE related_2.type                    = 'DATASET'
    AND datasets_2.IDENTIFIABLE_RESOURCE_FK = stat_2.id
    AND datasets_2.id                       = relateds.related_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_FK = new_related_id
    WHERE ID              = stat_2_update_is_replaced;
  END LOOP;
END;
---------------------------------
-- QUERIES
DECLARE
  new_related_id            NUMBER(19,0);
  stat_2_update_is_replaced NUMBER(19,0);
BEGIN
  FOR relateds IN
  (SELECT stat.id source,
    related.QUERY_FK related_fk,
    query.id query_fk
  FROM TB_STAT_RESOURCES stat,
    TB_RELATED_RESOURCES related,
    TB_QUERIES query
  WHERE related.type                 = 'QUERY'
  AND stat.replaces_fk               = related.ID
  AND query.IDENTIFIABLE_RESOURCE_FK = stat.id
  )
  LOOP
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
        NULL,
        NULL,
        NULL,
        NULL,
        NULL,
        relateds.query_fk,
        'QUERY'
      );
    -- OBTENER EL ID DEL STAT RESOURCE A ACTUALIZAR
    SELECT DISTINCT stat_2.id
    INTO stat_2_update_is_replaced
    FROM TB_STAT_RESOURCES stat_2,
      TB_QUERIES query_2,
      TB_RELATED_RESOURCES related_2
    WHERE related_2.type                 = 'QUERY'
    AND query_2.IDENTIFIABLE_RESOURCE_FK = stat_2.id
    AND query_2.id                       = relateds.related_fk;
    -- ACTUALIZAR EL STAT RESOURCE
    UPDATE tb_stat_resources
    SET IS_REPLACED_BY_FK = new_related_id
    WHERE ID              = stat_2_update_is_replaced;
  END LOOP;
END;
