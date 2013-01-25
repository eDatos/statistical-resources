package org.siemac.metamac.statistical.resources.core.publication.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionCollection;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.DatasourceMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationServiceTest extends StatisticalResourcesBaseTest implements PublicationServiceTestBase {

    @Autowired
    protected PublicationService                      publicationService;

    @Autowired
    protected PublicationVersionMockFactory           publicationVersionMockFactory;

    @Autowired
    protected PublicationMockFactory                  publicationMockFactory;

    @Autowired
    protected DatasourceMockFactory                   datasourceMockFactory;

    @Autowired
    protected StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    // ------------------------------------------------------------------------
    // PUBLICATIONS
    // ------------------------------------------------------------------------

    @Override
    @Test
    public void testCreatePublicationVersion() throws Exception {
        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersion();

        PublicationVersion actual = publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEquals("01.000", actual.getSiemacMetadataStatisticalResource().getVersionLogic());

        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    public void testCreatePublicationVersionErrorParameterPublicationRequired() throws Exception {
        try {
            publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), null);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionParameters.PUBLICATION_VERSION}, e.getExceptionItems().get(0));
        }
    }

    @Test
    public void testCreatePublicationVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        try {
            PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersionWithNullableSiemacStatisticalResource();
            publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_REQUIRED, 1, new String[]{ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublicationVersion() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC;
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorFinal() throws Exception {
        PublicationVersion finalPublication = publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03;

        try {
            publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), finalPublication);
            fail("publication version can be edited");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, 1, new String[]{finalPublication.getSiemacMetadataStatisticalResource().getUrn()}, e
                    .getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorIncorrectCode() throws Exception {
        PublicationVersion publication = publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;

        try {
            publication.getSiemacMetadataStatisticalResource().setCode("12345");
            publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), publication);
            fail("incorrect code");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.METADATA_INCORRECT, 1, new String[]{ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE}, e
                    .getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorDuplicatedCode() throws Exception {
        PublicationVersion publication = publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION;
        String duplicatedCode = publicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getCode();

        try {
            publication.getSiemacMetadataStatisticalResource().setCode(duplicatedCode);
            publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), publication);
            fail("duplicated code");
            // TODO: NOTA MENTAL: Es normal que este test no falle hasta que en el servicio se cumplimenten correctamente las URN en función de los CODE
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.IDENTIFIABLE_STATISTICAL_RESOURCE_URN_DUPLICATED, 1, new String[]{duplicatedCode}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrn() throws Exception {
        String urn = publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03.getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnV2() throws Exception {
        String urn = publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION, actual);
    }

    @Test
    public void testRetrievePublicationVersionByUrnErrorParameterRequiered() throws Exception {
        try {
            publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), null);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PARAMETER_REQUIRED, 1, new String[]{ServiceExceptionSingleParameters.URN}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnErrorNotFound() throws Exception {
        try {
            publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testRetrievePublicationVersions() throws Exception {
        String urn = publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03.getSiemacMetadataStatisticalResource().getUrn();
        List<PublicationVersion> actual = publicationService.retrievePublicationVersions(getServiceContextWithoutPrincipal(), urn);

        assertEquals(2, actual.size());
        assertEqualsPublicationVersionCollection(publicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS.getVersions(), actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testFindPublicationVersionsByCondition() throws Exception {
        {
            // Find by last version
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                    .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().isLastVersion()).eq(Boolean.TRUE)
                    .orderBy(PublicationVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<PublicationVersion> publicationVersionPagedResult = publicationService.findPublicationVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, publicationVersionPagedResult.getTotalRows());
            assertEquals(publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn(), publicationVersionPagedResult
                    .getValues().get(0).getSiemacMetadataStatisticalResource().getUrn());
        }

        {
            // Find by version number
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                    .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq("02.000")
                    .orderBy(PublicationVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<PublicationVersion> publicationVersionPagedResult = publicationService.findPublicationVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, publicationVersionPagedResult.getTotalRows());
            assertEquals(publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn(), publicationVersionPagedResult
                    .getValues().get(0).getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testDeletePublicationVersion() throws Exception {
        String urn = publicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn();

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);

        // Validation
        try {
            publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
            fail("publicationVersion deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, 1, new String[]{urn}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testDeletePublicationVersionWithTwoVersions() throws Exception {
        String urnV1 = publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03.getSiemacMetadataStatisticalResource().getUrn();
        String urnV2 = publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getUrn();

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV2);

        // Validation
        try {
            publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV2);
            fail("publicationVersion deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, 1, new String[]{urnV2}, e.getExceptionItems().get(0));
        }

        PublicationVersion publicationVersionV1 = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV1);
        assertTrue(publicationVersionV1.getSiemacMetadataStatisticalResource().getIsLastVersion());
        assertNull(publicationVersionV1.getSiemacMetadataStatisticalResource().getIsReplacedBy());
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNotExists() throws Exception {
        try {
            publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
            fail("publicationVersion not exists");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNoLastVersion() throws Exception {
        String urnV1 = publicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03.getSiemacMetadataStatisticalResource().getUrn();

        try {
            publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV1);
            fail("publicationVersion can not be deleted");
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, 1, new String[]{urnV1}, e.getExceptionItems().get(0));
        }
    }

    @Override
    @Test
    public void testVersioningPublicationVersion() throws Exception {
        fail("not implemented");

    }
}
