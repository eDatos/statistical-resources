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
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
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
    private PublicationVersionRepository   publicationVersionRepository;

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
    // >> CANCEL PUBLICATION
    // ------------------------------------------------------------------------------------------------------
    @Override
    protected void checkCancelPublicationResource(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkDatasetThatReplaces(resource, exceptionItems);

        checkQueriesThatRequires(ctx, resource, exceptionItems);

        checkPublicationsThatHasPart(ctx, resource, exceptionItems);
    }

    private void checkPublicationsThatHasPart(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> publications = datasetVersionRepository.retrieveIsPartOf(resource);
        if (!publications.isEmpty()) {
            for (RelatedResourceResult publicationResult : publications) {
                PublicationVersion publicationVersion = publicationVersionRepository.retrieveByUrn(publicationResult.getUrn());
                if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(publicationVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus())) {
                    checkPublicationCanStaryNotVisibleAfterCancelDataset(ctx, resource, publicationVersion, exceptionItems);
                }
            }
        }
    }

    private void checkQueriesThatRequires(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> queries = datasetVersionRepository.retrieveIsRequiredBy(resource);
        if (!queries.isEmpty()) {
            for (RelatedResourceResult queryResult : queries) {
                QueryVersion queryVersion = queryVersionRepository.retrieveByUrn(queryResult.getUrn());
                if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(queryVersion.getLifeCycleStatisticalResource().getEffectiveProcStatus())) {
                    checkQueryCanStaryNotVisibleAfterCancelDataset(ctx, resource, queryVersion, exceptionItems);
                }
            }
        }
    }

    private void checkQueryCanStaryNotVisibleAfterCancelDataset(ServiceContext ctx, DatasetVersion resource, QueryVersion queryVersion, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        if (queryVersion.getFixedDatasetVersion() != null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_REQUIRED_BY_NOT_VISIBLE_QUERY, queryVersion.getLifeCycleStatisticalResource().getUrn()));
        } else {
            DatasetVersion lastPublishedVersion = datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
            boolean canStayNotVisible = false;
            if (lastPublishedVersion != null) {
                canStayNotVisible = queryService.checkQueryCompatibility(ctx, queryVersion, lastPublishedVersion);
            }
            if (!canStayNotVisible) {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_REQUIRED_BY_NOT_VISIBLE_QUERY, queryVersion.getLifeCycleStatisticalResource().getUrn()));
            }
        }
    }

    private void checkPublicationCanStaryNotVisibleAfterCancelDataset(ServiceContext ctx, DatasetVersion resource, PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        DatasetVersion lastPublishedVersion = datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
        if (lastPublishedVersion == null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_PART_OF_NOT_VISIBLE_PUBLICATION, publicationVersion.getSiemacMetadataStatisticalResource().getUrn()));
        }
    }

    protected void checkDatasetThatReplaces(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        RelatedResourceResult datasetThatReplaces = datasetVersionRepository.retrieveIsReplacedBy(resource);
        if (datasetThatReplaces != null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_REPLACED_BY_NOT_VISIBLE, datasetThatReplaces.getUrn()));
        }
    }

    @Override
    protected void applyCancelPublicationCurrentResource(ServiceContext ctx, DatasetVersion resource, DatasetVersion previousResource) throws MetamacException {
        resource.setBibliographicCitation(null);
    }

    @Override
    protected void applyCancelPublicationPreviousResource(ServiceContext ctx, DatasetVersion previousResource) throws MetamacException {
        // NOTHING
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
