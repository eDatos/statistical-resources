package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.CANCEL_PROGRAMMED_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PROGRAM_PUBLICATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PUBLISH;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.REJECT_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION;
import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.lang.shared.LocaleConstants;
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
import org.siemac.metamac.statistical.resources.web.client.WebMessageExceptionsConstants;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.server.dtos.GroupedNotificationDto;
import org.siemac.metamac.statistical.resources.web.server.dtos.ResourceNotificationBaseDto;
import org.siemac.metamac.statistical.resources.web.server.dtos.ResourceNotificationDto;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.rest.utils.RestExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.server.utils.WebTranslateExceptions;
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

    @Autowired
    private WebTranslateExceptions                              webTranslateExceptions;

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
        if (!notifications.isEmpty()) {
            switch (getLifeCycleAction(notifications)) {
                case SEND_TO_PRODUCTION_VALIDATION:
                    createSendToProductionValidationNotification(serviceContext, notifications);
                    break;
                case SEND_TO_DIFFUSION_VALIDATION:
                    createSendToDiffusionValidationNotification(serviceContext, notifications);
                    break;
                case REJECT_VALIDATION:
                    createCancelValidationNotification(serviceContext, notifications);
                    break;
                case PUBLISH:
                    createPublicationNotification(serviceContext, notifications);
                    break;
                case PROGRAM_PUBLICATION:
                    createProgrammedPublicationNotification(serviceContext, notifications);
                    break;
                case CANCEL_PROGRAMMED_PUBLICATION:
                    createCancelProgrammedPublicationNotification(serviceContext, notifications);
                    break;
                case VERSION:
                    // Do not send notifications
                    break;
            }
        }
    }

    //
    // SEND TO PRODUCTION VALIDATION
    //

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, ResourceNotificationDto notification) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notification);
    }

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notifications);
    }

    //
    // SEND TO DIFFUSION VALIDATION
    //

    private void createSendToDiffusionValidationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createSendToDiffusionValidationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notifications);
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

    private void createCancelValidationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        Map<ResourceNotificationBaseDto, String[]> notificationsWithReceivers = new HashMap<ResourceNotificationBaseDto, String[]>();
        for (ResourceNotificationBaseDto notification : notifications) {
            ProcStatusEnum previousProcStatusEnum = notification.getPreviousResource().getProcStatus();
            String receiverUsername = ProcStatusEnum.PRODUCTION_VALIDATION.equals(previousProcStatusEnum) ? notification.getPreviousResource().getProductionValidationUser() : notification
                    .getPreviousResource().getDiffusionValidationUser();
            notificationsWithReceivers.put(notification, new String[]{receiverUsername});
        }
        createNotificationWithReceivers(serviceContext, notificationsWithReceivers);
    }

    //
    // PUBLISH
    //

    private void createPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createPublicationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notifications);
    }

    //
    // PROGRAMMED PUBLICATION
    //

    private void createProgrammedPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notificationDto);
    }

    private void createProgrammedPublicationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, notifications);
    }

    //
    // CANCEL PROGRAMMED PUBLICATION
    //

    private void createCancelProgrammedPublicationNotification(ServiceContext serviceContext, ResourceNotificationDto notificationDto) throws MetamacWebException {
        String creatorUsername = notificationDto.getUpdatedResource().getCreationUser();
        String publisherUsername = notificationDto.getPreviousResource().getPublicationUser();
        createNotificationWithReceivers(serviceContext, notificationDto, new String[]{creatorUsername, publisherUsername});
    }

    private void createCancelProgrammedPublicationNotification(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        Map<ResourceNotificationBaseDto, String[]> notificationsWithReceivers = new HashMap<ResourceNotificationBaseDto, String[]>();
        for (ResourceNotificationBaseDto notification : notifications) {
            String creatorUsername = notification.getUpdatedResource().getCreationUser();
            String publisherUsername = notification.getPreviousResource().getPublicationUser();
            notificationsWithReceivers.put(notification, new String[]{creatorUsername, publisherUsername});
        }
        createNotificationWithReceivers(serviceContext, notificationsWithReceivers);
    }

    //
    // NOTIFICATIONS
    //

    /**
     * Creates a notification specifying the statistical operation, the roles and the application
     * 
     * @param serviceContext
     * @param notification
     * @throws MetamacWebException
     */
    private void createNotificationWithStatisticalOperationAndRoles(ServiceContext serviceContext, ResourceNotificationDto notification) throws MetamacWebException {
        MetamacRolesEnum[] notificationRoles = roles.containsKey(notification.getLifeCycleAction()) ? roles.get(notification.getLifeCycleAction()) : null;
        GroupedNotificationDto groupedNotificationDto = createGroupedNotification(notification);
        groupedNotificationDto.setRoles(notificationRoles);
        createNotification(serviceContext, groupedNotificationDto);
    }

    /**
     * Creates a notification specifying the statistical operation, the roles and the application
     * 
     * @param serviceContext
     * @param notifications
     * @param notificationRoles
     * @throws MetamacWebException
     */
    private void createNotificationWithStatisticalOperationAndRoles(ServiceContext serviceContext, List<ResourceNotificationBaseDto> notifications) throws MetamacWebException {
        boolean isErrorInNotification = false;
        MetamacRolesEnum[] notificationRoles = roles.get(getLifeCycleAction(notifications));
        Map<String, List<ResourceNotificationBaseDto>> notificationsByStatisticalOperation = groupNotificationsByStatisticalOperation(notifications);
        for (Map.Entry<String, List<ResourceNotificationBaseDto>> entry : notificationsByStatisticalOperation.entrySet()) {
            GroupedNotificationDto groupedNotificationDto = createGroupedNotificationWithStatisticalOperation(entry.getKey(), entry.getValue());
            groupedNotificationDto.setRoles(notificationRoles);
            try {
                createNotification(serviceContext, groupedNotificationDto);
            } catch (MetamacWebException e) {
                isErrorInNotification = true;
            }
        }
        if (isErrorInNotification) {
            throw WebExceptionUtils.createMetamacWebException(CommonServiceExceptionType.REST_API_NOTICES_ERROR_SENDING_NOTIFICATION, getTranslatedNotificationErrorMessage());
        }
    }

    /**
     * Creates a notification specifying the receivers
     * 
     * @param serviceContext
     * @param actionCode
     * @param messageCode
     * @param notification
     * @param receiversUsernames
     * @throws MetamacWebException
     */
    private void createNotificationWithReceivers(ServiceContext serviceContext, ResourceNotificationDto notification, String[] receiversUsernames) throws MetamacWebException {
        GroupedNotificationDto groupedNotificationDto = createGroupedNotification(notification);
        groupedNotificationDto.setReceiversUsernames(receiversUsernames);
        createNotification(serviceContext, groupedNotificationDto);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, Map<ResourceNotificationBaseDto, String[]> notificationsWithReceivers) throws MetamacWebException {
        boolean isErrorInNotification = false;
        Map<String, List<ResourceNotificationBaseDto>> groupedNotificationsByReceiver = groupNotificationsByReceiver(notificationsWithReceivers);
        for (Map.Entry<String, List<ResourceNotificationBaseDto>> entry : groupedNotificationsByReceiver.entrySet()) {
            ResourceInternal[] resourceInternals = getResourceInternals(entry.getValue());
            String receiverUserName = entry.getKey();
            GroupedNotificationDto groupedNotificationDto = new GroupedNotificationDto.Builder(resourceInternals, getLifeCycleAction(entry.getValue()))
                    .reasonOfRejection(getReasonOfRejection(entry.getValue())).programmedPublicationDate(getProgrammedPublicationDate(entry.getValue()))
                    .receiversUsernames(new String[]{receiverUserName}).build();
            try {
                createNotification(serviceContext, groupedNotificationDto);
            } catch (MetamacWebException e) {
                isErrorInNotification = true;
            }
        }
        if (isErrorInNotification) {
            throw WebExceptionUtils.createMetamacWebException(CommonServiceExceptionType.REST_API_NOTICES_ERROR_SENDING_NOTIFICATION, getTranslatedNotificationErrorMessage());
        }
    }

    private void createNotification(ServiceContext ctx, GroupedNotificationDto groupedNotificationDto) throws MetamacWebException {

        String actionCode = getActionCode(groupedNotificationDto.getLifeCycleAction());
        String messageCode = getMessageCode(groupedNotificationDto.getLifeCycleAction());

        String subject = buildSubject(ctx, actionCode);
        Message message = buildMessage(ctx, messageCode, groupedNotificationDto.getResources(), groupedNotificationDto.getReasonOfRejection(), groupedNotificationDto.getLifeCycleAction(),
                groupedNotificationDto.getProgrammedPublicationDate());

        NoticeBuilder noticeBuilder = NoticeBuilder.notification().withMessages(message).withSendingApplication(getSendingApp()).withSendingUser(ctx.getUserId()).withSubject(subject);
        if (groupedNotificationDto.getRoles() != null) {
            noticeBuilder = noticeBuilder.withRoles(groupedNotificationDto.getRoles());
        }
        if (StringUtils.isNotBlank(groupedNotificationDto.getStatisticalOperationUrn())) {
            noticeBuilder = noticeBuilder.withStatisticalOperations(groupedNotificationDto.getStatisticalOperationUrn());
        }
        if (groupedNotificationDto.getReceiversUsernames() != null) {
            noticeBuilder = noticeBuilder.withReceivers(groupedNotificationDto.getReceiversUsernames());
        }

        try {
            Response response = metamacApisLocator.getNoticesRestInternalFacadeV10().createNotice(noticeBuilder.build());
            this.restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private Message buildMessage(ServiceContext ctx, String messageCode, ResourceInternal[] resources, String reasonOfRejection, LifeCycleActionEnum lifeCycleAction, Date programmedPublicationDate) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        if (StringUtils.isNotBlank(reasonOfRejection)) {
            localisedMessage = localisedMessage + " (" + reasonOfRejection + ")";
        }
        if (LifeCycleActionEnum.PROGRAM_PUBLICATION.equals(lifeCycleAction) && programmedPublicationDate != null) {
            localisedMessage = MessageFormat.format(localisedMessage, programmedPublicationDate);
        }
        return MessageBuilder.message().withText(localisedMessage).withResources(resources).build();
    }

    private String buildSubject(ServiceContext ctx, String actionCode) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        return LocaleUtil.getMessageForCode(actionCode, locale);
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

    private Map<String, List<ResourceNotificationBaseDto>> groupNotificationsByStatisticalOperation(List<ResourceNotificationBaseDto> notifications) {
        Map<String, List<ResourceNotificationBaseDto>> result = new HashMap<String, List<ResourceNotificationBaseDto>>();
        for (ResourceNotificationBaseDto notification : notifications) {
            String statisticalOperationUrn = notification.getUpdatedResource().getStatisticalOperation().getUrn();
            if (!result.containsKey(statisticalOperationUrn)) {
                result.put(statisticalOperationUrn, new ArrayList<ResourceNotificationBaseDto>());
            }
            result.get(statisticalOperationUrn).add(notification);
        }
        return result;
    }

    private Map<String, List<ResourceNotificationBaseDto>> groupNotificationsByReceiver(Map<ResourceNotificationBaseDto, String[]> notificationsWithReceivers) {
        Map<String, List<ResourceNotificationBaseDto>> result = new HashMap<String, List<ResourceNotificationBaseDto>>();
        for (Map.Entry<ResourceNotificationBaseDto, String[]> entry : notificationsWithReceivers.entrySet()) {
            for (String receiverUserName : entry.getValue()) {
                if (!result.containsKey(receiverUserName)) {
                    result.put(receiverUserName, new ArrayList<ResourceNotificationBaseDto>());
                }
                result.get(receiverUserName).add(entry.getKey());
            }
        }
        return result;
    }

    private ResourceInternal[] getResourceInternals(List<ResourceNotificationBaseDto> resources) throws MetamacWebException {
        ResourceInternal[] result = new ResourceInternal[resources.size()];
        for (int i = 0; i < resources.size(); i++) {
            result[i] = restMapper.buildResourceInternal(resources.get(i).getUpdatedResource(), resources.get(i).getStatisticalResourceType());
        }
        return result;
    }

    private GroupedNotificationDto createGroupedNotificationWithStatisticalOperation(String statisticalOperatonUrn, List<ResourceNotificationBaseDto> resources) throws MetamacWebException {
        ResourceInternal[] resourceInternals = getResourceInternals(resources);
        String reasonOfRejection = getReasonOfRejection(resources);
        Date programmedPublicationDate = getProgrammedPublicationDate(resources);
        return new GroupedNotificationDto.Builder(resourceInternals, getLifeCycleAction(resources)).statisticalOperationUrn(statisticalOperatonUrn).reasonOfRejection(reasonOfRejection)
                .programmedPublicationDate(programmedPublicationDate).build();
    }

    private GroupedNotificationDto createGroupedNotification(ResourceNotificationDto notification) throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternal(notification.getUpdatedResource(), notification.getStatisticalResourceType());
        return new GroupedNotificationDto.Builder(new ResourceInternal[]{resourceInternal}, notification.getLifeCycleAction())
                .statisticalOperationUrn(notification.getUpdatedResource().getStatisticalOperation().getUrn()).reasonOfRejection(notification.getReasonOfRejection())
                .programmedPublicationDate(notification.getProgrammedPublicationDate()).build();
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

    /**
     * Returns the reason of rejection of the notifications. When a group of resources update their {@link ProcStatusEnum} at the same time, all of them have the same reason of rejection.
     * 
     * @param notifications
     * @return
     */
    private String getReasonOfRejection(List<ResourceNotificationBaseDto> notifications) {
        return notifications.get(0).getReasonOfRejection();
    }

    /**
     * Returns the programmed publication date of the notifications. When a group of resources update their {@link ProcStatusEnum} at the same time, all of them have the same programmed publication
     * date.
     * 
     * @param notifications
     * @return
     */
    private Date getProgrammedPublicationDate(List<ResourceNotificationBaseDto> notifications) {
        return notifications.get(0).getProgrammedPublicationDate();
    }

    private String getTranslatedNotificationErrorMessage() {
        Locale locale = (Locale) ServiceContextHolder.getCurrentServiceContext().getProperty(LocaleConstants.locale);
        return webTranslateExceptions.getTranslatedMessage(WebMessageExceptionsConstants.ERROR_SENDING_NOTIFICATIONS, locale);
    }
}
