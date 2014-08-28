package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum DatasetConstraintInclusionTypeEnum implements Serializable {
    INCLUSION, EXCLUSION;

    private DatasetConstraintInclusionTypeEnum() {
    }

    public String getName() {
        return name();
    }
}
