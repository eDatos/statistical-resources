package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.PublicationDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QuerySculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.security.DatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.QueriesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourcesServiceFacade.
 */
@Service("statisticalResourcesServiceFacade")
public class StatisticalResourcesServiceFacadeImpl extends StatisticalResourcesServiceFacadeImplBase {

    @Autowired
    @Qualifier("queryDo2DtoMapper")
    private QueryDo2DtoMapper                           queryDo2DtoMapper;

    @Autowired
    @Qualifier("queryDto2DoMapper")
    private QueryDto2DoMapper                           queryDto2DoMapper;

    @Autowired
    @Qualifier("datasetDo2DtoMapper")
    private PublicationDo2DtoMapper                         datasetDo2DtoMapper;

    @Autowired
    @Qualifier("datasetDto2DoMapper")
    private DatasetDto2DoMapper                         datasetDto2DoMapper;
    
    @Autowired
    @Qualifier("baseDto2DoMapper")
    private BaseDto2DoMapper                            baseDto2DoMapper;

    @Autowired
    private QueryMetamacCriteria2SculptorCriteriaMapper queryMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private QuerySculptorCriteria2MetamacCriteriaMapper querySculptorCriteria2MetamacCriteriaMapper;
    
    @Autowired
    private DatasetVersionMetamacCriteria2SculptorCriteriaMapper datasetMetamacCriteria2SculptorCriteriaMapper;
    
    @Autowired
    private DatasetVersionSculptorCriteria2MetamacCriteriaMapper datasetSculptorCriteria2MetamacCriteriaMapper;

    public StatisticalResourcesServiceFacadeImpl() {
    }

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Override
    public QueryDto retrieveQueryByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueryByUrn(ctx);

        // Retrieve
        Query query = getQueryService().retrieveQueryByUrn(ctx, urn);

        // Transform
        QueryDto queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public List<QueryDto> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueries(ctx);

        // Retrieve
        List<Query> queries = getQueryService().retrieveQueries(ctx);

        // Transform
        List<QueryDto> queriesDto = queryDo2DtoMapper.queryDoListToDtoList(queries);

