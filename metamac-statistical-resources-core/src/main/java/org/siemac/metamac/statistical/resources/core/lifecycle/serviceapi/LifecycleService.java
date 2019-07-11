package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface LifecycleService<E extends Object> {

    E sendToProductionValidation(ServiceContext ctx, String urn) throws MetamacException;

    E sendToDiffusionValidation(ServiceContext ctx, String urn) throws MetamacException;

    E sendToValidationRejected(ServiceContext ctx, String urn) throws MetamacException;

    E sendToPublished(ServiceContext ctx, String urn) throws MetamacException;

    E versioning(ServiceContext ctx, String urn, VersionTypeEnum versionType) throws MetamacException;

    void sendNewVersionPublishedStreamMessageByResource(ServiceContext ctx, E resource);
}
