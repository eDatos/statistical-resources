package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum PublicationTabTypeEnum implements Serializable {
    METADATA, STRUCTURE;

    private PublicationTabTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
