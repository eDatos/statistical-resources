package org.siemac.metamac.statistical.resources.core.common.mapper;

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
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;

@org.springframework.stereotype.Component("commonDo2DtoMapper")
public class CommonDo2DtoMapperImpl implements CommonDo2DtoMapper {

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public Collection<ExternalItemDto> externalItemDoCollectionToDtoCollection(Collection<ExternalItem> source) {
        HashSet<ExternalItemDto> result = new HashSet<ExternalItemDto>();

        if (source != null) {
            for (ExternalItem externalItem : source) {
                result.add(externalItemDoToDto(externalItem));
            }
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
        Long resourceId = RelatedResourceUtils.retrieveResourceIdLinkedToRelatedResource(source);
        NameableStatisticalResource nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);
        RelatedResourceDto target = new RelatedResourceDto();
        target.setRelatedId(resourceId);
        target.setCode(nameableResource.getCode());
        target.setTitle(internationalStringDoToDto(nameableResource.getTitle()));
        target.setType(source.getType());
        target.setUrn(nameableResource.getUrn());

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
