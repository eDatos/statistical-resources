package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsCategorisation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CategorisationMockFactory.CATEGORISATION_01_DATASET_VERSION_01_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CategorisationRepositoryTest extends StatisticalResourcesBaseTest implements CategorisationRepositoryTestBase {

    @Autowired
    protected CategorisationRepository categorisationRepository;

    @Override
    @Test
    @MetamacMock({CATEGORISATION_01_DATASET_VERSION_01_NAME})
    public void testRetrieveByUrn() throws Exception {
        Categorisation expected = categorisationMockFactory.retrieveMock(CATEGORISATION_01_DATASET_VERSION_01_NAME);
        Categorisation actual = categorisationRepository.retrieveByUrn(expected.getVersionableStatisticalResource().getUrn());
        assertEqualsCategorisation(expected, actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.CATEGORISATION_NOT_FOUND, URN_NOT_EXISTS));
        categorisationRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    public void testRetrieveCategorisationsByDatasetVersionUrn() throws Exception {
        // tested in DatasetRepositoryTest
    }
}
