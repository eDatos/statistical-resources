package org.siemac.metamac.statistical.resources.core.constraint.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Key;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyParts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Keys;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Region;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseRest2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintBasicDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueBasicDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.RegionValueTypeEnum;

public class ConstraintRest2DtoMapperImpl extends BaseRest2DtoMapperImpl implements ConstraintRest2DtoMapper {

    @Override
    public ContentConstraintDto toConstraintDto(ContentConstraint source) throws MetamacException {
        if (source == null) {
            return null;
        }

        ContentConstraintDto target = new ContentConstraintDto();

        // Extension
        maintainableArtefactRestToDto(source, target);

        target.setType(org.siemac.metamac.statistical.resources.core.enume.constraint.domain.ContentConstraintTypeEnum.valueOf(source.getType().value()));
        target.setConstraintAttachment(externalItemToExternalItemDto(source.getConstraintAttachment(), target.getConstraintAttachment()));

        if (source.getRegions() != null) {
            for (Region region : source.getRegions().getRegions()) {
                RegionValueBasicDto regionValueBasicDto = toRegionBasicDto(region);
                if (regionValueBasicDto != null) {
                    target.addRegion(regionValueBasicDto);
                }
            }
        }

        return target;
    }

    @Override
    public ContentConstraintBasicDto toConstraintBasicDto(ContentConstraint source) throws MetamacException {
        if (source == null) {
            return null;
        }

        ContentConstraintBasicDto target = new ContentConstraintBasicDto();

        // Extension
        maintainableArtefactRestToDto(source, target);

        target.setType(org.siemac.metamac.statistical.resources.core.enume.constraint.domain.ContentConstraintTypeEnum.valueOf(source.getType().value()));
        target.setConstraintAttachment(externalItemToExternalItemDto(source.getConstraintAttachment(), target.getConstraintAttachment()));

        return target;
    }

    private RegionValueBasicDto toRegionBasicDto(Region source) {
        if (source == null) {
            return null;
        }

        RegionValueBasicDto target = new RegionValueBasicDto();
        target.setCode(source.getCode());
        target.setRegionValueTypeEnum(RegionValueTypeEnum.valueOf(source.getCode()));

        return target;
    }

    @Override
    public RegionValueDto toRegionDto(RegionReference source) throws MetamacException {
        if (source == null) {
            return null;
        }

        RegionValueDto target = new RegionValueDto();

        target.setContentConstraintUrn(source.getContentConstraintUrn());
        target.setCode(source.getCode());
        target.setRegionValueTypeEnum(RegionValueTypeEnum.valueOf(source.getRegionValueType().value()));
        target.getKeys().addAll(toKeysDto(source.getKeys()));

        return target;
    }

    private List<KeyValueDto> toKeysDto(Keys source) {
        if (source == null) {
            return new ArrayList<KeyValueDto>();
        }

        List<KeyValueDto> target = new ArrayList<KeyValueDto>();

        for (Key key : source.getKeies()) {
            KeyValueDto keyElementDto = toKeyDto(key);
            if (keyElementDto != null) {
                target.add(keyElementDto);
            }
        }

        return target;
    }

    private KeyValueDto toKeyDto(Key source) {
        if (source == null) {
            return null;
        }

        KeyValueDto target = new KeyValueDto();

        target.setIncluded(BooleanUtils.toBoolean(source.isIncluded()));
        target.getParts().addAll(toKeyParts(source.getKeyParts()));

        return target;
    }

    private List<KeyPartDto> toKeyParts(KeyParts source) {
        if (source == null) {
            return new ArrayList<KeyPartDto>();
        }

        List<KeyPartDto> target = new ArrayList<KeyPartDto>(source.getKeyParts().size());

        for (KeyPart sourceKeyPart : source.getKeyParts()) {
            KeyPartDto keyPart = toKeyPart(sourceKeyPart);
            if (keyPart != null) {
                target.add(keyPart);
            }
        }

        return target;
    }

    private KeyPartDto toKeyPart(KeyPart source) {
        if (source == null) {
            return null;
        }

        KeyPartDto keyPart = new KeyPartDto();
        keyPart.setIdentifier(source.getIdentifier());
        keyPart.setValue(source.getValue());
        keyPart.setCascadeValues(BooleanUtils.toBoolean(source.isCascadeValues()));
        keyPart.setPosition(source.getPosition());

        keyPart.setBeforePeriod(source.getBeforePeriod());
        keyPart.setBeforePeriodInclusive(source.isBeforePeriodInclusive());

        keyPart.setAfterPeriod(source.getAfterPeriod());
        keyPart.setAfterPeriodInclusive(source.isAfterPeriodInclusive());

        keyPart.setStartPeriod(source.getStartPeriod());
        keyPart.setStartPeriodInclusive(source.isStartPeriodInclusive());

        keyPart.setEndPeriod(source.getEndPeriod());
        keyPart.setEndPeriodInclusive(source.isEndPeriodInclusive());

        return keyPart;
    }

    @Override
    public ExternalItemDto externalItemConstraintToExternalItemDto(ResourceInternal source) throws MetamacException {
        if (source == null) {
            return null;
        }

        ExternalItemDto externalItemDto = super.externalItemToExternalItemDto(source, null);

        if (!TypeExternalArtefactsEnum.CONTENT_CONSTRAINTS.equals(externalItemDto.getType())) {
            throw new UnsupportedOperationException("This method only is allowed for ContentConstraints type external items");
        }

        return externalItemDto;
    }

    @Override
    public List<ExternalItemDto> externalItemConstraintToExternalItemDto(List<ResourceInternal> sources) throws MetamacException {
        if (sources != null) {
            return new ArrayList<ExternalItemDto>();
        }

        List<ExternalItemDto> target = new ArrayList<ExternalItemDto>(sources.size());
        for (ResourceInternal source : sources) {
            target.add(externalItemConstraintToExternalItemDto(source));
        }

        return target;
    }
}
