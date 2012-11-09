package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public class PublicationsAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // PUBLICATION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublication(Publication expected, Publication actual) {
        assertEquals(expected.getUuid(), actual.getUuid());

        if (expected.getVersions() != null) {
            assertNotNull(actual.getVersions());
            assertEquals(expected.getVersions().size(), actual.getVersions().size());
            for (int i = 0; i < expected.getVersions().size(); i++) {
                assertEqualsPublicationVersion(expected.getVersions().get(i), actual.getVersions().get(i), true);
            }
        } else {
            assertEquals(null, actual);
        }
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual) {
        assertEqualsPublicationVersion(expected, actual, false);
    }

    private static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual, boolean publicationChecked) {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());

        if (!publicationChecked) {
            assertEqualsPublication(expected.getPublication(), actual.getPublication());
        }
    }
}
