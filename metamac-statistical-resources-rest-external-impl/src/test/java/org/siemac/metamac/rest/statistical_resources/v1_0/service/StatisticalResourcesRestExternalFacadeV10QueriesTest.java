package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.NOT_EXISTS;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_3_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_4_CODE;

import java.io.InputStream;
import java.util.Arrays;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.rest.common.test.utils.MetamacRestAsserts;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Queries;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;

public class StatisticalResourcesRestExternalFacadeV10QueriesTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testFindQueries() throws Exception {
        Queries queries = statisticalResourcesRestExternalFacadeClientXml.findQueries(null, null, null, null, null);

        assertEquals(4, queries.getQueries().size());
        assertEquals(StatisticalResourcesRestExternalConstants.KIND_QUERIES, queries.getKind());
    }

    @Test
    public void testFindQueriesXml() throws Exception {
        String requestUri = getFindQueriesUri(AGENCY_1, null, null, null, "es");
        InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/findQueries.xml");
        testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
    }

    @Test
    public void testRetrieveQuery() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_1_CODE, defaultLanguages, null);

        assertEquals(QUERY_1_CODE, query.getId());
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Query=agency1:query1", query.getUrn());
        assertEquals(StatisticalResourcesRestExternalConstants.KIND_QUERY, query.getKind());
        assertEquals("http://data.istac.es/apis/statistical-resources/v1.0/queries/agency1/query1", query.getSelfLink().getHref());
        MetamacRestAsserts.assertEqualsInternationalString("es", "title-query1 en Espanol", null, null, query.getName());
        assertEquals("http://data.istac.es/apis/statistical-resources/v1.0/datasets/agency1/dataset01/01.000", query.getMetadata().getRelatedDataset().getSelfLink().getHref());
    }

    @Test
    public void testRetrieveQueryWithDatasetGlobal() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_2_CODE, defaultLanguages, null);

        assertEquals(QUERY_2_CODE, query.getId());
        assertEquals("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Query=agency1:query2", query.getUrn());
        assertEquals("http://data.istac.es/apis/statistical-resources/v1.0/datasets/agency1/dataset01/~latest", query.getMetadata().getRelatedDataset().getSelfLink().getHref());
    }

    @Test
    public void testRetrieveQueryAnotherLanguage() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_1_CODE, Arrays.asList("en"), null);

        MetamacRestAsserts.assertEqualsInternationalString("es", "title-query1 en Espanol", "en", "title-query1 in English", query.getName());
    }

    @Test
    public void testRetrieveQueryXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_1_CODE, null, null);
        String[] requestUris = new String[]{requestBase + "?lang=es", requestBase + ".xml?lang=es", requestBase + "?_type=xml&lang=es"};
        for (int i = 0; i < requestUris.length; i++) {
            String requestUri = requestUris[i];
            InputStream responseExpected = StatisticalResourcesRestExternalFacadeV10QueriesTest.class.getResourceAsStream("/responses/queries/retrieveQuery.id1.xml");
            testRequestWithoutJaxbTransformation(requestUri, APPLICATION_XML, Status.OK, responseExpected);
        }
    }

    @Test
    public void testRetrieveQueryJson() throws Exception {
        // TODO testRetrieveQueryJson. Crear el fichero .json con el resultado de la llamada a la api (idem xml) (METAMAC-1570)
    }

    @Test
    public void testRetrieveQueryFixed() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_1_CODE, null, null);

        // Metadata
        assertEquals(4, query.getMetadata().getDimensions().getDimensions().size());
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(0);
            assertEquals("GEO_DIM", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof EnumeratedDimensionValues);
            {
                EnumeratedDimensionValues dimensionValues = (EnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(8, dimensionValues.getValues().size());
                int i = 0;
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("santa-cruz-tenerife", dimensionValue.getId());
                    assertEquals(null, dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("tenerife", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).santa-cruz-tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("la-laguna", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("santa-cruz", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("la-palma", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).santa-cruz-tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("los-llanos-de-aridane", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).la-palma", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("las-palmas-gran-canaria", dimensionValue.getId());
                    assertEquals(null, dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("fuerteventura", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).las-palmas-gran-canaria", dimensionValue.getVisualisationParent());
                }
                assertEquals(dimensionValues.getValues().size(), i);
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(1);
            assertEquals("TIME_PERIOD", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof NonEnumeratedDimensionValues);
            {
                NonEnumeratedDimensionValues dimensionValues = (NonEnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(2, dimensionValues.getValues().size());
                assertEquals("2011", dimensionValues.getValues().get(0).getId());
                assertEquals("2013", dimensionValues.getValues().get(1).getId());
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(2);
            assertEquals("measure01", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof EnumeratedDimensionValues);
            {
                EnumeratedDimensionValues dimensionValues = (EnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(3, dimensionValues.getValues().size());
                assertEquals("measure01-conceptScheme01-concept01", dimensionValues.getValues().get(0).getId());
                assertEquals("measure01-conceptScheme01-concept02", dimensionValues.getValues().get(1).getId());
                assertEquals("measure01-conceptScheme01-concept05", dimensionValues.getValues().get(2).getId());
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(3);
            assertEquals("dim01", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof EnumeratedDimensionValues);
            {
                EnumeratedDimensionValues dimensionValues = (EnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(1, dimensionValues.getValues().size());
                assertEquals("dim01-codelist01-code01", dimensionValues.getValues().get(0).getId());
            }
        }

        // Data
        assertEquals(48, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), StatisticalResourcesRestExternalConstants.DATA_SEPARATOR).length);
    }

    @Test
    public void testRetrieveQueryFixedChangingParentVisualisations() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_4_CODE, null, null);

        // Metadata
        assertEquals(4, query.getMetadata().getDimensions().getDimensions().size());
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(0);
            assertEquals("GEO_DIM", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof EnumeratedDimensionValues);
            {
                EnumeratedDimensionValues dimensionValues = (EnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(7, dimensionValues.getValues().size());
                int i = 0;
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("santa-cruz-tenerife", dimensionValue.getId());
                    assertEquals(null, dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("tenerife", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).santa-cruz-tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("la-laguna", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("santa-cruz", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("los-llanos-de-aridane", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).santa-cruz-tenerife", dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("las-palmas-gran-canaria", dimensionValue.getId());
                    assertEquals(null, dimensionValue.getVisualisationParent());
                }
                {
                    EnumeratedDimensionValue dimensionValue = dimensionValues.getValues().get(i++);
                    assertEquals("fuerteventura", dimensionValue.getId());
                    assertEquals("urn:sdmx:org.sdmx.infomodel.codelist.Code=agency01:GEO_DIM-codelist01(01.000).las-palmas-gran-canaria", dimensionValue.getVisualisationParent());
                }
                assertEquals(dimensionValues.getValues().size(), i);
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(1);
            assertEquals("TIME_PERIOD", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(2);
            assertEquals("measure01", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(3);
            assertEquals("dim01", dimension.getId());
        }

        // Data
        assertEquals(42, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), StatisticalResourcesRestExternalConstants.DATA_SEPARATOR).length);
    }

    @Test
    public void testRetrieveQueryAutoincremental() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_2_CODE, null, null);

        // Metadata
        assertEquals(4, query.getMetadata().getDimensions().getDimensions().size());
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(0);
            assertEquals("GEO_DIM", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(1);
            assertEquals("TIME_PERIOD", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof NonEnumeratedDimensionValues);
            {
                NonEnumeratedDimensionValues dimensionValues = (NonEnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(3, dimensionValues.getValues().size());
                assertEquals("2011", dimensionValues.getValues().get(0).getId());
                assertEquals("2013", dimensionValues.getValues().get(1).getId());
                assertEquals("2014", dimensionValues.getValues().get(2).getId());
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(2);
            assertEquals("measure01", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(3);
            assertEquals("dim01", dimension.getId());
        }

        // Data
        assertEquals(18, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), StatisticalResourcesRestExternalConstants.DATA_SEPARATOR).length);
    }

    @Test
    public void testRetrieveQueryLatestData() throws Exception {
        Query query = statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(AGENCY_1, QUERY_3_CODE, null, null);

        // Metadata
        assertEquals(4, query.getMetadata().getDimensions().getDimensions().size());
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(0);
            assertEquals("GEO_DIM", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(1);
            assertEquals("TIME_PERIOD", dimension.getId());
            assertTrue(dimension.getDimensionValues() instanceof NonEnumeratedDimensionValues);
            {
                NonEnumeratedDimensionValues dimensionValues = (NonEnumeratedDimensionValues) dimension.getDimensionValues();
                assertEquals(2, dimensionValues.getValues().size());
                assertEquals("2013", dimensionValues.getValues().get(0).getId());
                assertEquals("2014", dimensionValues.getValues().get(1).getId());
            }
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(2);
            assertEquals("measure01", dimension.getId());
        }
        {
            Dimension dimension = query.getMetadata().getDimensions().getDimensions().get(3);
            assertEquals("dim01", dimension.getId());
        }

        // Data
        assertEquals(12, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), StatisticalResourcesRestExternalConstants.DATA_SEPARATOR).length);
    }

    @Test
    public void testRetrieveQueryErrorNotExists() throws Exception {
        String agencyID = AGENCY_1;
        String resourceID = NOT_EXISTS;
        try {
            statisticalResourcesRestExternalFacadeClientXml.retrieveQuery(agencyID, resourceID, null, null);
        } catch (ServerWebApplicationException e) {
            assertEquals(Status.NOT_FOUND.getStatusCode(), e.getStatus());

            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = extractErrorFromException(statisticalResourcesRestExternalFacadeClientXml, e);
            assertEquals(RestServiceExceptionType.QUERY_NOT_FOUND.getCode(), exception.getCode());
            assertEquals("Query " + resourceID + " not found from Agency " + agencyID, exception.getMessage());
            assertEquals(2, exception.getParameters().getParameters().size());
            assertEquals(resourceID, exception.getParameters().getParameters().get(0));
            assertEquals(agencyID, exception.getParameters().getParameters().get(1));
            assertNull(exception.getErrors());
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    public String getRetrieveQueryUri(String agencyID, String resourceID, String fields, String langs) throws Exception {
        return getRetrieveResourceUri(StatisticalResourcesRestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, resourceID, null, fields, langs);
    }

    public String getFindQueriesUri(String agencyID, String query, String limit, String offset, String langs) throws Exception {
        return getFindResourcesUri(StatisticalResourcesRestExternalConstants.LINK_SUBPATH_QUERIES, agencyID, null, query, limit, offset, langs);
    }

}