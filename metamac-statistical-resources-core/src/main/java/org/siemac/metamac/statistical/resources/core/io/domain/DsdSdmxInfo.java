package org.siemac.metamac.statistical.resources.core.io.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalGroupKeyDescriptorReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.exception.MetamacException;

import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;

public class DsdSdmxInfo {

    private final LinkedHashMap<String, AttributeInfo> attributes;
    private final LinkedHashMap<String, GroupInfo>     groups;
    private final LinkedHashMap<String, ComponentInfo> dimensions;

    public LinkedHashMap<String, AttributeInfo> getAttributes() {
        return attributes;
    }

    public LinkedHashMap<String, GroupInfo> getGroups() {
        return groups;
    }

    public LinkedHashMap<String, ComponentInfo> getDimensions() {
        return dimensions;
    }

    public DsdSdmxInfo(DataStructureType dsd) throws MetamacException {
        dimensions = getDimensions(dsd);
        groups = getGroups(dsd, dimensions);
        attributes = getAttributes(dsd, groups, dimensions);
    }

    private LinkedHashMap<String, ComponentInfo> getDimensions(DataStructureType dsd) throws MetamacException {
        LinkedHashMap<String, ComponentInfo> dimensionMap = new LinkedHashMap<String, ComponentInfo>();

        List<Object> dimensions = dsd.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions();
        for (Object sourceDimension : dimensions) {
            if (sourceDimension instanceof DimensionType) {
                dimensionMap.put(((DimensionType) sourceDimension).getId(), new ComponentInfo(((DimensionType) sourceDimension).getId(), ComponentInfoTypeEnum.DIMENSION));
            } else if (sourceDimension instanceof TimeDimensionType) {
                dimensionMap.put(((TimeDimensionType) sourceDimension).getId(), new ComponentInfo(((TimeDimensionType) sourceDimension).getId(), ComponentInfoTypeEnum.TIME_DIMENSION));
            } else if (sourceDimension instanceof MeasureDimensionType) {
                dimensionMap.put(((MeasureDimensionType) sourceDimension).getId(), new ComponentInfo(((MeasureDimensionType) sourceDimension).getId(), ComponentInfoTypeEnum.MEASURE_DIMENSION));
            }
        }

        return dimensionMap;
    }

    private LinkedHashMap<String, GroupInfo> getGroups(DataStructureType dsd, LinkedHashMap<String, ComponentInfo> dimensions) throws MetamacException {
        LinkedHashMap<String, GroupInfo> groupMap = new LinkedHashMap<String, GroupInfo>();
        for (GroupType groupType : dsd.getDataStructureComponents().getGroups()) {
            List<ComponentInfo> groupDimensions = new ArrayList<ComponentInfo>(groupType.getGroupDimensions().size());
            for (GroupDimensionType groupDimension : groupType.getGroupDimensions()) {
                groupDimensions.add(getDimensions().get(groupDimension.getDimensionReference().getRef().getId()));
            }

            GroupInfo groupInfo = new GroupInfo(groupType.getId(), sortDimensionList(groupDimensions));
            groupMap.put(groupType.getId(), groupInfo);
        }

        return groupMap;
    }

    private LinkedHashMap<String, AttributeInfo> getAttributes(DataStructureType dsd, LinkedHashMap<String, GroupInfo> groups, LinkedHashMap<String, ComponentInfo> dimensions) throws MetamacException {
        LinkedHashMap<String, AttributeInfo> attributesMap = new LinkedHashMap<String, AttributeInfo>();
        List<Object> attributeList = dsd.getDataStructureComponents().getAttributeList().getAttributesAndReportingYearStartDaies();

        for (Object sourceAttribute : attributeList) {
            if (sourceAttribute instanceof ReportingYearStartDayType) {

                String attributeId = ((ReportingYearStartDayType) sourceAttribute).getId();
                if (StringUtils.isEmpty(attributeId)) {
                    // From conceptIdentity
                    ((ReportingYearStartDayType) sourceAttribute).getConceptIdentity().getRef().getId(); // Safe code in SRM
                }

                AttributeRelationshipType attributeRelationship = ((ReportingYearStartDayType) sourceAttribute).getAttributeRelationship();
                AttributeInfo attributeInfo = createAttributeInfo(groups, dimensions, attributeId, attributeRelationship);

                attributesMap.put(attributeInfo.getAttributeId(), attributeInfo);
            } else if (sourceAttribute instanceof AttributeType) {

                String attributeId = ((AttributeType) sourceAttribute).getId();
                if (StringUtils.isEmpty(attributeId)) {
                    // From conceptIdentity
                    ((AttributeType) sourceAttribute).getConceptIdentity().getRef().getId(); // Safe code in SRM
                }

                AttributeRelationshipType attributeRelationship = ((AttributeType) sourceAttribute).getAttributeRelationship();
                AttributeInfo attributeInfo = createAttributeInfo(groups, dimensions, attributeId, attributeRelationship);

                attributesMap.put(attributeInfo.getAttributeId(), attributeInfo);
            }
        }

        return attributesMap;
    }

    protected AttributeInfo createAttributeInfo(LinkedHashMap<String, GroupInfo> groups, LinkedHashMap<String, ComponentInfo> dimensions, String attributeId,
            AttributeRelationshipType attributeRelationship) {
        AttributeInfo attributeInfo = null;
        if (attributeRelationship.getNone() != null) {
            attributeInfo = new AttributeInfo(attributeId, AttributeInfo.AttachmentLevel.DATASET);
        } else if (attributeRelationship.getPrimaryMeasure() != null) {
            attributeInfo = new AttributeInfo(attributeId, AttributeInfo.AttachmentLevel.OBSERVATION);
        } else if (attributeRelationship.getGroup() != null) {
            attributeInfo = new AttributeInfo(attributeId, AttributeInfo.AttachmentLevel.GROUP);
            attributeInfo.setGroup(groups.get(attributeRelationship.getGroup().getRef().getId()));
        } else if (attributeRelationship.getDimensions() != null) {
            attributeInfo = new AttributeInfo(attributeId, AttributeInfo.AttachmentLevel.DIMENSION);

            List<ComponentInfo> groupDimensions = new ArrayList<ComponentInfo>(attributeRelationship.getDimensions().size());
            for (LocalDimensionReferenceType dimension : attributeRelationship.getDimensions()) {
                groupDimensions.add(dimensions.get(dimension.getRef().getId()));
            }
            attributeInfo.addAllDimensionInfo(sortDimensionList(groupDimensions));

            for (LocalGroupKeyDescriptorReferenceType group : attributeRelationship.getAttachmentGroups()) {
                attributeInfo.addAttachmentGroup(groups.get(group.getRef().getId()));
            }
        }
        return attributeInfo;
    }

    private List<ComponentInfo> sortDimensionList(List<ComponentInfo> groupDimensions) {
        // Sort groupDimensions
        List<ComponentInfo> groupDimensionsSorted = new ArrayList<ComponentInfo>(groupDimensions.size());
        for (ComponentInfo dimension : dimensions.values()) {
            for (ComponentInfo groupDimension : groupDimensions) {
                if (dimension.getCode().equals(groupDimension.getCode())) {
                    groupDimensionsSorted.add(dimension);
                    break;
                }
            }
        }
        return groupDimensionsSorted;
    }

    public interface DsdSdmxExtractor {

        public DsdSdmxInfo extractDsdInfo(String dsdUrn) throws MetamacException;

    }
}
