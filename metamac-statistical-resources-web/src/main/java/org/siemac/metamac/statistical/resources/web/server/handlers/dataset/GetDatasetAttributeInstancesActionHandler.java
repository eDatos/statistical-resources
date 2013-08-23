package org.siemac.metamac.statistical.resources.web.server.handlers.dataset;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributeInstancesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributeInstancesResult;
import org.siemac.metamac.web.common.server.handlers.SecurityActionHandler;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.shared.ActionException;

@Component
public class GetDatasetAttributeInstancesActionHandler extends SecurityActionHandler<GetDatasetAttributeInstancesAction, GetDatasetAttributeInstancesResult> {

    public GetDatasetAttributeInstancesActionHandler() {
        super(GetDatasetAttributeInstancesAction.class);
    }

    @Override
    public GetDatasetAttributeInstancesResult executeSecurityAction(GetDatasetAttributeInstancesAction action) throws ActionException {
        List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos = new ArrayList<DsdAttributeInstanceDto>();
        // TODO
        return new GetDatasetAttributeInstancesResult(dsdAttributeInstanceDtos);
    }
}
