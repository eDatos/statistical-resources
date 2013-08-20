package org.siemac.metamac.statistical.resources.core.query.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsDatasetVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsSelection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_15_PUBLISHED_NAME;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/dataset-repository-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class QueryVersioningServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private QueryVersionMockFactory        queryVersionMockFactory;

    @Autowired
    @Qualifier("queryLifecycleService")
    private LifecycleService<QueryVersion> queryVersionLifecycleService;

    @Test
    @MetamacMock(QUERY_VERSION_15_PUBLISHED_NAME)
    @Ignore
    public void testVersioningQueryVersion() throws Exception {
        QueryVersion queryVersion = queryVersionMockFactory.retrieveMock(QUERY_VERSION_15_PUBLISHED_NAME);

        QueryVersion queryNewVersion = queryVersionLifecycleService.versioning(getServiceContextWithoutPrincipal(), queryVersion.getLifeCycleStatisticalResource()
                .getUrn(), VersionTypeEnum.MINOR);
        assertNotNull(queryNewVersion);
        assertFalse(queryVersion.getLifeCycleStatisticalResource().getVersionLogic().equals(queryNewVersion.getLifeCycleStatisticalResource().getVersionLogic()));
        checkNewQueryVersionCreated(queryVersion, queryNewVersion);
    }

    private static void checkNewQueryVersionCreated(QueryVersion previous, QueryVersion next) throws MetamacException {
        BaseAsserts.assertEqualsVersioningLifecycle(previous.getLifeCycleStatisticalResource(), next.getLifeCycleStatisticalResource());

        // Non inherited fields

        // Inherited
        assertEquals(previous.getLatestDataNumber(), next.getLatestDataNumber());
        assertEquals(previous.getLatestTemporalCodeInCreation(), next.getLatestTemporalCodeInCreation());
        assertEqualsDatasetVersion(previous.getDatasetVersion(), next.getDatasetVersion());
        assertEqualsQuery(previous.getQuery(), next.getQuery());
        assertEquals(previous.getStatus(), next.getStatus());
        assertEquals(previous.getType(), next.getType());
        assertEqualsSelection(previous.getSelection(), next.getSelection());
    }
}