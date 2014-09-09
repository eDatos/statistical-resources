package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RelationshipDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.RepresentationDto;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.AttributeRelationshipTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.AttributeRepresentationTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.dataset.domain.DimensionTypeEnum;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemWebUtils;
import org.siemac.metamac.statistical_resources.rest.common.StatisticalResourcesRestConstants;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestMapperImpl implements RestMapper {

    @Autowired
    protected DatasetsDo2RestMapperV10 datasetsDo2RestMapperV10;

    @Override
    public List<DsdDimensionDto> buildDsdDimensionDtosFromDsdDimensions(List<DsdDimension> dsdDimensions) throws MetamacWebException {
        List<DsdDimensionDto> dsdDimensionDtos = new ArrayList<DsdDimensionDto>();
        for (int i = 0; i < dsdDimensions.size(); i++) {
            dsdDimensionDtos.add(buildDsdDimemsionDtoFromDsdDimension(i, dsdDimensions.get(i)));
        }
        return dsdDimensionDtos;
    }

    @Override
    public DsdDimensionDto buildDsdDimemsionDtoFromDsdDimension(int position, DsdDimension dsdDimension) throws MetamacWebException {
        DsdDimensionDto dsdDimensionDto = new DsdDimensionDto();
        dsdDimensionDto.setDimensionId(dsdDimension.getComponentId());
        dsdDimensionDto.setType(getDimensionType(dsdDimension));
        dsdDimensionDto.setPosition(position);
        dsdDimensionDto.setCodelistRepresentationUrn(dsdDimension.getCodelistRepresentationUrn());
        dsdDimensionDto.setConceptSchemeRepresentationUrn(dsdDimension.getConceptSchemeRepresentationUrn());
        return dsdDimensionDto;
    }

    @Override
    public List<DsdAttributeDto> buildDsdAttributeDtosFromDsdAttributes(List<DsdAttribute> dsdAttributes, Map<String, List<String>> dsdGgroupDimensions) throws MetamacWebException {
        List<DsdAttributeDto> dsdAttributeDtos = new ArrayList<DsdAttributeDto>();
        for (DsdAttribute dsdAttribute : dsdAttributes) {
            dsdAttributeDtos.add(buildDsdAttributeDtoFromDsdAttribute(dsdAttribute, dsdGgroupDimensions));
        }
        return dsdAttributeDtos;
    }

    @Override
    public DsdAttributeDto buildDsdAttributeDtoFromDsdAttribute(DsdAttribute dsdAttribute, Map<String, List<String>> dsdGgroupDimensions) throws MetamacWebException {
        DsdAttributeDto dsdAttributeDto = new DsdAttributeDto();
        dsdAttributeDto.setIdentifier(dsdAttribute.getComponentId());
        dsdAttributeDto.setAttributeRelationship(buildRelationShipDtoFromAttributeRelationship(dsdAttribute.getAttributeRelationship(), dsdGgroupDimensions));
        dsdAttributeDto.setAttributeRepresentation(buildRepresentationDtoFromDsdAttribute(dsdAttribute));
        return dsdAttributeDto;
    }

    @Override
    public List<ItemDto> buildItemDtosFromCodes(Codes codes) throws MetamacWebException {
        List<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (CodeResourceInternal codeResourceInternal : codes.getCodes()) {
            itemDtos.add(buildItemDto(codeResourceInternal, TypeExternalArtefactsEnum.CODE));
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> buildItemDtosFromConcepts(Concepts concepts) throws MetamacWebException {
        List<ItemDto> itemDtos = new ArrayList<ItemDto>();
        for (ItemResourceInternal conceptResourceInternal : concepts.getConcepts()) {
            itemDtos.add(buildItemDto(conceptResourceInternal, TypeExternalArtefactsEnum.CONCEPT));
        }
        return itemDtos;
    }

    private ItemDto buildItemDto(ItemResourceInternal itemResourceInternal, TypeExternalArtefactsEnum type) {
        ItemDto itemDto = new ItemDto();
        ExternalItemWebUtils.buildExternalItemDtoFromResource(itemDto, itemResourceInternal, type);
        itemDto.setItemParentUrn(itemResourceInternal.getParent());
        return itemDto;
    }

    private RelationshipDto buildRelationShipDtoFromAttributeRelationship(AttributeRelationship attributeRelationship, Map<String, List<String>> dsdGgroupDimensions) throws MetamacWebException {
        RelationshipDto relationshipDto = new RelationshipDto();
        relationshipDto.setRelationshipType(getTypeRelathionship(attributeRelationship));
        relationshipDto.setDimensions(attributeRelationship.getDimensions());
        relationshipDto.setGroup(attributeRelationship.getGroup());
        relationshipDto.setGroupDimensions(dsdGgroupDimensions.get(attributeRelationship.getGroup()));
        return relationshipDto;
    }

    private RepresentationDto buildRepresentationDtoFromDsdAttribute(DsdAttribute dsdAttribute) {
        RepresentationDto representationDto = new RepresentationDto();
        if (StringUtils.isNotBlank(dsdAttribute.getCodelistRepresentationUrn()) || StringUtils.isNotBlank(dsdAttribute.getConceptSchemeRepresentationUrn())) {
            representationDto.setRepresentationType(AttributeRepresentationTypeEnum.ENUMERATION);
        } else if (dsdAttribute.getTextFormatRepresentation() != null) {
            representationDto.setRepresentationType(AttributeRepresentationTypeEnum.TEXT_FORMAT);
        }
        representationDto.setCodelistRepresentationUrn(dsdAttribute.getCodelistRepresentationUrn());
        representationDto.setConceptSchemeRepresentationUrn(dsdAttribute.getConceptSchemeRepresentationUrn());
        return representationDto;
    }

    private AttributeRelationshipTypeEnum getTypeRelathionship(AttributeRelationship attributeRelationship) {
        if (StringUtils.isNotBlank(attributeRelationship.getPrimaryMeasure())) {
            return AttributeRelationshipTypeEnum.PRIMARY_MEASURE_RELATIONSHIP;
        }
        if (StringUtils.isNotBlank(attributeRelationship.getGroup())) {
            return AttributeRelationshipTypeEnum.GROUP_RELATIONSHIP;
        }
        if (attributeRelationship.getDimensions() != null && !attributeRelationship.getDimensions().isEmpty()) {
            return AttributeRelationshipTypeEnum.DIMENSION_RELATIONSHIP;
        }
        if (attributeRelationship.getNone() != null) {
            return AttributeRelationshipTypeEnum.NO_SPECIFIED_RELATIONSHIP;
        }
        return null;
    }

    private DimensionTypeEnum getDimensionType(DsdDimension dimension) {
        if (dimension.getType() != null) {
            switch (dimension.getType()) {
                case MEASURE:
                    return DimensionTypeEnum.MEASURE;
                case OTHER:
                    return DimensionTypeEnum.OTHER;
                case SPATIAL:
                    return DimensionTypeEnum.SPATIAL;
                case TEMPORAL:
                    return DimensionTypeEnum.TEMPORAL;
                default:
                    break;
            }
        }
        return null;
    }

    //
    // NOTIFICATIONS
    //

    @Override
    public ResourceInternal buildResourceInternalFromDatasetVersion(DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        ResourceInternal resourceInternal = new ResourceInternal();
        if (datasetVersionDto != null) {
            resourceInternal.setId(datasetVersionDto.getCode());
            resourceInternal.setUrn(datasetVersionDto.getUrn());
            resourceInternal.setKind(StatisticalResourcesRestConstants.KIND_DATASET);
            resourceInternal.setName(toInternationalString(datasetVersionDto.getTitle()));
            resourceInternal.setManagementAppLink(datasetsDo2RestMapperV10.toDatasetVersionManagementApplicationLink(datasetVersionDto));
            resourceInternal.setSelfLink(datasetsDo2RestMapperV10.toDatasetSelfLink(datasetVersionDto));
        }
        return resourceInternal;
    }

    private InternationalString toInternationalString(InternationalStringDto sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (LocalisedStringDto source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }
}
