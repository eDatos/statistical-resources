package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;


public class QueryLifecycleServiceInvocationValidatorTest extends StatisticalResourcesBaseTest {

    private QueryLifecycleServiceInvocationValidator queryLifecycleServiceInvocationValidator = new QueryLifecycleServiceInvocationValidator();
    
    @Test
    public void testSendToProductionValidationQueryVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        queryLifecycleServiceInvocationValidator.checkSendToProductionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToDiffusionValidationQueryVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        queryLifecycleServiceInvocationValidator.checkSendToDiffusionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToValidationRejectedQueryVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        queryLifecycleServiceInvocationValidator.checkSendToValidationRejectedInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToPublishedQueryVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        queryLifecycleServiceInvocationValidator.checkSendToPublishedInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.QUERY_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
}
