package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleService;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

/**
 * Spring based transactional test with DbUnit support.
 */
public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    @InjectMocks
    protected LifecycleService<DatasetVersion>         datasetLifecycleService = new DatasetLifecycleServiceImpl();

    @Mock
    private SiemacLifecycleChecker                     siemacLifecycleChecker;

    @Mock
    private LifecycleCommonMetadataChecker             lifecycleCommonMetadataChecker;

    @Mock
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    @Mock
    private DatasetVersionRepository                   datasetVersionRepository;

    protected DatasetVersionMockFactory                datasetVersionMockFactory;

    protected DatasetService                           datasetService;

    @Before
    public void setUp() {
        datasetVersionMockFactory = new DatasetVersionMockFactory();
        datasetVersionMockFactory.setStatisticalResourcesPersistedDoMocks(new StatisticalResourcesPersistedDoMocks());
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void validateMockitoUsage() {
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testSendToProductionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);

        verify(datasetLifecycleServiceInvocationValidator, times(1)).checkSendToProductionValidation(any(ServiceContext.class), Mockito.eq(datasetVersion));
        verify(datasetVersionRepository, times(1)).retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        verify(siemacLifecycleChecker, times(1)).checkSendToProductionValidation(any(HasSiemacMetadataStatisticalResource.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkDatasetVersionCommonMetadata(any(DatasetVersion.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionChanged);
    }

    @Test
    public void testSendToDiffusionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);
    }

    @Test
    public void testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersionChanged);
    }

    @Test
    public void testSendToDiffusionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion);

        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(),
                Mockito.anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSendToValidationRejected() throws Exception {
        fail("TODO: Pendiente Robert - not tested");
    }

    @Test
    public void testSendToPublished() throws Exception {
        thrown.expect(UnsupportedOperationException.class);

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        datasetLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersion);
    }

}
