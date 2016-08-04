package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.statistical.resources.web.server.rest.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetCommonMetadataConfigurationsListActionHandler extends SecurityActionHandler<GetCommonMetadataConfigurationsListAction, GetCommonMetadataConfigurationsListResult> {

    @Autowired
    private CommonMetadataRestExternalFacade commonMetadataRestExternalFacade;

    public GetCommonMetadataConfigurationsListActionHandler() {
        super(GetCommonMetadataConfigurationsListAction.class);
    }

    @Override
    public GetCommonMetadataConfigurationsListResult executeSecurityAction(GetCommonMetadataConfigurationsListAction action) throws ActionException {
        return new GetCommonMetadataConfigurationsListResult(commonMetadataRestExternalFacade.findConfigurations(action.getCriteria()));
    }

}
