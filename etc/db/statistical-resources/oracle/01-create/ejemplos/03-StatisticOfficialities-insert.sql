
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, 'Estadística oficial', 'es', SEQ_I18NSTRS.currval, 1);
insert into TB_LIS_STAT_OFFICIALITY(ID,IDENTIFIER,VERSION,DESCRIPTION_FK) values (SEQ_STAT_OFFICIALITY.nextval,'OFFICIAL',0, SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, 'Estadística no oficial', 'es', SEQ_I18NSTRS.currval, 1);
insert into TB_LIS_STAT_OFFICIALITY(ID,IDENTIFIER,VERSION,DESCRIPTION_FK) values (SEQ_STAT_OFFICIALITY.nextval,'NOTOFFICIAL',0, SEQ_I18NSTRS.currval);