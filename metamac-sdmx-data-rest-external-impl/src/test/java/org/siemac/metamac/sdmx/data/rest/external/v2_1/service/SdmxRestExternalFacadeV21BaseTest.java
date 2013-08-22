package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.MetamacRestBaseTest;
import org.siemac.metamac.rest.common.test.ServerResource;
import org.springframework.context.ApplicationContext;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

public abstract class SdmxRestExternalFacadeV21BaseTest extends MetamacRestBaseTest {

    private static String                          jaxrsServerAddress = "http://localhost:" + ServerResource.PORT + "/apis/sdmx/data-resources";
    protected String                               baseApi            = jaxrsServerAddress + "/v2.1";
    protected static ApplicationContext            applicationContext = null;
    protected static SdmxDataRestExternalFacadeV21 sdmxDataRestExternalFacadeClientXml;

    protected DatasetRepositoriesServiceFacade     datasetRepositoriesServiceFacade;

    @SuppressWarnings({"unchecked", "rawtypes"})
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

        // Start server
        assertTrue("server did not launch correctly", launchServer(ServerResource.class, true));

        // Get application context from Jetty
        applicationContext = ApplicationContextProvider.getApplicationContext();

        // Rest clients
        // xml
        {
            List providers = new ArrayList();
            providers.add(applicationContext.getBean("jaxbProvider", JAXBElementProvider.class));
            sdmxDataRestExternalFacadeClientXml = JAXRSClientFactory.create(jaxrsServerAddress, SdmxDataRestExternalFacadeV21.class, providers, Boolean.TRUE);
        }
    }

    @Before
    public void setUp() throws Exception {
        ConfigurationService configurationService = applicationContext.getBean(ConfigurationService.class);
        resetMocks();
    }

    @Test
    public void testErrorWithoutMatchError404() throws Exception {
        String requestUri = baseApi + "/nomatch";

        WebClient webClient = WebClient.create(requestUri).accept(APPLICATION_XML);
        Response response = webClient.get();

        assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
        InputStream responseActual = (InputStream) response.getEntity();
        assertTrue(StringUtils.isBlank(IOUtils.toString(responseActual)));
    }

    protected SdmxDataRestExternalFacadeV21 getSdmxDataRestExternalFacadeClientXml() {
        WebClient.client(sdmxDataRestExternalFacadeClientXml).reset();
        WebClient.client(sdmxDataRestExternalFacadeClientXml).accept(APPLICATION_XML);
        return sdmxDataRestExternalFacadeClientXml;
    }

    protected abstract void resetMocks() throws Exception;
}