package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetVersionsOfQuery {

    @In(1)
    String                    queryVersionUrn;

    @Out(1)
    List<QueryVersionBaseDto> queryVersionBaseDtos;
}
