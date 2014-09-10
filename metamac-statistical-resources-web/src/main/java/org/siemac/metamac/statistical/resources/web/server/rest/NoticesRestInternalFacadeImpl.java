package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.CANCEL_PROGRAMMED_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PROGRAM_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PUBLISH;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.REJECT_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.ServiceContextUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacApplicationsEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.MessageBuilder;
import org.siemac.metamac.rest.notices.v1_0.domain.utils.NoticeBuilder;
import org.siemac.metamac.statistical.resources.core.invocation.service.MetamacApisLocator;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.shared.dtos.NotificationDto;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(NoticesRestInternalFacade.BEAN_ID)
public class NoticesRestInternalFacadeImpl implements NoticesRestInternalFacade {

    @Autowired
    private MetamacApisLocator                                  metamacApisLocator;

    @Autowired
    private RestExceptionUtils                                  restExceptionUtils;

    @Autowired
    private RestMapper                                          restMapper;

    private static Map<LifeCycleActionEnum, MetamacRolesEnum[]> roles;
    private static Map<LifeCycleActionEnum, String>             actionCodes;
    private static Map<LifeCycleActionEnum, String>             messageCodes;

    static {
        roles = new HashMap<LifeCycleActionEnum, MetamacRolesEnum[]>();
        roles.put(SEND_TO_PRODUCTION_VALIDATION, new MetamacRolesEnum[]{MetamacRolesEnum.TECNICO_PRODUCCION});
        roles.put(SEND_TO_DIFFUSION_VALIDATION, new MetamacRolesEnum[]{MetamacRolesEnum.TECNICO_DIFUSION, MetamacRolesEnum.TECNICO_APOYO_DIFUSION});
        roles.put(PUBLISH, new MetamacRolesEnum[]{MetamacRolesEnum.JEFE_PRODUCCION, MetamacRolesEnum.TECNICO_PRODUCCION, MetamacRolesEnum.TECNICO_APOYO_PRODUCCION});
        roles.put(PROGRAM_PUBLICATION, new MetamacRolesEnum[]{MetamacRolesEnum.JEFE_PRODUCCION, MetamacRolesEnum.TECNICO_PRODUCCION, MetamacRolesEnum.TECNICO_APOYO_PRODUCCION});

        actionCodes = new HashMap<LifeCycleActionEnum, String>();
        actionCodes.put(SEND_TO_PRODUCTION_VALIDATION, ServiceNoticeAction.RESOURCE_SEND_PRODUCTION_VALIDATION);
        actionCodes.put(SEND_TO_DIFFUSION_VALIDATION, ServiceNoticeAction.RESOURCE_SEND_DIFFUSION_VALIDATION);
        actionCodes.put(REJECT_VALIDATION, ServiceNoticeAction.RESOURCE_CANCEL_VALIDATION);
        actionCodes.put(PUBLISH, ServiceNoticeAction.RESOURCE_PUBLICATION);
        actionCodes.put(PROGRAM_PUBLICATION, ServiceNoticeAction.RESOURCE_PUBLICATION_PROGRAMMED);
        actionCodes.put(CANCEL_PROGRAMMED_PUBLICATION, ServiceNoticeAction.RESOURCE_CANCEL_PROGRAMMED_PUBLICATION);

        messageCodes = new HashMap<LifeCycleActionEnum, String>();
        messageCodes.put(SEND_TO_PRODUCTION_VALIDATION, ServiceNoticeMessage.RESOURCE_SEND_PRODUCTION_VALIDATION_OK);
        messageCodes.put(SEND_TO_DIFFUSION_VALIDATION, ServiceNoticeMessage.RESOURCE_SEND_DIFFUSION_VALIDATION_OK);
        messageCodes.put(REJECT_VALIDATION, ServiceNoticeMessage.RESOURCE_CANCEL_VALIDATION_OK);
        messageCodes.put(PUBLISH, ServiceNoticeMessage.RESOURCE_PUBLICATION_OK);
        messageCodes.put(PROGRAM_PUBLICATION, ServiceNoticeMessage.RESOURCE_PUBLICATION_PROGRAMMED_OK);
        messageCodes.put(CANCEL_PROGRAMMED_PUBLICATION, ServiceNoticeMessage.RESOURCE_CANCEL_PROGRAMMED_PUBLICATION_OK);
    }

