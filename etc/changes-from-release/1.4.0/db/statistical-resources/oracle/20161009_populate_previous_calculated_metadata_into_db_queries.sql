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


commit;