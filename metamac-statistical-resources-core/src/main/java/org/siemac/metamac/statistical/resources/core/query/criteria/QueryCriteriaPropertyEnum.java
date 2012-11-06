package org.siemac.metamac.statistical.resources.core.query.criteria;

public enum QueryCriteriaPropertyEnum {

    CODE, TITLE, DESCRIPTION, OPERATION_CODE, OPERATION_ID, URN;

    public String value() {
        return name();
    }

    public static QueryCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}