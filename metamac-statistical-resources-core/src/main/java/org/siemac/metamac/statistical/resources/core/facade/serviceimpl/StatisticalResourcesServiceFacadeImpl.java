package org.siemac.metamac.statistical.resources.core.facade.serviceimpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.criteria.SculptorCriteria;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
import org.siemac.metamac.statistical.resources.core.constraint.mapper.ConstraintDto2RestMapper;
import org.siemac.metamac.statistical.resources.core.constraint.mapper.ConstraintRest2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper.DatasetVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.StatRepoDto2StatisticalResourcesDtoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.StatisticalResourcesDto2StatRepoDtoMapper;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintBasicDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionMainCoveragesDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.criteria.mapper.PublicationVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.publication.mapper.PublicationDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QuerySculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryVersionMetamacCriteria2SculptorCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.criteria.mapper.QueryVersionSculptorCriteria2MetamacCriteriaMapper;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.query.mapper.QueryDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.security.ConstraintsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.DatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.PublicationsSecurityUtils;
import org.siemac.metamac.statistical.resources.core.security.QueriesSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;

/**
 * Implementation of StatisticalResourcesServiceFacade.
 */
@Service("statisticalResourcesServiceFacade")
public class StatisticalResourcesServiceFacadeImpl extends StatisticalResourcesServiceFacadeImplBase {

    @Autowired
    private CommonDo2DtoMapper                                       commonDo2DtoMapper;

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
    private ConstraintDto2RestMapper                                 constraintDto2RestMapper;

    @Autowired
    private ConstraintRest2DtoMapper                                 constraintRest2DtoMapper;

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

    @Autowired
    private StatisticalResourcesDto2StatRepoDtoMapper                statisticalResourcesDto2StatRepoDtoMapper;

    @Autowired
    private StatRepoDto2StatisticalResourcesDtoMapper                statRepoDto2StatisticalResourcesDtoMapper;

    @Autowired
    private SrmRestInternalService                                   srmRestInternalService;

    @Autowired
    private ConstraintsService                                       contraintsService;

    @Autowired
    private DatasetVersionRepository                                 datasetVersionRepository;
    @Autowired
    private PublicationVersionRepository                             publicationVersionRepository;
    @Autowired
    private QueryVersionRepository                                   queryVersionRepository;

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
        // Retrieve
        QueryVersion queryVersion = getQueryService().retrieveQueryVersionByUrn(ctx, urn);

        // Security
        QueriesSecurityUtils.canRetrieveQueryVersionByUrn(ctx, queryVersion);

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
    public List<QueryVersionBaseDto> retrieveQueriesVersions(ServiceContext ctx) throws MetamacException {
        // Security
        QueriesSecurityUtils.canRetrieveQueriesVersions(ctx);

        // Retrieve
        List<QueryVersion> queryVersions = getQueryService().retrieveQueryVersions(ctx);

        // Transform
        List<QueryVersionBaseDto> queriesDto = queryDo2DtoMapper.queryVersionDoListToDtoList(queryVersions);

        return queriesDto;
    }

    @Override
    public QueryVersionDto createQuery(ServiceContext ctx, QueryVersionDto queryVersionDto, ExternalItemDto statisticalOperationDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCreateQuery(ctx, statisticalOperationDto.getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);
        ExternalItem statisticalOperation = datasetDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionParameters.STATISTICAL_OPERATION);

        // Create
        queryVersion = getQueryService().createQueryVersion(ctx, queryVersion, statisticalOperation);

        // Transform to DTO
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto updateQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canUpdateQueryVersion(ctx, queryVersionDto);

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Update
        queryVersion = getQueryService().updateQueryVersion(ctx, queryVersion);

