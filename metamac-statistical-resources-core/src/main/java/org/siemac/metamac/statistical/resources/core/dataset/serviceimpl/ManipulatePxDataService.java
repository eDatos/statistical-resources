package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.ValidateDataVersusDsd;

import com.arte.statistic.parser.px.domain.PxModel;

// TODO mover la definición de este servicio al btdesign de sculptor
public interface ManipulatePxDataService {

    public static final String BEAN_ID = "manipulatePxDataService";

    public PxModel importPx(ServiceContext ctx, File pxFile, DataStructure dataStructure, String datasetID, String dataSourceID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception;

}
