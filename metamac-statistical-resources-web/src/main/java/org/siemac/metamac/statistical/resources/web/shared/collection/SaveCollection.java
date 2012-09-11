package org.siemac.metamac.statistical.resources.web.shared.collection;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class SaveCollection {

    @In(1)
    CollectionDto collectionDto;

    @Out(1)
    CollectionDto savedCollection;

}
