package org.siemac.metamac.statistical.resources.core.utils.shared;

import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;


public class StatisticalResourcesVersionSharedUtils {
    public static final VersionPatternEnum VERSION_PATTERN = VersionPatternEnum.XXX_YYY;
    public static final String INITIAL_VERSION = VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION;

    public static String createInitialVersion() {
        return VersionUtil.createInitialVersion(VERSION_PATTERN);
    }

    public static Boolean isInitialVersion(String version) {
        return VersionUtil.isInitialVersion(version);
    }

    /**
     * Create the next version tag. 
     * 
     * @param olderVersion actual version
     * @param minor, TRUE if is a new minor version, FALSE if not
     * @return new version
     */
    public static String createNextVersion(String olderVersion, VersionTypeEnum versionType) {
        return VersionUtil.createNextVersion(olderVersion, VERSION_PATTERN, versionType);
    }

}
