package org.siemac.metamac.statistical.resources.core.base.mapper;

import java.util.Date;
import java.util.Set;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;

public interface BaseDo2DtoMapper {

    // Base Hierarchy
    public void nameableStatisticalResourceDoToDto(NameableStatisticalResource source, NameableStatisticalResourceDto target);
    public void identifiableStatisticalResourceDoToDto(IdentifiableStatisticalResource source, IdentifiableStatisticalResourceDto target);
    public void statisticalResourceDoToDto(StatisticalResource source, StatisticalResourceDto target);
    
    // External Item
    public Set<ExternalItemDto> externalItemDoListToDtoList(Set<ExternalItem> source);
    public ExternalItemDto externalItemDoToDto(ExternalItem source);
    
    // International String
    public InternationalStringDto internationalStringDoToDto(InternationalString source);
    
    // Other types
    public Date dateDoToDto(DateTime source);

}
