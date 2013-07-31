package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;

public interface StatisticalOperationsRestInternalService {

    public Operation retrieveOperationById(String operationCode) throws MetamacException;

    public Operations findOperations(int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findOperationsUrns(int firstResult, int maxResult, String query) throws MetamacException;
    public List<ExternalItem> findOperationsAsExternalItems(int firstResult, int maxResult, String query) throws MetamacException;
    
    public Instances findInstances(String operationId, int firstResult, int maxResult, String query) throws MetamacException;
    public List<String> findInstancesUrns(String operationId, int firstResult, int maxResult, String query) throws MetamacException;

}
