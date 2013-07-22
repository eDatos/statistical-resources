package org.siemac.metamac.statistical.resources.core.query.criteria.enums;

public enum QueryVersionCriteriaOrderEnum {

    CODE, URN, TITLE;

    public String value() {
        return name();
    }

    public static QueryVersionCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
