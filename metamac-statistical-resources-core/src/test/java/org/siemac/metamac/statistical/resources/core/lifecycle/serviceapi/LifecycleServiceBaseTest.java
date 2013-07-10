package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

/**
 * Definition of test methods to implement.
 */

// IDEA: This class could be generated

public interface LifecycleServiceBaseTest {

    // ------------------------------------------------------------------------------------------------------
    // PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    public void testCheckSendToProductionValidationResource() throws Exception;

    public void testApplySendToProductionValidationResource() throws Exception;

    // ------------------------------------------------------------------------------------------------------
    // DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    public void testCheckSendToDiffusionValidationResource() throws Exception;

    public void testApplySendToDiffusionValidationResource() throws Exception;

    // ------------------------------------------------------------------------------------------------------
    // VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    public void testCheckSendToValidationRejectedResource() throws Exception;

    public void testApplySendToValidationRejectedResource() throws Exception;

    // ------------------------------------------------------------------------------------------------------
    // PUBLISHED
    // ------------------------------------------------------------------------------------------------------
    public void testCheckSendToPublishedResource() throws Exception;

    public void testApplySendToPublishedResource() throws Exception;

    // TODO: Pendiente eliminar
    public void testCheckSendToPublishedLinkedStatisticalResource() throws Exception;

    // TODO: Pendiente eliminar
    public void testApplySendToPublishedLinkedStatisticalResource() throws Exception;

    // ------------------------------------------------------------------------------------------------------
    // VERSIONING
    // ------------------------------------------------------------------------------------------------------
    public void testCheckVersioningResource() throws Exception;

    public void testApplyVersioningResource() throws Exception;

    // TODO: Pendiente eliminar
    public void testCheckVersioningLinkedStatisticalResource() throws Exception;

    // TODO: Pendiente eliminar
    public void testApplyVersioningLinkedStatisticalResource() throws Exception;

}
