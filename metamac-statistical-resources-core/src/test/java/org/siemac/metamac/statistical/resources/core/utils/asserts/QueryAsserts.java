package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class QueryAsserts extends BaseAsserts {

    // -----------------------------------------------------------------
    // QUERY: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsQuery(Query expected, Query actual) {
        assertEqualsNameableStatisticalResource(expected.getLifeCycleStatisticalResource(), actual.getLifeCycleStatisticalResource());
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
            // TODO: Comprobar dto.versionOptimisticLocking = entity.version
        }
        assertEqualsNameableStatisticalResource(entity.getLifeCycleStatisticalResource(), dto, mapperEnum);
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
}
