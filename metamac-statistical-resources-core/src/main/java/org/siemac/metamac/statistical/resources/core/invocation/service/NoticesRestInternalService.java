package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.io.Serializable;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;

public interface NoticesRestInternalService {

    public static final String BEAN_ID = "noticesRestInternalService";

    // Background Notifications
    public void createErrorBackgroundNotification(String user, String actionCode, MetamacException exception);

    public void createSuccessBackgroundNotification(String user, String actionCode, String successMessageCode, Serializable... successMessageParameters);

    // Stream Messaging Notifications
    public void createErrorOnStreamMessagingService(String user, String actionCode, List<HasSiemacMetadata> affectedResources, String errorMessageCode, Serializable... errorMessageParameters);

}
