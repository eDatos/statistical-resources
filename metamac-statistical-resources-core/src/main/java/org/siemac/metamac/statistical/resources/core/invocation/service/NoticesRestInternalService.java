package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.io.Serializable;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface NoticesRestInternalService {

    public static final String BEAN_ID = "noticesRestInternalService";

    // Background Notifications
    public void createErrorBackgroundNotification(String user, String actionCode, MetamacException exception);
    public void createDbImportErrorBackgroundNotification(String statisticalOperationUrn, String actionCode, MetamacException exception);
    public void createSuccessBackgroundNotification(String user, String actionCode, String successMessageCode, Serializable... successMessageParameters);

    // Stream Messaging Notifications
    public void createErrorOnStreamMessagingService(String user, String actionCode, HasSiemacMetadata affectedResource, String errorMessageCode, Serializable... extraParameters);
    public void createErrorOnStreamMessagingService(String user, String actionCode, QueryVersion affectedResource, String errorMessageCode, Serializable... extraParameters);
}
