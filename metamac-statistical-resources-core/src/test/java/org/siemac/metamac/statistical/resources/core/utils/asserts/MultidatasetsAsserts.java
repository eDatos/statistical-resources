package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public class MultidatasetsAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // MULTIDATASET: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsMultidataset(Multidataset expected, Multidataset actual) {
        if (expected.getVersions() != null) {
            assertNotNull(actual.getVersions());
            assertEquals(expected.getVersions().size(), actual.getVersions().size());
            assertEqualsMultidatasetVersionCollection(expected.getVersions(), actual.getVersions());
        } else {
            assertEquals(null, actual);
        }
    }

    // -----------------------------------------------------------------
    // MULTIDATASET VERSION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsMultidatasetVersion(MultidatasetVersion expected, MultidatasetVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected multidatasetVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsMultidatasetVersion(expected, actual, false);
        }
    }

    public static void assertEqualsMultidatasetVersionNotChecksMultidataset(MultidatasetVersion expected, MultidatasetVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected multidatasetVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsMultidatasetVersion(expected, actual, true);
        }
    }

    private static void assertEqualsMultidatasetVersion(MultidatasetVersion expected, MultidatasetVersion actual, boolean multidatasetChecked) throws MetamacException {
        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());
        assertEqualsMultidatasetVersionMetadata(expected, actual);

        assertEqualsRelaxedMultidatasetCubesCollection(expected.getCubes(), actual.getCubes());

        if (!multidatasetChecked) {
            assertEqualsMultidataset(expected.getMultidataset(), actual.getMultidataset());
        }
    }

    public static void assertEqualsMultidatasetVersionCollection(Collection<MultidatasetVersion> expected, Collection<MultidatasetVersion> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (MultidatasetVersion expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    private static void assertEqualsMultidatasetVersionMetadata(MultidatasetVersion expected, MultidatasetVersion actual) {
        assertEquals(expected.getFormatExtentResources(), actual.getFormatExtentResources());
        assertEqualsInternationalString(expected.getFilteringDimension(), actual.getFilteringDimension());
    }

    // -----------------------------------------------------------------
    // MULTIDATASET HIERARCHY: DTO & DO
    // -----------------------------------------------------------------

    public static void assertFilledMetadataForMultidatasetCube(MultidatasetCube cube) {
        assertNotNull(cube.getNameableStatisticalResource().getUrn());
        assertNotNull(cube.getNameableStatisticalResource().getCode());
        assertNotNull(cube.getIdentifier());
        assertNotNull(cube.getOrderInMultidataset());
        assertNotNull(cube.getMultidatasetVersion());
    }

    public static void assertRelaxedEqualsMultidatasetCube(MultidatasetCube expected, MultidatasetCube actual) {
        assertEqualsNameableStatisticalResource(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
        assertEquals(expected.getOrderInMultidataset(), actual.getOrderInMultidataset());
        assertRelaxedEqualsObject(expected.getMultidatasetVersion(), actual.getMultidatasetVersion());
        assertRelaxedEqualsObject(expected.getDataset(), actual.getDataset());
        assertRelaxedEqualsObject(expected.getQuery(), actual.getQuery());
    }

    public static void assertEqualsRelaxedMultidatasetCubesCollection(List<MultidatasetCube> expected, List<MultidatasetCube> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (MultidatasetCube expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    public static void assertEqualsVersionedMultidatasetCubeCollection(List<MultidatasetCube> expected, List<MultidatasetCube> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (MultidatasetCube expectedItem : expected) {
                boolean match = false;
                for (MultidatasetCube actualItem : actual) {
                    try {
                        assertEqualsVersionedMultidatasetCube(expectedItem, actualItem);
                        match = true;
                    } catch (AssertionError e) {
                        continue;
                    }

                }

                if (!match) {
                    fail("Found elements in expected collection, which are not contained in actual collection. Element: " + expectedItem.getId());
                }
            }
        } else {
            assertNull(actual);
        }
    }

    private static void assertEqualsVersionedMultidatasetCube(MultidatasetCube expected, MultidatasetCube actual) {
        if (expected != null && actual != null) {
            assertEqualsVersionedNameableStatisticalResourceForResourceThatChangesCode(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
            assertEquals(expected.getIdentifier(), actual.getIdentifier());
            assertEquals(expected.getDatasetUrn(), actual.getDatasetUrn());
            assertEquals(expected.getQueryUrn(), actual.getQueryUrn());
        }
    }

    // -----------------------------------------------------------------
    // MULTIDATASET: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsMultidataset(MultidatasetVersion entity, RelatedResourceDto dto) throws MetamacException {
        assertNotNull(entity.getMultidataset().getId());
        assertEquals(entity.getMultidataset().getId(), dto.getId());

        assertNotNull(entity.getMultidataset().getVersion());
        assertEquals(entity.getMultidataset().getVersion(), dto.getVersion());

        assertEquals(TypeRelatedResourceEnum.MULTIDATASET, dto.getType());
        assertEquals(entity.getMultidataset().getIdentifiableStatisticalResource().getCode(), dto.getCode());
        assertNull(dto.getCodeNested());
        assertEquals(entity.getMultidataset().getIdentifiableStatisticalResource().getUrn(), dto.getUrn());
        assertEqualsInternationalString(entity.getSiemacMetadataStatisticalResource().getTitle(), dto.getTitle());
    }

    // -----------------------------------------------------------------
    // MULTIDATASET VERSION: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsMultidatasetVersion(MultidatasetVersion entity, RelatedResourceDto dto) {
        assertNotNull(entity.getId());
        assertEquals(entity.getId(), dto.getId());

        assertNotNull(entity.getVersion());
        assertEquals(entity.getVersion(), dto.getVersion());

        assertEquals(TypeRelatedResourceEnum.MULTIDATASET_VERSION, dto.getType());
        assertEquals(entity.getSiemacMetadataStatisticalResource().getCode(), dto.getCode());
        assertNull(dto.getCodeNested());
        assertEquals(entity.getSiemacMetadataStatisticalResource().getUrn(), dto.getUrn());
        assertEqualsInternationalString(entity.getSiemacMetadataStatisticalResource().getTitle(), dto.getTitle());
    }

    public static void assertEqualsMultidatasetVersion(MultidatasetVersion entity, MultidatasetVersionDto dto) throws MetamacException {
        assertEqualsMultidatasetVersion(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsMultidatasetVersion(MultidatasetVersionDto dto, MultidatasetVersion entity) throws MetamacException {
        assertEqualsMultidatasetVersion(dto, entity, MapperEnum.DTO2DO);
    }

    public static void assertEqualsMultidatasetVersionDoAndDtoCollection(Collection<MultidatasetVersion> expected, Collection<MultidatasetVersionBaseDto> actual) throws MetamacException {
        assertEqualsMultidatasetVersionCollection(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsMultidatasetVersionDtoAndDoCollection(Collection<MultidatasetVersionBaseDto> expected, Collection<MultidatasetVersion> actual) throws MetamacException {
        assertEqualsMultidatasetVersionCollection(actual, expected, MapperEnum.DTO2DO);
    }

    private static void assertEqualsMultidatasetVersion(MultidatasetVersionDto dto, MultidatasetVersion entity, MapperEnum mapperEnum) throws MetamacException {
        assertEqualsSiemacMetadataStatisticalResource(entity.getSiemacMetadataStatisticalResource(), dto, mapperEnum);

        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            if (ProcStatusEnumUtils.isInAnyProcStatus(entity, ProcStatusEnum.PUBLISHED)) {
                assertEquals(entity.getFormatExtentResources(), dto.getFormatExtentResources());
            } else {
                assertNotNull(dto.getFormatExtentResources());
                assertNull(entity.getFormatExtentResources());
            }

            assertEquals(entity.getId(), dto.getId());

            assertNotNull(entity.getVersion());
            assertEquals(entity.getVersion(), dto.getVersion());
        }
        assertEqualsInternationalString(entity.getFilteringDimension(), dto.getFilteringDimension());
    }

    public static void assertEqualsMultidatasetVersionBase(MultidatasetVersion entity, MultidatasetVersionBaseDto dto) throws MetamacException {
        assertEqualsMultidatasetVersionBase(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsMultidatasetVersionBase(MultidatasetVersionBaseDto dto, MultidatasetVersion entity) throws MetamacException {
        assertEqualsMultidatasetVersionBase(dto, entity, MapperEnum.DTO2DO);
    }

    private static void assertEqualsMultidatasetVersionBase(MultidatasetVersionBaseDto dto, MultidatasetVersion entity, MapperEnum mapperEnum) throws MetamacException {
        assertEqualsSiemacMetadataStatisticalResourceBase(entity.getSiemacMetadataStatisticalResource(), dto, mapperEnum);

        // Multidataset attributes
        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertEquals(entity.getId(), dto.getId());

            assertNotNull(entity.getVersion());
            assertEquals(entity.getVersion(), dto.getVersion());
        }
    }

    private static void assertEqualsMultidatasetVersionCollection(Collection<MultidatasetVersion> entities, Collection<MultidatasetVersionBaseDto> dtos, MapperEnum mapperEnum)
            throws MetamacException {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (MultidatasetVersion expectedItem : entities) {
                boolean match = false;
                for (MultidatasetVersionBaseDto actualItem : dtos) {
                    try {
                        assertEqualsMultidatasetVersionBase(actualItem, expectedItem, mapperEnum);
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

    public static void assertEqualsMultidatasetCube(MultidatasetCube expected, MultidatasetCubeDto actual) {
        assertEqualsMultidatasetCube(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsMultidatasetCube(MultidatasetCubeDto actual, MultidatasetCube expected) {
        assertEqualsMultidatasetCube(expected, actual, MapperEnum.DTO2DO);
    }

    public static void assertEqualsMultidatasetCube(MultidatasetCube entity, MultidatasetCubeDto dto, MapperEnum mapperEnum) {
        assertEqualsNameableStatisticalResource(entity.getNameableStatisticalResource(), dto, mapperEnum);
        assertEquals(entity.getIdentifier(), dto.getIdentifier());
        assertEquals(entity.getOrderInMultidataset(), dto.getOrderInMultidataset());
        assertEquals(entity.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), dto.getParentMultidatasetUrn());
        assertEquals(entity.getDatasetUrn(), dto.getDatasetUrn());
        assertEquals(entity.getQueryUrn(), dto.getQueryUrn());
    }

    public static void assertRelaxedEqualsElementLevelCollection(Collection<MultidatasetCube> entities, Collection<MultidatasetCubeDto> dtos) {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (MultidatasetCube expectedItem : entities) {
                boolean match = false;
                for (MultidatasetCubeDto actualItem : dtos) {
                    try {
                        assertEqualsMultidatasetCube(actualItem, expectedItem);
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
