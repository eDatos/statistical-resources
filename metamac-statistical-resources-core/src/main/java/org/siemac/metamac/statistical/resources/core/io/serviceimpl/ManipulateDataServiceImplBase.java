package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacObservation2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;
import org.springframework.beans.factory.annotation.Autowired;

import com.arte.statistic.parser.generic.Reader;

import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;
import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

public abstract class ManipulateDataServiceImplBase<S extends Object> implements ManipulateDataService<S> {

    private static final int SPLIT_DATA_FACTOR = 5000;

    @Autowired
    protected MetamacObservation2StatRepoMapper metamacObservation2StatRepoMapper;
    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    protected abstract Reader getReader(S source) throws Exception;
    protected abstract void closeReader() throws Exception;

    @Override
    public void importData(S source, DataStructure dataStructure, String datasetID, String dataSourceID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        List<ObservationExtendedDto> dataDtos = new LinkedList<>();
        ObservationExtendedDto observationExtendedDto = null;
        Reader reader = null;

        try {
            reader = getReader(source);

            boolean processData = true;
            while (processData) {
                for (int i = 0; i < SPLIT_DATA_FACTOR; i++) {
                    observationExtendedDto = metamacObservation2StatRepoMapper.toObservation(reader.next(), dataSourceID);
                    if (observationExtendedDto == null) {
                        // Insert incomplete slice
                        insertDataAndAttributes(datasetID, dataDtos, validateDataVersusDsd);
                        processData = false;
                        break;
                    }
                    dataDtos.add(observationExtendedDto);
                }
                // Insert slice
                if (processData) {
                    insertDataAndAttributes(datasetID, dataDtos, validateDataVersusDsd);
                    dataDtos.clear();
                }
            }
        } finally {
            closeReader();
        }

    }

    private void insertDataAndAttributes(String datasetID, List<ObservationExtendedDto> dataDtos, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {
        // Persist Observations and attributes at level observation.
        if (!dataDtos.isEmpty()) {
            validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetID, dataDtos);
        }
    }
}