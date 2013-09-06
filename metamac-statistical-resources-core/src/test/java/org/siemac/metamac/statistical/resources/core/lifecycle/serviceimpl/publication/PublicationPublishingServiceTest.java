package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSiemacSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_40_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME;

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
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
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
public class PublicationPublishingServiceTest extends StatisticalResourcesMockRestBaseTest {

    @Autowired
    private PublicationVersionMockFactory publicationVersionMockFactory;

    @Autowired
    @Qualifier("publicationLifecycleService")
    private LifecycleService<PublicationVersion> publicationVersionLifecycleService;

    @Autowired
    private PublicationVersionRepository publicationVersionRepository;
    
    @Autowired
    private TaskService                      taskService;

    @Override
    @Before
    public void setUp() throws MetamacException {
        super.setUp();
        mockAllTaskInProgressForDatasetVersion(false);
    }


    @MetamacMock({})
    //FIXME: mock with previous version
    public void testPublishPublicationVersionWithPreviousVersion() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(DATASET_VERSION_72_PREPARED_TO_PUBLISH_WITH_PREVIOUS_VERSION_NAME);
        SiemacMetadataStatisticalResource siemacResource = publicationVersion.getSiemacMetadataStatisticalResource();
        String urn = siemacResource.getUrn();

        mockSiemacExternalItemsPublished(siemacResource);

        publicationVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), urn);

        publicationVersion = publicationVersionRepository.retrieveByUrn(urn);

        Assert.assertNotNull(publicationVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getPublicationVersion());

        assertPublishingPublicationVersion(publicationVersion, publicationVersion.getSiemacMetadataStatisticalResource().getReplacesVersion().getPublicationVersion());
    }

    
    @MetamacMock(PUBLICATION_VERSION_40_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME)
    //FIXME create Mock
    public void testPublishDatasetVersionCheckExternalItemsNotPublished() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_40_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME);
        SiemacMetadataStatisticalResource siemacResource = publicationVersion.getSiemacMetadataStatisticalResource();
        String urn = siemacResource.getUrn();

        mockSiemacExternalItemsNotPublished(siemacResource);
        
        //No external items to check in PublictaionVersion

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        exceptionItems.addAll(getExceptionItemsForExternalItemNotPublishedSiemac(siemacResource, "publicationVersion"));

        expectedMetamacException(new MetamacException(exceptionItems));

        publicationVersionLifecycleService.sendToPublished(getServiceContextAdministrador(), urn);
    }
    


    private void assertPublishingPublicationVersion(PublicationVersion current, PublicationVersion previous) throws MetamacException {
        assertNotNullAutomaticallyFilledMetadataSiemacSendToPublished(current, previous);

        Integer formatExtentResources = current.getChildrenAllLevels().size();
        assertEquals(formatExtentResources, current.getFormatExtentResources());
    }

    
    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------


    private void mockAllTaskInProgressForDatasetVersion(boolean status) throws MetamacException {
        TaskMockUtils.mockAllTaskInProgressForDatasetVersion(taskService, status);
    }

}
