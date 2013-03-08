package org.siemac.metamac.statistical.resources.core.base.mapper;

import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
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
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleTypeRepository;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("baseDto2DoMapper")
public class BaseDto2DoMapperImpl implements BaseDto2DoMapper {

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    @Autowired
    private RelatedResourceRepository     relatedResourceRepository;
    
    @Autowired
    private VersionRationaleTypeRepository versionRationaleTypeRepository;    

    // ------------------------------------------------------------
    // BASE HIERARCHY
    // ------------------------------------------------------------

    @Override
    public SiemacMetadataStatisticalResource siemacMetadataStatisticalResourceDtoToDo(SiemacMetadataStatisticalResourceDto source, SiemacMetadataStatisticalResource target, String metadataName)
            throws MetamacException {
        // Hierarchy
        lifeCycleStatisticalResourceDtoToDo(source, target, metadataName);

        target.setLanguage(externalItemDtoToDo(source.getLanguage(), target.getLanguage(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGE)));
        externalItemDtoListToDoList(source.getLanguages(), target.getLanguages(), addParameter(metadataName, ServiceExceptionSingleParameters.LANGUAGES));

        target.setStatisticalOperation(externalItemDtoToDo(source.getStatisticalOperation(), target.getStatisticalOperation(), metadataName + ServiceExceptionSingleParameters.STATISTICAL_OPERATION));
        target.setStatisticalOperationInstance(externalItemDtoToDo(source.getStatisticalOperationInstance(), target.getStatisticalOperationInstance(), metadataName
                + ServiceExceptionSingleParameters.STATISTICAL_OPERATION_INSTANCE));

        target.setSubtitle(internationalStringDtoToDo(source.getSubtitle(), target.getSubtitle(), addParameter(metadataName, ServiceExceptionSingleParameters.SUBTITLE)));
        target.setTitleAlternative(internationalStringDtoToDo(source.getTitleAlternative(), target.getTitleAlternative(),
                addParameter(metadataName, ServiceExceptionSingleParameters.TITLE_ALTERNATIVE)));
        target.setAbstractLogic(internationalStringDtoToDo(source.getAbstractLogic(), target.getAbstractLogic(), addParameter(metadataName, ServiceExceptionSingleParameters.ABSTRACT_LOGIC)));

        // TODO: keywords
        target.setMaintainer(externalItemDtoToDo(source.getMaintainer(), target.getMaintainer(), addParameter(metadataName, ServiceExceptionSingleParameters.MAINTAINER)));
        target.setCreator(externalItemDtoToDo(source.getCreator(), target.getCreator(), addParameter(metadataName, ServiceExceptionSingleParameters.CREATOR)));
        externalItemDtoListToDoList(source.getContributor(), target.getContributor(), addParameter(metadataName, ServiceExceptionSingleParameters.CONTRIBUTOR));
        target.setConformsTo(internationalStringDtoToDo(source.getConformsTo(), target.getConformsTo(), addParameter(metadataName, ServiceExceptionSingleParameters.CONFORMS_TO)));
        target.setConformsToInternal(internationalStringDtoToDo(source.getConformsToInternal(), target.getConformsToInternal(),
                addParameter(metadataName, ServiceExceptionSingleParameters.CONFORMS_TO_INTERNAL)));

        externalItemDtoListToDoList(source.getPublisher(), target.getPublisher(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER));
        externalItemDtoListToDoList(source.getPublisherContributor(), target.getPublisherContributor(), addParameter(metadataName, ServiceExceptionSingleParameters.PUBLISHER_CONTRIBUTOR));
        externalItemDtoListToDoList(source.getMediator(), target.getMediator(), addParameter(metadataName, ServiceExceptionSingleParameters.MEDIATOR));
        target.setNewnessUntilDate(dateDtoToDo(source.getNewnessUntilDate()));

        target.setReplaces(relatedResourceDtoToDo(source.getReplaces(), target.getReplaces(), addParameter(metadataName, ServiceExceptionSingleParameters.REPLACES)));
        target.setIsReplacedBy(relatedResourceDtoToDo(source.getIsReplacedBy(), target.getIsReplacedBy(), addParameter(metadataName, ServiceExceptionSingleParameters.IS_REPLACED_BY)));

        target.setRightsHolder(externalItemDtoToDo(source.getRightsHolder(), target.getRightsHolder(), addParameter(metadataName, ServiceExceptionSingleParameters.RIGHTS_HOLDER)));
        target.setLicense(internationalStringDtoToDo(source.getLicense(), target.getLicense(), addParameter(metadataName, ServiceExceptionSingleParameters.LICENSE)));
        target.setAccessRights(internationalStringDtoToDo(source.getAccessRights(), target.getAccessRights(), addParameter(metadataName, ServiceExceptionSingleParameters.ACCESS_RIGHTS)));

        return target;
    }

