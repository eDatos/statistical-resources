package org.siemac.metamac.statistical.resources.web.shared.dataset;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure = false)
public class VersionDatasetVersion {

    @In(1)
    DatasetVersionDto datasetVersionDto;

    @In(2)
    VersionTypeEnum   versionType;

    @Out(1)
    DatasetVersionDto resultDatasetVersionDto;
}
