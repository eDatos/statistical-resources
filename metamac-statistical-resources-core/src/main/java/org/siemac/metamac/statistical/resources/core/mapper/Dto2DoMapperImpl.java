package org.siemac.metamac.statistical.resources.core.mapper;

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
import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.domain.Query;
import org.siemac.metamac.statistical.resources.core.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.QueryDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.exception.QueryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("dto2DoMapper")
public class Dto2DoMapperImpl implements Dto2DoMapper {

    @Autowired
    private QueryRepository               queryRepository;

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    // ------------------------------------------------------------
    // QUERIES
    // ------------------------------------------------------------

    @Override
    public Query queryDtoToDo(QueryDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        Query target = null;
        if (source.getId() == null) {
            target = new Query();
            target.setNameableStatisticalResource(new NameableStatisticalResource());
        } else {
            try {
                target = queryRepository.findById(source.getId());
            } catch (QueryNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.QUERY_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.QUERY)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getVersion());
        }

        queryDtoToDo(source, target);

        return target;
    }

    private Query queryDtoToDo(QueryDto source, Query target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.NAMEABLE_RESOURCE).build();
        }

        // Hierarchy
        nameableStatisticalResourceDtoToDo(source, target.getNameableStatisticalResource());
        
        // Non modifiable after creation

        // Attributes modifiable
        // TODO: Dataset

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        // TODO: Add update date
        // target.setUpdateDate(new DateTime());

        return target;

    }

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------

    private NameableStatisticalResource nameableStatisticalResourceDtoToDo(NameableStatisticalResourceDto source, NameableStatisticalResource target) throws MetamacException {

        // Hierarchy
        identifiableStatisticalResourceDtoToDo(source, target);
        
        // Non modifiable after creation

        // Attributes modifiable
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), ServiceExceptionParameters.NAMEABLE_RESOURCE_TITLE));
        target.setDescription(internationalStringDtoToDo(source.getDescription(), target.getDescription(), ServiceExceptionParameters.NAMEABLE_RESOURCE_DESCRIPTION));

        return target;
    }

    private void identifiableStatisticalResourceDtoToDo(IdentifiableStatisticalResourceDto source, IdentifiableStatisticalResource target) throws MetamacException {
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

    private void statisticalResourceDtoToDo(StatisticalResourceDto source, StatisticalResource target) throws MetamacException {
        target.setOperation(externalItemDtoToDo(source.getOperation(), target.getOperation(), ServiceExceptionParameters.STATISTICAL_RESOURCE_OPERATION));

    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------
    private InternationalString internationalStringDtoToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException {

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
        target.setInternationalString(internationalStringTarget);
        return target;
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------
    private ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
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
