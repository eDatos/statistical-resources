package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryCriteriaPropertyEnum {

    CODE, TITLE, DESCRIPTION, URN, STATUS, PROC_STATUS;

    public String value() {
        return name();
    }

    public static QueryCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}