package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEmptyMethod;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String              TESTING_CLASS           = "org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset.DatasetLifecycleServiceImpl";

    @InjectMocks
    protected DatasetLifecycleServiceImpl    datasetLifecycleService = new DatasetLifecycleServiceImpl();

    @Mock
    private SiemacLifecycleChecker           siemacLifecycleChecker;

    @SuppressWarnings("unused")
    @Mock
    private SiemacLifecycleFiller            siemacLifecycleFiller;

    @SuppressWarnings("unused")
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private RestMapperTestImpl               restMapper;

    @SuppressWarnings("unused")
    @Mock
    private LifecycleInvocationValidatorBase lifecycleInvocationValidatorBase;

    @Mock
    private LifecycleCommonMetadataChecker   lifecycleCommonMetadataChecker;

    @Mock
    private DatasetVersionRepository         datasetVersionRepository;

    protected DatasetVersionMockFactory      datasetVersionMockFactory;

    protected DatasetService                 datasetService;

    @SuppressWarnings("unused")
    @Mock
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Mock
    private SrmRestInternalService           srmRestInternalService;

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

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToProductionValidation() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        verify(datasetVersionRepository, times(1)).retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        verify(siemacLifecycleChecker, times(1)).checkSendToProductionValidation(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkDatasetVersionCommonMetadata(any(DatasetVersion.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionChanged.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testCheckSendToProductionValidationRequiredMetadata() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersion.getDatasources().clear();
        datasetVersion.setRelatedDsd(null);

        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        datasetLifecycleService.checkSendToProductionValidationResource(datasetVersion, exceptionItems);

        MetamacException expected = new MetamacException(Arrays.asList(new MetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD),
                new MetamacExceptionItem(ServiceExceptionType.DATASET_EMPTY_DATASOURCES, datasetVersion.getSiemacMetadataStatisticalResource().getUrn())));
        MetamacAsserts.assertEqualsMetamacException(expected, new MetamacException(exceptionItems));
    }

    @Test
    public void testApplySendToProductionValidationResourceFillCoveragesLocalRepresentation() throws Exception {
        // Mock related stuff
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensions();

        // TEST
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);

        datasetLifecycleService.applySendToProductionValidationResource(getServiceContextAdministrador(), datasetVersion);
    }

    @Override
    @Test
    public void testCheckSendToProductionValidationResource() throws Exception {
        // It's already tested in:
        // - testSendToProductionValidation
        // - testSendToProductionValidationChangingSomeFieldsDontHaveEffect
        // - testCheckSendToProductionValidationRequiredMetadata
        // - testApplySendToProductionValidationResourceFillCoveragesLocalRepresentation
    }

    @Override
    @Test
    public void testApplySendToProductionValidationResource() throws Exception {
        // It's already tested in:
        // - testSendToProductionValidation
        // - testSendToProductionValidationChangingSomeFieldsDontHaveEffect
        // - testCheckSendToProductionValidationRequiredMetadata
        // - testApplySendToProductionValidationResourceFillCoveragesLocalRepresentation
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToDiffusionValidation() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersionChanged.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testSendToDiffusionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(),
                Mockito.anyListOf(MetamacExceptionItem.class));
    }

    @Override
    @Test
    public void testCheckSendToDiffusionValidationResource() throws Exception {
        // It's already tested in:
        // - testSendToDiffusionValidation
        // - testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect
        // - testSendToDiffusionValidationRequiredFields
    }

    @Override
    @Test
    public void testApplySendToDiffusionValidationResource() throws Exception {
        // It's already tested in:
        // - testSendToDiffusionValidation
        // - testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect
        // - testSendToDiffusionValidationRequiredFields
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToValidationRejected() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testSendToValidationRejectedFail() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersion);

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());

        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(DatasetVersion.class), Mockito.anyString(),
                Mockito.anyListOf(MetamacExceptionItem.class));
    }

    @Override
    @Test
    public void testCheckSendToValidationRejectedResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToValidationRejectedResource");
    }

    @Override
    @Test
    public void testApplySendToValidationRejectedResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToValidationRejectedResource");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToPublished() throws Exception {
        // FIXME: Do test in testCheckSendToPublishedResource and testApplySendToPublishedResource
        thrown.expect(UnsupportedOperationException.class);

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        datasetLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Ignore
    @Override
    @Test
    public void testCheckSendToPublishedResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    @Ignore
    @Override
    @Test
    public void testApplySendToPublishedResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Ignore
    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    @Ignore
    @Override
    @Test
    public void testApplyVersioningResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    // ------------------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // ------------------------------------------------------------------------------------------------------


    private class RestMapperTestImpl extends RestMapper {

        @Override
        public ExternalItem buildExternalItemFromSrmItemResourceInternal(ItemResourceInternal itemResourceInternal) throws MetamacException {
            ExternalItem externalItem = new ExternalItem();
            externalItem.setCode(itemResourceInternal.getId());
            externalItem.setCodeNested(itemResourceInternal.getNestedId());
            externalItem.setUri(itemResourceInternal.getSelfLink().getHref());
            externalItem.setUrn(itemResourceInternal.getUrn());
            externalItem.setUrnProvider(itemResourceInternal.getUrnProvider());
            externalItem.setType(TypeExternalArtefactsEnum.fromValue(itemResourceInternal.getKind()));
            externalItem.setManagementAppUrl(itemResourceInternal.getManagementAppLink());
            externalItem.setTitle(getInternationalStringFromInternationalStringResource(itemResourceInternal.getName()));
            return externalItem;
        }

        @Override
        public ExternalItem buildExternalItemFromCode(CodeResourceInternal code) throws MetamacException {
            ExternalItem externalItem = new ExternalItem();
            externalItem.setCode(code.getId());
            externalItem.setCodeNested(code.getNestedId());
            externalItem.setUri(code.getSelfLink().getHref());
            externalItem.setUrn(code.getUrn());
            externalItem.setUrnProvider(code.getUrnProvider());
            externalItem.setType(TypeExternalArtefactsEnum.fromValue(code.getKind()));
            externalItem.setManagementAppUrl(code.getManagementAppLink());
            externalItem.setTitle(getInternationalStringFromInternationalStringResource(code.getName()));
            return externalItem;
        }
    }
}
