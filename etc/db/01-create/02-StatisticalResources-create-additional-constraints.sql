ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT LOCALE_INTERNATIONAL UNIQUE(LOCALE, INTERNATIONAL_STRING_FK) DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT URN_UNIQUE UNIQUE(URN);

