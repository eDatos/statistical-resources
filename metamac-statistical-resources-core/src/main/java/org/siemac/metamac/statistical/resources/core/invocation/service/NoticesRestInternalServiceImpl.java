package org.siemac.metamac.statistical.resources.core.invocation.service;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.utils.TranslateExceptions;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionUtils;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NoticesRestInternalService.BEAN_ID)
public class NoticesRestInternalServiceImpl implements NoticesRestInternalService {

    private static final String               ERROR  = "ERROR";

    private static Logger                     logger = LoggerFactory.getLogger(NoticesRestInternalServiceImpl.class);

    @Autowired
    private MetamacApisLocator                restApiLocator;

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    @Autowired
    private TranslateExceptions               translateExceptions;

    @Autowired
    private RestMapper                        restMapper;

    @Override
    public void createErrorBackgroundNotification(String user, String actionCode, MetamacException exception) {
        try {
            Locale locale = configurationService.retrieveLanguageDefaultLocale();

            Throwable localisedException = translateExceptions.translateException(locale, exception);
            String localisedMessage = localisedException.getMessage();
            localisedMessage = ERROR + " - " + localisedMessage;

            createBackgroundNotification(actionCode, localisedMessage, user);
            logger.info("Sending errorBackgroundNotification for user " + user);
        } catch (MetamacException e) {
            logger.error("Error creating createErrorBackgroundNotification:", e);
        }
    }

    @Override
    public void createSuccessBackgroundNotification(String user, String actionCode, String successMessageCode, Serializable... successMessageParameters) {
        try {
            Locale locale = configurationService.retrieveLanguageDefaultLocale();
            String localisedMessage = LocaleUtil.getMessageForCode(successMessageCode, locale);
            localisedMessage = MessageFormat.format(localisedMessage, successMessageParameters);
            createBackgroundNotification(actionCode, localisedMessage, user);
            logger.info("Sending successBackgroundNotification for user " + user);
        } catch (MetamacException e) {
            logger.error("Error creating createSuccessBackgroundNotification:", e);
        }
    }

    private void createBackgroundNotification(String actionCode, String message, String user) throws MetamacException {
        try {
            Locale locale = configurationService.retrieveLanguageDefaultLocale();
            String subject = LocaleUtil.getMessageForCode(actionCode, locale);
            String sendingApp = MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS.getName();

            // @formatter:off
            Notice notification = NoticeBuilder.notification()
                                                .withMessagesWithoutResources(message)
                                                .withSendingApplication(sendingApp)
                                                .withReceivers(user)
                                                .withSendingUser(user)
                                                .withSubject(subject)
                                                .build();
            // @formatter:on

            restApiLocator.getNoticesRestInternalFacadeV10().createNotice(notification);
        } catch (Exception e) {
            throw manageNoticesInternalRestException(e);
        }
    }

    @Override
    public void createErrorOnStreamMessagingService(String user, String actionCode, HasSiemacMetadata affectedResource, String errorMessageCode, Serializable... extraParameters) {
        ResourceInternal resourceInternal = restMapper.generateResourceInternal(affectedResource);
        createNotificationErrorOnStreamMessagingService(user, actionCode, resourceInternal, errorMessageCode, extraParameters);
    }

    @Override
    public void createErrorOnStreamMessagingService(String user, String actionCode, QueryVersion affectedResource, String errorMessageCode, Serializable... extraParameters) {
        ResourceInternal resourceInternal = restMapper.generateResourceInternal(affectedResource);
        createNotificationErrorOnStreamMessagingService(user, actionCode, resourceInternal, errorMessageCode, extraParameters);
    }

    private void createNotificationErrorOnStreamMessagingService(String user, String actionCode, ResourceInternal resourceInternal, String errorMessageCode, Serializable... extraParameters) {
        try {
            Locale locale = configurationService.retrieveLanguageDefaultLocale();
            String subject = LocaleUtil.getMessageForCode(actionCode, locale);
            String localisedMessage = LocaleUtil.getMessageForCode(errorMessageCode, locale);
            String sendingApp = MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS.getName();

            List<ResourceInternal> resourceInternalList = new ArrayList<ResourceInternal>();
            resourceInternalList.add(resourceInternal);

            Message message = MessageBuilder.message().withText(localisedMessage).withResources(resourceInternalList).build();

            Notice notification = NoticeBuilder.notification().withMessages(message).withSendingApplication(sendingApp).withSubject(subject).withSendingUser(user)
                    .withRoles(MetamacRolesEnum.ADMINISTRADOR).build();

            restApiLocator.getNoticesRestInternalFacadeV10().createNotice(notification);

        } catch (MetamacException e) {
            logger.error("Error creating notification for error on stream messaging service");
        }
    }

    // -------------------------------------------------------------------------------------------------
    // PRIVATE UTILS
    // -------------------------------------------------------------------------------------------------

    private MetamacException manageNoticesInternalRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_NOTICES_INTERNAL, restApiLocator.getNoticesRestInternalFacadeV10());
    }
}
