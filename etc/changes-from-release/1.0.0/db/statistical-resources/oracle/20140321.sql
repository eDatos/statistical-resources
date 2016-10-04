-- -----------------------------------------------------------------------------------------------
--METAMAC-2201 - El code y la urn debe ser not nullable, asegurarse además de generar urns para los que no tengan ahora
-- -----------------------------------------------------------------------------------------------
ALTER TABLE TB_STAT_RESOURCES MODIFY (
  CODE VARCHAR2(255 CHAR) NOT NULL,
  URN VARCHAR2(255 CHAR) NOT NULL);
  
