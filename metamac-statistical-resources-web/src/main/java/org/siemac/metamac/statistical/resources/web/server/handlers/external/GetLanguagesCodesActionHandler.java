package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import java.util.ArrayList;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetLanguagesCodesActionHandler extends SecurityActionHandler<GetLanguagesCodesAction, GetLanguagesCodesResult> {

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public GetLanguagesCodesActionHandler() {
        super(GetLanguagesCodesAction.class);
    }

    @Override
    public GetLanguagesCodesResult executeSecurityAction(GetLanguagesCodesAction action) throws ActionException {
        try {
            String languagesCodelistUrn = configurationService.retrieveDefaultCodelistLanguagesUrn();
            if (languagesCodelistUrn != null) {
                ExternalItemsResult result = srmRestInternalFacade.findCodesInCodelist(languagesCodelistUrn, action.getFirstResult(), action.getMaxResults(), new MetamacWebCriteria());
                return new GetLanguagesCodesResult(result.getExternalItemDtos(), result.getFirstResult(), result.getTotalResults());
            }
            return new GetLanguagesCodesResult(new ArrayList<ExternalItemDto>(), 0, 0);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
