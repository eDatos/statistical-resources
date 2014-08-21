package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
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
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker.ExternalItemChecker;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private ExternalItemChecker externalItemChecker;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    @Autowired
    private QueryVersionRepository queryVersionRepository;

    @Autowired
    private PublicationVersionRepository publicationVersionRepository;

    @Autowired
    private QueryService queryService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ConstraintsService constraintsService;

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
        this.checkExternalItemsPreviouslyPublished(ctx, resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);

        // No related resources to check
    }

    private void checkExternalItemsPreviouslyPublished(ServiceContext ctx, DatasetVersion resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {

        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getGeographicCoverage(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_COVERAGE),
                exceptionItems);
        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getMeasureCoverage(), addParameter(metadataName, ServiceExceptionSingleParameters.MEASURE_COVERAGE), exceptionItems);

        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getGeographicGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.GEOGRAPHIC_GRANULARITIES),
                exceptionItems);
        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getTemporalGranularities(), addParameter(metadataName, ServiceExceptionSingleParameters.TEMPORAL_GRANULARITIES),
                exceptionItems);

        if (!resource.getStatisticalUnit().isEmpty()) {
            this.externalItemChecker
                    .checkExternalItemsExternallyPublished(resource.getStatisticalUnit(), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_UNIT), exceptionItems);
        }

        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getRelatedDsd(), addParameter(metadataName, ServiceExceptionSingleParameters.RELATED_DSD), exceptionItems);

        this.externalItemChecker.checkExternalItemsExternallyPublished(resource.getUpdateFrequency(), addParameter(metadataName, ServiceExceptionSingleParameters.UPDATE_FREQUENCY), exceptionItems);

        for (Categorisation categorisation : resource.getCategorisations()) {
            this.externalItemChecker.checkExternalItemsExternallyPublished(categorisation.getCategory(), addParameter(metadataName, ServiceExceptionSingleParameters.CATEGORISATIONS), exceptionItems);
            this.externalItemChecker
                    .checkExternalItemsExternallyPublished(categorisation.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.CATEGORISATIONS), exceptionItems);
        }

        // Publish associated constraints
        this.constraintsService.publishContentConstraint(ctx, resource.getLifeCycleStatisticalResource().getUrn(), Boolean.TRUE);
    }

    @Override
    protected void applySendToPublishedCurrentResource(ServiceContext ctx, DatasetVersion resource, DatasetVersion previousResource) throws MetamacException {
        resource.setBibliographicCitation(this.buildBibliographicCitation(resource));
    }

    @Override
    protected void applySendToPublishedPreviousResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
    }

    private InternationalString buildBibliographicCitation(DatasetVersion resource) {
        ExternalItem rightsHolder = resource.getSiemacMetadataStatisticalResource().getCreator();
        String version = resource.getSiemacMetadataStatisticalResource().getVersionLogic();
        InternationalString publisherName = resource.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle();
        // This will be replaced by the API
        String publicUrl = StatisticalResourcesConstants.BIBLIOGRAPHIC_CITATION_URI_TOKEN;
        InternationalString bibliographicInternational = new InternationalString();
        String pubYear = String.valueOf(resource.getSiemacMetadataStatisticalResource().getValidFrom().getYear());
        for (LocalisedString localisedTitle : resource.getSiemacMetadataStatisticalResource().getTitle().getTexts()) {
            String locale = localisedTitle.getLocale();
            StringBuilder bibliographicCitation = new StringBuilder();
            bibliographicCitation.append(rightsHolder.getCode()).append(" (").append(pubYear).append(") ");
            bibliographicCitation.append(localisedTitle.getLabel()).append(" (v").append(version).append(") [dataset].");
            bibliographicCitation.append(" ").append(this.getLocalisedTextInLocaleOrAppDefault(publisherName, locale));
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
        this.checkDatasetThatReplaces(resource, exceptionItems);

        this.checkQueriesThatRequires(ctx, resource, exceptionItems);

        this.checkPublicationsThatHasPart(ctx, resource, exceptionItems);
    }

    private void checkPublicationsThatHasPart(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> publications = this.datasetVersionRepository.retrieveIsPartOf(resource);
        if (!publications.isEmpty()) {
            for (RelatedResourceResult publicationResult : publications) {
                PublicationVersion publicationVersion = this.publicationVersionRepository.retrieveByUrn(publicationResult.getUrn());
                if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(publicationVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus())) {
                    this.checkPublicationCanStaryNotVisibleAfterCancelDataset(resource, publicationVersion, exceptionItems);
                }
            }
        }
    }

    private void checkQueriesThatRequires(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> queries = this.datasetVersionRepository.retrieveIsRequiredBy(resource);
        if (!queries.isEmpty()) {
            for (RelatedResourceResult queryResult : queries) {
                QueryVersion queryVersion = this.queryVersionRepository.retrieveByUrn(queryResult.getUrn());
                if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(queryVersion.getLifeCycleStatisticalResource().getEffectiveProcStatus())) {
                    this.checkQueryCanStayNotVisibleAfterCancelDataset(ctx, resource, queryVersion, exceptionItems);
                }
            }
        }
    }

    private void checkQueryCanStayNotVisibleAfterCancelDataset(ServiceContext ctx, DatasetVersion resource, QueryVersion queryVersion, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        if (queryVersion.getFixedDatasetVersion() != null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_REQUIRED_BY_NOT_VISIBLE_QUERY, queryVersion.getLifeCycleStatisticalResource().getUrn()));
        } else {
            DatasetVersion lastPublishedVersion = this.datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
            boolean canStayNotVisible = false;
            if (lastPublishedVersion != null) {
                canStayNotVisible = this.queryService.checkQueryCompatibility(ctx, queryVersion, lastPublishedVersion);
            }
            if (!canStayNotVisible) {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_REQUIRED_BY_NOT_VISIBLE_QUERY, queryVersion.getLifeCycleStatisticalResource().getUrn()));
            }
        }
    }

    private void checkPublicationCanStaryNotVisibleAfterCancelDataset(DatasetVersion resource, PublicationVersion publicationVersion, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        DatasetVersion lastPublishedVersion = this.datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
        if (lastPublishedVersion == null) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_VERSION_IS_PART_OF_NOT_VISIBLE_PUBLICATION, publicationVersion.getSiemacMetadataStatisticalResource().getUrn()));
        }
    }

    protected void checkDatasetThatReplaces(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        RelatedResourceResult datasetThatReplaces = this.datasetVersionRepository.retrieveIsReplacedBy(resource);
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
            this.datasetService.initializeCategorisationMetadataForCreation(ctx, categorisation);
        }
        return newVersion;
    }

    @Override
    protected void applyVersioningNewResource(ServiceContext ctx, DatasetVersion resource, DatasetVersion previous) throws MetamacException {

        // Versioning the content constraints associated
        this.constraintsService.versioningContentConstraint(ctx, previous.getLifeCycleStatisticalResource().getUrn(), this.guessVersionTypeEnum(resource, previous));

        resource.setUserModifiedDateNextUpdate(Boolean.FALSE);
        String oldDatasetRepositoryId = previous.getDatasetRepositoryId();
        resource.setDatasetRepositoryId(resource.getSiemacMetadataStatisticalResource().getUrn());

        TaskInfoDataset taskInfo = new TaskInfoDataset();
        taskInfo.setDatasetVersionId(oldDatasetRepositoryId);
        this.taskService.planifyDuplicationDataset(ctx, taskInfo, resource.getDatasetRepositoryId());
    }

    private VersionTypeEnum guessVersionTypeEnum(DatasetVersion resource, DatasetVersion previous) {
        String[] versionPreviousParts = previous.getLifeCycleStatisticalResource().getVersionLogic().split("\\.");
        String[] versionParts = resource.getLifeCycleStatisticalResource().getVersionLogic().split("\\.");
        if (versionParts[0] != null && versionPreviousParts[0] != null && Integer.valueOf(versionParts[0]) > Integer.valueOf(versionPreviousParts[0])) {
            return VersionTypeEnum.MAJOR;
        }

        return VersionTypeEnum.MINOR;
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
        return this.datasetVersionRepository.save(resource);
    }

    @Override
    protected DatasetVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return this.datasetVersionRepository.retrieveByUrn(urn);
    }

    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return this.datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected DatasetVersion retrievePreviousPublishedResourceByResource(DatasetVersion resource) throws MetamacException {
        return this.datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        this.lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(ctx, resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

    @Override
    protected String getResourceUrn(DatasetVersion resource) {
        return resource.getSiemacMetadataStatisticalResource().getUrn();
    }
}
