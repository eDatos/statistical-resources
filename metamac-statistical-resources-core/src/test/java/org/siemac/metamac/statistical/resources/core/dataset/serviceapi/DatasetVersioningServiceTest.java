package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/include/task-mockito.xml",
        "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class DatasetVersioningServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetVersionMockFactory        datasetVersionMockFactory;

    @Autowired
    @Qualifier("datasetLifecycleService")
    private LifecycleService<DatasetVersion> datasetVersionLifecycleService;
    
    @Autowired
    private TaskService                             taskService;

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    @Ignore
    public void testVersioningDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DatasetVersion datasetNewVersion = datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), urn, VersionTypeEnum.MINOR);

        assertNotNull(datasetNewVersion);
        assertFalse(datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(datasetNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewDatasetVersionCreated(datasetVersion, datasetNewVersion);
    }

    @Test
    @MetamacMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME)
    public void testVersioningDatasetVersionImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        String urn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        mockTaskInProgressForResource(urn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, urn));

        datasetVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), urn, VersionTypeEnum.MINOR);
    }

    private static void checkNewDatasetVersionCreated(DatasetVersion previous, DatasetVersion next) throws MetamacException {
        BaseAsserts.assertEqualsVersioningSiemacMetadata(previous.getSiemacMetadataStatisticalResource(), next.getSiemacMetadataStatisticalResource());

        // Non inherited fields
        assertEquals(0, next.getGeographicCoverage().size());
        assertEquals(0, next.getTemporalCoverage().size());
        assertEquals(0, next.getMeasureCoverage().size());
        assertNull(next.getDateStart());
        assertNull(next.getDateEnd());
        assertNull(next.getDateNextUpdate());
        assertNull(next.getBibliographicCitation());

        // Inherited
        BaseAsserts.assertEqualsExternalItemCollection(previous.getGeographicGranularities(), next.getGeographicGranularities());
        BaseAsserts.assertEqualsExternalItemCollection(previous.getTemporalGranularities(), next.getTemporalGranularities());
        BaseAsserts.assertEqualsExternalItemCollection(previous.getStatisticalUnit(), next.getStatisticalUnit());
        BaseAsserts.assertEqualsExternalItem(previous.getRelatedDsd(), next.getRelatedDsd());
        BaseAsserts.assertEqualsExternalItem(previous.getUpdateFrequency(), next.getUpdateFrequency());
        BaseAsserts.assertEqualsStatisticOfficiality(previous.getStatisticOfficiality(), next.getStatisticOfficiality());
    }
    

    private void mockTaskInProgressForResource(String datasetVersionUrn, boolean status) throws MetamacException {
        TaskMockUtils.mockTaskInProgressForDatasetVersion(taskService, datasetVersionUrn, status);
    }

}
