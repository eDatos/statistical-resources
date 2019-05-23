package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesVersionSharedUtils;

public class StatisticalResourcesVersionUtils extends StatisticalResourcesVersionSharedUtils {

    private StatisticalResourcesVersionUtils() {

    }

    public static Boolean isInitialVersion(HasLifecycle hasLifecycleStatisticalResource) {
        return hasLifecycleStatisticalResource != null ? isInitialVersion(hasLifecycleStatisticalResource.getLifeCycleStatisticalResource().getVersionLogic()) : false;
    }

    public static String createNextVersion(HasLifecycle resource, VersionTypeEnum versionType) throws MetamacException {
        return createNextVersion(resource.getLifeCycleStatisticalResource().getVersionLogic(), versionType);
    }

    public static String createNextVersion(String olderVersion, VersionTypeEnum versionType) throws MetamacException {
        try {
            return VersionUtil.createNextVersion(olderVersion, versionType);
        } catch (UnsupportedOperationException e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.RESOURCE_MAXIMUM_VERSION_REACHED).withMessageParameters(versionType, olderVersion).build();
        }
    }

    public static String getVersionWithoutDot(String version) {
        return VersionUtil.getVersionWithoutDot(version);
    }
}
