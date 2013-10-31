package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum DatasetTabTypeEnum implements Serializable {
    METADATA, DATASOURCES, ATTRIBUTES, CATEGORISATIONS;

    private DatasetTabTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
