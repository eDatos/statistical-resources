package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSiemacSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesMockRestBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/include/apis-locator-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetPublishingServiceTest extends StatisticalResourcesMockRestBaseTest {

    private static final String              BIBLIOGRAPHIC_CITATION_TEMPLATE = "#AUTHOR_CODE# (#PUB_DATE#) #TITLE# (v#VERSION#) [dataset]. #PUBLISHER_NAME# (#URI#)";

    @Autowired
    private DatasetVersionRepository         datasetVersionRepository;

    @Autowired
    @Qualifier("datasetLifecycleService")
    private LifecycleService<DatasetVersion> datasetVersionLifecycleService;

    @Autowired
    private TaskService                      taskService;

    @Override
    @Before
    public void setUp() throws MetamacException {
        super.setUp();
        mockAllTaskInProgressForDatasetVersion(false);
    }

    @Test
    @MetamacMock(DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME)
    public void testPublishDatasetVersionFirstVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME);
        SiemacMetadataStatisticalResource siemacResource = datasetVersion.getSiemacMetadataStatisticalResource();
        String datasetVersionUrn = siemacResource.getUrn();

        mockSiemacExternalItemsPublished(siemacResource);

        mockDatasetVersionExternalItemsPublished(datasetVersion);

        datasetVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersionUrn);

        datasetVersion = datasetVersionRepository.retrieveByUrn(datasetVersionUrn);

        assertPublishingDatasetVersion(datasetVersion, null);
    }

    @Test
    @MetamacMock(DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME)
    public void testPublishDatasetVersionWithPreviousVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME);
        SiemacMetadataStatisticalResource siemacResource = datasetVersion.getSiemacMetadataStatisticalResource();
        String datasetVersionUrn = siemacResource.getUrn();

        mockSiemacExternalItemsPublished(siemacResource);

        mockDatasetVersionExternalItemsPublished(datasetVersion);

        datasetVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersionUrn);

        datasetVersion = datasetVersionRepository.retrieveByUrn(datasetVersionUrn);

        Assert.assertNotNull(datasetVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getDatasetVersion());

        assertPublishingDatasetVersion(datasetVersion, datasetVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getDatasetVersion());
    }

    @Test
    @MetamacMock(DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME)
    public void testPublishDatasetVersionCheckExternalItemsNotPublished() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME);
        SiemacMetadataStatisticalResource siemacResource = datasetVersion.getSiemacMetadataStatisticalResource();
        String datasetVersionUrn = siemacResource.getUrn();

        mockSiemacExternalItemsNotPublished(siemacResource);
        mockDatasetVersionExternalItemsNotPublished(datasetVersion);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        exceptionItems.addAll(getExceptionItemsForExternalItemNotPublishedSiemac(siemacResource, "datasetVersion"));
        exceptionItems.addAll(getExceptionItemsForExternalItemNotPublishedDataset(datasetVersion));

        expectedMetamacException(new MetamacException(exceptionItems));

        datasetVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    @MetamacMock(DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED_NAME)
    public void testPublishDatasetVersionCheckRelatedResourcesNotPublished() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_71_RELATED_RESOURCES_UNPUBLISHED_NAME);
        SiemacMetadataStatisticalResource siemacResource = datasetVersion.getSiemacMetadataStatisticalResource();
        String datasetVersionUrn = siemacResource.getUrn();

        mockSiemacExternalItemsPublished(siemacResource);
        mockDatasetVersionExternalItemsPublished(datasetVersion);

        String prefix = "datasetVersion.siemacMetadataStatisticalResource";

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(buildRelatedResourceNotPublishedException(siemacResource.getReplaces(), prefix, "replaces"));

        expectedMetamacException(new MetamacException(exceptionItems));

        datasetVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersionUrn);
    }

    private void assertPublishingDatasetVersion(DatasetVersion current, DatasetVersion previous) throws MetamacException {
        assertNotNullAutomaticallyFilledMetadataSiemacSendToPublished(current, previous);

        InternationalString bibliographicCitation = buildBibliographicCitation(current, "en", "es");
        BaseAsserts.assertEqualsInternationalString(bibliographicCitation, current.getBibliographicCitation());
    }

    private List<MetamacExceptionItem> getExceptionItemsForExternalItemNotPublishedDataset(DatasetVersion datasetVersion) {
        String prefix = "datasetVersion";
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(datasetVersion.getGeographicCoverage(), prefix, "geographicCoverage"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(datasetVersion.getMeasureCoverage(), prefix, "measureCoverage"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(datasetVersion.getGeographicGranularities(), prefix, "geographicGranularities"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(datasetVersion.getTemporalGranularities(), prefix, "temporalGranularities"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(datasetVersion.getStatisticalUnit(), prefix, "statisticalUnit"));
        exceptionItems.add(buildExternalItemNotPublishedException(datasetVersion.getRelatedDsd(), prefix, "relatedDsd"));
        exceptionItems.add(buildExternalItemNotPublishedException(datasetVersion.getUpdateFrequency(), prefix, "updateFrequency"));
        for (Categorisation categorisation : datasetVersion.getCategorisations()) {
            exceptionItems.add(buildExternalItemNotPublishedException(categorisation.getCategory(), prefix, "categorisations"));
            exceptionItems.add(buildExternalItemNotPublishedException(categorisation.getMaintainer(), prefix, "categorisations"));
        }
        return exceptionItems;
    }

    private InternationalString buildBibliographicCitation(DatasetVersion datasetVersion, String... locales) {
        InternationalString bibliographicCitation = new InternationalString();
        String creatorCode = datasetVersion.getSiemacMetadataStatisticalResource().getCreator().getCode();
        String pubYear = String.valueOf(datasetVersion.getSiemacMetadataStatisticalResource().getValidFrom().getYear());
        String version = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();
        String preBuiltTemplate = BIBLIOGRAPHIC_CITATION_TEMPLATE.replaceAll("#AUTHOR_CODE#", creatorCode);
        preBuiltTemplate = preBuiltTemplate.replaceAll("#PUB_DATE#", pubYear);
        preBuiltTemplate = preBuiltTemplate.replaceAll("#VERSION#", version);

        for (String locale : locales) {
            String localisedTemplate = preBuiltTemplate;
            String title = datasetVersion.getSiemacMetadataStatisticalResource().getTitle().getLocalisedLabel(locale);
            String publisherName = datasetVersion.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle().getLocalisedLabel(locale);
            localisedTemplate = localisedTemplate.replaceAll("#TITLE#", title);
            localisedTemplate = localisedTemplate.replaceAll("#PUBLISHER_NAME#", publisherName);
            LocalisedString localisedString = new LocalisedString();
            localisedString.setLocale(locale);
            localisedString.setLabel(localisedTemplate);
            bibliographicCitation.getTexts().add(localisedString);
        }
        return bibliographicCitation;
    }

    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------

    private void mockDatasetVersionExternalItemsNotPublished(DatasetVersion datasetVersion) {
        mockExternalItemsNotPublished(datasetVersion.getGeographicCoverage());
        mockExternalItemsNotPublished(datasetVersion.getMeasureCoverage());
        mockExternalItemsNotPublished(datasetVersion.getGeographicGranularities());
        mockExternalItemsNotPublished(datasetVersion.getTemporalGranularities());
        mockExternalItemsNotPublished(datasetVersion.getStatisticalUnit());
        mockExternalItemNotPublished(datasetVersion.getRelatedDsd());
        mockExternalItemNotPublished(datasetVersion.getUpdateFrequency());
        for (Categorisation categorisation : datasetVersion.getCategorisations()) {
            mockExternalItemNotPublished(categorisation.getCategory());
            mockExternalItemNotPublished(categorisation.getMaintainer());
        }
    }

    private void mockDatasetVersionExternalItemsPublished(DatasetVersion datasetVersion) {
        mockExternalItemsPublished(datasetVersion.getGeographicCoverage());
        mockExternalItemsPublished(datasetVersion.getMeasureCoverage());
        mockExternalItemsPublished(datasetVersion.getGeographicGranularities());
        mockExternalItemsPublished(datasetVersion.getTemporalGranularities());
        mockExternalItemsPublished(datasetVersion.getStatisticalUnit());
        mockExternalItemPublished(datasetVersion.getRelatedDsd());
        mockExternalItemPublished(datasetVersion.getUpdateFrequency());
        for (Categorisation categorisation : datasetVersion.getCategorisations()) {
            mockExternalItemPublished(categorisation.getCategory());
            mockExternalItemPublished(categorisation.getMaintainer());
        }
    }

    private void mockAllTaskInProgressForDatasetVersion(boolean status) throws MetamacException {
        TaskMockUtils.mockAllTaskInProgressForDatasetVersion(taskService, status);
    }

}
