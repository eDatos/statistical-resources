package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
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
public class QueryRepositoryTest extends StatisticalResourcesBaseTest implements QueryRepositoryTestBase {

    @Autowired
    protected QueryRepository queryRepository;

    @Autowired
    protected StatisticalResourceRepository statisticalResourceRepository;

    @Test
    public void testFindByUrn() throws MetamacException {
        Query expected = queryRepository.save(StatisticalResourcesDoMocks.mockQuery());
        Query actual = queryRepository.findByUrn(expected.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(expected, actual);
    }

    @Test
    public void tetsFindByUrnNotFound() {
        try {
            queryRepository.findByUrn(URN_NOT_EXISTS);
            fail("not found");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            StatisticalResourcesAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.QUERY_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }
}
