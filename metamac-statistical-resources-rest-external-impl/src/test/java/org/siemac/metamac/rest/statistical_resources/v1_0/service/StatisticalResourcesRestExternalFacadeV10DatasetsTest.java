package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10DatasetsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testFindDatasetsXml() throws Exception {
        // TODO testFindDatasetsXml
    }

    @Test
    public void testRetrieveDataset() throws Exception {
        // TODO testRetrieveDataset
    }

    @Test
    public void testRetrieveDatasetXml() throws Exception {
        String requestBase = getRetrieveDatasetUri(AGENCY_1, DATASET_1_CODE, VERSION_1, null, null);
        String[] requestUris = new String[]{requestBase, requestBase + ".xml", requestBase + "?_type=xml"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10DatasetsTest.class.getResourceAsStream("/responses/datasets/retrieveDataset.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveDatasetJson() throws Exception {
        // TODO testRetrieveDatasetJson
    }

    @Test
    public void testRetrieveDatasetErrorNotExists() throws Exception {
        // TODO testRetrieveDatasetErrorNotExists
    }

    @Test
    public void testRetrieveDatasetErrorNotExistsXml() throws Exception {
        // TODO testRetrieveDatasetErrorNotExistsXml
    }

    public String getRetrieveDatasetUri(String agencyID, String resourceID, String version, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(RestExternalConstants.LINK_SUBPATH_DATASETS, agencyID, resourceID, version, fields, langs);
    }

    public String getFindDatasetsUri(String agencyID, String resourceID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(RestExternalConstants.LINK_SUBPATH_DATASETS, agencyID, resourceID, query, limit, offset, langs);
    }

}