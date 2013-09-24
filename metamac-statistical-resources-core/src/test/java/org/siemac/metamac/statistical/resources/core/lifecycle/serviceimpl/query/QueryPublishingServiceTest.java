package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesMockRestBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
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
@Ignore
public class QueryPublishingServiceTest extends StatisticalResourcesMockRestBaseTest {

    @Autowired
    private DatasetVersionMockFactory        datasetVersionMockFactory;

    @Autowired
    private DatasetVersionRepository         datasetVersionRepository;

    @Autowired
    @Qualifier("datasetLifecycleService")
    private LifecycleService<DatasetVersion> datasetVersionLifecycleService;

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private TaskService                      taskService;

    @Override
    @Before
    public void setUp() throws MetamacException {
        super.setUp();
        mockAllTaskInProgressForDatasetVersion(false);
    }

    private void assertPublishingQueryVersion(QueryVersion current) throws MetamacException {
        LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(current, null);

        assertTrue(current.getFixedDatasetVersion() != null || current.getDataset() != null);
        assertNotNull(current.getStatus());
        assertTrue(current.getStatus().equals(QueryStatusEnum.ACTIVE) || current.getStatus().equals(QueryStatusEnum.DISCONTINUED));
    }

    // -------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------

    private void mockAllTaskInProgressForDatasetVersion(boolean status) throws MetamacException {
        TaskMockUtils.mockAllTaskInProgressForDatasetVersion(taskService, status);
    }

}
