package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasSiemacMetadataMocks.mockHasSiemacMetadataPublished;

import java.util.Arrays;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;

/*
 * No spring context, we set the SUT (Software under test) dependencies with mocked objects. Unit testing style ;)
 */
@RunWith(MockitoJUnitRunner.class)
public class SiemacLifecycleFillerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private SiemacLifecycleFiller         siemacLifecycleFiller = new SiemacLifecycleFiller();

    @Mock
    private LifecycleFiller               lifecycleFiller;

    @After
    public void checkMockitoUsage() {
        validateMockitoUsage();
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToProductionValidationActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToProductionValidation();

        siemacLifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    @Test
    public void testSiemacResourceApplySendToProductionValidationActionsKeywordsBuilding() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToProductionValidation();
        mockedResource.getSiemacMetadataStatisticalResource().setTitle(new InternationalString(new String[]{"es", "en"}, new String[]{"Paro en España", "Unemployment in Spain"}));
        mockedResource.getSiemacMetadataStatisticalResource().setDescription(new InternationalString(new String[]{"es", "en"}, new String[]{"Medido en miles", "Measured in thousands"}));

        siemacLifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);

        asssertContainsKeywordsInLocale(mockedResource, "es", "Paro", "España", "Medido", "miles");
        asssertContainsKeywordsInLocale(mockedResource, "en", "Unemployment", "Spain", "Measured", "thousands");
        assertEquals(2, mockedResource.getSiemacMetadataStatisticalResource().getKeywords().getLocales().size());

        verify(lifecycleFiller, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }
    
    @Test
    public void testSiemacResourceApplySendToProductionValidationActionsNoKeywordsBuildingOnUserDefinedKeywords() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToProductionValidation();
        mockedResource.getSiemacMetadataStatisticalResource().setKeywords(new InternationalString(new String[]{"es", "en"}, new String[]{"IPC CANARIAS", "IPC CANARY ISLAND"}));
        mockedResource.getSiemacMetadataStatisticalResource().setTitle(new InternationalString(new String[]{"es", "en"}, new String[]{"Paro en España", "Unemployment in Spain"}));
        mockedResource.getSiemacMetadataStatisticalResource().setDescription(new InternationalString(new String[]{"es", "en"}, new String[]{"Medido en miles", "Measured in thousands"}));
        
        siemacLifecycleFiller.applySendToProductionValidationActions(getServiceContextAdministrador(), mockedResource);
        
        asssertContainsKeywordsInLocale(mockedResource, "es", "IPC", "CANARIAS");
        asssertContainsKeywordsInLocale(mockedResource, "en", "IPC", "CANARY", "ISLAND");
        assertEquals(2, mockedResource.getSiemacMetadataStatisticalResource().getKeywords().getLocales().size());
        
        verify(lifecycleFiller, times(1)).applySendToProductionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    private void asssertContainsKeywordsInLocale(HasSiemacMetadata resource, String locale, String... keywords) {
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
    public void testSiemacResourceApplySendToDiffusionValidationActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToDiffusionValidation();

        siemacLifecycleFiller.applySendToDiffusionValidationActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToDiffusionValidationActions(any(ServiceContext.class), any(HasLifecycle.class));
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSiemacResourceApplySendToValidationRejectedActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToValidationRejected();

        siemacLifecycleFiller.applySendToValidationRejectedActions(getServiceContextAdministrador(), mockedResource);

        // No specific actions for siemac
        verify(lifecycleFiller, times(1)).applySendToValidationRejectedActions(any(ServiceContext.class), any(HasLifecycle.class));
    }
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------
    
    @Test
    public void testSiemacResourceApplySendToPublishedActions() throws Exception {
        HasSiemacMetadata mockedResource = mockHasSiemacMetadataPrepareToPublished();
        HasSiemacMetadata previosMockedVersion = mockHasSiemacMetadataPublished();

        siemacLifecycleFiller.applySendToPublished(getServiceContextAdministrador(), mockedResource, previosMockedVersion);

        verify(lifecycleFiller, times(1)).applySendToPublishedActions(any(ServiceContext.class), any(HasLifecycle.class), any(HasLifecycle.class));
        assertNotNull(mockedResource.getSiemacMetadataStatisticalResource().getCopyrightedDate());
        assertEquals(mockedResource.getLifeCycleStatisticalResource().getValidFrom(), mockedResource.getSiemacMetadataStatisticalResource().getCopyrightedDate());
    }

}
