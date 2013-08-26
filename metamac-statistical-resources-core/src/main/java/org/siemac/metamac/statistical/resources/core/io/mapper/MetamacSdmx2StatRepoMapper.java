package org.siemac.metamac.statistical.resources.core.io.mapper;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;

import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;

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
     * Convert parser conditions into repository conditions
     * 
     * @param serieConditions
     * @return
     * @throws MetamacException
     */
    public List<ConditionDimensionDto> conditionsToRepository(List<DimensionCodeInfo> serieConditions) throws MetamacException;
}
