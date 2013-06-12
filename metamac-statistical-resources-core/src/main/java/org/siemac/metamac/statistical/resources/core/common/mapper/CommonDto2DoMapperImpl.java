package org.siemac.metamac.statistical.resources.core.common.mapper;

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
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("commonDto2DoMapper")
public class CommonDto2DoMapperImpl extends BaseDto2DoMapperImpl implements CommonDto2DoMapper {

    @Autowired
    private InternationalStringRepository internationalStringRepository;

    @Autowired
    private ExternalItemRepository        externalItemRepository;

    @Autowired
    private RelatedResourceRepository     relatedResourceRepository;

    @Autowired
    private PublicationVersionRepository  publicationVersionRepository;

    @Autowired
    private DatasetVersionRepository      datasetVersionRepository;

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

        checkExistsLocaleInDefaultLanguage(target.getTexts(), metadataName);

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

    private void checkExistsLocaleInDefaultLanguage(Set<LocalisedString> targets, String metadataName) throws MetamacException {
        boolean existsDefaultLanguage = false;
        for (LocalisedString localisedString : targets) {
            if (localisedString.getLocale().equals(configurationService.retrieveLanguageDefault())) {
                existsDefaultLanguage = true;
                break;
            }
        }

        if (!existsDefaultLanguage) {
            throw new MetamacException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, metadataName);
        }
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------

    @Override
    public ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        target = externalItemDtoToDoWithoutUrls(source, target, metadataName);

        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDtoToDo(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDtoToDo(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemDtoToDo(source, target);
            } else {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDtoToEntity");
            }
        }

        return target;
    }

    private ExternalItem externalItemDtoToDoWithoutUrls(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException {
        if (source == null) {
            if (target != null) {
                // delete previous entity
                externalItemRepository.delete(target);
            }
            return null;
        }

        if (target == null) {
            // We set uri because it's required but the information is incorrect because it includes the base
            target = new ExternalItem(source.getCode(), source.getUri(), source.getUrn(), source.getUrnInternal(), source.getType(), internationalStringDtoToDo(source.getTitle(), null, metadataName));
        } else {
            target.setCode(source.getCode());
            target.setUrn(source.getUrn());
            target.setUrnInternal(source.getUrnInternal());
            target.setType(source.getType());
            target.setTitle(internationalStringDtoToDo(source.getTitle(), target.getTitle(), metadataName));
        }

        return target;
    }

    private ExternalItem commonMetadataExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(commonMetadataExternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItem srmExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(srmInternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItem statisticalOperationsExternalItemDtoToDo(ExternalItemDto source, ExternalItem target) throws MetamacException {
        target.setUri(statisticalOperationsInternalApiUrlDtoToDo(source.getUri()));
        target.setManagementAppUrl(statisticalOperationsInternalWebAppUrlDtoToDo(source.getManagementAppUrl()));
        return target;
    }

    @Override
    public List<ExternalItem> externalItemDtoListToDoList(List<ExternalItemDto> sources, List<ExternalItem> targets, String metadataName) throws MetamacException {
        if (targets == null) {
            targets = new ArrayList<ExternalItem>();
        }

        List<ExternalItem> targetsBefore = targets;
        List<ExternalItem> newTargets = new ArrayList<ExternalItem>();

        if (sources != null) {
            for (ExternalItemDto source : sources) {
                boolean existsBefore = false;
                for (ExternalItem target : targetsBefore) {
                    if (source.getId() != null && target.getId().equals(source.getId())) {
                        newTargets.add(externalItemDtoToDo(source, target, metadataName));
                        existsBefore = true;
                        break;
                    }
                }
                if (!existsBefore) {
                    newTargets.add(externalItemDtoToDo(source, null, metadataName));
                }
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

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        StatisticalResourcesValidationUtils.checkMetadataRequired(source, metadataName, exceptionItems);
        ExceptionUtils.throwIfException(exceptionItems);

        if (target == null) {
            // New
            target = new RelatedResource(source.getType());
        }

        target.setType(source.getType());
        setCorrespondingResourceRelatedBasedOnType(target, source);

        return target;
    }

    private void setCorrespondingResourceRelatedBasedOnType(RelatedResource target, RelatedResourceDto source) throws MetamacException {
        switch (source.getType()) {
            case DATASET_VERSION:
                target.setDatasetVersion(datasetVersionRepository.retrieveByUrn(source.getUrn()));
                break;
            case PUBLICATION_VERSION:
                target.setPublicationVersion(publicationVersionRepository.retrieveByUrn(source.getUrn()));
                break;
        }
    }

    @Override
    public List<RelatedResource> relatedResourceDtoListToDoList(List<RelatedResourceDto> sources, List<RelatedResource> targets, String metadataName) throws MetamacException {
        if (targets == null) {
            targets = new ArrayList<RelatedResource>();
        }

        List<RelatedResource> targetsBefore = targets;
        List<RelatedResource> newTargets = new ArrayList<RelatedResource>();

        if (sources != null) {
            for (RelatedResourceDto source : sources) {
                boolean existsBefore = false;
                for (RelatedResource target : targetsBefore) {
                    if (relatedResourceDtoRepresentsSameAsEntity(source, target)) {
                        newTargets.add(relatedResourceDtoToDo(source, target, metadataName));
                        existsBefore = true;
                        break;
                    }
                }
                if (!existsBefore) {
                    newTargets.add(relatedResourceDtoToDo(source, null, metadataName));
                }
            }
        }

        // Delete missing
        for (RelatedResource oldTarget : targetsBefore) {
            boolean found = false;
            for (RelatedResource newTarget : newTargets) {
                found = found || relatedResourcesRepresentSameEntity(oldTarget, newTarget);
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

    private boolean relatedResourceDtoRepresentsSameAsEntity(RelatedResourceDto source, RelatedResource target) {
        if (source.getType().equals(target.getType())) {
            switch (source.getType()) {
                case DATASET_VERSION:
                    if (source.getUrn().equals(target.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn()))
                        return true;
                    break;
                case PUBLICATION_VERSION:
                    if (source.getUrn().equals(target.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn()))
                        return true;
                    break;
            }
        }
        return false;
    }

    private boolean relatedResourcesRepresentSameEntity(RelatedResource source, RelatedResource target) {
        if (source.getType().equals(target.getType())) {
            switch (source.getType()) {
                case DATASET_VERSION:
                    if (source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn().equals(target.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn()))
                        return true;
                    break;
                case PUBLICATION_VERSION:
                    if (source.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn().equals(target.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn()))
                        return true;
                    break;
            }
        }
        return false;
    }

    @Override
    public DateTime dateDtoToDo(Date source) {
        if (source == null) {
            return null;
        }
        return new DateTime(source);
    }
}