        return queriesDto;
    }

    @Override
    public QueryDto createQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCreateQuery(ctx);

        // Transform
        Query query = queryDto2DoMapper.queryDtoToDo(queryDto);

        // Create
        query = getQueryService().createQuery(ctx, query);

        // Transform to DTO
        queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public QueryDto updateQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canUpdateQuery(ctx);

        // Transform
        Query query = queryDto2DoMapper.queryDtoToDo(queryDto);

        // Update
        query = getQueryService().updateQuery(ctx, query);

        // Transform to Dto
        queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public MetamacCriteriaResult<QueryDto> findQueriesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = queryMetamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<Query> result = getQueryService().findQueriesByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<QueryDto> metamacCriteriaResult = querySculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultQuery(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public QueryDto markQueryAsDiscontinued(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canMarkQueryAsDiscontinued(ctx);

        // Transform
        Query query = queryDto2DoMapper.queryDtoToDo(queryDto);

        // Update
        query = getQueryService().markQueryAsDiscontinued(ctx, query);

        // Transform
        queryDto = queryDo2DtoMapper.queryDoToDto(query);

        return queryDto;
    }

    @Override
    public void deleteQuery(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canDeleteQuery(ctx);
        
        // Delete
        getQueryService().deleteQuery(ctx, urn);
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    public DatasourceDto createDatasource(ServiceContext ctx, String urnDatasetVersion, DatasourceDto datasourceDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCreateDatasource(ctx);

        // Transform
        Datasource datasource = datasetDto2DoMapper.datasourceDtoToDo(datasourceDto);

        // Create
        datasource = getDatasetService().createDatasource(ctx, urnDatasetVersion, datasource);

        // Transform to DTO
        datasourceDto = datasetDo2DtoMapper.datasourceDoToDto(datasource);

        return datasourceDto;
    }

    @Override
    public DatasourceDto updateDatasource(ServiceContext ctx, DatasourceDto datasourceDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canUpdateDatasource(ctx);

        // Transform
        Datasource datasource = datasetDto2DoMapper.datasourceDtoToDo(datasourceDto);

        // Update
        datasource = getDatasetService().updateDatasource(ctx, datasource);

        // Transform to Dtos
        datasourceDto = datasetDo2DtoMapper.datasourceDoToDto(datasource);

        return datasourceDto;
    }

    @Override
    public DatasourceDto retrieveDatasourceByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasourceByUrn(ctx);

        // Retrieve
        Datasource datasource = getDatasetService().retrieveDatasourceByUrn(ctx, urn);

        // Transform
        DatasourceDto datasourceDto = datasetDo2DtoMapper.datasourceDoToDto(datasource);

        return datasourceDto;
    }

    @Override
    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canDeleteDatasource(ctx);

        // Delete
        getDatasetService().deleteDatasource(ctx, urn);
    }

    @Override
    public List<DatasourceDto> retrieveDatasourcesByDatasetVersion(ServiceContext ctx, String urnDatasetVersion) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasourcesByDatasetVersion(ctx);

        // Retrieve
        List<Datasource> datasources = getDatasetService().retrieveDatasourcesByDatasetVersion(ctx, urnDatasetVersion);

        // Transform
        List<DatasourceDto> datasourcesDto = datasetDo2DtoMapper.datasourceDoListToDtoList(datasources);

        return datasourcesDto;
    }

    // ------------------------------------------------------------------------
    // DATASETS
    // ------------------------------------------------------------------------

    @Override
    public DatasetDto createDataset(ServiceContext ctx, DatasetDto datasetDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCreateDataset(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetDto);
        ExternalItem statisticalOperation = baseDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionSingleParameters.STATISTICAL_OPERATION);

        // Retrieve
        DatasetVersion datasetVersionCreated = getDatasetService().createDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Transform
        DatasetDto datasetCreated = datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersionCreated);

        return datasetCreated;
    }

    @Override
    public DatasetDto updateDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canUpdateDataset(ctx);
        
        //Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetDto);
        
        ////Update
        datasetVersion = getDatasetService().updateDatasetVersion(ctx, datasetVersion);
        
        
        //Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

    @Override
    public void deleteDataset(ServiceContext ctx, String urn) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canDeleteDataset(ctx);
        
        //Delete
        getDatasetService().deleteDatasetVersion(ctx, urn);
    }

    @Override
    public MetamacCriteriaResult<DatasetDto> findDatasetsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canFindDatasetsByCondition(ctx);
        
        //Transform
        SculptorCriteria sculptorCriteria = datasetMetamacCriteria2SculptorCriteriaMapper.getDatasetVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);
        
        //Find
        PagedResult<DatasetVersion> result = getDatasetService().findDatasetVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());
        
        // Transform
        MetamacCriteriaResult<DatasetDto> metamacCriteriaResult = datasetSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultDatasetVersion(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public DatasetDto retrieveDatasetByUrn(ServiceContext ctx, String urn) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canRetrieveDatasetByUrn(ctx);
        
        //Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, urn);
        
        //Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

    @Override
    public List<DatasetDto> retrieveDatasetVersions(ServiceContext ctx, String urn) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canRetrieveDatasetVersions(ctx);
        
        //Retrieve
        List<DatasetVersion> datasetVersions = getDatasetService().retrieveDatasetVersions(ctx, urn);
        
        //Transform
        List<DatasetDto> datasets = new ArrayList<DatasetDto>();
        for (DatasetVersion version : datasetVersions) {
            datasets.add(datasetDo2DtoMapper.datasetVersionDoToDto(version));
        }
        return datasets;
    }

    @Override
    public DatasetDto versioningDataset(ServiceContext ctx, String urnToCopy, VersionTypeEnum versionType) throws MetamacException {
        //Security
        DatasetsSecurityUtils.canVersionDataset(ctx);
        
        //Versioning
        DatasetVersion datasetVersion = getDatasetService().versioningDatasetVersion(ctx, urnToCopy, versionType);
        
        //Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

}
