package org.siemac.metamac.statistical.resources.web.shared.external;

import org.siemac.metamac.core.common.dto.ExternalItemDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDefaultAgency {

    @Out(1)
    ExternalItemDto agency;
}