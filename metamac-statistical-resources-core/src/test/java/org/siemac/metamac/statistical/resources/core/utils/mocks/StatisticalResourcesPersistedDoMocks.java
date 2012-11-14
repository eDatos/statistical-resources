package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.joda.time.DateTime;
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
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class StatisticalResourcesPersistedDoMocks extends StatisticalResourcesDoMocks {

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public static Datasource mockPersistedDatasource() {
        Datasource datasource = new Datasource();

        datasource.setDatasetVersion(mockPersistedDatasetVersion());
        datasource.setIdentifiableStatisticalResource(mockPersistedIdentifiableStatisticalResource(new IdentifiableStatisticalResource()));

        return datasource;
    }
    
    public static Datasource mockPersistedDatasource(DatasetVersion datasetVersion) {
        Datasource datasource = mockPersistedDatasource();
        datasource.setDatasetVersion(datasetVersion);

        return datasource;
    }

    // -----------------------------------------------------------------
    // DATASET
    // -----------------------------------------------------------------
    public static Dataset mockPersistedDataset() {
        return mockPersistedDataset(false);
    }

    public static Dataset mockPersistedDatasetWithGeneratedDatasetVersions() {
        return mockPersistedDataset(true);
    }

    private static Dataset mockPersistedDataset(boolean withVersion) {
        Dataset ds = new Dataset();
        if (withVersion) {
            ds.addVersion(mockPersistedDatasetVersion(ds));
        }
        return ds;
    }

    // -----------------------------------------------------------------
    // DATASET VERSION
    // -----------------------------------------------------------------
    public static DatasetVersion mockPersistedDatasetVersion() {
        return mockPersistedDatasetVersion(null);
    }

    public static DatasetVersion mockPersistedDatasetVersion(Dataset dataset) {
        DatasetVersion datasetVersion = new DatasetVersion();

        datasetVersion.setSiemacMetadataStatisticalResource(mockPersistedSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (dataset != null) {
            datasetVersion.setDataset(dataset);
        } else {
            Dataset ds = mockPersistedDataset(false);
            datasetVersion.setDataset(ds);
            ds.addVersion(datasetVersion);
        }

        return datasetVersion;
    }

    
    // -----------------------------------------------------------------
    // PUBLICATION
    // -----------------------------------------------------------------
    public static Publication mockPersistedPublication() {
        return mockPersistedPublication(false);
    }

    public static Publication mockPersistedPublicationWithGeneratedPublicationVersions() {
        return mockPersistedPublication(true);
    }

    private static Publication mockPersistedPublication(boolean withVersion) {
        Publication ds = new Publication();
        if (withVersion) {
            ds.addVersion(mockPersistedPublicationVersion(ds));
        }
        return ds;
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION
    // -----------------------------------------------------------------
    public static PublicationVersion mockPersistedPublicationVersion() {
        return mockPersistedPublicationVersion(null);
    }

    public static PublicationVersion mockPersistedPublicationVersion(Publication publication) {
        PublicationVersion publicationVersion = new PublicationVersion();

        publicationVersion.setSiemacMetadataStatisticalResource(mockPersistedSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET, StatisticalResourceFormatEnum.DS));
        if (publication != null) {
            publicationVersion.setPublication(publication);
        } else {
            Publication ds = mockPersistedPublication(false);
            publicationVersion.setPublication(ds);
            ds.addVersion(publicationVersion);
        }

        return publicationVersion;
    }
    
    
    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------
    public static Query mockPersistedQuery() {
        Query query = new Query();
        query.setNameableStatisticalResource(mockPersistedNameableStatisticalResorce());
        return query;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------
    private static SiemacMetadataStatisticalResource mockPersistedSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum type, StatisticalResourceFormatEnum format) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        mockPersistedLifeCycleStatisticalResource(resource);

        resource.setType(type);
        resource.setFormat(format);
        return resource;
    }

    private static LifeCycleStatisticalResource mockPersistedLifeCycleStatisticalResource(LifeCycleStatisticalResource resource) {
        mockPersistedVersionableStatisticalResource(resource);

        resource.setCreator(mockAgencyExternalItem());
        resource.addContributor(mockAgencyExternalItem());
        resource.addMediator(mockAgencyExternalItem());
        resource.addPublisher(mockAgencyExternalItem());
        
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        return resource;
    }

    private static VersionableStatisticalResource mockPersistedVersionableStatisticalResource(VersionableStatisticalResource resource) {
        mockPersistedNameableStatisticalResorce(resource);

         resource.setVersionDate(new DateTime());
         resource.setVersionLogic("01.000");
        return resource;
    }

    private static NameableStatisticalResource mockPersistedNameableStatisticalResorce() {
        NameableStatisticalResource nameableResource = new NameableStatisticalResource();
        mockPersistedNameableStatisticalResorce(nameableResource);
        return nameableResource;
    }

    private static NameableStatisticalResource mockPersistedNameableStatisticalResorce(NameableStatisticalResource nameableResource) {
        mockPersistedIdentifiableStatisticalResource(nameableResource);

        nameableResource.setTitle(mockInternationalString());
        nameableResource.setDescription(mockInternationalString());
        return nameableResource;
    }

    private static IdentifiableStatisticalResource mockPersistedIdentifiableStatisticalResource(IdentifiableStatisticalResource resource) {
        resource.setCode("resource-" + mockString(10));

        mockPersistedStatisticalResource(resource);
        return resource;
    }

    private static StatisticalResource mockPersistedStatisticalResource(StatisticalResource resource) {
        resource.setOperation(mockStatisticalOperationItem());
        return resource;
    }
}
