package org.siemac.metamac.statistical_resources.rest.external.invocation;

import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations.v1_0.domain.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(StatisticalOperationsRestExternalFacade.BEAN_ID)
public class StatisticalOperationsRestExternalFacadeImpl implements StatisticalOperationsRestExternalFacade {

    @Autowired
    private MetamacApisLocator restApiLocator;

    @Override
    public Operation retrieveOperation(String operationCode) {
        return restApiLocator.getStatisticalOperationsV1_0().retrieveOperationById(operationCode);
    }

    @Override
    public Instance retrieveInstanceById(String operationId, String id) {
        return restApiLocator.getStatisticalOperationsV1_0().retrieveInstanceById(operationId, id);
    }
}
