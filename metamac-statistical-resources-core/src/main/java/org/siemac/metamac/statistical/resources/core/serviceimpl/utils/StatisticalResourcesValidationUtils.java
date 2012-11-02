package org.siemac.metamac.statistical.resources.core.serviceimpl.utils;

import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;

public class StatisticalResourcesValidationUtils extends ValidationUtils {

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(InternationalString parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        }
    }

    /**
     * Check InternationalString is valid
     */
    public static void checkMetadataOptionalIsValid(InternationalString parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        }
    }

    /**
     * Check ExternalItem is valid
     */
    public static void checkMetadataOptionalIsValid(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // if it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        }
    }

    /**
     * Check if a collection metadata is valid.
     * @param parameter
     * @param parameterName
     * @param exceptions
     */

    @SuppressWarnings("rawtypes")
    public static void checkListMetadataOptionalIsValid(Collection parameter, String parameterName, List<MetamacExceptionItem> exceptions) {

        if (parameter == null) {
            return;
        }

        int exceptionSize = exceptions.size();

        for (Object item : parameter) {
            if (InternationalString.class.isInstance(parameter)) {
                checkMetadataOptionalIsValid((InternationalString) item, parameterName, exceptions);
            } else if (ExternalItem.class.isInstance(parameter)) {
                checkMetadataOptionalIsValid((ExternalItem) item, parameterName, exceptions);
            } else {
                checkMetadataOptionalIsValid(item, parameterName, exceptions);
            }

            // With one incorrect item is enough
            if (exceptions.size() > exceptionSize) {
                return;
            }
        }
    }

    /**
     * Check if an InternationalString is empty.
     */
    private static Boolean isEmpty(InternationalString parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        if (parameter.getTexts().size() == 0) {
            return Boolean.TRUE;
        }
        for (LocalisedString localisedString : parameter.getTexts()) {
            if (isEmpty(localisedString.getLabel()) || isEmpty(localisedString.getLocale())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Check if an ExternalItem is empty.
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(ExternalItem parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }

        return isEmpty(parameter.getCode()) || isEmpty(parameter.getUri()) || isEmpty(parameter.getUrn()) || isEmpty(parameter.getType());
    }
}
