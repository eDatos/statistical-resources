package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
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

    public static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected publicationVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsPublicationVersion(expected, actual, false);
        }
    }

    public static void assertEqualsPublicationVersionNotChecksPublication(PublicationVersion expected, PublicationVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected publicationVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsPublicationVersion(expected, actual, true);
        }
    }

    private static void assertEqualsPublicationVersion(PublicationVersion expected, PublicationVersion actual, boolean publicationChecked) throws MetamacException {
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());
        assertEqualsPublicationVersionMetadata(expected, actual);

        assertEqualsRelaxedElementLevelCollection(expected.getChildrenFirstLevel(), actual.getChildrenAllLevels());
        assertEqualsRelaxedElementLevelCollection(expected.getChildrenAllLevels(), actual.getChildrenAllLevels());

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

    public static void assertFilledMetadataForChaptersInAllLevels(Chapter chapter) {
        assertFilledMetadataForAllElementLevels(chapter.getElementLevel());
        assertNotNull(chapter.getNameableStatisticalResource().getUrn());
        assertNotNull(chapter.getNameableStatisticalResource().getCode());
        assertNull(chapter.getNameableStatisticalResource().getUri());
    }

    public static void assertFilledMetadataForChaptersInFirstLevel(Chapter chapter) {
        assertFilledMetadataForFirstLevelElementLevels(chapter.getElementLevel());
    }

    public static void assertFilledMetadataForChaptersInNoFirstLevel(Chapter chapter) {
        assertFilledMetadataForNoFirstLevelElementLevels(chapter.getElementLevel());
    }

    public static void assertFilledMetadataForCubesInAllLevels(Cube cube) {
        assertFilledMetadataForAllElementLevels(cube.getElementLevel());
        assertNotNull(cube.getNameableStatisticalResource().getUrn());
        assertNotNull(cube.getNameableStatisticalResource().getCode());
        assertNull(cube.getNameableStatisticalResource().getUri());
    }

    public static void assertFilledMetadataForCubesInFirstLevel(Cube cube) {
        assertFilledMetadataForFirstLevelElementLevels(cube.getElementLevel());
    }

    public static void assertFilledMetadataForCubesInNoFirstLevel(Cube cube) {
        assertFilledMetadataForNoFirstLevelElementLevels(cube.getElementLevel());
    }

    private static void assertFilledMetadataForAllElementLevels(ElementLevel elementLevel) {
        assertNotNull(elementLevel.getOrderInLevel());
        assertNotNull(elementLevel.getPublicationVersion());
    }

    private static void assertFilledMetadataForFirstLevelElementLevels(ElementLevel elementLevel) {
        assertNotNull(elementLevel.getPublicationVersionFirstLevel());
        assertNull(elementLevel.getParent());
    }

    private static void assertFilledMetadataForNoFirstLevelElementLevels(ElementLevel elementLevel) {
        assertNull(elementLevel.getPublicationVersionFirstLevel());
        assertNotNull(elementLevel.getParent());
    }

    public static void assertRelaxedEqualsChapter(Chapter expected, Chapter actual) {
        assertEqualsNameableStatisticalResource(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
        assertRelaxedEqualsElementLevel(expected.getElementLevel(), actual.getElementLevel());
    }

    public static void assertRelaxedEqualsCube(Cube expected, Cube actual) {
        assertEqualsNameableStatisticalResource(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
        assertRelaxedEqualsElementLevel(expected.getElementLevel(), actual.getElementLevel());
    }

    private static void assertRelaxedEqualsElementLevel(ElementLevel expected, ElementLevel actual) {
        assertEquals(expected.getChildren().size(), actual.getChildren().size());

        assertRelaxedEqualsObject(expected.getParent(), actual.getParent());
        assertRelaxedEqualsObject(expected.getPublicationVersion(), actual.getPublicationVersion());
        assertRelaxedEqualsObject(expected.getPublicationVersionFirstLevel(), actual.getPublicationVersionFirstLevel());
        assertEquals(expected.getOrderInLevel(), actual.getOrderInLevel());

        // It's not necessary check cube or chapter because this method is always called from an assert of one of these two elements
    }

    private static void assertEqualsRelaxedElementLevelCollection(List<ElementLevel> expected, List<ElementLevel> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (ElementLevel expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    // -----------------------------------------------------------------
    // PUBLICATION: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublication(PublicationVersion entity, RelatedResourceDto dto) throws MetamacException {
        assertNotNull(entity.getPublication().getId());
        assertEquals(entity.getPublication().getId(), dto.getId());

        assertNotNull(entity.getPublication().getUuid());
        assertEquals(entity.getPublication().getUuid(), dto.getUuid());

        assertNotNull(entity.getPublication().getVersion());
        assertEquals(entity.getPublication().getVersion(), dto.getVersion());

        assertEquals(TypeRelatedResourceEnum.PUBLICATION, dto.getType());
        assertEquals(entity.getPublication().getIdentifiableStatisticalResource().getCode(), dto.getCode());
        assertNull(dto.getCodeNested());
        assertEquals(entity.getPublication().getIdentifiableStatisticalResource().getUrn(), dto.getUrn());
        assertEqualsInternationalString(entity.getSiemacMetadataStatisticalResource().getTitle(), dto.getTitle());
    }

    // -----------------------------------------------------------------
    // PUBLICATION VERSION: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsPublicationVersion(PublicationVersion entity, PublicationVersionDto dto) throws MetamacException {
        assertEqualsPublicationVersion(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsPublicationVersion(PublicationVersionDto dto, PublicationVersion entity) throws MetamacException {
        assertEqualsPublicationVersion(dto, entity, MapperEnum.DTO2DO);
    }

    public static void assertEqualsPublicationVersionDoAndDtoCollection(Collection<PublicationVersion> expected, Collection<PublicationVersionDto> actual) throws MetamacException {
        assertEqualsPublicationVersionCollection(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsPublicationVersionDtoAndDoCollection(Collection<PublicationVersionDto> expected, Collection<PublicationVersion> actual) throws MetamacException {
        assertEqualsPublicationVersionCollection(actual, expected, MapperEnum.DTO2DO);
    }

    private static void assertEqualsPublicationVersion(PublicationVersionDto dto, PublicationVersion entity, MapperEnum mapperEnum) throws MetamacException {
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

    private static void assertEqualsPublicationVersionCollection(Collection<PublicationVersion> entities, Collection<PublicationVersionDto> dtos, MapperEnum mapperEnum) throws MetamacException {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (PublicationVersion expectedItem : entities) {
                boolean match = false;
                for (PublicationVersionDto actualItem : dtos) {
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

    public static void assertEqualsChapter(Chapter expected, ChapterDto actual) {
        assertEqualsChapter(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsChapter(ChapterDto actual, Chapter expected) {
        assertEqualsChapter(expected, actual, MapperEnum.DTO2DO);
    }

    public static void assertEqualsChapter(Chapter entity, ChapterDto dto, MapperEnum mapperEnum) {
        assertEqualsNameableStatisticalResource(entity.getNameableStatisticalResource(), dto, mapperEnum);
        assertEquals(entity.getElementLevel().getOrderInLevel(), dto.getOrderInLevel());
        assertEquals(entity.getElementLevel().getParentUrn(), dto.getParentChapterUrn());
    }

    public static void assertEqualsCube(Cube expected, CubeDto actual) {
        assertEqualsCube(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsCube(CubeDto actual, Cube expected) {
        assertEqualsCube(expected, actual, MapperEnum.DTO2DO);
    }

    public static void assertEqualsCube(Cube entity, CubeDto dto, MapperEnum mapperEnum) {
        assertEqualsNameableStatisticalResource(entity.getNameableStatisticalResource(), dto, mapperEnum);
        assertEquals(entity.getElementLevel().getOrderInLevel(), dto.getOrderInLevel());
        assertEquals(entity.getElementLevel().getParentUrn(), dto.getParentChapterUrn());
        assertEquals(entity.getDatasetUrn(), dto.getDatasetUrn());
        assertEquals(entity.getQueryUrn(), dto.getQueryUrn());
    }

    public static void assertRelaxedEqualsElementLevelCollection(Collection<ElementLevel> entities, Collection<ElementLevelDto> dtos) {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (ElementLevel expectedItem : entities) {
                boolean match = false;
                for (ElementLevelDto actualItem : dtos) {
                    try {
                        assertRelaxedEqualsElementLevel(actualItem, expectedItem);
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

    private static void assertRelaxedEqualsElementLevel(ElementLevelDto expected, ElementLevel actual) {
        assertRelaxedEqualsElementLevelCollection(actual.getChildren(), expected.getSubelements());

        assertRelaxedEqualsObject(expected.getChapter(), actual.getChapter());
        assertRelaxedEqualsObject(expected.getCube(), actual.getCube());
    }

    public static void assertEqualsPublicationStructure(PublicationVersion expected, PublicationStructureDto actual) {
        assertEquals(expected.getSiemacMetadataStatisticalResource().getUrn(), actual.getPublicationUrn());
        assertEqualsChildren(expected.getChildrenFirstLevel(), actual.getElements());
    }

    private static void assertEqualsChildren(List<ElementLevel> expected, List<ElementLevelDto> actual) {
        assertRelaxedEqualsElementLevelCollection(expected, actual);
    }
}
