ALTER TABLE TB_LOCALISED_STRINGS ADD CONSTRAINT LOCALE_INTERNATIONAL UNIQUE(LOCALE, INTERNATIONAL_STRING_FK) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE TB_STAT_RESOURCES ADD CONSTRAINT URN_UNIQUE UNIQUE(URN);

ALTER TABLE TB_QUERY_SELECTION_ITEMS ADD CONSTRAINT QUERY_DIMENSION_UNIQUE UNIQUE(DIMENSION, QUERY_FK) DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE TB_CODE_ITEMS ADD CONSTRAINT DIMENSION_CODE_UNIQUE UNIQUE(CODE, QUERY_SELECTION_ITEM_FK) DEFERRABLE INITIALLY DEFERRED;
