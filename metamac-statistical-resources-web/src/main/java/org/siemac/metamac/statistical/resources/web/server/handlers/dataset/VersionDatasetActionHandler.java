package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.server.MOCK.MockServices;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class VersionDatasetActionHandler extends SecurityActionHandler<VersionDatasetAction, VersionDatasetResult> {

    public VersionDatasetActionHandler() {
        super(VersionDatasetAction.class);
    }

    @Override
    public VersionDatasetResult executeSecurityAction(VersionDatasetAction action) throws ActionException {
        try {
            DatasetDto datasetDto = MockServices.versionDataset(action.getUrn(), action.getVersionType());
            return new VersionDatasetResult(datasetDto);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }
}
