package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationCriteriaOrderEnum {

    CODE, URN, PUBLICATION_VERSION_TITLE, STATISTICAL_OPERATION_URN;

    public String value() {
        return name();
    }

    public static PublicationCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
