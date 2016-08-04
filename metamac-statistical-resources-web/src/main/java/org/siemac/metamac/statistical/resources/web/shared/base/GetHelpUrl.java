package org.siemac.metamac.statistical.resources.web.shared.base;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetHelpUrl {

    @Out(1)
    String helpUrl;

}
