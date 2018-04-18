package org.siemac.metamac.statistical.resources.core.multidataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetDto2DoMapper extends BaseDto2DoMapper {

    public void checkOptimisticLocking(MultidatasetVersionBaseDto multidatasetVersionDto) throws MetamacException;

    public MultidatasetVersion multidatasetVersionDtoToDo(MultidatasetVersionDto source) throws MetamacException;

    public MultidatasetCube multidatasetCubeDtoToDo(MultidatasetCubeDto source) throws MetamacException;

}
