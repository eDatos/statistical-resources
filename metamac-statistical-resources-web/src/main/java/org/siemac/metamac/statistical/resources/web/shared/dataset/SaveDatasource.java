package org.siemac.metamac.statistical.resources.web.shared.dataset;


import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class SaveDatasource {

    @In(1)
    DatasourceDto datasource;
    
    @Out(1)
    DatasourceDto datasourceSaved;
}
