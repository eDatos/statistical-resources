package org.siemac.metamac.statistical.resources.core.common.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;

public interface CommonDto2DoMapper extends BaseDto2DoMapper {

    // International Strings
    public InternationalString internationalStringDtoToDo(InternationalStringDto source, InternationalString target, String metadataName) throws MetamacException;

    // External Items
    public ExternalItem externalItemDtoToDo(ExternalItemDto source, ExternalItem target, String metadataName) throws MetamacException;
    public Collection<ExternalItem> externalItemDtoCollectionToDoList(Collection<ExternalItemDto> source, Collection<ExternalItem> target, String metadataName) throws MetamacException;

    // Related Resource
    public RelatedResource relatedResourceDtoToDo(RelatedResourceDto source, RelatedResource target, String metadataName) throws MetamacException;
    public List<RelatedResource> relatedResourceDtoListToDoList(List<RelatedResourceDto> sources, List<RelatedResource> targets, String metadataName) throws MetamacException;

    // Date
    public DateTime dateDtoToDo(Date source);

}
