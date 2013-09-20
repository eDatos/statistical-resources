-- YEAR
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.YEAR', SEQ_I18NSTRS.currval);

-- MONTH
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{MM}/{yyyy}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{MM}/{yyyy}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.MONTH', SEQ_I18NSTRS.currval);

-- DATE
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.DATE', SEQ_I18NSTRS.currval);

-- DATE RANGE
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} to {dd_END}/{MM_END}/{yyyy_END}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} a {dd_END}/{MM_END}/{yyyy_END}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.DATE_RANGE', SEQ_I18NSTRS.currval);

-- DATETIME
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} {hh}:{mm}:{ss}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} {hh}:{mm}:{ss}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.DATETIME', SEQ_I18NSTRS.currval);

-- DATETIME RANGE
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} {hh}:{mm}:{ss} to {dd_END}/{MM_END}/{yyyy_END} {hh_END}:{mm_END}:{ss_END}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{dd}/{MM}/{yyyy} {hh}:{mm}:{ss} a {dd_END}/{MM_END}/{yyyy_END} {hh_END}:{mm_END}:{ss_END}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.DATETIME_RANGE', SEQ_I18NSTRS.currval);

-- SEMESTER
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} First semester', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Primer semestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.SEMESTER.S1', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Second semester', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Segundo semestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.SEMESTER.S2', SEQ_I18NSTRS.currval);

-- TRIMESTER
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} First trimester', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Primer trimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.TRIMESTER.T1', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Second trimester', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Segundo trimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.TRIMESTER.T2', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Third trimester', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Tercer trimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.TRIMESTER.T3', SEQ_I18NSTRS.currval);

-- QUARTER
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} First quarter', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Primer cuatrimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.QUARTER.Q1', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Second quarter', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Segundo cuatrimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.QUARTER.Q2', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Third quarter', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Tercer cuatrimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.QUARTER.Q3', SEQ_I18NSTRS.currval);

INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Fourth quarter', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Cuarto cuatrimestre', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.QUARTER.Q4', SEQ_I18NSTRS.currval);

-- WEEK
INSERT INTO TB_INTERNATIONAL_STRINGS (ID, VERSION) values (SEQ_I18NSTRS.nextval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Week {ww}', 'en', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_LOCALISED_STRINGS (ID, LABEL, LOCALE, INTERNATIONAL_STRING_FK, VERSION) values (SEQ_L10NSTRS.nextval, '{yyyy} Semana {ww}', 'es', SEQ_I18NSTRS.currval, 1);
INSERT INTO TB_TRANSLATIONS (ID, CODE, TITLE_FK) values (SEQ_TRANSLATIONS.nextval, 'TIME_SDMX.WEEK', SEQ_I18NSTRS.currval);


commit;