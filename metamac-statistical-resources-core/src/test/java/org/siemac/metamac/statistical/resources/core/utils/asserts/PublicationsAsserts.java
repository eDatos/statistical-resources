package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
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
            assertEqualsPublicationVersionCollection(expected.getVersions(), actual.getVersions());
        } else {
            assertEquals(null, actual);
        }
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual) {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected publicationVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsPublicationVersion(expected, actual, false);
        }
    }

    private static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual, boolean publicationChecked) {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());

        assertEqualsPublicationVersionMetadata(expected, actual);
        
        // TODO: HAy que añadir assertEquals de children y children firstLevel

        if (!publicationChecked) {
            assertEqualsPublication(expected.getPublication(), actual.getPublication());
        }
    }

    public static void assertEqualsPublicationVersionCollection(Collection<PublicationVersion> expected, Collection<PublicationVersion> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (PublicationVersion expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    private static void assertEqualsPublicationVersionMetadata(PublicationVersion expected, PublicationVersion actual) {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEquals(expected.getFormatExtentResources(), actual.getFormatExtentResources());
    }

    
    // -----------------------------------------------------------------
    // PUBLICATION HIERARCHY: DO & DO
    // -----------------------------------------------------------------

    public static void assertRelaxedEqualsChapter(Chapter expected, Chapter actual) {
        assertEqualsNameableStatisticalResource(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
        assertRelaxedEqualsElementLevel(expected.getElementLevel(), actual.getElementLevel());
    }

    private static void assertRelaxedEqualsElementLevel(ElementLevel expected, ElementLevel actual) {
        assertEquals(expected.getChildren().size(), actual.getChildren().size());
        
        assertRelaxedEqualsObject(expected.getParent(), actual.getParent());
        assertRelaxedEqualsObject(expected.getPublicationVersion(), actual.getPublicationVersion());
        assertRelaxedEqualsObject(expected.getPublicationVersionFirstLevel(), actual.getPublicationVersionFirstLevel());
        assertEquals(expected.getOrderInLevel(), actual.getOrderInLevel());
        assertRelaxedEqualsObject(expected.getCube(), actual.getCube());
        assertRelaxedEqualsObject(expected.getChapter(), actual.getChapter());
    }
    
    // -----------------------------------------------------------------
    // PUBLICATION VERSION: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublicationVersion(PublicationVersion entity, PublicationDto dto) {
        assertEqualsPublicationVersion(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsPublicationVersion(PublicationDto dto, PublicationVersion entity) {
        assertEqualsPublicationVersion(dto, entity, MapperEnum.DTO2DO);
    }

    public static void assertEqualsPublicationVersionDoAndDtoCollection(Collection<PublicationVersion> expected, Collection<PublicationDto> actual) {
        assertEqualsPublicationVersionCollection(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsPublicationVersionDtoAndDoCollection(Collection<PublicationDto> expected, Collection<PublicationVersion> actual) {
        assertEqualsPublicationVersionCollection(actual, expected, MapperEnum.DTO2DO);
    }

    private static void assertEqualsPublicationVersion(PublicationDto dto, PublicationVersion entity, MapperEnum mapperEnum) {
        assertEqualsSiemacMetadataStatisticalResource(entity.getSiemacMetadataStatisticalResource(), dto, mapperEnum);

        // Publication attributes
        assertEquals(entity.getFormatExtentResources(), dto.getFormatExtentResources());

        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertEquals(entity.getId(), dto.getId());

            assertNotNull(entity.getUuid());
            assertEquals(entity.getUuid(), dto.getUuid());

            assertNotNull(entity.getVersion());
            assertEquals(entity.getVersion(), dto.getVersion());
        }
    }

    private static void assertEqualsPublicationVersionCollection(Collection<PublicationVersion> entities, Collection<PublicationDto> dtos, MapperEnum mapperEnum) {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (PublicationVersion expectedItem : entities) {
                boolean match = false;
                for (PublicationDto actualItem : dtos) {
                    try {
                        assertEqualsPublicationVersion(actualItem, expectedItem, mapperEnum);
                        match = true;
                    } catch (AssertionError e) {
                        continue;
                    }
                }

                if (!match) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(dtos);
        }
    }
}
