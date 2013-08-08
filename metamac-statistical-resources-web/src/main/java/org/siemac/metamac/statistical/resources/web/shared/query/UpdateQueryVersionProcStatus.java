package org.siemac.metamac.statistical.resources.web.shared.query;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateQueryVersionProcStatus {

    @In(1)
    QueryVersionDto     queryVersionToUpdateProcStatus;

    @In(2)
    LifeCycleActionEnum lifeCycleAction;

    @Optional
    @In(3)
    VersionTypeEnum     versionType;

    @Out(1)
    QueryVersionDto     queryVersionDto;
}
