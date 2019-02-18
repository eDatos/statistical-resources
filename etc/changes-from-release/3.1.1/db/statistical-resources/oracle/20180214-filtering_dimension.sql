-- --------------------------------------------------------------------------------------------------
-- METAMAC-2878 - Permitir indicar el nombre que se quiere que tome la dimensión de filtrado
-- --------------------------------------------------------------------------------------------------

ALTER TABLE METAMAC_STATISTICAL_RESOURCES.TB_MULTIDATASETS_VERSIONS ADD FILTERING_DIMENSION_FK NUMBER(19);

ALTER TABLE TB_MULTIDATASETS_VERSIONS ADD CONSTRAINT FK_TB_MULTIDATASETS_VERSIONS11
	FOREIGN KEY (FILTERING_DIMENSION_FK) REFERENCES TB_INTERNATIONAL_STRINGS (ID) DEFERRABLE initially IMMEDIATE;

commit;