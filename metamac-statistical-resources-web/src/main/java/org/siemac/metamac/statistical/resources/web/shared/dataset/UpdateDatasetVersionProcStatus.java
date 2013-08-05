package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateDatasetVersionProcStatus {

    @In(1)
    List<DatasetVersionDto> datasetVersionsToUpdateProcStatus;

    @In(2)
    ProcStatusEnum          nextProcStatus;

    @Optional
    @Out(1)
    DatasetVersionDto       datasetVersionDto;
}
