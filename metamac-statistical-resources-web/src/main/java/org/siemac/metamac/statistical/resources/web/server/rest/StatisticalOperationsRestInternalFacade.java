package org.siemac.metamac.statistical.resources.web.server.rest;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface StatisticalOperationsRestInternalFacade {

    public ExternalItemDto retrieveOperation(String operationCode) throws MetamacWebException;

    public ExternalItemsResult findOperations(int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException;

    public ExternalItemsResult findOperationInstances(String operationId, int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException;

}
