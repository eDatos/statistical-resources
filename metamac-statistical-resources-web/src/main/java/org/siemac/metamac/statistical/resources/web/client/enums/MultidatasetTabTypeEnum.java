package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum MultidatasetTabTypeEnum implements Serializable {
    METADATA, STRUCTURE;

    private MultidatasetTabTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
