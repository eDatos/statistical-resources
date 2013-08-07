package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QuerySculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.security.DatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.PublicationsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.QueriesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of StatisticalResourcesServiceFacade.
 */
@Service("statisticalResourcesServiceFacade")
public class StatisticalResourcesServiceFacadeImpl extends StatisticalResourcesServiceFacadeImplBase {

    @Autowired
    private QueryDo2DtoMapper                                        queryDo2DtoMapper;

    @Autowired
    private QueryDto2DoMapper                                        queryDto2DoMapper;

    @Autowired
    private DatasetDo2DtoMapper                                      datasetDo2DtoMapper;

    @Autowired
    private DatasetDto2DoMapper                                      datasetDto2DoMapper;

    @Autowired
    private PublicationDo2DtoMapper                                  publicationDo2DtoMapper;

    @Autowired
    private PublicationDto2DoMapper                                  publicationDto2DoMapper;

    @Autowired
    private QueryVersionMetamacCriteria2SculptorCriteriaMapper       queryVersionMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private QueryVersionSculptorCriteria2MetamacCriteriaMapper       queryVersionSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private QueryMetamacCriteria2SculptorCriteriaMapper              queryMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private QuerySculptorCriteria2MetamacCriteriaMapper              querySculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private DatasetVersionMetamacCriteria2SculptorCriteriaMapper     datasetVersionMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private DatasetVersionSculptorCriteria2MetamacCriteriaMapper     datasetVersionSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private DatasetMetamacCriteria2SculptorCriteriaMapper            datasetMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private DatasetSculptorCriteria2MetamacCriteriaMapper            datasetSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private PublicationVersionMetamacCriteria2SculptorCriteriaMapper publicationVersionMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private PublicationSculptorCriteria2MetamacCriteriaMapper        publicationSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private PublicationMetamacCriteria2SculptorCriteriaMapper        publicationMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private PublicationVersionSculptorCriteria2MetamacCriteriaMapper publicationVersionSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private LifecycleService<DatasetVersion>                         datasetLifecycleService;

    @Autowired
    private LifecycleService<PublicationVersion>                     publicationLifecycleService;

    @Autowired
    private LifecycleService<QueryVersion>                           queryLifecycleService;

    public StatisticalResourcesServiceFacadeImpl() {
    }

    // ------------------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------------------

