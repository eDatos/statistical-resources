package org.siemac.metamac.statistical.resources.web.server.handlers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.WebMessageExceptionsConstants;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebTranslateExceptions;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;

public abstract class UpdateResourceProcStatusBaseActionHandler<A extends Action<R>, R extends Result> extends SecurityActionHandler<A, R> {

    @Autowired
    private WebTranslateExceptions webTranslateExceptions;

    public UpdateResourceProcStatusBaseActionHandler(Class<A> actionType) {
        super(actionType);
    }

    protected void addExceptionsItemToMetamacException(ProcStatusEnum nextProcStatus, LifeCycleStatisticalResourceDto lifeCycleResource, MetamacException mainMetamacException,
            MetamacException thrownMetamacException) {

        MetamacExceptionItem item = createMetamacExceptionItem(nextProcStatus, lifeCycleResource);

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();

        // If the thrown MetamacException had a principal exception, create a tree structure with this principal exception
        if (thrownMetamacException.getPrincipalException() != null) {
            MetamacExceptionItem principalException = thrownMetamacException.getPrincipalException();
            principalException.setExceptionItems(thrownMetamacException.getExceptionItems());
            exceptionItems.add(principalException);
        } else {
            exceptionItems = thrownMetamacException.getExceptionItems();
        }

        item.setExceptionItems(exceptionItems);

        // Add the exception items to the main metamacException
        mainMetamacException.getExceptionItems().add(item);
    }

    protected MetamacExceptionItem createMetamacExceptionItem(ProcStatusEnum nextProcStatus, LifeCycleStatisticalResourceDto lifeCycleResource) {
        String exceptionCode = getExceptionCode(nextProcStatus);
        String exceptionMessage = getTranslatedErrorMessage(exceptionCode, lifeCycleResource.getUrn());

        MetamacExceptionItem item = new MetamacExceptionItem();
        item.setCode(exceptionCode);
        item.setMessage(exceptionMessage);
        item.setMessageParameters(new Serializable[]{lifeCycleResource.getUrn()});
        return item;
    }

    protected String getExceptionCode(ProcStatusEnum procStatus) {
        switch (procStatus) {
            case PRODUCTION_VALIDATION:
                return WebMessageExceptionsConstants.ERROR_SEND_RESOURCE_PRODUCTION_VALIDATION;
            case DIFFUSION_VALIDATION:
                return WebMessageExceptionsConstants.ERROR_SEND_RESOURCE_DIFFUSION_VALIDATION;
            case VALIDATION_REJECTED:
                return WebMessageExceptionsConstants.ERROR_REJECT_VALIDATION;
            case PUBLISHED:
                return WebMessageExceptionsConstants.ERROR_PUBLISH_RESOURCE;
            default:
                return null;
        }
    }

    protected String getTranslatedErrorMessage(String exceptionCode, String... parameters) {
        Locale locale = (Locale) ServiceContextHolder.getCurrentServiceContext().getProperty(LocaleConstants.locale);
        String exceptionMessage = webTranslateExceptions.getTranslatedMessage(exceptionCode, locale, parameters);
        return exceptionMessage;
    }
}