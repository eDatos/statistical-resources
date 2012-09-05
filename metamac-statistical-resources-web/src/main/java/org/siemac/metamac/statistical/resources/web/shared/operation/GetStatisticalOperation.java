package org.siemac.metamac.statistical.resources.web.shared.operation;

import org.siemac.metamac.core.common.dto.ExternalItemDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class GetStatisticalOperation {
    @In(1)
    String urn;
    
    @Out(1)
    ExternalItemDto operation;
}
