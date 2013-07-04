package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.Header;
import com.arte.statistic.parser.sdmx.v2_1.domain.PayloadStructure;
import com.arte.statistic.parser.sdmx.v2_1.domain.StructureReferenceBase;

public class ManipulateSdmx21DataCallbackImpl implements ManipulateDataCallback {

    private DataStructure                    dataStructure                    = null;
    private Metamac2StatRepoMapper           metamac2StatRepoMapper           = null;
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade = null;

    // Calculated data
    private Map<String, ComponentInfo>       dimensionsInfoMap                = null;
    private Map<String, ComponentInfo>       attributesInfoMap                = null;
    private Map<String, Object>              attributesMap                    = null;
    private Map<String, List<ComponentInfo>> groupDimensionMapInfo            = null;

    // Cache
    private Set<String>                      keyAttributesAdded               = new HashSet<String>();
    private DatasetRepositoryDto             datasetRepositoryDto             = null;

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure, Metamac2StatRepoMapper metamac2StatRepoMapper, DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade) {
        this.dataStructure = dataStructure;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
    }

    @Override
    public void startDatasetCreation(DataContainer dataContainer) {

        // Create DatasetRepository
        DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();
        datasetRepositoryDto.setDatasetId(UUID.randomUUID().toString());
        for (ComponentInfo componentInfo : retrieveDimensionsInfo()) {
            datasetRepositoryDto.getDimensions().add(componentInfo.getCode());
        }

    }

    @Override
    public void finalizeDatasetCreation(DataContainer dataContainer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertDataAndAttributes(DataContainer dataContainer) {

        List<ObservationExtendedDto> dataDtos = new ArrayList<ObservationExtendedDto>();
        List<AttributeDto> attributeDtos = new ArrayList<AttributeDto>();

        try {
            // Transform Data y Attributes (series or observation level) into repository model
            metamac2StatRepoMapper.populateDatas(dataContainer, getAttributesMap(), dataDtos, attributeDtos);

            // Transform Attributes (group level) into repository model
            metamac2StatRepoMapper.processGroupAttribute(dataContainer.getGroups(), attributeDtos);

            // Transform Attributes (dataset level) into repository model
            metamac2StatRepoMapper.processDatasetAttribute(dataContainer.getAttributes(), attributeDtos);

            // Process attributes: The attributes can appears flat on the XML. So you have to group them. According to the DSD definition.
            // Note: The observation level attributes need not be flattened.
            List<AttributeDto> compactedAttributes = new ArrayList<AttributeDto>();
            for (AttributeDto attributeDto : attributeDtos) {

                if (getAttributesMap().containsKey(attributeDto.getAttributeId())) {
                    // TODO validate attribute
                    String attributeCustomKey = metamac2StatRepoMapper.generateAttributeKeyInAttachmentLevel(attributeDto,
                            calculateAttributeRelationshipType(getAttributesMap().get(attributeDto.getAttributeId())), retrieveDimensionsInfo(), getGroupDimensionMapInfo());

                    // If attribute is not processed
                    if (!this.keyAttributesAdded.contains(attributeCustomKey)) {
                        this.keyAttributesAdded.add(attributeCustomKey);
                        compactedAttributes.add(attributeDto);
                    }
                } else {
                    // TODO Error check
                }
            }

            // Persist Observations and attributes
            if (!dataDtos.isEmpty()) {
                datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetRepositoryDto.getDatasetId(), dataDtos); // TODO para perfomance si sabesmos que no hay colision usar el
                                                                                                                                    // create sin update
            }

            if (!attributeDtos.isEmpty()) {
                datasetRepositoriesServiceFacade.createAttributes(datasetRepositoryDto.getDatasetId(), attributeDtos);
            }

        } catch (MetamacException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public List<ComponentInfo> retrieveDimensionsInfo() {
        return (List<ComponentInfo>) getDimensionsInfoMap().values();
    }

    private Map<String, ComponentInfo> getDimensionsInfoMap() {
        if (dimensionsInfoMap == null) {
            calculateDimensionsInfo();
        }
        return dimensionsInfoMap;
    }

    @Override
    public List<ComponentInfo> retrieveAttributesInfo() {
        return (List<ComponentInfo>) getAttributesInfoMap().values();
    }

    private Map<String, Object> getAttributesMap() {
        if (this.attributesMap == null) {
            calculateAttributesInfo();
        }
        return this.attributesMap;
    }

    private Map<String, ComponentInfo> getAttributesInfoMap() {
        if (attributesInfoMap == null) {
            calculateAttributesInfo();
        }
        return attributesInfoMap;
    }

    private Map<String, List<ComponentInfo>> getGroupDimensionMapInfo() {
        if (groupDimensionMapInfo == null) {
            calculateGroupDimensionMapInfo();
        }
        return groupDimensionMapInfo;
    }

    /*
     * In metamac only are valid the datasets that references to the "dsdUrn" data structure definition.
     * (non-Javadoc)
     * @see com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback#isValidDataset(com.arte.statistic.parser.sdmx.v2_1.domain.HeaderDto, java.lang.String)
     */
    @Override
    public boolean isValidDataset(Header messageHeader, String currentDatasetRef) {
        // TODO revisar si quieren esta constraint en metamac cuando se le pregunte a Alberto o cualquier otra
        for (PayloadStructure payloadStructure : messageHeader.getStructure()) {
            if (currentDatasetRef.equals(payloadStructure.getStructureID())) {
                StructureReferenceBase structureReference = payloadStructure.getStructure();
                // Check
                if (StringUtils.isEmpty(payloadStructure.getStructure().getUrn())) {
                    String[] ref = UrnUtils.splitUrnStructure(this.dataStructure.getUrn());
                    return (ref[0].equals(structureReference.getAgency()) && ref[1].equals(structureReference.getCode()) && ref[2].equals(structureReference.getVersionLogic()));
                } else {
                    return payloadStructure.getStructure().getUrn().equals(this.dataStructure.getUrn());
                }
            }
        }

        return false;
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/

    private void calculateDimensionsInfo() {
        Map<String, ComponentInfo> dimensionsInfoMap = new HashMap<String, ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getDimensionList() != null) {
            for (Object sourceDim : dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (sourceDim instanceof DimensionType) {
                    dimensionsInfoMap.put(((DimensionType) sourceDim).getId(), new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoTypeEnum.DIMENSION));
                } else if (sourceDim instanceof TimeDimensionType) {
                    dimensionsInfoMap.put(((TimeDimensionType) sourceDim).getId(), new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoTypeEnum.TIME_DIMENSION));
                } else if (sourceDim instanceof MeasureDimensionType) {
                    dimensionsInfoMap.put(((MeasureDimensionType) sourceDim).getId(), new ComponentInfo(((DimensionType) sourceDim).getId(), ComponentInfoTypeEnum.MEASURE_DIMENSION));
                }
            }
        }

        // Save calculate data
        this.dimensionsInfoMap = dimensionsInfoMap;
    }

    public void calculateAttributesInfo() {
        Map<String, ComponentInfo> attributesInfoMap = new HashMap<String, ComponentInfo>();
        Map<String, Object> attributesMap = new HashMap<String, Object>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getAttributeList() != null) {
            for (Object sourceAttribute : dataStructure.getDataStructureComponents().getAttributeList().getAttributesAndReportingYearStartDaies()) {
                if (sourceAttribute instanceof ReportingYearStartDayType) {
                    attributesInfoMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), new ComponentInfo(((ReportingYearStartDayType) sourceAttribute).getId(),
                            ComponentInfoTypeEnum.REPORTING_YEAR_START_DAY));
                    attributesMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), sourceAttribute);
                } else if (sourceAttribute instanceof AttributeType) {
                    attributesInfoMap.put(((AttributeType) sourceAttribute).getId(), new ComponentInfo(((AttributeType) sourceAttribute).getId(), ComponentInfoTypeEnum.DATA_ATTRIBUTE));
                    attributesMap.put(((AttributeType) sourceAttribute).getId(), sourceAttribute);
                }

            }
        }

        // Save calculate data
        this.attributesInfoMap = attributesInfoMap;
        this.attributesMap = attributesMap;
    }

    public void calculateGroupDimensionMapInfo() {
        Map<String, List<ComponentInfo>> groupDimensionMapInfo = new HashMap<String, List<ComponentInfo>>();

        if (dataStructure.getDataStructureComponents() != null) {
            for (GroupType sourceGroupType : dataStructure.getDataStructureComponents().getGroups()) {
                // Group Dimensions
                List<ComponentInfo> groupDimensions = new LinkedList<ComponentInfo>();
                for (GroupDimensionType groupDimensionType : sourceGroupType.getGroupDimensions()) {
                    groupDimensions.add(getDimensionsInfoMap().get(groupDimensionType.getDimensionReference().getRef().getId()));
                }
                groupDimensionMapInfo.put(sourceGroupType.getId(), groupDimensions);
            }
        }

        this.groupDimensionMapInfo = groupDimensionMapInfo;
    }

    private AttributeRelationshipType calculateAttributeRelationshipType(Object attribute) {
        if (attribute instanceof ReportingYearStartDayType) {
            return ((ReportingYearStartDayType) attribute).getAttributeRelationship();
        } else if (attribute instanceof AttributeType) {
            return ((AttributeType) attribute).getAttributeRelationship();
        }
        throw new RuntimeException("Attribute object not supported!");
    }
}
