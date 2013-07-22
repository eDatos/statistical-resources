package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryVersionCriteriaPropertyEnum {

    CODE, TITLE, DESCRIPTION, URN, STATUS, PROC_STATUS;

    public String value() {
        return name();
    }

    public static QueryVersionCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}