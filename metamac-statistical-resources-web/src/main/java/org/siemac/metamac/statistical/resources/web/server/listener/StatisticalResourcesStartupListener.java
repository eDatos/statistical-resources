package org.siemac.metamac.statistical.resources.web.server.listener;

import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.statistical.resources.core.stream.serviceimpl.StreamMessagingServiceKafkaImpl;
import org.siemac.metamac.web.common.server.listener.InternalApplicationStartupListener;

public class StatisticalResourcesStartupListener extends InternalApplicationStartupListener {

    @Override
    public void checkDatasourceProperties() {
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
    }

    @Override
    public void checkWebApplicationsProperties() {
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_STATISTICAL_OPERATIONS_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_SRM_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_COMMON_METADATA_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_PORTAL_INTERNAL_WEB);
        checkRequiredProperty(ConfigurationConstants.WEB_APPLICATION_PORTAL_EXTERNAL_WEB);
    }

    @Override
    public void checkApiProperties() {
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        checkRequiredProperty(ConfigurationConstants.ENDPOINT_NOTICES_INTERNAL_API);
    }

    @Override
    public void checkOtherModuleProperties() {
        // Common
        checkOptionalDefaultCodelistTemporalGranularityUrn();
        checkOptionalDefaultCodelistGeographicalGranularityUrn();
        checkOptionalDefaultCodeLanguageUrn();
        checkOptionalDefaultCodelistLanguagesUrn();

        // Specific
        checkRequiredProperty(StatisticalResourcesConfigurationConstants.HELP_URL);

        // Confluent && Kafka
        checkKafkaProperties();

    }

    protected void checkKafkaProperties() {
        for (String kafkaProp : StreamMessagingServiceKafkaImpl.getMandatoryConfig()) {
            checkRequiredProperty(kafkaProp);
        }
    }

    @Override
    public String projectName() {
        return "statistical-resources-internal";
    }
}
