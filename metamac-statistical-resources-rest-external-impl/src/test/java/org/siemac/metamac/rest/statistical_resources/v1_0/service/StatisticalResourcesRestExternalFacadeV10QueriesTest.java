package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_3_CODE;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10QueriesTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testRetrieveQueryMetadataXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_1_CODE, RestExternalConstants.FIELD_EXCLUDE_DATA, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.metadata.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveQueryDataFixedXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_1_CODE, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.fixed.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveQueryDataAutoincrementalXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_2_CODE, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.autoincremental.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveQueryDataLatestDataXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_3_CODE, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.latestData.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Ignore
    // TODO testRetrieveQueryJson
    @Test
    public void testRetrieveQueryJson() throws Exception {

    }

    @Ignore
    // TODO testRetrieveQueryErrorNotExists
    @Test
    public void testRetrieveQueryErrorNotExists() throws Exception {

    }

    @Ignore
    // TODO testRetrieveQueryErrorNotExistsXml
    @Test
    public void testRetrieveQueryErrorNotExistsXml() throws Exception {
    }

    public String getRetrieveQueryUri(String agencyID, String resourceID, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(RestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, resourceID, null, fields, langs);
    }

    public String getFindQueriesUri(String agencyID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(RestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, null, query, limit, offset, langs);
    }

}