package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private ExternalItemChecker            externalItemChecker;

    @Autowired
    private DatasetService                 datasetService;

    @Autowired
    private DatasetVersionRepository       datasetVersionRepository;

    @Autowired
    private QueryVersionRepository         queryVersionRepository;

    @Autowired
    private LifecycleService<QueryVersion> queryLifecycleService;

    @Autowired
    private QueryService                   queryService;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.DATASET_VERSION;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToPublishedResource(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkExternalItemsPreviouslyPublished(resource, ServiceExceptionSingleParameters.DATASET_VERSION, exceptionItems);

        // No related resources to check
    }

    private void checkExternalItemsPreviouslyPublished(DatasetVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {

        externalItemChecker.checkExternalItemsExternallyPublished(resource.getGeographicCoverage(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_COVERAGE), exceptionItems);
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getMeasureCoverage(), addParameter(metadataName, ServiceExceptionSingleParameters.MEASURE_COVERAGE), exceptionItems);

        externalItemChecker.checkExternalItemsExternallyPublished(resource.getGeographicGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_GRANULARITIES),
                exceptionItems);
        externalItemChecker.checkExternalItemsExternallyPublished(resource.getTemporalGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.TEMPORAL_GRANULARITIES),
                exceptionItems);

        if (!resource.getStatisticalUnit().isEmpty()) {
            externalItemChecker.checkExternalItemsExternallyPublished(resource.getStatisticalUnit(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_UNIT), exceptionItems);
        }

        externalItemChecker.checkExternalItemsExternallyPublished(resource.getRelatedDsd(), addParameter(metadataName, ServiceExceptionSingleParameters.RELATED_DSD), exceptionItems);

        externalItemChecker.checkExternalItemsExternallyPublished(resource.getUpdateFrequency(), addParameter(metadataName, ServiceExceptionSingleParameters.UPDATE_FREQUENCY), exceptionItems);

        for (Categorisation categorisation : resource.getCategorisations()) {
            externalItemChecker.checkExternalItemsExternallyPublished(categorisation.getCategory(), addParameter(metadataName, ServiceExceptionSingleParameters.CATEGORISATIONS), exceptionItems);
            externalItemChecker.checkExternalItemsExternallyPublished(categorisation.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.CATEGORISATIONS), exceptionItems);
        }
    }

    @Override
    protected void applySendToPublishedCurrentResource(ServiceContext ctx, DatasetVersion resource, DatasetVersion previousResource) throws MetamacException {
        resource.setBibliographicCitation(buildBibliographicCitation(resource));
        // cHANGE QUERIES
    }

    @Override
    protected void applySendToPublishedPreviousResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
    }

    private InternationalString buildBibliographicCitation(DatasetVersion resource) {
        ExternalItem rightsHolder = resource.getSiemacMetadataStatisticalResource().getCreator();
        String version = resource.getSiemacMetadataStatisticalResource().getVersionLogic();
        InternationalString publisherName = resource.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle();
        String publicUrl = StatisticalResourcesConstants.BIBLIOGRAPHIC_CITATION_URI_TOKEN; // This will be replaced by the API
        InternationalString bibliographicInternational = new InternationalString();
        String pubYear = String.valueOf(resource.getSiemacMetadataStatisticalResource().getValidFrom().getYear());
        for (LocalisedString localisedTitle : resource.getSiemacMetadataStatisticalResource().getTitle().getTexts()) {
            String locale = localisedTitle.getLocale();
            StringBuilder bibliographicCitation = new StringBuilder();
            bibliographicCitation.append(rightsHolder.getCode()).append(" (").append(pubYear).append(") ");
            bibliographicCitation.append(localisedTitle.getLabel()).append(" (v").append(version).append(") [dataset].");
            bibliographicCitation.append(" ").append(getLocalisedTextInLocaleOrAppDefault(publisherName, locale));
            bibliographicCitation.append(" (").append(publicUrl).append(")");

            LocalisedString localised = new LocalisedString(locale, bibliographicCitation.toString());
            bibliographicInternational.addText(localised);
        }
        return bibliographicInternational;
    }

    private String getLocalisedTextInLocaleOrAppDefault(InternationalString internationaString, String locale) {
        if (internationaString.getLocalisedLabel(locale) != null) {
            return internationaString.getLocalisedLabel(locale);
        } else {
            return internationaString.getLocalisedLabel(locale);
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected DatasetVersion copyResourceForVersioning(ServiceContext ctx, DatasetVersion previousResource) throws MetamacException {
        DatasetVersion newVersion = DatasetVersioningCopyUtils.copyDatasetVersion(previousResource);
        for (Categorisation categorisation : newVersion.getCategorisations()) {
            datasetService.initializeCategorisationMetadataForCreation(ctx, categorisation);
        }
        return newVersion;
    }

    @Override
    protected void applyVersioningNewResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        resource.setUserModifiedDateNextUpdate(Boolean.FALSE);
        resource.setDatasetRepositoryId(resource.getSiemacMetadataStatisticalResource().getUrn());

        // FIXME: Pendiente añadir método de duplicado en DatasetRepositoriesServiceFacade
        // It's necessary to duplicate datasetRepository and set the new id to datasetVersion
        // throw new UnsupportedOperationException("Not implemented: needed a DatasetRepositoriesServiceFacade method");
    }

    @Override
    protected void applyVersioningPreviousResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    @Override
    protected DatasetVersion updateResourceUrn(DatasetVersion resource) throws MetamacException {
        String[] creator = new String[]{resource.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};

        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(creator, resource.getSiemacMetadataStatisticalResource().getCode(), resource
                .getSiemacMetadataStatisticalResource().getVersionLogic());

        resource.getSiemacMetadataStatisticalResource().setUrn(urn);
        return resource;
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected DatasetVersion saveResource(DatasetVersion resource) {
        return datasetVersionRepository.save(resource);
    }

    @Override
    protected DatasetVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(urn);
    }

    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected DatasetVersion retrievePreviousPublishedResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

    @Override
    protected String getResourceUrn(DatasetVersion resource) {
        return resource.getSiemacMetadataStatisticalResource().getUrn();
    }
}
