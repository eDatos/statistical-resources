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

commit;