package org.siemac.metamac.statistical.resources.web.server.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class StatisticalResourcesStartupListener extends ApplicationStartupListener {

    private static final Log LOG = LogFactory.getLog(StatisticalResourcesStartupListener.class);

    @Override
    public void checkConfiguration() {

        LOG.info("**************************************************************");
        LOG.info("[metamac-statistical-resources-web] Checking application configuration");
        LOG.info("**************************************************************");

        // SECURITY
        checkSecurityProperties();
        checkWebApplicationProperties();
        checkApiProperties();

        // DATASOURCE
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_URL);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DIALECT);
        // DATASOURCE REPOSITORY
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DRIVER_NAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_URL);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_USERNAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_PASSWORD);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DIALECT);

        // DEFAULT RESOURCES
        checkDefaultCodelistTemporalGranularityUrn();
        checkDefaultCodelistGeographicalGranularityUrn();
        checkDefaultCodeLanguageUrn();
        checkDefaultCodelistLanguagesUrn();

        // OTHER CONFIGURATION PROPERTIES

        // Common properties
        checkEditionLanguagesProperty();
        checkNavBarUrlProperty();
        checkOrganisationProperty();

        // Statistical resources properties
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.USER_GUIDE_FILE_NAME);

        LOG.info("**************************************************************");
        LOG.info("[metamac-statistical-resources-web] Application configuration checked");
        LOG.info("**************************************************************");
    }

    private void checkApiProperties() {
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
    }

    private void checkWebApplicationProperties() {
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_SRM_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_PORTAL_EXTERNAL_WEB);
    }
}
