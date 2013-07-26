package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetStatisticOfficialitiesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetStatisticOfficialitiesResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetStatisticOfficialitiesActionHandler extends SecurityActionHandler<GetStatisticOfficialitiesAction, GetStatisticOfficialitiesResult>{

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;
        
    public GetStatisticOfficialitiesActionHandler() {
        super(GetStatisticOfficialitiesAction.class);
    }
    
    @Override
    public GetStatisticOfficialitiesResult executeSecurityAction(GetStatisticOfficialitiesAction action) throws ActionException {
        try {
            List<StatisticOfficialityDto> officialities = statisticalResourcesServiceFacade.findStatisticOfficialities(ServiceContextHolder.getCurrentServiceContext());
            return new GetStatisticOfficialitiesResult(officialities);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
