package org.siemac.metamac.statistical.resources.core.utils.mocks.templates;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
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

        datasetVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource(StatisticalResourceTypeEnum.DATASET));

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

    // -----------------------------------------------------------------
    // STATISTICAL OFFICIALITY
    // -----------------------------------------------------------------

    // -----------------------------------------------------------------
    // VERSION RATIONALE TYPE
    // -----------------------------------------------------------------
    private static VersionRationaleType mockVersionRationaleType(VersionRationaleTypeEnum enumValue) {
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

    
    // -----------------------------------------------------------------
    // LIFE CYCLE PREPARATIONS
    // -----------------------------------------------------------------

    public static void prepareToProductionValidationSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        prepareToProductionValidationLifecycleResource(resource);

        prepareToLifecycleCommonSiemacResource(resource);
    }

    public static void prepareToProductionValidationLifecycleResource(HasLifecycleStatisticalResource resource) {
        prepareToLifecycleCommonLifeCycleResource(resource);

        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);
    }

    public static void prepareToDiffusionValidationSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        prepareToDiffusionValidationLifecycleResource(resource);

        prepareToLifecycleCommonSiemacResource(resource);
    }

    public static void prepareToDiffusionValidationLifecycleResource(HasLifecycleStatisticalResource resource) {
        prepareToLifecycleCommonLifeCycleResource(resource);

        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setProductionValidationDate(new DateTime().minusDays(1));
        resource.getLifeCycleStatisticalResource().setProductionValidationUser("productionUser");
    }
    
    public static void prepareToPublishedSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        prepareToPublishedLifecycleResource(resource);

        prepareToLifecycleCommonSiemacResource(resource);
    }

    public static void prepareToPublishedLifecycleResource(HasLifecycleStatisticalResource resource) {
        prepareToLifecycleCommonLifeCycleResource(resource);

        DateTime publicationDate = new DateTime().minusDays(1);
        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DIFFUSION_VALIDATION);
        resource.getLifeCycleStatisticalResource().setValidFrom(publicationDate);
        resource.getLifeCycleStatisticalResource().setDiffusionValidationDate(publicationDate);
        resource.getLifeCycleStatisticalResource().setDiffusionValidationUser("diffusionUser");
    }

    public static void createPublishedSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        prepareToPublishedLifecycleResource(resource);
        createPublishedLifecycleResource(resource);
    }
    
    public static void createPublishedLifecycleResource(HasLifecycleStatisticalResource resource) {
        prepareToLifecycleCommonLifeCycleResource(resource);

        resource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PUBLISHED);
        resource.getLifeCycleStatisticalResource().setPublicationDate(new DateTime().minusDays(1));
        resource.getLifeCycleStatisticalResource().setPublicationUser("diffusionUser");
    }
    
    private static void prepareToLifecycleCommonSiemacResource(HasSiemacMetadataStatisticalResource resource) {
        resource.getSiemacMetadataStatisticalResource().setLanguage(mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().addLanguage(mockCodeExternalItem());
        resource.getSiemacMetadataStatisticalResource().addLanguage(mockCodeExternalItem());

        ExternalItem operation = StatisticalResourcesPersistedDoMocks.mockStatisticalOperationItem();
        resource.getSiemacMetadataStatisticalResource().setStatisticalOperation(operation);

        resource.getSiemacMetadataStatisticalResource().setMaintainer(mockAgencyExternalItem());
        resource.getSiemacMetadataStatisticalResource().setCreator(mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().setLastUpdate(new DateTime().minusMinutes(10));

        resource.getSiemacMetadataStatisticalResource().addPublisher(mockOrganizationUnitExternalItem());

        resource.getSiemacMetadataStatisticalResource().setRightsHolder(mockOrganizationUnitExternalItem());
        resource.getSiemacMetadataStatisticalResource().setLicense(mockInternationalString());

    }

    private static void prepareToLifecycleCommonLifeCycleResource(HasLifecycleStatisticalResource resource) {
        resource.getLifeCycleStatisticalResource().setVersionLogic("002.000");
        resource.getLifeCycleStatisticalResource().addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnum.MINOR_DATA_UPDATE));
        resource.getLifeCycleStatisticalResource().setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);

        resource.getLifeCycleStatisticalResource().setTitle(mockInternationalString());
        resource.getLifeCycleStatisticalResource().setDescription(mockInternationalString());
    }

}
