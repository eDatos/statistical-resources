package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.dto.datasets.CategorisationDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.server.rest.SrmRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetCategorisationsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.CreateDatasetCategorisationsResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class CreateDatasetCategorisationsActionHandler extends SecurityActionHandler<CreateDatasetCategorisationsAction, CreateDatasetCategorisationsResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    @Autowired
    private StatisticalResourcesConfiguration configurationService;

    @Autowired
    private SrmRestInternalFacade             srmRestInternalFacade;

    public CreateDatasetCategorisationsActionHandler() {
        super(CreateDatasetCategorisationsAction.class);
    }

    @Override
    public CreateDatasetCategorisationsResult executeSecurityAction(CreateDatasetCategorisationsAction action) throws ActionException {
        ExternalItemDto maintainer = null;
        try {
            String organisationUrn = configurationService.retrieveOrganisationUrn();
            maintainer = srmRestInternalFacade.retrieveAgencyByUrn(organisationUrn);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        for (String categoryUrn : action.getCategoriesUrns()) {
            try {
                ExternalItemDto category = srmRestInternalFacade.retrieveCategoryByUrn(categoryUrn);
                CategorisationDto categorisationDto = buildCategorisation(category, maintainer);
                statisticalResourcesServiceFacade.createCategorisation(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionUrn(), categorisationDto);
            } catch (MetamacException e) {
                exceptionItems.addAll(e.getExceptionItems());
            }
        }
        if (!exceptionItems.isEmpty()) {
            throw WebExceptionUtils.createMetamacWebException(new MetamacException(exceptionItems));
        } else {
            return new CreateDatasetCategorisationsResult();
        }
    }

    private CategorisationDto buildCategorisation(ExternalItemDto category, ExternalItemDto maintainer) {
        CategorisationDto dto = new CategorisationDto();
        dto.setCategory(category);
        dto.setMaintainer(maintainer);
        return dto;
    }
}
