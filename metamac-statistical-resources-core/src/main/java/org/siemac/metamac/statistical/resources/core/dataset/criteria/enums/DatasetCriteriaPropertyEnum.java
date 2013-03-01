package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetCriteriaPropertyEnum {

    CODE, TITLE, URN, PROC_STATUS;

    public String value() {
        return name();
    }

    public static DatasetCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}