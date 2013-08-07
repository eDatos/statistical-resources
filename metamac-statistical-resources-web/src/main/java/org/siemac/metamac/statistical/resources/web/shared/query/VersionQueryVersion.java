package org.siemac.metamac.statistical.resources.web.shared.query;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class VersionQueryVersion {

    @In(1)
    String          queryVersionUrn;

    @In(2)
    VersionTypeEnum versionType;

    @Out(1)
    QueryVersionDto queryVersionDto;
}
