package org.siemac.metamac.statistical.resources.core.publication.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionNotChecksPublication;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertFilledMetadataForChaptersInAllLevels;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertFilledMetadataForChaptersInFirstLevel;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertFilledMetadataForChaptersInNoFirstLevel;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertRelaxedEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_12_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_16_PUBLISHED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED_NAME;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PublicationServiceTest extends StatisticalResourcesBaseTest implements PublicationServiceTestBase {

    @Autowired
    private PublicationMockFactory                  publicationMockFactory;

    @Autowired
    private PublicationVersionMockFactory           publicationVersionMockFactory;

    @Autowired
    private PublicationService                      publicationService;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Autowired
    private final PlatformTransactionManager        transactionManager = null;

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreatePublicationVersion() throws Exception {
        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();

        PublicationVersion actual = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        String operationCode = actual.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000001", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(
                buildPublicationVersionUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 1, expected.getSiemacMetadataStatisticalResource()
                        .getVersionLogic()), actual.getSiemacMetadataStatisticalResource().getUrn());

        assertEqualsPublicationVersionNotChecksPublication(expected, actual);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME)
    public void testCreatePublicationVersionInSameOperationThatAlreadyExistsPublications() throws Exception {
        PublicationVersion publicationVersionOperation01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        String operationCode = publicationVersionOperation01.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        PublicationVersion actual = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
        assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
        assertEquals(
                buildPublicationVersionUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 4, expected.getSiemacMetadataStatisticalResource()
                        .getVersionLogic()), actual.getSiemacMetadataStatisticalResource().getUrn());

        assertEqualsPublicationVersionNotChecksPublication(expected, actual);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME)
    public void testCreateConsecutivesPublicationVersionInSameOperationThatAlreadyExistsPublications() throws Exception {
        PublicationVersion publicationVersionOperation01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_07_OPERATION_0001_CODE_000003_NAME);
        String operationCode = publicationVersionOperation01.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
            PublicationVersion actual = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);

            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000004", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(
                    buildPublicationVersionUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 4, expected.getSiemacMetadataStatisticalResource()
                            .getVersionLogic()), actual.getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsPublicationVersionNotChecksPublication(expected, actual);
        }
        {
            ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
            PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
            PublicationVersion actual = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);

            assertEquals("001.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());
            assertEquals(operationCode + "_000005", actual.getSiemacMetadataStatisticalResource().getCode());
            assertEquals(
                    buildPublicationVersionUrn(expected.getSiemacMetadataStatisticalResource().getMaintainer().getCode(), operationCode, 5, expected.getSiemacMetadataStatisticalResource()
                            .getVersionLogic()), actual.getSiemacMetadataStatisticalResource().getUrn());
            assertEqualsPublicationVersionNotChecksPublication(expected, actual);
        }
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX_NAME)
    public void testCreatePublicationVersionMaxCodeReached() throws Exception {
        PublicationVersion publicationVersionOperation01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_11_OPERATION_0002_CODE_MAX_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_MAX_REACHED_IN_OPERATION, publicationVersionOperation01.getSiemacMetadataStatisticalResource()
                .getStatisticalOperation().getUrn()));

        String operationCode = publicationVersionOperation01.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode();
        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode);
        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), statisticalResourcesNotPersistedDoMocks.mockPublicationVersion(), statisticalOperation);
    }

    @Test
    public void testCreatePublicationVersionErrorParameterPublicationRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), null, statisticalOperation);
    }

    @Test
    public void testCreatePublicationVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE));

        ExternalItem statisticalOperation = StatisticalResourcesNotPersistedDoMocks.mockStatisticalOperationExternalItem();
        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersionWithNullableSiemacStatisticalResource();
        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, statisticalOperation);
    }

    @Test
    public void testCreateDatasetVersionErrorParameterStatisticalOperationRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED,
                ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__STATISTICAL_OPERATION));

        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();
        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected, null);
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublicationVersion() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorFinal() throws Exception {
        PublicationVersion finalPublication = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, finalPublication.getSiemacMetadataStatisticalResource().getUrn(),
                "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));

        publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), finalPublication);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorIncorrectCode() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE));

        PublicationVersion publication = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        publication.getSiemacMetadataStatisticalResource().setCode("@12345");
        publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), publication);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testUpdatePublicationVersionDraftProcStatus() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testUpdatePublicationVersionProductionValidationProcStatus() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testUpdatePublicationVersionDifussionValidationProcStatus() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testUpdatePublicationVersionValidationRejectedProcStatus() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testUpdatePublicationVersionPublishedProcStatus() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, expected.getSiemacMetadataStatisticalResource().getUrn(),
                "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));

        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrn() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME), actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnV2() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    public void testRetrievePublicationVersionByUrnErrorParameterRequiered() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN));

        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnErrorNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS));

        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testRetrievePublicationVersions() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        List<PublicationVersion> actual = publicationService.retrievePublicationVersions(getServiceContextWithoutPrincipal(), urn);

        assertEquals(2, actual.size());
        assertEqualsPublicationVersionCollection(publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getVersions(), actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testFindPublicationVersionsByCondition() throws Exception {

        {
            // Find by version number
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                    .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq("002.000")
                    .orderBy(PublicationVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<PublicationVersion> publicationVersionPagedResult = publicationService.findPublicationVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, publicationVersionPagedResult.getTotalRows());
            assertEquals(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                    publicationVersionPagedResult.getValues().get(0).getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testDeletePublicationVersion() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urn));

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);

        // Validation
        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testDeletePublicationVersionWithTwoVersions() throws Exception {
        String urnV1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String urnV2 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV2);

        // Validation
        PublicationVersion publicationVersionV1 = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV1);
        assertNull(publicationVersionV1.getSiemacMetadataStatisticalResource().getIsReplacedBy());

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urnV2));
        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV2);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS));

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNoLastVersion() throws Exception {
        String urnV1 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urnV1, "DRAFT, VALIDATION_REJECTED"));

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV1);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testDeletePublicationVersionDraftProcStatus() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testDeletePublicationVersionProductionValidationProcStatus() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testDeletePublicationVersionDiffusionValidationProcStatus() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testDeletePublicationVersionValidationRejectedProcStatus() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME, PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME, PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME,
            PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME, PUBLICATION_VERSION_16_PUBLISHED_NAME})
    public void testDeletePublicationVersionPublishedProcStatus() throws Exception {
        String urn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME).getSiemacMetadataStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, urn, "DRAFT, VALIDATION_REJECTED"));

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);
    }

    @Override
    @Test
    @MetamacMock(PUBLICATION_VERSION_16_PUBLISHED_NAME)
    public void testVersioningPublicationVersion() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_16_PUBLISHED_NAME);

        PublicationVersion publicationNewVersion = publicationService.versioningPublicationVersion(getServiceContextWithoutPrincipal(), publicationVersion.getSiemacMetadataStatisticalResource()
                .getUrn(), VersionTypeEnum.MINOR);
        assertNotNull(publicationNewVersion);
        assertFalse(publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic().equals(publicationNewVersion.getSiemacMetadataStatisticalResource().getVersionLogic()));
        checkNewPublicationVersionCreated(publicationVersion, publicationNewVersion);
    }

    private static void checkNewPublicationVersionCreated(PublicationVersion previous, PublicationVersion next) {
        BaseAsserts.assertEqualsVersioningSiemacMetadata(previous.getSiemacMetadataStatisticalResource(), next.getSiemacMetadataStatisticalResource());

        // TODO: Añadir la jerarquia

        // Non inherited fields

        // Inherited
        assertEquals(previous.getFormatExtentResources(), next.getFormatExtentResources());
    }

    private Object buildPublicationVersionUrn(String maintainerCode, String operationCode, int datasetSequentialId, String versionNumber) {
        StringBuilder strBuilder = new StringBuilder("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Collection=");
        strBuilder.append(maintainerCode).append(":").append(operationCode).append("_").append(String.format("%06d", datasetSequentialId)).append("(").append(versionNumber).append(")");
        return strBuilder.toString();
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapter() throws Exception {
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME)
                .getSiemacMetadataStatisticalResource().getUrn(), expected);

        assertRelaxedEqualsChapter(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterInFirstLevel() throws Exception {
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);

        assertFilledMetadataForChaptersInAllLevels(actual);
        assertFilledMetadataForChaptersInFirstLevel(actual);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterInNoFirstLevel() throws Exception {
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapterInParentElementLevel(publicationVersionMockFactory
                .retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getChildrenFirstLevel().get(0));
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);

        assertFilledMetadataForChaptersInAllLevels(actual);
        assertFilledMetadataForChaptersInNoFirstLevel(actual);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterWithOrderFirst() throws Exception {
        // Create transaction
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        // Create chapter
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(Long.valueOf(1));
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);

        // Finish transaction
        transactionManager.commit(status);

        // Retrieve children and check orderInLevel
        List<ElementLevel> actualChildren = publicationService.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn())
                .getChildrenFirstLevel();

        assertEquals(actualChildren.get(0).getId(), actual.getElementLevel().getId());

        assertEquals(Long.valueOf(1), actualChildren.get(0).getOrderInLevel());
        assertEquals(Long.valueOf(2), actualChildren.get(1).getOrderInLevel());
        assertEquals(Long.valueOf(3), actualChildren.get(2).getOrderInLevel());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterWithOrderLast() throws Exception {
        // Create transaction
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        // Create chapter
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(Long.valueOf(3));
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);

        // Finish transaction
        transactionManager.commit(status);

        // Retrieve children and check orderInLevel
        List<ElementLevel> actualChildren = publicationService.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn())
                .getChildrenFirstLevel();

        assertEquals(Long.valueOf(1), actualChildren.get(0).getOrderInLevel());
        assertEquals(Long.valueOf(2), actualChildren.get(1).getOrderInLevel());
        assertEquals(Long.valueOf(3), actualChildren.get(2).getOrderInLevel());

        assertEquals(actualChildren.get(2).getId(), actual.getElementLevel().getId());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterWithOrderMiddle() throws Exception {
        // Create transaction
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);

        // Create chapter
        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(Long.valueOf(2));
        Chapter actual = publicationService.createChapter(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(),
                expected);

        // Finish transaction
        transactionManager.commit(status);

        // Retrieve children and check orderInLevel
        List<ElementLevel> actualChildren = publicationService.retrievePublicationVersionByUrn(getServiceContextAdministrador(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn())
                .getChildrenFirstLevel();

        assertEquals(Long.valueOf(1), actualChildren.get(0).getOrderInLevel());
        assertEquals(Long.valueOf(2), actualChildren.get(1).getOrderInLevel());
        assertEquals(Long.valueOf(3), actualChildren.get(2).getOrderInLevel());

        assertEquals(actualChildren.get(1).getId(), actual.getElementLevel().getId());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorParameterRequiredPublicationVersionUrn() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION_URN));
        publicationService.createChapter(getServiceContextAdministrador(), null, statisticalResourcesNotPersistedDoMocks.mockChapter());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorParameterRequiredChapter() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CHAPTER));
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn(), null);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorMetadataIncorrectOrderInLevelNegative() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__ORDER_IN_LEVEL));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(Long.valueOf(-1));
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn(), expected);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorMetadataIncorrectOrderInLevelMaxValue() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__ORDER_IN_LEVEL));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(Long.MAX_VALUE);
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn(), expected);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorMetadataRequiredOrderInLevel() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__ORDER_IN_LEVEL));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setOrderInLevel(null);
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn(), expected);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME})
    public void testCreateChapterErrorMetadataRequiredPublicationVersion() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__PUBLICATION_VERSION));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expected.getElementLevel().setPublicationVersion(null);
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn(), expected);
    }

    @Test
    public void testCreateChapterErrorPublicationVersionNotExists() throws Exception {
        String publicationVersionUrn = URN_NOT_EXISTS;
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, publicationVersionUrn));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, expected);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_12_DRAFT_NAME})
    public void testCreateChapterStatusPublicationVersionDraft() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, statisticalResourcesNotPersistedDoMocks.mockChapter());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME})
    public void testCreateChapterStatusPublicationVersionProductionValidation() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_13_PRODUCTION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, statisticalResourcesNotPersistedDoMocks.mockChapter());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME})
    public void testCreateChapterStatusPublicationVersionDiffusionValidation() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_14_DIFFUSION_VALIDATION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, statisticalResourcesNotPersistedDoMocks.mockChapter());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME})
    public void testCreateChapterStatusPublicationVersionValidationRejected() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_15_VALIDATION_REJECTED_NAME).getSiemacMetadataStatisticalResource().getUrn();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, statisticalResourcesNotPersistedDoMocks.mockChapter());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testCreateChapterStatusPublicationVersionPublished() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME).getSiemacMetadataStatisticalResource()
                .getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn,
                "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapter();
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, expected);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterStatusPublicationErrorParentNotExists() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn();
        Chapter parentChapter = statisticalResourcesNotPersistedDoMocks.mockChapter();
        expectedMetamacException(new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION, parentChapter.getNameableStatisticalResource().getUrn(), publicationVersionUrn));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapterInParentElementLevel(parentChapter.getElementLevel());
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, expected);
    }
    
    
    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testCreateChapterStatusPublicationErrorParentNotExistsInPublicationVersion() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME)
                .getSiemacMetadataStatisticalResource().getUrn();
        Chapter parentChapter = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME).getChildrenFirstLevel().get(0).getChapter();
        expectedMetamacException(new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION, parentChapter.getNameableStatisticalResource().getUrn(), publicationVersionUrn));

        Chapter expected = statisticalResourcesNotPersistedDoMocks.mockChapterInParentElementLevel(parentChapter.getElementLevel());
        publicationService.createChapter(getServiceContextAdministrador(), publicationVersionUrn, expected);
    }
    

    @SuppressWarnings("static-access")
    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testUpdateChapter() throws Exception {
        Chapter expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME).getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        Chapter actual = publicationService.updateChapter(getServiceContextAdministrador(), expected);
        
        assertRelaxedEqualsChapter(expected, actual);
        CommonAsserts.assertEqualsInternationalString(expected.getNameableStatisticalResource().getTitle(), actual.getNameableStatisticalResource().getTitle());
    }
    
    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testUpdateChapterErrorParameterRequiredChapter() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.CHAPTER));
        publicationService.updateChapter(getServiceContextAdministrador(), null);
    }

    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME})
    public void testUpdateChapterStatusDraft() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_18_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_AND_LAST_VERSION_NAME);
        Chapter expected = publicationVersion.getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        publicationService.updateChapter(getServiceContextAdministrador(), expected);
    }
    
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION_NAME})
    public void testUpdateChapterStatusProductionValidation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_19_WITH_STRUCTURE_PRODUCTION_VALIDATION_NAME);
        Chapter expected = publicationVersion.getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        publicationService.updateChapter(getServiceContextAdministrador(), expected);
    }
    
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION_NAME})
    public void testUpdateChapterStatusDiffusionValidation() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_20_WITH_STRUCTURE_DIFFUSION_VALIDATION_NAME);
        Chapter expected = publicationVersion.getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        publicationService.updateChapter(getServiceContextAdministrador(), expected);
    }
    
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED_NAME})
    public void testUpdateChapterStatusValidationRejected() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_21_WITH_STRUCTURE_VALIDATION_REJECTED_NAME);
        Chapter expected = publicationVersion.getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        publicationService.updateChapter(getServiceContextAdministrador(), expected);
    }
    
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME})
    public void testUpdateChapterStatusPublished() throws Exception {
        String publicationVersionUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME).getSiemacMetadataStatisticalResource().getUrn();
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_WRONG_PROC_STATUS, publicationVersionUrn, "DRAFT, VALIDATION_REJECTED, PRODUCTION_VALIDATION, DIFFUSION_VALIDATION"));
        
        Chapter expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_17_WITH_STRUCTURE_FOR_PUBLICATION_VERSION_04_NAME).getChildrenFirstLevel().get(0).getChapter();
        expected.getNameableStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        
        publicationService.updateChapter(getServiceContextAdministrador(), expected);
    }

    @Override
    @Test
    public void testUpdateChapterLocation() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.updateChapterLocation(getServiceContextAdministrador(), null, null, null);
    }

    @Override
    @Test
    public void testRetrieveChapter() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.retrieveChapter(getServiceContextAdministrador(), null);
    }

    @Override
    @Test
    public void testDeleteChapter() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.deleteChapter(getServiceContextAdministrador(), null);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreateCube() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.createCube(getServiceContextAdministrador(), null, null);
    }

    @Override
    @Test
    public void testUpdateCube() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.updateCube(getServiceContextAdministrador(), null);
    }

    @Override
    @Test
    public void testUpdateCubeLocation() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.updateCubeLocation(getServiceContextAdministrador(), null, null, null);
    }

    @Override
    @Test
    public void testRetrieveCube() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.retrieveCube(getServiceContextAdministrador(), null);
    }

    @Override
    @Test
    public void testDeleteCube() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        publicationService.deleteCube(getServiceContextAdministrador(), null);
    }
}
