package org.siemac.metamac.statistical.resources.core.enume.utils;

import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class ProcStatusEnumUtilsTest extends StatisticalResourcesBaseTest {

    @Test
    public void testCheckPossibleProcStatus() throws Exception {
        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.PUBLISHED);
    }
    
    @Test
    public void testCheckPossibleProcStatusExpectingException() throws Exception {
        String urn = "URN_DUMMY";
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, PUBLISHED"));

        LifeCycleStatisticalResource resource = new LifeCycleStatisticalResource();
        resource.setProcStatus(StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION);
        resource.setUrn(urn);
        
        ProcStatusEnumUtils.checkPossibleProcStatus(resource, StatisticalResourceProcStatusEnum.DRAFT, StatisticalResourceProcStatusEnum.PUBLISHED);
    }

}