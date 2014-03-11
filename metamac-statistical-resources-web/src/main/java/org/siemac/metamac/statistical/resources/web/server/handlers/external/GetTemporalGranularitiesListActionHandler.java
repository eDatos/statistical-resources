package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import java.util.ArrayList;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetTemporalGranularitiesListActionHandler extends SecurityActionHandler<GetTemporalGranularitiesListAction, GetTemporalGranularitiesListResult> {

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public GetTemporalGranularitiesListActionHandler() {
        super(GetTemporalGranularitiesListAction.class);
    }

    @Override
    public GetTemporalGranularitiesListResult executeSecurityAction(GetTemporalGranularitiesListAction action) throws ActionException {
        try {
            String temporalGranularityCodelistUrn = configurationService.retrieveDefaultCodelistTemporalGranularityUrn();
            if (!StringUtils.isEmpty(temporalGranularityCodelistUrn)) {
                ExternalItemsResult result = srmRestInternalFacade.findCodesInCodelist(temporalGranularityCodelistUrn, action.getFirstResult(), action.getMaxResults(), action.getCriteria());
                return new GetTemporalGranularitiesListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
            }
            return new GetTemporalGranularitiesListResult(new ArrayList<ExternalItemDto>(), 0, 0);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
