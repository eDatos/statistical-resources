package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateDatasetVersionProcStatus {

    @In(1)
    List<DatasetVersionDto> datasetVersionsToUpdateProcStatus;

    @In(2)
    LifeCycleActionEnum     lifeCycleAction;

    @Optional
    @In(3)
    VersionTypeEnum         versionType;

    @Optional
    @Out(1)
    DatasetVersionDto       datasetVersionDto;
}
