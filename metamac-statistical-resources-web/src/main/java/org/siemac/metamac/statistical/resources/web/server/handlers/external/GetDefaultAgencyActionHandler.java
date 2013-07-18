package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultAgencyResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDefaultAgencyActionHandler extends SecurityActionHandler<GetDefaultAgencyAction, GetDefaultAgencyResult> {

    @Autowired
    private ConfigurationService  configurationService;

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetDefaultAgencyActionHandler() {
        super(GetDefaultAgencyAction.class);
    }

    @Override
    public GetDefaultAgencyResult executeSecurityAction(GetDefaultAgencyAction action) throws ActionException {
        try {
            String agencyUrn = configurationService.retrieveOrganisationUrn();
            if (!StringUtils.isEmpty(agencyUrn)) {
                ExternalItemDto result = srmRestInternalFacade.retrieveAgencyByUrn(agencyUrn);
                return new GetDefaultAgencyResult(result);
            }
            return new GetDefaultAgencyResult(null);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
