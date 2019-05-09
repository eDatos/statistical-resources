-- --------------------------------------------------------------------------------------------------
-- METAMAC-2879 - Permitir que las colecciones puedan enlazar multidatasets
-- --------------------------------------------------------------------------------------------------
ALTER TABLE TB_CUBES ADD MULTIDATASET_FK NUMBER(19);

ALTER TABLE TB_CUBES ADD CONSTRAINT FK_TB_CUBES_MULTIDATASET_FK FOREIGN KEY (MULTIDATASET_FK) REFERENCES TB_MULTIDATASETS (ID) DEFERRABLE initially IMMEDIATE;

commit;