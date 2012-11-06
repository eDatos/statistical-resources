package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryCriteriaOrderEnum {

    CODE, URN, TITLE;

    public String value() {
        return name();
    }

    public static QueryCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
