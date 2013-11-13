-- ###########################################
-- # insert
-- ###########################################

insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.dot_code_mapping','ONE_DOT=CODE_ONE,TWO_DOT=CODE_TWO,THREE_DOT=THREE_DOT,FOUR_DOT=FOUR_CODE,FIVE_DOT=FIVE_CODE,SIX_DOT=SIX_CODE');

-- DATASOURCE: ORACLE
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.db.url','jdbc:sqlserver://FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE;databaseName=FILL_ME_WITH_DATABASE_NAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.db.username','FILL_ME_WITH_USERNAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.db.driver_name','oracle.jdbc.OracleDriver');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.db.dialect','org.siemac.metamac.hibernate.dialect.Oracle10gDialectMetamac');

-- DATASOURCE REPO: ORACLE
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.repo.db.url','jdbc:sqlserver://FILL_ME_WITH_HOST:FILL_ME_WITH_PORT:XE;databaseName=FILL_ME_WITH_DATABASE_NAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.repo.db.username','FILL_ME_WITH_USERNAME');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.repo.db.password','FILL_ME_WITH_PASSWORD');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.repo.db.driver_name','oracle.jdbc.OracleDriver');
insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.repo.db.dialect','org.siemac.metamac.hibernate.dialect.Oracle10gDialectMetamac');

insert into TB_CONFIGURATIONS (CONF_KEY,CONF_VALUE) values ('metamac.statistical_resources.user_guide.file_name','Gestor_recursos_estadisticos-Manual_usuario.pdf');