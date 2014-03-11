package org.siemac.metamac.sdmx.data.rest.external.conf;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dataConfiguration")
public class DataConfigurationImpl implements DataConfiguration {

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    private String                            maintainerUrnDefault;

    private String                            organisationIdDefault;

    private String                            sdmxRegistryApiUrlBase;

    private String                            sdmxSrmApiUrlBase;

    private String                            sdmxStatisticalResourceApiUrlBase;

    @Override
    public String retrieveMaintainerUrnDefault() throws MetamacException {
        if (maintainerUrnDefault == null) {
            maintainerUrnDefault = retrieveProperty(DataConfigurationConstants.METAMAC_ORGANISATION_URN, Boolean.TRUE);
        }
        return maintainerUrnDefault;
    }

    @Override
    public String retrieveOrganisationIDDefault() throws MetamacException {
        if (organisationIdDefault == null) {
            organisationIdDefault = retrieveProperty(DataConfigurationConstants.METAMAC_ORGANISATION, Boolean.TRUE);
        }
        return organisationIdDefault;
    }

    @Override
    public String retrieveSdmxRegistryApiUrlBase() throws MetamacException {
        if (sdmxRegistryApiUrlBase == null) {
            sdmxRegistryApiUrlBase = retrieveProperty(DataConfigurationConstants.ENDPOINT_SDMX_REGISTRY_EXTERNAL_API, Boolean.TRUE);
        }
        return sdmxRegistryApiUrlBase;
    }

    @Override
    public String retrieveSdmxSrmApiUrlBase() throws MetamacException {
        if (sdmxSrmApiUrlBase == null) {
            sdmxSrmApiUrlBase = retrieveProperty(DataConfigurationConstants.ENDPOINT_SDMX_SRM_EXTERNAL_API, Boolean.TRUE);
        }
        return sdmxSrmApiUrlBase;
    }

    @Override
    public String retrieveSdmxStatisticalResourceApiUrlBase() throws MetamacException {
        if (sdmxStatisticalResourceApiUrlBase == null) {
            sdmxStatisticalResourceApiUrlBase = retrieveProperty(DataConfigurationConstants.ENDPOINT_SDMX_STATISTICAL_RESOURCES_EXTERNAL_API, Boolean.TRUE);
        }
        return sdmxStatisticalResourceApiUrlBase;
    }

    private String retrieveProperty(String propertyName, Boolean required) throws MetamacException {
        String propertyValue = configurationService.getProperty(propertyName);
        if (required && StringUtils.isEmpty(propertyValue)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_NOT_FOUND).withMessageParameters(propertyName).build();
        }
        return propertyValue;
    }

    private List<Object> retrievePropertyList(String propertyName, Boolean required) throws MetamacException {
        List<Object> propertyValue = configurationService.getConfig().getList(propertyName);
        if (required && CollectionUtils.isEmpty(propertyValue)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_NOT_FOUND).withMessageParameters(propertyName).build();
        }
        return propertyValue;
    }

}
