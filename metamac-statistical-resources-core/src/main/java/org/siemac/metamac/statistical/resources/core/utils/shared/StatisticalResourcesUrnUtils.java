package org.siemac.metamac.statistical.resources.core.utils.shared;

import org.apache.commons.lang.StringUtils;

public class StatisticalResourcesUrnUtils extends org.siemac.metamac.core.common.util.shared.UrnUtils {

    /**
     * Splits a dataset URN in agency and resourceId
     */
    public static String[] splitUrnDatasetGlobal(String urn) {
        String identifierSplited = removePrefix(urn);
        return splitUrnWithoutVersionWithoutPrefix(identifierSplited);
    }

    /**
     * Splits a dataset URN in agency, resourceId and version
     */
    public static String[] splitUrnDataset(String urn) {
        String identifierSplited = removePrefix(urn);
        return splitUrnWithoutPrefix(identifierSplited);
    }

    /**
     * Splits a query URN in agency and resourceId
     */
    public static String[] splitUrnQueryGlobal(String urn) {
        String identifierSplited = removePrefix(urn);
        return splitUrnWithoutVersionWithoutPrefix(identifierSplited);
    }

    /**
     * Splits a query URN in agency, resourceId and version
     */
    public static String[] splitUrnQuery(String urn) {
        String identifierSplited = removePrefix(urn);
        return splitUrnWithoutPrefix(identifierSplited);
    }

    private static String[] splitUrnWithoutVersionWithoutPrefix(String identifier) {
        String agencyID = StringUtils.substringBefore(identifier, COLON);
        String resourceID = StringUtils.substringAfter(identifier, COLON);
        return new String[]{agencyID, resourceID};
    }

    private static String[] splitUrnWithoutPrefix(String identifier) {
        String agencyID = StringUtils.substringBefore(identifier, COLON.toString());
        String resourceID = StringUtils.substringBetween(identifier, COLON, LEFT_PARENTHESIS);
        String version = StringUtils.substringBetween(identifier, LEFT_PARENTHESIS, RIGHT_PARENTHESIS);
        return new String[]{agencyID, resourceID, version};
    }
}
