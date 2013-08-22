package org.siemac.metamac.statistical.resources.web.shared.DTO;

import java.io.Serializable;

public enum RepresentationTypeEnum implements Serializable {
    ENUMERATION, TEXT_FORMAT;

    private RepresentationTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
