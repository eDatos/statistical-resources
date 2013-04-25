package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.DatasetServiceInvocationValidatorImpl;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.springframework.stereotype.Component;

@Component
public class DatasetLifecycleServiceInvocationValidator extends LifecycleInvocationValidatorBase<DatasetVersion> {

    @Override
    public void checkSendToProductionValidationInternal(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) {
        DatasetServiceInvocationValidatorImpl.checkExistingDatasetVersion(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    public void checkSendToDiffusionValidationInternal(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) {
        DatasetServiceInvocationValidatorImpl.checkExistingDatasetVersion(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }
    
    @Override
    public void checkSendToValidationRejectedInternal(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) {
        DatasetServiceInvocationValidatorImpl.checkExistingDatasetVersion(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void checkSendToPublishedInternal(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) {
        DatasetServiceInvocationValidatorImpl.checkExistingDatasetVersion(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
        
    }
}
