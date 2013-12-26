package org.siemac.metamac.statistical.resources.core.dataset.serviceapi;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.mock.Mocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasetServiceWithoutDatasetRepositoryMockitoTest extends StatisticalResourcesBaseTest {

    @Autowired
    private DatasetService         datasetService;

    @Autowired
    private SrmRestInternalService srmRestInternalService;

    @PersistenceContext(unitName = "StatisticalResourcesEntityManagerFactory")
    protected EntityManager        entityManager;

    @Autowired
    @Qualifier("dataSourceDatasetRepository")
    public void setDataSourceRepository(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            this.jdbcTemplateRepository = new JdbcTemplate(dataSource);
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Autowired
    @Qualifier("dataSource")
    public void setDataSourceResources(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            this.jdbcTemplateResources = new JdbcTemplate(dataSource);
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @Before
    public void setUp() throws Exception {
        Mocks.mockRestService(srmRestInternalService);

        clearDataBase(); // Clear dirty database
    }

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    @Test
    public void testCreateDatasetVersion() throws Exception {
        DatasetVersion expected = notPersistedDoMocks.mockDatasetVersion();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        DatasetVersion actual = datasetService.createDatasetVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertNotNull(actual.getDatasetRepositoryId());
    }
}
