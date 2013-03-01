package org.siemac.metamac.statistical.resources.core.dataset.criteria.enums;

public enum DatasetCriteriaOrderEnum {

    CODE, URN, TITLE;

    public String value() {
        return name();
    }

    public static DatasetCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}