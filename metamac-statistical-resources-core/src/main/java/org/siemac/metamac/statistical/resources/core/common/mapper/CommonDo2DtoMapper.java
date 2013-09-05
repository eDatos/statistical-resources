package org.siemac.metamac.statistical.resources.core.common.mapper;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;

public interface CommonDo2DtoMapper extends BaseDo2DtoMapper {

    // External Item
    public Collection<ExternalItemDto> externalItemDoCollectionToDtoCollection(Collection<ExternalItem> source) throws MetamacException;
    public List<ExternalItemDto> externalItemDoCollectionToDtoList(Collection<ExternalItem> source) throws MetamacException;
    public ExternalItemDto externalItemDoToDto(ExternalItem source) throws MetamacException;

    // Related resource
    public Collection<RelatedResourceDto> relatedResourceResultCollectionToDtoCollection(Collection<RelatedResourceResult> source) throws MetamacException;
    public Collection<RelatedResourceDto> relatedResourceDoCollectionToDtoCollection(Collection<RelatedResource> source) throws MetamacException;
    public RelatedResourceDto relatedResourceResultToDto(RelatedResourceResult source) throws MetamacException;
    public RelatedResourceDto relatedResourceDoToDto(RelatedResource source) throws MetamacException;
    
    // International String
    public InternationalStringDto internationalStringDoToDto(InternationalString source);

    // Temporal code
    public Collection<TemporalCodeDto> temporalCodeDoCollectionToDtoCollection(Collection<TemporalCode> source) throws MetamacException;
    public List<TemporalCodeDto> temporalCodeDoCollectionToDtoList(Collection<TemporalCode> source) throws MetamacException;
    public TemporalCodeDto temporalCodeDoToDto(TemporalCode source) throws MetamacException;
    
    // Other types
    public Date dateDoToDto(DateTime source);
    

}
