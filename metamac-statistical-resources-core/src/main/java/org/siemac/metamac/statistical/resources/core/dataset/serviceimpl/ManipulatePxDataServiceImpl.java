package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.io.FileUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.MetamacPx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators.ValidateDataVersusDsd;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.px.PxParser;
import com.arte.statistic.parser.px.PxParser.PxDataByDimensionsIterator;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

@Component(ManipulatePxDataService.BEAN_ID)
public class ManipulatePxDataServiceImpl implements ManipulatePxDataService {

    @Autowired
    private MetamacPx2StatRepoMapper         metamacPx2StatRepoMapper;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestInternalService           srmRestInternalService;

    private static final String              PX_METADATA_CHARSET = PxAttributeCodes.CHARSET + "=";
    private static int                       SPLIT_DATA_FACTOR   = 5000;

    @Override
    public void importPx(ServiceContext ctx, File pxFile, DataStructure dataStructure, String datasetID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        // Create or Update DatasetRepository
        DatasetRepositoryDto datasetRepositoryDto = createDatasetRepository(datasetID, validateDataVersusDsd);

        // Parse PX
        PxModel pxModel = readPxModel(pxFile);
        PxDataByDimensionsIterator pxDataByDimensionsIterator = readPxData(pxFile, pxModel);

        List<ObservationExtendedDto> dataDtos = new LinkedList<ObservationExtendedDto>();
        ObservationExtendedDto observationExtendedDto = null;

        boolean processData = true;
        while (processData) {
            for (int i = 0; i < SPLIT_DATA_FACTOR; i++) {
                observationExtendedDto = metamacPx2StatRepoMapper.toObservation(pxDataByDimensionsIterator);
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
    }

    private DatasetRepositoryDto createDatasetRepository(String datasetID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.findDatasetRepository(datasetID);

        if (datasetRepositoryDto == null) {
            // Create DatasetRepository
            datasetRepositoryDto = new DatasetRepositoryDto();
            datasetRepositoryDto.setDatasetId(datasetID);

            // Dimensions
            for (ComponentInfo componentInfo : validateDataVersusDsd.retrieveDimensionsInfo()) {
                datasetRepositoryDto.getDimensions().add(componentInfo.getCode());
            }

            // Max Attributes in Observation Level
            datasetRepositoryDto.setMaxAttributesObservation(validateDataVersusDsd.getAttributeIdsAtObservationLevelSet().size() + 1); // +1 by Extra Attribute with information about data source

            // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
            // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
            datasetRepositoryDto.setLanguages(Arrays.asList(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE));

            datasetRepositoryDto = datasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
        }

        return datasetRepositoryDto;
    }

    private void insertDataAndAttributes(String datasetID, List<ObservationExtendedDto> dataDtos, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        // List<AttributeDto> attributeDtos = new LinkedList<AttributeDto>();

        // Transform Data y Attributes (series or observation level) into repository model
        // this.metamac2StatRepoMapper.populateDatas(dataContainer, validateDataVersusDsd.getAttributesProcessorMap(), dataDtos, attributeDtos, datasetID);

        // Transform Attributes (group level) into repository model
        // metamac2StatRepoMapper.processGroupAttribute(dataContainer.getGroups(), attributeDtos, this.validateDataVersusDsd.getMandatoryAttributeIdsAtObservationLevel());

        // Transform Attributes (dataset level) into repository model
        // metamac2StatRepoMapper.processDatasetAttribute(dataContainer.getAttributes(), attributeDtos);

        // Process attributes: The attributes can appears flat on the XML. So you have to group them. According to the DSD definition.
        // Note: The observation level attributes need not be flattened.
        // List<AttributeDto> compactedAttributes = new ArrayList<AttributeDto>();
        // for (AttributeDto attributeDto : attributeDtos) {
        //
        // if (this.validateDataVersusDsd.getAttributesProcessorMap().containsKey(attributeDto.getAttributeId())) {
        // String attributeCustomKey = metamac2StatRepoMapper.generateAttributeKeyInAttachmentLevel(attributeDto,
        // this.validateDataVersusDsd.getAttributesProcessorMap().get(attributeDto.getAttributeId()).getAttributeRelationship(), this.validateDataVersusDsd.getGroupDimensionMapInfo());
        //
        // // If attribute is not processed
        // if (!this.keyAttributesAdded.contains(attributeCustomKey)) {
        // this.keyAttributesAdded.add(attributeCustomKey);
        // compactedAttributes.add(attributeDto);
        // }
        // } else {
        // // This is a validation error but for performance improvements the validation runs later
        // }
        // }

        // Persist Observations and attributes
        if (!dataDtos.isEmpty()) {
            // TODO sin validación contra DSD hasta que se decida la reunión de atributos
            // validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetID, dataDtos);
        }

        // if (!compactedAttributes.isEmpty()) {
        // this.validateDataVersusDsd.checkAttributes(compactedAttributes);
        // datasetRepositoriesServiceFacade.createAttributes(datasetRepositoryDto.getDatasetId(), compactedAttributes);
        // }
    }

    /**
     * Read a px model
     * 
     * @param pxFile
     * @return
     */
    private PxModel readPxModel(File pxFile) {

        // Parse px
        InputStream inputStream = null;
        try {
            String charsetName = guessPxCharset(pxFile);

            // Transform
            inputStream = new FileInputStream(pxFile);
            return PxParser.toPxModel(inputStream, charsetName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing px file", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * Read a px data
     * 
     * @param pxFile
     * @param pxModel
     * @return
     */
    private PxDataByDimensionsIterator readPxData(File pxFile, PxModel pxModel) {
        // Parse px
        try {
            String charsetName = guessPxCharset(pxFile);

            // Transform
            InputStream inputStream = new FileInputStream(pxFile);
            PxDataByDimensionsIterator pxDataByDimensionsIterator = PxParser.toPxDataByDimensionsIterator(inputStream, charsetName, pxModel.getStub(), pxModel.getHeading());
            return pxDataByDimensionsIterator;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing px file", e);
        }
    }

    /**
     * Guess charset of px file.
     * PX-Axis documentation says:"If the keyword CHARSET is missing it means that all texts are in DOS text format, so that the same files can be used both in the DOS
     * and the Windows version of PC-AXIS. In the Windows version the texts are translated into Windows format when read. When a file is
     * saved in PC-AXIS file format it is always saved in DOS text format in versions prior to 2000.
     * Starting with version 2000 the files can be either in DOS or Windows texts. If they are in Windows texts this information is added: CHARSET="ANSI";".
     * ICU library does not guess this charset, so we must force it
     */
    private String guessPxCharset(File pxFile) throws Exception {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(pxFile);
            byte[] byteData = new byte[500]; // so many characters because charset metadata can not be in first lines
            inputStream.read(byteData);
            String firstCharacters = new String(byteData);
            if (firstCharacters.contains(PX_METADATA_CHARSET)) {
                return FileUtils.guessCharset(pxFile);
            } else {
                return PxParser.CHARSET_MSDOS;
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
