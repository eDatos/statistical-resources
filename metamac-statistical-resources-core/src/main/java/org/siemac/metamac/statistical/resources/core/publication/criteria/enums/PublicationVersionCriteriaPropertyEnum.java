package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationVersionCriteriaPropertyEnum {

    CODE, TITLE, URN, PROC_STATUS, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static PublicationVersionCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}