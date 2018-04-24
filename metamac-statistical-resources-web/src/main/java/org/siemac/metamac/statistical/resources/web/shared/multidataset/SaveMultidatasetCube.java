package org.siemac.metamac.statistical.resources.web.shared.multidataset;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveMultidatasetCube {

    @In(1)
    String                 multidatasetVersionUrn;

    @In(2)
    MultidatasetCubeDto    element;

    @Out(1)
    MultidatasetVersionDto multidatasetVersionDto;

    @Out(2)
    MultidatasetCubeDto    savedElement;
}
