package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum.PUBLISH;
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
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.invocation.service.MetamacApisLocator;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
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

        actionCodes = new HashMap<LifeCycleActionEnum, String>();
        actionCodes.put(SEND_TO_PRODUCTION_VALIDATION, ServiceNoticeAction.RESOURCE_SEND_PRODUCTION_VALIDATION);
        actionCodes.put(SEND_TO_DIFFUSION_VALIDATION, ServiceNoticeAction.RESOURCE_SEND_DIFFUSION_VALIDATION);
        actionCodes.put(LifeCycleActionEnum.REJECT_VALIDATION, ServiceNoticeAction.RESOURCE_CANCEL_VALIDATION);
        actionCodes.put(PUBLISH, ServiceNoticeAction.RESOURCE_PUBLICATION);

        messageCodes = new HashMap<LifeCycleActionEnum, String>();
        messageCodes.put(SEND_TO_PRODUCTION_VALIDATION, ServiceNoticeMessage.RESOURCE_SEND_PRODUCTION_VALIDATION_OK);
        messageCodes.put(SEND_TO_DIFFUSION_VALIDATION, ServiceNoticeMessage.RESOURCE_SEND_DIFFUSION_VALIDATION_OK);
        messageCodes.put(LifeCycleActionEnum.REJECT_VALIDATION, ServiceNoticeMessage.RESOURCE_CANCEL_VALIDATION_OK);
        messageCodes.put(PUBLISH, ServiceNoticeMessage.RESOURCE_PUBLICATION_OK);
    }

    @Override
    public void createLifeCycleNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto, String reasonOfRejection)
            throws MetamacWebException {
        switch (lifeCycleAction) {
            case SEND_TO_PRODUCTION_VALIDATION:
                createSendToProductionValidationNotification(serviceContext, lifeCycleAction, datasetVersionDto);
                break;
            case SEND_TO_DIFFUSION_VALIDATION:
                createSendToDiffusionValidationNotification(serviceContext, lifeCycleAction, datasetVersionDto);
                break;
            case REJECT_VALIDATION:
                createCancelValidationNotification(serviceContext, lifeCycleAction, datasetVersionDto, reasonOfRejection);
                break;
            case PUBLISH:
                createPublicationNotification(serviceContext, lifeCycleAction, datasetVersionDto);
                break;
            case CANCEL_PROGRAMMED_PUBLICATION:
                break;
            case VERSION:
                break;
        }
    }

    private void createSendToProductionValidationNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, lifeCycleAction, datasetVersionDto);
    }

    private void createSendToDiffusionValidationNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, lifeCycleAction, datasetVersionDto);
    }

    private void createCancelValidationNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto, String reasonOfRejection)
            throws MetamacWebException {
        createNotificationWithReceivers(serviceContext, lifeCycleAction, datasetVersionDto, reasonOfRejection);
    }

    private void createPublicationNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        createNotificationWithStatisticalOperationAndRoles(serviceContext, lifeCycleAction, datasetVersionDto);
    }

    private void createNotificationWithStatisticalOperationAndRoles(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternalFromDatasetVersion(datasetVersionDto);
        String actionCode = getActionCode(lifeCycleAction);
        String messageCode = getMessageCode(lifeCycleAction);
        MetamacRolesEnum[] diffusionValidationRoles = roles.containsKey(lifeCycleAction) ? roles.get(lifeCycleAction) : null;
        String statisticalOperationUrn = datasetVersionDto.getStatisticalOperation().getUrn();
        String reasonOfRejection = null;
        createNotification(serviceContext, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, diffusionValidationRoles, statisticalOperationUrn, reasonOfRejection);
    }

    private void createNotificationWithReceivers(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto, String reasonOfRejection)
            throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternalFromDatasetVersion(datasetVersionDto);
        String actionCode = getActionCode(lifeCycleAction);
        String messageCode = getMessageCode(lifeCycleAction);
        MetamacRolesEnum[] cancelValidationRoles = null;
        String statisticalOperationUrn = null;
        createNotification(serviceContext, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, cancelValidationRoles, statisticalOperationUrn, reasonOfRejection,
                datasetVersionDto.getCreationUser());
    }
    private void createNotification(ServiceContext ctx, String actionCode, String messageCode, ResourceInternal[] resources, MetamacRolesEnum[] roles, String statisticalOperationUrn,
            String reasonOfRejection, String... receiversUsernames) throws MetamacWebException {

        String subject = buildSubject(ctx, actionCode);
        Message message = buildMessage(ctx, messageCode, reasonOfRejection, resources);

        NoticeBuilder noticeBuilder = NoticeBuilder.notification().withMessages(message).withSendingApplication(getSendingApp()).withSendingUser(ctx.getUserId()).withSubject(subject);
        if (roles != null) {
            noticeBuilder = noticeBuilder.withRoles(roles);
        }
        if (StringUtils.isNotBlank(statisticalOperationUrn)) {
            noticeBuilder = noticeBuilder.withStatisticalOperations(statisticalOperationUrn);
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

    private Message buildMessage(ServiceContext ctx, String messageCode, String reasonOfRejection, ResourceInternal[] resources) {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);
        if (StringUtils.isNotBlank(reasonOfRejection)) {
            localisedMessage = localisedMessage + " (" + reasonOfRejection + ")";
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
