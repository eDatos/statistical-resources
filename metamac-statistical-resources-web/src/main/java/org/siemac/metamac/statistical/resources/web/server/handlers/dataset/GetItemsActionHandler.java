package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetItemsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetItemsResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetItemsActionHandler extends SecurityActionHandler<GetItemsAction, GetItemsResult> {

    @Autowired
    private SrmRestInternalFacade srmRestInternalFacade;

    public GetItemsActionHandler() {
        super(GetItemsAction.class);
    }

    @Override
    public GetItemsResult executeSecurityAction(GetItemsAction action) throws com.gwtplatform.dispatch.shared.ActionException {
        List<ItemDto> items = new ArrayList<ItemDto>();
        if (TypeExternalArtefactsEnum.CODELIST.equals(action.getItemSchemeType())) {
            items = srmRestInternalFacade.retrieveCodes(action.getItemSchemeUrn());
        } else if (TypeExternalArtefactsEnum.CONCEPT_SCHEME.equals(action.getItemSchemeType())) {
            items = srmRestInternalFacade.retrieveConcepts(action.getItemSchemeUrn());
        }
        return new GetItemsResult(items);

    }
}
