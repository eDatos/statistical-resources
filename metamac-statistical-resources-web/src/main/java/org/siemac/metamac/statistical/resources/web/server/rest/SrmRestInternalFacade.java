package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;


public interface SrmRestInternalFacade {
    
    public List<ExternalItemDto> findDsds(int firstResult, int maxResult, DsdWebCriteria condition); 
}
