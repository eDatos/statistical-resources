package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PublicationRepositoryTest extends StatisticalResourcesBaseTest implements PublicationRepositoryTestBase {

    @Autowired
    private PublicationRepository         publicationRepository;

    @Autowired
    private PublicationVersionMockFactory publicationVersionMockFactory;

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003_NAME})
    public void testFindLastPublicationCode() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001_NAME);
        String code = publicationRepository.findLastPublicationCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0003", code);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001_NAME, PUBLICATION_VERSION_06_OPERATION_0001_CODE_0002_NAME, PUBLICATION_VERSION_07_OPERATION_0001_CODE_0003_NAME,
            PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001_NAME, PUBLICATION_VERSION_09_OPERATION_0002_CODE_0002_NAME, PUBLICATION_VERSION_10_OPERATION_0002_CODE_0003_NAME})
    public void testFindLastPublicationCodeMultipleOperations() throws Exception {
        PublicationVersion publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_05_OPERATION_0001_CODE_0001_NAME);
        String code = publicationRepository.findLastPublicationCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0003", code);

        publicationVersion = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_08_OPERATION_0002_CODE_0001_NAME);
        code = publicationRepository.findLastPublicationCode(publicationVersion.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        assertNotNull(code);
        assertEquals("0003", code);

        code = publicationRepository.findLastPublicationCode(CODE_NOT_EXISTS);
        assertNull(code);
    }
}
