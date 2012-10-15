package org.siemac.metamac.statistical.resources.core.mapper;

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
import org.siemac.metamac.statistical.resources.core.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;

@org.springframework.stereotype.Component("do2DtoMapper")
public class Do2DtoMapperImpl implements Do2DtoMapper {

    // ------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------

    @Override
    public QueryDto queryDoToDto(Query source) {
        if (source == null) {
            return null;
        }
        QueryDto target = new QueryDto();
        queryDoToDto(source, target);
        return target;
    }

    private QueryDto queryDoToDto(Query source, QueryDto target) {
        if (source == null) {
            return null;
        }

        nameableStatisticalResourceDoToDto(source.getNameableStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        return target;
    }

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------

    private void nameableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        identifiableStatisticalResourceDoToDto(source, target);

        target.setTitle(internationalStringDoToDto(source.getTitle()));
        target.setDescription(internationalStringDoToDto(source.getDescription()));
    }

    private void identifiableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
        if (source == null) {
            return;
        }
        statisticalResourceDoToDto(source, target);

        target.setCode(source.getCode());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
    }

    private void statisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) {
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

    private Set<ExternalItemDto> externalItemDoListToDtoList(Set<ExternalItem> source) {
        HashSet<ExternalItemDto> result = new HashSet<ExternalItemDto>();
        for (ExternalItem externalItem : source) {
            result.add(externalItemDoToDto(externalItem));
        }
        return result;
    }

    private ExternalItemDto externalItemDoToDto(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto(source.getCode(), source.getUri(), source.getUrn(), source.getType(), internationalStringDoToDto(source.getTitle()), source.getManagementAppUrl());
        return target;
    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    private InternationalStringDto internationalStringDoToDto(InternationalString source) {
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

    private Date dateDoToDto(DateTime source) {
        if (source == null) {
            return null;
        }
        return source.toDate();
    }

}
