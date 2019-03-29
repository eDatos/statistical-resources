package org.siemac.metamac.statistical.resources.core.utils.shared;

import org.siemac.metamac.core.common.util.shared.VersionUtil;

public class StatisticalResourcesVersionSharedUtils {

    protected StatisticalResourcesVersionSharedUtils() {

    }

    public static String getInitialVersion() {
        return VersionUtil.getInitialVersion();
    }

    public static Boolean isInitialVersion(String version) {
        return VersionUtil.isInitialVersion(version);
    }

    public static long getVersionToLong(String version) {
        return VersionUtil.getVersionToLong(version);
    }
}
