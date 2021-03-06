package org.siemac.metamac.statistical.resources.core.lifecycle;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEqualsInternationalString;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataSendToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataVersioningNewResource;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.LifecycleAsserts.assertNotNullAutomaticallyFilledMetadataVersioningPreviousResource;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToProductionValidation;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToPublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePrepareToValidationRejected;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourcePublished;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.HasLifecycleMocks.mockHasLifecycleStatisticalResourceVersioned;

import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.PublicationLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.StatisticalResourcesMockFactory;

public class LifecycleFillerTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private final LifecycleFiller     lifecycleFiller = new LifecycleFiller();

    @Mock
    StatisticalResourcesConfiguration statisticalResourcesConfiguration;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToProductionValidationActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToProductionValidation();

        lifecycleFiller.applySendToProductionValidationActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToProductionValidation(mockedResource);

    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToDiffusionValidationActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToDiffusionValidation();

        lifecycleFiller.applySendToDiffusionValidationActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToDiffusionValidation(mockedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToValidationRejectedActions() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToValidationRejected();

        lifecycleFiller.applySendToValidationRejectedActions(getServiceContextWithoutPrincipal(), mockedResource);
        assertNotNullAutomaticallyFilledMetadataSendToValidationRejected(mockedResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithoutPreviousVersion() throws Exception {
        DatasetVersion resource = persistedDoMocks.mockDatasetVersion();
        DatasetLifecycleTestUtils.prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.INIT_VERSION);

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithoutPreviousVersion() throws Exception {
        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationLifecycleTestUtils.prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.INIT_VERSION);

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsDatasetVersionWithPreviousVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));

        DatasetVersion resource = persistedDoMocks.mockDatasetVersion();
        DatasetLifecycleTestUtils.prepareToPublished(resource);
        resource.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.SECOND_VERSION);

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsPublicationVersionWithPreviousVersionErrorParameterRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PREVIOUS_VERSION));

        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationLifecycleTestUtils.prepareToPublished(resource);

        resource.getSiemacMetadataStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.SECOND_VERSION);

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), resource, null);
        assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(resource, null);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedCurrentResourceActionsDatasetVersionWithPreviousVersion() throws Exception {
        DatasetVersion resource = persistedDoMocks.mockDatasetVersion();
        DatasetVersion previousResource = persistedDoMocks.mockDatasetVersion();

        DatasetLifecycleTestUtils.prepareToPublished(resource);
        DatasetLifecycleTestUtils.fillAsPublished(previousResource);

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource);
        lifecycleFiller.applySendToPublishedPreviousResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource,
                RelatedResourceUtils.createRelatedResourceForHasLifecycleResource(resource));
        assertNotNullAutomaticallyFilledMetadataLifecycleSendToPublished(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplySendToPublishedActionsForUndefinedResourceType() throws Exception {
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourcePrepareToPublished();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applySendToPublishedCurrentResourceActions(getServiceContextWithoutPrincipal(), mockedResource, mockedPreviousResource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testLifeCycleResourceApplyVersioningNewResourceActions() throws Exception {
        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = persistedDoMocks.mockPublicationVersion();

        PublicationLifecycleTestUtils.fillAsVersioned(resource);
        PublicationLifecycleTestUtils.fillAsPublished(previousResource);

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);
        assertNotNullAutomaticallyFilledMetadataVersioningNewResource(resource, previousResource);
    }

    @Test
    public void testLifeCycleResourceApplyVersioningNewResourceActionsMinorVersionExpectedMajorVersionOccurred() throws Exception {
        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = persistedDoMocks.mockPublicationVersion();

        previousResource.getLifeCycleStatisticalResource().setVersionLogic(StatisticalResourcesMockFactory.MAXIMUM_MINOR_VERSION_AVAILABLE);

        PublicationLifecycleTestUtils.fillAsVersioned(resource);
        PublicationLifecycleTestUtils.fillAsPublished(previousResource);

        Locale locale = Locale.ENGLISH;

        Mockito.when(statisticalResourcesConfiguration.retrieveLanguageDefaultLocale()).thenReturn(locale);

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MINOR);
        assertNotNullAutomaticallyFilledMetadataVersioningNewResource(resource, previousResource);

        assertEquals(StatisticalResourcesMockFactory.SECOND_VERSION, resource.getLifeCycleStatisticalResource().getVersionLogic());

        assertTrue(CollectionUtils.isNotEmpty(resource.getSiemacMetadataStatisticalResource().getVersionRationaleTypes()));
        assertEquals(1, resource.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().size());
        assertEquals(VersionRationaleTypeEnum.MAJOR_OTHER, resource.getSiemacMetadataStatisticalResource().getVersionRationaleTypes().get(0).getValue());

        String localisedMessage = LocaleUtil.getMessageForCode(LifecycleFiller.MINOR_CHANGE_EXPECTED_MAJOR_VERSION_OCCURRED_MESSAGE, locale);
        assertEqualsInternationalString(new InternationalString(locale.getLanguage(), localisedMessage), resource.getSiemacMetadataStatisticalResource().getVersionRationale());
    }

    @Test
    public void testLifeCycleResourceApplyVersioningNewResourceActionsErrorUndefinedType() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type"));
        HasLifecycle mockedResource = mockHasLifecycleStatisticalResourceVersioned();
        HasLifecycle mockedPreviousResource = mockHasLifecycleStatisticalResourcePublished();

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), mockedResource, mockedPreviousResource, VersionTypeEnum.MAJOR);
    }

    @Test
    public void testLifeCycleResourceApplyVersioningPreviousResourceActions() throws Exception {
        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = persistedDoMocks.mockPublicationVersion();

        PublicationLifecycleTestUtils.fillAsVersioned(resource);
        PublicationLifecycleTestUtils.fillAsPublished(previousResource);

        lifecycleFiller.applyVersioningPreviousResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);

        assertNotNullAutomaticallyFilledMetadataVersioningPreviousResource(resource, previousResource);
    }

    @Test
    public void testReplacesVersionMatchsIsReplacedByVersion() throws MetamacException {
        PublicationVersion resource = persistedDoMocks.mockPublicationVersion();
        PublicationVersion previousResource = persistedDoMocks.mockPublicationVersion();

        PublicationLifecycleTestUtils.fillAsVersioned(resource);
        PublicationLifecycleTestUtils.fillAsPublished(previousResource);

        lifecycleFiller.applyVersioningNewResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);
        lifecycleFiller.applyVersioningPreviousResourceActions(getServiceContextWithoutPrincipal(), resource, previousResource, VersionTypeEnum.MAJOR);

        PublicationVersion newVersion = previousResource.getLifeCycleStatisticalResource().getIsReplacedByVersion().getPublicationVersion();
        assertThat(newVersion, is(notNullValue()));
        assertThat(newVersion, is(equalTo(resource)));

        PublicationVersion oldVersion = resource.getLifeCycleStatisticalResource().getReplacesVersion().getPublicationVersion();
        assertThat(oldVersion, is(notNullValue()));
        assertThat(oldVersion, is(equalTo(previousResource)));

    }

}
