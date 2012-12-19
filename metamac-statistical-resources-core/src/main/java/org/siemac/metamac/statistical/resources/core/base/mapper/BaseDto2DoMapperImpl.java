package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.HashSet;
import java.util.Set;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("baseDto2DoMapper")
public class BaseDto2DoMapperImpl implements BaseDto2DoMapper {

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------

    @Override
    public NameableStatisticalResource nameableStatisticalResourceDtoToDo(NameableStatisticalResourceDto source, NameableStatisticalResource target) throws MetamacException {

        // Hierarchy
        identifiableStatisticalResourceDtoToDo(source, target);

        // Non modifiable after creation

        // Attributes modifiable
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), ServiceExceptionParameters.NAMEABLE_RESOURCE_TITLE));
        target.setDescription(internationalStringDtoToDo(source.getDescription(), target.getDescription(), ServiceExceptionParameters.NAMEABLE_RESOURCE_DESCRIPTION));

        return target;
    }

    @Override
    public void identifiableStatisticalResourceDtoToDo(IdentifiableStatisticalResourceDto source, IdentifiableStatisticalResource target) throws MetamacException {
        // Hierarchy
        statisticalResourceDtoToDo(source, target);

        // Non modifiable after creation

        // Attributes modifiable
        // TODO: EL code hay momentos en los que no se puede editar
        target.setCode(source.getCode());
        // TODO: La URI no debería estar como metadato porque la cumplimenta la API
        target.setUri(source.getUri());
        // TODO: La URN se cumplimenta en el servicio
        target.setUrn(source.getUrn());
    }

    @Override
    public void statisticalResourceDtoToDo(StatisticalResourceDto source, StatisticalResource target) throws MetamacException {
        target.setOperation(externalItemDtoToDo(source.getOperation(), target.getOperation(), ServiceExceptionParameters.STATISTICAL_RESOURCE_OPERATION));

    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------
    @Override
    public InternationalString internationalStringDtoToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {

        if (source == null) {
            if (target != null) {
                // Delete old entity
                internationalStringRepository.delete(target);
            }

            return null;
        }

        if (target == null) {
            target = new InternationalString();
        }

        if (ValidationUtils.isEmpty(source)) {
            throw new MetamacException(ServiceExceptionType.METADATA_REQUIRED, metadataName);
        }

        Set<LocalisedString> localisedStringEntities = localisedStringDtoToDo(source.getTexts(), target.getTexts(), target);
        target.getTexts().clear();
        target.getTexts().addAll(localisedStringEntities);

        return target;
    }

    private Set<LocalisedString> localisedStringDtoToDo(Set<LocalisedStringDto> sources, Set<LocalisedString> targets, InternationalString internationalStringTarget) {

        Set<LocalisedString> targetsBefore = targets;
        targets = new HashSet<LocalisedString>();

        for (LocalisedStringDto source : sources) {
            boolean existsBefore = false;
            for (LocalisedString target : targetsBefore) {
                if (source.getLocale().equals(target.getLocale())) {
                    targets.add(localisedStringDtoToDo(source, target, internationalStringTarget));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                targets.add(localisedStringDtoToDo(source, internationalStringTarget));
            }
        }
        return targets;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, InternationalString internationalStringTarget) {
        LocalisedString target = new LocalisedString();
        localisedStringDtoToDo(source, target, internationalStringTarget);
        return target;
    }

    private LocalisedString localisedStringDtoToDo(LocalisedStringDto source, LocalisedString target, InternationalString internationalStringTarget) {
        target.setLabel(source.getLabel());
        target.setLocale(source.getLocale());
        target.setIsUnmodifiable(source.getIsUnmodifiable());
        target.setInternationalString(internationalStringTarget);
        return target;
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------

    @Override
    public ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            // New
            target = new ExternalItem(source.getCode(), source.getUri(), source.getUrn(), source.getType());
        }
        target.setCode(source.getCode());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), metadataName + ServiceExceptionParameters.EXTERNAL_ITEM_TITLE));

        return target;
    }

}
