package org.siemac.metamac.statistical.resources.core.invocation;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetamacApisLocator {

    @Autowired
    private ConfigurationService                       configurationService;

    private SrmRestInternalFacadeV10                   srmRestInternalFacadeV10                   = null;
    private StatisticalOperationsRestInternalFacadeV10 statisticalOperationsRestInternalFacadeV10 = null;
    private CommonMetadataV1_0                         commonMetadataRestExternalFacadeV10        = null;

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

    public StatisticalOperationsRestInternalFacadeV10 getStatisticalOperationsRestInternalFacadeV10() {
        if (statisticalOperationsRestInternalFacadeV10 == null) {
            String baseApi = configurationService.getProperties().getProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_STATISTICAL_OPERATIONS_INTERNAL_API);
            statisticalOperationsRestInternalFacadeV10 = JAXRSClientFactory.create(baseApi, StatisticalOperationsRestInternalFacadeV10.class, null, true); // true to do thread safe
        }
        // reset thread context
        WebClient.client(statisticalOperationsRestInternalFacadeV10).reset();
        WebClient.client(statisticalOperationsRestInternalFacadeV10).accept("application/xml");
        
        return statisticalOperationsRestInternalFacadeV10;
    }
    
    public CommonMetadataV1_0 getCommonMetadataRestExternalFacadeV10() {
        if (commonMetadataRestExternalFacadeV10 == null) {
            String baseApi = configurationService.getProperties().getProperty(StatisticalResourcesConfigurationConstants.ENDPOINT_COMMON_METADATA_EXTERNAL_API);
            commonMetadataRestExternalFacadeV10 = JAXRSClientFactory.create(baseApi, CommonMetadataV1_0.class, null, true); // true to do thread safe
        }
        // reset thread context
        WebClient.client(commonMetadataRestExternalFacadeV10).reset();
        WebClient.client(commonMetadataRestExternalFacadeV10).accept("application/xml");
        
        return commonMetadataRestExternalFacadeV10;
    }
}
