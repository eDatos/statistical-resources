package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum DatasetTabTypeEnum implements Serializable {
    METADATA, CONSTRAINTS, DATASOURCES, ATTRIBUTES, CATEGORISATIONS;

    private DatasetTabTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
