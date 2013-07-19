package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateDatasetProcStatus {

    @In(1)
    DatasetVersionDto     datasetVersionDto;

    @In(2)
    ProcStatusEnum nextProcStatus;

    @Out(1)
    DatasetVersionDto     resultDatasetVersionDto;

}
