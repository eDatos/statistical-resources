package org.siemac.metamac.statistical.resources.web.shared.utils;

import java.io.Serializable;

public enum ImportableResourceTypeEnum implements Serializable {
    DATASOURCE, PUBLICATION_VERSION_STRUCTURE;

    private ImportableResourceTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
