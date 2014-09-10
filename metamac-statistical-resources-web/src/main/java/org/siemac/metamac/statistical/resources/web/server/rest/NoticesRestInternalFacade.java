package org.siemac.metamac.statistical.resources.web.server.rest;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical.resources.web.shared.dtos.NotificationDto;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NoticesRestInternalFacade {

    public static final String BEAN_ID = "NoticesRestInternalFacade";

    void createLifeCycleNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException;
}
