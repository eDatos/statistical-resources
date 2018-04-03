package org.siemac.metamac.statistical.resources.core.multidataset.serviceimpl.validators;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

import java.net.URL;

import java.util.List;

public class MultidatasetServiceInvocationValidatorImpl {
    public static void checkCreateMultidatasetVersion(
        MultidatasetVersion multidatasetVersion,
        ExternalItem statisticalOperation, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkUpdateMultidatasetVersion(
        MultidatasetVersion multidatasetVersion,
        List<MetamacExceptionItem> exceptions) throws MetamacException {
    }

    public static void checkRetrieveMultidatasetVersionByUrn(
        String multidatasetVersionUrn, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkRetrieveLatestMultidatasetVersionByMultidatasetUrn(
        String multidatasetUrn, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkRetrieveLatestPublishedMultidatasetVersionByMultidatasetUrn(
        String multidatasetUrn, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkRetrieveMultidatasetVersions(
        String multidatasetVersionUrn, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkFindMultidatasetVersionsByCondition(
        List<ConditionalCriteria> conditions, PagingParameter pagingParameter,
        List<MetamacExceptionItem> exceptions) throws MetamacException {
    }

    public static void checkDeleteMultidatasetVersion(
        String multidatasetVersionUrn, List<MetamacExceptionItem> exceptions)
        throws MetamacException {
    }

    public static void checkImportMultidatasetVersionStructure(
        String multidatasetVersionUrn, URL fileURL, String language,
        List<MetamacExceptionItem> exceptions) throws MetamacException {
    }
}
