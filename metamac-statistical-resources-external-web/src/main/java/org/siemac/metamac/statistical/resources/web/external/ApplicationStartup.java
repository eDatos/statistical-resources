package org.siemac.metamac.statistical.resources.web.external;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.listener.ApplicationStartupListener;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;

public class ApplicationStartup extends ApplicationStartupListener {

    @Override
    public String projectName() {
        return "statistical-resources-external";
    }

    @Override
    public void checkApplicationProperties() throws MetamacException {
        // Datasource
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_URL);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DIALECT);

        // Datasource repository
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DRIVER_NAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_URL);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_USERNAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_PASSWORD);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_REPOSITORY_DIALECT);

        // Api
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_STATISTICAL_RESOURCES_EXTERNAL_API);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_SRM_EXTERNAL_API);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_EXTERNAL_API);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);

        // Web application
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.WEB_APPLICATION_PORTAL_EXTERNAL_WEB);

        // Misc
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.METAMAC_EDITION_LANGUAGES);
    }
}
