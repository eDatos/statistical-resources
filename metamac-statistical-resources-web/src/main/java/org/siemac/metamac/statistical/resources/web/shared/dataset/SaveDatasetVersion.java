package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveDatasetVersion {

    @In(1)
    DatasetVersionDto datasetVersion;

    @In(2)
    String            statisticalOperationCode;

    @Out(1)
    DatasetVersionDto savedDatasetVersion;
}
