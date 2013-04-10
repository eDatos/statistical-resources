package org.siemac.metamac.statistical.resources.core.utils.asserts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.Serializable;
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
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
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
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;

public class BaseAsserts extends MetamacAsserts {

    // -----------------------------------------------------------------
    // VERSIONING ASSERTS: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsVersioningSiemacMetadata(SiemacMetadataStatisticalResource previous, SiemacMetadataStatisticalResource next) {
        assertEqualsVersioningLifecycle(previous, next);

        // Not inherited
        assertNull(next.getLastUpdate());
        assertNull(next.getNewnessUntilDate());
        assertNull(next.getReplaces());
        assertNull(next.getIsReplacedBy());
        assertEquals(0, next.getHasPart().size());
        assertEquals(0, next.getIsPartOf().size());
        assertNull(next.getCopyrightedDate());

        // Inherited
        assertEqualsExternalItem(previous.getLanguage(), next.getLanguage());
        assertEqualsExternalItemCollection(previous.getLanguages(), next.getLanguages());

        assertEqualsExternalItem(previous.getStatisticalOperation(), next.getStatisticalOperation());
        assertEqualsExternalItemCollection(previous.getStatisticalOperationInstances(), next.getStatisticalOperationInstances());

        assertEqualsInternationalString(previous.getSubtitle(), next.getSubtitle());
        assertEqualsInternationalString(previous.getTitleAlternative(), next.getTitleAlternative());
        assertEqualsInternationalString(previous.getAbstractLogic(), next.getAbstractLogic());
        // TODO: keywords?
        assertEquals(previous.getType(), next.getType());

        assertEqualsExternalItem(previous.getMaintainer(), next.getMaintainer());
        assertEqualsExternalItem(previous.getCreator(), next.getCreator());
        assertEqualsExternalItemCollection(previous.getContributor(), next.getContributor());
        assertEqualsDate(previous.getCreatedDate(), next.getCreatedDate());
        assertEqualsInternationalString(previous.getConformsTo(), next.getConformsTo());
        assertEqualsInternationalString(previous.getConformsToInternal(), next.getConformsToInternal());

        assertEqualsExternalItemCollection(previous.getPublisher(), next.getPublisher());
        assertEqualsExternalItemCollection(previous.getPublisherContributor(), next.getPublisherContributor());
        assertEqualsExternalItemCollection(previous.getMediator(), next.getMediator());

        assertEqualsRelatedResourceCollection(previous.getRequires(), next.getRequires());
        assertEqualsRelatedResourceCollection(previous.getIsRequiredBy(), next.getIsRequiredBy());

