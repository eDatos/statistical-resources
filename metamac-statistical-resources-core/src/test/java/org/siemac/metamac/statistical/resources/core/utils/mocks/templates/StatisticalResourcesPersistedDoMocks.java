package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
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
            query.addVersion(mockQueryVersion(query,mockDatasetVersion()));
        }
        return query;
    }
    
    // -----------------------------------------------------------------
    // QUERY VERSION
    // -----------------------------------------------------------------
    @Override
    public QueryVersion mockQueryVersion(DatasetVersion datasetVersion) {
        return mockQueryVersion(null, datasetVersion);
    }
    
    public QueryVersion mockQueryVersion(Query query, DatasetVersion datasetVersion) {
        QueryVersion queryVersion = new QueryVersion();

        queryVersion.setLifeCycleStatisticalResource(mockLifeCycleStatisticalResource(new LifeCycleStatisticalResource()));
        
        if (query == null) {
            query = mockQueryWithoutGeneratedQueryVersions();
        }
        
        queryVersion.setQuery(query);
        query.addVersion(queryVersion);
        
        //Mock code
        queryVersion.getLifeCycleStatisticalResource().setCode(query.getIdentifiableStatisticalResource().getCode());
        
        if (datasetVersion != null) {
            queryVersion.setDatasetVersion(datasetVersion);
        } else {
            throw new IllegalArgumentException("Can not create a Query with no datasetversion linked");
        }
        
        if (datasetVersion.getSiemacMetadataStatisticalResource().getIsLastVersion()) {
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
    // VERSION RATIONALE TYPE
    // -----------------------------------------------------------------
    private static VersionRationaleType mockVersionRationaleType(StatisticalResourceVersionRationaleTypeEnum enumValue) {
        return new VersionRationaleType(enumValue);
    }
    
    
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
    
    
    
    /* Lifecycle preparations */
    
    public static void prepareToProductionValidationSiemacResource(SiemacMetadataStatisticalResource siemacResource) {
        prepareToProductionValidationLifecycleResource(siemacResource);
        
        prepareToLifecycleCommonSiemacResource(siemacResource);
    }
    
    public static void prepareToProductionValidationLifecycleResource(LifeCycleStatisticalResource lifecycleResource) {
        prepareToLifecycleCommonLifeCycleResource(lifecycleResource);
        
        lifecycleResource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
    }
    
    public static void prepareToDiffusionValidationSiemacResource(SiemacMetadataStatisticalResource siemacResource) {
        prepareToDiffusionValidationLifecycleResource(siemacResource);
        
        prepareToLifecycleCommonSiemacResource(siemacResource);
    }
    
    public static void prepareToDiffusionValidationLifecycleResource(LifeCycleStatisticalResource lifecycleResource) {
        prepareToLifecycleCommonLifeCycleResource(lifecycleResource);
        
        lifecycleResource.setProcStatus(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
        lifecycleResource.setProductionValidationDate(new DateTime().minusDays(1));
        lifecycleResource.setProductionValidationUser("productionUser");
    }
    
    
    public static void prepareToLifecycleCommonSiemacResource(SiemacMetadataStatisticalResource siemacResource) {
        siemacResource.setLanguage(mockCodeExternalItem());
        siemacResource.addLanguage(mockCodeExternalItem());
        siemacResource.addLanguage(mockCodeExternalItem());
        
        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem();
        siemacResource.setStatisticalOperation(operation);
        
        siemacResource.setMaintainer(mockAgencyExternalItem());
        siemacResource.setCreator(mockOrganizationUnitExternalItem());
        siemacResource.setLastUpdate(new DateTime().minusMinutes(10));
        
        siemacResource.addPublisher(mockOrganizationUnitExternalItem());
        
        siemacResource.setRightsHolder(mockOrganizationUnitExternalItem());
        siemacResource.setLicense(mockInternationalString());
        
    }
    
    private static void prepareToLifecycleCommonLifeCycleResource(LifeCycleStatisticalResource lifeCycleResource) {
        lifeCycleResource.setVersionLogic("02.000");
        lifeCycleResource.addVersionRationaleType(new VersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MINOR_DATA_UPDATE));
        lifeCycleResource.setNextVersion(StatisticalResourceNextVersionEnum.NON_SCHEDULED_UPDATE);
        
        lifeCycleResource.setTitle(mockInternationalString());
        lifeCycleResource.setDescription(mockInternationalString());
    }

}
