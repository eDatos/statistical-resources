package org.siemac.metamac.statistical.resources.core.io.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.parser.csv.domain.CsvObservation;

import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;

public interface MetamacCsv2StatRepoMapper {

    public static final String BEAN_ID = "metamacCsv2StatRepoMapper";

    /**
     * Transform Observation
     */
    public ObservationExtendedDto toObservation(CsvObservation observation, String datasourceId) throws MetamacException;

}
