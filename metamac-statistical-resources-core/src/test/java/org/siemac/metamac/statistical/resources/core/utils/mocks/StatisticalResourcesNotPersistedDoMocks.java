package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class StatisticalResourcesNotPersistedDoMocks extends StatisticalResourcesDoMocks {

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public static Datasource mockDatasource(DatasetVersion datasetVersion) {
        Datasource datasource = new Datasource();

        datasource.setIdentifiableStatisticalResource(mockIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));
        datasource.setDatasetVersion(datasetVersion);

        return datasource;
    }

    public static Datasource mockDatasourceWithIdentifiableAndDatasetVersionNull(DatasetVersion datasetVersion) {
        Datasource datasource = mockDatasource(datasetVersion);
        datasource.setIdentifiableStatisticalResource(null);
        datasource.setDatasetVersion(null);

        return datasource;
    }
    
    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------
    public static Dataset mockDatasetWithoutGeneratedDatasetVersions() {
        return mockDataset(false);
    }

    public static Dataset mockDatasetWithGeneratedDatasetVersions() {
        return mockDataset(true);
    }

    private static Dataset mockDataset(boolean withVersion) {
        Dataset ds = new Dataset();
        if (withVersion) {
            ds.addVersion(mockDatasetVersion(ds));
        }
        return ds;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    public static DatasetVersion mockDatasetVersion() {
        DatasetVersion datasetVerion = mockDatasetVersion(null);
        datasetVerion.setDataset(null);
        
        return datasetVerion;
    }

    public static DatasetVersion mockDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = new DatasetVersion();

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (dataset != null) {
            datasetVersion.setDataset(dataset);
        } else {
            Dataset ds = StatisticalResourcesPersistedDoMocks.mockPersistedDataset();
            datasetVersion.setDataset(ds);
            ds.addVersion(datasetVersion);
        }

        return datasetVersion;
    }

    
    // -----------------------------------------------------------------
    // PUBLICATION
    // -----------------------------------------------------------------
    public static Publication mockPublicationWithoutGeneratedPublicationVersions() {
        return mockPublication(false);
    }

    public static Publication mockPublicationWithGeneratedPublicationVersions() {
        return mockPublication(true);
    }

    private static Publication mockPublication(boolean withVersion) {
        Publication ds = new Publication();
        if (withVersion) {
            ds.addVersion(mockPublicationVersion(ds));
        }
        return ds;
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------
    public static PublicationVersion mockPublicationVersion() {
        return mockPublicationVersion(null);
    }

    public static PublicationVersion mockPublicationVersion(Publication publication) {
        PublicationVersion publicationVersion = new PublicationVersion();

        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (publication != null) {
            publicationVersion.setPublication(publication);
        } else {
            Publication ds = StatisticalResourcesPersistedDoMocks.mockPersistedPublication();
            publicationVersion.setPublication(ds);
            ds.addVersion(publicationVersion);
        }

        return publicationVersion;
    }
    
    
    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public static Query mockQuery() {
        Query query = new Query();
        query.setNameableStatisticalResource(mockNameableStatisticalResorce());
        return query;
    }

    public static Query mockQueryWithNameableNull() {
        Query query = mockQuery();
        query.setNameableStatisticalResource(null);
        return query;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------
    private static SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum type, StatisticalResourceFormatEnum format) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        mockLifeCycleStatisticalResource(resource);

        resource.setType(type);
        resource.setFormat(format);
        return resource;
    }

    private static LifeCycleStatisticalResource mockLifeCycleStatisticalResource(LifeCycleStatisticalResource resource) {
        mockVersionableStatisticalResource(resource);

        resource.setCreator(mockAgencyExternalItem());
        resource.addContributor(mockAgencyExternalItem());
        resource.addMediator(mockAgencyExternalItem());
        resource.addPublisher(mockAgencyExternalItem());
        return resource;
    }

    private static VersionableStatisticalResource mockVersionableStatisticalResource(VersionableStatisticalResource resource) {
        mockNameableStatisticalResorce(resource);

        return resource;
    }

    private static NameableStatisticalResource mockNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        mockNameableStatisticalResorce(nameableResource);
        return nameableResource;
    }

    private static NameableStatisticalResource mockNameableStatisticalResorce(NameableStatisticalResource nameableResource) {
        mockIdentifiableStatisticalResource(nameableResource);

        nameableResource.setTitle(mockInternationalString());
        nameableResource.setDescription(mockInternationalString());
        return nameableResource;
    }

    private static IdentifiableStatisticalResource mockIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        resource.setCode("resource-" + mockString(10));

        mockStatisticalResource(resource);
        return resource;
    }

    private static StatisticalResource mockStatisticalResource(StatisticalResource resource) {
        resource.setOperation(mockStatisticalOperationItem());
        return resource;
    }
}
