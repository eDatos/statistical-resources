package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;

public class DatasetInfo {

    private DatasetVersion                datasetVersion         = null;
    private DimensionCodeInfo             dimensionAtObservation = null;
    private DimensionCodeInfo             timeDimension          = null;
    private DimensionCodeInfo             measureDimension       = null;
    private List<ConditionObservationDto> coverage               = null;
    private List<DimensionCodeInfo>       conditions             = null; // Input conditions for query
    private Map<String, List<String>>     conditionsMap          = null; // Input conditions for query transformed into a map
    private DsdSdmxInfo                   dsdSdmxInfo            = null;

    public DatasetInfo(DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper, String queryKey, DatasetVersion datasetVersion,
            DsdSdmxInfo dsdSdmxInfo, RequestParameter requestParameter) throws Exception {

        this.datasetVersion = datasetVersion;
        this.dsdSdmxInfo = dsdSdmxInfo;

        DatasetRepositoryDto datasetRepository = datasetRepositoriesServiceFacade.retrieveDatasetRepository(datasetVersion.getDatasetRepositoryId());

        // 1- Calculate coverage
        this.coverage = calculateCoverage(datasetRepositoriesServiceFacade, datasetRepository);

        // 2- Calculate conditions
        this.conditions = calculateConditionsFromQuery(datasetRepository, queryKey);

        // 3- Calculate conditions map
        this.conditionsMap = metamac2StatRepoMapper.conditionsToRepositoryMap(this.conditions);

        // 4- Calculate MeasureDimension
        this.measureDimension = calculateMeasureDimension();

        // 5- Calculate TimeDimension
        this.timeDimension = calculateTimeDimension();

        // 6- Calculate DimensionAtObservation
        this.dimensionAtObservation = calculateDimensionAtObservation(requestParameter);
    }

    public DatasetVersion getDatasetVersion() {
        return datasetVersion;
    }

    public List<ConditionObservationDto> getCoverage() {
        return coverage;
    }

    public List<DimensionCodeInfo> getConditions() {
        return conditions;
    }

    public Map<String, List<String>> getConditionsMap() {
        return conditionsMap;
    }

    public DimensionCodeInfo getTimeDimension() {
        return timeDimension;
    }

    public DimensionCodeInfo getMeasureDimension() {
        return measureDimension;
    }

    public DimensionCodeInfo getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    public DsdSdmxInfo getDsdSdmxInfo() {
        return dsdSdmxInfo;
    }

    private List<DimensionCodeInfo> calculateConditionsFromQuery(DatasetRepositoryDto datasetRepository, String queryKey) throws ApplicationException {
        // The key are formed by dimension codes splitted by dots
        // Wildcarding is supported by omitting the dimension code for the dimension.
        // The OR operator is supported using the '+' character.
        // Examples: Normal key -> D.USD.EUR.SP00.A
        // Examples: Wildcard key -> D..EUR.SP00.A
        // Examples: Wildcard key -> D.USD+CHF.EUR.SP00.A
        List<DimensionCodeInfo> conditions = new LinkedList<DimensionCodeInfo>();
        if (StringUtils.isEmpty(queryKey)) {
            // If is a empty query, then all observations are needed
            for (int i = 0; i < datasetRepository.getDimensions().size(); i++) {
                addAllCodesConditionForDimension(datasetRepository, i, conditions);
            }
        } else {
            // Split the key by dots
            String str = queryKey.replaceAll("\\.", ". ");
            String[] split = str.split("\\.");
            for (int i = 0; i < split.length; i++) {
                split[i] = (StringUtils.isBlank(split[i]) ? null : split[i].trim());

                if (StringUtils.isEmpty(split[i])) {
                    // If is a wildcard dimension, all dimension codes are needed
                    addAllCodesConditionForDimension(datasetRepository, i, conditions);
                } else {
                    DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(datasetRepository.getDimensions().get(i), dsdSdmxInfo.getDimensions().get(datasetRepository.getDimensions().get(i))
                            .getTypeComponentInfo());
                    // Split the code by '+' to find OR operators for codes
                    String[] codes = split[i].split("\\+");
                    for (int j = 0; j < codes.length; j++) {
                        dimensionCodeInfo.addCode(codes[j]);
                    }

                    conditions.add(dimensionCodeInfo);
                }
            }
        }
        return conditions;
    }

    private void addAllCodesConditionForDimension(DatasetRepositoryDto datasetRepository, int dimensionOrder, List<DimensionCodeInfo> conditions) throws ApplicationException {

        String dimensionID = datasetRepository.getDimensions().get(dimensionOrder);
        ConditionObservationDto conditionObservationDto = getCoverage().get(dimensionOrder);
        DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(dimensionID, dsdSdmxInfo.getDimensions().get(dimensionID).getTypeComponentInfo());

        for (CodeDimensionDto codeDimensionDto : conditionObservationDto.getCodesDimension()) {
            dimensionCodeInfo.addCode(codeDimensionDto.getCodeDimensionId());
        }
        conditions.add(dimensionCodeInfo);
    }

    private List<ConditionObservationDto> calculateCoverage(DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, DatasetRepositoryDto datasetRepositoryDto) throws ApplicationException {
        return datasetRepositoriesServiceFacade.findCodeDimensions(datasetRepositoryDto.getDatasetId());
    }

    private DimensionCodeInfo calculateMeasureDimension() throws Exception {
        for (DimensionCodeInfo dimensionCodeInfo : getConditions()) {
            if (ComponentInfoTypeEnum.MEASURE_DIMENSION.equals(dimensionCodeInfo.getTypeComponentInfo())) {
                return dimensionCodeInfo;
            }
        }
        return null;
    }

    private DimensionCodeInfo calculateTimeDimension() throws Exception {
        for (DimensionCodeInfo dimensionCodeInfo : getConditions()) {
            if (ComponentInfoTypeEnum.TIME_DIMENSION.equals(dimensionCodeInfo.getTypeComponentInfo())) {
                return dimensionCodeInfo;
            }
        }
        return null;
    }

    private DimensionCodeInfo calculateDimensionAtObservation(RequestParameter requestParameter) throws Exception {
        if (StringUtils.isNotBlank(requestParameter.getDimensionAtObservation())) {
            for (DimensionCodeInfo dimensionCodeInfo : getConditions()) {
                if (dimensionCodeInfo.getCode().equals(requestParameter.getDimensionAtObservation())) {
                    return dimensionCodeInfo;
                }
            }
            throw new Exception("Impossible to determinate the dimension at observation level");
        } else {
            DimensionCodeInfo timeDimension = getTimeDimension();
            if (timeDimension != null) {
                return timeDimension;
            } else {
                List<DimensionCodeInfo> queryConditions = getConditions();
                int maxValue = -1;
                DimensionCodeInfo currentDimensionAtObservation = null;
                for (DimensionCodeInfo dimensionCodeInfo : queryConditions) {
                    if (dimensionCodeInfo.getCodes().size() > 0 && dimensionCodeInfo.getCodes().size() > maxValue) {
                        maxValue = dimensionCodeInfo.getCodes().size();
                        currentDimensionAtObservation = dimensionCodeInfo;
                    }
                }
                return currentDimensionAtObservation;
            }
        }
    }

}
