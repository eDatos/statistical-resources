package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.ProcStatusValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.validators.QueryServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.query.serviceimpl.validators.QueryConstraintValidator;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of QueryService.
 */
@Service("queryService")
public class QueryServiceImpl extends QueryServiceImplBase {

    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private QueryServiceInvocationValidator           queryServiceInvocationValidator;

    @Autowired
    private DatasetVersionRepository                  datasetVersionRepository;

    public QueryServiceImpl() {
    }

    @Override
    public QueryVersion retrieveQueryVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersionByUrn(ctx, urn);

        // Retrieve
        QueryVersion query = getQueryVersionRepository().retrieveByUrn(urn);
        return query;
    }

    @Override
    public List<QueryVersion> retrieveQueryVersions(ServiceContext ctx) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersions(ctx);

        // Retrieve
        List<QueryVersion> queries = getQueryVersionRepository().findAll();
        return queries;
    }

    @Override
    public PagedResult<QueryVersion> findQueryVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkFindQueryVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, Query.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<QueryVersion> queryPagedResult = getQueryVersionRepository().findByCondition(conditions, pagingParameter);
        return queryPagedResult;

    }

    @Override
    public QueryVersion createQueryVersion(ServiceContext ctx, QueryVersion queryVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkCreateQueryVersion(ctx, queryVersion, statisticalOperation);

        // Create query
        Query query = new Query();
        fillMetadataForCreateQuery(query, queryVersion, statisticalOperation);

        // Fill metadata
        fillMetadataForCreateQueryVersion(ctx, queryVersion, statisticalOperation);

        // Check unique URN
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(query.getIdentifiableStatisticalResource());

        // Checks
        // TODO: Comprobar si hay que hacer alguno más
        // TODO: Comprobar si pueden ser comunes al resto de artefactos

        // TODO: Check compatibility with dataset version

        // Save query
        query = getQueryRepository().save(query);

        queryVersion.setQuery(query);
        queryVersion = getQueryVersionRepository().save(queryVersion);
        return queryVersion;
    }

    @Override
    public QueryVersion updateQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkUpdateQueryVersion(ctx, queryVersion);

        // Check procStatus
        ProcStatusValidator.checkQueryVersionCanBeEdited(queryVersion);

        // Fill metadata
        fillMetadataForUpdateQueryVersion(queryVersion);

        // Check that could be update
        // TODO: Comprobar si hay que hacer alguno más
        // Check URN duplicated. We have to do it right now because later the fillMetadata method change the hibernate cache
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());

        // Repository operation
        return getQueryVersionRepository().save(queryVersion);

    }

    @Override
    public QueryVersion markQueryVersionAsDiscontinued(ServiceContext ctx, QueryVersion query) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkMarkQueryVersionAsDiscontinued(ctx, query);

        // Retrieve entity
        query = retrieveQueryVersionByUrn(ctx, query.getLifeCycleStatisticalResource().getUrn());

        // Check that query is pending_review
        QueryConstraintValidator.checkQueryVersionForMarkAsDiscontinued(query);

        // Change status
        query.setStatus(QueryStatusEnum.DISCONTINUED);

        return getQueryVersionRepository().save(query);
    }

    @Override
    public void deleteQueryVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkDeleteQueryVersion(ctx, urn);

        // Retrieve entity
        QueryVersion queryVersion = retrieveQueryVersionByUrn(ctx, urn);

        // Check that query is with a correct procStatus
        ProcStatusValidator.checkStatisticalResourceCanBeDeleted(queryVersion);

        if (VersionUtil.isInitialVersion(queryVersion.getLifeCycleStatisticalResource().getVersionLogic())) {
            Query query = queryVersion.getQuery();
            getQueryRepository().delete(query);
        } else {
            // Delete
            getQueryVersionRepository().delete(queryVersion);
        }
    }

    private void fillMetadataForCreateQuery(Query query, QueryVersion queryVersion, ExternalItem statisticalOperation) throws MetamacException {
        query.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        query.getIdentifiableStatisticalResource().setCode(queryVersion.getLifeCycleStatisticalResource().getCode());

        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode());
        query.getIdentifiableStatisticalResource().setUrn(urn);

        FillMetadataForCreateResourceUtils.fillMetadataForCreateIdentifiableResource(query.getIdentifiableStatisticalResource(), statisticalOperation);
    }

    private void fillMetadataForCreateQueryVersion(ServiceContext ctx, QueryVersion queryVersion, ExternalItem statisticalOperation) throws MetamacException {
        FillMetadataForCreateResourceUtils.fillMetadataForCreateLifeCycleResource(queryVersion.getLifeCycleStatisticalResource(), statisticalOperation, ctx);
        queryVersion.setStatus(determineQueryStatus(queryVersion));
        queryVersion.setLatestTemporalCodeInCreation(determineLatestTemporalCodeInCreation(queryVersion));
        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(), queryVersion
                .getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private void fillMetadataForUpdateQueryVersion(QueryVersion queryVersion) throws MetamacException {
        queryVersion.setStatus(determineQueryStatus(queryVersion));
        queryVersion.setLatestTemporalCodeInCreation(determineLatestTemporalCodeInCreation(queryVersion));

        // Update URN
        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(), queryVersion
                .getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private QueryStatusEnum determineQueryStatus(QueryVersion queryVersion) throws MetamacException {
        if (datasetVersionRepository.isLastVersion(queryVersion.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())) {
            return QueryStatusEnum.ACTIVE;
        } else {
            return QueryStatusEnum.DISCONTINUED;
        }
    }

    private String determineLatestTemporalCodeInCreation(QueryVersion queryVersion) {
        if (QueryTypeEnum.AUTOINCREMENTAL.equals(queryVersion.getType())) {
            List<TemporalCode> temporalCodes = queryVersion.getDatasetVersion().getTemporalCoverage();
            List<String> timeCodes = new ArrayList<String>();
            StatisticalResourcesCollectionUtils.temporalCodesToTimeCodes(temporalCodes, timeCodes);
            List<String> sortedCodes = SdmxTimeUtils.sortTimeList(timeCodes);
            return sortedCodes.get(0);
        }
        return null;
    }

    @Override
    public QueryVersion retrieveLatestQueryVersionByQueryUrn(ServiceContext ctx, String queryUrn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveLatestQueryVersionByQueryUrn(ctx, queryUrn);

        // Retrieve
        QueryVersion queryVersion = getQueryVersionRepository().retrieveLastVersion(queryUrn);
        return queryVersion;
    }

    @Override
    public QueryVersion retrieveLatestPublishedQueryVersionByQueryUrn(ServiceContext ctx, String queryUrn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveLatestPublishedQueryVersionByQueryUrn(ctx, queryUrn);

        // Retrieve
        QueryVersion queryVersion = getQueryVersionRepository().retrieveLastPublishedVersion(queryUrn);
        return queryVersion;
    }
}
