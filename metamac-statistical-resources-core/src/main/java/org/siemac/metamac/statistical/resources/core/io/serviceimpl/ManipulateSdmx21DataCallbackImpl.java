package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;

import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.Header;

public class ManipulateSdmx21DataCallbackImpl implements ManipulateDataCallback {

    private MetamacSdmx2StatRepoMapper       metamac2StatRepoMapper           = null;
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade = null;

    // DATASET: Calculated and cache data
    private DatasetRepositoryDto             datasetRepositoryDto             = null;
    private String                           datasetVersionId                 = null;
    private String                           dataSourceID                     = null;

    // Validator
    private ValidateDataVersusDsd            validateDataVersusDsd            = null;

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure, SrmRestInternalService srmRestInternalService, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper,
            DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, String datasetID, ValidateDataVersusDsd validateDataVersusDsd) throws MetamacException {
        this.validateDataVersusDsd = validateDataVersusDsd;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.datasetVersionId = datasetID;
    }

    @Override
    public void startDatasetCreation(DataContainer dataContainer) throws Exception {

        DatasetRepositoryDto datasetRepositoryDto = null;

        // If is a update Dataset
        if (StringUtils.isNotEmpty(this.datasetVersionId)) {
            datasetRepositoryDto = datasetRepositoriesServiceFacade.findDatasetRepository(this.datasetVersionId);
        }

        this.datasetRepositoryDto = datasetRepositoryDto;
    }

    @Override
    public void finalizeDatasetCreation(DataContainer dataContainer) throws Exception {
    }

    @Override
    public void insertDataAndAttributes(DataContainer dataContainer) throws Exception {
        List<ObservationExtendedDto> dataDtos = new LinkedList<ObservationExtendedDto>();

        // Transform Data y Attributes (series or observation level) into repository model
        metamac2StatRepoMapper.populateDatas(dataContainer, this.validateDataVersusDsd.getAttributesProcessorMap(), dataDtos, this.dataSourceID);

        // Persist Observations and observation level attributes
        if (!dataDtos.isEmpty()) {
            this.validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetRepositoryDto.getDatasetId(), dataDtos);
        }
    }

    @Override
    public List<ComponentInfo> retrieveDimensionsInfo() throws Exception {
        return this.validateDataVersusDsd.retrieveDimensionsInfo();
    }

    @Override
    public List<ComponentInfo> retrieveAttributesInfo() throws Exception {
        return this.validateDataVersusDsd.retrieveAttributesInfo();
    }

    public void setDataSourceID(String dataSourceID) {
        this.dataSourceID = dataSourceID;
    }

    /*
     * In metamac only are valid the datasets that references to the "dsdUrn" data structure definition.
     * (non-Javadoc)
     * @see com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback#isValidDataset(com.arte.statistic.parser.sdmx.v2_1.domain.HeaderDto, java.lang.String)
     */
    @Override
    public boolean isValidDataset(Header messageHeader, String currentDatasetRef) {
        // In metamac, the DSD specified in the header are ignored . A required DSD parameter is valid for the entire message.
        return true;
    }

}
