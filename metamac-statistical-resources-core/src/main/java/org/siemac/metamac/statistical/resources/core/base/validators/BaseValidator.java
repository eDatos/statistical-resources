package org.siemac.metamac.statistical.resources.core.base.validators;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;

public abstract class BaseValidator {

    // TODO: Determinar cuales son los estados en los que se puede borrar
    public static void checkStatisticalResourceCanBeDeleted(HasLifecycleStatisticalResource resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED);
    }

    // TODO: Determinar cuales son los estados en los que se puede actualizar
    public static void checkStatisticalResourceCanBeEdited(HasLifecycleStatisticalResource resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, ProcStatusEnum.DRAFT, ProcStatusEnum.VALIDATION_REJECTED, ProcStatusEnum.PRODUCTION_VALIDATION,
                ProcStatusEnum.DIFFUSION_VALIDATION);
    }
}
