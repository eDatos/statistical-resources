package org.siemac.metamac.statistical.resources.web.shared.multidataset;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveMultidatasetVersion {

    @In(1)
    MultidatasetVersionDto multidatasetVersionDto;

    @In(2)
    ExternalItemDto       statisticalOperationDto;

    @Out(1)
    MultidatasetVersionDto savedMultidatasetVersion;
}
