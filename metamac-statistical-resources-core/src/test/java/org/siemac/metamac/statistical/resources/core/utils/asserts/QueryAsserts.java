package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;


public class QueryAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // QUERY: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsQuery(Query expected, Query actual) {
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsIdentifiableStatisticalResource(expected.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());
        assertEquals(expected.getLatestDataNumber(), actual.getLatestDataNumber());
        
        if (expected.getVersions() != null) {
            assertNotNull(actual.getVersions());
            assertEquals(expected.getVersions().size(), actual.getVersions().size());
            assertEqualsQueryVersionCollection(expected.getVersions(), actual.getVersions());
        } else {
            assertEquals(null, actual);
        }
    }
    
    public static void assertEqualsQueryVersion(QueryVersion expected, QueryVersion actual) throws MetamacException {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected queryVersion and the actual are not equals");
        } else if (expected != null && actual != null) {
            assertEqualsQueryVersion(expected, actual, false);
        }
    }
    
    private static void assertEqualsQueryVersion(QueryVersion expected, QueryVersion actual, boolean queryChecked) throws MetamacException {
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsLifeCycleStatisticalResource(expected.getLifeCycleStatisticalResource(), actual.getLifeCycleStatisticalResource());
        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getLatestDataNumber(), actual.getLatestDataNumber());
        assertEqualsSelection(expected.getSelection(), actual.getSelection());
        
        if (!queryChecked) {
            assertEqualsQuery(expected.getQuery(), actual.getQuery());
        }
    }
    
    private static void assertEqualsSelection(List<QuerySelectionItem> expected, List<QuerySelectionItem> actual) {
        assertEqualsNullability(expected, actual);
        
        if (expected != null) {
            assertEquals(expected.size(), actual.size());
            for (QuerySelectionItem expectedSelectionItem : expected) {
                boolean found = false;
                for (QuerySelectionItem actualSelectionItem : actual) {
                    try {
                        assertEqualsQueryVersionSelectionItem(expectedSelectionItem, actualSelectionItem);
                        found = true;
                    } catch (AssertionError e) {
                        found = false;
                    }
                }
                if (!found) {
                    fail("Dimensions found in entity.getSelection, thar are not in actual dto.getSelection");
                }
            }
        }
    }

    
    
    private static void assertEqualsQueryVersionSelectionItem(QuerySelectionItem expectedSelectionItem, QuerySelectionItem actualSelectionItem) {
        assertEqualsNullability(expectedSelectionItem, actualSelectionItem);
        
        if (expectedSelectionItem != null) {
            assertEquals(expectedSelectionItem.getDimension(), actualSelectionItem.getDimension());
            for (CodeItem expectedCodeItem : expectedSelectionItem.getCodes()) {
                boolean found = false;
                for (CodeItem actualCodeItem : actualSelectionItem.getCodes()) {
                    if (expectedCodeItem.getCode().equals(actualCodeItem.getCode())) {
                        found = true;
                    }
                }
                
                if (!found) {
                    fail("Codes found in a dimension of entity.getSelection that are not in the respective dimension of dto.getSelection");
                }
            }
            
        }
        
    }

    public static void assertEqualsQueryVersionCollection(Collection<QueryVersion> expected, Collection<QueryVersion> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (QueryVersion q : expected) {
                if (!actual.contains(q)) {
                    fail("Found elements in expected collection, which are not contained in actual collection");
                }
            }
        } else {
            assertNull(actual);
        }
    }

    // -----------------------------------------------------------------
    // QUERY: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsQueryVersion(QueryVersion entity, QueryDto dto) {
        assertEqualsQueryVersion(entity, dto, MapperEnum.DO2DTO);
    }

    public static void assertEqualsQueryVersion(QueryDto dto, QueryVersion entity) {
        assertEqualsQueryVersion(entity, dto, MapperEnum.DTO2DO);
    }
    
    public static void assertEqualsQuerySelection(Map<String, List<CodeItemDto>> dtos, List<QuerySelectionItem> entities) {
        assertEqualsSelection(entities, dtos, MapperEnum.DTO2DO);
    }
    
    public static void assertEqualsQuerySelection(List<QuerySelectionItem> entities, Map<String, List<CodeItemDto>> dtos) {
        assertEqualsSelection(entities, dtos, MapperEnum.DO2DTO);
    }

    public static void assertEqualsQueryVersionDoAndDtoCollection(Collection<QueryVersion> expected, Collection<QueryDto> actual) {
        assertEqualsQueryVersionCollection(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsQueryVersionDtoAndDoCollection(Collection<QueryDto> expected, Collection<QueryVersion> actual) {
        assertEqualsQueryVersionCollection(actual, expected, MapperEnum.DTO2DO);
    }
    
    private static void assertEqualsQueryVersionCollection(Collection<QueryVersion> entities, Collection<QueryDto> dtos, MapperEnum mapperEnum) {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (QueryVersion expectedItem : entities) {
                boolean match = false;
                for (QueryDto actualItem : dtos) {
                    try {
                        assertEqualsQueryVersion(expectedItem, actualItem, mapperEnum);
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

    private static void assertEqualsQueryVersion(QueryVersion entity, QueryDto dto, MapperEnum mapperEnum) {
        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertEquals(entity.getId(), dto.getId());
            
            assertNotNull(entity.getUuid());
            assertEquals(entity.getUuid(), dto.getUuid());
            
            assertNotNull(entity.getVersion());
            assertEquals(entity.getVersion(), dto.getVersion());
            
            assertNotNull(entity.getStatus());
            assertEquals(entity.getStatus(), dto.getStatus());
        }
        assertEqualsNameableStatisticalResource(entity.getLifeCycleStatisticalResource(), dto, mapperEnum);
        assertEqualsRelatedDatasetVersionInQueryVersion(entity, dto.getRelatedDatasetVersion());
        
        assertNotNull(entity.getType());
        assertEquals(entity.getType(), dto.getType());
        
        assertEquals(entity.getLatestDataNumber(), dto.getLatestDataNumber());
        
        assertEqualsSelection(entity.getSelection(), dto.getSelection(), mapperEnum);
    }

    

    private static void assertEqualsSelection(List<QuerySelectionItem> entitySelection, Map<String, List<CodeItemDto>> dtoSelection, MapperEnum mapperEnum) {
        assertEqualsNullability(entitySelection, dtoSelection);
        
        if (entitySelection != null) {
            assertEquals(entitySelection.size(), dtoSelection.size());
            for (QuerySelectionItem entitySelectionItem : entitySelection) {
                List<CodeItemDto> dtoSelectionItem = dtoSelection.get(entitySelectionItem.getDimension());
                if (dtoSelectionItem == null) {
                    fail("Dimensions found in entity.getSelection, thar are not in actual dto.getSelection");
                } else {
                    assertEquals(entitySelectionItem.getCodes().size(), dtoSelectionItem.size());
                    
                    for (CodeItem entityCodeItem : entitySelectionItem.getCodes()) {
                        boolean found = false;
                        for (CodeItemDto dtoCodeItem : dtoSelectionItem) {
                            if (entityCodeItem.getCode().equals(dtoCodeItem.getCode())) {
                                assertEquals(entityCodeItem.getTitle(), dtoCodeItem.getTitle());
                                found = true;
                            }
                        }
                        if (!found) {
                            fail("Codes found in a dimension of entity.getSelection that are not in the respective dimension of dto.getSelection");
                        }
                    }
                }
            }
        }
    }
    
    // -----------------------------------------------------------------
    // QUERY: DTO & DTO
    // -----------------------------------------------------------------

    
    public static void assertEqualsQuerySelection(Map<String, List<CodeItemDto>> expected, Map<String, List<CodeItemDto>> actual) {
        assertEqualsNullability(expected, actual);
        
        if (expected != null) {
            assertEquals(expected.size(), actual.size());
            for (String dimId : expected.keySet()) {
                List<CodeItemDto> expectedCodes = expected.get(dimId);
                List<CodeItemDto> actualCodes = actual.get(dimId);
                assertEqualsCodeItemDtoCollection(expectedCodes, actualCodes);
            }
        }
    }

    public static void assertEqualsCodeItemDtoCollection(List<CodeItemDto> expected, List<CodeItemDto> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (int i = 0; i < expected.size(); i++) {
                assertEqualsCodeItemDto(expected.get(i), actual.get(i));
            }
        } else {
            assertNull(actual);
        }
    }
    
    
    private static void assertEqualsCodeItemDto(CodeItemDto expected, CodeItemDto actual) {
        assertEqualsNullability(expected, actual);
        if (expected != null) {
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getTitle(), actual.getTitle());
        }
    }

    
    // -----------------------------------------------------------------
    // DATASET VERSION: QUERY AND DATASETVERSION URN 
    // -----------------------------------------------------------------
    private static void assertEqualsRelatedDatasetVersionInQueryVersion(QueryVersion entity, RelatedResourceDto relatedDataset) {
        String datasetVersionEntityUrn = null; 
        String datasetVersionEntityCode = null; 
        InternationalString datasetVersionEntityTitle = null; 
        
        if (entity.getDatasetVersion() != null && entity.getDatasetVersion().getSiemacMetadataStatisticalResource() != null) {
            datasetVersionEntityUrn = entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
            datasetVersionEntityTitle = entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getTitle();
            datasetVersionEntityCode = entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getCode();
        }
        
        assertEquals(datasetVersionEntityUrn, relatedDataset.getUrn());
        assertEquals(TypeRelatedResourceEnum.DATASET_VERSION, relatedDataset.getType());
        assertEquals(datasetVersionEntityCode, relatedDataset.getCode());
        assertEqualsInternationalString(datasetVersionEntityTitle, relatedDataset.getTitle());
        
    }
}
