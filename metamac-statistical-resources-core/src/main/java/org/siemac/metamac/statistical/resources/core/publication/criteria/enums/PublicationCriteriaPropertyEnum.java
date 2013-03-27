package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationCriteriaPropertyEnum {

    CODE, TITLE, URN, PROC_STATUS;

    public String value() {
        return name();
    }

    public static PublicationCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}