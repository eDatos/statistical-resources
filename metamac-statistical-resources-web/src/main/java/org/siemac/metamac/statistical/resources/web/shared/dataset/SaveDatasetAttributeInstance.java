package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveDatasetAttributeInstance {

    @In(1)
    String                  datasetVersionUrn;

    @In(2)
    DsdAttributeInstanceDto dsdAttributeInstanceDto;

    @Out(1)
    DsdAttributeInstanceDto savedDsdAttributeInstanceDto;
}
