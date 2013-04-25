package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticalResourcesNotPersistedDoMocks extends StatisticalResourcesDoMocks {

    @Autowired
    StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    
    
    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
   
    @Override
    public QueryVersion mockQueryVersion(DatasetVersion datasetVersion, boolean isDatasetLastVersion) {
        QueryVersion queryVersion = new QueryVersion();

        queryVersion.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(new LifeCycleStatisticalResource()));
        
        //Mock code
        queryVersion.getLifeCycleStatisticalResource().setCode("resource-"+mockString(10));
        
        if (datasetVersion != null) {
            queryVersion.setDatasetVersion(datasetVersion);
        }

        queryVersion.addSelection(mockQuerySelectionItem());
        queryVersion.setType(QueryTypeEnum.FIXED);
        
        return queryVersion;
    }
    
    @Override
    public QueryVersion mockQueryVersionWithGeneratedDatasetVersion() {
        QueryVersion queryVersion = mockQueryVersion(statisticalResourcesPersistedDoMocks.mockDatasetVersion(),true);
        return queryVersion;
    }

    
    public QueryVersion mockQueryVersionWithStatisticalResourceNull(DatasetVersion datasetVersion) {
        QueryVersion queryVersion = mockQueryVersionWithDatasetVersion(datasetVersion,true);
        queryVersion.setLifeCycleStatisticalResource(null);
        return queryVersion;
    }
    

    public QueryVersion mockQueryVersionWithSelectionNull(DatasetVersion datasetVersion) {
        QueryVersion queryVersion = mockQueryVersionWithDatasetVersion(datasetVersion, true);
        queryVersion.getSelection().clear();
        return queryVersion;
    }

    public QueryVersion mockQueryVersionWithSelectionIncorrectDimensionNull(DatasetVersion datasetVersion) {
        QueryVersion queryVersion = mockQueryVersionWithDatasetVersion(datasetVersion, true);
        QuerySelectionItem querySelectionItem = new QuerySelectionItem();
        querySelectionItem.setDimension(null);
        CodeItem code = new CodeItem();
        code.setCode("CODE01");
        querySelectionItem.addCode(code);
        queryVersion.addSelection(querySelectionItem);
        return queryVersion;
    }

    public QueryVersion mockQueryVersionWithSelectionIncorrectCodesNull(DatasetVersion datasetVersion) {
        QueryVersion query = mockQueryVersionWithDatasetVersion(datasetVersion, true);
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
        DatasetVersion datasetVersion = mockDatasetVersionMetadata();
        datasetVersion.setDataset(null);
        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET));

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

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET));
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
    protected void setSpecialCasesIdentifiableStatisticalResourceMock(IdentifiableStatisticalResource resource) {
        // NOTHING
    }

    @Override
    protected void setSpecialCasesQueryVersionMock(QueryVersion queryVersion) {
        // NOTHING
    }

    @Override
    protected void setSpecialCasesStatisticOfficialityMock(StatisticOfficiality officiality) {
        // NOTHING
    }

}
