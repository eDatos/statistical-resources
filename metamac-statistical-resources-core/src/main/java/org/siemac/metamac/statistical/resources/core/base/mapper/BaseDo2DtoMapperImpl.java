package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;

@org.springframework.stereotype.Component("baseDo2DtoMapper")
public class BaseDo2DtoMapperImpl implements BaseDo2DtoMapper {

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------

    @Override
    public void siemacMetadataStatisticalResourceDoToDto(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        lifeCycleStatisticalResourceDoToDto(source, target);

        target.setLanguage(externalItemDoToDto(source.getLanguage()));
        target.getLanguages().clear();
        target.getLanguages().addAll(externalItemDoCollectionToDtoCollection(source.getLanguages()));

        target.setStatisticalOperation(externalItemDoToDto(source.getStatisticalOperation()));
        target.setStatisticalOperationInstance(externalItemDoToDto(source.getStatisticalOperationInstance()));

        target.setSubtitle(internationalStringDoToDto(source.getSubtitle()));
        target.setTitleAlternative(internationalStringDoToDto(source.getTitleAlternative()));
        target.setDescription(internationalStringDoToDto(source.getDescription()));
        target.setAbstractLogic(internationalStringDoToDto(source.getAbstractLogic()));

        target.setType(source.getType());

        target.setMaintainer(externalItemDoToDto(source.getMaintainer()));
        target.setCreator(externalItemDoToDto(source.getCreator()));
        target.getContributor().clear();
        target.getContributor().addAll(externalItemDoCollectionToDtoCollection(source.getContributor()));
        target.setResourceCreatedDate(dateDoToDto(source.getResourceCreatedDate()));
        target.setLastUpdate(dateDoToDto(source.getLastUpdate()));
        target.setConformsTo(internationalStringDoToDto(source.getConformsTo()));
        target.setConformsToInternal(internationalStringDoToDto(source.getConformsToInternal()));

        target.getPublisher().clear();
        target.getPublisher().addAll(externalItemDoCollectionToDtoCollection(source.getPublisher()));
        target.getPublisherContributor().clear();
        target.getPublisherContributor().addAll(externalItemDoCollectionToDtoCollection(source.getPublisherContributor()));
        target.getMediator().clear();
        target.getMediator().addAll(externalItemDoCollectionToDtoCollection(source.getMediator()));
        target.setNewnessUntilDate(dateDoToDto(source.getNewnessUntilDate()));

        target.setReplaces(relatedResourceDoToDto(source.getReplaces()));
        target.setReplacesVersion(relatedResourceDoToDto(source.getReplacesVersion()));
        target.setIsReplacedBy(relatedResourceDoToDto(source.getIsReplacedBy()));
        target.setIsReplacedByVersion(relatedResourceDoToDto(source.getIsReplacedByVersion()));

        target.getRequires().clear();
        target.getRequires().addAll(relatedResourceDoCollectionToDtoCollection(source.getRequires()));
        target.getIsRequiredBy().clear();
        target.getIsRequiredBy().addAll(relatedResourceDoCollectionToDtoCollection(source.getIsRequiredBy()));
        target.getHasPart().clear();
        target.getHasPart().addAll(relatedResourceDoCollectionToDtoCollection(source.getHasPart()));
        target.getIsPartOf().clear();
        target.getIsPartOf().addAll(relatedResourceDoCollectionToDtoCollection(source.getIsPartOf()));

        target.setRightsHolder(externalItemDoToDto(source.getRightsHolder()));
        target.setCopyrightedDate(dateDoToDto(source.getCopyrightedDate()));
        target.setLicense(internationalStringDoToDto(source.getLicense()));
        target.setAccessRights(internationalStringDoToDto(source.getAccessRights()));
    }
    @Override
    public void lifeCycleStatisticalResourceDoToDto(LifeCycleStatisticalResource source, LifeCycleStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        versionableStatisticalResourceDoToDto(source, target);

        target.setProcStatus(source.getProcStatus());
        target.setCreationDate(dateDoToDto(source.getCreationDate()));
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(dateDoToDto(source.getProductionValidationDate()));
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setDiffusionValidationDate(dateDoToDto(source.getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(dateDoToDto(source.getRejectValidationDate()));
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setInternalPublicationDate(dateDoToDto(source.getInternalPublicationDate()));
        target.setInternalPublicationUser(source.getInternalPublicationUser());
        target.setExternalPublicationDate(dateDoToDto(source.getExternalPublicationDate()));
        target.setExternalPublicationUser(source.getExternalPublicationUser());
        target.setExternalPublicationFailedDate(dateDoToDto(source.getExternalPublicationFailedDate()));

        target.setReplacesVersion(relatedResourceDoToDto(source.getReplacesVersion()));
        target.setIsReplacedByVersion(relatedResourceDoToDto(source.getIsReplacedByVersion()));
    }

    @Override
    public void versionableStatisticalResourceDoToDto(VersionableStatisticalResource source, VersionableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        nameableStatisticalResourceDoToDto(source, target);

        target.setVersionLogic(source.getVersionLogic());
        target.setNextVersionDate(dateDoToDto(source.getNextVersionDate()));
        target.setVersionRationale(internationalStringDoToDto(source.getVersionRationale()));
        target.setVersionRationaleType(source.getVersionRationaleType());
        target.setValidFrom(dateDoToDto(source.getValidFrom()));
        target.setValidTo(dateDoToDto(source.getValidTo()));
        target.setIsLastVersion(source.getIsLastVersion());
    }

    @Override
    public void nameableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        identifiableStatisticalResourceDoToDto(source, target);

        target.setTitle(internationalStringDoToDto(source.getTitle()));
        target.setDescription(internationalStringDoToDto(source.getDescription()));
    }

    @Override
    public void identifiableStatisticalResourceDoToDto(IdentifiableStatisticalResource source, IdentifiableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        statisticalResourceDoToDto(source, target);

        target.setCode(source.getCode());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
    }

    @Override
    public void statisticalResourceDoToDto(StatisticalResource source, StatisticalResourceDto target) {
        if (source == null) {
            return;
        }

        // Optimistic locking
        target.setOptimisticLockingVersion(source.getVersion());
        
        // Identity
        // Don't set the identity attributes because we choose the ones of the lastest element of the hierarchy

        // Auditable
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(dateDoToDto(source.getCreatedDate()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());
        target.setLastUpdated(dateDoToDto(source.getLastUpdated()));

    }

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public Collection<ExternalItemDto> externalItemDoCollectionToDtoCollection(Collection<ExternalItem> source) {
        HashSet<ExternalItemDto> result = new HashSet<ExternalItemDto>();
        for (ExternalItem externalItem : source) {
            result.add(externalItemDoToDto(externalItem));
        }
        return result;
    }

    @Override
    public ExternalItemDto externalItemDoToDto(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto(source.getCode(), source.getUri(), source.getUrn(), source.getType(), internationalStringDoToDto(source.getTitle()), source.getManagementAppUrl());
        return target;
    }

    @Override
    public Collection<RelatedResourceDto> relatedResourceDoCollectionToDtoCollection(Collection<RelatedResource> source) {
        HashSet<RelatedResourceDto> result = new HashSet<RelatedResourceDto>();
        for (RelatedResource resource : source) {
            result.add(relatedResourceDoToDto(resource));
        }
        return result;
    }

    @Override
    public RelatedResourceDto relatedResourceDoToDto(RelatedResource source) {
        if (source == null) {
            return null;
        }
        RelatedResourceDto target = new RelatedResourceDto();
        target.setCode(source.getCode());
        target.setTitle(internationalStringDoToDto(source.getTitle()));
        target.setType(source.getType());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());

        return target;
    }
    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Override
    public InternationalStringDto internationalStringDoToDto(InternationalString source) {
        if (source == null) {
            return null;
        }
        InternationalStringDto target = new InternationalStringDto();
        target.getTexts().addAll(localisedStringDoToDto(source.getTexts()));
        return target;
    }

    private Set<LocalisedStringDto> localisedStringDoToDto(Set<LocalisedString> sources) {
        Set<LocalisedStringDto> targets = new HashSet<LocalisedStringDto>();
        for (LocalisedString source : sources) {
            LocalisedStringDto target = new LocalisedStringDto();
            target.setLabel(source.getLabel());
            target.setLocale(source.getLocale());
            target.setIsUnmodifiable(source.getIsUnmodifiable());
            targets.add(target);
        }
        return targets;
    }

    // ------------------------------------------------------------
    // DATES
    // ------------------------------------------------------------

    @Override
    public Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

}
