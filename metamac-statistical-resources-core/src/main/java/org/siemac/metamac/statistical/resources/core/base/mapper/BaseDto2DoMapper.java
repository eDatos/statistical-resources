package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;

public interface BaseDto2DoMapper {

    // Base hierachy
    public SiemacMetadataStatisticalResource siemacMetadataStatisticalResourceDtoToDo(SiemacMetadataStatisticalResourceDto source, SiemacMetadataStatisticalResource target, String metadataName)
            throws MetamacException;
    public LifeCycleStatisticalResource lifeCycleStatisticalResourceDtoToDo(LifeCycleStatisticalResourceDto source, LifeCycleStatisticalResource target, String metadataName) throws MetamacException;
    public VersionableStatisticalResource versionableStatisticalResourceDtoToDo(VersionableStatisticalResourceDto source, VersionableStatisticalResource target, String metadataName)
            throws MetamacException;
    public NameableStatisticalResource nameableStatisticalResourceDtoToDo(NameableStatisticalResourceDto source, NameableStatisticalResource target, String metadataName) throws MetamacException;
    public void identifiableStatisticalResourceDtoToDo(IdentifiableStatisticalResourceDto source, IdentifiableStatisticalResource target, String metadataName) throws MetamacException;
    public void statisticalResourceDtoToDo(StatisticalResourceDto source, StatisticalResource target, String metadataName) throws MetamacException;

    // International Strings
    public InternationalString internationalStringDtoToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException;

    // External Items
    public ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException;
    public List<ExternalItem> externalItemDtoListToDoList(List<ExternalItemDto> source, List<ExternalItem> target, String metadataName) throws MetamacException;

    // Related Resource
    public RelatedResource relatedResourceDtoToDo(RelatedResourceDto source, RelatedResource target, String metadataName) throws MetamacException;
    public List<RelatedResource> relatedResourceDtoListToDoList(List<RelatedResourceDto> sources, List<RelatedResource> targets, String metadataName) throws MetamacException;

}
