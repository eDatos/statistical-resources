package org.siemac.metamac.statistical.resources.web.external;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;

public class ApplicationStartup implements ServletContextListener {

    private static final Log     LOG = LogFactory.getLog(ApplicationStartup.class);

    private ConfigurationService configurationService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            configurationService = ApplicationContextProvider.getApplicationContext().getBean(ConfigurationService.class);
            checkConfiguration();
        } catch (Exception e) {
            // Abort startup application
            throw new RuntimeException(e);
        }
    }

    private void checkConfiguration() {
        LOG.info("**********************************************************");
        LOG.info("Checking application configuration");
        LOG.info("**********************************************************");

        // Datasource
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DRIVER_NAME);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_URL);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_USERNAME);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_PASSWORD);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DIALECT);
        // Datasource repository
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DRIVER_NAME);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_URL);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_USERNAME);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_PASSWORD);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DIALECT);

        // Api
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_STATISTICAL_RESOURCES_EXTERNAL_API);
        configurationService.checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_SRM_EXTERNAL_API);

        LOG.info("**********************************************************");
        LOG.info("Application configuration checked");
        LOG.info("**********************************************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
