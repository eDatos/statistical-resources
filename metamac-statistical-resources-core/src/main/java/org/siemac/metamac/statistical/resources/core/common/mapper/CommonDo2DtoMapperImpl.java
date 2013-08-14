package org.siemac.metamac.statistical.resources.core.common.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.utils.TypeExternalArtefactsEnumUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

@org.springframework.stereotype.Component("commonDo2DtoMapper")
public class CommonDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements CommonDo2DtoMapper {

    // ------------------------------------------------------------
    // TEMPORAL CODE
    // ------------------------------------------------------------

    @Override
    public Collection<TemporalCodeDto> temporalCodeDoCollectionToDtoCollection(Collection<TemporalCode> source) throws MetamacException {
        HashSet<TemporalCodeDto> result = new HashSet<TemporalCodeDto>();

        if (source != null) {
            for (TemporalCode temporalCode : source) {
                result.add(temporalCodeDoToDto(temporalCode));
            }
        }
        return result;
    }
    
    @Override
    public List<TemporalCodeDto> temporalCodeDoCollectionToDtoList(Collection<TemporalCode> source) throws MetamacException {
        List<TemporalCodeDto> result = new ArrayList<TemporalCodeDto>();
        
        if (source != null) {
            for (TemporalCode temporalCode : source) {
                result.add(temporalCodeDoToDto(temporalCode));
            }
        }
        return result;
    }

    @Override
    public TemporalCodeDto temporalCodeDoToDto(TemporalCode source) throws MetamacException {
        TemporalCodeDto target = new TemporalCodeDto();
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());

        return target;
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEM
    // ------------------------------------------------------------

    @Override
    public Collection<ExternalItemDto> externalItemDoCollectionToDtoCollection(Collection<ExternalItem> source) throws MetamacException {
        HashSet<ExternalItemDto> result = new HashSet<ExternalItemDto>();

        if (source != null) {
            for (ExternalItem externalItem : source) {
                result.add(externalItemDoToDto(externalItem));
            }
        }
        return result;
    }
    
    @Override
    public List<ExternalItemDto> externalItemDoCollectionToDtoList(Collection<ExternalItem> source) throws MetamacException {
        List<ExternalItemDto> result = new ArrayList<ExternalItemDto>();
        
        if (source != null) {
            for (ExternalItem externalItem : source) {
                result.add(externalItemDoToDto(externalItem));
            }
        }
        return result;
    }

    @Override
    public ExternalItemDto externalItemDoToDto(ExternalItem source) throws MetamacException {
        ExternalItemDto target = externalItemDoToDtoWithoutUrls(source);
        if (target != null) {
            if (TypeExternalArtefactsEnumUtils.isExternalItemOfCommonMetadataApp(source.getType())) {
                target = commonMetadataExternalItemDoToDto(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfSrmApp(source.getType())) {
                target = srmExternalItemDoToDto(source, target);
            } else if (TypeExternalArtefactsEnumUtils.isExternalItemOfStatisticalOperationsApp(source.getType())) {
                target = statisticalOperationsExternalItemDoToDto(source, target);
            } else {
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of externalItem not defined for externalItemDoToDto");
            }
        }

        return target;
    }

    private ExternalItemDto externalItemDoToDtoWithoutUrls(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItemDto target = new ExternalItemDto();
        target.setId(source.getId());
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setUri(source.getUri());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setType(source.getType());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(internationalStringDoToDto(source.getTitle()));
        return target;
    }

    private ExternalItemDto commonMetadataExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(commonMetadataExternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(commonMetadataInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItemDto srmExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(srmInternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(srmInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    private ExternalItemDto statisticalOperationsExternalItemDoToDto(ExternalItem source, ExternalItemDto target) throws MetamacException {
        target.setUri(statisticalOperationsInternalApiUrlDoToDto(source.getUri()));
        target.setManagementAppUrl(statisticalOperationsInternalWebAppUrlDoToDto(source.getManagementAppUrl()));
        return target;
    }

    @Override
    public Collection<RelatedResourceDto> relatedResourceDoCollectionToDtoCollection(Collection<RelatedResource> source) throws MetamacException {
        HashSet<RelatedResourceDto> result = new HashSet<RelatedResourceDto>();
        if (source != null) {
            for (RelatedResource resource : source) {
                result.add(relatedResourceDoToDto(resource));
            }
        }
        return result;
    }

    @Override
    public RelatedResourceDto relatedResourceDoToDto(RelatedResource source) throws MetamacException {
        if (source == null) {
            return null;
        }
        NameableStatisticalResource nameableResource = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(source);
        RelatedResourceDto target = new RelatedResourceDto();
        target.setCode(nameableResource.getCode());
        target.setTitle(internationalStringDoToDto(nameableResource.getTitle()));
        target.setType(source.getType());
        target.setUrn(nameableResource.getUrn());
        target.setStatisticalOperationUrn(nameableResource.getStatisticalOperation() != null ? nameableResource.getStatisticalOperation().getUrn() : null);
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
