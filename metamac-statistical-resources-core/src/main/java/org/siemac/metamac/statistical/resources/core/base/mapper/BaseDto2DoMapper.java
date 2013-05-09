package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;

public interface BaseDto2DoMapper extends CommonDto2DoMapper {

    // Base hierachy
    public SiemacMetadataStatisticalResource siemacMetadataStatisticalResourceDtoToDo(SiemacMetadataStatisticalResourceDto source, SiemacMetadataStatisticalResource target, String metadataName)
            throws MetamacException;
    public LifeCycleStatisticalResource lifeCycleStatisticalResourceDtoToDo(LifeCycleStatisticalResourceDto source, LifeCycleStatisticalResource target, String metadataName) throws MetamacException;
    public VersionableStatisticalResource versionableStatisticalResourceDtoToDo(VersionableStatisticalResourceDto source, VersionableStatisticalResource target, String metadataName)
            throws MetamacException;
    public NameableStatisticalResource nameableStatisticalResourceDtoToDo(NameableStatisticalResourceDto source, NameableStatisticalResource target, String metadataName) throws MetamacException;
    public void identifiableStatisticalResourceDtoToDo(IdentifiableStatisticalResourceDto source, IdentifiableStatisticalResource target, String metadataName) throws MetamacException;
    public void statisticalResourceDtoToDo(StatisticalResourceDto source, StatisticalResource target, String metadataName) throws MetamacException;

    // Version ratinale type
    public VersionRationaleType versionRationaleTypeDtoToDo(VersionRationaleTypeDto source, VersionRationaleType target, String metadataName) throws MetamacException;
    public List<VersionRationaleType> versionRationaleTypeDtoListToDoList(List<VersionRationaleTypeDto> sources, List<VersionRationaleType> targets, String metadataName) throws MetamacException;

}
