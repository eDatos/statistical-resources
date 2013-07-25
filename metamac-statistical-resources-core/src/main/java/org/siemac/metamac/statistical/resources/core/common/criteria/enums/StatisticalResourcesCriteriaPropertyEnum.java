package org.siemac.metamac.statistical.resources.core.common.criteria.enums;

public enum StatisticalResourcesCriteriaPropertyEnum {

    CODE, TITLE, DESCRIPTION, URN, PROC_STATUS, LAST_VERSION, STATISTICAL_OPERATION_URN, QUERY_STATUS;

    public String value() {
        return name();
    }

    public static StatisticalResourcesCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}