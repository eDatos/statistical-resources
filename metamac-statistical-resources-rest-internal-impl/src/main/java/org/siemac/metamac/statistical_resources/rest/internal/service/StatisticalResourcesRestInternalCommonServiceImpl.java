package org.siemac.metamac.statistical_resources.rest.internal.service;

import static org.siemac.metamac.rest.exception.utils.RestExceptionUtils.checkParameterNotWildcardAll;
import static org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants.SERVICE_CONTEXT;
import static org.siemac.metamac.statistical_resources.rest.internal.service.utils.StatisticalResourcesRestInternalUtils.manageException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceProperties.LifeCycleStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceProperties.SiemacMetadataStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCriteriaUtils;
import org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants;
import org.siemac.metamac.statistical_resources.rest.internal.exception.RestServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestInternalCommonService")
public class StatisticalResourcesRestInternalCommonServiceImpl implements StatisticalResourcesRestInternalCommonService {

    private final PagingParameter pagingParameterOneResult = PagingParameter.pageAccess(1, 1, false);

    @Autowired
    private DatasetService        datasetService;

    @Autowired
    private PublicationService    publicationService;

    @Autowired
    private QueryService          queryService;

    @Override
    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version) {
        try {
            // Validations
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_RESOURCE_ID, resourceID);
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_VERSION, version);

            // Retrieve
            PagedResult<DatasetVersion> entitiesPagedResult = findDatasetVersionsCommon(agencyID, resourceID, version, null, pagingParameterOneResult);
            if (entitiesPagedResult.getValues().size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.DATASET_NOT_FOUND, resourceID, version, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            DatasetVersion datasetVersion = entitiesPagedResult.getValues().get(0);
            return datasetVersion;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public PagedResult<DatasetVersion> findDatasetVersions(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteria, PagingParameter pagingParameter) {
        try {
            return findDatasetVersionsCommon(agencyID, resourceID, version, conditionalCriteria, pagingParameter);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public PublicationVersion retrievePublicationVersion(String agencyID, String resourceID) {
        try {
            // Validations
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_RESOURCE_ID, resourceID);

            // Retrieve
            PagedResult<PublicationVersion> entitiesPagedResult = findPublicationVersionsCommon(agencyID, resourceID, null, pagingParameterOneResult);
            if (entitiesPagedResult.getValues().size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.COLLECTION_NOT_FOUND, resourceID, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            PublicationVersion publicationVersion = entitiesPagedResult.getValues().get(0);
            return publicationVersion;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public PagedResult<PublicationVersion> findPublicationVersions(String agencyID, List<ConditionalCriteria> conditionalCriteria, PagingParameter pagingParameter) {
        try {
            return findPublicationVersionsCommon(agencyID, null, conditionalCriteria, pagingParameter);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public QueryVersion retrieveQueryVersion(String agencyID, String resourceID) {
        try {
            // Validations
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestInternalConstants.PARAMETER_RESOURCE_ID, resourceID);

            // Retrieve
            PagedResult<QueryVersion> entitiesPagedResult = findQueryVersionsCommon(agencyID, resourceID, null, pagingParameterOneResult);
            if (entitiesPagedResult.getValues().size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.QUERY_NOT_FOUND, resourceID, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            QueryVersion queryVersion = entitiesPagedResult.getValues().get(0);
            return queryVersion;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public PagedResult<QueryVersion> findQueryVersions(String agencyID, List<ConditionalCriteria> conditionalCriteria, PagingParameter pagingParameter) {
        try {
            return findQueryVersionsCommon(agencyID, null, conditionalCriteria, pagingParameter);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private PagedResult<DatasetVersion> findDatasetVersionsCommon(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteriaQuery,
            PagingParameter pagingParameter) throws MetamacException {

        // Criteria to find by criteria
        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).distinctRoot().build());
        }
        addConditionalCriteriaAgencyIfApplicable(agencyID, DatasetVersion.class, DatasetVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaResourceIfApplicable(resourceID, DatasetVersion.class, DatasetVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaVersionIfApplicable(version, DatasetVersion.class, DatasetVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);

        // Find
        PagedResult<DatasetVersion> entitiesPagedResult = datasetService.findDatasetVersionsByCondition(SERVICE_CONTEXT, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }

    private PagedResult<PublicationVersion> findPublicationVersionsCommon(String agencyID, String resourceID, List<ConditionalCriteria> conditionalCriteriaQuery, PagingParameter pagingParameter)
            throws MetamacException {

        // Criteria to find by criteria
        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).distinctRoot().build());
        }
        addConditionalCriteriaAgencyIfApplicable(agencyID, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaResourceIfApplicable(resourceID, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaVersionIfApplicable(SrmRestConstants.WILDCARD_LATEST, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);

        // Find
        PagedResult<PublicationVersion> entitiesPagedResult = publicationService.findPublicationVersionsByCondition(SERVICE_CONTEXT, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }

    private PagedResult<QueryVersion> findQueryVersionsCommon(String agencyID, String resourceID, List<ConditionalCriteria> conditionalCriteriaQuery, PagingParameter pagingParameter)
            throws MetamacException {

        // Criteria to find by criteria
        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).distinctRoot().build());
        }
        addConditionalCriteriaAgencyIfApplicable(agencyID, QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaResourceIfApplicable(resourceID, QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaVersionIfApplicable(SrmRestConstants.WILDCARD_LATEST, QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);

        // Find
        PagedResult<QueryVersion> entitiesPagedResult = queryService.findQueryVersionsByCondition(SERVICE_CONTEXT, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaAgencyIfApplicable(String agencyID, Class entityClass, SiemacMetadataStatisticalResourceProperty siemacMetadataStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {
        if (agencyID != null && !SrmRestConstants.WILDCARD_ALL.equals(agencyID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(siemacMetadataStatisticalResourceProperty.maintainer().codeNested()).eq(agencyID).buildSingle());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaResourceIfApplicable(String resourceID, Class entityClass, SiemacMetadataStatisticalResourceProperty siemacMetadataStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {
        if (resourceID != null && !SrmRestConstants.WILDCARD_ALL.equals(resourceID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(siemacMetadataStatisticalResourceProperty.code()).eq(resourceID).buildSingle());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaVersionIfApplicable(String version, Class entityClass, SiemacMetadataStatisticalResourceProperty siemacMetadataStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {

        if (SrmRestConstants.WILDCARD_LATEST.equals(version)) {
            if (StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
                // Add latest restrictions Last version
                //@formatter:off
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                    .withProperty(siemacMetadataStatisticalResourceProperty.lastVersion()).eq(Boolean.TRUE)
                    .buildSingle()
                );
                // @formatter:on
            } else {
                // External API, add public restrictions and latest restrictions
                conditionalCriteria.add(StatisticalResourcesCriteriaUtils.buildLastPublishedVersionCriteria(entityClass, siemacMetadataStatisticalResourceProperty));
            }
        } else if (version != null && !SrmRestConstants.WILDCARD_ALL.equals(version)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(siemacMetadataStatisticalResourceProperty.versionLogic()).eq(version).buildSingle());
        }

        // if is a external API and not is latest search, add public restrictions (in query of latest the restrictions is already added)
        if (!SrmRestConstants.WILDCARD_LATEST.equals(version)) {
            if (!StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
                DateTime now = new DateTime();
                //@formatter:off
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                        .withProperty(siemacMetadataStatisticalResourceProperty.validFrom()).lessThanOrEqual(now)
                        .buildSingle());
    
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                        .withProperty(siemacMetadataStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED)
                        .buildSingle());
                // @formatter:on
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaAgencyIfApplicable(String agencyID, Class entityClass, LifeCycleStatisticalResourceProperty lifeCycleStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {
        if (agencyID != null && !SrmRestConstants.WILDCARD_ALL.equals(agencyID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(lifeCycleStatisticalResourceProperty.maintainer().codeNested()).eq(agencyID).buildSingle());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaResourceIfApplicable(String resourceID, Class entityClass, LifeCycleStatisticalResourceProperty lifeCycleStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {
        if (resourceID != null && !SrmRestConstants.WILDCARD_ALL.equals(resourceID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(lifeCycleStatisticalResourceProperty.code()).eq(resourceID).buildSingle());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void addConditionalCriteriaVersionIfApplicable(String version, Class entityClass, LifeCycleStatisticalResourceProperty lifeCycleStatisticalResourceProperty,
            List<ConditionalCriteria> conditionalCriteria) {

        if (SrmRestConstants.WILDCARD_LATEST.equals(version)) {
            if (StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
                // External API, add latest restrictions Last version
                //@formatter:off
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                    .withProperty(lifeCycleStatisticalResourceProperty.lastVersion()).eq(Boolean.TRUE)
                    .buildSingle()
                );
                // @formatter:on
            } else {
                // External API, add public restrictions and latest restrictions
                conditionalCriteria.add(StatisticalResourcesCriteriaUtils.buildLastPublishedVersionCriteria(entityClass, lifeCycleStatisticalResourceProperty));
            }
        } else if (version != null && !SrmRestConstants.WILDCARD_ALL.equals(version)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(lifeCycleStatisticalResourceProperty.versionLogic()).eq(version).buildSingle());
        }

        // if is a external API and not is latest search add public restrictions (in query of latest the restrictions is already added)
        if (!SrmRestConstants.WILDCARD_LATEST.equals(version)) {
            if (!StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
                DateTime now = new DateTime();
                //@formatter:off
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                        .withProperty(lifeCycleStatisticalResourceProperty.validFrom()).lessThanOrEqual(now)
                        .buildSingle());
    
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                        .withProperty(lifeCycleStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED)
                        .buildSingle());
                // @formatter:on
            }
        }
    }
}
