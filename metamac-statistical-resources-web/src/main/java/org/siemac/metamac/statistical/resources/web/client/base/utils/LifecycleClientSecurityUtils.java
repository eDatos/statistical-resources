package org.siemac.metamac.statistical.resources.web.client.base.utils;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class LifecycleClientSecurityUtils extends BaseClientSecurityUtils {

    protected static boolean canSendToProductionValidation(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED);
    }

    protected static boolean canSendToDiffusionValidation(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.PRODUCTION_VALIDATION);
    }

    protected static boolean canRejectValidation(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.PRODUCTION_VALIDATION, ProcStatusEnum.DIFFUSION_VALIDATION);
    }

    protected static boolean canProgramPublication(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.DIFFUSION_VALIDATION);
    }

    protected static boolean canPublish(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.DIFFUSION_VALIDATION);
    }

    protected static boolean canVersion(ProcStatusEnum procStatus) {
        return isAnyStatus(procStatus, ProcStatusEnum.PUBLISHED);
    }

}
