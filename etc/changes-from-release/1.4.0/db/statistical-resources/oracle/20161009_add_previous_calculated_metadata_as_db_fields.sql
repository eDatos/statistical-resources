-------------------------------------------------------------------------------- 
-- METAMAC-2501 - Evitar que los metadatos bidireccionales se calculen al vuelo
--------------------------------------------------------------------------------


-- Añadir nuevas columnas a las tablas
ALTER TABLE TB_STAT_RESOURCES ADD IS_REPLACED_BY_VERSION_FK NUMBER(19);
ALTER TABLE TB_STAT_RESOURCES ADD IS_REPLACED_BY_FK NUMBER(19);


-- Añadir las restricciones de foreign keys
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL46
    FOREIGN KEY (IS_REPLACED_BY_VERSION_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT FK_TB_STAT_RESOURCES_IS_REPL23
    FOREIGN KEY (IS_REPLACED_BY_FK) REFERENCES TB_RELATED_RESOURCES (ID)  DEFERRABLE initially IMMEDIATE
;

commit;