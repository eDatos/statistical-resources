package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Query;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryMetadata;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourcesInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants;
import org.siemac.metamac.statistical_resources.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.internal.service.utils.StatisticalResourcesRestInternalUtils;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.siemac.metamac.core.common.util.GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn;

@Component
public class QueriesDo2RestMapperV10Impl implements QueriesDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10 commonDo2RestMapper;

    @Autowired
    private DatasetsDo2RestMapperV10 datasetsDo2RestMapper;

    @Autowired
    private QueryVersionRepository queryVersionRepository;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    private static final Logger logger = LoggerFactory.getLogger(QueriesDo2RestMapperV10Impl.class);

    @Override
    public Queries toQueries(PagedResult<QueryVersion> sources, String agencyID, String query, String orderBy, Integer limit, List<String> selectedLanguages) {

        Queries targets = new Queries();
        targets.setKind(StatisticalResourcesRestInternalConstants.KIND_QUERIES);

        // Pagination
        String baseLink = this.toQueriesLink(agencyID, null);
        SculptorCriteria2RestCriteria.toPagedResult(sources, targets, query, orderBy, limit, baseLink);

        // Values
        for (QueryVersion source : sources.getValues()) {
            ResourceInternal target = this.toResource(source, selectedLanguages);
            targets.getQueries().add(target);
        }
        return targets;
    }

    @Override
    public Query toQuery(QueryVersion source, List<String> selectedLanguages, boolean includeMetadata, boolean includeData) throws Exception {
        if (source == null) {
            return null;
        }
        Query target = new Query();
        target.setKind(StatisticalResourcesRestInternalConstants.KIND_QUERY);
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(this.toQueryUrn(source));
        target.setSelfLink(this.toQuerySelfLink(source));
        target.setName(this.commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(this.commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(this.toQueryParentLink(source));
        target.setChildLinks(this.toQueryChildLinks(source));
        target.setSelectedLanguages(this.commonDo2RestMapper.toLanguages(selectedLanguages));
        DsdProcessorResult dsdProcessorResult = null;
        DatasetVersion relatedDatasetEffective = null;
        if (includeMetadata || includeData) {
            relatedDatasetEffective = this.getQueryRelatedDatasetVersionEffective(source);
            dsdProcessorResult = this.commonDo2RestMapper.processDataStructure(relatedDatasetEffective.getRelatedDsd().getUrn());
        }
        if (includeMetadata) {
            target.setMetadata(this.toQueryMetadata(source, relatedDatasetEffective, dsdProcessorResult, selectedLanguages));
        }
        if (includeData) {
            target.setData(this.toQueryData(source, relatedDatasetEffective, dsdProcessorResult, selectedLanguages));
        }
        return target;
    }

    private DatasetVersion getQueryRelatedDatasetVersionEffective(QueryVersion source) throws MetamacException {
        if (source.getFixedDatasetVersion() != null) {
            return source.getFixedDatasetVersion();
        } else {
            if (StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
                return this.datasetVersionRepository.retrieveLastVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn());
            } else {
                return this.datasetVersionRepository.retrieveLastPublishedVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn());
            }
        }
    }

    @Override
    public ResourceInternal toResource(QueryVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(this.toQueryUrn(source));
        target.setKind(StatisticalResourcesRestInternalConstants.KIND_QUERY);
        target.setSelfLink(this.toQuerySelfLink(source));
        target.setName(this.commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        target.setManagementAppLink(this.toQueryVersionManagementApplicationLink(source));

        return target;
    }

    @Override
    public ResourceInternal toResource(RelatedResourceResult source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        if (!TypeRelatedResourceEnum.QUERY_VERSION.equals(source.getType())) {
            logger.error("RelatedResource unsupported: " + source.getType());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setUrn(this.toQueryUrn(source.getMaintainerNestedCode(), source.getCode()));
        target.setKind(StatisticalResourcesRestInternalConstants.KIND_QUERY);
        target.setSelfLink(this.toQuerySelfLink(source));
        target.setName(this.commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        target.setManagementAppLink(this.toQueryVersionManagementApplicationLink(source));

        return target;
    }

    private QueryMetadata toQueryMetadata(QueryVersion source, DatasetVersion datasetVersion, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        QueryMetadata target = new QueryMetadata();

        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = this.calculateEffectiveDimensionValuesToQuery(source, datasetVersion);

        target.setRelatedDsd(this.commonDo2RestMapper.toDataStructureDefinition(datasetVersion.getRelatedDsd(), dsdProcessorResult.getDataStructure(), selectedLanguages));
        target.setDimensions(this.commonDo2RestMapper.toDimensions(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, effectiveDimensionValuesToDataByDimension,
                selectedLanguages));
        target.setAttributes(this.commonDo2RestMapper.toAttributes(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, selectedLanguages));

        ResourceInternal relatedDataset = null;
        if (source.getDataset() != null) {
            relatedDataset = this.datasetsDo2RestMapper.toResourceAsLatest(datasetVersion, selectedLanguages);
        } else {
            relatedDataset = this.datasetsDo2RestMapper.toResource(datasetVersion, selectedLanguages);
        }
        target.setRelatedDataset(relatedDataset);
        target.setStatus(this.toQueryStatus(source.getStatus()));
        target.setType(this.toQueryType(source.getType()));
        target.setLatestDataNumber(source.getLatestDataNumber());
        target.setStatisticalOperation(this.commonDo2RestMapper.toResourceExternalItemStatisticalOperations(source.getLifeCycleStatisticalResource().getStatisticalOperation(), selectedLanguages));
        target.setMaintainer(this.commonDo2RestMapper.toResourceExternalItemSrm(source.getLifeCycleStatisticalResource().getMaintainer(), selectedLanguages));
        target.setValidFrom(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getValidFrom()));
        target.setValidTo(this.commonDo2RestMapper.toDate(StatisticalResourcesRestInternalUtils.isDateAfterNowSetNull(source.getLifeCycleStatisticalResource().getValidTo())));
        target.setRequires(this.datasetsDo2RestMapper.toResource(datasetVersion, selectedLanguages));
        target.setIsPartOf(this.toQueryIsPartOf(source, selectedLanguages));
        target.setNextVersion(this.commonDo2RestMapper.toNextVersionType(source.getLifeCycleStatisticalResource().getNextVersion(), selectedLanguages));
        target.setNextVersionDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getNextVersionDate()));
        target.setProcStatus(this.commonDo2RestMapper.toProcStatusType(source.getLifeCycleStatisticalResource().getProcStatus(), selectedLanguages));
        target.setCreationDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getCreationDate()));
        target.setCreationUser(source.getLifeCycleStatisticalResource().getCreationUser());
        target.setProductionValidationDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getCreationDate()));
        target.setProductionValidationUser(source.getLifeCycleStatisticalResource().getPublicationUser());
        target.setDiffusionValidationDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getLifeCycleStatisticalResource().getDiffusionValidationUser());
        target.setRejectValidationDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getRejectValidationDate()));
        target.setRejectValidationUser(source.getLifeCycleStatisticalResource().getRejectValidationUser());
        target.setPublicationDate(this.commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getPublicationDate()));
        target.setPublicationUser(source.getLifeCycleStatisticalResource().getPublicationUser());

        return target;
    }

    private ResourcesInternal toQueryIsPartOf(QueryVersion source, List<String> selectedLanguages) throws MetamacException {
        List<RelatedResourceResult> relatedResourceIsPartOf = null;

        if (StatisticalResourcesRestInternalConstants.IS_INTERNAL_API) {
            relatedResourceIsPartOf = this.queryVersionRepository.retrieveIsPartOf(source);
        } else {
            relatedResourceIsPartOf = this.queryVersionRepository.retrieveIsPartOfOnlyLastPublished(source);
        }

        if (CollectionUtils.isEmpty(relatedResourceIsPartOf)) {
            return null;
        }
        ResourcesInternal targets = new ResourcesInternal();
        for (RelatedResourceResult relatedResourceResult : relatedResourceIsPartOf) {
            targets.getResources().add(this.commonDo2RestMapper.toResource(relatedResourceResult, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Data toQueryData(QueryVersion source, DatasetVersion datasetVersion, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws Exception {
        if (source == null) {
            return null;
        }
        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = this.calculateEffectiveDimensionValuesToQuery(source, datasetVersion);
        return this.commonDo2RestMapper.toData(datasetVersion, dsdProcessorResult, effectiveDimensionValuesToDataByDimension, selectedLanguages);
    }

    private ResourceLink toQueryParentLink(QueryVersion source) {
        return this.toQueriesSelfLink(null, null);
    }

    private ChildLinks toQueryChildLinks(QueryVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toQueriesSelfLink(String agencyID, String resourceID) {
        return this.commonDo2RestMapper.toResourceLink(StatisticalResourcesRestInternalConstants.KIND_QUERIES, this.toQueriesLink(agencyID, resourceID));
    }

    private String toQueriesLink(String agencyID, String resourceID) {
        String resourceSubpath = StatisticalResourcesRestInternalConstants.LINK_SUBPATH_QUERIES;
        return this.commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, null);
    }

    private ResourceLink toQuerySelfLink(QueryVersion source) {
        String agencyID = source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getLifeCycleStatisticalResource().getCode();
        return this.toQuerySelfLink(agencyID, resourceID);
    }

    private ResourceLink toQuerySelfLink(RelatedResourceResult source) {
        String agencyID = source.getMaintainerNestedCode();
        String resourceID = source.getCode();
        return this.toQuerySelfLink(agencyID, resourceID);
    }

    private ResourceLink toQuerySelfLink(String agencyID, String resourceID) {
        String link = this.toQueryLink(agencyID, resourceID);
        return this.commonDo2RestMapper.toResourceLink(StatisticalResourcesRestInternalConstants.KIND_QUERY, link);
    }

    private String toQueryLink(String agencyID, String resourceID) {
        String resourceSubpath = StatisticalResourcesRestInternalConstants.LINK_SUBPATH_QUERIES;
        String version = null; // do not return version
        return this.commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    /**
     * Retrieve urn to API, without version
     */
    private String toQueryUrn(QueryVersion source) {
        return this.toQueryUrn(source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested(), source.getLifeCycleStatisticalResource().getCode());
    }
    private String toQueryUrn(String maintainerNestedCode, String code) {
        return generateSiemacStatisticalResourceQueryUrn(new String[]{maintainerNestedCode}, code); // global urn without version
    }

    private org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryStatus toQueryStatus(QueryStatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case ACTIVE:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryStatus.ACTIVE;
            case DISCONTINUED:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryStatus.DISCONTINUED;
            default:
                logger.error("QueryStatusEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryType toQueryType(QueryTypeEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case FIXED:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryType.FIXED;
            case AUTOINCREMENTAL:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryType.AUTOINCREMENTAL;
            case LATEST_DATA:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.QueryType.LATEST_DATA;
            default:
                logger.error("QueryTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, List<String>> calculateEffectiveDimensionValuesToQuery(QueryVersion source, DatasetVersion datasetVersion) {
        Map<String, List<String>> dimensionValuesSelected = new HashMap<String, List<String>>(source.getSelection().size());
        for (QuerySelectionItem selection : source.getSelection()) {
            List<String> dimensionValues = this.calculateEffectiveDimensionValuesToQuery(source, datasetVersion, selection);
            dimensionValuesSelected.put(selection.getDimension(), dimensionValues);
        }
        return dimensionValuesSelected;
    }

    private List<String> calculateEffectiveDimensionValuesToQuery(QueryVersion source, DatasetVersion datasetVersion, QuerySelectionItem selection) {
        QueryTypeEnum type = source.getType();
        String dimensionId = selection.getDimension();
        List<String> selectionCodes = this.commonDo2RestMapper.codeItemToString(selection.getCodes());

        if (QueryTypeEnum.FIXED.equals(type)) {
            // return exactly
            return selectionCodes;
        } else if (QueryTypeEnum.AUTOINCREMENTAL.equals(type)) {
            if (this.isTemporalDimension(dimensionId)) {
                List<String> effectiveDimensionValues = new ArrayList<String>();
                List<String> temporalCoverageCodes = this.commonDo2RestMapper.temporalCoverageToString(datasetVersion.getTemporalCoverage());
                int indexLatestTemporalCodeInCreation = temporalCoverageCodes.indexOf(source.getLatestTemporalCodeInCreation());
                if (indexLatestTemporalCodeInCreation != 0) {
                    // add codes added after query creation
                    List<TemporalCode> temporalCodesAddedAfterQueryCreation = datasetVersion.getTemporalCoverage().subList(0, indexLatestTemporalCodeInCreation);
                    List<String> temporalCodesAddedAfterQueryCreationString = this.commonDo2RestMapper.temporalCoverageToString(temporalCodesAddedAfterQueryCreation);
                    for (String code : temporalCodesAddedAfterQueryCreationString) {
                        effectiveDimensionValues.add(code);
                    }
                }
                effectiveDimensionValues.addAll(selectionCodes);
                return effectiveDimensionValues;
            } else {
                // return exactly
                return selectionCodes;
            }
        } else if (QueryTypeEnum.LATEST_DATA.equals(type)) {
            if (this.isTemporalDimension(dimensionId)) {
                // return N data
                int codeLastIndexToReturn = -1;
                if (datasetVersion.getTemporalCoverage().size() < source.getLatestDataNumber()) {
                    codeLastIndexToReturn = datasetVersion.getTemporalCoverage().size(); // there is not N data, so return all
                } else {
                    codeLastIndexToReturn = source.getLatestDataNumber();
                }
                List<TemporalCode> temporalCodesLatestDataNumber = datasetVersion.getTemporalCoverage().subList(0, codeLastIndexToReturn);
                return this.commonDo2RestMapper.temporalCoverageToString(temporalCodesLatestDataNumber);
            } else {
                // return exactly
                return selectionCodes;
            }
        } else {
            logger.error("QueryTypeEnum unsupported: " + source);
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isTemporalDimension(String dimensionId) {
        return StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID.equals(dimensionId);
    }

    private String toQueryVersionManagementApplicationLink(QueryVersion source) {
        return this.commonDo2RestMapper.getInternalWebApplicationNavigation().buildQueryVersionUrl(source);
    }

    private String toQueryVersionManagementApplicationLink(RelatedResourceResult source) {
        return this.commonDo2RestMapper.getInternalWebApplicationNavigation().buildQueryVersionUrl(source);
    }
}