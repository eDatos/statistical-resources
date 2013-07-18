package org.siemac.metamac.statistical.resources.web.server.handlers.external;

import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDefaultLanguageInfoResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDefaultLanguageInfoActionHandler extends SecurityActionHandler<GetDefaultLanguageInfoAction, GetDefaultLanguageInfoResult> {

    @Autowired
    private ConfigurationService  configurationService;

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetDefaultLanguageInfoActionHandler() {
        super(GetDefaultLanguageInfoAction.class);
    }

    @Override
    public GetDefaultLanguageInfoResult executeSecurityAction(GetDefaultLanguageInfoAction action) throws ActionException {
        try {
            String languageCodeUrn = configurationService.retrieveDefaultCodeLanguageUrn();
            String languagesCodelistUrn = configurationService.retrieveDefaultCodelistLanguagesUrn();
            ExternalItemDto language = null;
            if (languageCodeUrn != null) {
                language = srmRestInternalFacade.retrieveCodeByUrn(languageCodeUrn);
            }
            return new GetDefaultLanguageInfoResult(language, languagesCodelistUrn);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

}
