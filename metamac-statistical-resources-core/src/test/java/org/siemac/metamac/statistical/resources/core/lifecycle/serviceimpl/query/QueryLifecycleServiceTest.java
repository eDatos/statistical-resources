package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;

import static org.junit.Assert.fail;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEmptyMethod;

public class QueryLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String         TESTING_CLASS         = "org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.query.QueryLifecycleServiceImpl";

    @InjectMocks
    protected QueryLifecycleServiceImpl queryLifecycleService = new QueryLifecycleServiceImpl();

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
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");
    }

    @Ignore
    @Override
    public void testApplySendToPublishedCurrentResource() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");
    }

    @Ignore
    @Override
    public void testApplySendToPublishedPreviousResource() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "checkVersioningResource");
    }

    @Override
    @Test
    public void testApplyVersioningNewResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applyVersioningNewResource");
    }

    @Override
    @Test
    public void testApplyVersioningPreviousResource() throws Exception {
        assertEmptyMethod(TESTING_CLASS, "applyVersioningPreviousResource");
    }

    @Ignore
    @Override
    @Test
    public void testCopyResourceForVersioning() throws Exception {
        // TODO: Implementar (METAMAC-2143)
        fail("not implemented");
    }
}
