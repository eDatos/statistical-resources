package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateDatasetProcStatus {

    @In(1)
    String                            urn;

    @In(2)
    ProcStatusEnum nextProcStatus;

    @In(3)
    ProcStatusEnum currentProcStatus;

    @Out(1)
    DatasetDto                        datasetDto;

}
