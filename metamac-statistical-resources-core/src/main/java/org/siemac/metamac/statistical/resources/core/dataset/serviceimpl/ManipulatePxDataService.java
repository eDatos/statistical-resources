package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.ValidateDataVersusDsd;

// TODO mover la definición de este servicio al btdesign de sculptor
public interface ManipulatePxDataService {

    public static final String BEAN_ID = "manipulatePxDataService";

    public void importPx(ServiceContext ctx, File pxFile, DataStructure dataStructure, String datasetID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception;

}
