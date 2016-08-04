package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateDatasetVersionsProcStatus {

    @In(1)
    List<DatasetVersionBaseDto> datasetVersionsToUpdateProcStatus;

    @In(2)
    LifeCycleActionEnum         lifeCycleAction;

    @Optional
    @In(3)
    VersionTypeEnum             versionType;

    @Optional
    @In(4)
    Date                        validFrom;

    @Optional
    @In(5)
    String                      reasonOfRejection;

    @Optional
    @Out(1)
    MetamacWebException         notificationException;
}