    @Override
    public LifeCycleStatisticalResource lifeCycleStatisticalResourceDtoToDo(LifeCycleStatisticalResourceDto source, LifeCycleStatisticalResource target, String metadataName) throws MetamacException {
        // Hierarchy
        versionableStatisticalResourceDtoToDo(source, target, metadataName);

        // All attributes are automatic, non modifiable

        return target;
    }

    @Override
    public VersionableStatisticalResource versionableStatisticalResourceDtoToDo(VersionableStatisticalResourceDto source, VersionableStatisticalResource target, String metadataName)
            throws MetamacException {
        // Hierarchy
        nameableStatisticalResourceDtoToDo(source, target, metadataName);

        // Non modifiable: versionLogic, versionDate

        // Attributes modifiable
        target.setNextVersionDate(CoreCommonUtil.transformDateToDateTime(source.getNextVersionDate()));
        target.setVersionRationale(internationalStringDtoToDo(source.getVersionRationale(), target.getVersionRationale(),
                addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE)));
        versionRationaleTypeDtoListToDoList(source.getVersionRationaleTypes(), target.getVersionRationaleTypes(), 
                addParameter(metadataName, ServiceExceptionSingleParameters.VERSION_RATIONALE_TYPES));
        target.setNextVersion(source.getNextVersion());

