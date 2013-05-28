package org.siemac.metamac.statistical.resources.web.server.rest;

import javax.annotation.PostConstruct;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestApiLocator {

    @Autowired
    private ConfigurationService                       configurationService;

    private StatisticalOperationsRestInternalFacadeV10 statisticalOperationsRestInternalFacadeV10 = null;
    private CommonMetadataV1_0                         commonMetadataRestExternalFacadeV10        = null;

    @PostConstruct
    public void initService() throws Exception {
        String statOperbaseApi = configurationService.getProperties().getProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
        statisticalOperationsRestInternalFacadeV10 = JAXRSClientFactory.create(statOperbaseApi, StatisticalOperationsRestInternalFacadeV10.class, null, true); // true to do thread safe
        
        String commonMetadataBaseApi = configurationService.getProperties().getProperty(ConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
        commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(commonMetadataBaseApi, CommonMetadataV1_0.class, null, true); // true to do thread safe
    }

    public StatisticalOperationsRestInternalFacadeV10 getStatisticalOperationsRestFacadeV10() {
        // Reset thread context
        WebClient.client(statisticalOperationsRestInternalFacadeV10).reset();
        WebClient.client(statisticalOperationsRestInternalFacadeV10).accept("application/xml");
        return statisticalOperationsRestInternalFacadeV10;
    }

    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");

        return commonMetadataRestExternalFacadeV10;
    }

}
