package org.siemac.metamac.statistical.resources.web.shared.base;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetUserGuideUrl {

    @Out(1)
    String userGuideUrl;

}