        // Transform to Dto
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public MetamacCriteriaResult<QueryVersionBaseDto> findQueriesVersionsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        QueriesSecurityUtils.canFindQueriesVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = queryVersionMetamacCriteria2SculptorCriteriaMapper.getQueryCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<QueryVersion> result = getQueryService().findQueryVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<QueryVersionBaseDto> metamacCriteriaResult = queryVersionSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultQuery(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public QueryVersionDto markQueryVersionAsDiscontinued(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canMarkQueryVersionAsDiscontinued(ctx, queryVersionDto);

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
        // Retrieve
        QueryVersionDto queryVersionDto = retrieveQueryVersionByUrn(ctx, urn);

        // Security
        QueriesSecurityUtils.canDeleteQueryVersion(ctx, queryVersionDto);

        // Delete
        getQueryService().deleteQueryVersion(ctx, urn);
    }

    @Override
    public QueryVersionDto sendQueryVersionToProductionValidation(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToProductionValidation(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToProductionValidation(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto sendQueryVersionToProductionValidation(ServiceContext ctx, QueryVersionBaseDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToProductionValidation(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        // Send to production validation and retrieve
        QueryVersion queryVersion = queryLifecycleService.sendToProductionValidation(ctx, queryVersionDto.getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto sendQueryVersionToDiffusionValidation(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToDiffusionValidation(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToDiffusionValidation(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto sendQueryVersionToDiffusionValidation(ServiceContext ctx, QueryVersionBaseDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToDiffusionValidation(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        // Send to production validation and retrieve
        QueryVersion queryVersion = queryLifecycleService.sendToDiffusionValidation(ctx, queryVersionDto.getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto sendQueryVersionToValidationRejected(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToValidationRejected(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to production validation and retrieve
        queryVersion = queryLifecycleService.sendToValidationRejected(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto sendQueryVersionToValidationRejected(ServiceContext ctx, QueryVersionBaseDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canSendQueryVersionToValidationRejected(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        // Send to production validation and retrieve
        QueryVersion queryVersion = queryLifecycleService.sendToValidationRejected(ctx, queryVersionDto.getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto publishQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canPublishQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        String urn = queryVersion.getLifeCycleStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        queryVersion = changeQueryVersionValidFromAndSave(urn, new DateTime());

        // Send to published
        queryVersion = queryLifecycleService.sendToPublished(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto publishQueryVersion(ServiceContext ctx, QueryVersionBaseDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canPublishQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        String urn = queryVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        QueryVersion queryVersion = changeQueryVersionValidFromAndSave(urn, new DateTime());

        // Send to published
        queryVersion = queryLifecycleService.sendToPublished(ctx, queryVersionDto.getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto programPublicationQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto, Date validFromDate) throws MetamacException {
        // Security
        QueriesSecurityUtils.canPublishQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform only for optimistic locking
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);
        String urn = queryVersion.getLifeCycleStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        queryVersion = changeQueryVersionValidFromAndSave(urn, validFromDate);

        queryVersion = queryLifecycleService.sendToPublished(ctx, urn);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto programPublicationQueryVersion(ServiceContext ctx, QueryVersionBaseDto queryVersionDto, Date validFromDate) throws MetamacException {
        // Security
        QueriesSecurityUtils.canPublishQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        String urn = queryVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        QueryVersion queryVersion = changeQueryVersionValidFromAndSave(urn, validFromDate);

        queryVersion = queryLifecycleService.sendToPublished(ctx, urn);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    private QueryVersion changeQueryVersionValidFromAndSave(String urn, Date validFrom) throws MetamacException {
        QueryVersion queryVersion = queryVersionRepository.retrieveByUrn(urn);
        queryVersion.getLifeCycleStatisticalResource().setValidFrom(queryDto2DoMapper.dateDtoToDo(validFrom));
        return queryVersionRepository.save(queryVersion);
    }

    private QueryVersion changeQueryVersionValidFromAndSave(String urn, DateTime validFrom) throws MetamacException {
        QueryVersion queryVersion = queryVersionRepository.retrieveByUrn(urn);
        queryVersion.getLifeCycleStatisticalResource().setValidFrom(validFrom);
        return queryVersionRepository.save(queryVersion);
    }

    @Override
    public QueryVersionDto cancelPublicationQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCancelPublicationQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Send to published
        queryVersion = queryLifecycleService.cancelPublication(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto cancelPublicationQueryVersion(ServiceContext ctx, QueryVersionBaseDto queryVersionDto) throws MetamacException {
        // Security
        QueriesSecurityUtils.canCancelPublicationQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        // Send to published
        QueryVersion queryVersion = queryLifecycleService.cancelPublication(ctx, queryVersionDto.getUrn());

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionDto versioningQueryVersion(ServiceContext ctx, QueryVersionDto queryVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        QueriesSecurityUtils.canVersionQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Transform
        QueryVersion queryVersion = queryDto2DoMapper.queryVersionDtoToDo(queryVersionDto);

        // Versioning
        queryVersion = queryLifecycleService.versioning(ctx, queryVersion.getLifeCycleStatisticalResource().getUrn(), versionType);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToDto(queryVersion);

        return queryVersionDto;
    }

    @Override
    public QueryVersionBaseDto versioningQueryVersion(ServiceContext ctx, QueryVersionBaseDto queryVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        QueriesSecurityUtils.canVersionQueryVersion(ctx, queryVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        queryDto2DoMapper.checkOptimisticLocking(queryVersionDto);

        // Versioning
        QueryVersion queryVersion = queryLifecycleService.versioning(ctx, queryVersionDto.getUrn(), versionType);

        // Transform
        queryVersionDto = queryDo2DtoMapper.queryVersionDoToBaseDto(queryVersion);

        return queryVersionDto;
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    @Override
    public DatasourceDto createDatasource(ServiceContext ctx, String urnDatasetVersion, DatasourceDto datasourceDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCreateDatasource(ctx, datasourceDto.getStatisticalOperation().getCode());

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
        DatasetsSecurityUtils.canUpdateDatasource(ctx, datasourceDto.getStatisticalOperation().getCode());

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
        // Retrieve
        Datasource datasource = getDatasetService().retrieveDatasourceByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canRetrieveDatasourceByUrn(ctx, datasource.getIdentifiableStatisticalResource().getStatisticalOperation().getCode());

        // Transform
        DatasourceDto datasourceDto = datasetDo2DtoMapper.datasourceDoToDto(datasource);

        return datasourceDto;
    }

    @Override
    public void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        String operationCode = getDatasetService().retrieveDatasourceByUrn(ctx, urn).getIdentifiableStatisticalResource().getStatisticalOperation().getCode();

        // Security
        DatasetsSecurityUtils.canDeleteDatasource(ctx, operationCode);

        // Delete
        getDatasetService().deleteDatasource(ctx, urn);
    }

    @Override
    public List<DatasourceDto> retrieveDatasourcesByDatasetVersion(ServiceContext ctx, String urnDatasetVersion) throws MetamacException {
        // Retrieve
        String operationCode = getDatasetService().retrieveDatasetVersionByUrn(ctx, urnDatasetVersion).getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        // Security
        DatasetsSecurityUtils.canRetrieveDatasourcesByDatasetVersion(ctx, operationCode);

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
        DatasetsSecurityUtils.canCreateDataset(ctx, statisticalOperationDto.getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);
        ExternalItem statisticalOperation = datasetDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionParameters.STATISTICAL_OPERATION);

        // Retrieve
        DatasetVersion datasetVersionCreated = getDatasetService().createDatasetVersion(ctx, datasetVersion, statisticalOperation);

        // Transform
        DatasetVersionDto datasetCreated = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersionCreated);

        return datasetCreated;
    }

    @Override
    public DatasetVersionDto updateDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canUpdateDatasetVersion(ctx, datasetVersionDto);

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Update
        datasetVersion = getDatasetService().updateDatasetVersion(ctx, datasetVersion);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public void deleteDatasetVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canDeleteDatasetVersion(ctx, datasetVersionDto);

        // Delete
        getDatasetService().deleteDatasetVersion(ctx, urn);
    }

    @Override
    public MetamacCriteriaResult<DatasetVersionBaseDto> findDatasetsVersionsByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFindDatasetsVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = datasetVersionMetamacCriteria2SculptorCriteriaMapper.getDatasetVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<DatasetVersion> result = getDatasetService().findDatasetVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<DatasetVersionBaseDto> metamacCriteriaResult = datasetVersionSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultDatasetVersion(ctx, result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public DatasetVersionDto retrieveDatasetVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionByUrn(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), datasetVersion
                .getSiemacMetadataStatisticalResource().getEffectiveProcStatus());

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public List<DatasetVersionBaseDto> retrieveDatasetVersions(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersions(ctx);

        // Retrieve
        List<DatasetVersion> datasetVersions = getDatasetService().retrieveDatasetVersions(ctx, datasetVersionUrn);

        // Transform
        List<DatasetVersionBaseDto> datasets = new ArrayList<DatasetVersionBaseDto>();
        for (DatasetVersion version : datasetVersions) {
            datasets.add(datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, version));
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
    public List<CodeItemDto> filterCoverageForDatasetVersionDimension(ServiceContext ctx, String datasetVersionUrn, String dsdDimensionId, String filter) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canFilterCoverageForDatasetVersionDimension(ctx);

        List<CodeDimension> codeDimensions = getDatasetService().filterCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dsdDimensionId, filter);

        return datasetDo2DtoMapper.codeDimensionDoListToCodeItemDtoList(codeDimensions);
    }

    @Override
    public DatasetVersionDto sendDatasetVersionToProductionValidation(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToProductionValidation(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Send to production validation and retrieve
        datasetVersion = datasetLifecycleService.sendToProductionValidation(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto sendDatasetVersionToProductionValidation(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToProductionValidation(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        // Send to production validation and retrieve
        DatasetVersion datasetVersion = datasetLifecycleService.sendToProductionValidation(ctx, datasetVersionDto.getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto sendDatasetVersionToDiffusionValidation(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToDiffusionValidation(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Send to production validation and retrieve
        datasetVersion = datasetLifecycleService.sendToDiffusionValidation(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto sendDatasetVersionToDiffusionValidation(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToDiffusionValidation(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        // Send to production validation and retrieve
        DatasetVersion datasetVersion = datasetLifecycleService.sendToDiffusionValidation(ctx, datasetVersionDto.getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto sendDatasetVersionToValidationRejected(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToValidationRejected(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Send to production validation and retrieve
        datasetVersion = datasetLifecycleService.sendToValidationRejected(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto sendDatasetVersionToValidationRejected(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canSendDatasetVersionToValidationRejected(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        // Send to production validation and retrieve
        DatasetVersion datasetVersion = datasetLifecycleService.sendToValidationRejected(ctx, datasetVersionDto.getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto publishDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canPublishDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        datasetVersion = changeDatasetVersionValidFromAndSave(datasetVersionUrn, new DateTime());

        datasetVersion = datasetLifecycleService.sendToPublished(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto publishDatasetVersion(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canPublishDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        String datasetVersionUrn = datasetVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        changeDatasetVersionValidFromAndSave(datasetVersionUrn, new DateTime());

        DatasetVersion datasetVersion = datasetLifecycleService.sendToPublished(ctx, datasetVersionDto.getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto programPublicationDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto, Date validFromDate) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canPublishDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform only for optimistic locking
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        datasetVersion = changeDatasetVersionValidFromAndSave(datasetVersionUrn, validFromDate);

        datasetVersion = datasetLifecycleService.sendToPublished(ctx, datasetVersionUrn);

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto programPublicationDatasetVersion(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto, Date validFromDate) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canPublishDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        String datasetVersionUrn = datasetVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        DatasetVersion datasetVersion = changeDatasetVersionValidFromAndSave(datasetVersionUrn, validFromDate);

        datasetVersion = datasetLifecycleService.sendToPublished(ctx, datasetVersionUrn);

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    private DatasetVersion changeDatasetVersionValidFromAndSave(String urn, Date validFrom) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionRepository.retrieveByUrn(urn);
        datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(datasetDto2DoMapper.dateDtoToDo(validFrom));
        return datasetVersionRepository.save(datasetVersion);
    }

    private DatasetVersion changeDatasetVersionValidFromAndSave(String urn, DateTime validFrom) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionRepository.retrieveByUrn(urn);
        datasetVersion.getSiemacMetadataStatisticalResource().setValidFrom(validFrom);
        return datasetVersionRepository.save(datasetVersion);
    }

    @Override
    public DatasetVersionDto cancelPublicationDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCancelPublicationDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        datasetVersion = datasetLifecycleService.cancelPublication(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionBaseDto cancelPublicationDatasetVersion(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canCancelPublicationDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        DatasetVersion datasetVersion = datasetLifecycleService.cancelPublication(ctx, datasetVersionDto.getUrn());

        // Transform
        datasetVersionDto = datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);

        return datasetVersionDto;
    }

    @Override
    public DatasetVersionDto versioningDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canVersionDataset(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Versioning
        datasetVersion = datasetLifecycleService.versioning(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), versionType);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToDto(ctx, datasetVersion);
    }

    @Override
    public DatasetVersionBaseDto versioningDatasetVersion(ServiceContext ctx, DatasetVersionBaseDto datasetVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canVersionDataset(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        datasetDto2DoMapper.checkOptimisticLocking(datasetVersionDto);

        // Versioning
        DatasetVersion datasetVersion = datasetLifecycleService.versioning(ctx, datasetVersionDto.getUrn(), versionType);

        // Transform
        return datasetDo2DtoMapper.datasetVersionDoToBaseDto(ctx, datasetVersion);
    }

    @Override
    public DatasetVersionDto retrieveLatestDatasetVersion(ServiceContext ctx, String datasetUrn) throws MetamacException {
        // Retrieve
        DatasetVersion dataset = getDatasetService().retrieveLatestDatasetVersionByDatasetUrn(ctx, datasetUrn);

        // Security
        DatasetsSecurityUtils.canRetrieveLatestDatasetVersion(ctx, dataset.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), dataset.getSiemacMetadataStatisticalResource()
                .getEffectiveProcStatus());

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
    public DatasetVersionMainCoveragesDto retrieveDatasetVersionMainCoverages(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionMainCoverages(ctx);

        // Retrieve
        DatasetVersion dataset = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Transform
        DatasetVersionMainCoveragesDto mainCoveragesDto = new DatasetVersionMainCoveragesDto();
        mainCoveragesDto.getGeographicCoverage().addAll(commonDo2DtoMapper.externalItemDoCollectionToDtoList(dataset.getGeographicCoverage()));
        mainCoveragesDto.getTemporalCoverage().addAll(commonDo2DtoMapper.temporalCodeDoCollectionToDtoList(dataset.getTemporalCoverage()));
        mainCoveragesDto.getMeasureCoverage().addAll(commonDo2DtoMapper.externalItemDoCollectionToDtoList(dataset.getMeasureCoverage()));

        return mainCoveragesDto;
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
    public void importDatasourcesInDatasetVersion(ServiceContext ctx, DatasetVersionDto datasetVersionDto, List<URL> fileUrls, Map<String, String> dimensionRepresentationMapping)
            throws MetamacException {
        // Security
        DatasetsSecurityUtils.canImportDatasourcesInDatasetVersion(ctx, datasetVersionDto.getStatisticalOperation().getCode());

        // Transform for optimistic locking
        DatasetVersion datasetVersion = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);

        // Service
        getDatasetService().importDatasourcesInDatasetVersion(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), fileUrls, dimensionRepresentationMapping);
    }

    @Override
    public void importDatasourcesInStatisticalOperation(ServiceContext ctx, String statisticalOperationCode, List<URL> fileUrls) throws MetamacException {
        // Security
        DatasetsSecurityUtils.canImportDatasourcesInStatisticalOperation(ctx, statisticalOperationCode);

        getDatasetService().importDatasourcesInStatisticalOperation(ctx, statisticalOperationCode, fileUrls);
    }

    @Override
    public DsdAttributeInstanceDto createAttributeInstance(ServiceContext ctx, String datasetVersionUrn, DsdAttributeInstanceDto dsdAttributeInstanceDto) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canCreateAttributeInstance(ctx, datasetVersionDto);

        // Transform
        AttributeInstanceDto attributeInstanceDto = statisticalResourcesDto2StatRepoDtoMapper.dsdAttributeInstanceDtoToAttributeInstanceDto(dsdAttributeInstanceDto);

        // Create attribute
        AttributeInstanceDto attributeInstanceCreated = getDatasetService().createAttributeInstance(ctx, datasetVersionUrn, attributeInstanceDto);

        DsdAttribute dsdAttribute = getDatasetVersionAttribute(ctx, datasetVersionUrn, attributeInstanceCreated.getAttributeId());

        return statRepoDto2StatisticalResourcesDtoMapper.attributeDtoToDsdAttributeInstanceDto(dsdAttribute, attributeInstanceCreated);
    }

    @Override
    public DsdAttributeInstanceDto updateAttributeInstance(ServiceContext ctx, String datasetVersionUrn, DsdAttributeInstanceDto dsdAttributeInstanceDto) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canUpdateAttributeInstance(ctx, datasetVersionDto);

        // Transform
        AttributeInstanceDto attributeInstanceDto = statisticalResourcesDto2StatRepoDtoMapper.dsdAttributeInstanceDtoToAttributeInstanceDto(dsdAttributeInstanceDto);

        // Update attribute
        AttributeInstanceDto attributeInstanceUpdated = getDatasetService().updateAttributeInstance(ctx, datasetVersionUrn, attributeInstanceDto);

        if (attributeInstanceUpdated != null) {
            DsdAttribute dsdAttribute = getDatasetVersionAttribute(ctx, datasetVersionUrn, attributeInstanceUpdated.getAttributeId());

            return statRepoDto2StatisticalResourcesDtoMapper.attributeDtoToDsdAttributeInstanceDto(dsdAttribute, attributeInstanceUpdated);
        } else {
            return null;
        }
    }

    @Override
    public void deleteAttributeInstance(ServiceContext ctx, String datasetVersionUrn, String uuid) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canDeleteAttributeInstance(ctx, datasetVersionDto);

        // Delete attribute
        getDatasetService().deleteAttributeInstance(ctx, datasetVersionUrn, uuid);
    }

    @Override
    public List<DsdAttributeInstanceDto> retrieveAttributeInstances(ServiceContext ctx, String datasetVersionUrn, String attributeId) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canRetrieveAttributeInstances(ctx, datasetVersionDto);

        // Retrieve
        List<AttributeInstanceDto> instances = getDatasetService().retrieveAttributeInstances(ctx, datasetVersionUrn, attributeId);

        DsdAttribute dsdAttribute = getDatasetVersionAttribute(ctx, datasetVersionUrn, attributeId);

        return statRepoDto2StatisticalResourcesDtoMapper.attributeDtosToDsdAttributeInstanceDtos(dsdAttribute, instances);
    }

    private DsdAttribute getDatasetVersionAttribute(ServiceContext ctx, String datasetVersionUrn, String attributeId) throws MetamacException {
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        DataStructure dsd = srmRestInternalService.retrieveDsdByUrn(datasetVersion.getRelatedDsd().getUrn());

        return DsdProcessor.getAttribute(dsd, attributeId);
    }

    @Override
    public CategorisationDto createCategorisation(ServiceContext ctx, String datasetVersionUrn, CategorisationDto categorisationDto) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canCreateCategorisation(ctx, datasetVersionDto);

        // Transform
        Categorisation categorisation = datasetDto2DoMapper.categorisationDtoToDo(categorisationDto);

        // Create
        categorisation = getDatasetService().createCategorisation(ctx, datasetVersionUrn, categorisation);

        // Transform
        return datasetDo2DtoMapper.categorisationDoToDto(categorisation);
    }

    @Override
    public CategorisationDto retrieveCategorisationByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        Categorisation categorisation = getDatasetService().retrieveCategorisationByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canRetrieveCategorisationByUrn(ctx, categorisation);

        // Transform
        CategorisationDto categorisationDto = datasetDo2DtoMapper.categorisationDoToDto(categorisation);
        return categorisationDto;
    }

    @Override
    public void deleteCategorisation(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        Categorisation categorisation = getDatasetService().retrieveCategorisationByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canDeleteCategorisation(ctx, categorisation);

        // Delete
        getDatasetService().deleteCategorisation(ctx, urn);
    }

    @Override
    public List<CategorisationDto> retrieveCategorisationsByDatasetVersion(ServiceContext ctx, String datasetVersionUrn) throws MetamacException {
        // Retrieve
        DatasetVersionDto datasetVersionDto = retrieveDatasetVersionByUrn(ctx, datasetVersionUrn);

        // Security
        DatasetsSecurityUtils.canRetrieveCategorisationsByDatasetVersion(ctx, datasetVersionDto);

        // Retrieve
        List<Categorisation> categorisations = getDatasetService().retrieveCategorisationsByDatasetVersion(ctx, datasetVersionUrn);

        // Transform
        List<CategorisationDto> categorisationsDto = datasetDo2DtoMapper.categorisationDoListToDtoList(categorisations);
        return categorisationsDto;
    }

    @Override
    public CategorisationDto endCategorisationValidity(ServiceContext ctx, String urn, Date validTo) throws MetamacException {
        // Retrieve
        Categorisation categorisation = getDatasetService().retrieveCategorisationByUrn(ctx, urn);

        // Security
        DatasetsSecurityUtils.canEndCategorisationValidity(ctx, categorisation);

        // Delete
        categorisation = getDatasetService().endCategorisationValidity(ctx, urn, CoreCommonUtil.transformDateToDateTime(validTo));

        // Transform
        CategorisationDto categorisationDto = datasetDo2DtoMapper.categorisationDoToDto(categorisation);
        return categorisationDto;
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
        PublicationsSecurityUtils.canCreatePublication(ctx, statisticalOperationDto.getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);
        ExternalItem statisticalOperation = publicationDto2DoMapper.externalItemDtoToDo(statisticalOperationDto, null, ServiceExceptionParameters.STATISTICAL_OPERATION);

        // Retrieve
        PublicationVersion publicationVersionCreated = getPublicationService().createPublicationVersion(ctx, publicationVersion, statisticalOperation);

        // Transform
        PublicationVersionDto publicationCreated = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersionCreated);

        return publicationCreated;
    }

    @Override
    public PublicationVersionDto updatePublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canUpdatePublicationVersion(ctx, publicationVersionDto);

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Update
        publicationVersion = getPublicationService().updatePublicationVersion(ctx, publicationVersion);

        // Transform
        return publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);
    }

    @Override
    public void deletePublicationVersion(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        PublicationVersionDto publicationVersionDto = retrievePublicationVersionByUrn(ctx, urn);

        // Security
        PublicationsSecurityUtils.canDeletePublicationVersion(ctx, publicationVersionDto);

        // Delete
        getPublicationService().deletePublicationVersion(ctx, urn);

    }

    @Override
    public MetamacCriteriaResult<PublicationVersionBaseDto> findPublicationVersionByCondition(ServiceContext ctx, MetamacCriteria criteria) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canFindPublicationsVersionsByCondition(ctx);

        // Transform
        SculptorCriteria sculptorCriteria = publicationVersionMetamacCriteria2SculptorCriteriaMapper.getPublicationVersionCriteriaMapper().metamacCriteria2SculptorCriteria(criteria);

        // Find
        PagedResult<PublicationVersion> result = getPublicationService().findPublicationVersionsByCondition(ctx, sculptorCriteria.getConditions(), sculptorCriteria.getPagingParameter());

        // Transform
        MetamacCriteriaResult<PublicationVersionBaseDto> metamacCriteriaResult = publicationVersionSculptorCriteria2MetamacCriteriaMapper.pageResultToMetamacCriteriaResultPublicationVersion(result,
                sculptorCriteria.getPageSize());

        return metamacCriteriaResult;
    }

    @Override
    public PublicationVersionDto retrievePublicationVersionByUrn(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, urn);

        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersionByUrn(ctx, publicationVersion);

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
    public List<PublicationVersionBaseDto> retrievePublicationVersions(ServiceContext ctx, String urn) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersions(ctx);

        // Retrieve
        List<PublicationVersion> publicationVersions = getPublicationService().retrievePublicationVersions(ctx, urn);

        // Transform
        List<PublicationVersionBaseDto> publications = publicationDo2DtoMapper.publicationVersionDoListToDtoList(publicationVersions);

        return publications;
    }

    @Override
    public PublicationStructureDto retrievePublicationVersionStructure(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Retrieve
        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Security
        PublicationsSecurityUtils.canRetrievePublicationVersionStructure(ctx, publicationVersion);

        // Build structure
        PublicationStructureDto publicationStructureDto = publicationDo2DtoMapper.publicationVersionStructureDoToDto(publicationVersion);

        return publicationStructureDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToProductionValidation(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToProductionValidation(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToProductionValidation(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto sendPublicationVersionToProductionValidation(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToProductionValidation(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        // Send to production validation and retrieve
        PublicationVersion publicationVersion = publicationLifecycleService.sendToProductionValidation(ctx, publicationVersionDto.getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToDiffusionValidation(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToDiffusionValidation(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToDiffusionValidation(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto sendPublicationVersionToDiffusionValidation(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToDiffusionValidation(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        // Send to production validation and retrieve
        PublicationVersion publicationVersion = publicationLifecycleService.sendToDiffusionValidation(ctx, publicationVersionDto.getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto sendPublicationVersionToValidationRejected(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToValidationRejected(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Send to production validation and retrieve
        publicationVersion = publicationLifecycleService.sendToValidationRejected(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto sendPublicationVersionToValidationRejected(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canSendPublicationVersionToValidationRejected(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        // Send to production validation and retrieve
        PublicationVersion publicationVersion = publicationLifecycleService.sendToValidationRejected(ctx, publicationVersionDto.getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto publishPublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canPublishPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);
        String urn = publicationVersion.getSiemacMetadataStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        publicationVersion = changePublicationVersionValidFromAndSave(urn, new DateTime());

        publicationVersion = publicationLifecycleService.sendToPublished(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto publishPublicationVersion(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canPublishPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        String urn = publicationVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        PublicationVersion publicationVersion = changePublicationVersionValidFromAndSave(urn, new DateTime());

        publicationVersion = publicationLifecycleService.sendToPublished(ctx, publicationVersionDto.getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto programPublicationPublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto, Date validFromDate) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canPublishPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform only for optimistic locking
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);
        String urn = publicationVersion.getSiemacMetadataStatisticalResource().getUrn();

        // We set validfrom and ONLY validfrom transparently
        publicationVersion = changePublicationVersionValidFromAndSave(urn, validFromDate);

        publicationVersion = publicationLifecycleService.sendToPublished(ctx, urn);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto programPublicationPublicationVersion(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto, Date validFromDate) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canPublishPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        String urn = publicationVersionDto.getUrn();

        // We set validfrom and ONLY validfrom transparently
        PublicationVersion publicationVersion = changePublicationVersionValidFromAndSave(urn, validFromDate);

        publicationVersion = publicationLifecycleService.sendToPublished(ctx, urn);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    private PublicationVersion changePublicationVersionValidFromAndSave(String urn, Date validFrom) throws MetamacException {
        PublicationVersion publicationVersion = publicationVersionRepository.retrieveByUrn(urn);
        publicationVersion.getSiemacMetadataStatisticalResource().setValidFrom(datasetDto2DoMapper.dateDtoToDo(validFrom));
        return publicationVersionRepository.save(publicationVersion);
    }

    private PublicationVersion changePublicationVersionValidFromAndSave(String urn, DateTime validFrom) throws MetamacException {
        PublicationVersion publicationVersion = publicationVersionRepository.retrieveByUrn(urn);
        publicationVersion.getSiemacMetadataStatisticalResource().setValidFrom(validFrom);
        return publicationVersionRepository.save(publicationVersion);
    }

    @Override
    public PublicationVersionDto cancelPublicationPublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCancelPublicationPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        publicationVersion = publicationLifecycleService.cancelPublication(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto cancelPublicationPublicationVersion(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canCancelPublicationPublicationVersion(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        PublicationVersion publicationVersion = publicationLifecycleService.cancelPublication(ctx, publicationVersionDto.getUrn());

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionDto versioningPublicationVersion(ServiceContext ctx, PublicationVersionDto publicationVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canVersionPublication(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Transform
        PublicationVersion publicationVersion = publicationDto2DoMapper.publicationVersionDtoToDo(publicationVersionDto);

        // Versioning
        publicationVersion = publicationLifecycleService.versioning(ctx, publicationVersion.getSiemacMetadataStatisticalResource().getUrn(), versionType);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToDto(publicationVersion);

        return publicationVersionDto;
    }

    @Override
    public PublicationVersionBaseDto versioningPublicationVersion(ServiceContext ctx, PublicationVersionBaseDto publicationVersionDto, VersionTypeEnum versionType) throws MetamacException {
        // Security
        PublicationsSecurityUtils.canVersionPublication(ctx, publicationVersionDto.getStatisticalOperation().getCode());

        // Check optimistic locking
        publicationDto2DoMapper.checkOptimisticLocking(publicationVersionDto);

        // Versioning
        PublicationVersion publicationVersion = publicationLifecycleService.versioning(ctx, publicationVersionDto.getUrn(), versionType);

        // Transform
        publicationVersionDto = publicationDo2DtoMapper.publicationVersionDoToBaseDto(publicationVersion);

        return publicationVersionDto;
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    @Override
    public ChapterDto createChapter(ServiceContext ctx, String publicationUrn, ChapterDto chapterDto) throws MetamacException {
        // Transform
        Chapter chapter = publicationDto2DoMapper.chapterDtoToDo(chapterDto);

        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationUrn);

        // Security
        PublicationsSecurityUtils.canCreateChapter(ctx, publicationVersion);

        // Create
        chapter = getPublicationService().createChapter(ctx, publicationUrn, chapter);

        // Transform to dto
        chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto updateChapter(ServiceContext ctx, ChapterDto chapterDto) throws MetamacException {
        // Transform
        Chapter chapter = publicationDto2DoMapper.chapterDtoToDo(chapterDto);

        // Security
        PublicationsSecurityUtils.canUpdateChapter(ctx, chapter);

        // Update
        chapter = getPublicationService().updateChapter(ctx, chapter);

        // Transform
        chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto updateChapterLocation(ServiceContext ctx, String chapterUrn, String parentTargetUrn, Long orderInLevel) throws MetamacException {
        // Retrieve to check security
        Chapter chapter = getPublicationService().retrieveChapter(ctx, chapterUrn);

        // Security
        PublicationsSecurityUtils.canUpdateChapterLocation(ctx, chapter);

        // Update
        chapter = getPublicationService().updateChapterLocation(ctx, chapterUrn, parentTargetUrn, orderInLevel);

        // Transform to dto
        ChapterDto chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public ChapterDto retrieveChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Retrieve
        Chapter chapter = getPublicationService().retrieveChapter(ctx, chapterUrn);

        // Security
        PublicationsSecurityUtils.canRetrieveChapter(ctx, chapter);

        // Transform
        ChapterDto chapterDto = publicationDo2DtoMapper.chapterDoToDto(chapter);
        return chapterDto;
    }

    @Override
    public void deleteChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Retrieve
        Chapter chapter = getPublicationService().retrieveChapter(ctx, chapterUrn);

        // Security
        PublicationsSecurityUtils.canDeleteChapter(ctx, chapter);

        // Delete
        getPublicationService().deleteChapter(ctx, chapterUrn);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    @Override
    public CubeDto createCube(ServiceContext ctx, String publicationUrn, CubeDto cubeDto) throws MetamacException {
        // Transform
        Cube cube = publicationDto2DoMapper.cubeDtoToDo(cubeDto);

        PublicationVersion publicationVersion = getPublicationService().retrievePublicationVersionByUrn(ctx, publicationUrn);

        // Security
        PublicationsSecurityUtils.canCreateCube(ctx, publicationVersion);

        // Create
        cube = getPublicationService().createCube(ctx, publicationUrn, cube);

        // Transform
        cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;

    }

    @Override
    public CubeDto updateCube(ServiceContext ctx, CubeDto cubeDto) throws MetamacException {
        // Transform
        Cube cube = publicationDto2DoMapper.cubeDtoToDo(cubeDto);

        // Security
        PublicationsSecurityUtils.canUpdateCube(ctx, cube);

        // Update
        cube = getPublicationService().updateCube(ctx, cube);

        // Transform
        cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;
    }

    @Override
    public CubeDto updateCubeLocation(ServiceContext ctx, String cubeUrn, String parentTargetUrn, Long orderInLevel) throws MetamacException {
        // Retrieve cube for security checkings
        Cube cube = getPublicationService().retrieveCube(ctx, cubeUrn);

        // Security
        PublicationsSecurityUtils.canUpdateCubeLocation(ctx, cube);

        // Update
        cube = getPublicationService().updateCubeLocation(ctx, cubeUrn, parentTargetUrn, orderInLevel);

        // Transform
        CubeDto cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;
    }

    @Override
    public CubeDto retrieveCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Retrieve
        Cube cube = getPublicationService().retrieveCube(ctx, cubeUrn);

        // Security
        PublicationsSecurityUtils.canRetrieveCube(ctx, cube);

        // Transform
        CubeDto cubeDto = publicationDo2DtoMapper.cubeDoToDto(cube);
        return cubeDto;

    }

    @Override
    public void deleteCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Retrieve
        Cube cube = getPublicationService().retrieveCube(ctx, cubeUrn);

        // Security
        PublicationsSecurityUtils.canDeleteCube(ctx, cube);

        // Delete
        getPublicationService().deleteCube(ctx, cubeUrn);
    }

    // ------------------------------------------------------------------------
    // CONSTRAINTS
    // ------------------------------------------------------------------------

    @Override
    public List<ExternalItemDto> findContentConstraintsForArtefact(ServiceContext ctx, String artefactUrn) throws MetamacException {
        // Security
        ConstraintsSecurityUtils.canFindContentConstraintsForArtefact(ctx);

        // Find
        List<ResourceInternal> contentConstraintsForArtefact = contraintsService.findContentConstraintsForArtefact(ctx, artefactUrn);

        // Transform
        return constraintRest2DtoMapper.externalItemConstraintToExternalItemDto(contentConstraintsForArtefact);
    }

    @Override
    public ContentConstraintBasicDto createContentConstraint(ServiceContext ctx, ContentConstraintDto contentConstraintDto) throws MetamacException {
        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraintDto);
        ConstraintsSecurityUtils.canCreateContentConstraint(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode());

        // Check that there isn't data sources
        if (!datasetVersion.getDatasources().isEmpty()) {
            throw MetamacExceptionBuilder.builder().withPrincipalException(new MetamacExceptionItem(ServiceExceptionType.CONSTRAINTS_CREATE_DATASET_WITH_DATASOURCES)).build();
        }

        // Transform
        ContentConstraint contentConstraint = constraintDto2RestMapper.constraintDtoTo(contentConstraintDto);

        // Create
        contentConstraint = contraintsService.createContentConstraint(ctx, contentConstraint);

        // Transform
        return constraintRest2DtoMapper.toConstraintBasicDto(contentConstraint);
    }

    @Override
    public ContentConstraintDto retrieveContentConstraintByUrn(ServiceContext ctx, String urn, Boolean includeDraft) throws MetamacException {
        // Retrieve
        ContentConstraint contentConstraint = contraintsService.retrieveContentConstraintByUrn(ctx, urn, includeDraft);

        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraint); // Dataset for extract operation
        ProcStatusEnum procStatus = null; // ProcStatus of contentConstraints, null is draft
        if (contentConstraint.getLifeCycle() != null) {
            procStatus = ProcStatusEnum.valueOf(contentConstraint.getLifeCycle().getProcStatus().value());
        }
        ConstraintsSecurityUtils.canRetrieveContentConstraintByUrn(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), procStatus);

        // Transform
        return constraintRest2DtoMapper.toConstraintDto(contentConstraint);
    }

    @Override
    public void deleteContentConstraint(ServiceContext ctx, String urn) throws MetamacException {
        // Retrieve
        ContentConstraint contentConstraint = contraintsService.retrieveContentConstraintByUrn(ctx, urn, Boolean.TRUE);

        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraint); // Dataset for extract operation
        ProcStatusEnum procStatus = null; // ProcStatus of contentConstraints, null is draft
        if (contentConstraint.getLifeCycle() != null) {
            procStatus = ProcStatusEnum.valueOf(contentConstraint.getLifeCycle().getProcStatus().value());
        }
        ConstraintsSecurityUtils.canDeleteContentConstraint(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), procStatus);

        // Delete
        contraintsService.deleteContentConstraint(ctx, urn);
    }

    @Override
    public RegionValueDto saveRegionForContentConstraint(ServiceContext ctx, String contentConstraintUrn, RegionValueDto regionValueDto) throws MetamacException {
        // Retrieve
        ContentConstraint contentConstraint = contraintsService.retrieveContentConstraintByUrn(ctx, contentConstraintUrn, Boolean.TRUE);

        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraint); // Dataset for extract operation
        ProcStatusEnum procStatus = null; // ProcStatus of contentConstraints, null is draft
        if (contentConstraint.getLifeCycle() != null) {
            procStatus = ProcStatusEnum.valueOf(contentConstraint.getLifeCycle().getProcStatus().value());
        }
        ConstraintsSecurityUtils.canSaveForContentConstraint(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), procStatus);

        // Transform
        RegionReference regionReference = constraintDto2RestMapper.toRegionReference(regionValueDto);

        // Create
        regionReference = contraintsService.saveRegionForContentConstraint(ctx, regionReference);

        // Transform
        return constraintRest2DtoMapper.toRegionDto(regionReference);
    }

    @Override
    public void deleteRegion(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException {
        // Retrieve
        ContentConstraint contentConstraint = contraintsService.retrieveContentConstraintByUrn(ctx, contentConstraintUrn, Boolean.TRUE);

        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraint); // Dataset for extract operation
        ProcStatusEnum procStatus = null; // ProcStatus of contentConstraints, null is draft
        if (contentConstraint.getLifeCycle() != null) {
            procStatus = ProcStatusEnum.valueOf(contentConstraint.getLifeCycle().getProcStatus().value());
        }
        ConstraintsSecurityUtils.deleteRegion(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), procStatus);

        // Delete
        contraintsService.deleteRegion(ctx, contentConstraintUrn, regionCode);
    }

    @Override
    public RegionValueDto retrieveRegionForContentConstraint(ServiceContext ctx, String contentConstraintUrn, String regionCode) throws MetamacException {
        // Retrieve
        ContentConstraint contentConstraint = contraintsService.retrieveContentConstraintByUrn(ctx, contentConstraintUrn, Boolean.TRUE);

        // Security
        DatasetVersion datasetVersion = obtainDatasetVersionFromContentConstraint(ctx, contentConstraint); // Dataset for extract operation
        ProcStatusEnum procStatus = null; // ProcStatus of contentConstraints, null is draft
        if (contentConstraint.getLifeCycle() != null) {
            procStatus = ProcStatusEnum.valueOf(contentConstraint.getLifeCycle().getProcStatus().value());
        }
        ConstraintsSecurityUtils.canRetrieveRegionForContentConstraint(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), procStatus);

        // Find
        RegionReference regionReference = contraintsService.retrieveRegionForContentConstraint(ctx, contentConstraintUrn, regionCode);

        // Transform
        return constraintRest2DtoMapper.toRegionDto(regionReference);
    }

    protected DatasetVersion obtainDatasetVersionFromContentConstraint(ServiceContext ctx, ContentConstraintDto contentConstraintDto) throws MetamacException {
        if (contentConstraintDto == null || contentConstraintDto.getConstraintAttachment() == null || contentConstraintDto.getConstraintAttachment().getType() == null) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONTENT_CONSTRAINT_ATTACHMENT);
        }

        if (TypeExternalArtefactsEnum.DATASET.equals(contentConstraintDto.getConstraintAttachment().getType())) {
            throw new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.CONTENT_CONSTRAINT_ATTACHMENT);
        }

        // Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, contentConstraintDto.getConstraintAttachment().getUrn());

        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionByUrn(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), datasetVersion
                .getSiemacMetadataStatisticalResource().getEffectiveProcStatus());

        return datasetVersion;
    }

    protected DatasetVersion obtainDatasetVersionFromContentConstraint(ServiceContext ctx, ContentConstraint contentConstraint) throws MetamacException {
        if (contentConstraint == null || contentConstraint.getConstraintAttachment() == null || contentConstraint.getConstraintAttachment().getKind() == null) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CONTENT_CONSTRAINT_ATTACHMENT);
        }

        if (TypeExternalArtefactsEnum.DATASET.getValue().equals(contentConstraint.getConstraintAttachment().getKind())) {
            throw new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.CONTENT_CONSTRAINT_ATTACHMENT);
        }

        // Retrieve
        DatasetVersion datasetVersion = getDatasetService().retrieveDatasetVersionByUrn(ctx, contentConstraint.getConstraintAttachment().getUrn());

        // Security
        DatasetsSecurityUtils.canRetrieveDatasetVersionByUrn(ctx, datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), datasetVersion
                .getSiemacMetadataStatisticalResource().getEffectiveProcStatus());

        return datasetVersion;
    }
}
