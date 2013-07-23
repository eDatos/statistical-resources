package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryCriteriaOrderEnum {

    CODE, URN, QUERY_VERSION_TITLE, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static QueryCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
