package org.siemac.metamac.statistical.resources.core.utils.shared;

import static org.siemac.metamac.core.common.constants.shared.UrnConstants.URN_SIEMAC_CLASS_CHAPTER_PREFIX;
import static org.siemac.metamac.core.common.constants.shared.UrnConstants.URN_SIEMAC_CLASS_CUBE_PREFIX;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;

public class StatisticalResourcesUrnParserUtils {

    // PUBLICATIONS

    public static boolean isPublicationChapterUrn(String urn) {
        return matches(URN_SIEMAC_CLASS_CHAPTER_PREFIX, UrnUtils.extractPrefix(urn));
    }

    public static boolean isPublicationCubeUrn(String urn) {
        return matches(URN_SIEMAC_CLASS_CUBE_PREFIX, UrnUtils.extractPrefix(urn));
    }

    // Generic methods

    protected static boolean matches(String prefix, String urn) {
        if (StringUtils.isBlank(prefix)) {
            throw new IllegalArgumentException("Prefix can not be empty");
        }
        if (StringUtils.isBlank(urn)) {
            throw new IllegalArgumentException("URN can not be empty");
        }
        return StringUtils.equals(prefix, UrnUtils.extractPrefix(urn));
    }
}
