package org.siemac.metamac.statistical.resources.web.server.handlers.base;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;
import org.siemac.metamac.statistical.resources.web.shared.base.GetLatestResourceVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.base.GetLatestResourceVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetLatestResourceVersionActionHandler extends SecurityActionHandler<GetLatestResourceVersionAction, GetLatestResourceVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetLatestResourceVersionActionHandler() {
        super(GetLatestResourceVersionAction.class);
    }

    @Override
    public GetLatestResourceVersionResult executeSecurityAction(GetLatestResourceVersionAction action) throws ActionException {
        try {
            String resourceUrn = action.getResourceUrn();
            LifeCycleStatisticalResourceDto statisticalResourceDto = null;

            if (StatisticalResourcesUrnParserUtils.isDatasetUrn(resourceUrn)) {
                statisticalResourceDto = statisticalResourcesServiceFacade.retrieveLatestDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), resourceUrn);
            } else if (StatisticalResourcesUrnParserUtils.isQueryUrn(resourceUrn)) {
                statisticalResourceDto = statisticalResourcesServiceFacade.retrieveLatestQueryVersion(ServiceContextHolder.getCurrentServiceContext(), resourceUrn);
            } else if (StatisticalResourcesUrnParserUtils.isPublicationUrn(resourceUrn)) {
                statisticalResourceDto = statisticalResourcesServiceFacade.retrieveLatestPublicationVersion(ServiceContextHolder.getCurrentServiceContext(), resourceUrn);
            } else if (StatisticalResourcesUrnParserUtils.isMultidatasetUrn(resourceUrn)) {
                statisticalResourceDto = statisticalResourcesServiceFacade.retrieveLatestMultidatasetVersion(ServiceContextHolder.getCurrentServiceContext(), resourceUrn);
            }

            return new GetLatestResourceVersionResult(statisticalResourceDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
