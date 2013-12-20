-- ----------------------------------------------------------------------------------
-- METAMAC-1976 - Posibilidad de setear en la WEB los datos por defecto de data
-- ----------------------------------------------------------------------------------

-- --------------
-- DB Connection
-- --------------
-- statistical-resources
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.db.url','jdbc:oracle:thin:@FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.db.driver_name','oracle.jdbc.OracleDriver');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.db.username','FILL_ME_WITH_USER');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.db.dialect','org.siemac.metamac.hibernate.dialect.Oracle10gDialectMetamac');
  
-- statistical-resources-repository
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.repo.db.url','jdbc:oracle:thin:@FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.repo.db.driver_name','oracle.jdbc.OracleDriver');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.repo.db.username','FILL_ME_WITH_USER');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.repo.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.repo.db.dialect','org.siemac.metamac.hibernate.dialect.Oracle10gDialectMetamac');


-- ---------------------------
-- Internal properties
-- ---------------------------
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.user_guide.file_name','Gestor_recursos_estadisticos-Manual_usuario.pdf');
insert into TB_DATA_CONFIGURATIONS (ID,VERSION,SYSTEM_PROPERTY,CONF_KEY,CONF_VALUE) values(GET_NEXT_SEQUENCE_VALUE('DATA_CONFIGURATIONS'),1,1,'metamac.statistical_resources.dot_code_mapping','ONE_DOT=CODE_ONE,TWO_DOT=CODE_TWO,THREE_DOT=THREE_DOT,FOUR_DOT=FOUR_CODE,FIVE_DOT=FIVE_CODE,SIX_DOT=SIX_CODE');