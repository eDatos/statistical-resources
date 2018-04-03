package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.junit.Assert.fail;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class MultidatasetRepositoryTest extends AbstractDbUnitJpaTests implements MultidatasetRepositoryTestBase {

    @Autowired
    protected MultidatasetRepository multidatasetRepository;

    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveByUrn not implemented");
    }
}