        assertEqualsExternalItem(previous.getRightsHolder(), next.getRightsHolder());
        assertEqualsInternationalString(previous.getLicense(), next.getLicense());
        assertEqualsInternationalString(previous.getAccessRights(), next.getAccessRights());
    }

    private static void assertEqualsVersioningLifecycle(LifeCycleStatisticalResource previous, LifeCycleStatisticalResource next) {
        assertEqualsVersioningVersionable(previous, next);
        assertEquals(StatisticalResourceProcStatusEnum.DRAFT, next.getProcStatus());

        assertNotNull(next.getCreationDate());
        assertFalse(previous.getCreationDate().equals(next.getCreationDate()));
        assertNotNull(next.getCreationUser());
        assertFalse(previous.getCreationUser().equals(next.getCreationUser()));

        assertNull(next.getProductionValidationDate());
        assertNull(next.getProductionValidationUser());
        assertNull(next.getDiffusionValidationDate());
        assertNull(next.getDiffusionValidationUser());
        assertNull(next.getRejectValidationDate());
        assertNull(next.getRejectValidationUser());
        assertNull(next.getInternalPublicationDate());
        assertNull(next.getInternalPublicationUser());
        assertNull(next.getExternalPublicationDate());
        assertNull(next.getExternalPublicationUser());
        assertNull(next.getExternalPublicationFailed());
        assertNull(next.getExternalPublicationFailedDate());
        assertNull(next.getReplacesVersion());
        assertNull(next.getIsReplacedByVersion());
    }

    private static void assertEqualsVersioningVersionable(VersionableStatisticalResource previous, VersionableStatisticalResource next) {
        assertEqualsVersioningNameable(previous, next);

        assertNotNull(next.getVersionLogic());
        assertFalse(previous.getVersionLogic().equals(next.getVersionLogic()));

        assertEquals(0, next.getVersionRationaleTypes().size());
        assertNull(next.getVersionRationale());
        assertNull(next.getValidFrom());
        assertNull(next.getValidTo());
        assertNull(next.getNextVersion());
        assertNull(next.getNextVersionDate());
    }

    private static void assertEqualsVersioningNameable(NameableStatisticalResource previous, NameableStatisticalResource next) {
        assertEqualsVersioningIdentifiable(previous, next);

        assertEqualsInternationalString(previous.getTitle(), next.getTitle());
        assertEqualsInternationalString(previous.getDescription(), next.getDescription());
    }

    private static void assertEqualsVersioningIdentifiable(IdentifiableStatisticalResource previous, IdentifiableStatisticalResource next) {
        assertEquals(previous.getCode(), next.getCode());
        assertNotNull(next.getUrn());
        assertFalse(next.getUrn().equals(previous.getUrn()));
        assertNull(next.getUri());
    }

    // -----------------------------------------------------------------
    // MAIN HERITANCE: DO & DO
    // -----------------------------------------------------------------

    protected static void assertEqualsSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource expected, SiemacMetadataStatisticalResource actual) {
        assertEqualsExternalItem(expected.getLanguage(), actual.getLanguage());
        assertEqualsExternalItemList(expected.getLanguages(), actual.getLanguages());

        assertEqualsExternalItem(expected.getStatisticalOperation(), actual.getStatisticalOperation());
        assertEqualsExternalItemCollection(expected.getStatisticalOperationInstances(), actual.getStatisticalOperationInstances());

        assertEqualsInternationalString(expected.getSubtitle(), actual.getSubtitle());
        assertEqualsInternationalString(expected.getTitleAlternative(), actual.getTitleAlternative());
        assertEqualsInternationalString(expected.getAbstractLogic(), actual.getAbstractLogic());

        // TODO: keywords

        assertEquals(expected.getType(), actual.getType());

        assertEqualsExternalItem(expected.getMaintainer(), actual.getMaintainer());
        assertEqualsExternalItem(expected.getCreator(), actual.getCreator());
        assertEqualsExternalItemList(expected.getContributor(), actual.getContributor());
        assertEqualsDate(expected.getResourceCreatedDate(), actual.getResourceCreatedDate());
        assertEqualsDate(expected.getLastUpdate(), actual.getLastUpdate());
        assertEqualsInternationalString(expected.getConformsTo(), actual.getConformsTo());
        assertEqualsInternationalString(expected.getConformsToInternal(), actual.getConformsToInternal());

        assertEqualsExternalItemList(expected.getPublisher(), actual.getPublisher());
        assertEqualsExternalItemList(expected.getPublisherContributor(), actual.getPublisherContributor());
        assertEqualsExternalItemList(expected.getMediator(), actual.getMediator());
        assertEqualsDate(expected.getNewnessUntilDate(), actual.getNewnessUntilDate());

        assertEqualsRelatedResource(expected.getReplaces(), actual.getReplaces());
        assertEqualsRelatedResource(expected.getReplacesVersion(), actual.getReplacesVersion());
        assertEqualsRelatedResource(expected.getIsReplacedBy(), actual.getIsReplacedBy());
        assertEqualsRelatedResource(expected.getIsReplacedByVersion(), actual.getIsReplacedByVersion());
        assertEqualsRelatedResourceCollection(expected.getRequires(), actual.getRequires());
        assertEqualsRelatedResourceCollection(expected.getIsRequiredBy(), actual.getIsRequiredBy());
        assertEqualsRelatedResourceCollection(expected.getHasPart(), actual.getHasPart());
        assertEqualsRelatedResourceCollection(expected.getIsPartOf(), actual.getIsPartOf());

        assertEqualsExternalItem(expected.getRightsHolder(), actual.getRightsHolder());
        assertEqualsDate(expected.getCopyrightedDate(), actual.getCopyrightedDate());
        assertEqualsInternationalString(expected.getLicense(), actual.getLicense());
        assertEqualsInternationalString(expected.getAccessRights(), actual.getAccessRights());

        assertEqualsLifeCycleStatisticalResource(expected, actual);
    }

    protected static void assertEqualsLifeCycleStatisticalResource(LifeCycleStatisticalResource expected, LifeCycleStatisticalResource actual) {
        assertEquals(expected.getProcStatus(), actual.getProcStatus());

        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getCreationUser(), actual.getCreationUser());
        assertEquals(expected.getProductionValidationDate(), actual.getProductionValidationDate());
        assertEquals(expected.getProductionValidationUser(), actual.getProductionValidationUser());
        assertEquals(expected.getDiffusionValidationDate(), actual.getDiffusionValidationDate());
        assertEquals(expected.getDiffusionValidationUser(), actual.getDiffusionValidationUser());
        assertEquals(expected.getRejectValidationDate(), actual.getRejectValidationDate());
        assertEquals(expected.getRejectValidationUser(), actual.getRejectValidationUser());
        assertEquals(expected.getInternalPublicationDate(), actual.getInternalPublicationDate());
        assertEquals(expected.getInternalPublicationUser(), actual.getInternalPublicationUser());
        assertEquals(expected.getExternalPublicationDate(), actual.getExternalPublicationDate());
        assertEquals(expected.getExternalPublicationUser(), actual.getExternalPublicationUser());

        assertEquals(expected.getExternalPublicationFailed(), actual.getExternalPublicationFailed());
        assertEquals(expected.getExternalPublicationFailedDate(), actual.getExternalPublicationFailedDate());

        assertEqualsRelatedResource(expected.getIsReplacedByVersion(), actual.getIsReplacedByVersion());
        assertEqualsRelatedResource(expected.getReplacesVersion(), actual.getReplacesVersion());

        assertEqualsVersionableStatisticalResource(expected, actual);
    }

    protected static void assertEqualsVersionableStatisticalResource(VersionableStatisticalResource expected, VersionableStatisticalResource actual) {

        assertEquals(expected.getVersionLogic(), actual.getVersionLogic());
        assertEqualsDate(expected.getNextVersionDate(), actual.getNextVersionDate());
        assertEquals(expected.getNextVersion(), actual.getNextVersion());
        assertEqualsVersionRationaleTypeCollection(expected.getVersionRationaleTypes(), actual.getVersionRationaleTypes());
        assertEqualsInternationalString(expected.getVersionRationale(), actual.getVersionRationale());
        assertEquals(expected.getValidFrom(), actual.getValidFrom());
        assertEquals(expected.getValidTo(), actual.getValidTo());

        assertEqualsNameableStatisticalResource(expected, actual);
    }
    protected static void assertEqualsNameableStatisticalResource(NameableStatisticalResource expected, NameableStatisticalResource actual) {
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        assertEqualsInternationalString(expected.getDescription(), actual.getDescription());

        assertEqualsIdentifiableStatisticalResource(expected, actual);
    }

    public static void assertEqualsIdentifiableStatisticalResource(IdentifiableStatisticalResource expected, IdentifiableStatisticalResource actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUri(), actual.getUri());
        assertEquals(expected.getUrn(), actual.getUrn());

        assertEqualsStatisticalResource(expected, actual);
    }

    protected static void assertEqualsStatisticalResource(StatisticalResource expected, StatisticalResource actual) {

    }

    // -----------------------------------------------------------------
    // MAIN HERITANCE: DTO & DO
    // -----------------------------------------------------------------

    protected static void assertEqualsSiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource entity, SiemacMetadataStatisticalResourceDto dto, MapperEnum mapperEnum) {
        switch (mapperEnum) {
            case DO2DTO:
                assertEqualsExternalItem(entity.getLanguage(), dto.getLanguage());
                assertEqualsExternalItemCollectionMapper(entity.getLanguages(), dto.getLanguages());

                assertEqualsExternalItem(entity.getStatisticalOperation(), dto.getStatisticalOperation());
                assertEqualsExternalItemCollectionMapper(entity.getStatisticalOperationInstances(), dto.getStatisticalOperationInstances());

                assertEqualsInternationalString(entity.getSubtitle(), dto.getSubtitle());
                assertEqualsInternationalString(entity.getTitleAlternative(), dto.getTitleAlternative());
                assertEqualsInternationalString(entity.getAbstractLogic(), dto.getAbstractLogic());
                // TODO: keywords

                assertEquals(dto.getType(), entity.getType());

                assertEqualsExternalItem(entity.getMaintainer(), dto.getMaintainer());
                assertEqualsExternalItem(entity.getCreator(), dto.getCreator());
                assertEqualsExternalItemCollectionMapper(entity.getContributor(), dto.getContributor());
                assertEqualsDate(entity.getResourceCreatedDate(), dto.getResourceCreatedDate());
                assertEqualsDate(entity.getLastUpdate(), dto.getLastUpdate());
                assertEqualsInternationalString(entity.getConformsTo(), dto.getConformsTo());
                assertEqualsInternationalString(entity.getConformsToInternal(), dto.getConformsToInternal());

                assertEqualsRelatedResource(entity.getReplaces(), dto.getReplaces());
                assertEqualsRelatedResource(entity.getReplacesVersion(), dto.getReplacesVersion());
                assertEqualsRelatedResource(entity.getIsReplacedBy(), dto.getIsReplacedBy());
                assertEqualsRelatedResource(entity.getIsReplacedByVersion(), dto.getIsReplacedByVersion());
                assertEqualsRelatedResourceCollectionMapper(entity.getRequires(), dto.getRequires());
                assertEqualsRelatedResourceCollectionMapper(entity.getIsRequiredBy(), dto.getIsRequiredBy());
                assertEqualsRelatedResourceCollectionMapper(entity.getHasPart(), dto.getHasPart());
                assertEqualsRelatedResourceCollectionMapper(entity.getIsPartOf(), dto.getIsPartOf());

                assertEqualsExternalItem(entity.getRightsHolder(), dto.getRightsHolder());
                assertEqualsDate(entity.getCopyrightedDate(), dto.getCopyrightedDate());
                assertEqualsInternationalString(entity.getLicense(), dto.getLicense());
                assertEqualsInternationalString(entity.getAccessRights(), dto.getAccessRights());

                break;
            case DTO2DO:
                assertEqualsExternalItem(entity.getLanguage(), dto.getLanguage());
                assertEqualsExternalItemCollectionMapper(entity.getLanguages(), dto.getLanguages());

                assertEqualsExternalItemCollectionMapper(entity.getStatisticalOperationInstances(), dto.getStatisticalOperationInstances());

                assertEqualsInternationalString(entity.getSubtitle(), dto.getSubtitle());
                assertEqualsInternationalString(entity.getTitleAlternative(), dto.getTitleAlternative());
                assertEqualsInternationalString(entity.getAbstractLogic(), dto.getAbstractLogic());
                // TODO: keywords

                assertEqualsExternalItem(entity.getCreator(), dto.getCreator());
                assertEqualsExternalItemCollectionMapper(entity.getContributor(), dto.getContributor());
                assertEqualsInternationalString(entity.getConformsTo(), dto.getConformsTo());
                assertEqualsInternationalString(entity.getConformsToInternal(), dto.getConformsToInternal());

                assertEqualsRelatedResource(entity.getReplaces(), dto.getReplaces());
                assertEqualsRelatedResource(entity.getIsReplacedBy(), dto.getIsReplacedBy());

                assertEqualsExternalItem(entity.getRightsHolder(), dto.getRightsHolder());
                assertEqualsInternationalString(entity.getLicense(), dto.getLicense());
                assertEqualsInternationalString(entity.getAccessRights(), dto.getAccessRights());

                break;
        }
        assertEqualsLifeCycleStatisticalResource(entity, dto, mapperEnum);
    }
    protected static void assertEqualsLifeCycleStatisticalResource(LifeCycleStatisticalResource entity, LifeCycleStatisticalResourceDto dto, MapperEnum mapperEnum) {
        switch (mapperEnum) {
            case DO2DTO:
                assertEquals(entity.getProcStatus(), dto.getProcStatus());

                assertEqualsDate(entity.getCreationDate(), dto.getCreationDate());
                assertEquals(entity.getCreationUser(), dto.getCreationUser());
                assertEqualsDate(entity.getProductionValidationDate(), dto.getProductionValidationDate());
                assertEquals(entity.getProductionValidationUser(), dto.getProductionValidationUser());
                assertEqualsDate(entity.getDiffusionValidationDate(), dto.getDiffusionValidationDate());
                assertEquals(entity.getDiffusionValidationUser(), dto.getDiffusionValidationUser());
                assertEqualsDate(entity.getRejectValidationDate(), dto.getRejectValidationDate());
                assertEquals(entity.getRejectValidationUser(), dto.getRejectValidationUser());
                assertEqualsDate(entity.getInternalPublicationDate(), dto.getInternalPublicationDate());
                assertEquals(entity.getInternalPublicationUser(), dto.getInternalPublicationUser());
                assertEqualsDate(entity.getExternalPublicationDate(), dto.getExternalPublicationDate());
                assertEquals(entity.getExternalPublicationUser(), dto.getExternalPublicationUser());

                assertEquals(entity.getExternalPublicationFailed(), dto.getExternalPublicationFailed());
                assertEqualsDate(entity.getExternalPublicationFailedDate(), dto.getExternalPublicationFailedDate());

                assertEqualsRelatedResource(entity.getIsReplacedByVersion(), dto.getIsReplacedByVersion());
                assertEqualsRelatedResource(entity.getReplacesVersion(), dto.getReplacesVersion());
                break;
        }
        assertEqualsVersionableStatisticalResource(entity, dto, mapperEnum);
    }
    protected static void assertEqualsVersionableStatisticalResource(VersionableStatisticalResource entity, VersionableStatisticalResourceDto dto, MapperEnum mapperEnum) {
        switch (mapperEnum) {
            case DO2DTO:
                assertEquals(entity.getVersionLogic(), dto.getVersionLogic());
                assertEqualsDate(entity.getNextVersionDate(), dto.getNextVersionDate());
                assertEquals(entity.getValidFrom(), dto.getValidFrom());
                assertEquals(entity.getValidTo(), dto.getValidTo());
                assertEqualsVersionRationaleTypeCollectionMapper(entity.getVersionRationaleTypes(), dto.getVersionRationaleTypes());
                assertEqualsInternationalString(entity.getVersionRationale(), dto.getVersionRationale());
                assertEquals(entity.getNextVersion(), dto.getNextVersion());
                break;
            case DTO2DO:
                assertEquals(entity.getNextVersion(), dto.getNextVersion());
                if (StatisticalResourceNextVersionEnum.SCHEDULED_UPDATE.equals(entity.getNextVersionDate())) {
                    assertEqualsDate(entity.getNextVersionDate(), dto.getNextVersionDate());
                }
                assertEqualsInternationalString(entity.getVersionRationale(), dto.getVersionRationale());
                assertEqualsVersionRationaleTypeCollectionMapper(entity.getVersionRationaleTypes(), dto.getVersionRationaleTypes());
                break;
        }
        assertEqualsNameableStatisticalResource(entity, dto, mapperEnum);
    }

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
        if (MapperEnum.DO2DTO.equals(mapperEnum)) {
            assertEquals(entity.getVersion(), dto.getOptimisticLockingVersion());
        }
    }

    // -----------------------------------------------------------------
    // STATISTIC OFFICIALITY: DO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsStatisticOfficiality(StatisticOfficiality expected, StatisticOfficiality actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getKey(), actual.getKey());
        assertEquals(expected.getUuid(), actual.getKey());
        assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
    }

    // -----------------------------------------------------------------
    // STATISTIC OFFICIALITY: DTO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsStatisticOfficiality(StatisticOfficiality expected, StatisticOfficialityDto actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
    }

    // -----------------------------------------------------------------
    // STATISTIC OFFICIALITY: DO & DTO
    // -----------------------------------------------------------------
    public static void assertEqualsStatisticOfficiality(StatisticOfficialityDto expected, StatisticOfficiality actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getIdentifier(), actual.getIdentifier());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUuid(), actual.getUuid());
        assertEqualsInternationalString(actual.getDescription(), expected.getDescription());
    }

    // -----------------------------------------------------------------
    // VERSION RATIONALE TYPE: DO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsVersionRationaleType(VersionRationaleType expected, VersionRationaleType actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getValue(), actual.getValue());
    }

    public static void assertEqualsVersionRationaleTypeCollection(Collection<VersionRationaleType> expected, Collection<VersionRationaleType> actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.size(), actual.size());

        for (VersionRationaleType expec : expected) {
            boolean found = false;
            for (VersionRationaleType actualRes : actual) {
                if (actualRes.getValue().equals(expec.getValue())) {
                    found = true;
                }
            }
            if (!found) {
                Assert.fail("Found element in expected collection which is not contained in actual collection");
            }
        }
    }

    // -----------------------------------------------------------------
    // VERSION RATIONALE TYPE: DTO & DO
    // -----------------------------------------------------------------
    public static void assertEqualsVersionRationaleType(VersionRationaleType expected, VersionRationaleTypeDto actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getValue(), actual.getValue());
    }

    public static void assertEqualsVersionRationaleTypeCollectionMapper(Collection<VersionRationaleType> entities, Collection<VersionRationaleTypeDto> dtos) {

        assertEqualsNullability(entities, dtos);
        if (entities == null) {
            assertNull(dtos);
        }
        assertEquals(entities.size(), dtos.size());
        for (VersionRationaleType entity : entities) {
            boolean found = false;
            Iterator<VersionRationaleTypeDto> itDto = dtos.iterator();
            while (itDto.hasNext() && !found) {
                VersionRationaleTypeDto dto = itDto.next();
                found = dto.getValue().equals(entity.getValue());
            }
            if (!found) {
                Assert.fail("Not equal collections");
            }
        }
    }

    // -----------------------------------------------------------------
    // VERSION RATIONALE TYPE: DO & DTO
    // -----------------------------------------------------------------
    public static void assertEqualsVersionRationaleType(VersionRationaleTypeDto expected, VersionRationaleType actual) {
        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }
        assertEquals(expected.getValue(), actual.getValue());
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
    // RELATED RESOURCE: DO & DO
    // -----------------------------------------------------------------

    public static void assertEqualsRelatedResource(RelatedResource expected, RelatedResource actual) {

        assertEqualsNullability(expected, actual);
        if (expected == null) {
            return;
        }

        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getUrn(), actual.getUrn());
        assertEquals(expected.getType(), actual.getType());
        assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
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
                if (actualRes.getUrn().equals(expec.getUrn())) {
                    found = true;
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

        assertEquals(entity.getCode(), dto.getCode());
        assertEquals(entity.getUrn(), dto.getUrn());
        assertEquals(entity.getType(), dto.getType());
        assertEqualsInternationalString(entity.getTitle(), dto.getTitle());
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

        assertEqualsNullability(entities, dtos);
        if (entities == null) {
            assertNull(dtos);
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
