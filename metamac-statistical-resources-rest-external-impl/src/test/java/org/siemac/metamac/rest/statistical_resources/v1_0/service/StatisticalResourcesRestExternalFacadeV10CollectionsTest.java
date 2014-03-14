package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.COLLECTION_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.NOT_EXISTS;

import java.io.InputStream;
import java.util.Arrays;

import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.junit.Test;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collection;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collections;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;

public class StatisticalResourcesRestExternalFacadeV10CollectionsTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testFindCollections() throws Exception {
        Collections collections = statisticalResourcesRestExternalFacadeClientXml.findCollections(null, null, null, null, null);

        assertEquals(4, collections.getCollections().size());
        assertEquals(StatisticalResourcesRestExternalConstants.KIND_COLLECTIONS, collections.getKind());
    }

    @Test
    public void testFindCollectionsXml() throws Exception {
        String requestUri = getFindCollectionsUri(AGENCY_1, null, null, null, "es");
        InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10CollectionsTest.class.getResourceAsStream("/responses/collections/findCollections.xml");
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveCollection() throws Exception {
        Collection collection = statisticalResourcesRestExternalFacadeClientXml.retrieveCollection(AGENCY_1, COLLECTION_1_CODE, defaultLanguages, null);

        assertEquals(COLLECTION_1_CODE, collection.getId());
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Collection=agency1:collection1", collection.getUrn());
        assertEquals(StatisticalResourcesRestExternalConstants.KIND_COLLECTION, collection.getKind());
        assertEquals("http://data.istac.es/apis/statistical-resources/v1.0/collections/agency1/collection1", collection.getSelfLink().getHref());

        MetamacRestAsserts.assertEqualsInternationalString("es", "title-collection1 en Espanol", null, null, collection.getName());
    }

    @Test
    public void testRetrieveCollectionAnotherLanguage() throws Exception {
        Collection collection = statisticalResourcesRestExternalFacadeClientXml.retrieveCollection(AGENCY_1, COLLECTION_1_CODE, Arrays.asList("en"), null);

        MetamacRestAsserts.assertEqualsInternationalString("es", "title-collection1 en Espanol", "en", "title-collection1 in English", collection.getName());
    }

    @Test
    public void testRetrieveCollectionXml() throws Exception {
        String requestBase = getRetrieveCollectionUri(AGENCY_1, COLLECTION_1_CODE, null, null);
        String[] requestUris = new String[]{requestBase + "?lang=es", requestBase + ".xml?lang=es", requestBase + "?_type=xml&lang=es"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10CollectionsTest.class.getResourceAsStream("/responses/collections/retrieveCollection.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }
    @Test
    public void testRetrieveCollectionJson() throws Exception {
        String requestBase = getRetrieveCollectionUri(AGENCY_1, COLLECTION_1_CODE, null, null);
        String[] requestUris = new String[]{requestBase + "?lang=es", requestBase + ".json?lang=es", requestBase + "?_type=json&lang=es"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10CollectionsTest.class.getResourceAsStream("/responses/collections/retrieveCollection.id1.json");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_JSON, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveCollectionErrorNotExists() throws Exception {
        String agencyID = AGENCY_1;
        String resourceID = NOT_EXISTS;
        try {
            statisticalResourcesRestExternalFacadeClientXml.retrieveCollection(agencyID, resourceID, null, null);
        } catch (ServerWebApplicationException e) {
            assertEquals(Status.NOT_FOUND.getStatusCode(), e.getStatus());

            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalResourcesRestExternalFacadeClientXml, e);
            assertEquals(RestServiceExceptionType.COLLECTION_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Collection " + resourceID + " not found from Agency " + agencyID, exception.getMessage());
            assertEquals(2, exception.getParameters().getParameters().size());
            assertEquals(resourceID, exception.getParameters().getParameters().get(0));
            assertEquals(agencyID, exception.getParameters().getParameters().get(1));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    public String getRetrieveCollectionUri(String agencyID, String resourceID, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(StatisticalResourcesRestExternalConstants.LINK_SUBPATH_COLLECTIONS, agencyID, resourceID, null, fields, langs);
    }

    public String getFindCollectionsUri(String agencyID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(StatisticalResourcesRestExternalConstants.LINK_SUBPATH_COLLECTIONS, agencyID, null, query, limit, offset, langs);
    }

}