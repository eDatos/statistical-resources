package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.apache.commons.lang.ArrayUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class ProcStatusEnumUtils extends BaseEnumUtils {

    public static void checkPossibleProcStatus(LifeCycleStatisticalResource resource, StatisticalResourceProcStatusEnum... possibleProcStatus) throws MetamacException {
        if (!ArrayUtils.contains(possibleProcStatus, resource.getProcStatus())) {
            String procStatusString = enumToString(possibleProcStatus);
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS).withMessageParameters(resource.getUrn(), procStatusString).build();
        }
    }
}
