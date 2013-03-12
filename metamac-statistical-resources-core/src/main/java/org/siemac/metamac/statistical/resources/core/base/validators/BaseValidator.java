package org.siemac.metamac.statistical.resources.core.base.validators;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;

public abstract class BaseValidator {

    // TODO: Determinar cuales son los estados en los que se puede borrar
    public static void checkStatisticalResourceCanBeDeleted(LifeCycleStatisticalResource resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);
    }

    // TODO: Determinar cuales son los estados en los que se puede actualizar
    public static void checkStatisticalResourceCanBeEdited(LifeCycleStatisticalResource resource) throws MetamacException {
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION,
                StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
    }
}
