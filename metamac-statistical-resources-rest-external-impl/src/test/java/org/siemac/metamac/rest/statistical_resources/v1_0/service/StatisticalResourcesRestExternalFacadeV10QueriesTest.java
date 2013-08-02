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
import org.siemac.metamac.rest.constants.RestConstants;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnUtils;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10QueriesTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    private QueryVersionRepository queryVersionRepository;

    @Test
    public void testRetrieveQueryXml() throws Exception {
        String requestBase = getQueryUri(AGENCY_1, QUERY_1_CODE, null, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.id1.xml");
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

    public String getQueryUri(String agencyID, String resourceID, String query, String limit, String offset) throws Exception {
        String uri = getResourceUri(RestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, resourceID, null);
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_QUERY, RestUtils.encodeParameter(query));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_LIMIT, RestUtils.encodeParameter(limit));
        uri = RestUtils.createLinkWithQueryParam(uri, RestConstants.PARAMETER_OFFSET, RestUtils.encodeParameter(offset));
        return uri.toString();
    }

}