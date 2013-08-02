package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnUtils;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10QueriesTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    private QueryVersionRepository queryVersionRepository;

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

    private void mockRetrieveQueryLastPublishedVersion() throws MetamacException {
        when(queryVersionRepository.retrieveLastVersion(any(String.class))).thenAnswer(new Answer<QueryVersion>() { // TODO retrieveLastPublishedVersion

                    @Override
                    public QueryVersion answer(InvocationOnMock invocation) throws Throwable {
                        String queryUrn = (String) invocation.getArguments()[0];
                        String[] queryUrnSplited = StatisticalResourcesUrnUtils.splitUrnQueryGlobal(queryUrn);
                        return restDoMocks.mockQueryVersion(queryUrnSplited[0], queryUrnSplited[1], VERSION_1);
                    };
                });
    }

    @Override
    protected void resetMocks() throws Exception {
        queryVersionRepository = applicationContext.getBean(QueryVersionRepository.class);
        reset(queryVersionRepository);

        mockRetrieveQueryLastPublishedVersion();
    }

    public String getRetrieveQueryUri(String agencyID, String resourceID, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(RestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, resourceID, null, fields, langs);
    }

    public String getFindQueriesUri(String agencyID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(RestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, null, query, limit, offset, langs);
    }

}