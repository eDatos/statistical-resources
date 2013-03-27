package org.siemac.metamac.statistical.resources.core.publication.criteria.enums;

public enum PublicationCriteriaOrderEnum {

    CODE, URN, TITLE;

    public String value() {
        return name();
    }

    public static PublicationCriteriaOrderEnum fromValue(String v) {
        return valueOf(v);
    }

}
