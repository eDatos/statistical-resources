package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertRelaxedEqualsCube;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_03_BASIC_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.CubeRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
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
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class CubeRepositoryTest extends StatisticalResourcesBaseTest implements CubeRepositoryTestBase {

    @Autowired
    private CubeRepository cubeRepository;
    
    @Autowired
    private CubeMockFactory cubeMockFactory;

    @Override
    @Test
    @MetamacMock({CUBE_01_BASIC_NAME, CUBE_02_BASIC_NAME, CUBE_03_BASIC_NAME})
    public void testRetrieveCubeByUrn() throws Exception {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_01_BASIC_NAME);
        Cube actual = cubeRepository.retrieveCubeByUrn(expected.getNameableStatisticalResource().getUrn());
        assertRelaxedEqualsCube(expected, actual);
    }
    
    @Test
    @MetamacMock({CUBE_01_BASIC_NAME, CUBE_02_BASIC_NAME, CUBE_03_BASIC_NAME})
    public void testRetrieveChapterByUrnErrorNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.CUBE_NOT_FOUND, URN_NOT_EXISTS));
        cubeRepository.retrieveCubeByUrn(URN_NOT_EXISTS);
    }

    @Override
    @Test
    public void testRetrieveCubePublishedByCode() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.retrieveCubePublishedByCode(null);
        // TODO Auto-generated method stub
    }

    @Override
    @Test
    public void testExistAnyCube() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.existAnyCube(null, null);
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindDatasetsLinkedWithPublicationVersion() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.findDatasetsLinkedWithPublicationVersion(null);
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testRetrieveCubeInPublishedPublication() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.retrieveCubeInPublishedPublication(null, null);
        // TODO Auto-generated method stub

    }

    @Override
    @Test
    public void testFindCubesInPublishedPublication() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.findCubesInPublishedPublication(null);
        // TODO Auto-generated method stub

    }
}
