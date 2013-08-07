package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateQueryVersionsProcStatus {

    @In(1)
    List<QueryVersionDto> queryVersionsToUpdateProcStatus;

    @In(2)
    LifeCycleActionEnum   lifeCycleAction;

    @Optional
    @In(3)
    VersionTypeEnum       versionType;

    @Optional
    @Out(1)
    QueryVersionDto       queryVersionDto;
}
