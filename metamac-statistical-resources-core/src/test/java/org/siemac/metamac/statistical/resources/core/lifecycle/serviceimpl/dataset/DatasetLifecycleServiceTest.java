package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEmptyMethod;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureComponents;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.constraint.api.ConstraintsService;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskService;
import org.siemac.metamac.statistical.resources.core.utils.DataMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.DatasetLifecycleTestUtils;
import org.siemac.metamac.statistical.resources.core.utils.TaskMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;

import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String              TESTING_CLASS           = "org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset.DatasetLifecycleServiceImpl";

    @InjectMocks
    protected DatasetLifecycleServiceImpl    datasetLifecycleService = new DatasetLifecycleServiceImpl();

    @Mock
    private SiemacLifecycleChecker           siemacLifecycleChecker;

    @Mock
    private SiemacLifecycleFiller            siemacLifecycleFiller;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private RestMapperTestImpl               restMapper;

    @Mock
    private LifecycleInvocationValidatorBase lifecycleInvocationValidatorBase;

    @Mock
    private LifecycleCommonMetadataChecker   lifecycleCommonMetadataChecker;

    @Mock
    private DatasetVersionRepository         datasetVersionRepository;

    @Mock
    protected DatasetService                 datasetService;

    @Mock
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Mock
    private SrmRestInternalService           srmRestInternalService;

    @Mock
    private TaskService                      taskService;

    @Mock
    private ConstraintsService               constraintsService;

    @Before
    public void setUp() {
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
        mockDsdAndDataRepositorySimpleDimensions();
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponents());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionUrn);

        verify(datasetVersionRepository, times(1)).retrieveByUrn(datasetVersionUrn);
        verify(siemacLifecycleChecker, times(1)).checkSendToProductionValidation(any(HasSiemacMetadata.class), anyString(), anyListOf(MetamacExceptionItem.class));
        verify(lifecycleCommonMetadataChecker, times(1)).checkDatasetVersionCommonMetadata(any(ServiceContext.class), any(DatasetVersion.class), anyString(), anyListOf(MetamacExceptionItem.class));
    }

    @Test
    public void testSendToProductionValidationImportationTaskInProgress() throws Exception {
        mockDsdAndDataRepositorySimpleDimensions();
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();
        mockTaskInProgressForResource(datasetVersionUrn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponents());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    public void testSendToProductionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);

        mockDsdAndDataRepositorySimpleDimensions();

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponents());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionChanged.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testApplySendToProductionValidationResourceFillCoveragesLocalRepresentation() throws Exception {
        // Mock related stuff
        mockDsdAndDataRepositorySimpleDimensions();

        // TEST
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);

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
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    public void testSendToDiffusionValidationImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        mockTaskInProgressForResource(datasetVersionUrn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    public void testSendToDiffusionValidationChangingSomeFieldsDontHaveEffect() throws Exception {
        // Original set to repository
        DatasetVersion datasetVersionOriginal = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        String datasetVersionUrn = datasetVersionOriginal.getSiemacMetadataStatisticalResource().getUrn();

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    public void testSendToDiffusionValidationRequiredFields() throws Exception {
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY_NAME);
        String datasteVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        datasetLifecycleService.sendToDiffusionValidation(getServiceContextAdministrador(), datasteVersionUrn);

        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(any(ServiceContext.class), Mockito.any(DatasetVersion.class), Mockito.anyString(),
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
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersionUrn);
    }

    @Test
    public void testSendToValidationRejectedImportationTaskInProgress() throws Exception {
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        mockTaskInProgressForResource(datasetVersionUrn, true);

        expectedMetamacException(new MetamacException(ServiceExceptionType.TASKS_IN_PROGRESS, datasetVersionUrn));

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Test
    public void testSendToValidationRejectedFail() throws Exception {
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_19_PRODUCTION_VALIDATION_NOT_READY_NAME);
        String datasetVersionUrn = datasetVersion.getSiemacMetadataStatisticalResource().getUrn();

        datasetLifecycleService.sendToValidationRejected(getServiceContextAdministrador(), datasetVersionUrn);

        Mockito.verify(lifecycleCommonMetadataChecker, Mockito.times(1)).checkDatasetVersionCommonMetadata(Mockito.any(ServiceContext.class), Mockito.any(DatasetVersion.class), Mockito.anyString(),
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
    @Ignore
    public void testSendToPublished() throws Exception {
        // GO TO DatasetPublishingServiceTest
    }

    @Ignore
    @Override
    @Test
    public void testCheckSendToPublishedResource() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");
    }

    @Ignore
    @Override
    @Test
    public void testApplySendToPublishedCurrentResource() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");

    }

    @Ignore
    @Override
    @Test
    public void testApplySendToPublishedPreviousResource() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");

    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "checkVersioningResource");
    }

    @Override
    @Test
    public void testApplyVersioningNewResource() throws Exception {
        DatasetVersion previous = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);

        DatasetVersion datasetVersionNew = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetLifecycleTestUtils.fillAsVersioned(datasetVersionNew);

        ApplicationContextProvider.getApplicationContext().getBean(ConstraintsService.class);

        datasetLifecycleService.applyVersioningNewResource(getServiceContextAdministrador(), datasetVersionNew, previous);
    }

    @Override
    @Test
    public void testApplyVersioningPreviousResource() throws Exception {
        DatasetVersion datasetVersion = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_14_OPER_03_CODE_01_PUBLISHED_NAME);
        DatasetLifecycleTestUtils.fillAsPublished(datasetVersion);

        datasetLifecycleService.applyVersioningPreviousResource(getServiceContextAdministrador(), datasetVersion);
    }

    @Override
    @Test
    public void testCopyResourceForVersioning() throws Exception {
        // TODO testCopyResourceForVersioning. Test all metadata (METAMAC-2143)

        DatasetVersion source = mockDatasetVersionInRepoFromMockFactory(DATASET_VERSION_70_PREPARED_TO_PUBLISH_EXTERNAL_ITEM_FULL_NAME);
        DatasetVersion target = datasetLifecycleService.copyResourceForVersioning(getServiceContextAdministrador(), source);

        // Categorisations
        assertEquals(source.getCategorisations().size(), target.getCategorisations().size());
        for (int i = 0; i < source.getCategorisations().size(); i++) {
            Categorisation expected = source.getCategorisations().get(i);
            Categorisation actual = target.getCategorisations().get(i);
            DatasetsAsserts.assertEqualsExternalItem(expected.getCategory(), actual.getCategory());
            DatasetsAsserts.assertEqualsExternalItem(expected.getMaintainer(), actual.getMaintainer());
        }
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

    private DatasetVersion mockDatasetVersionInRepoFromMockFactory(String mockName) throws MetamacException {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(mockName);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.eq(datasetVersion.getSiemacMetadataStatisticalResource().getUrn()))).thenReturn(datasetVersion);
        return datasetVersion;
    }

    private void mockDsdAndDataRepositorySimpleDimensions() throws Exception {
        DataMockUtils.mockDsdAndDataRepositorySimpleDimensionsNoAttributes(datasetRepositoriesServiceFacade, srmRestInternalService);
    }

    private void mockTaskInProgressForResource(String datasetVersionUrn, boolean status) throws MetamacException {
        TaskMockUtils.mockTaskInProgressForDatasetVersion(taskService, datasetVersionUrn, status);
    }

}
