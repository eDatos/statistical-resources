package org.siemac.metamac.statistical.resources.core.utils.shared;

import org.siemac.metamac.core.common.util.shared.StringUtils;

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
     * Splits an URN without prefix from a Dataset
     *
     * @param urnWithoutPrefix
     * @return
     */
    public static String[] splitDatasetUrnWithoutPrefix(String urnWithoutPrefix) {
        return splitUrnWithoutPrefix(urnWithoutPrefix);
    }

    /**
     * Splits an URN without prefix from a Publication
     *
     * @param urnWithoutPrefix
     * @return
     */
    public static String[] splitPublicationUrnWithoutPrefix(String urnWithoutPrefix) {
        return splitUrnWithoutPrefix(urnWithoutPrefix);
    }

    /**
     * Splits a publication URN in agency and resourceId
     */
    public static String[] splitUrnPublicationGlobal(String urn) {
        String identifierSplited = removePrefix(urn);
        return splitUrnWithoutVersionWithoutPrefix(identifierSplited);
    }

    /**
     * Splits an URN without prefix from a Query
     *
     * @param urnWithoutPrefix
     * @return
     */
    public static String[] splitQueryUrnWithoutPrefix(String urnWithoutPrefix) {
        return splitUrnWithoutPrefix(urnWithoutPrefix);
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

    /**
     * Splits an URN without prefix from a Multidataset
     *
     * @param urnWithoutPrefix
     * @return
     */
    public static String[] splitMultidatasetUrnWithoutPrefix(String urnWithoutPrefix) {
        return splitUrnWithoutPrefix(urnWithoutPrefix);
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
