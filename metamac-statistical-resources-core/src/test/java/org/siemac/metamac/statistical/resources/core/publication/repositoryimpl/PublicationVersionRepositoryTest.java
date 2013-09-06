package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_12_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationVersionRepositoryTest extends StatisticalResourcesBaseTest implements PublicationVersionRepositoryTestBase {

    @Autowired
    private PublicationMockFactory        publicationMockFactory;

    @Autowired
    private PublicationVersionMockFactory publicationVersionMockFactory;

    @Autowired
    private PublicationVersionRepository  publicationVersionRepository;

    @Override
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveByUrn(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME).getSiemacMetadataStatisticalResource()
                .getUrn());
        assertEqualsPublicationVersion(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS));
        publicationVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveLastVersion(publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME)
                .getIdentifiableStatisticalResource().getUrn());
        assertEqualsPublicationVersion(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME})
    public void testRetrieveLastVersionWithAllVersionsPublished() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME);
        PublicationVersion actual = publicationVersionRepository.retrieveLastVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME,
            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastVersionWithLatestVersionNoVisible() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME);
        PublicationVersion actual = publicationVersionRepository.retrieveLastVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersion() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationVersion actual = publicationVersionRepository.retrieveLastPublishedVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersionWithAllVersionsPublished() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_29_V3_PUBLISHED_FOR_PUBLICATION_05_NAME);
        PublicationVersion actual = publicationVersionRepository.retrieveLastPublishedVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_VERSION_12_DRAFT_NAME})
    public void testRetrieveLastPublishedVersionWithoutVersionsPublished() throws Exception {
        String publicationUrn = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_12_DRAFT_NAME).getPublication().getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = null;
        PublicationVersion actual = publicationVersionRepository.retrieveLastPublishedVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME, PUBLICATION_VERSION_12_DRAFT_NAME,
            PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastPublishedVersionWithLatestVersionNoVisible() throws Exception {
        String publicationUrn = publicationMockFactory.retrieveMock(PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME);
        PublicationVersion actual = publicationVersionRepository.retrieveLastPublishedVersion(publicationUrn);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveByVersion(publicationMockFactory.retrieveMock(PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME).getId(),
                publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME).getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsPublicationVersion(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME), actual);
    }

    @Test
    @Override
    @MetamacMock(PUBLICATION_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME)
    public void testRetrieveResourceThatReplacesPublicationVersion() throws Exception {
        PublicationVersion firstVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_30_V1_PUBLISHED_FOR_PUBLICATION_06_NAME);
        PublicationVersion secondVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME);

        {
            RelatedResourceResult resource = publicationVersionRepository.retrieveResourceThatReplacesPublicationVersion(firstVersion);
            assertNotNull(resource);
            CommonAsserts.assertEqualsRelatedResourceResultPublicationVersion(secondVersion, resource);
        }
        {
            RelatedResourceResult resource = publicationVersionRepository.retrieveResourceThatReplacesPublicationVersion(secondVersion);
            assertNull(resource);
        }
    }
}
