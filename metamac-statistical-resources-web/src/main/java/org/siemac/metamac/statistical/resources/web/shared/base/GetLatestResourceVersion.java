package org.siemac.metamac.statistical.resources.web.shared.base;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetLatestResourceVersion {

    @In(1)
    String                          resourceUrn;

    @Out(1)
    LifeCycleStatisticalResourceDto resourceVersion;
}
