package org.siemac.metamac.sdmx.data.rest.external.conf;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("srmConfiguration")
public class DataConfigurationImpl implements DataConfiguration {

    @Autowired
    private ConfigurationService configurationService;

    private String               maintainerUrnDefault;

    private String               organisationIdDefault;

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
