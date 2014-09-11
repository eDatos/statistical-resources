package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.CANCEL_PROGRAMMED_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PROGRAM_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PUBLISH;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.REJECT_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
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
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.invocation.service.MetamacApisLocator;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.shared.dtos.ResourceNotificationBaseDto;
import org.siemac.metamac.statistical.resources.web.shared.dtos.ResourceNotificationDto;
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
    public void createLifeCycleNotification(ServiceContext serviceContext, ResourceNotificationDto notification) throws MetamacWebException {
        switch (notification.getLifeCycleAction()) {
            case SEND_TO_PRODUCTION_VALIDATION:
                createSendToProductionValidationNotification(serviceContext, notification);
                break;
            case SEND_TO_DIFFUSION_VALIDATION:
                createSendToDiffusionValidationNotification(serviceContext, notification);
                break;
            case REJECT_VALIDATION:
                createCancelValidationNotification(serviceContext, notification);
                break;
            case PUBLISH:
                createPublicationNotification(serviceContext, notification);
                break;
            case PROGRAM_PUBLICATION:
                createProgrammedPublicationNotification(serviceContext, notification);
                break;
            case CANCEL_PROGRAMMED_PUBLICATION:
                createCancelProgrammedPublicationNotification(serviceContext, notification);
                break;
            case VERSION:
                // Do not send notifications
                break;
        }
    }

    @Override
    public void createLifeCycleNotifications(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        switch (getLifeCycleAction(notifications)) {
            case SEND_TO_PRODUCTION_VALIDATION:
                createSendToProductionValidationNotification(serviceContext, notifications);
                break;
            case SEND_TO_DIFFUSION_VALIDATION:
                // TODO METAMAC-1991
                break;
            case REJECT_VALIDATION:
                // TODO METAMAC-1991
                break;
            case PUBLISH:
                // TODO METAMAC-1991
                break;
            case PROGRAM_PUBLICATION:
                // TODO METAMAC-1991
                break;
            case CANCEL_PROGRAMMED_PUBLICATION:
                // TODO METAMAC-1991
                break;
            case VERSION:
                // Do not send notifications
                break;
        }
    }

    //
    // SEND TO PRODUCTION VALIDATION
    //

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        Map<String, ResourceNotificationBaseDto> notificationsByStatisticalOperation = groupNotificationsByStatisticalOperation(notifications);
        // TODO METAMAC-1991
    }

    //
    // SEND TO DIFFUSION VALIDATION
    //

    private void createSendToDiffusionValidationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    //
    // CANCEL VALIDATION
    //

    private void createCancelValidationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        ProcStatusEnum previousProcStatusEnum = notificationDto.getPreviousResource().getProcStatus();
        String receiverUsername = ProcStatusEnum.PRODUCTION_VALIDATION.equals(previousProcStatusEnum) ? notificationDto.getPreviousResource().getProductionValidationUser() : notificationDto
                .getPreviousResource().getDiffusionValidationUser();
        createNotificationWithReceivers(serviceContext, notificationDto, new String[]{receiverUsername});
    }

    //
    // PUBLISH
    //

    private void createPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    //
    // PROGRAMMED PUBLICATION
    //

    private void createProgrammedPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    //
    // CANCEL PROGRAMMED PUBLICATION
    //

    private void createCancelProgrammedPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        String creatorUsername = notificationDto.getUpdatedResource().getCreationUser();
        String publisherUsername = notificationDto.getPreviousResource().getPublicationUser();
        createNotificationWithReceivers(serviceContext, notificationDto, new String[]{creatorUsername, publisherUsername});
    }

    //
    // NOTIFICATIONS
    //

    private void createNotificationWithStatisticalOperationAndRoles(ServiceContext serviceContext, ResourceNotificationDto notification) throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternal(notification.getUpdatedResource(), notification.getStatisticalResourceType());
        String actionCode = getActionCode(notification.getLifeCycleAction());
        String messageCode = getMessageCode(notification.getLifeCycleAction());
        MetamacRolesEnum[] notificationRoles = roles.containsKey(notification.getLifeCycleAction()) ? roles.get(notification.getLifeCycleAction()) : null;
        String[] receiversUsernames = null;
        createNotification(serviceContext, notification, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, notificationRoles, receiversUsernames);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, ResourceNotificationDto notificationDto, String[] receiversUsernames) throws MetamacWebException {
        String actionCode = getActionCode(notificationDto.getLifeCycleAction());
        String messageCode = getMessageCode(notificationDto.getLifeCycleAction());
        createNotificationWithReceivers(serviceContext, actionCode, messageCode, notificationDto, receiversUsernames);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, String actionCode, String messageCode, ResourceNotificationDto notification, String[] receiversUsernames)
            throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternal(notification.getUpdatedResource(), notification.getStatisticalResourceType());
        MetamacRolesEnum[] cancelValidationRoles = null;
        createNotification(serviceContext, notification, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, cancelValidationRoles, receiversUsernames);
    }

    private void createNotification(ServiceContext ctx, ResourceNotificationDto notificationDto, String actionCode, String messageCode, ResourceInternal[] resources, MetamacRolesEnum[] roles,
            String[] receiversUsernames) throws MetamacWebException {

        String subject = buildSubject(ctx, actionCode);
        Message message = buildMessage(ctx, messageCode, notificationDto, resources);

        NoticeBuilder noticeBuilder = NoticeBuilder.notification().withMessages(message).withSendingApplication(getSendingApp()).withSendingUser(ctx.getUserId()).withSubject(subject);
        if (roles != null) {
            noticeBuilder = noticeBuilder.withRoles(roles);
        }
        if (StringUtils.isNotBlank(notificationDto.getUpdatedResource().getStatisticalOperation().getUrn())) {
            noticeBuilder = noticeBuilder.withStatisticalOperations(notificationDto.getUpdatedResource().getStatisticalOperation().getUrn());
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

    private Message buildMessage(ServiceContext ctx, String messageCode, ResourceNotificationDto notificationDto, ResourceInternal[] resources) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        if (StringUtils.isNotBlank(notificationDto.getReasonOfRejection())) {
            localisedMessage = localisedMessage + " (" + notificationDto.getReasonOfRejection() + ")";
        }
        if (LifeCycleActionEnum.PROGRAM_PUBLICATION.equals(notificationDto.getLifeCycleAction()) && notificationDto.getProgrammedPublicationDate() != null) {
            localisedMessage = MessageFormat.format(localisedMessage, notificationDto.getProgrammedPublicationDate());
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

    private Map<String, ResourceNotificationBaseDto> groupNotificationsByStatisticalOperation(List<ResourceNotificationBaseDto> notifications) {
        Map<String, ResourceNotificationBaseDto> result = new HashMap<String, ResourceNotificationBaseDto>();
        for (ResourceNotificationBaseDto notification : notifications) {
            result.put(notification.getUpdatedResource().getStatisticalOperation().getUrn(), notification);
        }
        return result;
    }

    /**
     * Returns the lifecycle action of the notifications. When a group of resources update their {@link ProcStatusEnum} at the same time, all of them have the same {@link LifeCycleActionEnum}.
     * 
     * @param notifications
     * @return
     */
    private LifeCycleActionEnum getLifeCycleAction(List<ResourceNotificationBaseDto> notifications) {
        return notifications.get(0).getLifeCycleAction();
    }
}
