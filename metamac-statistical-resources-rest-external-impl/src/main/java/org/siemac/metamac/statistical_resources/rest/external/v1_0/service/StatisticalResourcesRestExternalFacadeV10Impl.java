package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import static org.siemac.metamac.rest.exception.utils.RestExceptionUtils.checkParameterNotWildcardAll;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.hasField;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.manageException;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.parseDimensionExpression;

import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.rest.search.criteria.SculptorCriteria;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collection;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collections;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Datasets;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.service.StatisticalResourcesRestExternalCommonService;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection.CollectionsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsRest2DoMapper;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query.QueriesDo2RestMapperV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalFacadeV10")
public class StatisticalResourcesRestExternalFacadeV10Impl implements StatisticalResourcesV1_0 {

    @Autowired
    private StatisticalResourcesRestExternalCommonService commonService;

    @Autowired
    private DatasetsDo2RestMapperV10                      datasetsDo2RestMapper;

    @Autowired
    private DatasetsRest2DoMapper                         datasetsRest2DoMapper;

    @Autowired
    private CollectionsDo2RestMapperV10                   collectionsDo2RestMapper;

    @Autowired
    private QueriesDo2RestMapperV10                       queriesDo2RestMapper;

    @Override
    public Datasets findDatasets(String query, String orderBy, String limit, String offset, List<String> lang) {
        return findDatasets(null, null, null, query, orderBy, limit, offset, lang);
    }

    @Override
    public Datasets findDatasets(String agencyID, String query, String orderBy, String limit, String offset, List<String> lang) {
        checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
        return findDatasets(agencyID, null, null, query, orderBy, limit, offset, lang);
    }

    @Override
    public Datasets findDatasets(String agencyID, String resourceID, String query, String orderBy, String limit, String offset, List<String> lang) {
        checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
        checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);
        return findDatasets(agencyID, resourceID, null, query, orderBy, limit, offset, lang);
    }

    @Override
    public Dataset retrieveDataset(String agencyID, String resourceID, String version, List<String> lang, String fields, String dim) {
        try {
            DatasetVersion datasetVersion = commonService.retrieveDatasetVersion(agencyID, resourceID, version);

            Map<String, List<String>> dimensions = parseDimensionExpression(dim);
            boolean includeMetadata = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_METADATA);
            boolean includeData = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_DATA);
            Dataset dataset = datasetsDo2RestMapper.toDataset(datasetVersion, dimensions, lang, includeMetadata, includeData);
            return dataset;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Collections findCollections(String query, String orderBy, String limit, String offset, List<String> lang) {
        return null;
    }

    @Override
    public Collections findCollections(String agencyID, String query, String orderBy, String limit, String offset, List<String> lang) {
        return null;
    }

    @Override
    public Collections findCollections(String agencyID, String resourceID, String query, String orderBy, String limit, String offset, List<String> lang) {
        return null;
    }

    @Override
    public Queries findQueries(String agencyID, String query, String orderBy, String limit, String offset, List<String> lang) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Queries findQueries(String query, String orderBy, String limit, String offset, List<String> lang) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection retrieveCollection(String agencyID, String resourceID, String version, List<String> lang, String fields) {
        try {
            PublicationVersion publicationVersion = commonService.retrievePublicationVersion(agencyID, resourceID, version);

            boolean includeMetadata = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_METADATA);
            boolean includeData = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_DATA);
            Collection collection = collectionsDo2RestMapper.toCollection(publicationVersion, lang, includeMetadata, includeData);
            return collection;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    @Override
    public Query retrieveQuery(String agencyID, String resourceID, List<String> lang, String fields) {
        try {
            QueryVersion queryVersion = commonService.retrieveQueryVersion(agencyID, resourceID);

            boolean includeMetadata = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_METADATA);
            boolean includeData = !hasField(fields, RestExternalConstants.FIELD_EXCLUDE_DATA);
            Query query = queriesDo2RestMapper.toQuery(queryVersion, lang, includeMetadata, includeData);
            return query;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private Datasets findDatasets(String agencyID, String resourceID, String version, String query, String orderBy, String limit, String offset, List<String> lang) {
        try {
            SculptorCriteria sculptorCriteria = datasetsRest2DoMapper.getDatasetCriteriaMapper().restCriteriaToSculptorCriteria(query, orderBy, limit, offset);

            // Find
            PagedResult<DatasetVersion> entitiesPagedResult = commonService.findDatasetVersions(agencyID, resourceID, version, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

            // Transform
            Datasets datasets = datasetsDo2RestMapper.toDatasets(entitiesPagedResult, agencyID, resourceID, query, orderBy, sculptorCriteria.getLimit(), lang);
            return datasets;
        } catch (Exception e) {
            throw manageException(e);
        }
    }
}
