package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.statistical.resources.web.shared.DTO.DsdAttributeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class GetDatasetAttributes {

    @In(1)
    String                urn;

    @Out(1)
    List<DsdAttributeDto> datasetVersionAttributes;
}
