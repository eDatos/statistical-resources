package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryMetadata;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueriesDo2RestMapperV10Impl implements QueriesDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10   commonDo2RestMapper;

    @Autowired
    private DatasetsDo2RestMapperV10 datasetsDo2RestMapper;

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
        target.setUrn(source.getLifeCycleStatisticalResource().getUrn());
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(commonDo2RestMapper.toInternationalString(source.getLifeCycleStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toQueryParentLink(source));
        target.setChildLinks(toQueryChildLinks(source));
        target.setSelectedLanguages(commonDo2RestMapper.toLanguages(selectedLanguages));
        DsdProcessorResult dsdProcessorResult = null;
        if (includeMetadata || includeData) {
            dsdProcessorResult = commonDo2RestMapper.processDataStructure(source.getDatasetVersion().getRelatedDsd().getUrn());
        }
        if (includeMetadata) {
            target.setMetadata(toQueryMetadata(source, dsdProcessorResult, selectedLanguages));
        }
        if (includeData) {
            target.setData(toQueryData(source, dsdProcessorResult, selectedLanguages));
        }
        return target;
    }

    @Override
    public Resource toResource(QueryVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(source.getLifeCycleStatisticalResource().getUrn());
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
        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_QUERY);
        target.setSelfLink(toQuerySelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private QueryMetadata toQueryMetadata(QueryVersion source, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        QueryMetadata target = new QueryMetadata();

        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = commonDo2RestMapper.calculateEffectiveDimensionValuesToQuery(source);

        target.setRelatedDsd(commonDo2RestMapper.toDataStructureDefinition(source.getDatasetVersion().getRelatedDsd(), dsdProcessorResult.getDataStructure(), selectedLanguages));
        target.setDimensions(commonDo2RestMapper.toDimensions(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult,
                effectiveDimensionValuesToDataByDimension, selectedLanguages));
        target.setAttributes(commonDo2RestMapper.toAttributes(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, selectedLanguages));
        target.setRelatedDataset(datasetsDo2RestMapper.toResource(source.getDatasetVersion(), selectedLanguages));
        target.setStatus(toQueryStatus(source.getStatus()));
        target.setType(toQueryType(source.getType()));
        target.setLatestDataNumber(source.getLatestDataNumber());
        target.setStatisticalOperation(commonDo2RestMapper.toResourceExternalItemStatisticalOperations(source.getLifeCycleStatisticalResource().getStatisticalOperation(), selectedLanguages));
        target.setMaintainer(commonDo2RestMapper.toResourceExternalItemSrm(source.getLifeCycleStatisticalResource().getMaintainer(), selectedLanguages));
        target.setValidFrom(commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getValidFrom()));
        target.setValidTo(commonDo2RestMapper.toDate(source.getLifeCycleStatisticalResource().getValidTo()));
        target.setRequires(datasetsDo2RestMapper.toResource(source.getDatasetVersion(), selectedLanguages));
        return target;
    }

    private Data toQueryData(QueryVersion source, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws Exception {
        if (source == null) {
            return null;
        }
        Map<String, List<String>> effectiveDimensionValuesToDataByDimension = commonDo2RestMapper.calculateEffectiveDimensionValuesToQuery(source);
        return commonDo2RestMapper.toData(source.getDatasetVersion(), dsdProcessorResult, effectiveDimensionValuesToDataByDimension, selectedLanguages);
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
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_QUERY, toQueryLink(source));
    }

    private ResourceLink toQuerySelfLink(RelatedResourceResult source) {
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_QUERY, toQueryLink(source));
    }

    private String toQueryLink(QueryVersion source) {
        String agencyID = source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getLifeCycleStatisticalResource().getCode();
        return toQueryLink(agencyID, resourceID);
    }

    private String toQueryLink(RelatedResourceResult source) {
        String agencyID = source.getMaintainerNestedCode();
        String resourceID = source.getCode();
        return toQueryLink(agencyID, resourceID);
    }

    private String toQueryLink(String agencyID, String resourceID) {
        String resourceSubpath = StatisticalResourcesRestExternalConstants.LINK_SUBPATH_QUERIES;
        String version = null; // no devolver versión
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
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
            case PENDING_REVIEW:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryStatus.ACTIVE; // This is not an error. PENDING_REVIEW is not provided in API
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

}