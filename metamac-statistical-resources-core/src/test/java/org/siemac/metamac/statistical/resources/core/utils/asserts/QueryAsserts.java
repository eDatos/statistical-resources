package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;

public class QueryAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // QUERY: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsQuery(Query expected, Query actual) {
        assertEqualsNameableStatisticalResource(expected.getLifeCycleStatisticalResource(), actual.getLifeCycleStatisticalResource());
        DatasetsAsserts.assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getLatestDataNumber(), actual.getLatestDataNumber());
        assertEqualsSelection(expected.getSelection(), actual.getSelection());
    }
    
    private static void assertEqualsSelection(Set<QuerySelectionItem> expected, Set<QuerySelectionItem> actual) {
        assertEqualsNullability(expected, actual);
        
        if (expected != null) {
            assertEquals(expected.size(), actual.size());
            for (QuerySelectionItem expectedSelectionItem : expected) {
                boolean found = false;
                for (QuerySelectionItem actualSelectionItem : actual) {
                    try {
                        assertEqualsQuerySelectionItem(expectedSelectionItem, actualSelectionItem);
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

    
    
    private static void assertEqualsQuerySelectionItem(QuerySelectionItem expectedSelectionItem, QuerySelectionItem actualSelectionItem) {
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

    public static void assertEqualsQueryCollection(Collection<Query> expected, Collection<Query> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (Query q : expected) {
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

    public static void assertEqualsQuery(Query entity, QueryDto dto) {
        assertEqualsQuery(entity, dto, MapperEnum.DO2DTO);
    }

    public static void assertEqualsQuery(QueryDto dto, Query entity) {
        assertEqualsQuery(entity, dto, MapperEnum.DTO2DO);
    }

    public static void assertEqualsQueryDoAndDtoCollection(Collection<Query> expected, Collection<QueryDto> actual) {
        assertEqualsQueryCollection(expected, actual, MapperEnum.DO2DTO);
    }

    public static void assertEqualsQueryDtoAndDoCollection(Collection<QueryDto> expected, Collection<Query> actual) {
        assertEqualsQueryCollection(actual, expected, MapperEnum.DTO2DO);
    }

    
    private static void assertEqualsQuery(Query entity, QueryDto dto, MapperEnum mapperEnum) {
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
        assertEqualsDatasetVersionInQuery(entity, dto.getDatasetVersion());
        
        assertNotNull(entity.getType());
        assertEquals(entity.getType(), dto.getType());
        
        assertEquals(entity.getLatestDataNumber(), dto.getLatestDataNumber());
        
        assertEqualsSelection(entity.getSelection(), dto.getSelection(), mapperEnum);
    }
    

    private static void assertEqualsSelection(Set<QuerySelectionItem> entitySelection, Map<String, Set<String>> dtoSelection, MapperEnum mapperEnum) {
        assertEqualsNullability(entitySelection, dtoSelection);
        
        if (entitySelection != null) {
            assertEquals(entitySelection.size(), dtoSelection.size());
            for (QuerySelectionItem entitySelectionItem : entitySelection) {
                Set<String> dtoSelectionItem = dtoSelection.get(entitySelectionItem.getDimension());
                if (dtoSelectionItem == null) {
                    fail("Dimensions found in entity.getSelection, thar are not in actual dto.getSelection");
                } else {
                    assertEquals(entitySelectionItem.getCodes().size(), dtoSelectionItem.size());
                    
                    for (CodeItem entityCodeItem : entitySelectionItem.getCodes()) {
                        if (!dtoSelectionItem.contains(entityCodeItem.getCode())) {
                            fail("Codes found in a dimension of entity.getSelection that are not in the respective dimension of dto.getSelection");
                        }
                    }
                }
            }
        }
    }

    private static void assertEqualsQueryCollection(Collection<Query> entities, Collection<QueryDto> dtos, MapperEnum mapperEnum) {
        if (entities != null) {
            assertNotNull(dtos);
            assertEquals(entities.size(), dtos.size());
            for (Query expectedItem : entities) {
                boolean match = false;
                for (QueryDto actualItem : dtos) {
                    try {
                        assertEqualsQuery(expectedItem, actualItem, mapperEnum);
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
    
    // -----------------------------------------------------------------
    // DATASET VERSION: QUERY AND DATASETVERSION URN 
    // -----------------------------------------------------------------
    private static void assertEqualsDatasetVersionInQuery(Query entity, String datasetVersionDtoUrn) {
        String datasetVersionEntityUrn = null; 
            
        if (entity.getDatasetVersion() != null && entity.getDatasetVersion().getSiemacMetadataStatisticalResource() != null) {
            datasetVersionEntityUrn = entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
        }
        
        assertEquals(datasetVersionEntityUrn, datasetVersionDtoUrn);
    }
}
