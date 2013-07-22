package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetVersionCriteriaPropertyEnum {

    CODE, TITLE, URN, PROC_STATUS, LAST_VERSION, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static DatasetVersionCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}