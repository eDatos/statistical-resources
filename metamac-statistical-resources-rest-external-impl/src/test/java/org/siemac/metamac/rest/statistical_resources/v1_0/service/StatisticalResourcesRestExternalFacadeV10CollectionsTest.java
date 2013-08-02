package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10CollectionsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testRetrieveCollectionXml() throws Exception {
        String requestBase = getRetrieveCollectionUri(AGENCY_1, COLLECTION_1_CODE, VERSION_1, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10CollectionsTest.class.getResourceAsStream("/responses/collections/retrieveCollection.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Ignore
    // TODO testRetrievePublicationJson
    @Test
    public void testRetrieveCollectionJson() throws Exception {

    }

    @Ignore
    // TODO testRetrieveCollectionErrorNotExists
    @Test
    public void testRetrieveCollectionErrorNotExists() throws Exception {

    }

    @Ignore
    // TODO testRetrieveCollectionErrorNotExistsXml
    @Test
    public void testRetrieveCollectionErrorNotExistsXml() throws Exception {
    }

    public String getRetrieveCollectionUri(String agencyID, String resourceID, String version, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(RestExternalConstants.LINK_SUBPATH_COLLECTIONS, agencyID, resourceID, version, fields, langs);
    }

    public String getFindCollectionsUri(String agencyID, String resourceID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(RestExternalConstants.LINK_SUBPATH_COLLECTIONS, agencyID, resourceID, query, limit, offset, langs);
    }

}