package org.siemac.metamac.statistical.resources.core.lifecycle;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadataStatisticalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleChecker {

    @Autowired
    private LifecycleChecker               lifecycleChecker;

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToProductionValidation(HasSiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToProductionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToDiffusionValidation(HasSiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToDiffusionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToValidationRejected(HasSiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToValidationRejected(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    public void checkSendToPublished(HasSiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleChecker.checkSendToPublished(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PROTECTED COMMON UTILS
    // ------------------------------------------------------------------------------------------------------

    private void checkSiemacMetadataAllActions(HasSiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(resource, metadataName, exceptionItems);
    }
}
