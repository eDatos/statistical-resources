package org.siemac.metamac.statistical.resources.web.shared.DTO;

import java.io.Serializable;

public enum AttributeRelathionshipTypeEnum implements Serializable {
    NO_SPECIFIED_RELATIONSHIP, PRIMARY_MEASURE_RELATIONSHIP, GROUP_RELATIONSHIP, DIMENSION_RELATIONSHIP;

    private AttributeRelathionshipTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
