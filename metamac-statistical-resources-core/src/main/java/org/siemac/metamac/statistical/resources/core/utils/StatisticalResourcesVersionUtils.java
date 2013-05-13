package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesVersionSharedUtils;


public class StatisticalResourcesVersionUtils extends StatisticalResourcesVersionSharedUtils {


    public static Boolean isInitialVersion(HasLifecycle hasLifecycleStatisticalResource) {
        return hasLifecycleStatisticalResource != null? isInitialVersion(hasLifecycleStatisticalResource.getLifeCycleStatisticalResource().getVersionLogic()) : false;
    }
    
    public static String createNextVersion(HasLifecycle resource, VersionTypeEnum versionType) {
        return createNextVersion(resource.getLifeCycleStatisticalResource().getVersionLogic(), versionType);
    }
}
