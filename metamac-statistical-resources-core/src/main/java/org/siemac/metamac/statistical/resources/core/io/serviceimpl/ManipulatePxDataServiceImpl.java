package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.io.FileUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacPx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacPx2StatRepoMapperImpl;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators.ValidateDataVersusDsd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.parser.px.PxParser;
import com.arte.statistic.parser.px.PxParser.PxDataByDimensionsIterator;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceObservationDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;
import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Component(ManipulatePxDataService.BEAN_ID)
public class ManipulatePxDataServiceImpl implements ManipulatePxDataService {

    @Autowired
    private MetamacPx2StatRepoMapper         metamacPx2StatRepoMapper;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    private static final String              PX_METADATA_CHARSET = PxAttributeCodes.CHARSET + "=";
    private static int                       SPLIT_DATA_FACTOR   = 5000;

    @Override
    public PxModel importPx(ServiceContext ctx, File pxFile, DataStructure dataStructure, String datasetID, String dataSourceID, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        // Parse PX
        PxModel pxModel = readPxModel(pxFile);
        PxDataByDimensionsIterator pxDataByDimensionsIterator = readPxData(pxFile, pxModel); // Data iterator

        // Prepare attribute at observation level
        Map<String, AttributeInstanceObservationDto> attributesObservations = null;
        if (validateDataVersusDsd.getAttributeIdsAtObservationLevelSet().contains(MetamacPx2StatRepoMapperImpl.ATTR_OBS_NOTE)) {
            List<ComponentInfo> dimensionsInfo = validateDataVersusDsd.retrieveDimensionsInfo();
            Map<String, Integer> dimensionsOrderPxMap = metamacPx2StatRepoMapper.generateDimensionsOrderPxMap(pxModel);
            attributesObservations = metamacPx2StatRepoMapper.toAttributesObservations(pxModel, StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE, dimensionsInfo, dimensionsOrderPxMap);
        }

        List<ObservationExtendedDto> dataDtos = new LinkedList<ObservationExtendedDto>();
        ObservationExtendedDto observationExtendedDto = null;

        boolean processData = true;
        while (processData) {
            PxObservation pxObservation = pxDataByDimensionsIterator.next();
            if (pxObservation == null) {
                // Insert incomplete slice
                insertDataAndAttributes(datasetID, dataDtos, validateDataVersusDsd);
                processData = false;
                break;
            }

            observationExtendedDto = metamacPx2StatRepoMapper.toObservation(pxObservation, dataSourceID, attributesObservations);
            if (observationExtendedDto != null) {
                dataDtos.add(observationExtendedDto);
            }

            // Insert slice
            if (dataDtos.size() == SPLIT_DATA_FACTOR) {
                insertDataAndAttributes(datasetID, dataDtos, validateDataVersusDsd);
                dataDtos.clear();
            }
        }

        return pxModel;
    }

    private void insertDataAndAttributes(String datasetID, List<ObservationExtendedDto> dataDtos, ValidateDataVersusDsd validateDataVersusDsd) throws Exception {

        // Persist Observations and observation level attributes
        if (!dataDtos.isEmpty()) {
            validateDataVersusDsd.checkObservation(dataDtos);
            datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetID, dataDtos);
        }

        // Other attributes: In Metamac the attributes that not are in observation level are not imported.
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
            PxParser pxParser = new PxParser(false, false);
            return pxParser.toPxModel(inputStream, charsetName);
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
            PxParser pxParser = new PxParser(false, false);
            PxDataByDimensionsIterator pxDataByDimensionsIterator = pxParser.toPxDataByDimensionsIterator(inputStream, charsetName, pxModel.getStub(), pxModel.getHeading());
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
