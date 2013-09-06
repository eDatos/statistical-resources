package org.siemac.metamac.statistical.resources.core.enume.utils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class ProcStatusEnumUtilsTest extends StatisticalResourcesBaseTest {

    @Test
    public void testCheckPossibleProcStatus() throws Exception {
        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.DRAFT);

        ProcStatusEnumUtils.checkPossibleProcStatus(mockedResource, new ProcStatusEnum[]{ProcStatusEnum.DRAFT, ProcStatusEnum.PUBLISHED});
    }

    @Test
    public void testCheckPossibleProcStatusExpectingException() throws Exception {
        String urn = "URN_DUMMY";
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, PUBLISHED"));

        HasLifecycle mockedResource = mock(HasLifecycle.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());

        mockedResource.getLifeCycleStatisticalResource().setProcStatus(ProcStatusEnum.PRODUCTION_VALIDATION);
        mockedResource.getLifeCycleStatisticalResource().setUrn(urn);

        ProcStatusEnumUtils.checkPossibleProcStatus(mockedResource, new ProcStatusEnum[]{ProcStatusEnum.DRAFT, ProcStatusEnum.PUBLISHED});
    }

}