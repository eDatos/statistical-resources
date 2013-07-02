package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;

import com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoEnumType;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainerDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.HeaderDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.PayloadStructureDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.StructureReferenceBaseDto;

public class ManipulateSdmx21DataCallbackImpl implements ManipulateDataCallback {

    private DataStructure dataStructure = null;

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    @Override
    public void startDatasetCreation(DataContainerDto dataContainerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finalizeDatasetCreation(DataContainerDto dataContainerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertDataAndAttributes(DataContainerDto dataContainerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<ComponentInfo> retrieveDimensionsInfo() {
        List<ComponentInfo> dimensionsInfo = new ArrayList<ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getDimensionList() != null) {
            for (Object sourceDim : dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (sourceDim instanceof DimensionType) {
                    dimensionsInfo.add(new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoEnumType.DIMENSION));
                } else if (sourceDim instanceof TimeDimensionType) {
                    dimensionsInfo.add(new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoEnumType.TIME_DIMENSION));
                } else if (sourceDim instanceof MeasureDimensionType) {
                    dimensionsInfo.add(new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoEnumType.MEASURE_DIMENSION));
                }
            }
        }

        return dimensionsInfo;
    }

    @Override
    public List<ComponentInfo> retrieveAttributesInfo() {
        List<ComponentInfo> attributesInfo = new ArrayList<ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getAttributeList() != null) {
            for (Object sourceAttribute : dataStructure.getDataStructureComponents().getAttributeList().getAttributesAndReportingYearStartDaies()) {
                if (sourceAttribute instanceof ReportingYearStartDayType) {
                    attributesInfo.add(new ComponentInfo(((ReportingYearStartDayType) sourceAttribute).getId(), ComponentInfoEnumType.REPORTING_YEAR_START_DAY));
                } else if (sourceAttribute instanceof AttributeType) {
                    attributesInfo.add(new ComponentInfo(((AttributeType) sourceAttribute).getId(), ComponentInfoEnumType.DATA_ATTRIBUTE));
                }
            }
        }

        return attributesInfo;
    }

    /*
     * In metamac only are valid the datasets that references to the "dsdUrn" data structure definition.
     * (non-Javadoc)
     * @see com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback#isValidDataset(com.arte.statistic.parser.sdmx.v2_1.domain.HeaderDto, java.lang.String)
     */
    @Override
    public boolean isValidDataset(HeaderDto messageHeaderDto, String currentDatasetRef) {
        // TODO revisar si quieren esta constraint en metamac cuando se le pregunte a Alberto o cualquier otra
        for (PayloadStructureDto payloadStructureDto : messageHeaderDto.getStructure()) {
            if (currentDatasetRef.equals(payloadStructureDto.getStructureID())) {
                StructureReferenceBaseDto structureReference = payloadStructureDto.getStructure();
                // Check
                if (StringUtils.isEmpty(payloadStructureDto.getStructure().getUrn())) {
                    String[] ref = UrnUtils.splitUrnStructure(this.dataStructure.getUrn());
                    return (ref[0].equals(structureReference.getAgency()) && ref[1].equals(structureReference.getCode()) && ref[2].equals(structureReference.getVersionLogic()));
                } else {
                    return payloadStructureDto.getStructure().getUrn().equals(this.dataStructure.getUrn());
                }
            }
        }

        return false;
    }

}
