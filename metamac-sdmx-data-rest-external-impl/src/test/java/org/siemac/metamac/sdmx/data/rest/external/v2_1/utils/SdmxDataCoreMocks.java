package org.siemac.metamac.sdmx.data.rest.external.v2_1.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;

public class SdmxDataCoreMocks {

    public static Map<String, ObservationExtendedDto> mockObservations() {

        Map<String, ObservationExtendedDto> observationsMap = new HashMap<String, ObservationExtendedDto>();

        // OBS: 2010-08#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));
            observationExtendedDto.setPrimaryMeasure("1.3413");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.setPrimaryMeasure("110.04");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.setPrimaryMeasure("0.82363");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-08#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-08"));

            observationExtendedDto.setPrimaryMeasure("1.2894");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.setPrimaryMeasure("1.3089");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.setPrimaryMeasure("0.83987");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.setPrimaryMeasure("110.26");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-09#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-09"));

            observationExtendedDto.setPrimaryMeasure("1.3067");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#CHF
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "CHF"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.setPrimaryMeasure("1.3452");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#GBP
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "GBP"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.setPrimaryMeasure("0.87637");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#JPY
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "JPY"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.setPrimaryMeasure("113.67");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }

        // OBS: 2010-10#M#EUR#SP00#E#USD
        {
            ObservationExtendedDto observationExtendedDto = new ObservationExtendedDto();
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("FREQ", "M"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY", "USD"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("CURRENCY_DENOM", "EUR"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_TYPE", "SP00"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("EXR_VAR", "E"));
            observationExtendedDto.getCodesDimension().add(new CodeDimensionDto("TIME_PERIOD", "2010-10"));

            observationExtendedDto.setPrimaryMeasure("1.3898");
            observationsMap.put(observationExtendedDto.getUniqueKey(), observationExtendedDto);
        }
        return observationsMap;
    }

    public static Set<String> mockObservationsKeys() {
        Set<String> set = new HashSet<String>();

        set.add("M#CHF#EUR#SP00#E#2010-08");
        set.add("M#JPY#EUR#SP00#E#2010-08");
        set.add("M#GBP#EUR#SP00#E#2010-08");
        set.add("M#USD#EUR#SP00#E#2010-08");

        set.add("M#CHF#EUR#SP00#E#2010-09");
        set.add("M#GBP#EUR#SP00#E#2010-09");
        set.add("M#JPY#EUR#SP00#E#2010-09");
        set.add("M#USD#EUR#SP00#E#2010-09");

        set.add("M#CHF#EUR#SP00#E#2010-10");
        set.add("M#GBP#EUR#SP00#E#2010-10");
        set.add("M#JPY#EUR#SP00#E#2010-10");
        set.add("M#USD#EUR#SP00#E#2010-10");

        return set;
    }
}