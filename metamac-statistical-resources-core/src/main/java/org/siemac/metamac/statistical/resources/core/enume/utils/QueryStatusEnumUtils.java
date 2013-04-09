package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.apache.commons.lang.ArrayUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public class QueryStatusEnumUtils extends BaseEnumUtils {

    public static void checkPossibleQueryStatus(QueryVersion resource, QueryStatusEnum... possibleStatus) throws MetamacException {
        if (!ArrayUtils.contains(possibleStatus, resource.getStatus())) {
            String statusString = enumToString(possibleStatus);
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS).withMessageParameters(resource.getLifeCycleStatisticalResource().getUrn(), statusString).build();
        }
    }
}
