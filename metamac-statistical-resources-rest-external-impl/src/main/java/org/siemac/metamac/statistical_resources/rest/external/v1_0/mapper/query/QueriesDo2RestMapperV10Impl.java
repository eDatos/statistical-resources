package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

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
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryMetadata;
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
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.siemac.metamac.core.common.util.GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn;

@Component
public class QueriesDo2RestMapperV10Impl implements QueriesDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10   commonDo2RestMapper;

    @Autowired
    private DatasetsDo2RestMapperV10 datasetsDo2RestMapper;

    @Autowired
    private QueryVersionRepository   queryVersionRepository;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    private static final Logger      logger = LoggerFactory.getLogger(QueriesDo2RestMapperV10Impl.class);

    @Override
    public Queries toQueries(PagedResult<QueryVersion> sources, String agencyID, String query, String orderBy, Integer limit, List<String> selectedLanguages) {

        Queries targets = new Queries();
        targets.setKind(StatisticalResourcesRestExternalConstants.KIND_QUERIES);

        // Pagination
        String baseLink = toQueriesLink(agencyID, null);
        SculptorCriteria2RestCriteria.toPagedResult(sources, targets, query, orderBy, limit, baseLink);

        // Values
        for (QueryVersion source : sources.getValues()) {
            Resource target = toResource(source, selectedLanguages);
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
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_QUERY);
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(toQueryUrn(source));
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toQueryParentLink(source));
        target.setChildLinks(toQueryChildLinks(source));
        target.setSelectedLanguages(commonDo2RestMapper.toLanguages(selectedLanguages));
        DsdProcessorResult dsdProcessorResult = null;
        DatasetVersion relatedDatasetEffective = null;
        if (includeMetadata || includeData) {
            relatedDatasetEffective = getQueryRelatedDatasetVersionEffective(source);
            dsdProcessorResult = commonDo2RestMapper.processDataStructure(relatedDatasetEffective.getRelatedDsd().getUrn());
        }
        if (includeMetadata) {
            target.setMetadata(toQueryMetadata(source, relatedDatasetEffective, dsdProcessorResult, selectedLanguages));
        }
        if (includeData) {
            target.setData(toQueryData(source, relatedDatasetEffective, dsdProcessorResult, selectedLanguages));
        }
        return target;
    }

    private DatasetVersion getQueryRelatedDatasetVersionEffective(QueryVersion source) throws MetamacException {
        if (source.getFixedDatasetVersion() != null) {
            return source.getFixedDatasetVersion();
        } else {
            if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
                return datasetVersionRepository.retrieveLastVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn());
            } else {
                return datasetVersionRepository.retrieveLastPublishedVersion(source.getDataset().getIdentifiableStatisticalResource().getUrn());
            }
        }
    }

    @Override
    public Resource toResource(QueryVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(toQueryUrn(source));
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_QUERY);
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        return target;
    }

    @Override
    public Resource toResource(RelatedResourceResult source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        if (!TypeRelatedResourceEnum.QUERY_VERSION.equals(source.getType())) {
            logger.error("RelatedResource unsupported: " + source.getType());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(toQueryUrn(source.getMaintainerNestedCode(), source.getCode()));
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_QUERY);
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private QueryMetadata toQueryMetadata(QueryVersion source, DatasetVersion datasetVersion, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        QueryMetadata target = new QueryMetadata();

        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = calculateEffectiveDimensionValuesToQuery(source, datasetVersion);

        target.setRelatedDsd(commonDo2RestMapper.toDataStructureDefinition(datasetVersion.getRelatedDsd(), dsdProcessorResult.getDataStructure(), selectedLanguages));
        target.setDimensions(commonDo2RestMapper.toDimensions(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, effectiveDimensionValuesToDataByDimension,
                selectedLanguages));
        target.setAttributes(commonDo2RestMapper.toAttributes(datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, selectedLanguages));

        Resource relatedDataset = null;
        if (source.getDataset() != null) {
            relatedDataset = datasetsDo2RestMapper.toResourceAsLatest(datasetVersion, selectedLanguages);
        } else {
            relatedDataset = datasetsDo2RestMapper.toResource(datasetVersion, selectedLanguages);
        }
        target.setRelatedDataset(relatedDataset);
        target.setStatus(toQueryStatus(source.getStatus()));
        target.setType(toQueryType(source.getType()));
        target.setLatestDataNumber(source.getLatestDataNumber());
        target.setStatisticalOperation(commonDo2RestMapper.toResourceExternalItemStatisticalOperations(source.getLifeCycleStatisticalResource().getStatisticalOperation(), selectedLanguages));
        target.setMaintainer(commonDo2RestMapper.toResourceExternalItemSrm(source.getLifeCycleStatisticalResource().getMaintainer(), selectedLanguages));
        target.setValidFrom(commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getValidFrom()));
        target.setValidTo(commonDo2RestMapper.toDate(StatisticalResourcesRestExternalUtils.isDateAfterNowSetNull(source.getLifeCycleStatisticalResource().getValidTo())));
        target.setRequires(datasetsDo2RestMapper.toResource(datasetVersion, selectedLanguages));
        target.setIsPartOf(toQueryIsPartOf(source, selectedLanguages));
        return target;
    }

    private Resources toQueryIsPartOf(QueryVersion source, List<String> selectedLanguages) throws MetamacException {
        List<RelatedResourceResult> relatedResourceIsPartOf = null;

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            relatedResourceIsPartOf = queryVersionRepository.retrieveIsPartOf(source);
        } else {
            relatedResourceIsPartOf = queryVersionRepository.retrieveIsPartOfOnlyLastPublished(source);
        }

        if (CollectionUtils.isEmpty(relatedResourceIsPartOf)) {
            return null;
        }
        Resources targets = new Resources();
        for (RelatedResourceResult relatedResourceResult : relatedResourceIsPartOf) {
            targets.getResources().add(commonDo2RestMapper.toResource(relatedResourceResult, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Data toQueryData(QueryVersion source, DatasetVersion datasetVersion, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws Exception {
        if (source == null) {
            return null;
        }
        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = calculateEffectiveDimensionValuesToQuery(source, datasetVersion);
        return commonDo2RestMapper.toData(datasetVersion, dsdProcessorResult, effectiveDimensionValuesToDataByDimension, selectedLanguages);
    }

    private ResourceLink toQueryParentLink(QueryVersion source) {
        return toQueriesSelfLink(null, null);
    }

    private ChildLinks toQueryChildLinks(QueryVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toQueriesSelfLink(String agencyID, String resourceID) {
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_QUERIES, toQueriesLink(agencyID, resourceID));
    }

    private String toQueriesLink(String agencyID, String resourceID) {
        String resourceSubpath = StatisticalResourcesRestExternalConstants.LINK_SUBPATH_QUERIES;
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, null);
    }

    private ResourceLink toQuerySelfLink(QueryVersion source) {
        String agencyID = source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getLifeCycleStatisticalResource().getCode();
        return toQuerySelfLink(agencyID, resourceID);
    }

    private ResourceLink toQuerySelfLink(RelatedResourceResult source) {
        String agencyID = source.getMaintainerNestedCode();
        String resourceID = source.getCode();
        return toQuerySelfLink(agencyID, resourceID);
    }

    private ResourceLink toQuerySelfLink(String agencyID, String resourceID) {
        String link = toQueryLink(agencyID, resourceID);
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_QUERY, link);
    }

    private String toQueryLink(String agencyID, String resourceID) {
        String resourceSubpath = StatisticalResourcesRestExternalConstants.LINK_SUBPATH_QUERIES;
        String version = null; // do not return version
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    /**
     * Retrieve urn to API, without version
     */
    private String toQueryUrn(QueryVersion source) {
        return toQueryUrn(source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested(), source.getLifeCycleStatisticalResource().getCode());
    }
    private String toQueryUrn(String maintainerNestedCode, String code) {
        return generateSiemacStatisticalResourceQueryUrn(new String[]{maintainerNestedCode}, code); // global urn without version
    }

    private org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryStatus toQueryStatus(QueryStatusEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case ACTIVE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryStatus.ACTIVE;
            case DISCONTINUED:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryStatus.DISCONTINUED;
            default:
                logger.error("QueryStatusEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryType toQueryType(QueryTypeEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case FIXED:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryType.FIXED;
            case AUTOINCREMENTAL:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryType.AUTOINCREMENTAL;
            case LATEST_DATA:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryType.LATEST_DATA;
            default:
                logger.error("QueryTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Map<String, List<String>> calculateEffectiveDimensionValuesToQuery(QueryVersion source, DatasetVersion datasetVersion) {
        Map<String, List<String>> dimensionValuesSelected = new HashMap<String, List<String>>(source.getSelection().size());
        for (QuerySelectionItem selection : source.getSelection()) {
            List<String> dimensionValues = calculateEffectiveDimensionValuesToQuery(source, datasetVersion, selection);
            dimensionValuesSelected.put(selection.getDimension(), dimensionValues);
        }
        return dimensionValuesSelected;
    }

    private List<String> calculateEffectiveDimensionValuesToQuery(QueryVersion source, DatasetVersion datasetVersion, QuerySelectionItem selection) {
        QueryTypeEnum type = source.getType();
        String dimensionId = selection.getDimension();
        List<String> selectionCodes = commonDo2RestMapper.codeItemToString(selection.getCodes());

        if (QueryTypeEnum.FIXED.equals(type)) {
            // return exactly
            return selectionCodes;
        } else if (QueryTypeEnum.AUTOINCREMENTAL.equals(type)) {
            if (isTemporalDimension(dimensionId)) {
                List<String> effectiveDimensionValues = new ArrayList<String>();
                List<String> temporalCoverageCodes = commonDo2RestMapper.temporalCoverageToString(datasetVersion.getTemporalCoverage());
                int indexLatestTemporalCodeInCreation = temporalCoverageCodes.indexOf(source.getLatestTemporalCodeInCreation());
                if (indexLatestTemporalCodeInCreation != 0) {
                    // add codes added after query creation
                    List<TemporalCode> temporalCodesAddedAfterQueryCreation = datasetVersion.getTemporalCoverage().subList(0, indexLatestTemporalCodeInCreation);
                    List<String> temporalCodesAddedAfterQueryCreationString = commonDo2RestMapper.temporalCoverageToString(temporalCodesAddedAfterQueryCreation);
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
            if (isTemporalDimension(dimensionId)) {
                // return N data
                int codeLastIndexToReturn = -1;
                if (datasetVersion.getTemporalCoverage().size() < source.getLatestDataNumber()) {
                    codeLastIndexToReturn = datasetVersion.getTemporalCoverage().size(); // there is not N data, so return all
                } else {
                    codeLastIndexToReturn = source.getLatestDataNumber();
                }
                List<TemporalCode> temporalCodesLatestDataNumber = datasetVersion.getTemporalCoverage().subList(0, codeLastIndexToReturn);
                return commonDo2RestMapper.temporalCoverageToString(temporalCodesLatestDataNumber);
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

}