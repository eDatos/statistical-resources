package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.ValidateDataVersusDsd;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
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
    private Set<String>                      keyAttributesAdded               = new HashSet<String>();
    private DatasetRepositoryDto             datasetRepositoryDto             = null;
    private String                           repoDatasetID                    = null;
    private String                           dataSourceID                     = null;

    // Validator
    private ValidateDataVersusDsd            validateDataVersusDsd            = null;

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure, SrmRestInternalService srmRestInternalService, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper,
            DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, String datasetID, ValidateDataVersusDsd validateDataVersusDsd) throws MetamacException {
        this.validateDataVersusDsd = validateDataVersusDsd;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.repoDatasetID = datasetID;
    }

    @Override
    public void startDatasetCreation(DataContainer dataContainer) throws Exception {

        DatasetRepositoryDto datasetRepositoryDto = null;

        // If is a update Dataset
        if (StringUtils.isNotEmpty(this.repoDatasetID)) {
            datasetRepositoryDto = datasetRepositoriesServiceFacade.findDatasetRepository(this.repoDatasetID);
        }

        // If is a new Dataset
        if (datasetRepositoryDto == null) {
            datasetRepositoryDto = createDatasetRepository();
        }

        this.datasetRepositoryDto = datasetRepositoryDto;
    }

    protected DatasetRepositoryDto createDatasetRepository() throws Exception {
        // Create DatasetRepository
        DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();
        datasetRepositoryDto.setDatasetId(this.repoDatasetID);

        // Dimensions
        for (ComponentInfo componentInfo : retrieveDimensionsInfo()) {
            datasetRepositoryDto.getDimensions().add(componentInfo.getCode());
        }

        // Max Attributes in Observation Level
        datasetRepositoryDto.setMaxAttributesObservation(this.validateDataVersusDsd.getAttributeIdsAtObservationLevelSet().size() + 1); // +1 by Extra Attribute with information about data source

        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        datasetRepositoryDto.setLanguages(Arrays.asList(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE));

        return datasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
    }

    @Override
    public void finalizeDatasetCreation(DataContainer dataContainer) throws Exception {
    }

    @Override
    public void insertDataAndAttributes(DataContainer dataContainer) throws Exception {
        List<ObservationExtendedDto> dataDtos = new LinkedList<ObservationExtendedDto>();
        List<AttributeDto> attributeDtos = new LinkedList<AttributeDto>();

        // Transform Data y Attributes (series or observation level) into repository model
        metamac2StatRepoMapper.populateDatas(dataContainer, this.validateDataVersusDsd.getAttributesProcessorMap(), dataDtos, attributeDtos, this.dataSourceID);

        // Persist Observations and observation level attributes
        if (!dataDtos.isEmpty()) {
            this.validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetRepositoryDto.getDatasetId(), dataDtos);
        }
    }

    /**
     * THIS CODE IS UNUSED because in Metamac the attributes that not are in observation level are not imported.
     * The code is here for demo purposes.
     * 
     * @param dataContainer
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private void _UNUSED_insertDataAndAttributes(DataContainer dataContainer) throws Exception {

        List<ObservationExtendedDto> dataDtos = new LinkedList<ObservationExtendedDto>();
        List<AttributeDto> attributeDtos = new LinkedList<AttributeDto>();

        // Transform Data y Attributes (series or observation level) into repository model
        metamac2StatRepoMapper.populateDatas(dataContainer, this.validateDataVersusDsd.getAttributesProcessorMap(), dataDtos, attributeDtos, this.dataSourceID);

        // Transform Attributes (group level) into repository model
        metamac2StatRepoMapper.processGroupAttribute(dataContainer.getGroups(), attributeDtos, this.validateDataVersusDsd.getMandatoryAttributeIdsAtObservationLevel());

        // Transform Attributes (dataset level) into repository model
        metamac2StatRepoMapper.processDatasetAttribute(dataContainer.getAttributes(), attributeDtos);

        // Process attributes: The attributes can appears flat on the XML. So you have to group them. According to the DSD definition.
        // Note: The observation level attributes need not be flattened.
        List<AttributeDto> compactedAttributes = new ArrayList<AttributeDto>();
        for (AttributeDto attributeDto : attributeDtos) {

            if (this.validateDataVersusDsd.getAttributesProcessorMap().containsKey(attributeDto.getAttributeId())) {
                String attributeCustomKey = metamac2StatRepoMapper.generateAttributeKeyInAttachmentLevel(attributeDto,
                        this.validateDataVersusDsd.getAttributesProcessorMap().get(attributeDto.getAttributeId()).getAttributeRelationship(), this.validateDataVersusDsd.getGroupDimensionMapInfo());

                // If attribute is not processed
                if (!this.keyAttributesAdded.contains(attributeCustomKey)) {
                    this.keyAttributesAdded.add(attributeCustomKey);
                    compactedAttributes.add(attributeDto);
                }
            } else {
                // This is a validation error but for performance improvements the validation runs later
            }
        }

        // Persist Observations and attributes
        if (!dataDtos.isEmpty()) {
            this.validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetRepositoryDto.getDatasetId(), dataDtos);
        }

        if (!compactedAttributes.isEmpty()) {
            this.validateDataVersusDsd.checkAttributes(compactedAttributes);
            datasetRepositoriesServiceFacade.createAttributes(datasetRepositoryDto.getDatasetId(), compactedAttributes);
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
