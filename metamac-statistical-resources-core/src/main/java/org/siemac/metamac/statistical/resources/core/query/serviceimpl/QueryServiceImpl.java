package org.siemac.metamac.statistical.resources.core.query.serviceimpl;

import static org.siemac.metamac.core.common.util.MetamacCollectionUtils.find;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getUrnsFromRelatedResourceResults;

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
import org.siemac.metamac.core.common.util.transformers.MetamacTransformer;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.ProcStatusValidator;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceRepository;
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
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.transformers.CodeDimensionToCodeStringTransformer;
import org.siemac.metamac.statistical.resources.core.utils.transformers.CodeItemToCodeStringTransformer;
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
    private CodeDimensionRepository                   codeDimensionRepository;

    @Autowired
    private DatasetVersionRepository                  datasetVersionRepository;

    @Autowired
    private QueryVersionRepository                    queryVersionRepository;

    @Autowired
    private QueryLifecycleService                     queryLifecycleService;

    @Autowired
    private RelatedResourceRepository                 relatedResourceRepository;

    public QueryServiceImpl() {
    }

    //
    // QUERIES
    //

    @Override
    public List<Query> findQueriesByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkFindQueriesByCondition(ctx, conditions);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, DatasetVersion.class);
        return getQueryRepository().findByCondition(conditions);
    }

    //
    // QUERY VERSIONS
    //

    @Override
    public QueryVersion retrieveQueryVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersionByUrn(ctx, urn);

        // Retrieve
        return getQueryVersionRepository().retrieveByUrn(urn);
    }

    @Override
    public List<QueryVersion> retrieveQueryVersions(ServiceContext ctx) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersions(ctx);

        // Retrieve
        return getQueryVersionRepository().findAll();
    }

    @Override
    public List<QueryVersion> retrieveQueryVersions(ServiceContext ctx, String queryVersionUrn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkRetrieveQueryVersions(ctx, queryVersionUrn);

        // Retrieve
        List<QueryVersion> queryVersions = getQueryVersionRepository().retrieveByUrn(queryVersionUrn).getQuery().getVersions();

        return queryVersions;
    }

    @Override
    public PagedResult<QueryVersion> findQueryVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkFindQueryVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, Query.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        return getQueryVersionRepository().findByCondition(conditions, pagingParameter);
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

        checkQueryCompatibility(ctx, queryVersion);

        // Save query
        query = getQueryRepository().save(query);

        queryVersion.setQuery(query);
        return getQueryVersionRepository().save(queryVersion);
    }

    @Override
    public QueryVersion updateQueryVersion(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkUpdateQueryVersion(ctx, queryVersion);

        // Check procStatus
        ProcStatusValidator.checkQueryVersionCanBeEdited(queryVersion);

        // Fill metadata
        fillMetadataForUpdateQueryVersion(queryVersion);

        // Check that datasetVersion is published if the query version already is
        queryLifecycleService.checkLinkedDatasetOrDatasetVersionPublishedBeforeQuery(ctx, queryVersion);

        // Check URN duplicated. We have to do it right now because later the fillMetadata method change the hibernate cache
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(queryVersion.getLifeCycleStatisticalResource());

        checkQueryCompatibility(ctx, queryVersion);

        // Repository operation
        return getQueryVersionRepository().save(queryVersion);

    }

    private void checkQueryCompatibility(ServiceContext ctx, QueryVersion queryVersion) throws MetamacException {
        DatasetVersion datasetVersion = getCurrentDatasetVersionInQuery(queryVersion);
        if (!checkQueryCompatibility(ctx, queryVersion, datasetVersion)) {
            throw new MetamacException(ServiceExceptionType.QUERY_VERSION_NOT_COMPATIBLE_WITH_DATASET, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    public void deleteQueryVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Validations
        queryServiceInvocationValidator.checkDeleteQueryVersion(ctx, urn);

        // Retrieve entity
        QueryVersion queryVersion = retrieveQueryVersionByUrn(ctx, urn);

        // Check that query is with a correct procStatus
        ProcStatusValidator.checkStatisticalResourceCanBeDeleted(queryVersion);

        checkCanQueryVersionBeDeleted(queryVersion);

        if (StatisticalResourcesVersionUtils.isInitialVersion(queryVersion.getLifeCycleStatisticalResource().getVersionLogic())) {
            Query query = queryVersion.getQuery();
            getQueryRepository().delete(query);
        } else {
            // Previous version
            updateReplacedVersionIsReplacedByVersion(queryVersion);

            // Delete version
            Query query = queryVersion.getQuery();
            query.getVersions().remove(queryVersion);
            getQueryVersionRepository().delete(queryVersion);
        }
    }

    private void updateReplacedVersionIsReplacedByVersion(QueryVersion queryVersion) {
        RelatedResource previousResource = queryVersion.getLifeCycleStatisticalResource().getReplacesVersion();
        if (previousResource.getQueryVersion() != null) {
            QueryVersion previousVersion = previousResource.getQueryVersion();
            previousVersion.getLifeCycleStatisticalResource().setLastVersion(true);
            RelatedResource isReplacedByVersion = previousVersion.getLifeCycleStatisticalResource().getIsReplacedByVersion();
            relatedResourceRepository.delete(isReplacedByVersion);
            previousVersion.getLifeCycleStatisticalResource().setIsReplacedByVersion(null);
            getQueryVersionRepository().save(previousVersion);
        }
    }

    private void checkCanQueryVersionBeDeleted(QueryVersion queryVersion) throws MetamacException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        boolean isOnlyVersion = StatisticalResourcesVersionUtils.isInitialVersion(queryVersion.getLifeCycleStatisticalResource().getVersionLogic());

        if (isOnlyVersion) {
            checkQueryVersionIsPartOfSomePublication(queryVersion, exceptionItems);
        }

        if (exceptionItems.size() > 0) {
            MetamacExceptionItem item = new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_CANT_BE_DELETED, queryVersion.getLifeCycleStatisticalResource().getUrn());
            item.setExceptionItems(exceptionItems);
            throw new MetamacException(Arrays.asList(item));
        }
    }

    protected void checkQueryVersionIsPartOfSomePublication(QueryVersion queryVersion, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        List<RelatedResourceResult> resourcesIsPartOf = queryVersionRepository.retrieveIsPartOf(queryVersion);
        if (!resourcesIsPartOf.isEmpty()) {
            List<String> urns = getUrnsFromRelatedResourceResults(resourcesIsPartOf);
            Collections.sort(urns);
            String parameter = StringUtils.join(urns, ", ");
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.QUERY_VERSION_IS_PART_OF_OTHER_RESOURCES, parameter));
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
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(),
                queryVersion.getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private void fillMetadataForUpdateQueryVersion(QueryVersion queryVersion) throws MetamacException {
        queryVersion.setStatus(determineQueryStatus(queryVersion));
        queryVersion.setLatestTemporalCodeInCreation(determineLatestTemporalCodeInCreation(queryVersion));

        // Update URN
        String[] maintainerCodes = new String[]{queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested()};
        String urn = GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerCodes, queryVersion.getLifeCycleStatisticalResource().getCode(),
                queryVersion.getLifeCycleStatisticalResource().getVersionLogic());
        queryVersion.getLifeCycleStatisticalResource().setUrn(urn);
    }

    private QueryStatusEnum determineQueryStatus(QueryVersion queryVersion) throws MetamacException {
        DatasetVersion datasetVersion = getCurrentDatasetVersionInQuery(queryVersion);
        if (datasetVersionRepository.isLastVersion(datasetVersion.getSiemacMetadataStatisticalResource().getUrn())) {
            return QueryStatusEnum.ACTIVE;
        } else {
            return QueryStatusEnum.DISCONTINUED;
        }
    }

    private String determineLatestTemporalCodeInCreation(QueryVersion queryVersion) throws MetamacException {
        if (QueryTypeEnum.AUTOINCREMENTAL.equals(queryVersion.getType())) {
            DatasetVersion datasetVersion = getCurrentDatasetVersionInQuery(queryVersion);
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

    @Override
    public boolean checkQueryCompatibility(ServiceContext ctx, QueryVersion queryVersion, DatasetVersion datasetVersion) throws MetamacException {
        queryServiceInvocationValidator.checkCheckQueryCompatibility(ctx, queryVersion, datasetVersion);

        List<String> dimensionIds = datasetVersionRepository.retrieveDimensionsIds(datasetVersion);

        boolean hasTemporal = dimensionIds.contains(StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID);

        boolean compatible = true;

        compatible = compatible && checkQueryType(queryVersion, dimensionIds);

        compatible = compatible && checkQuerySelection(queryVersion, datasetVersion, dimensionIds);

        if (hasTemporal && QueryTypeEnum.AUTOINCREMENTAL.equals(queryVersion.getType())) {
            compatible = compatible && checkQueryAutoincremental(queryVersion, datasetVersion);
        }

        return compatible;
    }

    private boolean checkQueryAutoincremental(QueryVersion queryVersion, DatasetVersion datasetVersion) throws MetamacException {
        List<CodeDimension> codes = codeDimensionRepository.findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID, null);
        List<String> codesIdentifiers = getCodesIdsInCodesDimension(codes);
        return codesIdentifiers.contains(queryVersion.getLatestTemporalCodeInCreation());
    }

    private boolean checkQuerySelection(QueryVersion queryVersion, DatasetVersion datasetVersion, List<String> dimensionIds) throws MetamacException {
        Collection<String> selectedDimensions = getDimensionsIdsInQuerySelection(queryVersion);
        if (selectedDimensions.size() != dimensionIds.size()) {
            return false;
        }

        boolean compatible = true;
        for (String dimensionId : dimensionIds) {
            QuerySelectionItem item = find(queryVersion.getSelection(), new ObjectEqualsStringFieldPredicate("dimension", dimensionId));
            if (item != null) {
                compatible = compatible && checkQuerySelectionCodesForDimension(dimensionId, getCodesIdsInQuerySelection(item), datasetVersion);
            } else {
                return false;
            }
        }

        return compatible;
    }

    private boolean checkQuerySelectionCodesForDimension(String dimensionId, List<String> codesIdsInQuerySelection, DatasetVersion datasetVersion) throws MetamacException {
        List<CodeDimension> codes = codeDimensionRepository.findCodesForDatasetVersionByDimensionId(datasetVersion.getId(), dimensionId, null);
        List<String> codesIdentifiers = getCodesIdsInCodesDimension(codes);
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
        return datasetVersionRepository.retrieveLastVersion(queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn());
    }

}
