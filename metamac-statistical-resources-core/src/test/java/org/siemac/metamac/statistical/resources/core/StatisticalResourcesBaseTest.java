package org.siemac.metamac.statistical.resources.core;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.SchedulerRepository;
import org.siemac.metamac.common.test.MetamacBaseTest;
import org.siemac.metamac.common.test.dbunit.MetamacDBUnitBaseTests.DataBaseProvider;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.task.serviceimpl.TaskServiceImpl;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockAnnotationRule;
import org.springframework.beans.factory.annotation.Value;

public abstract class StatisticalResourcesBaseTest extends MetamacBaseTest {

    protected static String   EMPTY           = StringUtils.EMPTY;

    protected static Long     ID_NOT_EXISTS   = Long.valueOf(-1);
    protected static String   URN_NOT_EXISTS  = "not_exists";
    protected static String   CODE_NOT_EXISTS = "NOT_EXISTS";

    @Value("${metamac.statistical_resources.db.provider}")
    private String            databaseProvider;

    @Rule
    public MockAnnotationRule mockRule        = new MockAnnotationRule();

    @Rule
    public ExpectedException  thrown          = ExpectedException.none();

    protected ServiceContext getServiceContextWithoutPrincipal() {
        return mockServiceContextWithoutPrincipal();
    }

    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalResourcesRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalResourcesConstants.SECURITY_APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    @Override
    protected DataBaseProvider getDatabaseProvider() {
        return DataBaseProvider.valueOf(databaseProvider);
    }

    protected void waitUntilJobFinished() throws InterruptedException, SchedulerException {
        // Wait until the job is finished
        Thread.sleep(5 * 1000);
        Scheduler sched = SchedulerRepository.getInstance().lookup(TaskServiceImpl.SCHEDULER_INSTANCE_NAME); // get a reference to a scheduler
        while (sched.getCurrentlyExecutingJobs().size() != 0) {
            Thread.sleep(1 * 1000);
        }
    }
}
