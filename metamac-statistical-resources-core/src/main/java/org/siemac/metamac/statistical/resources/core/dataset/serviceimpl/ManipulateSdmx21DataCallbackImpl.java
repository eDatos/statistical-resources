package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ConceptType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.GroupType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponent;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.Metamac2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.Header;

public class ManipulateSdmx21DataCallbackImpl implements ManipulateDataCallback {

    private DataStructure                          dataStructure                           = null;
    private Metamac2StatRepoMapper                 metamac2StatRepoMapper                  = null;
    private DatasetRepositoriesServiceFacade       datasetRepositoriesServiceFacade        = null;
    private SrmRestInternalService                 srmRestInternalService                  = null;

    // Calculated and cache data
    private Map<String, ComponentInfo>             dimensionsInfoMap                       = null;
    private Map<String, ComponentInfo>             attributesInfoMap                       = null;
    private MultiMap                               enumerationRepresentationsMultimap      = null;                 // Key: URN, Value: Set<String>
    private Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap                  = null;
    private Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap                  = null;
    private Set<String>                            dimensionsCodeSet                       = null;
    private Set<String>                            attributesCodeSet                       = null;
    private Set<String>                            attributeIdsAtObservationLevelSet       = null;
    private List<String>                           mandatoryAttributeIdsAtObservationLevel = null;
    private Map<String, List<ComponentInfo>>       groupDimensionMapInfo                   = null;

