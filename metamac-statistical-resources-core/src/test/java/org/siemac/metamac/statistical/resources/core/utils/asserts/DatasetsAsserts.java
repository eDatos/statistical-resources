package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public class DatasetsAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // DATASET: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsDataset(Dataset expected, Dataset actual) {
        assertEquals(expected.getUuid(), actual.getUuid());

        if (expected.getVersions() != null) {
            assertNotNull(actual.getVersions());
            assertEquals(expected.getVersions().size(), actual.getVersions().size());
            for (int i = 0; i < expected.getVersions().size(); i++) {
                assertEqualsDatasetVersion(expected.getVersions().get(i), actual.getVersions().get(i), true);
            }
        } else {
            assertEquals(null, actual);
        }
    }

    // -----------------------------------------------------------------
    // DATASET VERSION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsDatasetVersion(DatasetVersion expected, DatasetVersion actual) {
        assertEqualsDatasetVersion(expected, actual, false);
    }

    private static void assertEqualsDatasetVersion(DatasetVersion expected, DatasetVersion actual, boolean datasetChecked) {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());

        if (!datasetChecked) {
            assertEqualsDataset(expected.getDataset(), actual.getDataset());
        }
    }
    
    public static void assertEqualsDatasetVersionCollection(Collection<DatasetVersion> expected, Collection<DatasetVersion> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (DatasetVersion expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    // -----------------------------------------------------------------
    // DATASOURCE: DO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsDatasource(Datasource expected, Datasource actual) {
        assertEqualsIdentifiableStatisticalResource(expected.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());
        assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
    }
    
    public static void assertEqualsDatasourceCollection(Collection<Datasource> expected, Collection<Datasource> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (Datasource expectedItem : expected) {
                if (!actual.contains(expectedItem)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    
    // -----------------------------------------------------------------
    // DATASOURCE: DTO & DO
    // -----------------------------------------------------------------
    
    public static void assertEqualsDatasource(Datasource entity, DatasourceDto dto) {
        assertEqualsDatasource(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsDatasource(DatasourceDto dto, Datasource entity) {
        assertEqualsDatasource(dto, entity, MapperEnum.DTO2DO);
    }

    public static void assertEqualsDatasourceDoAndDtoCollection(List<Datasource> expected, List<DatasourceDto> actual) {
        assertEqualsDatasourceCollection(actual, expected, MapperEnum.DO2DTO);
    }

    public static void assertEqualsDatasourceDtoAndDoCollection(List<DatasourceDto> expected, List<Datasource> actual) {
        assertEqualsDatasourceCollection(expected, actual, MapperEnum.DTO2DO);
    }

    private static void assertEqualsDatasource(DatasourceDto dto, Datasource entity, MapperEnum mapperEnum) {
        assertEqualsIdentifiableStatisticalResource(entity.getIdentifiableStatisticalResource(), dto, mapperEnum);
        
        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertEquals(entity.getId(), dto.getId());
            
            assertNotNull(entity.getUuid());
            assertEquals(entity.getUuid(), dto.getUuid());
            
            assertNotNull(entity.getVersion());
            assertEquals(entity.getVersion(), dto.getVersion());
            // TODO: Comprobar dto.versionOptimisticLocking = entity.version
            
            assertEquals(entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), dto.getDatasetVersionUrn());
        }
    }

    private static void assertEqualsDatasourceCollection(List<DatasourceDto> dtos, List<Datasource> entities, MapperEnum mapperEnum) {
        if (dtos != null) {
            assertNotNull(entities);
            assertEquals(dtos.size(), entities.size());
            for (DatasourceDto expectedItem : dtos) {
                boolean match = false;
                for (Datasource actualItem : entities) {
                    try {
                        assertEqualsDatasource(expectedItem, actualItem, mapperEnum);
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
            assertNull(entities);
        }
    }
}
