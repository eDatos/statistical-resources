-- --------------------------------------------------------------------------------------------------
-- METAMAC-2934 - Crear un campo "identificador" para los elementos que conforman un multidataset
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_MD_CUBES ADD IDENTIFIER VARCHAR2(255 CHAR);

UPDATE TB_MD_CUBES SET IDENTIFIER = 'Identificador_' || ORDER_IN_MULTIDATASET;

ALTER TABLE TB_MD_CUBES MODIFY (IDENTIFIER NOT NULL);

commit;
