package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface LifecycleService<E extends Object> {

    public E sendToProductionValidation(ServiceContext ctx, String urn) throws MetamacException;

    public E sendToDiffusionValidation(ServiceContext ctx, String urn) throws MetamacException;

    public E sendToValidationRejected(ServiceContext ctx, String urn) throws MetamacException;

    public E sendToPublished(ServiceContext ctx, String urn) throws MetamacException;

    public E cancelPublication(ServiceContext ctx, String urn) throws MetamacException;

    public E versioning(ServiceContext ctx, String urn, VersionTypeEnum versionType) throws MetamacException;

}
