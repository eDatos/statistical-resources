package org.siemac.metamac.statistical.resources.core.utils;

import java.util.Arrays;

import es.gobcan.istac.edatos.dataset.repository.dto.CodeDimensionDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ConditionObservationDto;
import es.gobcan.istac.edatos.dataset.repository.dto.DatasetRepositoryDto;

public class DsRepositoryMockUtils {

    public static DatasetRepositoryDto mockDatasetRepository(String datasetId, String... dimensionIds) {
        DatasetRepositoryDto datasetRepo = new DatasetRepositoryDto();
        datasetRepo.setDatasetId(datasetId);
        datasetRepo.setDimensions(Arrays.asList(dimensionIds));
        return datasetRepo;
    }
    
    public static ConditionObservationDto mockCodeDimensions(String dimensionId, String... codes) {
        ConditionObservationDto condition = new ConditionObservationDto();
        for (String code : codes) {
            condition.getCodesDimension().add(new CodeDimensionDto(dimensionId, code));
        }
        return condition;
    }
}
