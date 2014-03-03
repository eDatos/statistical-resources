package org.siemac.metamac.statistical_resources.rest.external.invocation;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("metamacApisLocatorRestExternal")
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService     configurationService;

    // FIXME SRM: cambiar a API externa (METAMAC-1909)
    private SrmRestInternalFacadeV10 srmRestExternalFacadeV10            = null;

    private CommonMetadataV1_0       commonMetadataRestExternalFacadeV10 = null;

    @PostConstruct
    public void initService() throws Exception {

        String srmExternalApi = configurationService.retrieveSrmExternalApiUrlBase();
        srmRestExternalFacadeV10 = JAXRSClientFactory.create(srmExternalApi, SrmRestInternalFacadeV10.class, null, true); // true to do thread safe

        String commonMetadataExternalApi = configurationService.retrieveCommonMetadataExternalApiUrlBase();
        commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(commonMetadataExternalApi, CommonMetadataV1_0.class, null, true); // true to do thread safe
    }

    public SrmRestInternalFacadeV10 getSrmRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(srmRestExternalFacadeV10).reset();
        WebClient.client(srmRestExternalFacadeV10).accept("application/xml");

        return srmRestExternalFacadeV10;
    }

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }
}
