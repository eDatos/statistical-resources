package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;

/*
 * No spring context, we set the SUT (Software under test) dependencies with mocked objects. Unit testing style ;)
 */

@RunWith(MockitoJUnitRunner.class)
public class SiemacLifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private SiemacLifecycleChecker         siemacLifecycleServiceImpl = new SiemacLifecycleChecker();

    @Mock
    private LifecycleChecker               lifecycleService;

    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @After
    public void checkMockitoUsage() {
        validateMockitoUsage();
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToProductionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        HasSiemacMetadata mockedResource = mock(HasSiemacMetadata.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        siemacLifecycleServiceImpl.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToProductionValidation(any(HasLifecycle.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToDiffusionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        HasSiemacMetadata mockedResource = mock(HasSiemacMetadata.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        siemacLifecycleServiceImpl.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToDiffusionValidation(any(HasLifecycle.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToValidationRejectedRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        HasSiemacMetadata mockedResource = mock(HasSiemacMetadata.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        prepareToValidationRejected(mockedResource);

        siemacLifecycleServiceImpl.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToValidationRejected(any(HasLifecycle.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------
    
    @Test
    public void testSiemacResourceCheckSendToPublished() throws Exception {
        fail("not implemented");
    }
    
    @Test
    public void testSiemacResourceCheckExternalItemsPreviouslyPublished() throws Exception {
        fail("not implemented");
    }
    
    @Test
    public void testSiemacResourceCheckRelatedResourcesPreviouslyPublished() throws Exception {
        fail("not implemented");
    }
}
