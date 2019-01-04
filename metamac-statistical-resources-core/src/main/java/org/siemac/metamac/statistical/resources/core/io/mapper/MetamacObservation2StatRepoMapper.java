package org.siemac.metamac.statistical.resources.core.io.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.parser.generic.domain.Observation;

import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;

public interface MetamacObservation2StatRepoMapper {

    public static final String BEAN_ID = "metamacObservation2StatRepoMapper";

    /**
     * Transform Observation
     */
    public ObservationExtendedDto toObservation(Observation observation, String datasourceId) throws MetamacException;

}
