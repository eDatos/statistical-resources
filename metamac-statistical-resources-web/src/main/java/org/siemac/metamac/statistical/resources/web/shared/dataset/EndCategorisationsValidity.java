package org.siemac.metamac.statistical.resources.web.shared.dataset;

import java.util.Date;
import java.util.List;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;

@GenDispatch(isSecure = false)
public class EndCategorisationsValidity {

    @In(1)
    List<String> urns;

    @In(2)
    Date         validTo;
}