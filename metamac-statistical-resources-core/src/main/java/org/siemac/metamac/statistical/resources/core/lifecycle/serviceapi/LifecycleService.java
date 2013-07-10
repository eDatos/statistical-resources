package org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;

public interface LifecycleService<E extends Object> {

    public E sendToProductionValidation(ServiceContext ctx, E resource) throws MetamacException;

    public E sendToDiffusionValidation(ServiceContext ctx, E resource) throws MetamacException;

    public E sendToValidationRejected(ServiceContext ctx, E resource) throws MetamacException;

    public E sendToPublished(ServiceContext ctx, E resource) throws MetamacException;

    public E versioning(ServiceContext ctx, E resource) throws MetamacException;

}
