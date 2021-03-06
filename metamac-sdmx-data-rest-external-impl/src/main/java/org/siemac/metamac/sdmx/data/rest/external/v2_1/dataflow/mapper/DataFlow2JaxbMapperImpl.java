package org.siemac.metamac.sdmx.data.rest.external.v2_1.dataflow.mapper;

import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataStructureRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataStructureReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowsType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapperImpl;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.common.mapper.CommonDo2JaxbMapper;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.service.utils.UriCalculator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dataFlow2JaxbMapper")
public class DataFlow2JaxbMapperImpl extends BaseDo2JaxbMapperImpl implements DataFlow2JaxbMapper {

    private static Logger       logger        = LoggerFactory.getLogger(DataFlow2JaxbMapperImpl.class);

    @Autowired
    private CommonDo2JaxbMapper commonDo2JaxbMapper;

    @Autowired
    private DataConfiguration   dataConfiguration;

    private static final String PATH_DATAFLOW = "dataflow";

    @Override
    public DataflowsType dataflowsDo2Jaxb(List<DatasetVersion> sourceList, boolean asStub) throws MetamacException {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }

        DataflowsType dataflowsType = getStructureObjectFactory().createDataflowsType();

        for (DatasetVersion datasetVersion : sourceList) {
            DataflowType dataflowType = dataflowDo2Jaxb(datasetVersion, asStub);
            if (dataflowType != null) {
                dataflowsType.getDataflows().add(dataflowType);
            }
        }

        return dataflowsType;
    }

    @Override
    public DataflowType dataflowDo2Jaxb(DatasetVersion source, boolean asStub) throws MetamacException {
        if (source == null) {
            return null;
        }

        DataflowType dataflowType = getStructureObjectFactory().createDataflowType();

        // Name: required
        dataflowType.getNames().addAll(commonDo2JaxbMapper.internationalStringDoToJaxb(source.getSiemacMetadataStatisticalResource().getTitle()));

        dataflowType.setId(source.getSiemacMetadataStatisticalResource().getCode());
        dataflowType.setAgencyID(source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested());
        dataflowType.setVersion(source.getSiemacMetadataStatisticalResource().getVersionLogic());

        if (!asStub) {
            dataflowType.setUrn(GeneratorUrnUtils.generateSdmxDataflowUrn(new String[]{dataflowType.getAgencyID()}, dataflowType.getId(), dataflowType.getVersion()));

            // Annotations: Not supported

            // Description:
            dataflowType.getDescriptions().addAll(commonDo2JaxbMapper.internationalStringDoToJaxb(source.getSiemacMetadataStatisticalResource().getDescription()));

            // Structures
            DataStructureReferenceType dataStructureReferenceType = getCommonObjectFactory().createDataStructureReferenceType();

            DataStructureRefType dataStructureRefType = getCommonObjectFactory().createDataStructureRefType();
            String[] urnStructure = UrnUtils.splitUrnStructure(source.getRelatedDsd().getUrn());
            dataStructureRefType.setAgencyID(urnStructure[0]);
            dataStructureRefType.setId(urnStructure[1]);
            dataStructureRefType.setVersion(urnStructure[2]);
            dataStructureReferenceType.setRef(dataStructureRefType);

            // Uri
            try {
                dataflowType.setUri(UriCalculator.calculateUriForDataset(source, RestUtils.createLink(dataConfiguration.retrieveSdmxRegistryApiUrlBase(), RestExternalConstants.API_VERSION_2_1),
                        PATH_DATAFLOW));
            } catch (MetamacException e) {
                logger.error("Impossible to calculate URI", e);
            }

            dataflowType.setStructure(dataStructureReferenceType);
        }

        return dataflowType;
    }

}