        return target;
    }

    @Override
    public NameableStatisticalResource nameableStatisticalResourceDtoToDo(NameableStatisticalResourceDto source, NameableStatisticalResource target, String metadataName) throws MetamacException {

        // Hierarchy
        identifiableStatisticalResourceDtoToDo(source, target, metadataName);

        // Non modifiable after creation

        // Attributes modifiable
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE)));
        target.setDescription(internationalStringDtoToDo(source.getDescription(), target.getDescription(), addParameter(metadataName, ServiceExceptionSingleParameters.DESCRIPTION)));

        return target;
    }

    @Override
    public void identifiableStatisticalResourceDtoToDo(IdentifiableStatisticalResourceDto source, IdentifiableStatisticalResource target, String metadataName) throws MetamacException {
        // Hierarchy
        statisticalResourceDtoToDo(source, target, metadataName);

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
    public void statisticalResourceDtoToDo(StatisticalResourceDto source, StatisticalResource target, String metadataName) throws MetamacException {
        if (target.getId() != null) {
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getOptimisticLockingVersion());
        }

        // Optimistic locking: Update "update date" attribute to force update to root entity, to increment "version" attribute
        target.setUpdateDate(new DateTime());
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
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE)));

        return target;
    }

    @Override
    public List<ExternalItem> externalItemDtoListToDoList(List<ExternalItemDto> sources, List<ExternalItem> targets, String metadataName) throws MetamacException {
        List<ExternalItem> targetsBefore = targets;
        List<ExternalItem> newTargets = new ArrayList<ExternalItem>();

        for (ExternalItemDto source : sources) {
            boolean existsBefore = false;
            for (ExternalItem target : targetsBefore) {
                if (source.getUrn().equals(target.getUrn())) {
                    newTargets.add(externalItemDtoToDo(source, target, metadataName));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                newTargets.add(externalItemDtoToDo(source, null, metadataName));
            }
        }

        // Delete missing
        for (ExternalItem oldTarget : targetsBefore) {
            boolean found = false;
            for (ExternalItem newTarget : newTargets) {
                found = found || (oldTarget.getUrn().equals(newTarget.getUrn()));
            }
            if (!found) {
                // Delete
                externalItemDtoToDo(null, oldTarget, metadataName);
            }
        }

        targets.clear();
        for (ExternalItem target : newTargets) {
            targets.add(target);
        }

        return targets;
    }

    // ------------------------------------------------------------
    // RELATED RESOURCES
    // ------------------------------------------------------------

    @Override
    public RelatedResource relatedResourceDtoToDo(RelatedResourceDto source, RelatedResource target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                relatedResourceRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            // New
            target = new RelatedResource(source.getCode(), source.getUri(), source.getUrn(), source.getType());
        }
        target.setCode(source.getCode());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setType(source.getType());
        target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), addParameter(metadataName, ServiceExceptionSingleParameters.TITLE)));

        return target;
    }

    @Override
    public List<RelatedResource> relatedResourceDtoListToDoList(List<RelatedResourceDto> sources, List<RelatedResource> targets, String metadataName) throws MetamacException {
        List<RelatedResource> targetsBefore = targets;
        List<RelatedResource> newTargets = new ArrayList<RelatedResource>();

        for (RelatedResourceDto source : sources) {
            boolean existsBefore = false;
            for (RelatedResource target : targetsBefore) {
                if (source.getUrn().equals(target.getUrn())) {
                    newTargets.add(relatedResourceDtoToDo(source, target, metadataName));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                newTargets.add(relatedResourceDtoToDo(source, null, metadataName));
            }
        }

        // Delete missing
        for (RelatedResource oldTarget : targetsBefore) {
            boolean found = false;
            for (RelatedResource newTarget : newTargets) {
                found = found || (oldTarget.getUrn().equals(newTarget.getUrn()));
            }
            if (!found) {
                // Delete
                relatedResourceDtoToDo(null, oldTarget, metadataName);
            }
        }

        targets.clear();
        for (RelatedResource target : newTargets) {
            targets.add(target);
        }

        return targets;
    }
    
    
    @Override
    public VersionRationaleType versionRationaleTypeDtoToDo(VersionRationaleTypeDto source, VersionRationaleType target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                versionRationaleTypeRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            target = new VersionRationaleType(source.getValue());
        }
        return target;
    }

    @Override
    public List<VersionRationaleType> versionRationaleTypeDtoListToDoList(List<VersionRationaleTypeDto> sources, List<VersionRationaleType> targets, String metadataName) throws MetamacException {
        List<VersionRationaleType> targetsBefore = targets;
        List<VersionRationaleType> newTargets = new ArrayList<VersionRationaleType>();

        for (VersionRationaleTypeDto source : sources) {
            boolean existsBefore = false;
            for (VersionRationaleType target : targetsBefore) {
                if (source.getValue().equals(target.getValue())) {
                    newTargets.add(versionRationaleTypeDtoToDo(source, target, metadataName));
                    existsBefore = true;
                    break;
                }
            }
            if (!existsBefore) {
                newTargets.add(versionRationaleTypeDtoToDo(source, null, metadataName));
            }
        }

        // Delete missing
        for (VersionRationaleType oldTarget : targetsBefore) {
            boolean found = false;
            for (VersionRationaleType newTarget : newTargets) {
                found = found || (oldTarget.getValue().equals(newTarget.getValue()));
            }
            if (!found) {
                // Delete
                versionRationaleTypeDtoToDo(null, oldTarget, metadataName);
            }
        }

        targets.clear();
        for (VersionRationaleType target : newTargets) {
            targets.add(target);
        }

        return targets;
    }
    
    
    private DateTime dateDtoToDo(Date source) {
        if (source == null) {
            return null;
        }
        return new DateTime(source);
    }
}
