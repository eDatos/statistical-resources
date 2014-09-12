package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical.resources.web.server.dtos.ResourceNotificationBaseDto;
import org.siemac.metamac.statistical.resources.web.server.dtos.ResourceNotificationDto;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface NoticesRestInternalFacade {

    public static final String BEAN_ID = "NoticesRestInternalFacade";

    void createLifeCycleNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException;
    void createLifeCycleNotifications(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notificationss) throws MetamacWebException;
}
