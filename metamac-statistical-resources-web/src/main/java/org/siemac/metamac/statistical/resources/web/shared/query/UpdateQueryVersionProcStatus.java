package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Optional;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class UpdateQueryVersionProcStatus {

    @In(1)
    List<QueryVersionDto> queryVersionsToUpdateProcStatus;

    @In(2)
    ProcStatusEnum        nextProcStatus;

    @Optional
    @Out(1)
    QueryVersionDto       queryVersionDto;
}
