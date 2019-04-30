package org.siemac.metamac.statistical.resources.web.server.handlers.multidataset;

import org.apache.commons.collections.ListUtils;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.multidataset.GetMultidatasetsResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwtplatform.dispatch.shared.ActionException;

public class GetMultidatasetsActionHandler extends SecurityActionHandler<GetMultidatasetsAction, GetMultidatasetsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public GetMultidatasetsActionHandler(Class<GetMultidatasetsAction> actionType) {
        super(actionType);
    }

    @Override
    public GetMultidatasetsResult executeSecurityAction(GetMultidatasetsAction action) throws ActionException {

        System.out.println("Suerte en la vida");
        return new GetMultidatasetsResult(ListUtils.EMPTY_LIST, 0, 0);
    }

}
