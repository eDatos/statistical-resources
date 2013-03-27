package  org.siemac.metamac.statistical.resources.core.base.serviceimpl;

import static org.siemac.metamac.statistical.resources.core.base.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.base.serviceapi.SiemacLifecycleService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml", "classpath:spring/statistical-resources/include/lifecycle-mockito.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class SiemacLifecycleServiceTest extends StatisticalResourcesBaseTest {

    @Autowired
    private SiemacLifecycleService siemacLifecycleServiceBaseImpl;
    
    
    @Test
    public void testSiemacResourceCheckSendToProductionValidationRequiredFields() throws Exception {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        
        expectedMetamacException(new MetamacException(Arrays.asList(
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LANGUAGES)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.STATISTICAL_OPERATION)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.TYPE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.MAINTAINER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.CREATOR)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LAST_UPDATE)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.PUBLISHER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.RIGHTS_HOLDER)),
                new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, addParameter(baseMetadata, ServiceExceptionSingleParameters.LICENSE))
        )));
        
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        siemacLifecycleServiceBaseImpl.checkSendToProductionValidation(resource, baseMetadata, exceptionItems);

        throw new MetamacException(exceptionItems);
    }
    
}
