package org.siemac.metamac.statistical.resources.web.server;

import org.siemac.metamac.statistical.resources.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetEditionLanguagesActionHandlers;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetEditionLanguagesAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

/**
 * Module which binds the handlers and configurations.
 */
@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    protected void configureHandlers() {


        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(GetEditionLanguagesAction.class, GetEditionLanguagesActionHandlers.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }

}
