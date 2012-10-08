package org.siemac.metamac.statistical.resources.core.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.serviceapi.utils.StatisticalResourcesAsserts;
import org.siemac.metamac.statistical.resources.core.serviceapi.utils.StatisticalResourcesDoMocks;
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
public class StatisticalResourceServiceTest extends StatisticalResourcesBaseTest implements StatisticalResourceServiceTestBase {

    @Autowired
    protected StatisticalResourceService statisticalResourceService;
    
    @Autowired
    protected QueryRepository queryRepository;
    

    @Test
    public void testRetrieveQueryByUrn() throws MetamacException {
        Query expected = queryRepository.save(StatisticalResourcesDoMocks.mockQuery());
        Query actual = statisticalResourceService.retrieveQueryByUrn(getServiceContextAdministrador(), expected.getNameableStatisticalResource().getUrn());
        StatisticalResourcesAsserts.assertEqualsQuery(expected, actual);
    }
    
    @Test
    public void testRetrieveQueryByUrnParameterRequired() throws MetamacException {
        try {
            statisticalResourceService.retrieveQueryByUrn(getServiceContextAdministrador(), EMPTY);
            fail("parameter required");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            MetamacAsserts.assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.URN}, e.getExceptionItems().get(0));
        }
    }
    
    
}
