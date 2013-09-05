package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo.AttachmentLevel;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo.DsdSdmxExtractor;
import org.siemac.metamac.statistical.resources.core.io.domain.GroupInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.RequestParameter;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;

import com.arte.statistic.dataset.repository.dto.AttributeBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.WriterDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.constants.MappingConstants;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfoTypeEnum;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.Group;
import com.arte.statistic.parser.sdmx.v2_1.domain.Header;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;
import com.arte.statistic.parser.sdmx.v2_1.domain.Observation;
import com.arte.statistic.parser.sdmx.v2_1.domain.PayloadStructure;
import com.arte.statistic.parser.sdmx.v2_1.domain.Serie;
import com.arte.statistic.parser.sdmx.v2_1.domain.StructureReferenceBase;

public class WriterDataCallbackImpl implements WriterDataCallback {

    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade = null;
    private MetamacSdmx2StatRepoMapper       metamac2StatRepoMapper           = null;
    private DsdSdmxExtractor                 dsdSdmxExtractor                 = null;

    // Calculated an cache data
    // --- For MessageContext
    private String                           queryKey                         = null;
    private PagedResult<DatasetVersion>      datasetsToFetch                  = null;
    private String                           sender                           = null;
    // --- For DatasetContext
    private int                              currentDatasetVersionIndex       = 0;
    protected DimensionCodeInfo              dimensionAtObservation           = null;
    protected DimensionCodeInfo              timeDimension                    = null;
    protected DimensionCodeInfo              measureDimension                 = null;
    private List<ConditionObservationDto>    coverage                         = null;
    private List<DimensionCodeInfo>          conditions                       = null; // Input conditions for query
    private Map<String, List<String>>        conditionsMap                    = null; // Input conditions for query transformed into a map
    private RequestParameter                 requestParameter                 = null;
    private DsdSdmxInfo                      dsdSdmxInfo                      = null;

    public WriterDataCallbackImpl(DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper, DsdSdmxExtractor dsdSdmxExtractor,
            PagedResult<DatasetVersion> datasetsToFetch, String querykey, RequestParameter requestParameter, String sender) throws Exception {
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.queryKey = querykey;
        this.datasetsToFetch = datasetsToFetch;
        this.requestParameter = requestParameter;
        this.sender = sender;
        this.dsdSdmxExtractor = dsdSdmxExtractor;

        calculateCacheInfo();
    }

    @Override
    public Header retrieveHeader() throws Exception {
        // TODO HEADER
        Header header = new Header();
        header.setId(generateMessageId());
        header.setTest(false);
        header.setPrepared(CoreCommonUtil.jodaDateTime2xsDateTime(new DateTime()));
        header.setSenderID(this.sender);

        // Structure: Note, in this implementation, we use only one structure in header.
        PayloadStructure payloadStructure = new PayloadStructure();
        payloadStructure.setDimensionAtObservation(getDimensionAtObservation().getCode());
        payloadStructure.setStructureID(datasetsToFetch.getValues().get(currentDatasetVersionIndex).getDatasetRepositoryId()); // TODO procesar más de un dataset por mensaje

        String[] splitItemScheme = UrnUtils
                .splitUrnWithoutPrefixItemScheme(datasetsToFetch.getValues().get(currentDatasetVersionIndex).getSiemacMetadataStatisticalResource().getMaintainer().getUrn());

        StructureReferenceBase structureReferenceBase = new StructureReferenceBase();
        structureReferenceBase.setAgency(splitItemScheme[0]);
        structureReferenceBase.setCode(splitItemScheme[1]);
        structureReferenceBase.setVersionLogic(splitItemScheme[2]);

        payloadStructure.setStructure(structureReferenceBase);

        header.addStructure(payloadStructure);

        return header;
    }

    @Override
    public Set<IdValuePair> retrieveAttributesAtDatasetLevel() throws Exception {
        Set<IdValuePair> attributes = new HashSet<IdValuePair>();

        List<AttributeDto> attributesFound = datasetRepositoriesServiceFacade.findAttributesWithDatasetAttachmentLevel(datasetsToFetch.getValues().get(currentDatasetVersionIndex)
                .getDatasetRepositoryId(), null);

        for (AttributeDto attributeDto : attributesFound) {
            attributes.add(new IdValuePair(attributeDto.getAttributeId(), attributeDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE)));
        }

