package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import static org.junit.Assert.fail;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class MultidatasetRepositoryTest extends AbstractDbUnitJpaTests
    implements MultidatasetRepositoryTestBase {
    @Autowired
    protected MultidatasetRepository multidatasetRepository;

    @Test
    public void testRetrieveByUrn() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveByUrn not implemented");
    }
}
