package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.Collection;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableRelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public interface BaseDo2DtoMapper extends CommonDo2DtoMapper {

    // Base Hierarchy
    public void siemacMetadataStatisticalResourceDoToDto(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResourceDto target) throws MetamacException;
    public void siemacMetadataStatisticalResourceDoToBaseDto(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResourceBaseDto target) throws MetamacException;

    public void lifeCycleStatisticalResourceDoToDto(LifeCycleStatisticalResource source, LifeCycleStatisticalResourceDto target) throws MetamacException;
    public void lifeCycleStatisticalResourceDoToBaseDto(LifeCycleStatisticalResource source, LifeCycleStatisticalResourceBaseDto target) throws MetamacException;

    public void versionableStatisticalResourceDoToDto(VersionableStatisticalResource source, VersionableStatisticalResourceDto target) throws MetamacException;
    public void versionableStatisticalResourceDoToBaseDto(VersionableStatisticalResource source, VersionableStatisticalResourceBaseDto target) throws MetamacException;

    public void nameableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target) throws MetamacException;
    public void nameableStatisticalResourceDoToBaseDto(NameableStatisticalResource source, NameableStatisticalResourceBaseDto target) throws MetamacException;

    public void identifiableStatisticalResourceDoToDto(IdentifiableStatisticalResource source, IdentifiableStatisticalResourceDto target) throws MetamacException;

    public void statisticalResourceDoToDto(StatisticalResource source, StatisticalResourceDto target) throws MetamacException;

    // Version rationale type
    public Collection<VersionRationaleTypeDto> versionRationaleTypeDoCollectionToDtoCollection(Collection<VersionRationaleType> source);
    public VersionRationaleTypeDto versionRationaleTypeDoToDto(VersionRationaleType source);
    public RelatedResourceDto lifecycleStatisticalResourceDoToRelatedResourceDto(LifeCycleStatisticalResource source, TypeRelatedResourceEnum type);
    public VersionableRelatedResourceDto lifecycleStatisticalResourceDoToVersionableRelatedResourceDto(LifeCycleStatisticalResource source, TypeRelatedResourceEnum type);
}
