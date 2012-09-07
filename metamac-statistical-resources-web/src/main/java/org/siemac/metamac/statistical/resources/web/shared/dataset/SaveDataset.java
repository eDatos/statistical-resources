package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveDataset {

    @In(1)
    DatasetDto dataset;

    @Out(1)
    DatasetDto savedDataset;
}
