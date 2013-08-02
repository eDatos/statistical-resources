package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

public interface MetamacPx2StatRepoMapper {

    public static final String BEAN_ID = "metamacPx2StatRepoMapper";

    /**
     * Transform to observation
     */
    public ObservationExtendedDto toObservation(PxObservation observation, String datasourceId, Map<String, List<AttributeBasicDto>> attributesObservations) throws MetamacException;

    /**
     * Calculate map of attributes
     * 
     * @param pxModel
     * @param preferredLanguage
     * @return
     * @throws MetamacException
     */
    public Map<String, List<AttributeBasicDto>> toAttributesObservations(PxModel pxModel, String preferredLanguage, List<ComponentInfo> dimensionsInfos, Map<String, Integer> dimensionsOrderPxMap)
            throws MetamacException;

    /**
     * @param pxModel
     * @return
     * @throws MetamacException
     */
    public Map<String, Integer> generateDimensionsOrderPxMap(PxModel pxModel) throws MetamacException;
}
