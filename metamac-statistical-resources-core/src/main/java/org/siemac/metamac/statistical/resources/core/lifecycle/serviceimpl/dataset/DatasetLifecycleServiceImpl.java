package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker             lifecycleCommonMetadataChecker;

    @Autowired
    private SiemacLifecycleChecker                     siemacLifecycleChecker;

    @Autowired
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    @Autowired
    private DatasetVersionRepository                   datasetVersionRepository;

    /*
     * @Autowired
     * private SrmRestInternalService srmRestInternalService;
     */

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // CHECK all dsd related info
    }

    @Override
    protected void checkSendToProductionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToProductionValidation(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO: Compute fields
        // Access Srm internal API and get information to fill:
        // GEOGRAPHIC_COVERAGE
        // TEMPORAL_COVERAGE
        // MEASURES

        // Access data and compute:
        // DATE_START
        // DATE_END
        // FORMAT_EXTENT_OBSERVATIONS
        // FORMAT_EXTENT_DIMENSIONS
    }

    @Override
    protected void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToProductionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void checkSendToDiffusionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToDiffusionValidation(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToDiffusionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void checkSendToValidationRejectedLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToValidationRejected(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToValidationRejectedLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleChecker.applySendToValidationRejectedActions(ctx, resource);
    }

    private InternationalString buildBibliographicCitation(DatasetVersion resource) {
        ExternalItem rightsHolder = resource.getSiemacMetadataStatisticalResource().getRightsHolder();
        String version = resource.getSiemacMetadataStatisticalResource().getVersionLogic();
        InternationalString publisherName = resource.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle();
        // FIXME: GET PUBLIC URL
        String publicUrl = "http://";
        InternationalString bibliographicInternational = new InternationalString();
        for (LocalisedString localisedTitle : resource.getSiemacMetadataStatisticalResource().getTitle().getTexts()) {
            String locale = localisedTitle.getLocale();

            StringBuilder bibliographicCitation = new StringBuilder();
            bibliographicCitation.append(rightsHolder.getCode()).append(" (").append("").append(") ");
            bibliographicCitation.append(localisedTitle.getLabel()).append(" (v").append(version).append(") [dataset].");
            bibliographicCitation.append(getLocalisedTextInLocaleOrAppDefault(publisherName, locale));
            bibliographicCitation.append(" ").append(publicUrl);

            LocalisedString localised = new LocalisedString(locale, bibliographicCitation.toString());
            bibliographicInternational.addText(localised);
        }
        return bibliographicInternational;
    }

    private String getLocalisedTextInLocaleOrAppDefault(InternationalString internationaString, String locale) {
        if (internationaString.getLocalisedLabel(locale) != null) {
            return internationaString.getLocalisedLabel(locale);
        } else {
            // FIXME: Choose other locale
            return internationaString.getLocalisedLabel(locale);
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToPublishedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented"); 

    }
    
    @Override
    protected void checkSendToPublishedLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------
    @Override
    protected LifecycleInvocationValidatorBase<DatasetVersion> getInvocationValidator() {
        return datasetLifecycleServiceInvocationValidator;
    }

    @Override
    protected DatasetVersion saveResource(DatasetVersion resource) {
        return datasetVersionRepository.save(resource);
    }

    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

}
