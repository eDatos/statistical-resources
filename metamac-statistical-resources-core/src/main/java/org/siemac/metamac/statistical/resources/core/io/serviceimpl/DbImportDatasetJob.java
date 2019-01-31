package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbImportDatasetJob implements Job {

    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    private static Logger                     logger = LoggerFactory.getLogger(DbImportDatasetJob.class);

    private StatisticalResourcesServiceFacade getStatisticalResourcesServiceFacade() {
        if (statisticalResourcesServiceFacade == null) {
            statisticalResourcesServiceFacade = (StatisticalResourcesServiceFacade) ApplicationContextProvider.getApplicationContext().getBean("statisticalResourcesServiceFacade");
        }
        return statisticalResourcesServiceFacade;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ServiceContext serviceContext = new ServiceContext("Metamac", context.getFireInstanceId(), "statistical-resources-core");
        try {
            logger.debug("DbImportDatasetJob: The database import job is running at {}", new Date());
            getStatisticalResourcesServiceFacade().createDbImportDatasetJob(serviceContext);
            logger.debug("DbImportDatasetJob: The database import job successfully executed at {}", new Date());
        } catch (MetamacException e) {
            logger.error("DbImportDatasetJob: an unexpected error has occurred during the job execution", e);
        }
    }

}