        return attributes;
    }

    @Override
    public Serie fecthSerie(List<DimensionCodeInfo> serieConditions) throws Exception {
        Serie serie = new Serie();

        // The last element in list is the key of dimension at observation level, the other elements have only one key
        int i = 0;
        Iterator<DimensionCodeInfo> itSerieCondition = serieConditions.iterator();
        while (i < serieConditions.size() - 1) {
            DimensionCodeInfo keyCondition = itSerieCondition.next();
            serie.getSeriesKey().add(new IdValuePair(keyCondition.getCode(), keyCondition.getCodes().iterator().next()));
            i++;
        }
        DimensionCodeInfo currentDimensionCodeAtObservation = itSerieCondition.next();

        Map<String, ObservationExtendedDto> observationsMap = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(datasetsToFetch.getValues().get(currentDatasetVersionIndex)
                .getDatasetRepositoryId(), metamac2StatRepoMapper.conditionsToRepositoryList(serieConditions)); // TODO procesar más de un dataset por mensaje

        for (Map.Entry<String, ObservationExtendedDto> entry : observationsMap.entrySet()) {
            serie.getObs().add(observationRepositoryToGroupedObservationWriter(entry.getValue(), currentDimensionCodeAtObservation));
        }

        return serie;
    }

    @Override
    public List<Group> fetchAttributesInGroups() throws Exception {

        List<Group> groups = new LinkedList<Group>();
        Map<String, List<AttributeBasicDto>> transformedAttributesMap = new HashMap<String, List<AttributeBasicDto>>();

        // For all attributes that can be on the message group element
        Iterator<AttributeInfo> it = dsdSdmxInfo.getAttributes().values().iterator();
        while (it.hasNext()) {
            AttributeInfo attributeInfo = it.next();
            if (AttachmentLevel.DIMENSION.equals(attributeInfo.getAttachmentLevel()) || AttachmentLevel.GROUP.equals(attributeInfo.getAttachmentLevel())) {
                // TODO: SAN hizo un método para obtener los attr desnormalizados, luego puedo cambiar este código.
                // para obtenerlos así y luego construir la key.

                // Find all instances of attributes with ID, attributeId.
                List<AttributeDto> attributes = datasetRepositoriesServiceFacade.findAttributesWithDimensionAttachmentLevel(datasetsToFetch.getValues().get(currentDatasetVersionIndex)
                        .getDatasetRepositoryId(), attributeInfo.getAttributeId(), this.conditionsMap);
                // Transform all fetched attributes in map indexed by key and add to the transformed attributes map
                transformedAttributesMap = ManipulateDataUtils.addTransformAttributesToCurrentTransformedAttributes(transformedAttributesMap, getQueryConditions(), attributes);
            }
        }

        // For all groups definition: generate keys and add attributes
        for (GroupInfo groupInfo : dsdSdmxInfo.getGroups().values()) {
            buildGroups(groups, groupInfo, transformedAttributesMap, new LinkedList<CodeDimensionDto>(), 0);
        }

        // TODO hay que poner los atrbutos en los grupos que sean, el q diga en groupp o uno de los attachment

        return groups;
    }

    @Override
    public List<DimensionCodeInfo> getQueryConditions() throws Exception {
        return conditions;
    }

    @Override
    public DimensionCodeInfo getDimensionAtObservation() throws Exception {
        return dimensionAtObservation;
    }

    @Override
    public DimensionCodeInfo getMeasureDimension() throws Exception {
        return measureDimension;
    }

    @Override
    public DimensionCodeInfo getTimeDimension() throws Exception {
        return timeDimension;
    }

    private List<ConditionObservationDto> retrieveCoverage() throws ApplicationException {
        if (coverage == null) {
            coverage = datasetRepositoriesServiceFacade.findCodeDimensions(datasetsToFetch.getValues().get(currentDatasetVersionIndex).getDatasetRepositoryId());
        }
        return coverage;
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo() throws Exception {
        DatasetRepositoryDto datasetRepository = datasetRepositoriesServiceFacade.retrieveDatasetRepository(datasetsToFetch.getValues().get(currentDatasetVersionIndex).getDatasetRepositoryId());

        // Dsd extractor
        dsdSdmxInfo = dsdSdmxExtractor.extractDsdInfo(datasetsToFetch.getValues().get(currentDatasetVersionIndex).getRelatedDsd().getUrn());

        // Calculate conditions
        this.conditions = calculateConditionsFromQuery(datasetRepository);
        this.conditionsMap = metamac2StatRepoMapper.conditionsToRepositoryMap(this.conditions);

        // Calculate DimensionAtObservation
        this.dimensionAtObservation = calculateDimensionAtObservation();

        // Calculate MeasureDimension
        this.measureDimension = calculateMeasureDimension();

        // Calculate TimeDimension
        this.timeDimension = calculateTimeDimension();
    }

    private DimensionCodeInfo calculateMeasureDimension() throws Exception {
        for (DimensionCodeInfo dimensionCodeInfo : getQueryConditions()) {
            if (ComponentInfoTypeEnum.MEASURE_DIMENSION.equals(dimensionCodeInfo.getTypeComponentInfo())) {
                return dimensionCodeInfo;
            }
        }
        return null;
    }

    private DimensionCodeInfo calculateTimeDimension() throws Exception {
        for (DimensionCodeInfo dimensionCodeInfo : getQueryConditions()) {
            if (ComponentInfoTypeEnum.TIME_DIMENSION.equals(dimensionCodeInfo.getTypeComponentInfo())) {
                return dimensionCodeInfo;
            }
        }
        return null;
    }

    private DimensionCodeInfo calculateDimensionAtObservation() throws Exception {
        if (StringUtils.isNotBlank(requestParameter.getDimensionAtObservation())) {
            for (DimensionCodeInfo dimensionCodeInfo : getQueryConditions()) {
                if (dimensionCodeInfo.getCode().equals(requestParameter.getDimensionAtObservation())) {
                    return dimensionCodeInfo;
                }
            }
            throw new Exception("Impossible to determinate the dimension at observation level");
        } else {
            DimensionCodeInfo timeDimension = getTimeDimension();
            if (timeDimension != null) {
                return timeDimension;
            } else {
                List<DimensionCodeInfo> queryCconditions = getQueryConditions();
                int maxValue = -1;
                DimensionCodeInfo currentDimensionAtObservation = null;
                for (DimensionCodeInfo dimensionCodeInfo : queryCconditions) {
                    if (dimensionCodeInfo.getCodes().size() > 0 && dimensionCodeInfo.getCodes().size() > maxValue) {
                        maxValue = dimensionCodeInfo.getCodes().size();
                        currentDimensionAtObservation = dimensionCodeInfo;
                    }
                }
                return currentDimensionAtObservation;
            }
        }
    }

    private List<DimensionCodeInfo> calculateConditionsFromQuery(DatasetRepositoryDto datasetRepository) throws ApplicationException {
        // The key are formed by dimension codes splitted by dots
        // Wildcarding is supported by omitting the dimension code for the dimension.
        // The OR operator is supported using the '+' character.
        // Examples: Normal key -> D.USD.EUR.SP00.A
        // Examples: Wildcard key -> D..EUR.SP00.A
        // Examples: Wildcard key -> D.USD+CHF.EUR.SP00.A
        List<DimensionCodeInfo> conditions = new LinkedList<DimensionCodeInfo>();
        if (StringUtils.isEmpty(this.queryKey)) {
            // If is a empty query, then all observations are needed
            for (int i = 0; i < datasetRepository.getDimensions().size(); i++) {
                addAllCodesConditionForDimension(datasetRepository, i, conditions);
            }
        } else {
            // Split the key by dots
            String str = this.queryKey.replaceAll("\\.", ". ");
            String[] split = str.split("\\.");
            for (int i = 0; i < split.length; i++) {
                split[i] = (StringUtils.isBlank(split[i]) ? null : split[i].trim());

                if (StringUtils.isEmpty(split[i])) {
                    // If is a wildcard dimension, all dimension codes are needed
                    addAllCodesConditionForDimension(datasetRepository, i, conditions);
                } else {
                    DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(datasetRepository.getDimensions().get(i), dsdSdmxInfo.getDimensions().get(datasetRepository.getDimensions().get(i))
                            .getTypeComponentInfo());
                    // Split the code by '+' to find OR operators for codes
                    String[] codes = split[i].split("\\+");
                    for (int j = 0; j < codes.length; j++) {
                        dimensionCodeInfo.addCode(codes[j]);
                    }

                    conditions.add(dimensionCodeInfo);
                }
            }
        }
        return conditions;
    }

    /**
     * Build all instances of group for the current query conditions
     * 
     * @param instanceGroups
     * @param groupInfo
     * @param transformedAttributesMap
     * @param observationkeys
     * @param numDimProcess
     */
    private void buildGroups(List<Group> instanceGroups, GroupInfo groupInfo, Map<String, List<AttributeBasicDto>> transformedAttributesMap, List<CodeDimensionDto> observationkeys, int numDimProcess) {

        // If the key of current group is fully generated
        if (numDimProcess == groupInfo.getDimensionsInfoList().size()) {
            String key = ManipulateDataUtils.generateKeyFromCodesDimensions(observationkeys);
            if (transformedAttributesMap.containsKey(key)) {
                // new instace of group
                Group group = new Group();
                // TODO hay que poner los atrbutos en los grupos que sean, el q diga en groupp o uno de los attachment
                // Keys
                for (CodeDimensionDto codeDimensionDto : observationkeys) {
                    group.addGroupKey(new IdValuePair(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId()));
                }
                // Attributes
                for (AttributeBasicDto attributeBasicDto : transformedAttributesMap.get(key)) {
                    group.addAttribute(new IdValuePair(attributeBasicDto.getAttributeId(), attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE)));
                }

                instanceGroups.add(group);
                // TODO EXCLUSION SET
            }
        } else {
            // Next dimension to process code
            ComponentInfo dimensionInfo = groupInfo.getDimensionsInfoList().get(numDimProcess);
            List<String> actualConditionsForCurrentDimension = conditionsMap.get(dimensionInfo.getCode());

            for (String code : actualConditionsForCurrentDimension) {
                // Auxiliary
                CodeDimensionDto dimensionCodeInfoAux = new CodeDimensionDto(dimensionInfo.getCode(), code);

                List<CodeDimensionDto> newObservationConditions = new ArrayList<CodeDimensionDto>(observationkeys.size() + 1);
                newObservationConditions.addAll(observationkeys);
                newObservationConditions.add(dimensionCodeInfoAux);

                buildGroups(instanceGroups, groupInfo, transformedAttributesMap, newObservationConditions, numDimProcess + 1);
            }
        }
    }

    private void addAllCodesConditionForDimension(DatasetRepositoryDto datasetRepository, int dimensionOrder, List<DimensionCodeInfo> conditions) throws ApplicationException {

        String dimensionID = datasetRepository.getDimensions().get(dimensionOrder);

        ConditionObservationDto conditionObservationDto = retrieveCoverage().get(dimensionOrder);

        DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(dimensionID, dsdSdmxInfo.getDimensions().get(dimensionID).getTypeComponentInfo());

        for (CodeDimensionDto codeDimensionDto : conditionObservationDto.getCodesDimension()) {
            dimensionCodeInfo.addCode(codeDimensionDto.getCodeDimensionId());
        }
        conditions.add(dimensionCodeInfo);
    }

    private Observation observationRepositoryToGroupedObservationWriter(ObservationExtendedDto observation, DimensionCodeInfo currentDimensionCodeAtObservation) throws Exception {
        Observation result = new Observation();

        // Key
        for (CodeDimensionDto codeDimensionDto : observation.getCodesDimension()) {
            if (getDimensionAtObservation().getCode().equals(codeDimensionDto.getDimensionId())) {
                result.getObservationKey().add(new IdValuePair(codeDimensionDto.getDimensionId(), codeDimensionDto.getCodeDimensionId()));
                break;
            }
        }
        // Observation Value
        result.setObservationValue(new IdValuePair(MappingConstants.OBS_VALUE, observation.getPrimaryMeasure()));

        // Attribute
        for (AttributeBasicDto attributeBasicDto : observation.getAttributes()) {
            if (!ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {
                result.addAttribute(new IdValuePair(attributeBasicDto.getAttributeId(), attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE)));
            }
        }

        // TODO Añadir otros attr que en este mensaje van a nivel de observation List<AttributeBasicDto> attributes = observation.getAttributes();
        return result;
    }

    private String generateMessageId() {
        StringBuilder stringBuilder = new StringBuilder("DATA_");
        stringBuilder.append(this.sender).append("_").append(UUID.randomUUID());
        return stringBuilder.toString();
    }

}
