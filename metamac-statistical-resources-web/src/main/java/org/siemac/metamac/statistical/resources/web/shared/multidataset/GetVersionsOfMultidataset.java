package org.siemac.metamac.statistical.resources.web.shared.multidataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetVersionsOfMultidataset {

    @In(1)
    String                          multidatasetVersionUrn;

    @Out(1)
    List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos;
}
