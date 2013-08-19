package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEmptyMethod;

import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;

public class QueryLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String TESTING_CLASS = "org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query.QueryLifecycleServiceImpl";

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckSendToProductionValidationResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "checkSendToProductionValidationResource");
    }

    @Override
    @Test
    public void testApplySendToProductionValidationResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToDiffusionValidationResource");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckSendToDiffusionValidationResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "checkSendToDiffusionValidationResource");
    }

    @Override
    @Test
    public void testApplySendToDiffusionValidationResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToDiffusionValidationResource");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckSendToValidationRejectedResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToValidationRejectedResource");
    }

    @Override
    @Test
    public void testApplySendToValidationRejectedResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applySendToValidationRejectedResource");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Ignore
    @Override
    @Test
    public void testCheckSendToPublishedResource() throws Exception {
        // TODO:
        fail("not implemented");
    }

    @Ignore
    @Override
    @Test
    public void testApplySendToPublishedResource() throws Exception {
        // TODO:
        // - cumplimentar format_extent_resources
        
        fail("not implemented");
    }


    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Ignore
    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        // TODO Auto-generated method stub
        fail("not implemented");
    }

    @Ignore
    @Override
    @Test
    public void testApplyVersioningNewResource() throws Exception {
        // TODO Auto-generated method stub
        fail("not implemented");
    }
    
    @Ignore
    @Override
    @Test
    public void testApplyVersioningPreviousResource() throws Exception {
        // TODO Auto-generated method stub
        fail("not implemented");
    }
}
