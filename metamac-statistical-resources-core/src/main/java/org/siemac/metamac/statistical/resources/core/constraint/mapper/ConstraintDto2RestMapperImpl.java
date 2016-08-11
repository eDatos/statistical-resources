package org.siemac.metamac.statistical.resources.core.constraint.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ContentConstraint;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Key;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPartType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyParts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Keys;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Region;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionReference;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.RegionValueType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Regions;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2RestMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;

@org.springframework.stereotype.Component("constraintDto2RestMapper")
public class ConstraintDto2RestMapperImpl extends BaseDto2RestMapperImpl implements ConstraintDto2RestMapper {

    @Override
    public ContentConstraint constraintDtoTo(ContentConstraintDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        ContentConstraint target = new ContentConstraint();

        // Extension
        maintainableArtefactDtoToRest(source, target, ServiceExceptionParameters.CONTENT_CONSTRAINT);

        // "constraintAttachment",
        target.setConstraintAttachment(externalItemToExternalItemRest(source.getConstraintAttachment(), target.getConstraintAttachment(), ServiceExceptionParameters.CONTENT_CONSTRAINT_ATTACHMENT));

        // "regions": without regions in create constraint PUT message

        return target;
    }

    private Regions toRegions(List<RegionValueDto> sources) throws MetamacException {
        Regions target = new Regions();

        for (RegionValueDto regionValueDto : sources) {
            Region region = toRegion(regionValueDto);
            if (region != null) {
                target.getRegions().add(region);
            }
        }
        target.setTotal(BigInteger.valueOf(target.getRegions().size()));

        return target;
    }

    @Override
    public Region toRegion(RegionValueDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        Region target = new Region();

        target.setCode(source.getCode());
        target.setRegionValueType(RegionValueType.fromValue(source.getRegionValueTypeEnum().getName()));

        target.setKeys(toKeys(source.getKeys()));

        return target;
    }

    @Override
    public RegionReference toRegionReference(RegionValueDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        Region region = toRegion(source);

        RegionReference target = new RegionReference();
        target.setContentConstraintUrn(source.getContentConstraintUrn());
        target.setCode(region.getCode());
        target.setKeys(region.getKeys());
        target.setRegionValueType(region.getRegionValueType());

        return target;
    }

    private Keys toKeys(List<KeyValueDto> sources) {
        Keys target = new Keys();
        for (KeyValueDto sourceKeyValue : sources) {
            Key key = toKey(sourceKeyValue);
            if (key != null) {
                target.getKeies().add(key);
            }
        }
        target.setTotal(BigInteger.valueOf(target.getKeies().size()));

        return target;
    }

    private Key toKey(KeyValueDto source) {
        if (source == null) {
            return null;
        }

        Key target = new Key();

        target.setIncluded(BooleanUtils.toBoolean(source.getIncluded()));
        target.setKeyParts(toKeyParts(source.getParts()));

        return target;
    }

    private KeyParts toKeyParts(List<KeyPartDto> sources) {
        KeyParts target = new KeyParts();

        for (KeyPartDto sourceKeyPart : sources) {
            org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart targetKeyPart = toKeyPart(sourceKeyPart);
            if (targetKeyPart != null) {
                target.getKeyParts().add(targetKeyPart);
            }
        }
        target.setTotal(BigInteger.valueOf(target.getKeyParts().size()));

        return target;
    }

    private KeyPart toKeyPart(KeyPartDto source) {
        if (source == null) {
            return null;
        }

        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart keyPart = new org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart();
        keyPart.setIdentifier(source.getIdentifier());
        keyPart.setType(KeyPartType.fromValue(source.getType().getName()));
        keyPart.setValue(source.getValue());
        keyPart.setCascadeValues(BooleanUtils.toBoolean(source.getCascadeValues()));
        keyPart.setPosition(source.getPosition());

        keyPart.setBeforePeriod(source.getBeforePeriod());
        keyPart.setBeforePeriodInclusive(source.getBeforePeriodInclusive());

        keyPart.setAfterPeriod(source.getAfterPeriod());
        keyPart.setAfterPeriodInclusive(source.getAfterPeriodInclusive());

        keyPart.setStartPeriod(source.getStartPeriod());
        keyPart.setStartPeriodInclusive(source.getStartPeriodInclusive());

        keyPart.setEndPeriod(source.getEndPeriod());
        keyPart.setEndPeriodInclusive(source.getEndPeriodInclusive());

        return keyPart;
    }
}
