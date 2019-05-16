-- --------------------------------------------------------------------------------------------------
-- METAMAC-2934 - Crear un campo "identificador" para los elementos que conforman un multidataset
-- --------------------------------------------------------------------------------------------------

ALTER TABLE TB_MD_CUBES ADD IDENTIFIER VARCHAR2(255 CHAR);

-- Se establece el valor del nuevo campo creado para los registros ya existentes
UPDATE TB_MD_CUBES SET IDENTIFIER = 'FILL_ME_WITH_IDENTIFIER_VALUE_1' WHERE ID = 'FILL_ME_WITH_CUBE_ID_1';
UPDATE TB_MD_CUBES SET IDENTIFIER = 'FILL_ME_WITH_IDENTIFIER_VALUE_2' WHERE ID = 'FILL_ME_WITH_CUBE_ID_2';
-- Harán falta tantas sentencias de update como registros existan en la tabla TB_MD_CUBES

-- Ejemplo para el entorno de local/demo:
-----------------------------------------
-- UPDATE TB_MD_CUBES SET IDENTIFIER = 'Identificador_' || ORDER_IN_MULTIDATASET;

ALTER TABLE TB_MD_CUBES MODIFY (IDENTIFIER NOT NULL);

commit;
