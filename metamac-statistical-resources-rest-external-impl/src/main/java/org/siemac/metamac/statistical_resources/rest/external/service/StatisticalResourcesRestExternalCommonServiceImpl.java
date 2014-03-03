package org.siemac.metamac.statistical_resources.rest.external.service;

import static org.siemac.metamac.rest.exception.utils.RestExceptionUtils.checkParameterNotWildcardAll;
import static org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants.SERVICE_CONTEXT;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.manageException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceProperties.LifeCycleStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceProperties.SiemacMetadataStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.PublicationService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalCommonService")
public class StatisticalResourcesRestExternalCommonServiceImpl implements StatisticalResourcesRestExternalCommonService {

    private final PagingParameter        pagingParameterOneResult = PagingParameter.pageAccess(1, 1, false);

    @Autowired
    private DatasetService               datasetService;

    @Autowired
    private PublicationService           publicationService;

    @Autowired
    private QueryService                 queryService;

    @Autowired
    private QueryVersionRepository       queryVersionRepository;

    @Autowired
    private PublicationVersionRepository publicationVersionRepository;

    @Override
    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version) {
        try {
            // Validations
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_VERSION, version);

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
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);

            // Retrieve last version published
            String[] maintainerCodes = StringUtils.splitPreserveAllTokens(agencyID, UrnConstants.DOT);
            String publicationUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionUrn(maintainerCodes, resourceID);
            PublicationVersion publicationVersion = null;
            try {
                publicationVersion = publicationVersionRepository.retrieveLastVersion(publicationUrn); // TODO retrieveLastPublishedVersion, y revisar tipo de excepción en catch (METAMAC-1851)
            } catch (MetamacException e) {
                if (e.getExceptionItems().size() == 1 && ServiceExceptionType.PUBLICATION_LAST_VERSION_NOT_FOUND.getCode().equals(e.getExceptionItems().get(0).getCode())) {
                    publicationVersion = null;
                } else {
                    throw e;
                }
            }
            if (publicationVersion == null) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.COLLECTION_NOT_FOUND, resourceID, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            return publicationVersion;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public PagedResult<PublicationVersion> findPublicationVersions(String agencyID, List<ConditionalCriteria> conditionalCriteria, PagingParameter pagingParameter) {
        try {
            return findPublicationVersionsCommon(agencyID, null, null, conditionalCriteria, pagingParameter);
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public QueryVersion retrieveQueryVersion(String agencyID, String resourceID) {
        try {
            // Validations
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(StatisticalResourcesRestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);

            // Retrieve last version published
            String[] maintainerCodes = StringUtils.splitPreserveAllTokens(agencyID, UrnConstants.DOT);
            String queryUrn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(maintainerCodes, resourceID);
            QueryVersion queryVersion = null;
            try {
                queryVersion = queryVersionRepository.retrieveLastVersion(queryUrn);// TODO retrieveLastPublishedVersion, y revisar tipo de excepción en catch (METAMAC-1851)
            } catch (MetamacException e) {
                if (e.getExceptionItems().size() == 1 && ServiceExceptionType.QUERY_LAST_VERSION_NOT_FOUND.getCode().equals(e.getExceptionItems().get(0).getCode())) {
                    queryVersion = null;
                } else {
                    throw e;
                }
            }
            if (queryVersion == null) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.QUERY_NOT_FOUND, resourceID, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
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
        // TODO publicados y con fecha de validez posterior a ahora (METAMAC-1851)

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

    private PagedResult<PublicationVersion> findPublicationVersionsCommon(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteriaQuery,
            PagingParameter pagingParameter) throws MetamacException {

        // Criteria to find by criteria
        // TODO publicados y con fecha de validez posterior a ahora (METAMAC-1851)

        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).distinctRoot().build());
        }
        addConditionalCriteriaAgencyIfApplicable(agencyID, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaResourceIfApplicable(resourceID, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaVersionIfApplicable(version, PublicationVersion.class, PublicationVersionProperties.siemacMetadataStatisticalResource(), conditionalCriteria);

        // Find
        PagedResult<PublicationVersion> entitiesPagedResult = publicationService.findPublicationVersionsByCondition(SERVICE_CONTEXT, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }

    private PagedResult<QueryVersion> findQueryVersionsCommon(String agencyID, String resourceID, List<ConditionalCriteria> conditionalCriteriaQuery, PagingParameter pagingParameter)
            throws MetamacException {

        // Criteria to find by criteria
        // TODO publicados y con fecha de validez posterior a ahora (METAMAC-1851)

        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).distinctRoot().build());
        }
        addConditionalCriteriaAgencyIfApplicable(agencyID, QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);
        addConditionalCriteriaResourceIfApplicable(resourceID, QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);
        // addConditionalCriteriaVersionIfApplicable(QueryVersion.class, QueryVersionProperties.lifeCycleStatisticalResource(), conditionalCriteria);

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
            // TODO Latest (METAMAC-1851)cambiar el codigo que se ejecuta en cada api
            boolean isInternalApi = false;
            if (isInternalApi) {
                // Last version
                //@formatter:off
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                    .withProperty(siemacMetadataStatisticalResourceProperty.lastVersion()).eq(Boolean.TRUE)
                    .buildSingle()
                );
                // @formatter:on
            } else {
                // Last published version
                //@formatter:off
                DateTime now = new DateTime();
                conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass)
                        .withProperty(siemacMetadataStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED)
                    .and()
                        .withProperty(siemacMetadataStatisticalResourceProperty.validFrom()).lessThanOrEqual(now)
                    .and()
                        .lbrace()
                        .withProperty(siemacMetadataStatisticalResourceProperty.validTo()).greaterThan(now)
                        .or()
                        .withProperty(siemacMetadataStatisticalResourceProperty.validTo()).isNull()
                        .rbrace()
                    .buildSingle()
                );
                // @formatter:on
            }
        } else if (version != null && !SrmRestConstants.WILDCARD_ALL.equals(version)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(entityClass).withProperty(siemacMetadataStatisticalResourceProperty.versionLogic()).eq(version).buildSingle());
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
}
