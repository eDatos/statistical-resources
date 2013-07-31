package org.siemac.metamac.statistical.resources.web.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.web.client.gin.StatisticalResourcesWebGinjector;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetStatisticOfficialitiesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetStatisticOfficialitiesResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoResult;
import org.siemac.metamac.web.common.client.MetamacSecurityEntryPoint;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class StatisticalResourcesWeb extends MetamacSecurityEntryPoint {

    private static Logger                                logger           = Logger.getLogger(StatisticalResourcesWeb.class.getName());
    private static final boolean                         SECURITY_ENABLED = true;

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

    @Override
    protected void onBeforeLoadApplication() {
        super.onBeforeLoadApplication();

        retrieveStatisticOfficialities();

        retrieveDefaults();
    }

    private void retrieveStatisticOfficialities() {
        ginjector.getDispatcher().execute(new GetStatisticOfficialitiesAction(), new AsyncCallback<GetStatisticOfficialitiesResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error retrieving statistic officialities");
            }
            @Override
            public void onSuccess(GetStatisticOfficialitiesResult result) {
                CommonUtils.setStatisticOfficialities(result.getStatisticOfficialities());
            }
        });
    }

    private void retrieveDefaults() {
        ginjector.getDispatcher().execute(new GetDefaultAgencyAction(), new AsyncCallback<GetDefaultAgencyResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error retrieving default agency");
                StatisticalResourcesDefaults.defaultAgency = null;
            }

            @Override
            public void onSuccess(GetDefaultAgencyResult result) {
                StatisticalResourcesDefaults.defaultAgency = result.getAgency();
            }
        });

        ginjector.getDispatcher().execute(new GetDefaultLanguageInfoAction(), new AsyncCallback<GetDefaultLanguageInfoResult>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "Error retrieving default language");
                StatisticalResourcesDefaults.defaultLanguage = null;
            }

            @Override
            public void onSuccess(GetDefaultLanguageInfoResult result) {
                StatisticalResourcesDefaults.defaultLanguage = result.getDefaultLanguage();
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
        StatisticalResourcesWeb.principal = principal;
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