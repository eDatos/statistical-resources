package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.publication.domain.CubeRepository;
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
public class CubeRepositoryTest extends StatisticalResourcesBaseTest implements CubeRepositoryTestBase {

    @Autowired
    protected CubeRepository cubeRepository;

    @Override
    @Test
    public void testRetrieveCubeByUrn() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.retrieveCubeByUrn(URN_NOT_EXISTS);
        // TODO Auto-generated method stub
        
    }

    @Override
    @Test
    public void testRetrieveCubePublishedByCode() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        cubeRepository.retrieveCubePublishedByCode(CODE_NOT_EXISTS);
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
