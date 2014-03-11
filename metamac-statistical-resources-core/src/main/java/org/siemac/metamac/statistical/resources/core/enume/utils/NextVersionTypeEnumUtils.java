package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.apache.commons.lang.ArrayUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;

public class NextVersionTypeEnumUtils extends BaseEnumUtils {

    public static boolean isInAnyNextVersionType(HasLifecycle resource, NextVersionTypeEnum... possibleNextVersionType) {
        return ArrayUtils.contains(possibleNextVersionType, resource.getLifeCycleStatisticalResource().getNextVersion());
    }
}
