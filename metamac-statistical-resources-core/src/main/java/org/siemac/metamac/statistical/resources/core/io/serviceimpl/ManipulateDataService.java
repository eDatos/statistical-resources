package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;

public interface ManipulateDataService<S extends Object> {

    public void importData(S source, DataStructure dataStructure, String datasetID, String dataSourceID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception;
}
