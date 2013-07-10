package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts.assertEmptyMethod;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleServiceBaseTest;

/**
 * Spring based transactional test with DbUnit support.
 */
public class PublicationLifecycleServiceTest extends StatisticalResourcesBaseTest implements LifecycleServiceBaseTest {

    private static final String TESTING_CLASS = "org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.publication.PublicationLifecycleServiceImpl";

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

    @Override
    @Test
    public void testCheckSendToPublishedResource() throws Exception {
        // TODO:
        // - Comprobar que al menos existe un cubo por capítulo
        // - comprobar que almenos existe un cubo en la publicación
        // - compobar que todos los datasets y consultas vinculados a cubos están publicados

        fail("not implemented");
    }

    @Override
    @Test
    public void testApplySendToPublishedResource() throws Exception {
        // TODO:
        // - cumplimentar format_extent_resources
        
        fail("not implemented");
    }

    @Override
    @Test
    public void testCheckSendToPublishedLinkedStatisticalResource() throws Exception {
        // TODO Auto-generated method stub
        // TODO: esto debe ser eliminado
    }

    @Override
    @Test
    public void testApplySendToPublishedLinkedStatisticalResource() throws Exception {
        // TODO Auto-generated method stub
        // TODO: esto debe ser eliminado
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    @Test
    public void testCheckVersioningResource() throws Exception {
        // TODO Auto-generated method stub
        fail("not implemented");
    }

    @Override
    @Test
    public void testApplyVersioningResource() throws Exception {
        // TODO Auto-generated method stub
        fail("not implemented");
    }

    @Override
    @Test
    public void testCheckVersioningLinkedStatisticalResource() throws Exception {
        // TODO Auto-generated method stub
        // TODO: esto debe ser eliminado
    }

    @Override
    @Test
    public void testApplyVersioningLinkedStatisticalResource() throws Exception {
        // TODO Auto-generated method stub
        // TODO: esto debe ser eliminado
    }
}
