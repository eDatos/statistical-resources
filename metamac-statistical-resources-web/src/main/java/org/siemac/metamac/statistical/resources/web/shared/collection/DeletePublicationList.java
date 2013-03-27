package org.siemac.metamac.statistical.resources.web.shared.collection;

import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure=false)
public class DeletePublicationList {

    @In(1)
    List<String> urns;
}
