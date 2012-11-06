package org.siemac.metamac.statistical.resources.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

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
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
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

    public static void assertEqualsQueryDoAndDtoCollection(Collection<Query> expected, Collection<QueryDto> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (Query expectedItem : expected) {
                boolean match = false;
                for (QueryDto actualItem : actual) {
                    try {
                        assertEqualsQuery(expectedItem, actualItem);
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
            assertNull(actual);
        }
    }

    // -----------------------------------------------------------------
    // DATASET
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
    // DATASET VERSION
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

    // -----------------------------------------------------------------
    // DATASOURCE
    // -----------------------------------------------------------------
    public static void assertEqualsDatasource(Datasource expected, Datasource actual) {
        assertEqualsIdentifiableStatisticalResource(expected.getIdentifiableStatisticalResource(), actual.getIdentifiableStatisticalResource());
        assertEqualsDatasetVersion(expected.getDatasetVersion(), actual.getDatasetVersion());
    }
    
    public static void assertEqualsDatasource(Datasource entity, DatasourceDto dto) {
        assertEqualsIdentifiableStatisticalResource(entity.getIdentifiableStatisticalResource(), dto);
        assertEquals(entity.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn(), dto.getDatasetVersionUrn());
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
    
    public static void assertEqualsDatasourceDoAndDtoCollection(List<Datasource> expected, List<DatasourceDto> actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
            for (Datasource expectedItem : expected) {
                boolean match = false;
                for (DatasourceDto actualItem : actual) {
                    try {
                        assertEqualsDatasource(expectedItem, actualItem);
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
            assertNull(actual);
        }
    }

    // -----------------------------------------------------------------
    // MAIN HERITANCE
    // -----------------------------------------------------------------

    private static void assertEqualsSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource expected, SiemacMetadataStatisticalResource actual) {
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getFormat(), actual.getFormat());

        assertEqualsLifeCycleStatisticalResource(expected, actual);
    }

    private static void assertEqualsLifeCycleStatisticalResource(LifeCycleStatisticalResource expected, LifeCycleStatisticalResource actual) {
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

    private static void assertEqualsVersionableStatisticalResource(VersionableStatisticalResource expected, VersionableStatisticalResource actual) {
        assertEquals(expected.getVersionLogic(), actual.getVersionLogic());
        assertEquals(expected.getNextVersionDate(), actual.getNextVersionDate());
        assertEquals(expected.getIsLastVersion(), actual.getIsLastVersion());
        assertEquals(expected.getReplacedBy(), actual.getReplacedBy());
        assertEquals(expected.getReplaceTo(), actual.getReplaceTo());
        assertEquals(expected.getVersionRationaleType(), actual.getVersionRationaleType());

        assertEqualsInternationalString(expected.getVersionRationale(), actual.getVersionRationale());

        assertEqualsNameableStatisticalResource(expected, actual);
    }

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

        assertEqualsIdentifiableStatisticalResource(entity, dto);
    }

    private static void assertEqualsIdentifiableStatisticalResource(IdentifiableStatisticalResource entity, IdentifiableStatisticalResourceDto dto) {
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

    // -----------------------------------------------------------------
    // OTHER
    // -----------------------------------------------------------------
    public static void assertCollectionStructure(Collection expected, Collection actual) {
        if (expected != null) {
            assertNotNull(actual);
            assertEquals(expected.size(), actual.size());
        } else {
            assertNull(actual);
        }
    }

}
