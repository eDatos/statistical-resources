package org.siemac.metamac.statistical.resources.core.base.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;

public class StatisticalResourcesUrnParserUtilsTest {

    //
    // PUBLICATIONS
    //

    @Test
    public void testIsPublicationUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isPublicationUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionUrn(new String[]{"maintainerAgencyId"}, "publicationCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isPublicationUrn("dummy=dummy"));
    }

    @Test
    public void testIsPublicationChapterUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isPublicationChapterUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionChapterUrn("chapterCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isPublicationChapterUrn("dummy=dummy"));
    }

    @Test
    public void testIsPublicationCubeUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isPublicationCubeUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionCubeUrn("cubeCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isPublicationCubeUrn("dummy=dummy"));
    }

    @Test
    public void testGetPublicationVersionCodeFromUrnWithoutPrefix() throws Exception {
        String urnWithoutPrefix = "ISTAC:E30308A_000002(001.000)";
        assertEquals("E30308A_000002", StatisticalResourcesUrnParserUtils.getPublicationVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix));
    }

    //
    // DATASET
    //

    @Test
    public void testIsDatasetUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isDatasetUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetUrn(new String[]{"maintainerAgencyId"}, "datasetCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isDatasetUrn("dummy=dummy"));
    }

    @Test
    public void testGetDatasetVersionCodeFromUrnWithoutPrefix() throws Exception {
        String urnWithoutPrefix = "ISTAC:E30308A_000006(001.000)";
        assertEquals("E30308A_000006", StatisticalResourcesUrnParserUtils.getDatasetVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix));
    }

    //
    // QUERIES
    //

    @Test
    public void testIsQueryUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isQueryUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(new String[]{"maintainerAgencyId"}, "datasetCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isQueryUrn("dummy=dummy"));
    }

    @Test
    public void testGetQueryVersionCodeFromUrnWithoutPrefix() throws Exception {
        String urnWithoutPrefix = "ISTAC:query00001(001.000)";
        assertEquals("query00001", StatisticalResourcesUrnParserUtils.getQueryVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix));
    }
}