    @Override
    public MetamacCriteriaResult<RelatedResourceDto> findQueriesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = queryMetamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Add condition for latest queryVersion
        ConditionalCriteria latestQueryVersionRestriction = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class)
                .withProperty(QueryVersionProperties.lifeCycleStatisticalResource().lastVersion()).eq(Boolean.TRUE).buildSingle();
        sculptorCriteria.getConditions().add(latestQueryVersionRestriction);

        // Find
        PagedResult<QueryVersion> result = getQueryService().findQueryVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<RelatedResourceDto> metamacCriteriaResult = querySculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultQueryRelatedResourceDto(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    // ------------------------------------------------------------------------
    // QUERY VERSION
    // ------------------------------------------------------------------------

    @Override
    public QueryVersionDto retrieveQueryVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueryVersionByUrn(ctx);

        // Retrieve
        QueryVersion queryVersion = getQueryService().retrieveQueryVersionByUrn(ctx, urn);

        // Transform
        QueryVersionDto queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto retrieveLatestQueryVersion(ServiceContext ctx, String queryUrn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveLatestQueryVersion(ctx);

        // Retrieve
        QueryVersion query = getQueryService().retrieveLatestQueryVersionByQueryUrn(ctx, queryUrn);

        // Transform
        QueryVersionDto queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(query);
        return queryVersionDto;
    }

    @Override
    public QueryVersionDto retrieveLatestPublishedQueryVersion(ServiceContext ctx, String queryUrn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveLatestPublishedQueryVersion(ctx);

        // Retrieve
        QueryVersion query = getQueryService().retrieveLatestPublishedQueryVersionByQueryUrn(ctx, queryUrn);

        // Transform
        QueryVersionDto queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(query);
        return queryVersionDto;
    }

    @Override
    public List<QueryVersionDto> retrieveQueriesVersions(ServiceContext ctx) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueriesVersions(ctx);

        // Retrieve
        List<QueryVersion> queryVersions = getQueryService().retrieveQueryVersions(ctx);

        // Transform
        List<QueryVersionDto> queriesDto = queryDo2DtoMapper.queryVersionDoListToDtoList(queryVersions);

        return queriesDto;
    }

    @Override
    public QueryVersionDto createQuery(ServiceContext ctx, QueryVersionDto queryVersionDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCreateQuery(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);
        ExternalItem statisticalOperation = datasetDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionSingleParameters.STATISTICAL_OPERATION);

        // Create
        queryVersion = getQueryService().createQueryVersion(ctx, queryVersion, statisticalOperation);

        // Transform to DTO
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto updateQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canUpdateQueryVersion(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Update
        queryVersion = getQueryService().updateQueryVersion(ctx, queryVersion);

        // Transform to Dto
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public MetamacCriteriaResult<QueryVersionDto> findQueriesVersionsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = queryVersionMetamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<QueryVersion> result = getQueryService().findQueryVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<QueryVersionDto> metamacCriteriaResult = queryVersionSculptorCriteria2MetamacCriteriaMapper
                .pageResultToMetamacCriteriaResultQuery(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public QueryVersionDto markQueryVersionAsDiscontinued(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Update
        queryVersion = getQueryService().markQueryVersionAsDiscontinued(ctx, queryVersion);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public void deleteQueryVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canDeleteQueryVersion(ctx);

        // Delete
        getQueryService().deleteQueryVersion(ctx, urn);
    }

    @Override
    public QueryVersionDto sendQueryVersionToProductionValidation(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToProductionValidation(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToProductionValidation(ctx, queryVersion);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto sendQueryVersionToDiffusionValidation(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToDiffusionValidation(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToDiffusionValidation(ctx, queryVersion);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto sendQueryVersionToValidationRejected(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToValidationRejected(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToValidationRejected(ctx, queryVersion);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
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
    public MetamacCriteriaResult<RelatedResourceDto> findDatasetsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFindDatasetsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = datasetMetamacCriteria2SculptorCriteriaMapper.getDatasetCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Add condition for latest datasetVersions
        ConditionalCriteria latestDatasetVersionRestriction = ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class)
                .withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion()).eq(Boolean.TRUE).buildSingle();
        sculptorCriteria.getConditions().add(latestDatasetVersionRestriction);

        // Find
        PagedResult<DatasetVersion> result = getDatasetService().findDatasetVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<RelatedResourceDto> metamacCriteriaResult = datasetSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultDatasetRelatedResourceDto(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    public DatasetVersionDto createDataset(ServiceContext ctx, DatasetVersionDto datasetVersionDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCreateDataset(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);
        ExternalItem statisticalOperation = datasetDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionSingleParameters.STATISTICAL_OPERATION);

        // Retrieve
        DatasetVersion datasetVersionCreated = getDatasetService().createDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Transform
        DatasetVersionDto datasetCreated = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersionCreated);

        return datasetCreated;
    }

    @Override
    public DatasetVersionDto updateDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canUpdateDatasetVersion(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // //Update
        datasetVersion = getDatasetService().updateDatasetVersion(ctx, datasetVersion);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public void deleteDatasetVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canDeleteDatasetVersion(ctx);

        // Delete
        getDatasetService().deleteDatasetVersion(ctx, urn);
    }

    @Override
    public MetamacCriteriaResult<DatasetVersionDto> findDatasetsVersionsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFindDatasetsVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = datasetVersionMetamacCriteria2SculptorCriteriaMapper.getDatasetVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<DatasetVersion> result = getDatasetService().findDatasetVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<DatasetVersionDto> metamacCriteriaResult = datasetVersionSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultDatasetVersion(ctx, result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public DatasetVersionDto retrieveDatasetVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionByUrn(ctx);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, urn);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public List<DatasetVersionDto> retrieveDatasetVersions(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersions(ctx);

        // Retrieve
        List<DatasetVersion> datasetVersions = getDatasetService().retrieveDatasetVersions(ctx, datasetVersionUrn);

        // Transform
        List<DatasetVersionDto> datasets = new ArrayList<DatasetVersionDto>();
        for (DatasetVersion version : datasetVersions) {
            datasets.add(datasetDo2DtoMapper.datasetVersionDoToDto(ctx, version));
        }
        return datasets;
    }

    @Override
    public List<String> retrieveDatasetVersionDimensionsIds(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionDimensionsIds(ctx);

        return getDatasetService().retrieveDatasetVersionDimensionsIds(ctx, datasetVersionUrn);
    }

    @Override
    public List<CodeItemDto> retrieveCoverageForDatasetVersionDimension(ServiceContext ctx, String datasetVersionUrn, String dsdDimensionId) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveCoverageForDatasetVersionDimension(ctx);

        List<CodeDimension> codeDimensions = getDatasetService().retrieveCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dsdDimensionId);

        return datasetDo2DtoMapper.codeDimensionDoListToCodeItemDtoList(codeDimensions);
    }

    @Override
    public DatasetVersionDto versioningDatasetVersion(ServiceContext ctx, String urnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canVersionDataset(ctx);

        // Versioning
        DatasetVersion datasetVersion = getDatasetService().versioningDatasetVersion(ctx, urnToCopy, versionType);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public DatasetVersionDto sendDatasetVersionToProductionValidation(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToProductionValidation(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Send to production validation and retrieve
        datasetVersion = datasetLifecycleService.sendToProductionValidation(ctx, datasetVersion);

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto sendDatasetVersionToDiffusionValidation(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToDiffusionValidation(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Send to production validation and retrieve
        datasetVersion = datasetLifecycleService.sendToDiffusionValidation(ctx, datasetVersion);

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto retrieveLatestDatasetVersion(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveLatestDatasetVersion(ctx);

        // Retrieve
        DatasetVersion dataset = getDatasetService().retrieveLatestDatasetVersionByDatasetUrn(ctx, datasetUrn);

        // Transform
        DatasetVersionDto datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, dataset);
        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto retrieveLatestPublishedDatasetVersion(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveLatestPublishedDatasetVersion(ctx);

        // Retrieve
        DatasetVersion dataset = getDatasetService().retrieveLatestPublishedDatasetVersionByDatasetUrn(ctx, datasetUrn);

        // Transform
        DatasetVersionDto datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, dataset);
        return datasetVersionDto;
    }

    @Override
    public List<StatisticOfficialityDto> findStatisticOfficialities(ServiceContext ctx) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFindStatisticOfficialities(ctx);

        // Retrieve
        List<StatisticOfficiality> statisticOfficialities = getDatasetService().findStatisticOfficialities(ctx);

        // Transform
        return datasetDo2DtoMapper.statisticOfficialityDoList2DtoList(statisticOfficialities);
    }

    @Override
    public void importDatasourcesInDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto, List<URL> fileUrls) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canImportDatasourcesInDatasetVersion(ctx, datasetVersionDto);

        // Transform for optimistic locking
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Service
        getDatasetService().importDatasourcesInDatasetVersion(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), fileUrls);
    }
    
    @Override
    public void importDatasourcesInStatisticalOperation(ServiceContext ctx, String statisticalOperationUrn, List<URL> fileUrls) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canImportDatasourcesInStatisticalOperation(ctx, statisticalOperationUrn);
        
        getDatasetService().importDatasourcesInStatisticalOperation(ctx, statisticalOperationUrn, fileUrls);
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Override
    public MetamacCriteriaResult<RelatedResourceDto> findPublicationsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canFindPublicationsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = publicationMetamacCriteria2SculptorCriteriaMapper.getPublicationCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Add condition for latest queryVersion
        ConditionalCriteria latestPublicationVersionRestriction = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().lastVersion()).eq(Boolean.TRUE).buildSingle();
        sculptorCriteria.getConditions().add(latestPublicationVersionRestriction);

        // Find
        PagedResult<PublicationVersion> result = getPublicationService().findPublicationVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<RelatedResourceDto> metamacCriteriaResult = publicationSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultPublicationRelatedResourceDto(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS VERSIONS
    // ------------------------------------------------------------------------

    @Override
    public PublicationVersionDto createPublication(ServiceContext ctx, PublicationVersionDto publicationVersionDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCreatePublication(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);
        ExternalItem statisticalOperation = publicationDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionSingleParameters.STATISTICAL_OPERATION);

        // Retrieve
        PublicationVersion publicationVersionCreated = getPublicationService().createPublicationVersion(ctx, publicationVersion, statisticalOperation);

        // Transform
        PublicationVersionDto publicationCreated = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersionCreated);

        return publicationCreated;
    }

    @Override
    public PublicationVersionDto updatePublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdatePublicationVersion(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // //Update
        publicationVersion = getPublicationService().updatePublicationVersion(ctx, publicationVersion);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public void deletePublicationVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canDeletePublicationVersion(ctx);

        // Delete
        getPublicationService().deletePublicationVersion(ctx, urn);

    }

    @Override
    public MetamacCriteriaResult<PublicationVersionDto> findPublicationVersionByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canFindPublicationsVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = publicationVersionMetamacCriteria2SculptorCriteriaMapper.getPublicationVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<PublicationVersion> result = getPublicationService().findPublicationVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<PublicationVersionDto> metamacCriteriaResult = publicationVersionSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultPublicationVersion(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public PublicationVersionDto retrievePublicationVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersionByUrn(ctx);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, urn);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public PublicationVersionDto retrieveLatestPublicationVersion(ServiceContext ctx, String publicationUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrieveLatestPublicationVersion(ctx);

        // Retrieve
        PublicationVersion publication = getPublicationService().retrieveLatestPublicationVersionByPublicationUrn(ctx, publicationUrn);

        // Transform
        PublicationVersionDto publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publication);
        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto retrieveLatestPublishedPublicationVersion(ServiceContext ctx, String publicationUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrieveLatestPublishedPublicationVersion(ctx);

        // Retrieve
        PublicationVersion publication = getPublicationService().retrieveLatestPublishedPublicationVersionByPublicationUrn(ctx, publicationUrn);

        // Transform
        PublicationVersionDto publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publication);
        return publicationVersionDto;
    }

    @Override
    public List<PublicationVersionDto> retrievePublicationVersions(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersions(ctx);

        // Retrieve
        List<PublicationVersion> publicationVersions = getPublicationService().retrievePublicationVersions(ctx, urn);

        // Transform
        List<PublicationVersionDto> publications = publicationDo2DtoMapper.publicationVersionDoListToDtoList(publicationVersions);

        return publications;
    }

    @Override
    public PublicationVersionDto versioningPublicationVersion(ServiceContext ctx, String publicationVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canVersionPublication(ctx);

        // Versioning
        PublicationVersion publicationVersion = getPublicationService().versioningPublicationVersion(ctx, publicationVersionUrnToCopy, versionType);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public PublicationStructureDto retrievePublicationVersionStructure(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersionStructure(ctx);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Build structure
        PublicationStructureDto publicationStructureDto = publicationDo2DtoMapper.publicationVersionStructureDoToDto(publicationVersion);

        return publicationStructureDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToProductionValidation(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToProductionValidation(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToProductionValidation(ctx, publicationVersion);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToDiffusionValidation(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToDiffusionValidation(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToDiffusionValidation(ctx, publicationVersion);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToValidationRejected(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToValidationRejected(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToValidationRejected(ctx, publicationVersion);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    @Override
    public ChapterDto createChapter(ServiceContext ctx, String publicationUrn, ChapterDto chapterDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCreateChapter(ctx);

        // Transform
        Chapter chapter = publicationDto2DoMapper.chapterDtoToDo(chapterDto);

        // Create
        chapter = getPublicationService().createChapter(ctx, publicationUrn, chapter);

        // Transform to dto
        chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto updateChapter(ServiceContext ctx, ChapterDto chapterDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdateChapter(ctx);

        // Transform
        Chapter chapter = publicationDto2DoMapper.chapterDtoToDo(chapterDto);

        // Update
        chapter = getPublicationService().updateChapter(ctx, chapter);

        // Transform
        chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto updateChapterLocation(ServiceContext ctx, String chapterUrn, String parentTargetUrn, Long orderInLevel) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdateChapterLocation(ctx);

        // Update
        Chapter chapter = getPublicationService().updateChapterLocation(ctx, chapterUrn, parentTargetUrn, orderInLevel);

        // Transform to dto
        ChapterDto chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto retrieveChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrieveChapter(ctx);

        // Retrieve
        Chapter chapter = getPublicationService().retrieveChapter(ctx, chapterUrn);

        // Transform
        ChapterDto chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public void deleteChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canDeleteChapter(ctx);

        // Delete
        getPublicationService().deleteChapter(ctx, chapterUrn);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    @Override
    public CubeDto createCube(ServiceContext ctx, String publicationUrn, CubeDto cubeDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCreateCube(ctx);

        // Transform
        Cube cube = publicationDto2DoMapper.cubeDtoToDo(cubeDto);

        // Create
        cube = getPublicationService().createCube(ctx, publicationUrn, cube);

        // Transform
        cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;

    }

    @Override
    public CubeDto updateCube(ServiceContext ctx, CubeDto cubeDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdateCube(ctx);

        // Transform
        Cube cube = publicationDto2DoMapper.cubeDtoToDo(cubeDto);

        // Update
        cube = getPublicationService().updateCube(ctx, cube);

        // Transform
        cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;
    }

    @Override
    public CubeDto updateCubeLocation(ServiceContext ctx, String cubeUrn, String parentTargetUrn, Long orderInLevel) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdateCubeLocation(ctx);

        // Update
        Cube cube = getPublicationService().updateCubeLocation(ctx, cubeUrn, parentTargetUrn, orderInLevel);

        // Transform
        CubeDto cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;
    }

    @Override
    public CubeDto retrieveCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrieveCube(ctx);

        // Retrieve
        Cube cube = getPublicationService().retrieveCube(ctx, cubeUrn);

        // Transform
        CubeDto cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;

    }

    @Override
    public void deleteCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canDeleteCube(ctx);

        // Delete
        getPublicationService().deleteCube(ctx, cubeUrn);
    }
}
