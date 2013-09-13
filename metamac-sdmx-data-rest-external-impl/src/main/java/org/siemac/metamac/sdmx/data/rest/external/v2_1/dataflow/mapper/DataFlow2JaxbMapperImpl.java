package org.siemac.metamac.sdmx.data.rest.external.v2_1.dataflow.mapper;

import java.util.ArrayList;
import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataStructureRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataStructureReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataflowsType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.springframework.stereotype.Component;

@Component("dataFlow2JaxbMapper")
public class DataFlow2JaxbMapperImpl implements DataFlow2JaxbMapper {

    private org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory structureObjectFactory = new org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory();

    private org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory    commonObjectFactory    = new org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory();

    @Override
    public DataflowsType dataflowsDo2Jaxb(List<DatasetVersion> sourceList, boolean asStub) throws MetamacException {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }

        DataflowsType dataflowsType = structureObjectFactory.createDataflowsType();

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

        DataflowType dataflowType = structureObjectFactory.createDataflowType();

        // Name: required
        dataflowType.getNames().addAll(internationalStringDoToJaxb(source.getSiemacMetadataStatisticalResource().getTitle()));

        dataflowType.setId(source.getSiemacMetadataStatisticalResource().getCode());
        dataflowType.setAgencyID(source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested());
        dataflowType.setVersion(source.getSiemacMetadataStatisticalResource().getVersionLogic());

        if (!asStub) {
            dataflowType.setUrn(GeneratorUrnUtils.generateSdmxDataflowUrn(new String[]{dataflowType.getAgencyID()}, dataflowType.getId(), dataflowType.getVersion()));

            // Annotations: Not supported

            // Description:
            dataflowType.getDescriptions().addAll(internationalStringDoToJaxb(source.getSiemacMetadataStatisticalResource().getDescription()));

            // Structures
            DataStructureReferenceType dataStructureReferenceType = commonObjectFactory.createDataStructureReferenceType();

            DataStructureRefType dataStructureRefType = commonObjectFactory.createDataStructureRefType();
            String[] urnStructure = UrnUtils.splitUrnStructure(source.getRelatedDsd().getUrn());
            dataStructureRefType.setAgencyID(urnStructure[0]);
            dataStructureRefType.setId(urnStructure[1]);
            dataStructureRefType.setVersion(urnStructure[2]);
            dataStructureReferenceType.setRef(dataStructureRefType);

            dataflowType.setStructure(dataStructureReferenceType);
        }

        return dataflowType;
    }

    public List<TextType> internationalStringDoToJaxb(InternationalString source) {
        List<TextType> target = new ArrayList<TextType>();
        if (source == null) {
            return target;
        }
        for (LocalisedString localisedStringDo : source.getTexts()) {
            // LocalisedStringDo to TextType
            TextType textType = commonObjectFactory.createTextType();
            textType.setLang(localisedStringDo.getLocale());
            textType.setValue(localisedStringDo.getLabel());

            // Add to result
            target.add(textType);
        }
        return target;
    }
}
