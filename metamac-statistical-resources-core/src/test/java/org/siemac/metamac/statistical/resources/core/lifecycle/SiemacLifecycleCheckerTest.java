package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.LifecycleTestUtils.prepareToValidationRejected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;

/*
 * No spring context, we set the SUT (Software under test) dependencies with mocked objects. Unit testing style ;)
 */
public class SiemacLifecycleCheckerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private SiemacLifecycleChecker         siemacLifecycleServiceImpl;

    @Mock
    private LifecycleChecker               lifecycleService;

    @Mock
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Before
    public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        siemacLifecycleServiceImpl = new SiemacLifecycleChecker();
        MockitoAnnotations.initMocks(this);
    }

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

        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        siemacLifecycleServiceImpl.checkSendToProductionValidation(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToProductionValidation(any(HasLifecycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSiemacResourceApplySendToProductionValidationActions() throws Exception {
        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        prepareToProductionValidation(mockedResource);

        siemacLifecycleServiceImpl.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac

        verify(lifecycleService, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycleStatisticalResource.class));
    }

    @Test
    public void testSiemacResourceApplySendToProductionValidationActionsKeywordsBuilding() throws Exception {
        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        
        prepareToProductionValidation(mockedResource);
        mockedResource.getSiemacMetadataStatisticalResource().setTitle(new InternationalString(new String[]{"es", "en"}, new String[]{"Paro en España", "Unemployment in Spain"}));
        mockedResource.getSiemacMetadataStatisticalResource().setDescription(new InternationalString(new String[]{"es", "en"}, new String[]{"Medido en miles", "Measured in thousands"}));

        siemacLifecycleServiceImpl.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        asssertContainsKeywordsInLocale(mockedResource, "es", "Paro", "España", "Medido", "miles");
        asssertContainsKeywordsInLocale(mockedResource, "en", "Unemployment", "Spain", "Measured", "thousands");
        assertEquals(2, mockedResource.getSiemacMetadataStatisticalResource().getKeywords().getLocales().size());

        verify(lifecycleService, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycleStatisticalResource.class));
    }

    private void asssertContainsKeywordsInLocale(HasSiemacMetadataStatisticalResource resource, String locale, String... keywords) {
        assertNotNull(resource.getSiemacMetadataStatisticalResource().getKeywords());
        String localisedKeywords = resource.getSiemacMetadataStatisticalResource().getKeywords().getLocalisedLabel(locale);
        assertNotNull(localisedKeywords);
        List<String> actualKeywords = Arrays.asList(localisedKeywords.split("\\s"));
        assertEquals(keywords.length, actualKeywords.size());
        assertTrue(actualKeywords.containsAll(Arrays.asList(keywords)));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToDiffusionValidationRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());

        siemacLifecycleServiceImpl.checkSendToDiffusionValidation(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToDiffusionValidation(any(HasLifecycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSiemacResourceApplySendToDiffusionValidationActions() throws Exception {
        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        
        prepareToDiffusionValidation(mockedResource);

        siemacLifecycleServiceImpl.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac

        verify(lifecycleService, times(1)).applySendToDiffusionValidationActions(any(ServiceContext.class), any(HasLifecycleStatisticalResource.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceCheckSendToValidationRejectedRequiredFields() throws Exception {
        String baseMetadata = ServiceExceptionSingleParameters.SIEMAC_METADATA_STATISTICAL_RESOURCE;
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        
        prepareToValidationRejected(mockedResource);

        siemacLifecycleServiceImpl.checkSendToValidationRejected(mockedResource, baseMetadata, exceptionItems);

        // NO specific checks for siemac
        Assert.assertEquals(0, exceptionItems.size());

        verify(lifecycleService, times(1)).checkSendToValidationRejected(any(HasLifecycleStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkSiemacCommonMetadata(any(HasSiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSiemacResourceApplySendToValidationRejectedActions() throws Exception {
        HasSiemacMetadataStatisticalResource mockedResource = mock(HasSiemacMetadataStatisticalResource.class);
        when(mockedResource.getLifeCycleStatisticalResource()).thenReturn(new LifeCycleStatisticalResource());
        when(mockedResource.getSiemacMetadataStatisticalResource()).thenReturn(new SiemacMetadataStatisticalResource());
        
        prepareToValidationRejected(mockedResource);

        siemacLifecycleServiceImpl.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac

        verify(lifecycleService, times(1)).applySendToValidationRejectedActions(any(ServiceContext.class), any(HasLifecycleStatisticalResource.class));
    }

}
