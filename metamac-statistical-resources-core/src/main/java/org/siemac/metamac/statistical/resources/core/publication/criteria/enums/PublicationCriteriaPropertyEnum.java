package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationCriteriaPropertyEnum {

    CODE, PUBLICATION_VERSION_TITLE, URN, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static PublicationCriteriaPropertyEnum fromValue(String v) {
        return valueOf(v);
    }
}