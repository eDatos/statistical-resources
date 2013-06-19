package org.siemac.metamac.statistical.resources.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.web.client.gin.StatisticalResourcesWebGinjector;
import org.siemac.metamac.web.common.client.MetamacEntryPoint;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;
import org.siemac.metamac.web.common.client.utils.ApplicationEditionLanguages;
import org.siemac.metamac.web.common.client.widgets.MetamacNavBar;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlResult;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlResult;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesResult;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.MockCASUserResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StatisticalResourcesWeb extends MetamacSecurityEntryPoint {

    private static final boolean                         SECURITY_ENABLED = true;
    private static Logger                                logger           = Logger.getLogger(StatisticalResourcesWeb.class.getName());

    private static MetamacPrincipal                      principal;
    private static StatisticalResourcesWebConstants      constants;
    private static StatisticalResourcesWebCoreMessages   coreMessages;
    private static StatisticalResourcesWebMessages       messages;

    public static final StatisticalResourcesWebGinjector ginjector        = GWT.create(StatisticalResourcesWebGinjector.class);

    @Override
    public void onModuleLoad() {
        setUncaughtExceptionHandler();
        
        prepareApplication(SECURITY_ENABLED);
    }

    public static MetamacPrincipal getCurrentUser() {
        return StatisticalResourcesWeb.principal;
    }

    public static StatisticalResourcesWebConstants getConstants() {
        if (constants == null) {
            constants = (StatisticalResourcesWebConstants) GWT.create(StatisticalResourcesWebConstants.class);
        }
        return constants;
    }

    public static StatisticalResourcesWebCoreMessages getCoreMessages() {
        if (coreMessages == null) {
            coreMessages = (StatisticalResourcesWebCoreMessages) GWT.create(StatisticalResourcesWebCoreMessages.class);
        }
        return coreMessages;
    }

    public static StatisticalResourcesWebMessages getMessages() {
        if (messages == null) {
            messages = (StatisticalResourcesWebMessages) GWT.create(StatisticalResourcesWebMessages.class);
        }
        return messages;
    }

    public static StatisticalResourcesWebGinjector getResourcesWebGinjector() {
        return ginjector;
    }

    public static void showErrorPage() {
        ginjector.getPlaceManager().revealErrorPlace(null);
    }
    
    @Override
    protected String getApplicationTitle() {
        return getConstants().appTitle();
    }
    
    @Override
    protected MetamacPrincipal getPrincipal() {
        return principal;
    }
    
    @Override
    protected void setPrincipal(MetamacPrincipal principal) {
        this.principal = principal;
    }
    
    @Override
    protected String getSecurityApplicationId() {
        return StatisticalResourcesConstants.SECURITY_APPLICATION_ID;
    }
    
    @Override
    protected MetamacWebGinjector getWebGinjector() {
        return getResourcesWebGinjector();
    }
}