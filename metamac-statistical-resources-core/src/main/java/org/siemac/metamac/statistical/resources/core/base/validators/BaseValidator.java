package org.siemac.metamac.statistical.resources.core.base.validators;

import org.apache.commons.lang.ArrayUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.utils.ProcStatusUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public abstract class BaseValidator {

    // TODO: Determinar cuales son los estados en los que se puede borrar
    public static void checkStatisticalResourceCanBeDeleted(LifeCycleStatisticalResource resource) throws MetamacException {
        checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED);
    }

    // TODO: Determinar cuales son los estados en los que se puede actualizar
    public static void checkStatisticalResourceCanBeEdited(LifeCycleStatisticalResource resource) throws MetamacException {
        checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.VALIDATION_REJECTED, StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION,
                StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION);
    }

    private static void checkPossibleProcStatus(LifeCycleStatisticalResource resource, StatisticalResourceProcStatusEnum... possibleProcStatus) throws MetamacException {
        if (!ArrayUtils.contains(possibleProcStatus, resource.getProcStatus())) {
            String procStatusString = ProcStatusUtils.procStatusEnumToString(possibleProcStatus);
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS).withMessageParameters(resource.getUrn(), procStatusString).build();
        }
    }
}
