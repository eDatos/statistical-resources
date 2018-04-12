package org.siemac.metamac.statistical.resources.web.shared.multidataset;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteMultidatasetVersions {

    @In(1)
    List<String> urns;
}
