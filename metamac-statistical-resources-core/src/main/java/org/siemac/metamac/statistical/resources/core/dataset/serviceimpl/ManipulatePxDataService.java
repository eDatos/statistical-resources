package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

// TODO mover la definición de este servicio al btdesign de sculptor
public interface ManipulatePxDataService {

    public static final String BEAN_ID = "manipulatePxDataService";

    public void importPx(ServiceContext ctx, File pxFile, String datasetID) throws org.siemac.metamac.core.common.exception.MetamacException;

}
