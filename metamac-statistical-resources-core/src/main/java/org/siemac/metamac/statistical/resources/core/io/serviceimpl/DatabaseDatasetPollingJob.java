package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class DatabaseDatasetPollingJob implements Job {

    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    private static Logger                     logger = LoggerFactory.getLogger(DatabaseDatasetPollingJob.class);

    private StatisticalResourcesServiceFacade getStatisticalResourcesServiceFacade() {
        if (statisticalResourcesServiceFacade == null) {
            statisticalResourcesServiceFacade = (StatisticalResourcesServiceFacade) ApplicationContextProvider.getApplicationContext().getBean("statisticalResourcesServiceFacade");
        }
        return statisticalResourcesServiceFacade;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.debug("The database dataset polling job is running at {}", new Date());
            ServiceContext serviceContext = new ServiceContext("Metamac", context.getFireInstanceId(), "statistical-resources-core");
            getStatisticalResourcesServiceFacade().updateDatabaseDatasetsIfNeeded(serviceContext);
            logger.debug("The database dataset polling job successfully executed at {}", new Date());
        } catch (MetamacException e) {
            logger.error("An unexpected error has occurred during the database dataset polling job execution", e);
        }
    }
}
