package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
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
    private SiemacLifecycleFiller                      siemacLifecycleFiller;

    @Mock
    private LifecycleCommonMetadataChecker             lifecycleCommonMetadataChecker;

    @Mock
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    @Mock
    private DatasetVersionRepository                   datasetVersionRepository;

    protected DatasetVersionMockFactory                datasetVersionMockFactory;

    protected DatasetService                           datasetService;

    @Mock
    private SrmRestInternalService                     srmRestInternalService;

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

    @Override
    @Test
    public void testSendToProductionValidation() throws Exception {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);
        
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion);

        verify(datasetLifecycleServiceInvocationValidator, times(1)).checkSendToProductionValidation(any(ServiceContext.class), Mockito.eq(datasetVersion));
        verify(datasetVersionRepository, times(1)).retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        verify(siemacLifecycleChecker, times(1)).checkSendToProductionValidation(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkDatasetVersionCommonMetadata(any(DatasetVersion.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);
        
        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionChanged);
    }

    @Override
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

    @Override
    @Test
    public void testSendToValidationRejected() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersion);
    }
    
    @Test
    public void testSendToValidationRejectedFail() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);
        
        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersion);
        
        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(),
                Mockito.anyListOf(MetamacExceptionItem.class));
    }

    @Override
    @Test
    public void testSendToPublished() throws Exception {
        //FIXME: Do test
        thrown.expect(UnsupportedOperationException.class);

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        datasetLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersion);
    }

}
