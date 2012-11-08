package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collection;

import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;

public class BaseAsserts extends MetamacAsserts {

    // -----------------------------------------------------------------
    // MAIN HERITANCE: DO & DO
    // -----------------------------------------------------------------

    protected static void assertEqualsSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource expected, SiemacMetadataStatisticalResource actual) {
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getFormat(), actual.getFormat());

        assertEqualsLifeCycleStatisticalResource(expected, actual);
    }

    protected static void assertEqualsLifeCycleStatisticalResource(LifeCycleStatisticalResource expected, LifeCycleStatisticalResource actual) {
        assertEquals(expected.getVersionResponsibilityCreator(), actual.getVersionResponsibilityCreator());
        assertEquals(expected.getVersionResponsibilityContributor(), actual.getVersionResponsibilityContributor());
        assertEquals(expected.getVersionResponsibilitySubmitted(), actual.getVersionResponsibilitySubmitted());
        assertEquals(expected.getVersionResponsibilityAccepted(), actual.getVersionResponsibilityAccepted());
        assertEquals(expected.getVersionResponsibilityIssued(), actual.getVersionResponsibilityIssued());
        assertEquals(expected.getVersionResponsibilityOutOfPrint(), actual.getVersionResponsibilityOutOfPrint());
        assertEquals(expected.getProcStatus(), actual.getProcStatus());

        assertEqualsExternalItem(expected.getCreator(), actual.getCreator());

        assertCollectionStructure(expected.getContributor(), actual.getContributor());
        if (expected.getContributor() != null) {
            for (int i = 0; i < expected.getContributor().size(); i++) {
                assertEqualsExternalItem(expected.getContributor().get(i), actual.getContributor().get(i));
            }
        }

        assertCollectionStructure(expected.getPublisher(), actual.getPublisher());
        if (expected.getPublisher() != null) {
            for (int i = 0; i < expected.getPublisher().size(); i++) {
                assertEqualsExternalItem(expected.getPublisher().get(i), actual.getPublisher().get(i));
            }
        }

        assertCollectionStructure(expected.getMediator(), actual.getMediator());
        if (expected.getMediator() != null) {
            for (int i = 0; i < expected.getMediator().size(); i++) {
                assertEqualsExternalItem(expected.getMediator().get(i), actual.getMediator().get(i));
            }
        }

        assertEqualsVersionableStatisticalResource(expected, actual);
    }

    protected static void assertEqualsVersionableStatisticalResource(VersionableStatisticalResource expected, VersionableStatisticalResource actual) {
        assertEquals(expected.getVersionLogic(), actual.getVersionLogic());
        assertEquals(expected.getNextVersionDate(), actual.getNextVersionDate());
        assertEquals(expected.getIsLastVersion(), actual.getIsLastVersion());
        assertEquals(expected.getReplacedBy(), actual.getReplacedBy());
        assertEquals(expected.getReplaceTo(), actual.getReplaceTo());
        assertEquals(expected.getVersionRationaleType(), actual.getVersionRationaleType());

        assertEqualsInternationalString(expected.getVersionRationale(), actual.getVersionRationale());

        assertEqualsNameableStatisticalResource(expected, actual);
    }

    protected static void assertEqualsNameableStatisticalResource(NameableStatisticalResource expected, NameableStatisticalResource actual) {
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEqualsInternationalString(expected.getDescription(), actual.getDescription());

        assertEqualsIdentifiableStatisticalResource(expected, actual);
    }

    protected static void assertEqualsIdentifiableStatisticalResource(IdentifiableStatisticalResource expected, IdentifiableStatisticalResource actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());

        assertEqualsStatisticalResource(expected, actual);
    }

    protected static void assertEqualsStatisticalResource(StatisticalResource expected, StatisticalResource actual) {
        assertEqualsExternalItem(expected.getOperation(), actual.getOperation());

    }

    // -----------------------------------------------------------------
    // MAIN HERITANCE: DTO & DO
    // -----------------------------------------------------------------

    protected static void assertEqualsNameableStatisticalResource(NameableStatisticalResource entity, NameableStatisticalResourceDto dto, MapperEnum mapperEnum) {
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
        assertEqualsInternationalString(entity.getDescription(), dto.getDescription());

        assertEqualsIdentifiableStatisticalResource(entity, dto, mapperEnum);
    }

    protected static void assertEqualsIdentifiableStatisticalResource(IdentifiableStatisticalResource entity, IdentifiableStatisticalResourceDto dto, MapperEnum mapperEnum) {
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getUri(), dto.getUri());
        assertEquals(entity.getUrn(), dto.getUrn());

        assertEqualsStatisticalResouce(entity, dto, mapperEnum);
    }

    protected static void assertEqualsStatisticalResouce(StatisticalResource entity, StatisticalResourceDto dto, MapperEnum mapperEnum) {
        assertEqualsExternalItem(entity.getOperation(), dto.getOperation());
        
        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertNotNull(entity.getCreatedBy());
            assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
            
            assertNotNull(entity.getCreatedDate());
            assertEqualsDate(entity.getCreatedDate(), dto.getCreatedDate());
            
            assertNotNull(entity.getLastUpdatedBy());
            assertEquals(entity.getLastUpdatedBy(), dto.getLastUpdatedBy());
            
            assertNotNull(entity.getLastUpdated());
            assertEqualsDate(entity.getLastUpdated(), dto.getLastUpdated());
        }
    }

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
}
