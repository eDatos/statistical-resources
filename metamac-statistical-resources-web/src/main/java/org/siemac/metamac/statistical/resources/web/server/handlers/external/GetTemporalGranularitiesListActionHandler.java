package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import java.util.ArrayList;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetTemporalGranularitiesListActionHandler extends SecurityActionHandler<GetTemporalGranularitiesListAction, GetTemporalGranularitiesListResult> {

    @Autowired
    private ConfigurationService  configurationService;

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetTemporalGranularitiesListActionHandler() {
        super(GetTemporalGranularitiesListAction.class);
    }

    @Override
    public GetTemporalGranularitiesListResult executeSecurityAction(GetTemporalGranularitiesListAction action) throws ActionException {
        String temporalGranularityCodelistUrn = configurationService.getProperty(StatisticalResourcesConfigurationConstants.DEFAULT_CODELIST_TEMPORAL_GRANULARITY_URN);
        if (!StringUtils.isEmpty(temporalGranularityCodelistUrn)) {
            ExternalItemsResult result = srmRestInternalFacade.findCodesInCodelist(temporalGranularityCodelistUrn, action.getFirstResult(), action.getMaxResults(), action.getCriteria());
            return new GetTemporalGranularitiesListResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
        }
        return new GetTemporalGranularitiesListResult(new ArrayList<ExternalItemDto>(), 0, 0);
    }
}