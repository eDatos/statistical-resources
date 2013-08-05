package org.siemac.metamac.rest.statistical_resources.v1_0.service;

import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.AGENCY_1;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_1_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_2_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_3_CODE;
import static org.siemac.metamac.rest.statistical_resources.constants.RestTestConstants.QUERY_4_CODE;

import java.io.InputStream;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Query;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;

public class StatisticalResourcesRestExternalFacadeV10QueriesTest extends StatisticalResourcesRestExternalFacadeV10BaseTest {

    @Test
    public void testRetrieveQueryXml() throws Exception {
        String requestBase = getRetrieveQueryUri(AGENCY_1, QUERY_1_CODE, null, null);
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
        assertEquals(48, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), RestExternalConstants.DATA_OBSERVATION_SEPARATOR).length);
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
        assertEquals(42, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), RestExternalConstants.DATA_OBSERVATION_SEPARATOR).length);
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
        assertEquals(18, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), RestExternalConstants.DATA_OBSERVATION_SEPARATOR).length);
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
        assertEquals(12, StringUtils.splitByWholeSeparatorPreserveAllTokens(query.getData().getObservations(), RestExternalConstants.DATA_OBSERVATION_SEPARATOR).length);
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