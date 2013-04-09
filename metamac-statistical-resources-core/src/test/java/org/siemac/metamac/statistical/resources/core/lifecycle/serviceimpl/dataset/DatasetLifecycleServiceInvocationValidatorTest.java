package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;


public class DatasetLifecycleServiceInvocationValidatorTest extends StatisticalResourcesBaseTest {

    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator = new DatasetLifecycleServiceInvocationValidator();
    
    @Test
    public void testSendToProductionValidationDatasetVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        datasetLifecycleServiceInvocationValidator.checkSendToProductionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToDiffusionValidationDatasetVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        datasetLifecycleServiceInvocationValidator.checkSendToDiffusionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.DATASET_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }

}
