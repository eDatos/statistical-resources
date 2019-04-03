package org.siemac.metamac.statistical.resources.core.utils.shared;

import org.siemac.metamac.core.common.util.shared.VersionUtil;

public class StatisticalResourcesVersionSharedUtils {

    public static final String INITIAL_VERSION = VersionUtil.INITIAL_VERSION;

    protected StatisticalResourcesVersionSharedUtils() {
    }

    public static Boolean isInitialVersion(String version) {
        return VersionUtil.isInitialVersion(version);
    }

    public static long versionStringToLong(String version) {
        return VersionUtil.versionStringToLong(version);
    }
}
