package org.siemac.metamac.sdmx.data.rest.external.conf;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
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
            maintainerUrnDefault = configurationService.retrieveOrganisationUrn();
        }
        return maintainerUrnDefault;
    }

    @Override
    public String retrieveOrganisationIDDefault() throws MetamacException {
        if (organisationIdDefault == null) {
            organisationIdDefault = configurationService.retrieveOrganisation();
        }
        return organisationIdDefault;
    }

    @Override
    public String retrieveSdmxRegistryApiUrlBase() throws MetamacException {
        if (sdmxRegistryApiUrlBase == null) {
            sdmxRegistryApiUrlBase = configurationService.retrieveSdmxRegistryExternalApiUrlBase();
        }
        return sdmxRegistryApiUrlBase;
    }

    @Override
    public String retrieveSdmxSrmApiUrlBase() throws MetamacException {
        if (sdmxSrmApiUrlBase == null) {
            sdmxSrmApiUrlBase = configurationService.retrieveSdmxSrmExternalApiUrlBase();
        }
        return sdmxSrmApiUrlBase;
    }

    @Override
    public String retrieveSdmxStatisticalResourceApiUrlBase() throws MetamacException {
        if (sdmxStatisticalResourceApiUrlBase == null) {
            sdmxStatisticalResourceApiUrlBase = configurationService.retrieveSdmxStatisticalResourcesExternalApiUrlBase();
        }
        return sdmxStatisticalResourceApiUrlBase;
    }

}
