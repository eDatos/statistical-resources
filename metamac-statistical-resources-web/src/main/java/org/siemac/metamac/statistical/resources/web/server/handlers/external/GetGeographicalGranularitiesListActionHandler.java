package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import java.util.ArrayList;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetGeographicalGranularitiesListActionHandler extends SecurityActionHandler<GetGeographicalGranularitiesListAction, GetGeographicalGranularitiesListResult> {

    @Autowired
    private ConfigurationService  configurationService;

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetGeographicalGranularitiesListActionHandler() {
        super(GetGeographicalGranularitiesListAction.class);
    }

    @Override
    public GetGeographicalGranularitiesListResult executeSecurityAction(GetGeographicalGranularitiesListAction action) throws ActionException {
        try {
            String geoGranularityCodelistUrn = configurationService.retrieveDefaultCodelistGeographicalGranularityUrn();
            if (!StringUtils.isEmpty(geoGranularityCodelistUrn)) {
                ExternalItemsResult result = srmRestInternalFacade.findCodesInCodelist(geoGranularityCodelistUrn, action.getFirstResult(), action.getMaxResults(), action.getCriteria());
                return new GetGeographicalGranularitiesListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
            }
            return new GetGeographicalGranularitiesListResult(new ArrayList<ExternalItemDto>(), 0, 0);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
