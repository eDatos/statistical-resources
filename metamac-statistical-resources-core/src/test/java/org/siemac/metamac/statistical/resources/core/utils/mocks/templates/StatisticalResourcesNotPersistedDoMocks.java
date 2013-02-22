package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticalResourcesNotPersistedDoMocks extends StatisticalResourcesDoMocks {

    @Autowired
    StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------

    public Query mockQueryWithStatisticalResourceNull(DatasetVersion datasetVersion) {
        Query query = mockQueryWithDatasetVersion(datasetVersion);
        query.setLifeCycleStatisticalResource(null);
        return query;
    }
    
    public Query mockQueryWithSelectionNull(DatasetVersion datasetVersion) {
        Query query = mockQueryWithDatasetVersion(datasetVersion);
        query.getSelection().clear();
        return query;
    }
    
    public Query mockQueryWithSelectionIncorrectDimensionNull(DatasetVersion datasetVersion) {
        Query query = mockQueryWithDatasetVersion(datasetVersion);
        QuerySelectionItem querySelectionItem = new QuerySelectionItem();
        querySelectionItem.setDimension(null);
        CodeItem code = new CodeItem();
        code.setCode("CODE01");
        querySelectionItem.addCode(code);
        query.addSelection(querySelectionItem);
        return query;
    }
    
    public Query mockQueryWithSelectionIncorrectCodesNull(DatasetVersion datasetVersion) {
        Query query = mockQueryWithDatasetVersion(datasetVersion);
        QuerySelectionItem querySelectionItem = new QuerySelectionItem();
        querySelectionItem.setDimension("DIMENSION01");
        querySelectionItem.getCodes().clear();
        query.addSelection(querySelectionItem);
        return query;
    }
    

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public Datasource mockDatasourceWithIdentifiableAndDatasetVersionNull(DatasetVersion datasetVersion) {
        Datasource datasource = mockDatasource(datasetVersion);
        datasource.setIdentifiableStatisticalResource(null);
        datasource.setDatasetVersion(null);

        return datasource;
    }

    public Datasource mockDatasourceForPersist() {
        return mockDatasource();
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    @Override
    public DatasetVersion mockDatasetVersion() {
        DatasetVersion datasetVerion = mockDatasetVersion(null);
        datasetVerion.setDataset(null);

        return datasetVerion;
    }

    @Override
    public DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = mockDatasetVersionMetadata();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (dataset != null) {
            datasetVersion.setDataset(dataset);
        } else {
            Dataset ds = statisticalResourcesPersistedDoMocks.mockDatasetWithoutGeneratedDatasetVersions();
            datasetVersion.setDataset(ds);
            ds.addVersion(datasetVersion);
        }

        return datasetVersion;
    }

    public DatasetVersion mockDatasetVersionWithNullableSiemacStatisticalResource() {
        DatasetVersion datasetVersion = mockDatasetVersion();
        datasetVersion.setSiemacMetadataStatisticalResource(null);
        return datasetVersion;
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------
    @Override
    public PublicationVersion mockPublicationVersion() {
        PublicationVersion publicationVersion = mockPublicationVersion(null);
        publicationVersion.setPublication(null);
        return publicationVersion;
    }

    @Override
    public PublicationVersion mockPublicationVersion(Publication publication) {
        PublicationVersion publicationVersion = mockPublicationVersionMetadata();

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (publication != null) {
            publicationVersion.setPublication(publication);
        } else {
            Publication pub = statisticalResourcesPersistedDoMocks.mockPublicationWithoutGeneratedPublicationVersions();
            publicationVersion.setPublication(pub);
            pub.addVersion(publicationVersion);
        }

        return publicationVersion;
    }

    public PublicationVersion mockPublicationVersionWithNullableSiemacStatisticalResource() {
        PublicationVersion publicationVersion = mockPublicationVersion();
        publicationVersion.setSiemacMetadataStatisticalResource(null);
        return publicationVersion;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    @Override
    protected void setSpecialCasesSiemacMetadataStatisticalResourceMock(SiemacMetadataStatisticalResource resource) {
        // NOTHING
    }

    @Override
    protected void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource) {
        // NOTHING

    }

    @Override
    protected void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource) {
        // NOTHING

    }

    @Override
    protected void setSpecialCasesQueryMock(Query query) {
        // NOTHING
        
    }
}
