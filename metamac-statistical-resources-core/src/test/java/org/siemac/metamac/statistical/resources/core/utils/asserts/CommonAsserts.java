package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.MetamacReflectionUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class CommonAsserts extends MetamacAsserts {

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalString expected, InternationalString actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }

        assertEquals(expected.getTexts().size(), actual.getTexts().size());
        for (LocalisedString localisedStringExpected : expected.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), actual.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }

    public static void assertEqualsInternationalString(InternationalString internationalString, String locale1, String label1, String locale2, String label2) {
        int count = 0;
        if (locale1 != null) {
            assertEquals(label1, internationalString.getLocalisedLabel(locale1));
            count++;
        }
        if (locale2 != null) {
            assertEquals(label2, internationalString.getLocalisedLabel(locale2));
            count++;
        }
        assertEquals(count, internationalString.getTexts().size());
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsInternationalString(InternationalString entity, InternationalStringDto dto) {
        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getTexts().size(), dto.getTexts().size());
        for (LocalisedString localisedStringExpected : entity.getTexts()) {
            assertEquals(localisedStringExpected.getLabel(), dto.getLocalisedLabel(localisedStringExpected.getLocale()));
        }
    }

    // -----------------------------------------------------------------
    // RELATED RESOURCE: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsRelatedResource(RelatedResource expected, RelatedResource actual) {

        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        
        assertEquals(expected.getType(), actual.getType());
        Long actualResourceId = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(actual);
        Long expectedResourceId = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(expected);
        assertEqualsNullability(expectedResourceId, actualResourceId);
        assertEquals(expectedResourceId, actualResourceId);
    }


    public static void assertEqualsRelatedResourceCollection(Collection<RelatedResource> expected, Collection<RelatedResource> actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.size(), actual.size());

        for (RelatedResource expec : expected) {
            boolean found = false;
            for (RelatedResource actualRes : actual) {
                if (actualRes.getType().equals(expec.getType())) {
                    Long actualResId = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(actualRes);
                    Long expecId = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(expec);
                    if (actualResId.equals(expecId)) {
                        found = true;
                    }
                }
            }
            if (!found) {
                Assert.fail("Found element in expected collection which is not contained in actual collection");
            }
        }
    }

    public static void assertEqualsRelatedResourceList(List<RelatedResource> expected, List<RelatedResource> actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEqualsRelatedResource(expected.get(i), actual.get(i));
        }
    }

    // -----------------------------------------------------------------
    // RELATED RESOURCES : DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsRelatedResource(RelatedResource entity, RelatedResourceDto dto) {

        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getType(), dto.getType());
        Long id = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(entity);

        assertEqualsNullability(id, dto.getRelatedId());
        assertEquals(id, dto.getRelatedId());
    }

    public static void assertEqualsRelatedResourceCollectionMapper(Collection<RelatedResource> entities, Collection<RelatedResourceDto> dtos) {

        assertEqualsNullability(entities, dtos);
        if (entities == null) {
            assertNull(dtos);
        }
        assertEquals(entities.size(), dtos.size());
        for (RelatedResource entity : entities) {
            boolean found = false;
            Iterator<RelatedResourceDto> itDto = dtos.iterator();
            while (itDto.hasNext() && !found) {
                RelatedResourceDto dto = itDto.next();
                found = true;
                try {
                    assertEqualsRelatedResource(entity, dto);
                } catch (AssertionError e) {
                    found = false;
                }
            }
            if (!found) {
                Assert.fail("Not equal collections");
            }
        }
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItem expected, ExternalItem actual) {

        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }

        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getType(), actual.getType());
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getManagementAppUrl(), actual.getManagementAppUrl());
    }

    // CAUTION: objects must implement equals!
    public static void assertEqualsExternalItemCollection(Collection<ExternalItem> expected, Collection<ExternalItem> actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.size(), actual.size());

        for (ExternalItem expec : expected) {
            boolean found = false;
            for (ExternalItem actualItem : actual) {
                if (actualItem.getUrn().equals(expec.getUrn())) {
                    found = true;
                }
            }
            if (!found) {
                Assert.fail("Found element in expected collection which is not contained in actual collection");
            }
        }
    }

    public static void assertEqualsExternalItemList(List<ExternalItem> expected, List<ExternalItem> actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.size(), actual.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEqualsExternalItem(expected.get(i), actual.get(i));
        }
    }

    // -----------------------------------------------------------------
    // EXTERNAL ITEMS: DTO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsExternalItem(ExternalItem entity, ExternalItemDto dto) {

        assertEqualsNullability(entity, dto);
        if (entity == null) {
            return;
        }

        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getUri(), dto.getUri());
        assertEquals(entity.getUrn(), dto.getUrn());
        assertEquals(entity.getType(), dto.getType());
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
        assertEquals(entity.getManagementAppUrl(), dto.getManagementAppUrl());
    }

    public static void assertEqualsExternalItemCollectionMapper(Collection<ExternalItem> entities, Collection<ExternalItemDto> dtos) {
        if (entities == null) {
            entities = new ArrayList<ExternalItem>(); 
        }
        
        if (dtos == null) {
            dtos = new ArrayList<ExternalItemDto>();
        }

        assertEquals(entities.size(), dtos.size());
        for (ExternalItem entity : entities) {
            boolean found = false;
            Iterator<ExternalItemDto> itDto = dtos.iterator();
            while (itDto.hasNext() && !found) {
                ExternalItemDto dto = itDto.next();
                found = true;
                try {
                    assertEqualsExternalItem(entity, dto);
                } catch (AssertionError e) {
                    found = false;
                }
            }
            if (!found) {
                Assert.fail("Not equal collections");
            }
        }
    }

    // -----------------------------------------------------------------
    // OTHER
    // -----------------------------------------------------------------
    @SuppressWarnings("rawtypes")
    public static void assertCollectionStructure(Collection expected, Collection actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
        } else {
            assertNull(actual);
        }
    }

    private static boolean messageParametersEquals(Serializable[] expected, Serializable[] actual) {
        if (expected != null && actual != null) {
            for (Serializable expec : expected) {
                boolean found = false;
                for (Serializable act : actual) {
                    if (expec.equals(act)) {
                        found = true;
                    }
                }
                if (!found) {
                    return false;
                }
            }
        } else {
            return expected == null && actual == null;
        }
        return true;
    }

    protected static void assertRelaxedEqualsObject(Object expected, Object actual) {
        if ((expected != null && actual == null) || (expected == null && actual != null)) {
            fail("The expected object and the actual are not equals");
        } else if (expected != null && actual != null) {
            Long expectedId = MetamacReflectionUtils.retrieveObjectId(expected);
            Long actualId = MetamacReflectionUtils.retrieveObjectId(actual);
            assertEquals(expectedId, actualId);
        }
    }
}
