package org.siemac.metamac.statistical_resources.rest.internal.invocation;

import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;

public interface StatisticalOperationsRestInternalFacade {

    public static final String BEAN_ID = "statisticalOperationsInternalFacade";

    public Operation retrieveOperation(String operationCode);
}
