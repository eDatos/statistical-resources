package org.siemac.metamac.statistical.resources.web.server.listener;

import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.web.common.server.listener.ApplicationStartupListener;

public class StatisticalResourcesStartupListener extends ApplicationStartupListener {

    @Override
    public void checkConfiguration() {

        // SECURITY

        checkSecurityProperties();

        // DATASOURCE

        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DRIVER_NAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_URL);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_USERNAME);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_PASSWORD);
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.DB_DIALECT);

        // API

        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);

        // OTHER CONFIGURATION PROPERTIES

        // Common properties

        checkEditionLanguagesProperty();
        checkNavBarUrlProperty();
        checkOrganisationProperty();

        // Statistical resources properties

        checkRequiredProperty(StatisticalResourcesConfigurationConstants.USER_GUIDE_FILE_NAME);

        // TODO add properties to check
    }
}
