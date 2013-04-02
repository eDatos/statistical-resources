package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetCriteriaOrderEnum {

    CODE, URN, TITLE, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static DatasetCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
