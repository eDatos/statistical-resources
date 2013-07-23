package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryCriteriaPropertyEnum {

    CODE, QUERY_VERSION_TITLE, URN, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static QueryCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}