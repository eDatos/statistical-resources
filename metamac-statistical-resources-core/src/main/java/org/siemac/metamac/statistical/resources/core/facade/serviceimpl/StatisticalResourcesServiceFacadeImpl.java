package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
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
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QuerySculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
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
    private BaseDto2DoMapper                                         baseDto2DoMapper;

    @Autowired
    private QueryMetamacCriteria2SculptorCriteriaMapper              queryMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private QuerySculptorCriteria2MetamacCriteriaMapper              querySculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private DatasetVersionMetamacCriteria2SculptorCriteriaMapper     datasetMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private DatasetVersionSculptorCriteria2MetamacCriteriaMapper     datasetSculptorCriteria2MetamacCriteriaMapper;

    @Autowired
    private PublicationVersionMetamacCriteria2SculptorCriteriaMapper publicationMetamacCriteria2SculptorCriteriaMapper;

    @Autowired
    private PublicationVersionSculptorCriteria2MetamacCriteriaMapper publicationSculptorCriteria2MetamacCriteriaMapper;

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
        QueryVersion queryVersion = getQueryService().retrieveQueryVersionByUrn(ctx, urn);

        // Transform
        QueryDto queryDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryDto;
    }

    @Override
    public List<QueryDto> retrieveQueries(ServiceContext ctx) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueries(ctx);

        // Retrieve
        List<QueryVersion> queryVersions = getQueryService().retrieveQueryVersions(ctx);

        // Transform
        List<QueryDto> queriesDto = queryDo2DtoMapper.queryVersionDoListToDtoList(queryVersions);

        return queriesDto;
    }

    @Override
    public QueryDto createQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCreateQuery(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryDto);

        // Create
        queryVersion = getQueryService().createQueryVersion(ctx, queryVersion);

        // Transform to DTO
        queryDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryDto;
    }

    @Override
    public QueryDto updateQuery(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canUpdateQuery(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryDto);

        // Update
        queryVersion = getQueryService().updateQueryVersion(ctx, queryVersion);

        // Transform to Dto
        queryDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryDto;
    }

    @Override
    public MetamacCriteriaResult<QueryDto> findQueriesByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = queryMetamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<QueryVersion> result = getQueryService().findQueryVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<QueryDto> metamacCriteriaResult = querySculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultQuery(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public QueryDto markQueryAsDiscontinued(ServiceContext ctx, QueryDto queryDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canMarkQueryAsDiscontinued(ctx);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryDto);

        // Update
        queryVersion = getQueryService().markQueryVersionAsDiscontinued(ctx, queryVersion);

        // Transform
        queryDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryDto;
    }

    @Override
    public void deleteQuery(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        QueriesSecurityUtils.canDeleteQuery(ctx);

        // Delete
        getQueryService().deleteQueryVersion(ctx, urn);
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
        // Security
        DatasetsSecurityUtils.canUpdateDataset(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetDto);

        // //Update
        datasetVersion = getDatasetService().updateDatasetVersion(ctx, datasetVersion);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

    @Override
    public void deleteDataset(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canDeleteDataset(ctx);

        // Delete
        getDatasetService().deleteDatasetVersion(ctx, urn);
    }

    @Override
    public MetamacCriteriaResult<DatasetDto> findDatasetsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFindDatasetsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = datasetMetamacCriteria2SculptorCriteriaMapper.getDatasetVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<DatasetVersion> result = getDatasetService().findDatasetVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<DatasetDto> metamacCriteriaResult = datasetSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultDatasetVersion(result, sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public DatasetDto retrieveDatasetByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetByUrn(ctx);

        // Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, urn);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

    @Override
    public List<DatasetDto> retrieveDatasetVersions(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersions(ctx);

        // Retrieve
        List<DatasetVersion> datasetVersions = getDatasetService().retrieveDatasetVersions(ctx, urn);

        // Transform
        List<DatasetDto> datasets = new ArrayList<DatasetDto>();
        for (DatasetVersion version : datasetVersions) {
            datasets.add(datasetDo2DtoMapper.datasetVersionDoToDto(version));
        }
        return datasets;
    }

    @Override
    public DatasetDto versioningDataset(ServiceContext ctx, String urnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canVersionDataset(ctx);

        // Versioning
        DatasetVersion datasetVersion = getDatasetService().versioningDatasetVersion(ctx, urnToCopy, versionType);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);
    }

    @Override
    public DatasetDto sendToProductionValidation(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendToProductionValidation(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetDto);

        // Send to production validation and retrieve
        getDatasetLifecycleService().sendToProductionValidation(ctx, datasetVersion);
        datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetDto.getUrn());

        // Transform
        datasetDto = datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);

        return datasetDto;
    }

    @Override
    public DatasetDto sendToDiffusionValidation(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendToDiffusionValidation(ctx);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetDto);

        // Send to production validation and retrieve
        getDatasetLifecycleService().sendToDiffusionValidation(ctx, datasetVersion);
        datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetDto.getUrn());

        // Transform
        datasetDto = datasetDo2DtoMapper.datasetVersionDoToDto(datasetVersion);

        return datasetDto;
    }

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Override
    public PublicationDto createPublication(ServiceContext ctx, PublicationDto publicationDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCreatePublication(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationDto);
        ExternalItem statisticalOperation = baseDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionSingleParameters.STATISTICAL_OPERATION);

        // Retrieve
        PublicationVersion publicationVersionCreated = getPublicationService().createPublicationVersion(ctx, publicationVersion, statisticalOperation);

        // Transform
        PublicationDto publicationCreated = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersionCreated);

        return publicationCreated;
    }

    @Override
    public PublicationDto updatePublication(ServiceContext ctx, PublicationDto publicationDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdatePublication(ctx);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationDto);

        // //Update
        publicationVersion = getPublicationService().updatePublicationVersion(ctx, publicationVersion);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public void deletePublication(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canDeletePublication(ctx);

        // Delete
        getPublicationService().deletePublicationVersion(ctx, urn);

    }

    @Override
    public MetamacCriteriaResult<PublicationDto> findPublicationByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canFindPublicationsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = publicationMetamacCriteria2SculptorCriteriaMapper.getPublicationVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<PublicationVersion> result = getPublicationService().findPublicationVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<PublicationDto> metamacCriteriaResult = publicationSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultPublicationVersion(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public PublicationDto retrievePublicationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationByUrn(ctx);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, urn);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public List<PublicationDto> retrievePublicationVersions(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersions(ctx);

        // Retrieve
        List<PublicationVersion> publicationVersions = getPublicationService().retrievePublicationVersions(ctx, urn);

        // Transform
        List<PublicationDto> publications = publicationDo2DtoMapper.publicationVersionDoListToDtoList(publicationVersions);

        return publications;
    }

    @Override
    public PublicationDto versioningPublication(ServiceContext ctx, String urnToCopy, VersionTypeEnum versionType) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canVersionPublication(ctx);

        // Versioning
        PublicationVersion publicationVersion = getPublicationService().versioningPublicationVersion(ctx, urnToCopy, versionType);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecha
    public List<String> findDatasetVersionForPublicationVersion(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Security
        // not security

        // Service call
        List<DatasetVersion> datasetsVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationVersionUrn).getDatasets();

        // Transform to Dto
        List<String> datasetVersionsUrn = datasetVersionSetDo2UrnList(datasetsVersion);

        // Return
        return datasetVersionsUrn;
    }

    @Override
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecha
    public List<String> addDatasetVersionToPublicationVersion(ServiceContext ctx, String publicationVersionUrn, String datasetVersionUrn) throws MetamacException {
        // Security
        // not security

        // Service call
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        publicationVersion.addDataset(datasetVersion);

        getPublicationService().updatePublicationVersion(ctx, publicationVersion);

        // Return
        return findDatasetVersionForPublicationVersion(ctx, publicationVersionUrn);
    }

    @Override
    // TODO: Este metodo debe ser eliminado cuando la jerarquia este bien hecha
    public List<String> removeDatasetVersionToPublicationVersion(ServiceContext ctx, String publicationVersionUrn, String datasetVersionUrn) throws MetamacException {
        // Security
        // not security

        // Service call
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        publicationVersion.removeDataset(datasetVersion);

        getPublicationService().updatePublicationVersion(ctx, publicationVersion);

        // Return
        return findDatasetVersionForPublicationVersion(ctx, publicationVersionUrn);
    }

    private List<String> datasetVersionSetDo2UrnList(Collection<DatasetVersion> datasetsVersion) {
        List<String> datasetVersionUrnList = new ArrayList<String>();
        for (DatasetVersion item : datasetsVersion) {
            datasetVersionUrnList.add(item.getSiemacMetadataStatisticalResource().getUrn());
        }
        return datasetVersionUrnList;
    }

}
