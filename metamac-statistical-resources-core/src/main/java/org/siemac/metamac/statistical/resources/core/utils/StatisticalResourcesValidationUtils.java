package org.siemac.metamac.statistical.resources.core.utils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;

public class StatisticalResourcesValidationUtils extends ValidationUtils {

    /**
     * Check for a required metadata and add an exception for a failed validation.
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
        }
        
        // TODO: Añadir aquí comprobación de según el tipo urn o urnInternal required o empty + TEST
//        ValidationUtils.checkMetadataRequired(conceptSchemeVersion.getRelatedOperation(), ServiceExceptionParameters.CONCEPT_SCHEME_RELATED_OPERATION, exceptions);
//        if (conceptSchemeVersion.getRelatedOperation() != null) {
//            // urn in ExternalItems is optional, but it is required for statistical operation
//            ValidationUtils.checkMetadataRequired(conceptSchemeVersion.getRelatedOperation().getUrn(), ServiceExceptionParameters.CONCEPT_SCHEME_RELATED_OPERATION, exceptions);
//            ValidationUtils.checkMetadataEmpty(conceptSchemeVersion.getRelatedOperation().getUrnInternal(), ServiceExceptionParameters.CONCEPT_SCHEME_RELATED_OPERATION, exceptions);
//        }
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
        }
    }
    
    /**
     * Check if a metadata is valid
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
     * Check if a Set<QuerySelectionItem> is empty
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
     * @param parameter
     * @return
     */
    private static Boolean isEmpty(QuerySelectionItem parameter) {
        if (isEmpty(parameter.getDimension()) || isEmpty(parameter.getCodes())) {
            return Boolean.TRUE;
        }
        
        return Boolean.FALSE;
    }
}
