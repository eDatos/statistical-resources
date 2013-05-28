package org.siemac.metamac.statistical.resources.web.server.rest;

import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;


public interface SrmRestInternalFacade {
    
    public ExternalItemsResult findDsds(int firstResult, int maxResult, DsdWebCriteria condition); 
}
