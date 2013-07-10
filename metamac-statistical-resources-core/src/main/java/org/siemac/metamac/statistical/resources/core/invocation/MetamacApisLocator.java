package org.siemac.metamac.statistical.resources.core.invocation;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService     configurationService;

    private SrmRestInternalFacadeV10 srmRestInternalFacadeV10 = null;

    public SrmRestInternalFacadeV10 getSrmRestInternalFacadeV10() {
        if (srmRestInternalFacadeV10 == null) {
            String baseApi = configurationService.getProperties().getProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_SRM_INTERNAL_API);
            srmRestInternalFacadeV10 = JAXRSClientFactory.create(baseApi, SrmRestInternalFacadeV10.class, null, true); // true to do thread safe
        }
        // reset thread context
        WebClient.client(srmRestInternalFacadeV10).reset();
        WebClient.client(srmRestInternalFacadeV10).accept("application/xml");

        return srmRestInternalFacadeV10;
    }
}
