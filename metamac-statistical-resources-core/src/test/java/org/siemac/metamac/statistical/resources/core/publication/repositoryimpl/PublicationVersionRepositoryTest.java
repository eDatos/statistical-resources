package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.common.test.utils.MetamacAsserts.assertEqualsMetamacExceptionItem;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.mocks.MetamacMock;
import org.siemac.metamac.statistical.resources.core.mocks.PublicationMockFactory;
import org.siemac.metamac.statistical.resources.core.mocks.PublicationVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
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
public class PublicationVersionRepositoryTest extends StatisticalResourcesBaseTest implements PublicationVersionRepositoryTestBase {

    @Autowired
    protected PublicationVersionRepository  publicationVersionRepository;

    @Autowired
    protected PublicationVersionMockFactory publicationVersionMockFactory;

    @Autowired
    protected PublicationMockFactory        publicationMockFactory;

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME})
    public void testRetrieveByUrn() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveByUrn(publicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC.getSiemacMetadataStatisticalResource().getUrn());
        assertEqualsPublicationVersion(publicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC, actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        try {
            publicationVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
        } catch (MetamacException e) {
            assertEquals(1, e.getExceptionItems().size());
            assertEqualsMetamacExceptionItem(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, 1, new String[]{URN_NOT_EXISTS}, e.getExceptionItems().get(0));
        }
    }

    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveLastVersion(publicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS.getId());
        assertEqualsPublicationVersion(publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME, PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS_NAME})
    public void testRetrieveByVersion() throws Exception {
        PublicationVersion actual = publicationVersionRepository.retrieveByVersion(publicationMockFactory.PUBLICATION_03_BASIC_WITH_2_PUBLICATION_VERSIONS.getId(),
                publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION.getSiemacMetadataStatisticalResource().getVersionLogic());
        assertEqualsPublicationVersion(publicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION, actual);
    }
}
