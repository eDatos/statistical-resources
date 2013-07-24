package org.siemac.metamac.statistical.resources.web.shared.query;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveQueryVersion {

    @In(1)
    QueryVersionDto queryVersionDto;

    @Out(1)
    QueryVersionDto savedQueryVersionDto;
}
