package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
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

        IdentifiableStatisticalResource identifiable = mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        query.setIdentifiableStatisticalResource(identifiable);

        if (withVersion) {
            query.addVersion(mockQueryVersion(query, mockDatasetVersion(), true));
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

        queryVersion.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(new LifeCycleStatisticalResource()));

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

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------

    public Dataset mockDatasetWithoutGeneratedDatasetVersions() {
        return mockDataset(false);
    }

    public Dataset mockDatasetWithGeneratedDatasetVersions() {
        return mockDataset(true);
    }

    private Dataset mockDataset(boolean withVersion) {
        Dataset dataset = new Dataset();
        dataset.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));
        if (withVersion) {
            dataset.addVersion(mockDatasetVersion(dataset));
        }
        return dataset;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    @Override
    public DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }

    public DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = mockDatasetVersionMetadata();
        String datasetCode = datasetVersion.getSiemacMetadataStatisticalResource().getCode();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET));
        datasetVersion.setBibliographicCitation(mockInternationalStringMetadata(datasetCode, "bibliographicCitation"));

        // Mock code
        String statisticalOperationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(statisticalOperationCode + "_000001");

        if (dataset != null) {
            datasetVersion.setDataset(dataset);
        } else {
            Dataset ds = mockDatasetWithoutGeneratedDatasetVersions();
            datasetVersion.setDataset(ds);
            ds.addVersion(datasetVersion);
        }

        return datasetVersion;
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

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.COLLECTION));
        if (publication != null) {
            publicationVersion.setPublication(publication);
        } else {
            Publication pub = mockPublicationWithoutGeneratedPublicationVersions();
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
        resource.setStatisticalOperation(mockStatisticalOperationExternalItem("statisticalOperation01"));

        DateTime today = new DateTime();
        resource.setResourceCreatedDate(today.minusDays(1));
        resource.setLastUpdate(today);
    }
    @Override
    protected void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource) {
        resource.setProcStatus(ProcStatusEnum.DRAFT);
        resource.setCreationDate(new DateTime());
        resource.setCreationUser(USER_MOCK);
    }

    @Override
    protected void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource) {
        resource.setNextVersionDate(new DateTime());
        resource.setVersionLogic(VersionUtil.PATTERN_XXX_YYY_INITIAL_VERSION);
    }

    @Override
    protected void setSpecialCasesIdentifiableStatisticalResourceMock(IdentifiableStatisticalResource resource) {
        resource.setCode("resource-" + mockString(10));
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
