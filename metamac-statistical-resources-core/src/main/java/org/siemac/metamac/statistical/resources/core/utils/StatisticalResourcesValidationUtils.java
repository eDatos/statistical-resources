package org.siemac.metamac.statistical.resources.core.utils;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;

public class StatisticalResourcesValidationUtils extends ValidationUtils {

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(ElementLevel parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        isEmpty(parameter, parameterName, exceptions);

        if (parameter.getOrderInLevel() != null && parameter.getOrderInLevel() < 0) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, addParameter(parameterName, ServiceExceptionParameters.ORDER_IN_LEVEL)));
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(Set<QuerySelectionItem> parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
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
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(RelatedResource parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_REQUIRED, parameterName));
        }
    }

    private static void checkUrnExternalItemRequired(ExternalItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataEmpty(parameter.getUrnProvider(), parameterName, exceptions);
        } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(parameter.getType())) {
            checkMetadataRequired(parameter.getUrn(), parameterName, exceptions);
            checkMetadataOptionalIsValid(parameter.getUrnProvider(), parameterName, exceptions);
        } else {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.UNKNOWN, "Unknown type of ExternalItem"));
        }
    }

    /**
     * Check for a required metadata and add an exception for a failed validation.
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataRequired(RelatedResourceDto parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
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
        } else {
            checkUrnExternalItemRequired(parameter, parameterName, exceptions);
        }
    }

    /**
     * Check if a metadata is valid
     * 
     * @param parameter
     * @param parameterName
     * @param exceptions
     */
    public static void checkMetadataOptionalIsValid(QuerySelectionItem parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (parameter == null) {
            return;
        }

        // If it is not null, it must be complete
        if (isEmpty(parameter)) {
            exceptions.add(new MetamacExceptionItem(CommonServiceExceptionType.METADATA_INCORRECT, parameterName));
        }
    }

    /**
     * Check if a collection metadata is valid.
     * 
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
            if (InternationalString.class.isInstance(item)) {
                checkMetadataOptionalIsValid((InternationalString) item, parameterName, exceptions);
            } else if (ExternalItem.class.isInstance(item)) {
                checkMetadataOptionalIsValid((ExternalItem) item, parameterName, exceptions);
            } else if (QuerySelectionItem.class.isInstance(item)) {
                checkMetadataOptionalIsValid((QuerySelectionItem) item, parameterName, exceptions);
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
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(ExternalItem parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        return isEmpty(parameter.getCode()) || isEmpty(parameter.getUri()) || isEmpty(parameter.getType());
    }

    /**
     * Check if a RelatedResourceDto is empty.
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(RelatedResourceDto parameter) {
        if (parameter == null) {
            return Boolean.TRUE;
        }
        return isEmpty(parameter.getUrn()) || isEmpty(parameter.getType());
    }

    /**
     * Check if a RelatedResource is empty.
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(RelatedResource parameter) {
        if (parameter == null || parameter.getType() == null) {
            return Boolean.TRUE;
        }

        switch (parameter.getType()) {
            case DATASET:
                if (parameter.getDataset() == null) {
                    return true;
                }
                break;
            case DATASET_VERSION:
                if (parameter.getDatasetVersion() == null) {
                    return true;
                }
                break;
            case PUBLICATION:
                if (parameter.getPublication() == null) {
                    return true;
                }
                break;
            case PUBLICATION_VERSION:
                if (parameter.getPublicationVersion() == null) {
                    return true;
                }
                break;
            case QUERY:
                if (parameter.getQuery() == null) {
                    return true;
                }
                break;
            case QUERY_VERSION:
                if (parameter.getQueryVersion() == null) {
                    return true;
                }
                break;
            default:
                return true;
        }

        return false;
    }

    /**
     * Check if a Set<QuerySelectionItem> is empty
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(Set<QuerySelectionItem> parameter) {
        if (parameter == null || parameter.size() == 0) {
            return Boolean.TRUE;
        }

        for (QuerySelectionItem querySelectionItem : parameter) {
            if (isEmpty(querySelectionItem)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    /**
     * Check if a QuerySelectionItem is empty
     * 
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(QuerySelectionItem parameter) {
        if (isEmpty(parameter.getDimension()) || isEmpty(parameter.getCodes())) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Check if a ElementLevel is empty
     * 
     * @param parameter
     * @return
     */
    private static void isEmpty(ElementLevel parameter, String parameterName, List<MetamacExceptionItem> exceptions) {
        if (isEmpty(parameter.getPublicationVersion())) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(parameterName, ServiceExceptionSingleParameters.PUBLICATION_VERSION)));
        }

        if (isEmpty(parameter.getOrderInLevel())) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(parameterName, ServiceExceptionSingleParameters.ORDER_IN_LEVEL)));
        }
    }

}
