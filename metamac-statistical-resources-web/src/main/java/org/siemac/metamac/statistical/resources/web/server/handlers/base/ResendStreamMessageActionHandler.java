package org.siemac.metamac.statistical.resources.web.server.handlers.base;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.base.ResendStreamMessageAction;
import org.siemac.metamac.statistical.resources.web.shared.base.ResendStreamMessageResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class ResendStreamMessageActionHandler extends SecurityActionHandler<ResendStreamMessageAction, ResendStreamMessageResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public ResendStreamMessageActionHandler() {
        super(ResendStreamMessageAction.class);
    }

    @Override
    public ResendStreamMessageResult executeSecurityAction(ResendStreamMessageAction action) throws ActionException {

        try {
            LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto = action.getLifeCycleStatisticalResourceDto();

            if (lifeCycleStatisticalResourceDto instanceof QueryVersionDto) {
                QueryVersionBaseDto resendPublishedQueryVersionStreamMessage = statisticalResourcesServiceFacade
                        .resendPublishedQueryVersionStreamMessage(ServiceContextHolder.getCurrentServiceContext(), lifeCycleStatisticalResourceDto.getUrn());
                lifeCycleStatisticalResourceDto.setPublicationStreamStatus(resendPublishedQueryVersionStreamMessage.getPublicationStreamStatus());
            } else if (lifeCycleStatisticalResourceDto instanceof DatasetVersionDto) {
                DatasetVersionBaseDto resendPublishedStreamMessage = statisticalResourcesServiceFacade.resendPublishedDatasetVersionStreamMessage(ServiceContextHolder.getCurrentServiceContext(),
                        lifeCycleStatisticalResourceDto.getUrn());
                lifeCycleStatisticalResourceDto.setPublicationStreamStatus(resendPublishedStreamMessage.getPublicationStreamStatus());
            } else if (lifeCycleStatisticalResourceDto instanceof PublicationVersionDto) {
                PublicationVersionBaseDto resendPublishedPublicationVersionStreamMessage = statisticalResourcesServiceFacade
                        .resendPublishedPublicationVersionStreamMessage(ServiceContextHolder.getCurrentServiceContext(), lifeCycleStatisticalResourceDto.getUrn());
                resendPublishedPublicationVersionStreamMessage.setPublicationStreamStatus(resendPublishedPublicationVersionStreamMessage.getPublicationStreamStatus());
            }

            return new ResendStreamMessageResult(lifeCycleStatisticalResourceDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
