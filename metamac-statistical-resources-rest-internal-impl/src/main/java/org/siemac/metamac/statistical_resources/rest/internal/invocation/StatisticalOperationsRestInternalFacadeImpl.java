package org.siemac.metamac.statistical_resources.rest.internal.invocation;

import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(StatisticalOperationsRestInternalFacade.BEAN_ID)
public class StatisticalOperationsRestInternalFacadeImpl implements StatisticalOperationsRestInternalFacade {

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public Operation retrieveOperation(String operationCode) {
        return restApiLocator.getStatisticalOperationsRestInternalFacadeV10().retrieveOperationById(operationCode);
    }
}
