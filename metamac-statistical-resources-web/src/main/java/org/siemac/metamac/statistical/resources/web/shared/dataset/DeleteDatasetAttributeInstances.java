package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteDatasetAttributeInstances {

    @In(1)
    DsdAttributeDto attributeDto;

    @In(2)
    String          datasetVersionUrn;

    @In(3)
    List<String>    uuids;
}
