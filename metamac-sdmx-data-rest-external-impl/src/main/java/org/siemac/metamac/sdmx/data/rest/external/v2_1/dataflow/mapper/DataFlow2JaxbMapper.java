package org.siemac.metamac.sdmx.data.rest.external.v2_1.dataflow.mapper;

import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowsType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public interface DataFlow2JaxbMapper extends BaseDo2JaxbMapper {

    public DataflowsType dataflowsDo2Jaxb(List<DatasetVersion> sourceList, boolean asStub) throws MetamacException;
    public DataflowType dataflowDo2Jaxb(DatasetVersion datasetVersion, boolean asStub) throws MetamacException;

}
