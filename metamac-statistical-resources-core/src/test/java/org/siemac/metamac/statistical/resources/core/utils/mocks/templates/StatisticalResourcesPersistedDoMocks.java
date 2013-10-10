package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.DatasetVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.PublicationVersionMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.QueryVersionMock;

public class StatisticalResourcesPersistedDoMocks extends StatisticalResourcesDoMocks {

    private static StatisticalResourcesPersistedDoMocks instance;

    private StatisticalResourcesPersistedDoMocks() {
    }

    public static StatisticalResourcesPersistedDoMocks getInstance() {
        if (instance == null) {
            instance = new StatisticalResourcesPersistedDoMocks();
        }
        return instance;
    }

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public Query mockQuery(QueryMock query) {
        if (query.getIdentifiableStatisticalResource() == null) {
            query.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }

        fillNeededMetadataToGenerateQueryUrn(query);

        mockIdentifiableStatisticalResource(query.getIdentifiableStatisticalResource(), TypeRelatedResourceEnum.QUERY);

        String[] maintainerAgencyId = new String[]{query.getMaintainerCode()};

        query.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(maintainerAgencyId, query.getIdentifiableStatisticalResource().getCode()));

        return query;
    }

    private void fillNeededMetadataToGenerateQueryUrn(QueryMock query) {
        if (query.getMaintainerCode() == null) {
            query.setMaintainerCode(mockString(10));
        }
    }

    public Query mockQueryWithGeneratedQueryVersion(QueryMock queryMock) {
        String maintainerId = mockString(10);

        Query query = mockQuery(queryMock);

        QueryVersionMock queryVersionTemplate = new QueryVersionMock();
        queryVersionTemplate.setQuery(query);
        queryVersionTemplate.getLifeCycleStatisticalResource().setCode(query.getIdentifiableStatisticalResource().getCode());
        queryVersionTemplate.getLifeCycleStatisticalResource().setMaintainer(mockAgencyExternalItem(maintainerId, maintainerId));
        DatasetVersion datasetVersion = mockDatasetVersion();
        StatisticalResourcesPersistedDoMocks.mockDatasetVersionCoverages(datasetVersion);
        queryVersionTemplate.setDataset(datasetVersion.getDataset());
        queryVersionTemplate.setStatus(QueryStatusEnum.ACTIVE);

        mockQueryVersion(queryVersionTemplate);

        return query;
    }

    public Query mockQueryWithGeneratedQueryVersion() {
        return mockQueryWithGeneratedQueryVersion(new QueryMock());
    }

    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
    @Override
    public QueryVersion mockQueryVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        return mockQueryVersion(null, datasetVersion, isDatasetLastVersion);
    }

    @Override
    public QueryVersion mockQueryVersion(Dataset dataset) {
        return mockQueryVersion(null, dataset);
    }

    public QueryVersion mockQueryVersion(Query query, DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        QueryVersion template = new QueryVersion();
        template.setQuery(query);
        template.setFixedDatasetVersion(datasetVersion);
        template.setStatus(isDatasetLastVersion ? QueryStatusEnum.ACTIVE : QueryStatusEnum.DISCONTINUED);
        return mockQueryVersion(template);
    }

    public QueryVersion mockQueryVersion(Query query, Dataset dataset) {
        QueryVersion template = new QueryVersion();
        template.setQuery(query);
        template.setDataset(dataset);
        template.setStatus(QueryStatusEnum.ACTIVE);
        return mockQueryVersion(template);
    }

    public QueryVersion mockQueryVersion(QueryVersion queryVersion) {
        LifeCycleStatisticalResource lifecycleResource = queryVersion.getLifeCycleStatisticalResource();
        if (lifecycleResource == null) {
            lifecycleResource = new LifeCycleStatisticalResource();
        }

        if (queryVersion.getQuery() != null && queryVersion.getQuery().getIdentifiableStatisticalResource() != null) {
            lifecycleResource.setCode(queryVersion.getQuery().getIdentifiableStatisticalResource().getCode());
        }

        queryVersion.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(lifecycleResource, TypeRelatedResourceEnum.QUERY_VERSION));

        if (queryVersion.getQuery() == null) {
            QueryMock queryTemplate = new QueryMock();
            queryTemplate.getIdentifiableStatisticalResource().setCode(queryVersion.getLifeCycleStatisticalResource().getCode());
            queryTemplate.setMaintainerCode(queryVersion.getLifeCycleStatisticalResource().getMaintainer().getCodeNested());
            queryVersion.setQuery(mockQuery(queryTemplate));
        }

        if (!queryVersion.getQuery().getVersions().contains(queryVersion)) {
            queryVersion.getQuery().addVersion(queryVersion);
        }

        if (queryVersion.getFixedDatasetVersion() == null && queryVersion.getDataset() == null) {
            throw new IllegalArgumentException("Can not create a Query with no datasetversion or dataset linked");
        }

        if (queryVersion.getStatus() == null) {
            queryVersion.setStatus(QueryStatusEnum.ACTIVE);
        }

        if (queryVersion.getSelection().isEmpty()) {
            mockQuerySelectionFromDatasetVersion(queryVersion, getDatasetVersionInQueryVersion(queryVersion));
        }

        if (queryVersion.getType() == null) {
            queryVersion.setType(QueryTypeEnum.FIXED);
        }

        return queryVersion;
    }

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    @Override
    public Datasource mockDatasourceWithGeneratedDatasetVersion() {
        return mockDatasourceWithDatasetVersion(mockDatasetVersion());
    }

    public Datasource mockDatasourceWithDatasetVersion(DatasetVersion datasetVersion) {
        Datasource datasource = new Datasource();
        datasource.setDatasetVersion(datasetVersion);
        return mockDatasource(datasource);
    }

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------

    public void mockDataset(DatasetMock dataset) {
        if (dataset.getIdentifiableStatisticalResource() == null) {
            dataset.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }

        fillNeededMetadataToGenerateDatasetCode(dataset);

        dataset.getIdentifiableStatisticalResource().setCode(buildSequentialResourceCode(dataset.getIdentifiableStatisticalResource().getStatisticalOperation().getCode(), dataset.getSequentialId()));

        mockIdentifiableStatisticalResource(dataset.getIdentifiableStatisticalResource(), TypeRelatedResourceEnum.DATASET);

        String maintainerId = dataset.getMaintainerCode();

        String[] maintainerAgencyId = new String[]{maintainerId};

        dataset.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(maintainerAgencyId, dataset.getIdentifiableStatisticalResource().getCode()));

    }

    private void fillNeededMetadataToGenerateDatasetCode(DatasetMock dataset) {
        if (dataset.getSequentialId() == null) {
            dataset.setSequentialId(1);
        }

        if (dataset.getIdentifiableStatisticalResource().getStatisticalOperation() == null) {
            dataset.getIdentifiableStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }

    public Dataset mockDatasetWithGeneratedDatasetVersion(DatasetMock dataset) {
        if (dataset == null) {
            dataset = new DatasetMock();
        }
        if (dataset.getMaintainerCode() == null) {
            dataset.setMaintainerCode(mockString(10));
        }
        mockDataset(dataset);

        DatasetVersionMock template = new DatasetVersionMock();
        template.setDataset(dataset);
        template.setMaintainerCode(dataset.getMaintainerCode());
        DatasetVersion datasetVersion = mockDatasetVersion(template);

        return datasetVersion.getDataset();
    }

    public Dataset mockDatasetWithGeneratedDatasetVersion() {
        return mockDatasetWithGeneratedDatasetVersion(null);
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    @Override
    public DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }

    @Override
    public DatasetVersion mockDatasetVersion(DatasetVersionMock datasetVersion) {
        if (datasetVersion == null) {
            datasetVersion = new DatasetVersionMock();
        }

        Dataset dataset = datasetVersion.getDataset();

        // Statistical Operation
        if (dataset != null && dataset.getIdentifiableStatisticalResource() != null && dataset.getIdentifiableStatisticalResource().getStatisticalOperation() != null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(
                    mockStatisticalOperationExternalItem(dataset.getIdentifiableStatisticalResource().getStatisticalOperation().getCode()));
        }

        // CODE
        if (dataset != null && dataset.getIdentifiableStatisticalResource() != null && dataset.getIdentifiableStatisticalResource().getCode() != null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setCode(datasetVersion.getDataset().getIdentifiableStatisticalResource().getCode());
        } else {
            fillNeededMetadataToGenerateDatasetVersionCode(datasetVersion);
            String datasetCode = buildSequentialResourceCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), datasetVersion.getSequentialId());
            datasetVersion.getSiemacMetadataStatisticalResource().setCode(datasetCode);
        }

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION));

        if (datasetVersion.getBibliographicCitation() == null) {
            datasetVersion.setBibliographicCitation(mockInternationalStringMetadata(datasetVersion.getSiemacMetadataStatisticalResource().getCode(), "bibliographicCitation"));
        }

        if (datasetVersion.getRelatedDsd() == null) {
            datasetVersion.setRelatedDsd(mockDsdExternalItem());
        }

        // DATASET CODE
        String datasetCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        if (datasetVersion.getDataset() == null) {
            DatasetMock template = new DatasetMock();
            template.setCode(datasetCode);
            template.setMaintainerCode(getMaintainerCode(datasetVersion));
            template.addVersion(datasetVersion);
            mockDataset(template);
        } else {
            if (!datasetVersion.getDataset().getVersions().contains(datasetVersion)) {
                datasetVersion.getDataset().addVersion(datasetVersion);
            }
        }

        computeCoverageRelatedMetadata(datasetVersion);

        return datasetVersion;
    }

    public static void mockDatasetVersionCoverages(DatasetVersion datasetVersion) {
        datasetVersion.getDimensionsCoverage().clear();

        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C01"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C02"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C03"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM02", "C01"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM02", "C02"));
    }

    public static void mockDatasetVersionCoveragesWithTemporalAndRelated(DatasetVersion datasetVersion) {
        datasetVersion.getDimensionsCoverage().clear();

        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C01"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C02"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("DIM01", "C03"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2010"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2011"));
        datasetVersion.addDimensionsCoverage(new CodeDimension("TIME_PERIOD", "2012"));
    }

    public static void computeCoverageRelatedMetadata(DatasetVersion datasetVersion) {
        if (datasetVersion.getTemporalCoverage().isEmpty()) {
            for (CodeDimension codeDim : datasetVersion.getDimensionsCoverage()) {
                if (codeDim.getDsdComponentId().equals(StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID)) {
                    datasetVersion.addTemporalCoverage(StatisticalResourcesDoMocks.mockTemporalCode(codeDim.getIdentifier(), codeDim.getIdentifier()));
                }
            }
        }

        if (datasetVersion.getGeographicCoverage().isEmpty()) {
            for (CodeDimension codeDim : datasetVersion.getDimensionsCoverage()) {
                if (codeDim.getDsdComponentId().equals("GEO_DIM")) {
                    datasetVersion.addGeographicCoverage(StatisticalResourcesDoMocks.mockCodeExternalItem(codeDim.getIdentifier()));
                }
            }
        }

        if (datasetVersion.getMeasureCoverage().isEmpty()) {
            for (CodeDimension codeDim : datasetVersion.getDimensionsCoverage()) {
                if (codeDim.getDsdComponentId().equals("MEAS_DIM")) {
                    datasetVersion.addMeasureCoverage(StatisticalResourcesDoMocks.mockConceptExternalItem(codeDim.getIdentifier()));
                }
            }
        }
    }

    private void fillNeededMetadataToGenerateDatasetVersionCode(DatasetVersionMock datasetVersion) {
        if (datasetVersion.getSequentialId() == null) {
            datasetVersion.setSequentialId(1);
        }

        if (datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation() == null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }

    private static String buildSequentialResourceCode(String operationCode, int sequentialId) {
        return operationCode + "_" + String.format("%06d", sequentialId);
    }

    // -----------------------------------------------------------------
    // CATEGORISATION
    // -----------------------------------------------------------------
    public Categorisation mockCategorisationWithGeneratedCategory(DatasetVersion datasetVersion, ExternalItem maintainer, String categoryCode, String categorisationCode) {
        return mockCategorisationWithDatasetVersionAndCategory(datasetVersion, maintainer, mockCategoryExternalItem(categoryCode), categorisationCode);
    }

    public Categorisation mockCategorisationWithDatasetVersionAndCategory(DatasetVersion datasetVersion, ExternalItem maintainer, ExternalItem category, String categorisationCode) {
        Categorisation categorisation = new Categorisation();
        categorisation.setVersionableStatisticalResource(new VersionableStatisticalResource());
        categorisation.getVersionableStatisticalResource().setCode(categorisationCode);
        categorisation.getVersionableStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSdmxCategorisationUrn(new String[]{maintainer.getCodeNested()}, categorisationCode, StatisticalResourcesVersionUtils.INITIAL_VERSION));
        categorisation.setDatasetVersion(datasetVersion);
        categorisation.setCategory(category);
        categorisation.setMaintainer(maintainer);
        return mockCategorisation(categorisation);
    }

    // -----------------------------------------------------------------
    // PUBLICATION
    // -----------------------------------------------------------------

    public void mockPublication(PublicationMock publication) {
        if (publication.getIdentifiableStatisticalResource() == null) {
            publication.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }

        fillNeededMetadataToGeneratePublicationCode(publication);

        publication.getIdentifiableStatisticalResource().setCode(
                buildSequentialResourceCode(publication.getIdentifiableStatisticalResource().getStatisticalOperation().getCode(), publication.getSequentialId()));

        mockIdentifiableStatisticalResource(publication.getIdentifiableStatisticalResource(), TypeRelatedResourceEnum.PUBLICATION);

        String maintainerId = publication.getMaintainerCode();

        String[] maintainerAgencyId = new String[]{maintainerId};

        publication.getIdentifiableStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(maintainerAgencyId, publication.getIdentifiableStatisticalResource().getCode()));

    }

    private void fillNeededMetadataToGeneratePublicationCode(PublicationMock publication) {
        if (publication.getSequentialId() == null) {
            publication.setSequentialId(1);
        }

        if (publication.getIdentifiableStatisticalResource().getStatisticalOperation() == null) {
            publication.getIdentifiableStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }

    public Publication mockPublicationWithGeneratedDatasetVersion(PublicationMock publication) {
        if (publication == null) {
            publication = new PublicationMock();
        }
        if (publication.getMaintainerCode() == null) {
            publication.setMaintainerCode(mockString(10));
        }
        mockPublication(publication);

        PublicationVersionMock template = new PublicationVersionMock();
        template.setPublication(publication);
        template.setMaintainerCode(publication.getMaintainerCode());
        template.getSiemacMetadataStatisticalResource().setLastVersion(true);
        PublicationVersion publicationVersion = mockPublicationVersion(template);

        return publicationVersion.getPublication();
    }

    public Publication mockPublicationWithGeneratedPublicationVersion() {
        return mockPublicationWithGeneratedDatasetVersion(null);
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------
    @Override
    public PublicationVersion mockPublicationVersion() {
        return mockPublicationVersion(null);
    }

    public PublicationVersion mockPublicationVersion(PublicationVersionMock publicationVersion) {
        if (publicationVersion == null) {
            publicationVersion = new PublicationVersionMock();
        }

        Publication publication = publicationVersion.getPublication();

        // Statistical Operation
        if (publication != null && publication.getIdentifiableStatisticalResource() != null && publication.getIdentifiableStatisticalResource().getStatisticalOperation() != null) {
            publicationVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(
                    mockStatisticalOperationExternalItem(publication.getIdentifiableStatisticalResource().getStatisticalOperation().getCode()));
        }

        // CODE
        if (publication != null && publication.getIdentifiableStatisticalResource() != null && publication.getIdentifiableStatisticalResource().getCode() != null) {
            publicationVersion.getSiemacMetadataStatisticalResource().setCode(publicationVersion.getPublication().getIdentifiableStatisticalResource().getCode());
        } else {
            fillNeededMetadataToGeneratePublicationVersionCode(publicationVersion);
            String publicationCode = buildSequentialResourceCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), publicationVersion.getSequentialId());
            publicationVersion.getSiemacMetadataStatisticalResource().setCode(publicationCode);
        }

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(publicationVersion.getSiemacMetadataStatisticalResource(),
                TypeRelatedResourceEnum.PUBLICATION_VERSION));

        // PUBLICATION CODE
        String publicationCode = publicationVersion.getSiemacMetadataStatisticalResource().getCode();
        if (publicationVersion.getPublication() == null) {
            PublicationMock template = new PublicationMock();
            template.setCode(publicationCode);
            template.addVersion(publicationVersion);
            template.setMaintainerCode(getMaintainerCode(publicationVersion));
            mockPublication(template);
        } else {
            if (!publicationVersion.getPublication().getVersions().contains(publicationVersion)) {
                publicationVersion.getPublication().addVersion(publicationVersion);
            }
        }

        return publicationVersion;
    }

    private void fillNeededMetadataToGeneratePublicationVersionCode(PublicationVersionMock publicationVersion) {
        if (publicationVersion.getSequentialId() == null) {
            publicationVersion.setSequentialId(1);
        }

        if (publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation() == null) {
            publicationVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }

    private String getMaintainerCode(HasLifecycle resourceWithLifecycle) {
        if (resourceWithLifecycle != null && resourceWithLifecycle.getLifeCycleStatisticalResource() != null && resourceWithLifecycle.getLifeCycleStatisticalResource().getMaintainer() != null) {
            return resourceWithLifecycle.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        }
        return null;
    }

    public Chapter mockChapterWithParent() {
        return mockChapterInParentElementLevel(mockChapterElementLevel(mockPublicationVersion()));
    }

    public Cube mockDatasetCubeWithParent(Dataset mockDatasetWithGeneratedDatasetVersions) {
        Cube cube = mockDatasetCube(mockDatasetWithGeneratedDatasetVersions);
        cube.getElementLevel().setParent(mockChapter().getElementLevel());
        return cube;
    }

    public Cube mockQueryCubeWithParent(Query mockQueryWithGeneratedQueryVersions) {
        Cube cube = mockQueryCube(mockQueryWithGeneratedQueryVersions);
        cube.getElementLevel().setParent(mockChapter().getElementLevel());
        return cube;
    }

    // -----------------------------------------------------------------
    // STATISTICAL OFFICIALITY
    // -----------------------------------------------------------------

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    @Override
    protected void setSpecialCasesSiemacMetadataStatisticalResourceMock(SiemacMetadataStatisticalResource resource) {
        if (resource.getLastUpdate() == null && resource.getResourceCreatedDate() == null) {
            DateTime today = new DateTime();
            resource.setResourceCreatedDate(today.minusDays(1));
            resource.setLastUpdate(today);
        } else if (resource.getLastUpdate() == null) {
            DateTime update = resource.getResourceCreatedDate().plusDays(1);
            if (update.isAfterNow()) {
                update = new DateTime();
            }
            resource.setLastUpdate(update);
        } else if (resource.getResourceCreatedDate() == null) {
            resource.setResourceCreatedDate(resource.getLastUpdate().minusDays(1));
        }
    }

    @Override
    protected void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource, TypeRelatedResourceEnum artefactType) {
        if (resource.getLastVersion() == null) {
            resource.setLastVersion(true);
        }
        if (resource.getProcStatus() == null) {
            resource.setProcStatus(ProcStatusEnum.DRAFT);
        }
        if (resource.getCreationDate() == null) {
            resource.setCreationDate(new DateTime());
        }
        if (resource.getCreationUser() == null) {
            resource.setCreationUser(USER_MOCK);
        }

        String code = resource.getCode();
        String[] maintainerAgencyId = new String[]{resource.getMaintainer().getCodeNested()};
        String version = resource.getVersionLogic();

        // URN
        switch (artefactType) {
            case DATASET:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(maintainerAgencyId, code));
                break;
            case DATASET_VERSION:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainerAgencyId, code, version));
                break;
            case PUBLICATION:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionUrn(maintainerAgencyId, code));
                break;
            case PUBLICATION_VERSION:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainerAgencyId, code, version));
                break;
            case QUERY:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(maintainerAgencyId, code));
                break;
            case QUERY_VERSION:
                resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryVersionUrn(maintainerAgencyId, code, version));
                break;
            default:
                // It's setting by fillIdentiyAndAuditMetadata in DBMockPersisterBase
                break;
        }
    }

    @Override
    protected void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource) {
        if (resource.getVersionLogic() == null) {
            resource.setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);
        }
    }

    @Override
    protected void setSpecialCasesIdentifiableStatisticalResourceMock(IdentifiableStatisticalResource resource, TypeRelatedResourceEnum artefactType) {
        if (resource.getCode() == null) {
            resource.setCode("resource-" + mockString(10));
        }
        if (resource.getUrn() == null) {
            switch (artefactType) {
                case DATASET:
                case DATASET_VERSION:
                case PUBLICATION:
                case PUBLICATION_VERSION:
                case QUERY:
                case QUERY_VERSION:
                    // NOTHING, will be built in lifecycleResource
                    break;
                default:
                    // It's setting by fillIdentiyAndAuditMetadata in DBMockPersisterBase
                    break;
            }
        }
    }

    @Override
    protected void setSpecialCasesStatisticalResourceMock(StatisticalResource resource) {
        if (resource.getStatisticalOperation() == null) {
            resource.setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }

    @Override
    protected void setSpecialCasesQueryVersionMock(QueryVersion queryVersion) {
        // has to be discontinued because the related dataset is not final
        queryVersion.setStatus(QueryStatusEnum.DISCONTINUED);
    }

    @Override
    protected void setSpecialCasesStatisticOfficialityMock(StatisticOfficiality officiality) {
        officiality.setVersion(0L);
    }
}
