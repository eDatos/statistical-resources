package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;

public interface MetamacPx2StatRepoMapper {

    public static final String BEAN_ID = "metamacPx2StatRepoMapper";

    /**
     * Transform attributes (global, dimensions, observations...) removing dimensions with '*' as codes, and assigning new attributes identifiers
     * IMPORTANT: Change pxModel!
     */
    public void reviewPxAttributesIdentifiersAndDimensions(PxModel pxModel);

    /**
     * Transform to dataset
     */
    public ObservationExtendedDto toObservation(PxObservation observation, String datasourceId) throws MetamacException;

}
