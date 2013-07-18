package org.siemac.metamac.statistical.resources.web.shared.query;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveQuery {

    @In(1)
    QueryDto query;

    @Out(1)
    QueryDto savedQuery;
}
