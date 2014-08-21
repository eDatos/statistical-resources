package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.core.common.util.predicates.ObjectEqualsStringFieldPredicate;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.core.common.util.transformers.MetamacTransformer;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.ProcStatusValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimensionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.query.QueryLifecycleService;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.validators.QueryServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.siemac.metamac.statistical.resources.core.utils.transformers.CodeDimensionToCodeStringTransformer;
import org.siemac.metamac.statistical.resources.core.utils.transformers.CodeItemToCodeStringTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.siemac.metamac.core.common.util.MetamacCollectionUtils.find;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getUrnsFromRelatedResourceResults;

/**
 * Implementation of QueryService.
 */
@Service("queryService")
public class QueryServiceImpl extends QueryServiceImplBase {

    @Autowired
    private IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    private QueryServiceInvocationValidator queryServiceInvocationValidator;

    @Autowired
    private CodeDimensionRepository codeDimensionRepository;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    @Autowired
    private QueryVersionRepository queryVersionRepository;

    @Autowired
    private QueryLifecycleService queryLifecycleService;

    public QueryServiceImpl() {
    }

    @Override
    public QueryVersion retrieveQueryVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkRetrieveQueryVersionByUrn(ctx, urn);

        // Retrieve
        QueryVersion query = this.getQueryVersionRepository().retrieveByUrn(urn);
        return query;
    }

    @Override
    public List<QueryVersion> retrieveQueryVersions(ServiceContext ctx) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkRetrieveQueryVersions(ctx);

        // Retrieve
        List<QueryVersion> queries = this.getQueryVersionRepository().findAll();
        return queries;
    }

    @Override
    public PagedResult<QueryVersion> findQueryVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkFindQueryVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, Query.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<QueryVersion> queryPagedResult = this.getQueryVersionRepository().findByCondition(conditions, pagingParameter);
        return queryPagedResult;
    }

    @Override
    public QueryVersion createQueryVersion(ServiceContext ctx, QueryVersion queryVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkCreateQueryVersion(ctx, queryVersion, statisticalOperation);

        // Create query
        Query query = new Query();
        this.fillMetadataForCreateQuery(query, queryVersion, statisticalOperation);

        // Fill metadata
        this.fillMetadataForCreateQueryVersion(ctx, queryVersion, statisticalOperation);

        // Check unique URN
        this.identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());
        this.identifiableStatisticalResourceRepository.checkDuplicatedUrn(query.getIdentifiableStatisticalResource());

        this.checkQueryCompatibility(ctx, queryVersion);

        // Save query
        query = this.getQueryRepository().save(query);

        queryVersion.setQuery(query);
        queryVersion = this.getQueryVersionRepository().save(queryVersion);
        return queryVersion;
    }

    @Override
    public QueryVersion updateQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkUpdateQueryVersion(ctx, queryVersion);

        // Check procStatus
        ProcStatusValidator.checkQueryVersionCanBeEdited(queryVersion);

        // Fill metadata
        this.fillMetadataForUpdateQueryVersion(queryVersion);

        // Check that datasetVersion is published if the query version already is
        this.queryLifecycleService.checkLinkedDatasetOrDatasetVersionPublishedBeforeQuery(ctx, queryVersion);

        // Check URN duplicated. We have to do it right now because later the fillMetadata method change the hibernate cache
        this.identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());

        this.checkQueryCompatibility(ctx, queryVersion);

        // Repository operation
        return this.getQueryVersionRepository().save(queryVersion);

    }

    private void checkQueryCompatibility(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        DatasetVersion datasetVersion = this.getCurrentDatasetVersionInQuery(queryVersion);
        if (!this.checkQueryCompatibility(ctx, queryVersion, datasetVersion)) {
            throw new MetamacException(ServiceExceptionType.QUERY_VERSION_NOT_COMPATIBLE_WITH_DATASET, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    public void deleteQueryVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkDeleteQueryVersion(ctx, urn);

        // Retrieve entity
        QueryVersion queryVersion = this.retrieveQueryVersionByUrn(ctx, urn);

        // Check that query is with a correct procStatus
        ProcStatusValidator.checkStatisticalResourceCanBeDeleted(queryVersion);

        this.checkCanQueryVersionBeDeleted(queryVersion);

        if (VersionUtil.isInitialVersion(queryVersion.getLifeCycleStatisticalResource().getVersionLogic())) {
            Query query = queryVersion.getQuery();
            this.getQueryRepository().delete(query);
        } else {
            // Delete
            this.getQueryVersionRepository().delete(queryVersion);
        }
    }

    private void checkCanQueryVersionBeDeleted(QueryVersion queryVersion) throws MetamacException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        List<RelatedResourceResult> resourcesIsPartOf = this.queryVersionRepository.retrieveIsPartOf(queryVersion);
        if (!resourcesIsPartOf.isEmpty()) {
            List<String> urns = getUrnsFromRelatedResourceResults(resourcesIsPartOf);
            Collections.sort(urns);
            String parameter = StringUtils.join(urns, ", ");
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_IS_PART_OF_OTHER_RESOURCES, parameter));
        }

        if (exceptionItems.size() > 0) {
            MetamacExceptionItem item = new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_CANT_BE_DELETED, queryVersion.getLifeCycleStatisticalResource().getUrn());
            item.setExceptionItems(exceptionItems);
            throw new MetamacException(Arrays.asList(item));
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
        queryVersion.setStatus(this.determineQueryStatus(queryVersion));
        queryVersion.setLatestTemporalCodeInCreation(this.determineLatestTemporalCodeInCreation(queryVersion));
        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(), queryVersion
                .getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private void fillMetadataForUpdateQueryVersion(QueryVersion queryVersion) throws MetamacException {
        queryVersion.setStatus(this.determineQueryStatus(queryVersion));
        queryVersion.setLatestTemporalCodeInCreation(this.determineLatestTemporalCodeInCreation(queryVersion));

        // Update URN
        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(), queryVersion
                .getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private QueryStatusEnum determineQueryStatus(QueryVersion queryVersion) throws MetamacException {
        DatasetVersion datasetVersion = this.getCurrentDatasetVersionInQuery(queryVersion);
        if (this.datasetVersionRepository.isLastVersion(datasetVersion.getSiemacMetadataStatisticalResource().getUrn())) {
            return QueryStatusEnum.ACTIVE;
        } else {
            return QueryStatusEnum.DISCONTINUED;
        }
    }

    private String determineLatestTemporalCodeInCreation(QueryVersion queryVersion) throws MetamacException {
        if (QueryTypeEnum.AUTOINCREMENTAL.equals(queryVersion.getType())) {
            DatasetVersion datasetVersion = this.getCurrentDatasetVersionInQuery(queryVersion);
            List<TemporalCode> temporalCodes = datasetVersion.getTemporalCoverage();
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
        this.queryServiceInvocationValidator.checkRetrieveLatestQueryVersionByQueryUrn(ctx, queryUrn);

        // Retrieve
        QueryVersion queryVersion = this.getQueryVersionRepository().retrieveLastVersion(queryUrn);
        return queryVersion;
    }

    @Override
    public QueryVersion retrieveLatestPublishedQueryVersionByQueryUrn(ServiceContext ctx, String queryUrn) throws MetamacException {
        // Validations
        this.queryServiceInvocationValidator.checkRetrieveLatestPublishedQueryVersionByQueryUrn(ctx, queryUrn);

        // Retrieve
        QueryVersion queryVersion = this.getQueryVersionRepository().retrieveLastPublishedVersion(queryUrn);
        return queryVersion;
    }

    @Override
    public boolean checkQueryCompatibility(ServiceContext ctx, QueryVersion queryVersion, DatasetVersion datasetVersion) throws MetamacException {
        this.queryServiceInvocationValidator.checkCheckQueryCompatibility(ctx, queryVersion, datasetVersion);

        List<String> dimensionIds = this.datasetVersionRepository.retrieveDimensionsIds(datasetVersion);

        boolean hasTemporal = dimensionIds.contains(StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID);

        boolean compatible = true;

        compatible = compatible && this.checkQueryType(queryVersion, dimensionIds);

        compatible = compatible && this.checkQuerySelection(queryVersion, datasetVersion, dimensionIds);

        if (hasTemporal && QueryTypeEnum.AUTOINCREMENTAL.equals(queryVersion.getType())) {
            compatible = compatible && this.checkQueryAutoincremental(queryVersion, datasetVersion);
        }

        return compatible;
    }

    private boolean checkQueryAutoincremental(QueryVersion queryVersion, DatasetVersion datasetVersion) throws MetamacException {
        List<CodeDimension> codes = this.codeDimensionRepository.findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID, null);
        List<String> codesIdentifiers = this.getCodesIdsInCodesDimension(codes);
        return codesIdentifiers.contains(queryVersion.getLatestTemporalCodeInCreation());
    }

    private boolean checkQuerySelection(QueryVersion queryVersion, DatasetVersion datasetVersion, List<String> dimensionIds) throws MetamacException {
        Collection<String> selectedDimensions = this.getDimensionsIdsInQuerySelection(queryVersion);
        if (selectedDimensions.size() != dimensionIds.size()) {
            return false;
        }

        boolean compatible = true;
        for (String dimensionId : dimensionIds) {
            QuerySelectionItem item = find(queryVersion.getSelection(), new ObjectEqualsStringFieldPredicate("dimension", dimensionId));
            if (item != null) {
                compatible = compatible && this.checkQuerySelectionCodesForDimension(dimensionId, this.getCodesIdsInQuerySelection(item), datasetVersion);
            } else {
                return false;
            }
        }

        return compatible;
    }

    private boolean checkQuerySelectionCodesForDimension(String dimensionId, List<String> codesIdsInQuerySelection, DatasetVersion datasetVersion) throws MetamacException {
        List<CodeDimension> codes = this.codeDimensionRepository.findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), dimensionId, null);
        List<String> codesIdentifiers = this.getCodesIdsInCodesDimension(codes);
        for (String codeInSelection : codesIdsInQuerySelection) {
            if (!codesIdentifiers.contains(codeInSelection)) {
                return false;
            }
        }
        return true;
    }

    private Collection<String> getDimensionsIdsInQuerySelection(QueryVersion queryVersion) {
        Set<String> selectedDimensionIds = new HashSet<String>();
        StatisticalResourcesCollectionUtils.mapCollection(queryVersion.getSelection(), selectedDimensionIds, new MetamacTransformer<QuerySelectionItem, String>() {

            @Override
            public String transformItem(QuerySelectionItem item) {
                return item.getDimension();
            }
        });
        return selectedDimensionIds;
    }

    private List<String> getCodesIdsInQuerySelection(QuerySelectionItem selection) {
        List<String> selectedCodesIds = new ArrayList<String>();
        StatisticalResourcesCollectionUtils.mapCollection(selection.getCodes(), selectedCodesIds, new CodeItemToCodeStringTransformer());
        return selectedCodesIds;
    }

    private List<String> getCodesIdsInCodesDimension(List<CodeDimension> codesDimension) {
        List<String> codesIds = new ArrayList<String>();
        StatisticalResourcesCollectionUtils.mapCollection(codesDimension, codesIds, new CodeDimensionToCodeStringTransformer());
        return codesIds;
    }

    protected boolean checkQueryType(QueryVersion queryVersion, List<String> dimensionIds) {
        if (!dimensionIds.contains(StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID)) {
            if (!QueryTypeEnum.FIXED.equals(queryVersion.getType())) {
                return false;
            }
        }
        return true;
    }

    private DatasetVersion getCurrentDatasetVersionInQuery(QueryVersion queryVersion) throws MetamacException {
        if (queryVersion.getFixedDatasetVersion() != null) {
            return queryVersion.getFixedDatasetVersion();
        }
        return this.datasetVersionRepository.retrieveLastVersion(queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn());
    }

}
