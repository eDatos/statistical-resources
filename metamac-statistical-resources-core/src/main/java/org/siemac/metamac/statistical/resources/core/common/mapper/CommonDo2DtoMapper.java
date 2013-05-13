package org.siemac.metamac.statistical.resources.core.common.mapper;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;

public interface CommonDo2DtoMapper {

    // External Item
    public Collection<ExternalItemDto> externalItemDoCollectionToDtoCollection(Collection<ExternalItem> source);
    public ExternalItemDto externalItemDoToDto(ExternalItem source);

    // Related resource
    public Collection<RelatedResourceDto> relatedResourceDoCollectionToDtoCollection(Collection<RelatedResource> source);
    public RelatedResourceDto relatedResourceDoToDto(RelatedResource source);
    
    // International String
    public InternationalStringDto internationalStringDoToDto(InternationalString source);

    // Other types
    public Date dateDoToDto(DateTime source);

}