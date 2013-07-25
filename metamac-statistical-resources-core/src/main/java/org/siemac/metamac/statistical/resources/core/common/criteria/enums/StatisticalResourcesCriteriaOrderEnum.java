package org.siemac.metamac.statistical.resources.core.common.criteria.enums;

public enum StatisticalResourcesCriteriaOrderEnum {

    CODE, URN, TITLE, LAST_VERSION, PROC_STATUS, STATISTICAL_OPERATION_URN, LAST_UPDATED, QUERY_STATUS;

    public String value() {
        return name();
    }

    public static StatisticalResourcesCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
