package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetVersionCriteriaOrderEnum {

    CODE, URN, TITLE, LAST_VERSION, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static DatasetVersionCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
