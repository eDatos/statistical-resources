package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleChecker {

    @Autowired
    private LifecycleChecker               lifecycleChecker;

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private ExternalItemChecker            externalItemChecker;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToProductionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToDiffusionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToValidationRejected(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(HasSiemacMetadata resource, HasSiemacMetadata previousVersion, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToPublished(resource, previousVersion, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
        checkRelatedResourcesPreviouslyPublished(resource, metadataName, exceptionItems);
        checkExternalItemsPreviouslyPublished(resource.getSiemacMetadataStatisticalResource(), addParameter(metadataName, ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE),
                exceptionItems);
    }

    private void checkExternalItemsPreviouslyPublished(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // Language
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getLanguage(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGE), exceptionItems);

        // Languages
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getLanguages(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGES), exceptionItems);

        // Statistical Operation Instance 
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getStatisticalOperationInstances(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION_INSTANCES),
                exceptionItems);

        // Creator
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getCreator(), addParameter(metadataName, ServiceExceptionSingleParameters.CREATOR), exceptionItems);

        // Contributor 
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getContributor(), addParameter(metadataName, ServiceExceptionSingleParameters.CONTRIBUTOR), exceptionItems);

        // Publisher 
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getPublisher(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER), exceptionItems);

        // Publisher contributor 
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getPublisherContributor(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER_CONTRIBUTOR), exceptionItems);

        // Mediator 
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getMediator(), addParameter(metadataName, ServiceExceptionSingleParameters.MEDIATOR), exceptionItems);

        // Common metadata
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getCommonMetadata(), addParameter(metadataName, ServiceExceptionSingleParameters.COMMON_METADATA), exceptionItems);
    }

    private void checkRelatedResourcesPreviouslyPublished(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        // Replaces

        // Is replaced by

        // Requires

        // Is required by

        // Has part

        // Is part of

        // TODO Auto-generated method stub
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------
    public void checkVersioning(HasSiemacMetadata resource, HasSiemacMetadata previousVersion, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToPublished(resource, previousVersion, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);

        // TODO: Metadatos de relaciones entre recursos
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PROTECTED COMMON UTILS
    // ------------------------------------------------------------------------------------------------------

    private void checkSiemacMetadataAllActions(HasSiemacMetadata resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(resource, metadataName, exceptionItems);
    }
}
