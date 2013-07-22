package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetCriteriaPropertyEnum {

    CODE, LATEST_DATASET_VERSION_TITLE, URN, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static DatasetCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}