package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.Locale;

import javax.ws.rs.core.Response;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.ServiceContextUtils;
import org.siemac.metamac.rest.notices.v1_0.domain.Message;
import org.siemac.metamac.rest.notices.v1_0.domain.Notice;
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
    private MetamacApisLocator metamacApisLocator;

    @Autowired
    private RestExceptionUtils restExceptionUtils;

    @Autowired
    private RestMapper         restMapper;

    @Override
    public void createLifeCycleNotification(ServiceContext serviceContext, LifeCycleActionEnum lifeCycleAction, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        switch (lifeCycleAction) {
            case SEND_TO_PRODUCTION_VALIDATION:
                createSendToProductionValidationNotification(serviceContext, datasetVersionDto);
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
            case CANCEL_PROGRAMMED_PUBLICATION:
                // TODO METAMAC-1991
                break;
            case VERSION:
                // TODO METAMAC-1991
                break;
        }
    }

    @Override
    public void createSendToProductionValidationNotification(ServiceContext serviceContext, DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        ResourceInternal resourceInternal = restMapper.buildResourceInternalFromDatasetVersion(datasetVersionDto);
        String actionCode = ServiceNoticeAction.RESOURCE_SEND_PRODUCTION_VALIDATION;
        String messageCode = ServiceNoticeMessage.RESOURCE_SEND_PRODUCTION_VALIDATION_OK;
        MetamacRolesEnum[] roles = new MetamacRolesEnum[]{MetamacRolesEnum.TECNICO_PRODUCCION};
        String statisticalOperationUrn = datasetVersionDto.getStatisticalOperation().getUrn();
        createNotification(serviceContext, actionCode, messageCode, new ResourceInternal[]{resourceInternal}, roles, statisticalOperationUrn);
    }

    private void createNotification(ServiceContext ctx, String actionCode, String messageCode, ResourceInternal[] resources, MetamacRolesEnum[] roles, String statisticalOperationUrn)
            throws MetamacWebException {
        Locale locale = ServiceContextUtils.getLocale(ctx);
        String localisedAction = LocaleUtil.getMessageForCode(actionCode, locale);
        String localisedMessage = LocaleUtil.getMessageForCode(messageCode, locale);

        String sendingApp = MetamacApplicationsEnum.GESTOR_RECURSOS_ESTADISTICOS.getName();
        String subject = "[" + sendingApp + "] " + localisedAction;

        Message message = MessageBuilder.message().withText(localisedMessage).withResources(resources).build();

        Notice notification = NoticeBuilder.notification().withMessages(message).withSendingApplication(sendingApp).withSendingUser(ctx.getUserId()).withSubject(subject).withRoles(roles)
                .withStatisticalOperations(statisticalOperationUrn).build();

        Response response;
        try {
            response = metamacApisLocator.getNoticesRestInternalFacadeV10().createNotice(notification);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
        this.restExceptionUtils.checkSendNotificationRestResponseAndThrowErrorIfApplicable(ctx, response);
    }
}
