package org.siemac.metamac.statistical.resources.core.publication.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.*;
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
    private PublicationMockFactory                  publicationMockFactory;

    @Autowired
    private PublicationVersionMockFactory           publicationVersionMockFactory;

    @Autowired
    private PublicationService                      publicationService;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

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
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION), 1);

        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    public void testCreatePublicationVersionErrorMetadataSiemacStatisticalResourceRequired() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE), 1);
        PublicationVersion expected = statisticalResourcesNotPersistedDoMocks.mockPublicationVersionWithNullableSiemacStatisticalResource();
        publicationService.createPublicationVersion(getServiceContextWithoutPrincipal(), expected);
    }

    @Override
    @SuppressWarnings("static-access")
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testUpdatePublicationVersion() throws Exception {
        PublicationVersion expected = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_01_BASIC_NAME);
        expected.getSiemacMetadataStatisticalResource().setTitle(statisticalResourcesNotPersistedDoMocks.mockInternationalString());
        expected.getSiemacMetadataStatisticalResource().setDescription(statisticalResourcesNotPersistedDoMocks.mockInternationalString());

        PublicationVersion actual = publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorFinal() throws Exception {
        PublicationVersion finalPublication = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, finalPublication.getSiemacMetadataStatisticalResource().getUrn()), 1);

        publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), finalPublication);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testUpdatePublicationVersionErrorIncorrectCode() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_INCORRECT, ServiceExceptionParameters.PUBLICATION_VERSION__SIEMAC_METADATA_STATISTICAL_RESOURCE__CODE), 1);

        PublicationVersion publication = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        publication.getSiemacMetadataStatisticalResource().setCode("@12345");
        publicationService.updatePublicationVersion(getServiceContextWithoutPrincipal(), publication);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrn() throws Exception {
        String urn = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME), actual);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnV2() throws Exception {
        String urn = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();
        PublicationVersion actual = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
        assertEqualsPublicationVersion(publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    public void testRetrievePublicationVersionByUrnErrorParameterRequiered() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PARAMETER_REQUIRED, ServiceExceptionSingleParameters.URN), 1);

        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), null);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrievePublicationVersionByUrnErrorNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS), 1);

        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testRetrievePublicationVersions() throws Exception {
        String urn = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        List<PublicationVersion> actual = publicationService.retrievePublicationVersions(getServiceContextWithoutPrincipal(), urn);

        assertEquals(2, actual.size());
        assertEqualsPublicationVersionCollection(publicationMockFactory.getMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getVersions(), actual);
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
            assertEquals(publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(), publicationVersionPagedResult.getValues().get(0)
                    .getSiemacMetadataStatisticalResource().getUrn());
        }

        {
            // Find by version number
            List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                    .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq("02.000")
                    .orderBy(PublicationVersionProperties.siemacMetadataStatisticalResource().id()).ascending().build();

            PagingParameter pagingParameter = PagingParameter.rowAccess(0, Integer.MAX_VALUE, true);
            PagedResult<PublicationVersion> publicationVersionPagedResult = publicationService.findPublicationVersionsByCondition(getServiceContextWithoutPrincipal(), conditions, pagingParameter);
            assertEquals(1, publicationVersionPagedResult.getTotalRows());
            assertEquals(publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn(), publicationVersionPagedResult.getValues().get(0)
                    .getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_01_BASIC_NAME, PUBLICATION_VERSION_01_BASIC_NAME})
    public void testDeletePublicationVersion() throws Exception {
        String urn = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urn), 1);

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urn);

        // Validation
        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urn);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME})
    public void testDeletePublicationVersionWithTwoVersions() throws Exception {
        String urnV1 = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();
        String urnV2 = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getUrn();

        // Delete publication version
        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV2);

        // Validation
        PublicationVersion publicationVersionV1 = publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV1);
        assertTrue(publicationVersionV1.getSiemacMetadataStatisticalResource().getIsLastVersion());
        assertNull(publicationVersionV1.getSiemacMetadataStatisticalResource().getIsReplacedBy());

        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urnV2), 1);
        publicationService.retrievePublicationVersionByUrn(getServiceContextWithoutPrincipal(), urnV2);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNotExists() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS), 1);

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testDeletePublicationVersionErrorNoLastVersion() throws Exception {
        String urnV1 = publicationVersionMockFactory.getMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME).getSiemacMetadataStatisticalResource().getUrn();

        expectedMetamacException(new MetamacException(ServiceExceptionType.LIFE_CYCLE_STATISTICAL_RESOURCE_NOT_MODIFIABLE, urnV1), 1);

        publicationService.deletePublicationVersion(getServiceContextWithoutPrincipal(), urnV1);
    }

    @Override
    @Test
    public void testVersioningPublicationVersion() throws Exception {
        fail("not implemented");
    }
}
