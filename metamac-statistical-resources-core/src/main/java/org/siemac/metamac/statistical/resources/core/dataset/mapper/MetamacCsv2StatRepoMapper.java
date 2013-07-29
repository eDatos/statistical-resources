package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.csv.domain.CsvObservation;

public interface MetamacCsv2StatRepoMapper {

    public static final String BEAN_ID = "metamacCsv2StatRepoMapper";

    /**
     * Transform Observation
     */
    public ObservationExtendedDto toObservation(CsvObservation observation, String datasourceId) throws MetamacException;

}
