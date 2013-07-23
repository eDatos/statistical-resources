package org.siemac.metamac.statistical.resources.core.base.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;

public class StatisticalResourcesUrnParserUtilsTest {

    @Test
    public void testIsPublicationChapterUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isPublicationChapterUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionChapterUrn("chapterCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isPublicationChapterUrn("dummy=dummy"));
    }

    @Test
    public void testIsPublicationCubeUrn() throws Exception {
        assertTrue(StatisticalResourcesUrnParserUtils.isPublicationCubeUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionCubeUrn("chapterCode")));
        assertFalse(StatisticalResourcesUrnParserUtils.isPublicationCubeUrn("dummy=dummy"));
    }
}
