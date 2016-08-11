package org.siemac.metamac.statistical.resources.core;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Arte
 *         This listener run a check action about database state to find in_progress job. If there are jobs with this state then marks it with failed status.
 *         This listener runs when spring context is initialized (ContextRefreshedEvent)
 */
public class SdmxContextInitialized implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger     logger = LoggerFactory.getLogger(SdmxContextInitialized.class);

    @Autowired
    private TaskServiceFacade tasksServiceFacade;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ServiceContext ctx = new ServiceContext("Metamac", "Tasks", "Metamac");
        try {
            logger.info("Launching markAllInProgressTaskToFailed in order to recover all failed and in progress tasks");
            tasksServiceFacade.markAllInProgressTaskToFailed(ctx);
        } catch (MetamacException e) {
            logger.error("Impossible to mark old jobs with running state to failed state.", e);
        }
    }
}
