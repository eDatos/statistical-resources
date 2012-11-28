package org.siemac.metamac.statistical.resources.core.base.validators;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public abstract class BaseValidator {

    // TODO: OJO Esta clase es provisional. Hay que determinar la mejor manera de que estas cosas sean comunes (si fuera posible)
   public static void checkStatisticalResourceCanBeEdited(LifeCycleStatisticalResource resource) throws MetamacException {
        StatisticalResourceProcStatusEnum resourceStatus = resource.getProcStatus();

        if (!StatisticalResourceProcStatusEnum.DRAFT.equals(resourceStatus) && !StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(resourceStatus)
                && !StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(resourceStatus) && !StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(resourceStatus)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE).withMessageParameters(resource.getUrn()).build();
        }
    }

    
}
