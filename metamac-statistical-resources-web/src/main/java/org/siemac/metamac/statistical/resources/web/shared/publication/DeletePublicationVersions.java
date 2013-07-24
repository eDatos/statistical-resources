package org.siemac.metamac.statistical.resources.web.shared.publication;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class DeletePublicationVersions {

    @In(1)
    List<String> urns;
}
