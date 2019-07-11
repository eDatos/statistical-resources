package org.siemac.metamac.statistical.resources.web.shared.dataset;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class CreateDatabaseDatasource {

    @In(1)
    String urn;

    @In(2)
    String tablename;
}
