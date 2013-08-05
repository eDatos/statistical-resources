package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.DATASET_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.NOT_EXISTS;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.VERSION_1;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.junit.Test;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Datasets;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;

public class StatisticalResourcesRestExternalFacadeV10DatasetsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testFindDatasets() throws Exception {
        Datasets datasets = statisticalResourcesRestExternalFacadeClientXml.findDatasets(null, null, null, null, null);

        assertEquals(4, datasets.getDatasets().size());
        assertEquals(RestExternalConstants.KIND_DATASETS, datasets.getKind());
    }

    @Test
    public void testFindDatasetsXml() throws Exception {
        String requestUri = getFindDatasetsUri(AGENCY_1, DATASET_1_CODE, null, null, null, null);
        InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10DatasetsTest.class.getResourceAsStream("/responses/datasets/findDatasets.xml");
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveDataset() throws Exception {
        Dataset dataset = statisticalResourcesRestExternalFacadeClientXml.retrieveDataset(AGENCY_1, DATASET_1_CODE, VERSION_1, null, null, null);

        assertEquals(DATASET_1_CODE, dataset.getId());
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=agency1:dataset1(01.000)", dataset.getUrn());
        assertEquals(RestExternalConstants.KIND_DATASET, dataset.getKind());
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
        String agencyID = AGENCY_1;
        String resourceID = NOT_EXISTS;
        String version = VERSION_1;
        try {
            statisticalResourcesRestExternalFacadeClientXml.retrieveDataset(agencyID, resourceID, version, null, null, null);
        } catch (ServerWebApplicationException e) {
            assertEquals(Status.NOT_FOUND.getStatusCode(), e.getStatus());

            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalResourcesRestExternalFacadeClientXml, e);
            assertEquals(RestServiceExceptionType.DATASET_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Dataset " + resourceID + " not found in version " + version + " from Agency " + agencyID, exception.getMessage());
            assertEquals(3, exception.getParameters().getParameters().size());
            assertEquals(resourceID, exception.getParameters().getParameters().get(0));
            assertEquals(version, exception.getParameters().getParameters().get(1));
            assertEquals(agencyID, exception.getParameters().getParameters().get(2));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    public String getRetrieveDatasetUri(String agencyID, String resourceID, String version, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(RestExternalConstants.LINK_SUBPATH_DATASETS, agencyID, resourceID, version, fields, langs);
    }

    public String getFindDatasetsUri(String agencyID, String resourceID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(RestExternalConstants.LINK_SUBPATH_DATASETS, agencyID, resourceID, query, limit, offset, langs);
    }

}