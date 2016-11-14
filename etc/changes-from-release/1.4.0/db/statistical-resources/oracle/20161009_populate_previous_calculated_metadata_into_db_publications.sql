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

commit;