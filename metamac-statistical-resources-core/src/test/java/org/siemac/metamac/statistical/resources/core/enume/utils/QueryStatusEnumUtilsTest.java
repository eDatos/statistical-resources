package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class QueryStatusEnumUtilsTest extends StatisticalResourcesBaseTest {

    @Autowired
    private StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;
    
    @Test
    public void testCheckPossibleQueryStatus() throws Exception {
        QueryVersion resource = statisticalResourcesPersistedDoMocks.mockQueryVersionWithGeneratedDatasetVersion();
        resource.setStatus(QueryStatusEnum.DISCONTINUED);
        QueryStatusEnumUtils.checkPossibleQueryStatus(resource, QueryStatusEnum.DISCONTINUED, QueryStatusEnum.ACTIVE, QueryStatusEnum.PENDING_REVIEW);
    }
    
    @Test
    public void testCheckPossibleQueryStatusExpectingException() throws Exception {
        String urn = "URN_DUMMY";
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DISCONTINUED, PENDING_REVIEW"));

        QueryVersion resource = statisticalResourcesPersistedDoMocks.mockQueryVersionWithGeneratedDatasetVersion();
        resource.setStatus(QueryStatusEnum.ACTIVE);
        resource.getLifeCycleStatisticalResource().setUrn(urn);
        
        QueryStatusEnumUtils.checkPossibleQueryStatus(resource, QueryStatusEnum.DISCONTINUED, QueryStatusEnum.PENDING_REVIEW);
    }

}