package org.siemac.metamac.statistical.resources.core.multidataset.serviceapi;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import static org.junit.Assert.fail;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
public class MultidatasetServiceTest extends AbstractDbUnitJpaTests
    implements MultidatasetServiceTestBase {
    @Autowired
    protected MultidatasetService multidatasetService;

    @Test
    public void testCreateMultidatasetVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testCreateMultidatasetVersion not implemented");
    }

    @Test
    public void testUpdateMultidatasetVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testUpdateMultidatasetVersion not implemented");
    }

    @Test
    public void testRetrieveMultidatasetVersionByUrn() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveMultidatasetVersionByUrn not implemented");
    }

    @Test
    public void testRetrieveLatestMultidatasetVersionByMultidatasetUrn()
        throws Exception {
        // TODO Auto-generated method stub
        fail(
            "testRetrieveLatestMultidatasetVersionByMultidatasetUrn not implemented");
    }

    @Test
    public void testRetrieveLatestPublishedMultidatasetVersionByMultidatasetUrn()
        throws Exception {
        // TODO Auto-generated method stub
        fail(
            "testRetrieveLatestPublishedMultidatasetVersionByMultidatasetUrn not implemented");
    }

    @Test
    public void testRetrieveMultidatasetVersions() throws Exception {
        // TODO Auto-generated method stub
        fail("testRetrieveMultidatasetVersions not implemented");
    }

    @Test
    public void testFindMultidatasetVersionsByCondition() throws Exception {
        // TODO Auto-generated method stub
        fail("testFindMultidatasetVersionsByCondition not implemented");
    }

    @Test
    public void testDeleteMultidatasetVersion() throws Exception {
        // TODO Auto-generated method stub
        fail("testDeleteMultidatasetVersion not implemented");
    }

    @Test
    public void testImportMultidatasetVersionStructure() throws Exception {
        // TODO Auto-generated method stub
        fail("testImportMultidatasetVersionStructure not implemented");
    }
}
