package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateQueryVersionProcStatus {

    @In(1)
    List<QueryVersionDto> queryVersionsToUpdateProcStatus;

    @In(2)
    LifeCycleActionEnum   lifeCycleAction;

    @Optional
    @Out(1)
    QueryVersionDto       queryVersionDto;
}
