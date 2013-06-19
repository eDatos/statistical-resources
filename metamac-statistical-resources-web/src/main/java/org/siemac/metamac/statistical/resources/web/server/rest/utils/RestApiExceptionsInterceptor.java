package org.siemac.metamac.statistical.resources.web.server.rest.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.aspectj.lang.JoinPoint;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
import org.siemac.metamac.statistical.resources.core.invocation.MetamacApisLocator;
import org.siemac.metamac.statistical.resources.web.client.WebMessageExceptionsConstants;
import org.siemac.metamac.statistical.resources.web.server.rest.RestApiLocator;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebTranslateExceptions;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This Interceptor requires that the target methods receive a ServiceContext as first parameter 
 * @author rcorrod
 *
 */
public class RestApiExceptionsInterceptor {

    private static Logger          logger = Logger.getLogger(RestApiExceptionsInterceptor.class.getName());
    
    @Autowired 
    private RestApiLocator restApiLocator;
    
    @Autowired
    private MetamacApisLocator coreRestApisLocator;

    @Autowired
    private WebTranslateExceptions webTranslateExceptions;
    
    public void afterThrowingStatisticalOperations(JoinPoint joinPoint, Throwable ex) throws MetamacWebException {
        Client client = WebClient.client(restApiLocator.getStatisticalOperationsRestFacadeV10());
        translateException(ex, getServiceContextFromParams(joinPoint), client, WebMessageExceptionsConstants.REST_API_STATISTICAL_OPERATIONS_INVOCATION_ERROR_PREFIX);
    }
    
    public void afterThrowingStructuralResources(JoinPoint joinPoint, Throwable ex) throws MetamacWebException { 
        Client client = WebClient.client(coreRestApisLocator.getSrmRestInternalFacadeV10());
        translateException(ex, getServiceContextFromParams(joinPoint), client, WebMessageExceptionsConstants.REST_API_SRM_INVOCATION_ERROR_PREFIX);
    }
    
    public void afterThrowingCommonMetadata(JoinPoint joinPoint, Throwable ex) throws MetamacWebException {
        Client client = WebClient.client(restApiLocator.getCommonMetadataRestExternalFacadeV10());
        translateException(ex, getServiceContextFromParams(joinPoint), client, WebMessageExceptionsConstants.REST_API_COMMON_METADATA_INVOCATION_ERROR_PREFIX);
    }
    
    private void translateException(Throwable ex, ServiceContext ctx, Client client, String messageKeyPrefix) throws MetamacWebException {
        if (ex instanceof ServerWebApplicationException) {
            throwMetamacWebExceptionFromServerWebApplicationException(ctx, (ServerWebApplicationException)ex, client, messageKeyPrefix);
        }
        throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, ex.getMessage());
    }
    
    private ServiceContext getServiceContextFromParams(JoinPoint joinPoint) {
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof ServiceContext) {
                return (ServiceContext) joinPoint.getArgs()[0];
            }
        }
        return null;
    }
    
    
    
    private void throwMetamacWebExceptionFromServerWebApplicationException(ServiceContext serviceContext, ServerWebApplicationException e, Client client, String messageKeyPrefix) throws MetamacWebException {
        org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(client, org.siemac.metamac.rest.common.v1_0.domain.Exception.class);

        if (exception == null) {
            logger.log(Level.SEVERE, e.getMessage());
            throwMetamacWebExceptionForStatus(serviceContext, messageKeyPrefix, e.getResponse().getStatus());
        }

        throw WebExceptionUtils.createMetamacWebException(exception);
    }
    
    private void throwMetamacWebExceptionForStatus(ServiceContext serviceContext, String messageKeyPrefix, int statusCode) throws MetamacWebException {
        String locale = null;
        if (serviceContext != null) {
            locale = (String) serviceContext.getProperty(LocaleConstants.locale);
        }
        String messageKey = messageKeyPrefix + statusCode;
        String exceptionMessage = webTranslateExceptions.getTranslatedMessage(messageKey, locale);
        if (exceptionMessage == null) {
            messageKey = messageKeyPrefix+"unknown";
            exceptionMessage = webTranslateExceptions.getTranslatedMessage(messageKey, locale);
        }

        throw new MetamacWebException(messageKey, exceptionMessage);
    }
}