    // Cache
    private Set<String>                            keyAttributesAdded                      = new HashSet<String>();
    private DatasetRepositoryDto                   datasetRepositoryDto                    = null;
    private String                                 repoDatasetID                           = null;

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure, SrmRestInternalService srmRestInternalService, Metamac2StatRepoMapper metamac2StatRepoMapper,
            DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, String datasetID) {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.repoDatasetID = datasetID;

        calculateCacheInfo();
    }

    public ManipulateSdmx21DataCallbackImpl(DataStructure dataStructure, SrmRestInternalService srmRestInternalService, Metamac2StatRepoMapper metamac2StatRepoMapper,
            DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade) {
        this.srmRestInternalService = srmRestInternalService;
        this.dataStructure = dataStructure;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;

        calculateCacheInfo();
    }

    @Override
    public void startDatasetCreation(DataContainer dataContainer) {

        DatasetRepositoryDto datasetRepositoryDto = null;

        // If is a update Dataset
        try {
            // Find previous dataset
            if (StringUtils.isNotEmpty(this.repoDatasetID)) {
                datasetRepositoryDto = datasetRepositoriesServiceFacade.findDatasetRepository(this.repoDatasetID);
            }
        } catch (ApplicationException e) {
            // TODO: Lanzar excepcion, mark failed?
        }

        // If is a new Dataset
        if (datasetRepositoryDto == null) {
            createDatasetRepository();
        }
    }

    protected void createDatasetRepository() {
        // Create DatasetRepository
        DatasetRepositoryDto datasetRepositoryDto = new DatasetRepositoryDto();
        datasetRepositoryDto.setDatasetId(this.repoDatasetID);

        // Dimensions
        for (ComponentInfo componentInfo : retrieveDimensionsInfo()) {
            datasetRepositoryDto.getDimensions().add(componentInfo.getCode());
        }

        // Max Attributes in Observation Level
        datasetRepositoryDto.setMaxAttributesObservation(this.attributeIdsAtObservationLevelSet.size());

        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
        datasetRepositoryDto.setLanguages(Arrays.asList(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE));

        try {
            this.datasetRepositoryDto = datasetRepositoriesServiceFacade.createDatasetRepository(datasetRepositoryDto);
        } catch (ApplicationException e) {
            // TODO Lanzar excepcion, mark failed?
            e.printStackTrace();
        }
    }

    @Override
    public void finalizeDatasetCreation(DataContainer dataContainer) {
        // TODO pendiente de la gestión de errores, de alberto
    }

    @Override
    public void insertDataAndAttributes(DataContainer dataContainer) {

        List<ObservationExtendedDto> dataDtos = new ArrayList<ObservationExtendedDto>();
        List<AttributeDto> attributeDtos = new ArrayList<AttributeDto>();

        try {
            // Transform Data y Attributes (series or observation level) into repository model
            metamac2StatRepoMapper.populateDatas(dataContainer, this.attributesProcessorMap, dataDtos, attributeDtos);

            // Transform Attributes (group level) into repository model
            metamac2StatRepoMapper.processGroupAttribute(dataContainer.getGroups(), attributeDtos);

            // Transform Attributes (dataset level) into repository model
            metamac2StatRepoMapper.processDatasetAttribute(dataContainer.getAttributes(), attributeDtos);

            // Process attributes: The attributes can appears flat on the XML. So you have to group them. According to the DSD definition.
            // Note: The observation level attributes need not be flattened.
            List<AttributeDto> compactedAttributes = new ArrayList<AttributeDto>();
            for (AttributeDto attributeDto : attributeDtos) {

                if (this.attributesProcessorMap.containsKey(attributeDto.getAttributeId())) {
                    // TODO validate attribute
                    String attributeCustomKey = metamac2StatRepoMapper.generateAttributeKeyInAttachmentLevel(attributeDto, this.attributesProcessorMap.get(attributeDto.getAttributeId())
                            .getAttributeRelationship(), retrieveDimensionsInfo(), this.groupDimensionMapInfo);

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
                checkObservation(dataDtos);
                datasetRepositoriesServiceFacade.createOrUpdateObservationsExtended(datasetRepositoryDto.getDatasetId(), dataDtos); // TODO para perfomance si sabesmos que no hay colision usar el
                                                                                                                                    // create sin update
            }

            if (!attributeDtos.isEmpty()) {
                checkAttributes(attributeDtos);
                datasetRepositoriesServiceFacade.createAttributes(datasetRepositoryDto.getDatasetId(), attributeDtos);
            }

        } catch (MetamacException e) {
            // TODO Lanzar excepcion, mark failed?
            e.printStackTrace();
        } catch (ApplicationException e) {
            // TODO Lanzar excepcion, mark failed?
            e.printStackTrace();
        }
    }

    @Override
    public List<ComponentInfo> retrieveDimensionsInfo() {
        return new ArrayList<ComponentInfo>(this.dimensionsInfoMap.values());
    }

    @Override
    public List<ComponentInfo> retrieveAttributesInfo() {
        return new ArrayList<ComponentInfo>(this.attributesInfoMap.values());
    }

    // private Map<String, ComponentInfo> getDimensionsInfoMap() {
    // if (this.dimensionsInfoMap == null) {
    // calculateDimensionsInfo();
    // }
    // return dimensionsInfoMap;
    // }

    // private Map<String, List<ComponentInfo>> getGroupDimensionMapInfo() {
    // if (this.groupDimensionMapInfo == null) {
    // calculateGroupDimensionMapInfo();
    // }
    // return groupDimensionMapInfo;
    // }

    // private Set<String> getDimensionsCodeSet() {
    // return getDimensionsInfoMap().keySet();
    // }

    // private Map<String, Object> getAttributesMap() {
    // if (this.attributesMap == null) {
    // calculateAttributesInfo();
    // }
    // return this.attributesMap;
    // }
    //
    // private Map<String, ComponentInfo> getAttributesInfoMap() {
    // if (this.attributesInfoMap == null) {
    // calculateAttributesInfo();
    // }
    // return attributesInfoMap;
    // }

    // private List<String> getMandatoryAttributeIdsAtObservationLevel() {
    // if (this.mandatoryAttributeIdsAtObservationLevel == null) {
    // calculateAttributesInfo();
    // }
    // return this.mandatoryAttributeIdsAtObservationLevel;
    // }
    //
    // private Set<String> getAttributeIdsAtObservationLevelSet() {
    // if (this.attributeIdsAtObservationLevelSet == null) {
    // calculateAttributesInfo();
    // }
    // return this.attributeIdsAtObservationLevelSet;
    // }

    // private Set<String> getAttributesCodeSet() {
    // return getAttributesInfoMap().keySet();
    // }

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

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo() {
        MultiMap enumerationRepresentationsMultimap = MultiValueMap.decorate(new HashMap<String, Set<String>>(), HashSet.class);

        calculateCacheDimensionInfo(enumerationRepresentationsMultimap);
        calculatedCacheAttributeInfo(enumerationRepresentationsMultimap);
        calculateCacheGroupInfo();

        this.enumerationRepresentationsMultimap = enumerationRepresentationsMultimap;

    }

    protected void calculateCacheGroupInfo() {
        // Groups
        Map<String, List<ComponentInfo>> groupDimensionMapInfo = new HashMap<String, List<ComponentInfo>>();

        if (dataStructure.getDataStructureComponents() != null) {
            for (GroupType sourceGroupType : dataStructure.getDataStructureComponents().getGroups()) {
                // Group Dimensions
                List<ComponentInfo> groupDimensions = new LinkedList<ComponentInfo>();
                for (GroupDimensionType groupDimensionType : sourceGroupType.getGroupDimensions()) {
                    groupDimensions.add(this.dimensionsInfoMap.get(groupDimensionType.getDimensionReference().getRef().getId()));
                }
                groupDimensionMapInfo.put(sourceGroupType.getId(), groupDimensions);
            }
        }

        this.groupDimensionMapInfo = groupDimensionMapInfo;
    }

    protected void calculatedCacheAttributeInfo(MultiMap enumerationRepresentationsMultimap) {
        // Attributes
        Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap = new HashMap<String, DsdProcessor.DsdAttribute>();
        Map<String, ComponentInfo> attributesInfoMap = new HashMap<String, ComponentInfo>();
        Set<String> attributeIdsAtObservationLevelSet = new HashSet<String>();
        List<String> mandatoryAttributeIdsAtObservationLevel = new LinkedList<String>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getAttributeList() != null) {
            for (Object sourceAttribute : dataStructure.getDataStructureComponents().getAttributeList().getAttributesAndReportingYearStartDaies()) {
                DsdAttribute dsdAttribute = null;
                if (sourceAttribute instanceof ReportingYearStartDayType) {
                    dsdAttribute = new DsdAttribute((ReportingYearStartDayType) sourceAttribute);
                    // Processor
                    attributesProcessorMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), dsdAttribute);
                    // ComponentInfo
                    attributesInfoMap.put(((ReportingYearStartDayType) sourceAttribute).getId(), new ComponentInfo(((ReportingYearStartDayType) sourceAttribute).getId(),
                            ComponentInfoTypeEnum.REPORTING_YEAR_START_DAY));
                } else if (sourceAttribute instanceof Attribute) {
                    dsdAttribute = new DsdAttribute((Attribute) sourceAttribute);
                    // Processor
                    attributesProcessorMap.put(((Attribute) sourceAttribute).getId(), new DsdAttribute((Attribute) sourceAttribute));
                    // Representation
                    cacheEnumerateRepresentation(dsdAttribute, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    attributesInfoMap.put(((Attribute) sourceAttribute).getId(), new ComponentInfo(((Attribute) sourceAttribute).getId(), ComponentInfoTypeEnum.DATA_ATTRIBUTE));
                }
                if (dsdAttribute != null) {
                    if (dsdAttribute.isAttributeAtObservationLevel()) {
                        attributeIdsAtObservationLevelSet.add(dsdAttribute.getComponentId());
                    }
                    if (dsdAttribute.isMandatory()) {
                        mandatoryAttributeIdsAtObservationLevel.add(dsdAttribute.getComponentId());
                    }
                }
            }
        }

        this.attributesProcessorMap = attributesProcessorMap;
        this.attributesInfoMap = attributesInfoMap;
        this.attributeIdsAtObservationLevelSet = attributeIdsAtObservationLevelSet;
        this.mandatoryAttributeIdsAtObservationLevel = mandatoryAttributeIdsAtObservationLevel;
        this.attributesCodeSet = attributesInfoMap.keySet();
    }

    protected MultiMap calculateCacheDimensionInfo(MultiMap enumerationRepresentationsMultimap) {
        // Dimensions
        Map<String, DsdProcessor.DsdDimension> dimensionsProcessorMap = new HashMap<String, DsdProcessor.DsdDimension>();
        Map<String, ComponentInfo> dimensionsInfoMap = new HashMap<String, ComponentInfo>();

        if (dataStructure.getDataStructureComponents() != null && dataStructure.getDataStructureComponents().getDimensionList() != null) {
            for (Object sourceDim : dataStructure.getDataStructureComponents().getDimensionList().getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (sourceDim instanceof Dimension) {
                    DsdDimension dsdDimension = new DsdDimension((Dimension) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((Dimension) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    dimensionsInfoMap.put(((Dimension) sourceDim).getId(), new ComponentInfo(((Dimension) sourceDim).getId(), ComponentInfoTypeEnum.DIMENSION));
                } else if (sourceDim instanceof TimeDimensionType) {
                    // Processor
                    dimensionsProcessorMap.put(((TimeDimensionType) sourceDim).getId(), new DsdDimension((TimeDimensionType) sourceDim));
                    // ComponentInfo
                    dimensionsInfoMap.put(((TimeDimensionType) sourceDim).getId(), new ComponentInfo(((TimeDimensionType) sourceDim).getId(), ComponentInfoTypeEnum.TIME_DIMENSION));
                } else if (sourceDim instanceof MeasureDimensionType) {
                    DsdDimension dsdDimension = new DsdDimension((MeasureDimensionType) sourceDim);
                    // Processor
                    dimensionsProcessorMap.put(((MeasureDimensionType) sourceDim).getId(), dsdDimension);
                    // Representation
                    cacheEnumerateRepresentation(dsdDimension, enumerationRepresentationsMultimap);
                    // ComponentInfo
                    dimensionsInfoMap.put(((MeasureDimensionType) sourceDim).getId(), new ComponentInfo(((MeasureDimensionType) sourceDim).getId(), ComponentInfoTypeEnum.MEASURE_DIMENSION));
                }
            }
        }

        this.dimensionsProcessorMap = dimensionsProcessorMap;
        this.dimensionsInfoMap = dimensionsInfoMap;
        this.dimensionsCodeSet = dimensionsInfoMap.keySet();

        return enumerationRepresentationsMultimap;
    }

    private void cacheEnumerateRepresentation(DsdComponent dsdComponent, MultiMap enumerationRepresentationsMultimap) {

        {
            // Codelist: If is not currently cached
            String codelistRepresentationUrn = dsdComponent.getCodelistRepresentationUrn();
            if (enumerationRepresentationsMultimap.containsKey(codelistRepresentationUrn)) {
                Codelist codelist = srmRestInternalService.retrieveCodelistByUrn(codelistRepresentationUrn);

                for (CodeType codeType : codelist.getCodes()) {
                    enumerationRepresentationsMultimap.put(codelistRepresentationUrn, codeType.getId());
                }
            }
        }

        {
            // ConceptScheme:
            String conceptSchemeRepresentationUrn = dsdComponent.getConceptSchemeRepresentationUrn();
            if (enumerationRepresentationsMultimap.containsKey(conceptSchemeRepresentationUrn)) {
                ConceptScheme conceptScheme = srmRestInternalService.retrieveConceptSchemeByUrn(conceptSchemeRepresentationUrn);

                for (ConceptType conceptType : conceptScheme.getConcepts()) {
                    enumerationRepresentationsMultimap.put(conceptSchemeRepresentationUrn, conceptType.getId());
                }
            }
        }
    }

    /**************************************************************************
     * VALIDATORS
     **************************************************************************/

    private void checkObservation(List<ObservationExtendedDto> dataDtos) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();
        for (ObservationExtendedDto overExtendedDto : dataDtos) {

            // Number of dimensions
            if (retrieveDimensionsInfo().size() != overExtendedDto.getCodesDimension().size()) {
                // TODO El número de dimensiones del DSD es distinto al de la observacion
            }

            // The used dimension if correct
            for (CodeDimensionDto codeDimensionDto : overExtendedDto.getCodesDimension()) {
                if (!this.dimensionsCodeSet.contains(codeDimensionDto.getDimensionId())) {
                    // TODO la dimension usada no existe
                }
            }

            // Number of attributes at observation level
            if (overExtendedDto.getAttributes().size() <= this.attributeIdsAtObservationLevelSet.size()) {
                // TODO El numero de atributos a nivel de obseracion difiere del DS
            }

            // The used attribute if correct
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                if (!this.attributeIdsAtObservationLevelSet.contains(attributeBasicDto.getAttributeId())) {
                    // TODO excepción: No existe definido este atributo de nivel de observaicon en el DSD
                }
            }

            // Mandatory attributes at observation level
            Set<String> attributesInCurrentObservation = new HashSet<String>();
            for (AttributeBasicDto attributeBasicDto : overExtendedDto.getAttributes()) {
                attributesInCurrentObservation.add(attributeBasicDto.getAttributeId());
            }
            for (String attributeId : this.mandatoryAttributeIdsAtObservationLevel) {
                if (!attributesInCurrentObservation.contains(attributeId)) {
                    // TODO Excepcion: No existe el atributo X obligatorio a nivel de obseracion
                }
            }

        }

        ExceptionUtils.throwIfException(exceptions);
    }

    private void checkAttributes(List<AttributeDto> attributeDtos) throws MetamacException {
        List<MetamacExceptionItem> exceptions = new LinkedList<MetamacExceptionItem>();

        for (AttributeDto attributeDto : attributeDtos) {

            // The used attribute if correct
            if (!this.attributesCodeSet.contains(attributeDto.getAttributeId()) || this.attributeIdsAtObservationLevelSet.contains(attributeDto.getAttributeId())) {
                // TODO excepcion, el atributo X no está definido en el DSD, o está definido a otro nivel de attachment
            }

        }

        ExceptionUtils.throwIfException(exceptions);
    }
}
