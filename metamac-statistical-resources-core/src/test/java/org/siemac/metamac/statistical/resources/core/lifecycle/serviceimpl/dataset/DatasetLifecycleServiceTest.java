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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.siemac.metamac.common.test.utils.MetamacAsserts;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
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
import org.siemac.metamac.statistical.resources.core.utils.DsRepositoryMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.SrmMockUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

public class DatasetLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

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

    protected DatasetVersionMockFactory      datasetVersionMockFactory;

    protected DatasetService                 datasetService;

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
        mockDsdAndatasetRepositoryForProductionValidation();

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
        mockDsdAndatasetRepositoryForProductionValidation();

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

    @Ignore
    @Override
    @Test
    public void testCheckSendToProductionValidationResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar

    }

    @Ignore
    @Override
    @Test
    public void testApplySendToProductionValidationResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
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

    @Ignore
    @Override
    @Test
    public void testCheckSendToDiffusionValidationResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    @Ignore
    @Override
    @Test
    public void testApplySendToDiffusionValidationResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
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

    @Ignore
    @Override
    @Test
    public void testCheckSendToValidationRejectedResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    @Ignore
    @Override
    @Test
    public void testApplySendToValidationRejectedResource() throws Exception {
        fail("not implemented");
        // TODO: Implementar
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Test
    public void testSendToPublished() throws Exception {
        // FIXME: Do test
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

        CodelistReferenceType codelistReference = SrmMockUtils.buildCodelistRef("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=TEST:codelist-01(1.0)");
        Codes codes = SrmMockUtils.buildCodes(3);
        Mockito.when(srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistReference.getURN())).thenReturn(codes);

        ConceptSchemeReferenceType conceptSchemeReference = SrmMockUtils.buildConceptSchemeRef("urn:sdmx:org.sdmx.infomodel.conceptscheme.ConceptScheme=TEST:cshm-01(1.0)");
        Concepts concepts = SrmMockUtils.buildConcepts(3);
        Mockito.when(srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeReference.getURN())).thenReturn(concepts);

        // Create a datastructure with dimensions marked as measure temporal and spatial

        DataStructure dsd = SrmMockUtils.mockDsdWithGeoTimeAndMeasureDimensions("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=TFFS:CRED_EXT_DEBT(1.0)", "GEO_DIM", "TIME_PERIOD",
                "MEAS_DIM", conceptSchemeReference, codelistReference);
        Mockito.when(srmRestInternalService.retrieveDsdByUrn(Mockito.anyString())).thenReturn(dsd);
    }

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
