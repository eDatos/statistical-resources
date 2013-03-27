package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.validators;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.DatasetServiceInvocationValidatorImpl;

public class DatasetLifecycleServiceInvocationValidatorImpl extends DatasetServiceInvocationValidatorImpl {
    
    public static void checkSendToProductionValidation(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) throws org.siemac.metamac.core.common.exception.MetamacException {
        checkExistingDatasetVersion(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }
    
    public static void checkSendToDiffusionValidation(DatasetVersion datasetVersion, List<MetamacExceptionItem> exceptions) throws org.siemac.metamac.core.common.exception.MetamacException {
        checkExistingDatasetVersion(datasetVersion, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }
}
