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

commit;