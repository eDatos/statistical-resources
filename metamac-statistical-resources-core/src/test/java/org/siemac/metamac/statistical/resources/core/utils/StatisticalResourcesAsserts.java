package org.siemac.metamac.statistical.resources.core.utils;

import static org.junit.Assert.assertEquals;

import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public class StatisticalResourcesAsserts extends MetamacAsserts {

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------

    public static void assertEqualsQuery(Query expected, Query actual) {
        assertEqualsNameableStatisticalResource(expected.getNameableStatisticalResource(), actual.getNameableStatisticalResource());
    }
    
    public static void assertEqualsQuery(Query entity, QueryDto dto) {
        assertEqualsNameableStatisticalResource(entity.getNameableStatisticalResource(), dto);
    }


    // -----------------------------------------------------------------
    // MAIN HERITANCE
    // -----------------------------------------------------------------

    private static void assertEqualsNameableStatisticalResource(NameableStatisticalResource expected, NameableStatisticalResource actual) {
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
        
        assertEqualsIdentifiableStatisticalResource(expected, actual);
    }

    private static void assertEqualsIdentifiableStatisticalResource(IdentifiableStatisticalResource expected, IdentifiableStatisticalResource actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());

        assertEqualsStatisticalResource(expected, actual);
    }

    private static void assertEqualsStatisticalResource(StatisticalResource expected, StatisticalResource actual) {
        assertEqualsExternalItem(expected.getOperation(), actual.getOperation());
        
    }
    
    private static void assertEqualsNameableStatisticalResource(NameableStatisticalResource entity, NameableStatisticalResourceDto dto) {
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
        assertEqualsInternationalString(entity.getDescription(), dto.getDescription());
        
        assertEqualsIdentifiableResource(entity, dto);
    }
    
    private static void assertEqualsIdentifiableResource(IdentifiableStatisticalResource entity, IdentifiableStatisticalResourceDto dto) {
        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getUri(), dto.getUri());
        assertEquals(entity.getUrn(), dto.getUrn());
        
        assertEqualsStatisticalResouce(entity, dto);
    }
    
    private static void assertEqualsStatisticalResouce(StatisticalResource entity, StatisticalResourceDto dto) {
        assertEqualsExternalItem(entity.getOperation(), dto.getOperation());
        
        assertEquals(entity.getCreatedBy(), dto.getCreatedBy());
        assertEqualsDate(entity.getCreatedDate(), dto.getCreatedDate());
        assertEquals(entity.getLastUpdatedBy(), dto.getLastUpdatedBy());
        assertEqualsDate(entity.getLastUpdated(), dto.getLastUpdated());
    }

    // -----------------------------------------------------------------
    // INTERNATIONAL STRING
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
    // EXTERNAL ITEMS
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

}
