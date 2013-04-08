package org.siemac.metamac.statistical.resources.core.base.serviceapi;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;

public interface SiemacLifecycleService {

    // Draft >> Production Validation
    void checkSendToProductionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    void applySendToProductionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource);

    // Production validation >> Diffusion validation
    void checkSendToDiffusionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    void applySendToDiffusionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource);

    // Production validation || Diffusion validation >> Validation Rejected
    void checkSendToValidationRejected(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException;

    void applySendToValidationRejectedActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource);

}
