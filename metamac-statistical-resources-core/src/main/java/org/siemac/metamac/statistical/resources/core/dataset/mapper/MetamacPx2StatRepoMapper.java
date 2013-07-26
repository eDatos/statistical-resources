package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.px.PxParser.PxDataByDimensionsIterator;
import com.arte.statistic.parser.px.domain.PxModel;

public interface MetamacPx2StatRepoMapper {

    /**
     * Transform attributes (global, dimensions, observations...) removing dimensions with '*' as codes, and assigning new attributes identifiers
     * IMPORTANT: Change pxModel!
     */
    public void reviewPxAttributesIdentifiersAndDimensions(PxModel pxModel);

    /**
     * Transform to dataset
     */
    // public DatasetDto toDataset(PxModel pxModel, PxImportDto pxImportDto) throws ApplicationException;

    public ObservationExtendedDto toObservation(PxDataByDimensionsIterator iterator) throws MetamacException;

    /**
     * Transform observations and observations attributes
     */
    // public List<ObservationExtendedDto> toObservations(PxDataIterator pxDataIterator, List<PxAttribute> pxAttributesOneObservation, List<DimensionDto> dimensionsDto) throws ApplicationException;

    /**
     * Transform observations and observations attributes
     */
    // public ObservationExtendedIterator toObservationsIterator(PxDataIterator pxDataIterator, List<PxAttribute> pxAttributesOneObservation, List<DimensionDto> dimensionsDto)
    // throws ApplicationException;

    /**
     * Transform dataset and dimensions attributes
     */
    // public List<AttributeDto> toAttributes(PxModel pxModel) throws ApplicationException;

    /**
     * Transform observations attributes (only attributes for one observation)
     * 
     * @return Map with key = unique key of observations; value = list of attributes of observation
     */
    // public Map<String, List<AttributeBasicDto>> toAttributesObservations(List<PxAttribute> attributesOneObservation, List<DimensionDto> dimensionsDto) throws ApplicationException;
    //
    // public InternationalStringDto toInternationalStringDsd(com.arte.statistic.parser.px.domain.InternationalString source) throws ApplicationException;
    //
    // public com.arte.statistic.dataset.repository.dto.InternationalStringDto toInternationalStringStatisticRepository(com.arte.statistic.parser.px.domain.InternationalString source)
    // throws ApplicationException;

}
