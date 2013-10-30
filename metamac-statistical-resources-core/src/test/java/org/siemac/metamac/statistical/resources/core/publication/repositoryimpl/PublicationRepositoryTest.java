package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublication;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory.PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationRepositoryTest extends StatisticalResourcesBaseTest implements PublicationRepositoryTestBase {

    @Autowired
    protected PublicationRepository publicationRepository;

    @Override
    @Test
    @MetamacMock(PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME)
    public void testRetrieveByUrn() throws Exception {
        Publication expected = publicationMockFactory.retrieveMock(PUBLICATION_02_BASIC_WITH_GENERATED_VERSION_NAME);
        Publication actual = publicationRepository.retrieveByUrn(expected.getIdentifiableStatisticalResource().getUrn());
        assertEqualsPublication(expected, actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, URN_NOT_EXISTS));
        publicationRepository.retrieveByUrn(URN_NOT_EXISTS);
    }
}
