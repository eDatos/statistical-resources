
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (nextval('seq_i18nstrs'), 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (nextval('seq_l10nstrs'), 'Estadística oficial', 'es', currval('seq_i18nstrs'), 1);
insert into TB_LIS_STAT_OFFICIALITY(ID,IDENTIFIER,VERSION,DESCRIPTION_FK) values (nextval('seq_stat_officiality'),'OFFICIAL',0, currval('seq_i18nstrs'));

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (nextval('seq_i18nstrs'), 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (nextval('seq_l10nstrs'), 'Estadística no oficial', 'es', currval('seq_i18nstrs'), 1);
insert into TB_LIS_STAT_OFFICIALITY(ID,IDENTIFIER,VERSION,DESCRIPTION_FK) values (nextval('seq_stat_officiality'),'NOTOFFICIAL',0, currval('seq_i18nstrs'));