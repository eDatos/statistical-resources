package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationVersionCriteriaOrderEnum {

    CODE, URN, TITLE, PROC_STATUS, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static PublicationVersionCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
