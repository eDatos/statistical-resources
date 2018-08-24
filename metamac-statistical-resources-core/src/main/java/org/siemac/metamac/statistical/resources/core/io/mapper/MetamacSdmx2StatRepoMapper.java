package org.siemac.metamac.statistical.resources.core.io.mapper;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;

import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;

import es.gobcan.istac.edatos.dataset.repository.dto.ConditionDimensionDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;

public interface MetamacSdmx2StatRepoMapper {

    /**
     * Convert Datas and Attributes into statistic-dataset-repository-model
     *
     * @param dataContainerDto
     * @param attributesMap
     * @param dataDtos
     * @param datasourceId
     * @throws MetamacException
     */
    public void populateDatas(DataContainer dataContainerDto, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, String datasourceId)
            throws MetamacException;

    /**
     * Convert parser conditions into repository conditions Map to query attributes
     *
     * @param serieConditions
     * @return
     * @throws MetamacException
     */
    public List<ConditionDimensionDto> conditionsToRepositoryList(List<DimensionCodeInfo> serieConditions) throws MetamacException;

    /**
     * Convert parser conditions into repository conditions List to query observations
     *
     * @param serieConditions
     * @return
     * @throws MetamacException
     */
    public Map<String, List<String>> conditionsToRepositoryMap(List<DimensionCodeInfo> serieConditions) throws MetamacException;
}