    @Override
    public void createLifeCycleNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        switch (notificationDto.getLifeCycleAction()) {
            case SEND_TO_PRODUCTION_VALIDATION:
                createSendToProductionValidationNotification(serviceContext, notificationDto);
                break;
            case SEND_TO_DIFFUSION_VALIDATION:
                createSendToDiffusionValidationNotification(serviceContext, notificationDto);
                break;
            case REJECT_VALIDATION:
                createCancelValidationNotification(serviceContext, notificationDto);
                break;
            case PUBLISH:
                createPublicationNotification(serviceContext, notificationDto);
                break;
            case PROGRAM_PUBLICATION:
                createProgrammedPublicationNotification(serviceContext, notificationDto);
                break;
            case CANCEL_PROGRAMMED_PUBLICATION:
                createCancelProgrammedPublicationNotification(serviceContext, notificationDto);
                break;
            case VERSION:
                // Do not send notifications
                break;
        }
    }

    @Override
    public void createPublicationErrorNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        String actionCode = ServiceNoticeAction.RESOURCE_PUBLICATION_ERROR;
        String messageCode = ServiceNoticeMessage.RESOURCE_PUBLICATION_ERROR_OK;
        String[] receiversUsernames = new String[]{};
        // TODO METAMAC-1991
        createNotificationWithReceivers(serviceContext, actionCode, messageCode, notificationDto, receiversUsernames);
    }

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createSendToDiffusionValidationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createCancelValidationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        String[] receiversUsernames = new String[]{};
        // TODO METAMAC-1991
        createNotificationWithReceivers(serviceContext, notificationDto, receiversUsernames);
    }

    private void createPublicationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createProgrammedPublicationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createCancelProgrammedPublicationNotification(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        String[] receiversUsernames = new String[]{};
        // TODO METAMAC-1991
        createNotificationWithReceivers(serviceContext, notificationDto, receiversUsernames);
    }

    private void createNotificationWithStatisticalOperationAndRoles(ServiceContext serviceContext, NotificationDto notificationDto) throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternalFromDatasetVersion(notificationDto.getDatasetVersionDto());
        String actionCode = getActionCode(notificationDto.getLifeCycleAction());
        String messageCode = getMessageCode(notificationDto.getLifeCycleAction());
        MetamacRolesEnum[] notificationRoles = roles.containsKey(notificationDto.getLifeCycleAction()) ? roles.get(notificationDto.getLifeCycleAction()) : null;
        String[] receiversUsernames = null;
        createNotification(serviceContext, notificationDto, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, notificationRoles, receiversUsernames);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, NotificationDto notificationDto, String[] receiversUsernames) throws MetamacWebException {
        String actionCode = getActionCode(notificationDto.getLifeCycleAction());
        String messageCode = getMessageCode(notificationDto.getLifeCycleAction());
        createNotificationWithReceivers(serviceContext, actionCode, messageCode, notificationDto, receiversUsernames);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, String actionCode, String messageCode, NotificationDto notificationDto, String[] receiversUsernames)
            throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternalFromDatasetVersion(notificationDto.getDatasetVersionDto());
        MetamacRolesEnum[] cancelValidationRoles = null;
        createNotification(serviceContext, notificationDto, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, cancelValidationRoles, receiversUsernames);
    }

    private void createNotification(ServiceContext ctx, NotificationDto notificationDto, String actionCode, String messageCode, ResourceInternal[] resources, MetamacRolesEnum[] roles,
            String[] receiversUsernames) throws MetamacWebException {

        String subject = buildSubject(ctx, actionCode);
        Message message = buildMessage(ctx, messageCode, notificationDto, resources);

        NoticeBuilder noticeBuilder = NoticeBuilder.notification().withMessages(message).withSendingApplication(getSendingApp()).withSendingUser(ctx.getUserId()).withSubject(subject);
        if (roles != null) {
            noticeBuilder = noticeBuilder.withRoles(roles);
        }
        if (StringUtils.isNotBlank(notificationDto.getDatasetVersionDto().getStatisticalOperation().getUrn())) {
            noticeBuilder = noticeBuilder.withStatisticalOperations(notificationDto.getDatasetVersionDto().getStatisticalOperation().getUrn());
        }
        if (receiversUsernames != null) {
            noticeBuilder = noticeBuilder.withReceivers(receiversUsernames);
        }

        try {
            Response response = metamacApisLocator.getNoticesRestInternalFacadeV10().createNotice(noticeBuilder.build());
            this.restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private Message buildMessage(ServiceContext ctx, String messageCode, NotificationDto notificationDto, ResourceInternal[] resources) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        if (StringUtils.isNotBlank(notificationDto.getReasonOfRejection())) {
            localisedMessage = localisedMessage + " (" + notificationDto.getReasonOfRejection() + ")";
        }
        return MessageBuilder.message().withText(localisedMessage).withResources(resources).build();
    }

    private String buildSubject(ServiceContext ctx, String actionCode) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedAction = LocaleUtil.getMessageForCode(actionCode, locale);
        return "[" + getSendingApp() + "] " + localisedAction;
    }

    private String getActionCode(LifeCycleActionEnum lifeCycleAction) {
        return actionCodes.containsKey(lifeCycleAction) ? actionCodes.get(lifeCycleAction) : StringUtils.EMPTY;
    }

    private String getMessageCode(LifeCycleActionEnum lifeCycleAction) {
        return messageCodes.containsKey(lifeCycleAction) ? messageCodes.get(lifeCycleAction) : StringUtils.EMPTY;
    }

    private String getSendingApp() {
        return MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS.getName();
    }
}
