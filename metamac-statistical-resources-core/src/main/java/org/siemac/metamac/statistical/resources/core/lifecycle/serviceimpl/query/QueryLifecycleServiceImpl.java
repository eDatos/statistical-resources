package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import static org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants.METHOD_NOT_IMPLEMENT_IN_THIS_VERSION;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.query.QueryLifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical.resources.core.query.utils.QueryVersioningCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryLifecycleService")
public class QueryLifecycleServiceImpl extends LifecycleTemplateService<QueryVersion> implements QueryLifecycleService {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private QueryVersionRepository         queryVersionRepository;

    @Autowired
    private PublicationVersionRepository   publicationVersionRepository;

    @Autowired
    private QueryService                   queryService;

    @Autowired
    private DatasetVersionRepository       datasetVersionRepository;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.QUERY_VERSION;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // nothing specific to check
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void applySendToPublishedCurrentResource(ServiceContext ctx, QueryVersion resource, QueryVersion previousResource) throws MetamacException {
        // nothing to do
    }

    @Override
    protected void applySendToPublishedPreviousResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        throw new UnsupportedOperationException("Can not publish a query with previous published version");
    }

    @Override
    protected void checkSendToPublishedResource(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (!QueryStatusEnum.ACTIVE.equals(resource.getStatus()) && !QueryStatusEnum.DISCONTINUED.equals(resource.getStatus())) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_PUBLISH_INVALID_STATUS, resource.getLifeCycleStatisticalResource().getUrn()));
        }
        checkLinkedDatasetOrDatasetVersionPublishedForQuery(ctx, resource, exceptionItems);
    }

    @Override
    public void checkLinkedDatasetOrDatasetVersionPublishedBeforeQuery(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        if (ProcStatusEnumUtils.isInAnyProcStatus(resource, ProcStatusEnum.PUBLISHED, ProcStatusEnum.PUBLISHED_NOT_VISIBLE)) {
            List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
            checkLinkedDatasetOrDatasetVersionPublishedForQuery(ctx, resource, exceptionItems);
            ExceptionUtils.throwIfException(exceptionItems);
        }
    }

    private void checkLinkedDatasetOrDatasetVersionPublishedForQuery(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        if (resource.getDataset() != null) {
            checkLinkedDatasetPublishedOrVisibleBeforeQuery(ctx, resource, exceptionItems);
        } else if (resource.getFixedDatasetVersion() != null) {
            checkLinkedDatasetVersionPublishedOrVisibleForQuery(resource, exceptionItems);
        } else {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_PUBLISH_MUST_LINK_TO_DATASET, resource.getLifeCycleStatisticalResource().getUrn()));
        }
    }

    private void checkLinkedDatasetPublishedOrVisibleBeforeQuery(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        String datasetUrn = resource.getDataset().getIdentifiableStatisticalResource().getUrn();
        DatasetVersion lastVersion = datasetVersionRepository.retrieveLastVersion(datasetUrn);

        if (!isDatasetVersionPublishedAndVisibleBeforeOrEqualDate(lastVersion, resource.getLifeCycleStatisticalResource().getValidFrom())) {
            DatasetVersion lastPublishedVersion = datasetVersionRepository.retrieveLastPublishedVersion(datasetUrn);
            if (lastPublishedVersion != null) {
                if (!queryService.checkQueryCompatibility(ctx, resource, lastPublishedVersion)) {
                    exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_NOT_COMPATIBLE_WITH_LAST_PUBLISHED_DATASET_VERSION, datasetUrn));
                }
            } else {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_DATASET_WITH_NO_PUBLISHED_VERSION, datasetUrn));
            }
        }
    }

    private void checkLinkedDatasetVersionPublishedOrVisibleForQuery(QueryVersion resource, List<MetamacExceptionItem> exceptionItems) {
        String datasetVersionUrn = resource.getFixedDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
        DatasetVersion datasetVersion = resource.getFixedDatasetVersion();

        if (!isDatasetVersionPublishedAndVisibleBeforeOrEqualDate(datasetVersion, resource.getLifeCycleStatisticalResource().getValidFrom())) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_DATASET_VERSION_MUST_BE_PUBLISHED, datasetVersionUrn));
        }
    }

    private boolean isDatasetVersionPublishedAndVisibleBeforeOrEqualDate(DatasetVersion datasetVersion, DateTime date) {
        if (ProcStatusEnumUtils.isInAnyProcStatus(datasetVersion, ProcStatusEnum.PUBLISHED)) {
            return true;
        }
        if (ProcStatusEnumUtils.isInAnyProcStatus(datasetVersion, ProcStatusEnum.PUBLISHED_NOT_VISIBLE)) {
            if (!datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom().isAfter(date)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> CANCEL PUBLICATION
    // ------------------------------------------------------------------------------------------------------
    @Override
    protected void checkCancelPublicationResource(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkPublicationsThatHasPart(ctx, resource, exceptionItems);
    }

    private void checkPublicationsThatHasPart(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> publications = queryVersionRepository.retrieveIsPartOf(resource);
        if (!publications.isEmpty()) {
            for (RelatedResourceResult publicationResult : publications) {
                PublicationVersion publicationVersion = publicationVersionRepository.retrieveByUrn(publicationResult.getUrn());
                if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(publicationVersion.getSiemacMetadataStatisticalResource().getEffectiveProcStatus())) {
                    exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_IS_PART_OF_NOT_VISIBLE_PUBLICATION, publicationVersion.getSiemacMetadataStatisticalResource()
                            .getUrn()));
                }
            }
        }
    }

    @Override
    protected void applyCancelPublicationCurrentResource(ServiceContext ctx, QueryVersion resource, QueryVersion previousResource) throws MetamacException {
        // Nothing
    }

    @Override
    protected void applyCancelPublicationPreviousResource(ServiceContext ctx, QueryVersion previousResource) throws MetamacException {
        // nothing
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningResource(QueryVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        throw new UnsupportedOperationException(METHOD_NOT_IMPLEMENT_IN_THIS_VERSION);
    }

    @Override
    protected QueryVersion copyResourceForVersioning(ServiceContext ctx, QueryVersion previousResource) throws MetamacException {
        return QueryVersioningCopyUtils.copyQueryVersion(previousResource);
    }

    @Override
    protected void applyVersioningNewResource(ServiceContext ctx, QueryVersion resource, QueryVersion previous) throws MetamacException {
        throw new UnsupportedOperationException(METHOD_NOT_IMPLEMENT_IN_THIS_VERSION);
    }

    @Override
    protected void applyVersioningPreviousResource(ServiceContext ctx, QueryVersion resource) throws MetamacException {
        throw new UnsupportedOperationException(METHOD_NOT_IMPLEMENT_IN_THIS_VERSION);
    }

    @Override
    protected QueryVersion updateResourceUrn(QueryVersion resource) throws MetamacException {
        String[] creator = new String[]{resource.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        resource.getLifeCycleStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(creator, resource.getLifeCycleStatisticalResource().getCode(), resource.getLifeCycleStatisticalResource()
                        .getVersionLogic()));
        return resource;
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected QueryVersion saveResource(QueryVersion resource) {
        return queryVersionRepository.save(resource);
    }

    @Override
    protected QueryVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return queryVersionRepository.retrieveByUrn(urn);
    }

    @Override
    protected QueryVersion retrieveResourceByResource(QueryVersion resource) throws MetamacException {
        return queryVersionRepository.retrieveByUrn(resource.getLifeCycleStatisticalResource().getUrn());
    }

    @Override
    protected QueryVersion retrievePreviousPublishedResourceByResource(QueryVersion resource) throws MetamacException {
        return queryVersionRepository.retrieveLastPublishedVersion(resource.getQuery().getIdentifiableStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, QueryVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkQueryVersionCommonMetadata(resource, ServiceExceptionParameters.QUERY_VERSION, exceptions);
    }

    @Override
    protected String getResourceUrn(QueryVersion resource) {
        return resource.getLifeCycleStatisticalResource().getUrn();
    }

}