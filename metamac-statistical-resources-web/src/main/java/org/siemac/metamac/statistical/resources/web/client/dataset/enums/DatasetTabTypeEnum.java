package org.siemac.metamac.statistical.resources.web.client.dataset.enums;

import java.io.Serializable;

public enum DatasetTabTypeEnum implements Serializable {
    METADATA, DATASOURCES, ATTRIBUTES;

    private DatasetTabTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
