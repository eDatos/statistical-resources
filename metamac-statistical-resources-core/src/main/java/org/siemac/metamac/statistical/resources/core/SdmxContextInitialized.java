package org.siemac.metamac.statistical.resources.core;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Arte
 *         This listener run a check action about database state to find in_progress job. If there are jobs with this state then marks it with failed status.
 *         This listener runs when spring context is initialized (ContextRefreshedEvent)
 */
@Component
public class SdmxContextInitialized implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger     logger = LoggerFactory.getLogger(SdmxContextInitialized.class);

    @Autowired
    private TaskServiceFacade tasksServiceFacade;

    public void test() throws Exception {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ServiceContext ctx = new ServiceContext("Metamac", "Tasks", "Metamac");
        try {
            tasksServiceFacade.markAllInProgressTaskToFailed(ctx);
        } catch (MetamacException e) {
            logger.error("Impossible to mark old jobs with running state to failed state.", e);
        }
    }
}
