package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.siemac.metamac.rest.statistical_resources.v1_0.dataset.utils.DatasetsRestDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.service.StatisticalResourcesV1_0;
import org.springframework.context.ApplicationContext;

public abstract class StatisticalResourcesRestExternalFacadeV10BaseTest extends MetamacRestBaseTest {

    private static String                                 jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/statistical-resources";
    protected String                                      baseApi            = jaxrsServerAddress + "/v1.0";
    protected static ApplicationContext                   applicationContext = null;
    private static StatisticalResourcesV1_0               statisticalResourcesRestExternalFacadeClientXml;
    private static String                                 apiEndpointv10;

    protected static StatisticalResourcesPersistedDoMocks coreDoMocks;
    protected static DatasetsRestDoMocks                  datasetDoMocks;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();
        coreDoMocks = ApplicationContextProvider.getApplicationContext().getBean(StatisticalResourcesPersistedDoMocks.class);
        datasetDoMocks = new DatasetsRestDoMocks(coreDoMocks);

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            statisticalResourcesRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, StatisticalResourcesV1_0.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        apiEndpointv10 = configurationService.getProperty(ConfigurationConstants.ENDPOINT_STATISTICAL_RESOURCES_EXTERNAL_API) + "/v1.0";

        resetMocks();
    }

    @Test
    public void testErrorWithoutMatchError404() throws Exception {
        String requestUri = baseApi + "/nomatch";
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.NOT_FOUND, new ByteArrayInputStream(new byte[0]));
    }

    protected StatisticalResourcesV1_0 getStatisticalResourcesRestExternalFacadeClientXml() {
        WebClient.client(statisticalResourcesRestExternalFacadeClientXml).reset();
        WebClient.client(statisticalResourcesRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return statisticalResourcesRestExternalFacadeClientXml;
    }

    protected String getResourceUri(String resourcePath, String agencyID, String resourceID, String version) {
        StringBuilder uri = new StringBuilder();
        uri.append(baseApi + "/" + resourcePath);
        if (agencyID != null) {
            uri.append("/" + agencyID);
            if (resourceID != null) {
                uri.append("/" + resourceID);
                if (version != null) {
                    uri.append("/" + version);
                }
            }
        }
        return uri.toString();
    }

    protected String getApiEndpoint() {
        return apiEndpointv10;
    }

    protected abstract void resetMocks() throws Exception;
}