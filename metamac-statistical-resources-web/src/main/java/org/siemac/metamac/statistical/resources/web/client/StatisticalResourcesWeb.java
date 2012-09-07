package org.siemac.metamac.statistical.resources.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.web.client.gin.StatisticalResourcesWebGinjector;
import org.siemac.metamac.web.common.client.MetamacEntryPoint;
import org.siemac.metamac.web.common.client.events.LoginAuthenticatedEvent;
import org.siemac.metamac.web.common.client.utils.ApplicationEditionLanguages;
import org.siemac.metamac.web.common.client.widgets.IstacNavBar;
import org.siemac.metamac.web.common.client.widgets.WaitingAsyncCallback;
import org.siemac.metamac.web.common.shared.GetEditionLanguagesAction;
import org.siemac.metamac.web.common.shared.GetEditionLanguagesResult;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlResult;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlResult;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.MockCASUserResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StatisticalResourcesWeb extends MetamacEntryPoint {

    private static Logger                                logger    = Logger.getLogger(StatisticalResourcesWeb.class.getName());

    private static MetamacPrincipal                      principal;
    private static StatisticalResourcesWebConstants      constants;
    private static StatisticalResourcesWebCoreMessages   coreMessages;
    private static StatisticalResourcesWebMessages       messages;

    public static final StatisticalResourcesWebGinjector ginjector = GWT.create(StatisticalResourcesWebGinjector.class);

    interface GlobalResources extends ClientBundle {

        @NotStrict
        @Source("StatisticalResourcesWebStyles.css")
        CssResource css();
    }

    public void onModuleLoad() {
        ginjector.getDispatcher().execute(new GetNavigationBarUrlAction(), new WaitingAsyncCallback<GetNavigationBarUrlResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error loading toolbar");
                loadNonSecuredApplication();
            }

            public void onWaitSuccess(GetNavigationBarUrlResult result) {
                // Load scripts for navigation bar
                IstacNavBar.loadScripts(result.getNavigationBarUrl());
                loadNonSecuredApplication();
            };
        });

    }

    // TODO This method should be removed to use CAS authentication
    // Application id should be the same than the one defined in org.siemac.metamac.statistical.operations.core.constants.StatisticalResourcesConstants.SECURITY_APPLICATION_ID
    private void loadNonSecuredApplication() {
        ginjector.getDispatcher().execute(new MockCASUserAction(StatisticalResourcesConstants.SECURITY_APPLICATION_ID), new WaitingAsyncCallback<MockCASUserResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error mocking CAS user");
            }
            @Override
            public void onWaitSuccess(MockCASUserResult result) {
                StatisticalResourcesWeb.principal = result.getMetamacPrincipal();

                // Load edition languages
                ginjector.getDispatcher().execute(new GetEditionLanguagesAction(), new WaitingAsyncCallback<GetEditionLanguagesResult>() {

                    @Override
                    public void onWaitFailure(Throwable caught) {
                        logger.log(Level.SEVERE, "Error loading edition languages");
                        // If an error occurs while loading edition languages, enable SPANISH, ENGLISH and PORTUGUESE by default
                        ApplicationEditionLanguages.setEditionLanguages(new String[]{ApplicationEditionLanguages.SPANISH, ApplicationEditionLanguages.ENGLISH, ApplicationEditionLanguages.PORTUGUESE});
                        loadApplication();
                    }
                    @Override
                    public void onWaitSuccess(GetEditionLanguagesResult result) {
                        ApplicationEditionLanguages.setEditionLanguages(result.getLanguages());
                        loadApplication();
                    }
                });
            }
        });
    }

    // TODO Restore this method to use CAS authentication
    // private void loadSecuredApplication() {
    // String ticketParam = Window.Location.getParameter(TICKET);
    // if (ticketParam != null) {
    // UrlBuilder urlBuilder = Window.Location.createUrlBuilder();
    // urlBuilder.removeParameter(TICKET);
    // urlBuilder.setHash(Window.Location.getHash() + TICKET_HASH + ticketParam);
    // String url = urlBuilder.buildString();
    // Window.Location.replace(url);
    // return;
    // }
    //
    // String hash = Window.Location.getHash();
    //
    // String ticketHash = null;
    // if (hash.contains(TICKET_HASH)) {
    // ticketHash = hash.substring(hash.indexOf(TICKET_HASH) + TICKET_HASH.length(), hash.length());
    // }
    //
    // if (ticketHash == null || ticketHash.length() == 0) {
    // displayLoginView();
    // } else {
    // String serviceUrl = Window.Location.createUrlBuilder().buildString();
    // ginjector.getDispatcher().execute(new ValidateTicketAction(ticketHash, serviceUrl), new WaitingAsyncCallback<ValidateTicketResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable arg0) {
    // logger.log(Level.SEVERE, "Error validating ticket");
    // }
    // @Override
    // public void onWaitSuccess(ValidateTicketResult result) {
    // ResourcesWeb.principal = result.getMetamacPrincipal();
    //
    // String url = Window.Location.createUrlBuilder().setHash("").buildString();
    // Window.Location.assign(url);
    //
    // // Load edition languages
    // ginjector.getDispatcher().execute(new GetEditionLanguagesAction(), new WaitingAsyncCallback<GetEditionLanguagesResult>() {
    //
    // @Override
    // public void onWaitFailure(Throwable caught) {
    // logger.log(Level.SEVERE, "Error loading edition languages");
    // // If an error occurs while loading edition languages, enable SPANISH, ENGLISH and PORTUGUESE by default
    // ApplicationEditionLanguages.setEditionLanguages(new String[]{ApplicationEditionLanguages.SPANISH, ApplicationEditionLanguages.ENGLISH,
    // ApplicationEditionLanguages.PORTUGUESE});
    // loadApplication();
    // }
    // @Override
    // public void onWaitSuccess(GetEditionLanguagesResult result) {
    // ApplicationEditionLanguages.setEditionLanguages(result.getLanguages());
    // loadApplication();
    // }
    // });
    // }
    // });
    // }
    // }

    private void loadApplication() {
        LoginAuthenticatedEvent.fire(ginjector.getEventBus(), StatisticalResourcesWeb.principal);
        // This is required for GWT-Platform proxy's generator.
        DelayedBindRegistry.bind(ginjector);
        ginjector.getPlaceManager().revealCurrentPlace();
        // Inject global styles
        GWT.<GlobalResources> create(GlobalResources.class).css().ensureInjected();
    }

    public void displayLoginView() {
        String serviceUrl = Window.Location.createUrlBuilder().buildString();
        ginjector.getDispatcher().execute(new GetLoginPageUrlAction(serviceUrl), new WaitingAsyncCallback<GetLoginPageUrlResult>() {

            @Override
            public void onWaitFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error getting login page URL");
            }
            @Override
            public void onWaitSuccess(GetLoginPageUrlResult result) {
                Window.Location.replace(result.getLoginPageUrl());
            }
        });
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

}