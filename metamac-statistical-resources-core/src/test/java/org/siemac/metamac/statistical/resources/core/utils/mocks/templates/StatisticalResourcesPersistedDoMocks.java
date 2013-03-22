package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class StatisticalResourcesPersistedDoMocks extends StatisticalResourcesDoMocks {

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public Query mockQueryWithGeneratedDatasetVersion() {
        Query query = mockQuery(mockDatasetVersion());
        return query;
    }
    
    public Query mockQueryWithSelectionAndGeneratedDatasetVersion() {
        Query query = mockQueryWithSelectionAndDatasetVersion(mockDatasetVersion());
        return query;
    }
    
    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public Datasource mockDatasourceWithGeneratedDatasetVersion() {
        return mockDatasource(mockDatasetVersion());
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    @Override
    public DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }
    
    @Override
    public DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = mockDatasetVersionMetadata();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET));
        
        // Mock code
        String statisticalOperationCode = datasetVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        datasetVersion.getSiemacMetadataStatisticalResource().setCode(statisticalOperationCode+"_000001");
        
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
    
    // -----------------------------------------------------------------
    // STATISTICAL OFFICIALITY
    // -----------------------------------------------------------------
    
    
    
    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    @Override
    protected void setSpecialCasesSiemacMetadataStatisticalResourceMock(SiemacMetadataStatisticalResource resource) {
        resource.setStatisticalOperation(mockStatisticalOperationItem());

        resource.setResourceCreatedDate(new DateTime());
        resource.setLastUpdate(resource.getResourceCreatedDate());
    }

    @Override
    protected void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource) {
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        resource.setCreationDate(new DateTime());
        resource.setCreationUser(USER_MOCK);
    }

    @Override
    protected void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource) {
        resource.setNextVersionDate(new DateTime());
        resource.setVersionLogic("01.000");
        resource.setIsLastVersion(Boolean.FALSE);
    }

    @Override
    protected void setSpecialCasesQueryMock(Query query) {
        // has to be discontinued because the related dataset is not final
        query.setStatus(QueryStatusEnum.DISCONTINUED);
    }
    
    @Override
    protected void setSpecialCasesStatisticOfficialityMock(StatisticOfficiality officiality) {
        officiality.setVersion(0L);
    }
 
}
