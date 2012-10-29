package org.siemac.metamac.statistical.resources.core.base.mapper;

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
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;

@org.springframework.stereotype.Component("baseDo2DtoMapper")
public class BaseDo2DtoMapperImpl implements BaseDo2DtoMapper {

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------
    
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
    public void identifiableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        statisticalResourceDoToDto(source, target);

        target.setCode(source.getCode());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
    }

    @Override
    public void statisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }

        // Identity
        // Don't set the identity attributes because we choose the ones of the lastest element of the hierarchy
        
        // Auditable
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(dateDoToDto(source.getCreatedDate()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());
        target.setLastUpdated(dateDoToDto(source.getLastUpdated()));

        // Operation
        target.setOperation(externalItemDoToDto(source.getOperation()));
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public Set<ExternalItemDto> externalItemDoListToDtoList(Set<ExternalItem> source) {
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
