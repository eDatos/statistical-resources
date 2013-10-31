package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteCategorisationsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class DeleteCategorisationsActionHandler extends SecurityActionHandler<DeleteCategorisationsAction, DeleteCategorisationsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public DeleteCategorisationsActionHandler() {
        super(DeleteCategorisationsAction.class);
    }

    @Override
    public DeleteCategorisationsResult executeSecurityAction(DeleteCategorisationsAction action) throws ActionException {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        for (String urn : action.getUrns()) {
            try {
                statisticalResourcesServiceFacade.deleteCategorisation(ServiceContextHolder.getCurrentServiceContext(), urn);
            } catch (MetamacException e) {
                exceptionItems.addAll(e.getExceptionItems());
            }
        }
        if (!exceptionItems.isEmpty()) {
            throw WebExceptionUtils.createMetamacWebException(new MetamacException(exceptionItems));
        }
        return new DeleteCategorisationsResult();
    }

}
