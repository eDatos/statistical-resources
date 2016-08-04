package org.siemac.metamac.statistical.resources.web.client.enums;

import java.io.Serializable;

public enum LifeCycleActionEnum implements Serializable {
    SEND_TO_PRODUCTION_VALIDATION, SEND_TO_DIFFUSION_VALIDATION, REJECT_VALIDATION, PUBLISH, PROGRAM_PUBLICATION, CANCEL_PROGRAMMED_PUBLICATION, VERSION;

    private LifeCycleActionEnum() {
    }

    public String getName() {
        return name();
    }
}
