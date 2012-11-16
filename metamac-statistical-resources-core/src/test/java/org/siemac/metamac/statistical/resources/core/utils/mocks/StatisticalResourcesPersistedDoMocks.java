package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.stereotype.Component;

@Component
public class StatisticalResourcesPersistedDoMocks extends StatisticalResourcesDoMocks {

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public Datasource mockDatasource() {
        return mockDatasource(mockDatasetVersion());
    }
    
    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    public DatasetVersion mockDatasetVersion() {
        return mockDatasetVersion(null);
    }

    public DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = mockDatasetVersionMetadata();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
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
    public PublicationVersion mockPublicationVersion() {
        return mockPublicationVersion(null);
    }

    public PublicationVersion mockPublicationVersion(Publication publication) {
        PublicationVersion publicationVersion = mockPublicationVersionMetadata();

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
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
    // BASE HIERARCHY
    // -----------------------------------------------------------------
   
    @Override
    protected void setSpecialCasesLifeCycleStatisticalResourceMock(LifeCycleStatisticalResource resource) {
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
    }

    @Override
    protected void setSpecialCasesVersionableStatisticalResourceMock(VersionableStatisticalResource resource) {
        resource.setVersionDate(new DateTime());
        resource.setVersionLogic("01.000");        
    }

}
