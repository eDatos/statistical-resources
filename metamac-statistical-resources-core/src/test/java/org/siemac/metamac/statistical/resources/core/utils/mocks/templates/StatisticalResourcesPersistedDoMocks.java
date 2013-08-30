package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
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
import org.springframework.stereotype.Component;

@Component
public class StatisticalResourcesPersistedDoMocks extends StatisticalResourcesDoMocks {

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public Query mockQueryWithoutGeneratedQueryVersions() {
        return mockQuery(false);
    }

    public Query mockQueryWithGeneratedQueryVersions() {
        return mockQuery(true);
    }

    private Query mockQuery(boolean withVersion) {
        Query query = new Query();

        IdentifiableStatisticalResource identifiable = mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource(), TypeRelatedResourceEnum.QUERY);
        query.setIdentifiableStatisticalResource(identifiable);

        if (withVersion) {
            mockQueryVersion(query, mockDatasetVersion(), true);
        }
        return query;
    }

    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
    @Override
    public QueryVersion mockQueryVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        return mockQueryVersion(null, datasetVersion, isDatasetLastVersion);
    }

    public QueryVersion mockQueryVersion(Query query, DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        QueryVersion queryVersion = new QueryVersion();

        queryVersion.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(new LifeCycleStatisticalResource(), TypeRelatedResourceEnum.QUERY_VERSION));

        if (query == null) {
            query = mockQueryWithoutGeneratedQueryVersions();
        }

        queryVersion.setQuery(query);
        query.addVersion(queryVersion);

        // Mock code
        queryVersion.getLifeCycleStatisticalResource().setCode(query.getIdentifiableStatisticalResource().getCode());

        if (datasetVersion != null) {
            queryVersion.setDatasetVersion(datasetVersion);
        } else {
            throw new IllegalArgumentException("Can not create a Query with no datasetversion linked");
        }

        if (isDatasetLastVersion) {
            queryVersion.setStatus(QueryStatusEnum.ACTIVE);
        } else {
            queryVersion.setStatus(QueryStatusEnum.DISCONTINUED);
        }

        queryVersion.addSelection(mockQuerySelectionItem());
        queryVersion.setType(QueryTypeEnum.FIXED);

        return queryVersion;
    }

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public Datasource mockDatasourceWithGeneratedDatasetVersion() {
        return mockDatasource(mockDatasetVersion());
    }
    
    public Datasource mockDatasourceWithDatasetVersion(DatasetVersion datasetVersion) {
        return mockDatasource(datasetVersion);
    }

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------

    public Dataset mockDataset(Dataset dataset, String maintainerId) {
        IdentifiableStatisticalResource resource = dataset.getIdentifiableStatisticalResource();
        if (resource == null) {
            resource = new IdentifiableStatisticalResource();
        }
        dataset.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(resource, TypeRelatedResourceEnum.DATASET));
        
        if (maintainerId == null) {
            maintainerId = mockString(10);
        }
        
        String[] maintainerAgencyId = new String[] {maintainerId};
        
        dataset.getIdentifiableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(maintainerAgencyId, dataset.getIdentifiableStatisticalResource().getCode()));
        
        return dataset;
    }

    public Dataset mockDatasetWithGeneratedDatasetVersions(Dataset dataset) {
        dataset = mockDataset(new Dataset(), null);
        
        DatasetVersionMock datasetVersion = new DatasetVersionMock();
        datasetVersion.setDataset(dataset);
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(dataset.getIdentifiableStatisticalResource().getCode());
        dataset.addVersion(mockDatasetVersion(datasetVersion));        
        return dataset;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    @Override
    public DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }

    public DatasetVersion mockDatasetVersion(DatasetVersionMock datasetVersion) {
        if (datasetVersion == null) {
            datasetVersion = new DatasetVersionMock();
        }
        
        
        //CODE
        if (datasetVersion.getSequentialId() != null || datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation() != null) {
            fillNeededMetadataToGenerateDatasetVersionCode(datasetVersion);
            String datasetCode = buildDatasetCode(datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode(), datasetVersion.getSequentialId());
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
            template.addVersion(datasetVersion);
            
            datasetVersion.setDataset(mockDataset(template, datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()));
        }
        
        return datasetVersion;
    }
    
    private void fillNeededMetadataToGenerateDatasetVersionCode(DatasetVersionMock datasetVersion) {
        if (datasetVersion.getSequentialId() == null) {
            datasetVersion.setSequentialId(1);
        }
        
        if (datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation() == null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
    }
    private void fillNeededMetadataToGenerateUrn(DatasetVersionMock datasetVersion) {
        if (datasetVersion.getSequentialId() == null) {
            datasetVersion.setSequentialId(1);
        }
        
        if (datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation() == null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setStatisticalOperation(mockStatisticalOperationExternalItem(mockString(10)));
        }
        
        if (datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer() == null) {
            String code = mockString(10);
            datasetVersion.getSiemacMetadataStatisticalResource().setMaintainer(mockAgencyExternalItem(code, code));
        }
        if (datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic() == null) {
            datasetVersion.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        }
    }

    private static String buildDatasetCode(String operationCode, int sequentialId) {
        return operationCode + "_" +String.format("%06d", sequentialId);
    }

    protected String buildDatasetVersionUrn(DatasetVersion datasetVersion) {
        String[] maintainerCodes = new String[] {datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        String datasetCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        String version = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();
        return GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(maintainerCodes, datasetCode, version);
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------
    @Override
    public PublicationVersion mockPublicationVersion() {
        return mockPublicationVersion(null);
    }

    @Override
    public PublicationVersion mockPublicationVersion(Publication publication) {
        PublicationVersion publicationVersion = mockPublicationVersionMetadata();

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.PUBLICATION_VERSION));
        if (publication != null) {
            publicationVersion.setPublication(publication);
        } else {
            Publication pub = mockPublicationWithoutGeneratedPublicationVersion();
            publicationVersion.setPublication(pub);
            pub.addVersion(publicationVersion);
        }

        return publicationVersion;
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
                    //NOTHING, will be built in lifecycleResource
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
