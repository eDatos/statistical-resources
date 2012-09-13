package org.siemac.metamac.statistical.resources.web.shared.collection;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class VersionCollection {

    @In(1)
    String          urn;

    @In(2)
    VersionTypeEnum versionType;

    @Out(1)
    CollectionDto   collectionDto;

}
