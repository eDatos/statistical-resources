package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

public class DatasetsAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // DATASET: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsDataset(Dataset expected, Dataset actual) {
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsIdentifiableStatisticalResource(expected.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());

        if (expected.getVersions() != null) {
            assertNotNull(actual.getVersions());
            assertEquals(expected.getVersions().size(), actual.getVersions().size());
            assertEqualsDatasetVersionCollection(expected.getVersions(), actual.getVersions());
        } else {
            assertEquals(null, actual);
        }
    }
    
    public static void assertEqualsCoverageForDsdComponent(DatasetVersion datasetVersion, String dsdComponentId, List<CodeDimension> codes) {
        assertNotNull(datasetVersion.getCoverages());
        List<CodeDimension> codesForDimensions = new ArrayList<CodeDimension>();
        for (CodeDimension codeDim : datasetVersion.getCoverages()) {
            if (codeDim.getDsdComponentId().equals(dsdComponentId)) {
                codesForDimensions.add(codeDim);
            }
        }
        
        assertEquals(codes.size(), codesForDimensions.size());
        
        for (int i = 0; i < codes.size(); i++) {
            assertEquals(codes.get(i).getIdentifier(), codesForDimensions.get(i).getIdentifier());
            assertEquals(codes.get(i).getTitle(), codesForDimensions.get(i).getTitle());
        }
    }
    
    public static void assertEqualsCodeDimensionsCollection(List<CodeDimension> expected, List<CodeDimension> actual) {
        assertEqualsNullability(expected, actual);
        
        if (expected != null) {
            assertEquals(expected.size(), actual.size());
            
            
            for (int i = 0; i < expected.size(); i++) {
                CodeDimension expectedCode = expected.get(i);
                CodeDimension actualCode = actual.get(i);
                assertEqualsCodeDimension(expectedCode, actualCode);
            }
        }
    }

    public static void assertEqualsCodeDimension(CodeDimension expected, CodeDimension actual) {
        assertEqualsNullability(expected, actual);
        assertEquals(expected.getDsdComponentId(), actual.getDsdComponentId());
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEqualsNullability(expected.getDatasetVersion(), actual.getDatasetVersion());
        if (expected.getDatasetVersion() != null) {
            assertEquals(expected.getDatasetVersion().getId(), actual.getDatasetVersion().getId());
        }
    }

    // -----------------------------------------------------------------
    // DATASET VERSION: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsDatasetVersion(DatasetVersion expected, DatasetVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected datasetVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsDatasetVersion(expected, actual, false);
        }
    }
    
    public static void assertEqualsDatasetVersionNotChecksDataset(DatasetVersion expected, DatasetVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected datasetVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsDatasetVersion(expected, actual, true);
        }
    }

    private static void assertEqualsDatasetVersion(DatasetVersion expected, DatasetVersion actual, boolean datasetChecked) throws MetamacException {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEqualsSiemacMetadataStatisticalResource(expected.getSiemacMetadataStatisticalResource(), actual.getSiemacMetadataStatisticalResource());

        assertEqualsDatasetVersionMetadata(expected, actual);

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

    private static void assertEqualsDatasetVersionMetadata(DatasetVersion expected, DatasetVersion actual) {
        assertEquals(expected.getUuid(), actual.getUuid());

        assertEqualsExternalItem(expected.getRelatedDsd(), actual.getRelatedDsd());
        assertEqualsExternalItemCollection(expected.getGeographicCoverage(), actual.getGeographicCoverage());
        assertEqualsTemporalCodeCollection(expected.getTemporalCoverage(), actual.getTemporalCoverage());
        assertEqualsExternalItemCollection(expected.getMeasureCoverage(), actual.getMeasureCoverage());
        assertEqualsExternalItemList(expected.getGeographicGranularities(), actual.getGeographicGranularities());
        assertEqualsExternalItemList(expected.getTemporalGranularities(), actual.getTemporalGranularities());
        assertEqualsExternalItemList(expected.getStatisticalUnit(), actual.getStatisticalUnit());
        assertEqualsExternalItem(expected.getUpdateFrequency(), actual.getUpdateFrequency());

        assertEquals(expected.getDateStart(), actual.getDateStart());
        assertEquals(expected.getDateEnd(), actual.getDateEnd());
        assertEquals(expected.getDateNextUpdate(), actual.getDateNextUpdate());

        assertEquals(expected.getFormatExtentDimensions(), actual.getFormatExtentDimensions());
        assertEquals(expected.getFormatExtentObservations(), actual.getFormatExtentObservations());

        assertEqualsStatisticOfficiality(expected.getStatisticOfficiality(), actual.getStatisticOfficiality());

        assertEqualsInternationalString(expected.getBibliographicCitation(), actual.getBibliographicCitation());

    }

    // -----------------------------------------------------------------
    // DATASET VERSION: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsDatasetVersion(DatasetVersion entity, DatasetVersionDto dto) throws MetamacException {
        assertEqualsDatasetVersion(dto, entity, MapperEnum.DO2DTO);
    }

    public static void assertEqualsDatasetVersion(DatasetVersionDto dto, DatasetVersion entity) throws MetamacException {
        assertEqualsDatasetVersion(dto, entity, MapperEnum.DTO2DO);
    }

    private static void assertEqualsDatasetVersion(DatasetVersionDto dto, DatasetVersion entity, MapperEnum mapperEnum) throws MetamacException {
        assertEqualsSiemacMetadataStatisticalResource(entity.getSiemacMetadataStatisticalResource(), dto, mapperEnum);

        // Dataset attributes

        // TODO: Las cosas comunes ponerlas fuera
        switch (mapperEnum) {
            case DO2DTO:
                assertEquals(entity.getId(), dto.getId());
                assertEquals(entity.getUuid(), dto.getUuid());
                assertEquals(entity.getVersion(), dto.getVersion());

                assertEqualsExternalItemCollectionMapper(entity.getGeographicCoverage(), dto.getGeographicCoverage());
                assertEqualsTemporalCodeCollectionMapper(entity.getTemporalCoverage(), dto.getTemporalCoverage());
                assertEqualsExternalItemCollectionMapper(entity.getMeasureCoverage(), dto.getMeasureCoverage());
                assertEqualsExternalItemCollectionMapper(entity.getTemporalGranularities(), dto.getTemporalGranularities());
                assertEqualsExternalItemCollectionMapper(entity.getGeographicGranularities(), dto.getGeographicGranularities());
                assertEqualsExternalItemCollectionMapper(entity.getStatisticalUnit(), dto.getStatisticalUnit());

                assertEqualsDate(entity.getDateStart(), dto.getDateStart());
                assertEqualsDate(entity.getDateEnd(), dto.getDateEnd());
                assertEqualsDate(entity.getDateNextUpdate(), dto.getDateNextUpdate());

                assertEqualsStatisticOfficiality(entity.getStatisticOfficiality(), dto.getStatisticOfficiality());
                assertEqualsExternalItem(entity.getRelatedDsd(), dto.getRelatedDsd(), mapperEnum);
                assertEqualsExternalItem(entity.getUpdateFrequency(), dto.getUpdateFrequency(), mapperEnum);
                assertEquals(entity.getFormatExtentDimensions(), dto.getFormatExtentDimensions());
                assertEquals(entity.getFormatExtentObservations(), dto.getFormatExtentObservations());

                assertEqualsInternationalString(entity.getBibliographicCitation(), dto.getBibliographicCitation());
                break;
            case DTO2DO:
                assertEqualsExternalItemCollectionMapper(entity.getStatisticalUnit(), dto.getStatisticalUnit());
                assertEqualsExternalItem(entity.getRelatedDsd(), dto.getRelatedDsd(), mapperEnum);
                assertEqualsExternalItem(entity.getUpdateFrequency(), dto.getUpdateFrequency(), mapperEnum);
                assertEqualsStatisticOfficiality(entity.getStatisticOfficiality(), dto.getStatisticOfficiality());
                break;
        }
    }

    // -----------------------------------------------------------------
    // DATASOURCE: DO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsDatasource(Datasource expected, Datasource actual) throws MetamacException {
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
