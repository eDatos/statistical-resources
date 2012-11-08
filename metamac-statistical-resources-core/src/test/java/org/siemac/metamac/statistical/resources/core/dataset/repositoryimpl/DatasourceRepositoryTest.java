package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_BASIC_01;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_BASIC_01_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory.DATASOURCE_BASIC_02_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class DatasourceRepositoryTest extends StatisticalResourcesBaseTest implements DatasourceRepositoryTestBase {

    @Autowired
    protected DatasourceRepository datasourceRepository;

    @Test
    @MetamacMock({DATASOURCE_BASIC_01_NAME, DATASOURCE_BASIC_02_NAME})
    public void testRetrieveByUrn() throws MetamacException {
        Datasource expected = DATASOURCE_BASIC_01;
        Datasource actual = datasourceRepository.retrieveByUrn(DATASOURCE_BASIC_01.getIdentifiableStatisticalResource().getUrn());
        assertEqualsDatasource(expected, actual);
    }
    
    @Test
    @MetamacMock({DATASOURCE_BASIC_01_NAME, DATASOURCE_BASIC_02_NAME})
    public void testRetrieveByUrnNotFound() {
        try {
            datasourceRepository.retrieveByUrn(URN_NOT_EXISTS);
            fail("not found");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.DATASOURCE_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }
}
