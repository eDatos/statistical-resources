package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.facade.serviceapi.StatisticalResourcesServiceFacade;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetVersionResult;
import org.siemac.metamac.web.common.server.ServiceContextHolder;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionDatasetVersionActionHandler extends SecurityActionHandler<VersionDatasetVersionAction, VersionDatasetVersionResult> {

    @Autowired
    private StatisticalResourcesServiceFacade statisticalResourcesServiceFacade;

    public VersionDatasetVersionActionHandler() {
        super(VersionDatasetVersionAction.class);
    }

    @Override
    public VersionDatasetVersionResult executeSecurityAction(VersionDatasetVersionAction action) throws ActionException {
        try {
            // FIXME: using dataset not urn
            DatasetVersionDto datasetDto = statisticalResourcesServiceFacade.versioningDatasetVersion(ServiceContextHolder.getCurrentServiceContext(), action.getDatasetVersionDto().getUrn(),
                    action.getVersionType());
            return new VersionDatasetVersionResult(datasetDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
