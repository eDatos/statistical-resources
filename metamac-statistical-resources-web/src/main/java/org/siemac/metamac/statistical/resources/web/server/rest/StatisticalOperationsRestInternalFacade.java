package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface StatisticalOperationsRestInternalFacade {

    public ExternalItemDto retrieveOperation(String operationCode) throws MetamacWebException;

    public List<ExternalItemDto> findOperations(int firstResult, int maxResult, String operation) throws MetamacWebException;

}
