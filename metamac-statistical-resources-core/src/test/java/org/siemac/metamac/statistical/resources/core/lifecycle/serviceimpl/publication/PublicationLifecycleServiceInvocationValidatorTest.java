package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;


public class PublicationLifecycleServiceInvocationValidatorTest extends StatisticalResourcesBaseTest {

    private PublicationLifecycleServiceInvocationValidator publicationLifecycleServiceInvocationValidator = new PublicationLifecycleServiceInvocationValidator();
    
    @Test
    public void testSendToProductionValidationPublicationVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        publicationLifecycleServiceInvocationValidator.checkSendToProductionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToDiffusionValidationPublicationVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        publicationLifecycleServiceInvocationValidator.checkSendToDiffusionValidationInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }

    @Test
    public void testSendToValidationRejectedQueryVersionRequired() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        publicationLifecycleServiceInvocationValidator.checkSendToValidationRejectedInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
    @Test
    public void testSendToPublishedQueryVersionRequired() throws Exception {
        // TODO: EN el test del published hay que comprobar que exista al menos una tabla por nivel. Aquí o en el checker?
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        publicationLifecycleServiceInvocationValidator.checkSendToPublishedInternal(getServiceContextAdministrador(), null, exceptionItems);
        
        MetamacException expected = new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION);
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }
    
}
