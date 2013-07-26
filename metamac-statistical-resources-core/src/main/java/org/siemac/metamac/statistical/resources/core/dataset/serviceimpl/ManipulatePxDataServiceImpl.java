package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.io.FileUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.MetamacPx2StatRepoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.px.PxParser;
import com.arte.statistic.parser.px.PxParser.PxDataByDimensionsIterator;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxModel;

@Component(ManipulatePxDataService.BEAN_ID)
public class ManipulatePxDataServiceImpl implements ManipulatePxDataService {

    @Autowired
    private MetamacPx2StatRepoMapper         metamacPx2StatRepoMapper;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    private static final String              PX_METADATA_CHARSET = PxAttributeCodes.CHARSET + "=";

    @Override
    public void importPx(ServiceContext ctx, File pxFile, String datasetID) throws MetamacException {

        // Hay un problema y es que hay que hacer dos llamadas al parser de PX con IS, lo que
        // signfica que tenemos que abrir dos veces el Is, lo que significa ahora mismoq ue el JOB envia IS,
        // luego hhabría que salvarlo a disco (otra vez, ya lo hizo la planificación del JIOB). Por tanto esto
        // es doble trabajo lo que signfica que es un desperdicio. Entonces una buena cosa sería cambiar el
        // FileDescriptor para que almacene FILE en vez de IS, sin embargo los servicios que le llegan al plannify usan este
        // mismo objeto y por tanto cambiarían FIle por IS. Ver que solución tomar

        // Parse px
        PxModel pxModel = readPxModel(pxFile);
        PxDataByDimensionsIterator pxDataByDimensionsIterator = readPxData(pxFile, pxModel);

        ObservationExtendedDto observationExtendedDto = null;
        do {
            observationExtendedDto = metamacPx2StatRepoMapper.toObservation(pxDataByDimensionsIterator);
            if (observationExtendedDto != null) {
                System.out.println(observationExtendedDto.getUniqueKey());
            }
        } while (observationExtendedDto != null);
    }

    private DatasetRepositoryDto createDatasetRepository(String datasetID) throws Exception {

        DatasetRepositoryDto datasetRepositoryDto = datasetRepositoriesServiceFacade.findDatasetRepository(datasetID);

        if (datasetRepositoryDto == null) {
            // Create DatasetRepository
            datasetRepositoryDto = new DatasetRepositoryDto();
            datasetRepositoryDto.setDatasetId(datasetID);

            // Dimensions
            // for (ComponentInfo componentInfo : retrieveDimensionsInfo()) {
            // datasetRepositoryDto.getDimensions().add(componentInfo.getCode());
            // }

            // Max Attributes in Observation Level
            // datasetRepositoryDto.setMaxAttributesObservation(this.attributeIdsAtObservationLevelSet.size() + 1); // +1 by Extra Attribute with information about data source

            // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
            // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
            datasetRepositoryDto.setLanguages(Arrays.asList(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE));

            datasetRepositoryDto = datasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
        }

        return datasetRepositoryDto;
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
