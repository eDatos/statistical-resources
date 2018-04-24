package org.siemac.metamac.statistical.resources.core.multidataset.mapper;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapper;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public interface MultidatasetDo2DtoMapper extends BaseDo2DtoMapper {

    // Multidataset
    public RelatedResourceDto multidatasetVersionDoToMultidatasetRelatedResourceDto(MultidatasetVersion source) throws MetamacException;

    // Multidataset Version
    public RelatedResourceDto multidatasetVersionDoToMultidatasetVersionRelatedResourceDto(MultidatasetVersion source);
    public MultidatasetVersionDto multidatasetVersionDoToDto(MultidatasetVersion source) throws MetamacException;
    public MultidatasetVersionBaseDto multidatasetVersionDoToBaseDto(MultidatasetVersion source) throws MetamacException;
    public List<MultidatasetVersionBaseDto> multidatasetVersionDoListToDtoList(List<MultidatasetVersion> expected) throws MetamacException;

    // Multidataset structure
    public MultidatasetCubeDto multidatasetCubeDoToDto(MultidatasetCube multidatasetCube) throws MetamacException;
    public List<MultidatasetCubeDto> multidatasetCubeDoListToDtoList(List<MultidatasetCube> sources) throws MetamacException;
}
