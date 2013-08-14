package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class GetDatasetVersionMainCoverages {

    @In(1)
    String datasetVersionUrn;
    
    @Out(1)
    List<ExternalItemDto> geographicCoverage;
    
    @Out(2)
    List<TemporalCodeDto> temporalCoverage;
    
    @Out(3)
    List<ExternalItemDto> measureCoverage;
}
