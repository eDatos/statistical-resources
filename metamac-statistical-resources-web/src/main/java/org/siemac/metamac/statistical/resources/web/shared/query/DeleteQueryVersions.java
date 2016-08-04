package org.siemac.metamac.statistical.resources.web.shared.query;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeleteQueryVersions {

    @In(1)
    List<String> urns;
}