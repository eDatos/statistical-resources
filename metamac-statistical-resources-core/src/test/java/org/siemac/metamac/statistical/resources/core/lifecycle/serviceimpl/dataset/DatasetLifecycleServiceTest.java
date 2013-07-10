package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts.assertEqualsCoverageForDsdComponent;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_15_DRAFT_NOT_READY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_20_PRODUCTION_VALIDATION_READY_FOR_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_21_PRODUCTION_VALIDATION_READY_FOR_VALIDATION_REJECTED_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;
import org.siemac.metamac.statistical.resources.core.utils.DsRepositoryMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

/**
 * Spring based transactional test with DbUnit support.
 */
public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String                        DEFAULT_LOCALE          = "es";

    @InjectMocks
    protected DatasetLifecycleServiceImpl              datasetLifecycleService = new DatasetLifecycleServiceImpl();

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
    private DatasetRepositoriesServiceFacade           datasetRepositoriesServiceFacade;

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

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToProductionValidation() throws Exception {
        mockDsdAndatasetRepositoryForProductionValidation();

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
        mockDsdAndatasetRepositoryForProductionValidation();

        DataStructure emptyDsd = new DataStructure();
        emptyDsd.setDataStructureComponents(new DataStructureComponentsType());
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(emptyDsd);

        DatasetVersion datasetVersionOriginal = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        Mockito.when(datasetVersionRepository.retrieveByUrn(Mockito.anyString())).thenReturn(datasetVersionOriginal);

        DatasetVersion datasetVersionChanged = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);
        datasetVersionChanged.setStatisticOfficiality(null);

        datasetLifecycleService.sendToProductionValidation(getServiceContextAdministrador(), datasetVersionChanged);
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
        mockDsdAndatasetRepositoryForProductionValidation();

        // TEST
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_16_DRAFT_READY_FOR_PRODUCTION_VALIDATION_NAME);

        datasetLifecycleService.applySendToProductionValidationResource(getServiceContextAdministrador(), datasetVersion);

        assertEquals(Integer.valueOf(3), datasetVersion.getFormatExtentDimensions());
        // Assert.assertEquals(Integer.valueOf(27), datasetVersion.getFormatExtentObservations());

        // all coverages
        assertEqualsCoverageForDsdComponent(datasetVersion, "GEO_DIM",
                Arrays.asList(new CodeDimension("GEO_DIM", "code-01", "Code 01"), new CodeDimension("GEO_DIM", "code-02", "Code 02"), new CodeDimension("GEO_DIM", "code-03", "Code 03")));

        assertEqualsCoverageForDsdComponent(datasetVersion, "MEAS_DIM", Arrays.asList(new CodeDimension("MEAS_DIM", "concept-01", "Concept 01"), new CodeDimension("MEAS_DIM", "concept-02",
                "Concept 02"), new CodeDimension("MEAS_DIM", "concept-03", "Concept 03")));

        assertEqualsCoverageForDsdComponent(datasetVersion, "TIME_PERIOD",
                Arrays.asList(new CodeDimension("MEAS_DIM", "2010"), new CodeDimension("MEAS_DIM", "2011"), new CodeDimension("MEAS_DIM", "2012")));

        assertEquals(9, datasetVersion.getCoverages().size());

        // specific coverages
        assertEquals(3, datasetVersion.getGeographicCoverage().size());
        assertEquals(3, datasetVersion.getTemporalCoverage().size());
        assertEquals(3, datasetVersion.getMeasureCoverage().size());
    }
    
    @Override
    @Test
    public void testCheckSendToProductionValidationResource() throws Exception {
        fail("not implemented");
        
    }

    @Override
    @Test
    public void testApplySendToProductionValidationResource() throws Exception {
        fail("not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

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
    public void testCheckSendToDiffusionValidationResource() throws Exception {
        fail("not implemented");
    }

    @Override
    @Test
    public void testApplySendToDiffusionValidationResource() throws Exception {
        fail("not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

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
    public void testCheckSendToValidationRejectedResource() throws Exception {
        fail("not implemented");
    }

    @Override
    @Test
    public void testApplySendToValidationRejectedResource() throws Exception {
        fail("not implemented");
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToPublished() throws Exception {
        // FIXME: Do test
        thrown.expect(UnsupportedOperationException.class);

        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_15_DRAFT_NOT_READY_NAME);
        datasetLifecycleService.sendToPublished(getServiceContextAdministrador(), datasetVersion);
    }
    

    @Override
    @Test
    public void testCheckSendToPublishedResource() throws Exception {
        fail("not implemented");
    }

    @Override
    @Test
    public void testApplySendToPublishedResource() throws Exception {
        fail("not implemented");
    }

    @Override
    @Test
    public void testCheckSendToPublishedLinkedStatisticalResource() throws Exception {
        fail("not implemented");
        // TODO: Esto debería desaparecer
    }

    @Override
    @Test
    public void testApplySendToPublishedLinkedStatisticalResource() throws Exception {
        fail("not implemented");
        // TODO: Esto debería desaparecer
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------


    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        fail("not implemented");        
    }

    @Override
    @Test
    public void testApplyVersioningResource() throws Exception {
        fail("not implemented");        
    }

    @Override
    @Test
    public void testCheckVersioningLinkedStatisticalResource() throws Exception {
        fail("not implemented");
        // TODO: Esto debería desaparecer
    }

    @Override
    @Test
    public void testApplyVersioningLinkedStatisticalResource() throws Exception {
        // TODO: Esto debería desaparecer
        fail("not implemented");        
    }
    
    /**
     * UTILS
     */
    private void mockDsdAndatasetRepositoryForProductionValidation() throws Exception {
        List<ConditionObservationDto> dimensionsCodes = new ArrayList<ConditionObservationDto>();

        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("GEO_DIM", "code-01", "code-02", "code-03"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("TIME_PERIOD", "2010", "2011", "2012"));
        dimensionsCodes.add(DsRepositoryMockUtils.mockCodeDimensions("MEAS_DIM", "concept-01", "concept-02", "concept-03"));
        Mockito.when(datasetRepositoriesServiceFacade.findCodeDimensions(Mockito.anyString())).thenReturn(dimensionsCodes);

        DatasetRepositoryDto datasetRepoDto = DsRepositoryMockUtils.mockDatasetRepository("dsrepo-01", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM");
        Mockito.when(datasetRepositoriesServiceFacade.retrieveDatasetRepository(Mockito.anyString())).thenReturn(datasetRepoDto);

        // Mock codelist and concept Scheme

        Codelist codelist = SrmMockUtils.buildCodelistWithCodes("codelist-01", "urn:uuid:codelist-01", DEFAULT_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveCodelistByUrn(codelist.getUrn())).thenReturn(codelist);

        ConceptScheme conceptScheme = SrmMockUtils.buildConceptSchemeWithConcepts("csch-01", "urn:uuid:cshm-01", DEFAULT_LOCALE, 3);
        Mockito.when(srmRestInternalService.retrieveConceptSchemeByUrn(conceptScheme.getUrn())).thenReturn(conceptScheme);

        // Create a datastructure with dimensions marked as measure temporal and spatial

        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:uuid:dsd-urn", "GEO_DIM", "TIME_PERIOD", "MEAS_DIM", conceptScheme, codelist);
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);

    }

}
