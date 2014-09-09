package org.siemac.metamac.statistical.resources.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NoticesRestInternalFacade {

    public static final String BEAN_ID = "NoticesRestInternalFacade";

    void createLifeCycleNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException;
    void createSendToProductionValidationNotification(ServiceContext serviceContext, DatasetVersionDto datasetVersionDto) throws MetamacWebException;
}
