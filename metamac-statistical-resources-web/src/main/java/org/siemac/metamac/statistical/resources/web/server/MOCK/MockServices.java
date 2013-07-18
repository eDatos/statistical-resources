package org.siemac.metamac.statistical.resources.web.server.MOCK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPaginatorResult;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaResult;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockServices {

    private static final String                            DATASOURCE_URI_PREFIX = "/datasources/";
    private static final String                            AGENCY_URI_PREFIX     = "http://siemac.metamac/agency/";
    private static final String                            DATASET_URI_PREFIX    = "http://siemac.metamac/datasets/";
    private static final String                            COLLECTION_URI_PREFIX = "http://siemac.metamac/collections/";
    private static final String                            QUERY_URI_PREFIX      = "http://siemac.metamac/queries/";

    private static Map<String, DatasetDto>                 datasets;
    private static Map<String, DatasourceDto>              datasources;
    private static Map<String, PublicationDto>             collections;
    private static Map<String, ExternalItemDto>            agencies;
    private static StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    private static ExternalItemDto                         istacAgency;

    private static Logger                                  logger                = LoggerFactory.getLogger(MockServices.class);

    // static {
    // getAgencies();
    // }

    //
    // ORGANIZATIONS
    //

    // public static MetamacCriteriaResult<ExternalItemDto> findAgencies(int firstResult, int maxResults) throws MetamacException {
    // List<ExternalItemDto> agenciesList = new ArrayList<ExternalItemDto>(getAgencies().values());
    //
    // int endIndex = agenciesList.size();
    // if (endIndex - firstResult > maxResults) {
    // endIndex = firstResult + maxResults;
    // }
    // MetamacCriteriaResult<ExternalItemDto> result = new MetamacCriteriaResult<ExternalItemDto>();
    // MetamacCriteriaPaginatorResult paginatorResult = new MetamacCriteriaPaginatorResult();
    // paginatorResult.setFirstResult(firstResult);
    // paginatorResult.setMaximumResultSize(maxResults);
    // paginatorResult.setTotalResults(agenciesList.size());
    // result.setPaginatorResult(paginatorResult);
    // result.setResults(new ArrayList<ExternalItemDto>(agenciesList.subList(firstResult, endIndex)));
    // return result;
    // }

    // private static Map<String, ExternalItemDto> getAgencies() {
    // if (agencies == null) {
    // agencies = new HashMap<String, ExternalItemDto>();
    // istacAgency = createAgency("ds-0001", "ISTAC", "ISTAC");
    // createAgency("age-0002", "Agencia 2", "Agency 2");
    // createAgency("age-0003", "Agencia 3", "Agency 3");
    // createAgency("age-0004", "Agencia 4", "Agency 4");
    // createAgency("age-0005", "Agencia 5", "Agency 5");
    // createAgency("age-0006", "Agencia 6", "Agency 6");
    // }
    // return agencies;
    // }

    // private static ExternalItemDto createAgency(String code, String title_es, String title_en) {
    // String uri = AGENCY_URI_PREFIX + code;
    // String urn = UrnUtils.generateUrn(UrnConstants.URN_SDMX_CLASS_AGENCY_PREFIX, code);
    // ExternalItemDto agency = new ExternalItemDto(code, uri, urn, urn, TypeExternalArtefactsEnum.AGENCY, createInternationalString(title_es, title_en));
    // agencies.put(agency.getUrn(), agency);
    // return agency;
    // }

    //
    // DATASETS
    //

    public static DatasetDto createDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        String identifier = datasetDto.getCode();
        String datasetUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, identifier);
        // if (getDatasets().containsKey(datasetUrn)) {
        // throw new MetamacException(ServiceExceptionType.DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED, identifier);
        // }

        Date now = new Date();

        datasetDto.setId(Long.valueOf(getDatasets().size() + 1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);

        // Audit
        // datasetDto.setResponsabilityCreator(ctx.getUserId());
        // datasetDto.setDateCreated(now);
        // datasetDto.setDateLastUpdate(now);
        // datasetDto.setLastUpdateUser(ctx.getUserId());

        // Identifiers
        datasetDto.setUri(DATASET_URI_PREFIX + datasetDto.getCode());
        datasetDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetDto.getCode()));

        // Version
        // datasetDto.setVersionDate(now);
        datasetDto.setVersionLogic("01.000");

        // Life cycle
        datasetDto.setCreator(istacAgency);
        datasetDto.setProcStatus(ProcStatusEnum.DRAFT);

        // Content
        // ContentMetadataDto contentMetadata = new ContentMetadataDto();
        // contentMetadata.setSpatialCoverage(new ArrayList<String>());
        // contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        // contentMetadata.setTemporalCoverage(new ArrayList<String>());
        // contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        // contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        // datasetDto.setContentMetadata(contentMetadata);

        getDatasets().put(datasetDto.getUrn(), datasetDto);
        return datasetDto;
    }

    public static DatasetDto retrieveDataset(ServiceContext ctx, String datasetUrn) throws MetamacException {
        DatasetDto dataset = getDatasets().get(datasetUrn);
        if (dataset != null) {
            return dataset;
        } else {
            throw new MetamacException(ServiceExceptionType.DATASET_NOT_FOUND, datasetUrn);
        }
    }

    public static DatasetDto updateDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (datasetDto.getUuid() == null || !getDatasets().containsKey(datasetDto.getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        DatasetDto oldDataset = getDatasets().get(datasetDto.getUrn());

        // if (!oldDataset.getStatisticalOperation().getUrn().equals(datasetDto.getStatisticalOperation().getUrn())) {
        // throw new MetamacException(CommonServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.DATASET_SOPERATION);
        // }

        // Date now = new Date();
        // datasetDto.setDateLastUpdate(now);
        // datasetDto.setLastUpdateUser(ctx.getUserId());

        datasetDto.setVersion(datasetDto.getVersion() + 1);

        getDatasets().put(datasetDto.getUrn(), datasetDto);

        return datasetDto;
    }

    public static void deleteDataset(ServiceContext ctx, String urn) throws MetamacException {
        if (urn == null || !getDatasets().containsKey(urn)) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        getDatasets().remove(urn);
    }

    public static MetamacCriteriaResult<DatasetDto> findDatasets(String operationUrn, int firstResult, int maxResults) throws MetamacException {
        List<DatasetDto> datasetsList = new ArrayList<DatasetDto>();
        // for (DatasetDto dataset : getDatasets().values()) {
        // if (operationUrn.equals(dataset.getOperation().getUrn())) {
        // datasetsList.add(dataset);
        // }
        // }

        int endIndex = datasetsList.size();
        if (endIndex - firstResult > maxResults) {
            endIndex = firstResult + maxResults;
        }
        MetamacCriteriaResult<DatasetDto> result = new MetamacCriteriaResult<DatasetDto>();
        MetamacCriteriaPaginatorResult paginatorResult = new MetamacCriteriaPaginatorResult();
        paginatorResult.setFirstResult(firstResult);
        paginatorResult.setMaximumResultSize(maxResults);
        paginatorResult.setTotalResults(datasetsList.size());
        result.setPaginatorResult(paginatorResult);
        result.setResults(new ArrayList<DatasetDto>(datasetsList.subList(firstResult, endIndex)));
        return result;
    }

    private static Map<String, DatasetDto> getDatasets() {
        if (datasets == null) {
            datasets = new HashMap<String, DatasetDto>();
            try {
                List<ExternalItemDto> operations = getOperationsList();
                if (operations.size() > 0) {
                    Random randGen = new Random();
                    createDataset("ds-0001", "Dataset 1", "Dataset 1", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0002", "Dataset 2", "Dataset 2", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0003", "Dataset 3", "Dataset 3", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0004", "Dataset 4", "Dataset 4", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0005", "Dataset 5", "Dataset 5", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0006", "Dataset 6", "Dataset 6", operations.get(randGen.nextInt(operations.size())));
                }
            } catch (MetamacWebException e) {
                logger.error("Error en datasets ", e);
            }
        }
        return datasets;
    }

    private static void createDataset(String code, String title_es, String title_en, ExternalItemDto operation) {
        Date now = new Date();
        DatasetDto datasetDto = new DatasetDto();

        datasetDto.setId(Long.valueOf(datasets.size() + 1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);
        // Base
        // datasetDto.setOperation(operation);
        // datasetDto.setResponsabilityCreator("ISTAC_ADMIN");
        // datasetDto.setDateCreated(now);
        // datasetDto.setDateLastUpdate(now);
        // datasetDto.setLastUpdateUser("ISTAC_ADMIN");
        datasetDto.setCode(code);
        datasetDto.setTitle(createInternationalString(title_es, title_en));
        datasetDto.setUri(DATASET_URI_PREFIX + code);
        datasetDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, code));
        // datasetDto.setVersionDate(now);
        datasetDto.setVersionLogic("01.000");

        // Life cycle
        datasetDto.setCreator(istacAgency);
        datasetDto.setProcStatus(ProcStatusEnum.DRAFT);

        // Content
        // ContentMetadataDto contentMetadata = new ContentMetadataDto();
        // contentMetadata.setLanguage("es");
        // contentMetadata.setLanguages(new ArrayList<String>());
        // contentMetadata.getLanguages().add("es");
        // contentMetadata.getLanguages().add("en");
        // contentMetadata.setDescription(createInternationalString(
        // "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus"
        // + " et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla "
        // + "consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.", ""));
        //
        // contentMetadata.setKeywords(new ArrayList<String>());
        // contentMetadata.getKeywords().add("statistic");
        // contentMetadata.getKeywords().add("data");
        // contentMetadata.getKeywords().add("dataset");
        //
        // contentMetadata.setSpatialCoverage(new ArrayList<String>());
        // contentMetadata.getSpatialCoverage().add("CANARIAS");
        // contentMetadata.getSpatialCoverage().add("LANZAROTE");
        // contentMetadata.getSpatialCoverage().add("Lanzarote - Este");
        // contentMetadata.getSpatialCoverage().add("Lanzarote - Norte");
        //
        // contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        // contentMetadata.getSpatialCoverageCodes().add("ES70");
        // contentMetadata.getSpatialCoverageCodes().add("ES708");
        // contentMetadata.getSpatialCoverageCodes().add("ES708A01");
        // contentMetadata.getSpatialCoverageCodes().add("ES708A02");
        //
        // contentMetadata.setTemporalCoverage(new ArrayList<String>());
        // contentMetadata.getTemporalCoverage().add("2002 Primer trimestre");
        // contentMetadata.getTemporalCoverage().add("2002 Segundo trimestre");
        // contentMetadata.getTemporalCoverage().add("2002 Tercer trimestre");
        //
        // contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        // contentMetadata.getTemporalCoverageCodes().add("2002Q1");
        // contentMetadata.getTemporalCoverageCodes().add("2002Q2");
        // contentMetadata.getTemporalCoverageCodes().add("2002Q3");
        //
        // contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        // contentMetadata.setType(StatisticalResourceTypeEnum.DATASET);
        // contentMetadata.setCopyrightedDate(new Date());
        // datasetDto.setContentMetadata(contentMetadata);

        datasets.put(datasetDto.getUrn(), datasetDto);
    }

    public static DatasetDto sendDatasetToProductionValidation(String urn) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        return datasetDto;
    }

    public static DatasetDto sendDatasetToDiffusionValidation(String urn) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        return datasetDto;
    }

    public static DatasetDto rejectDatasetProductionValidation(String urn) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setProcStatus(ProcStatusEnum.DRAFT);
        return datasetDto;
    }

    public static DatasetDto rejectDatasetDiffusionValidation(String urn) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setProcStatus(ProcStatusEnum.DRAFT);
        return datasetDto;
    }

    public static DatasetDto publishDataset(String urn) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setProcStatus(ProcStatusEnum.PUBLISHED);
        return datasetDto;
    }

    public static DatasetDto versionDataset(String urn, VersionTypeEnum versionType) throws MetamacException {
        DatasetDto datasetDto = retrieveDataset(ServiceContextHolder.getCurrentServiceContext(), urn);
        datasetDto.setId(Long.valueOf(collections.size() + 1));
        // datasetDto.setVersionLogic(VersionUtil.createNextVersionTag(datasetDto.getVersionLogic(), VersionTypeEnum.MINOR.equals(versionType)));
        datasetDto.setProcStatus(ProcStatusEnum.DRAFT);
        getDatasets().put(datasetDto.getUrn(), datasetDto);
        return datasetDto;
    }

    //
    // DATASOURCES
    //

    public static DatasourceDto createDatasource(ServiceContext ctx, DatasourceDto datasourceDto) throws MetamacException {
        String identifier = datasourceDto.getCode();
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_QUERY_PREFIX, identifier);
        // if (getDatasources().containsKey(urn)) {
        // throw new MetamacException(ServiceExceptionType.DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED, identifier);
        // }

        Date now = new Date();

        datasourceDto.setId(Long.valueOf(getDatasets().size() + 1));
        datasourceDto.setUuid(UUID.randomUUID().toString());
        datasourceDto.setVersion(1L);
        // datasourceDto.setOperation(datasourceDto.getOperation());
        //
        // // Audit
        // datasourceDto.setResponsabilityCreator(ctx.getUserId());
        // datasourceDto.setDateCreated(now);
        // datasourceDto.setDateLastUpdate(now);
        // datasourceDto.setLastUpdateUser(ctx.getUserId());

        // Identifiers
        datasourceDto.setUri(DATASET_URI_PREFIX + datasourceDto.getCode());
        datasourceDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_QUERY_PREFIX, datasourceDto.getCode()));

        getDatasources().put(datasourceDto.getUrn(), datasourceDto);
        return datasourceDto;
    }

    public static DatasourceDto retrieveDatasource(ServiceContext ctx, String urn) throws MetamacException {
        DatasourceDto datasource = getDatasources().get(urn);
        if (datasource != null) {
            return datasource;
        } else {
            throw new MetamacException(ServiceExceptionType.DATASET_NOT_FOUND, urn);
        }
    }

    public static DatasourceDto updateDatasource(ServiceContext ctx, DatasourceDto datasourceDto) throws MetamacException {
        if (datasourceDto.getUuid() == null || !getDatasources().containsKey(datasourceDto.getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        DatasourceDto oldDatasource = getDatasources().get(datasourceDto.getUrn());

        // if (!oldDatasource.getDataset().getUrn().equals(datasourceDto.getDataset().getUrn())) {
        // throw new MetamacException(CommonServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.DATASOURCE_DATASET);
        // }
        //
        // Date now = new Date();
        // datasourceDto.setDateLastUpdate(now);
        // datasourceDto.setLastUpdateUser(ctx.getUserId());

        datasourceDto.setVersion(datasourceDto.getVersion() + 1);

        getDatasources().put(datasourceDto.getUrn(), datasourceDto);

        return datasourceDto;
    }

    public static void deleteDatasource(ServiceContext ctx, String urn) throws MetamacException {
        if (urn == null || !getDatasets().containsKey(urn)) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        getDatasets().remove(urn);
    }

    public static MetamacCriteriaResult<DatasourceDto> findDatasources(String datasetUrn, int firstResult, int maxResults) throws MetamacException {
        List<DatasourceDto> datasourcesList = new ArrayList<DatasourceDto>();
        for (DatasourceDto datasource : getDatasources().values()) {
            // if (datasetUrn.equals(datasource.getDataset().getUrn())) {
            // datasourcesList.add(datasource);
            // }
        }

        int endIndex = datasourcesList.size();
        if (endIndex - firstResult > maxResults) {
            endIndex = firstResult + maxResults;
        }
        MetamacCriteriaResult<DatasourceDto> result = new MetamacCriteriaResult<DatasourceDto>();
        MetamacCriteriaPaginatorResult paginatorResult = new MetamacCriteriaPaginatorResult();
        paginatorResult.setFirstResult(firstResult);
        paginatorResult.setMaximumResultSize(maxResults);
        paginatorResult.setTotalResults(datasourcesList.size());
        result.setPaginatorResult(paginatorResult);
        result.setResults(new ArrayList<DatasourceDto>(datasourcesList.subList(firstResult, endIndex)));
        return result;
    }

    private static Map<String, DatasourceDto> getDatasources() {
        if (datasources == null) {
            datasources = new HashMap<String, DatasourceDto>();
            List<DatasetDto> datasets = new ArrayList<DatasetDto>(getDatasets().values());
            if (datasets.size() > 0) {
                Random randGen = new Random();
                createDatasource("dsource-0001", "Datasource 1", "Datasource 1", datasets.get(randGen.nextInt(datasets.size())));
                createDatasource("dsource-0002", "Datasource 2", "Datasource 2", datasets.get(randGen.nextInt(datasets.size())));
                createDatasource("dsource-0003", "Datasource 3", "Datasource 3", datasets.get(randGen.nextInt(datasets.size())));
                createDatasource("dsource-0004", "Datasource 4", "Datasource 4", datasets.get(randGen.nextInt(datasets.size())));
                createDatasource("dsource-0005", "Datasource 5", "Datasource 5", datasets.get(randGen.nextInt(datasets.size())));
                createDatasource("dsource-0006", "Datasource 6", "Datasource 6", datasets.get(randGen.nextInt(datasets.size())));
            }
        }
        return datasources;
    }

    private static void createDatasource(String code, String title_es, String title_en, DatasetDto dataset) {
        Date now = new Date();
        DatasourceDto datasourceDto = new DatasourceDto();

        datasourceDto.setId(Long.valueOf(datasources.size() + 1));
        datasourceDto.setUuid(UUID.randomUUID().toString());
        datasourceDto.setVersion(1L);
        // Base
        // datasourceDto.setOperation(dataset.getOperation());
        // datasourceDto.setDataset(dataset);
        // datasourceDto.setResponsabilityCreator("ISTAC_ADMIN");
        // datasourceDto.setDateCreated(now);
        // datasourceDto.setDateLastUpdate(now);
        // datasourceDto.setLastUpdateUser("ISTAC_ADMIN");
        // datasourceDto.setIdentifier(code);
        // datasourceDto.setTitle(createInternationalString(title_es, title_en));
        // datasourceDto.setUri(dataset.getUri() + DATASOURCE_URI_PREFIX + code);
        datasourceDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, code));

        datasources.put(datasourceDto.getUrn(), datasourceDto);
    }

    //
    // COLLECTIONS
    //

    //
    // OTHERS METHODS
    //

    public static StatisticalOperationsRestInternalFacade getStatisticalOperationsRestInternalFacade() {
        if (statisticalOperationsRestInternalFacade == null) {
            statisticalOperationsRestInternalFacade = ApplicationContextProvider.getApplicationContext().getBean(StatisticalOperationsRestInternalFacade.class);
        }
        return statisticalOperationsRestInternalFacade;
    }

    private static InternationalStringDto createInternationalString(String text_es, String text_en) {
        InternationalStringDto intString = new InternationalStringDto();
        {
            LocalisedStringDto loc = new LocalisedStringDto();
            loc.setLabel(text_es);
            loc.setLocale("es");
            intString.addText(loc);
        }
        {
            LocalisedStringDto loc = new LocalisedStringDto();
            loc.setLabel(text_en);
            loc.setLocale("en");
            intString.addText(loc);
        }
        return intString;
    }

    private static List<ExternalItemDto> getOperationsList() throws MetamacWebException {
        return null;
    }

}
