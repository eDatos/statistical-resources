package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo.AttachmentLevel;
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

    private DatasetRepositoriesServiceFacade       datasetRepositoriesServiceFacade = null;
    private MetamacSdmx2StatRepoMapper             metamac2StatRepoMapper           = null;

    // Calculated an cache data
    protected LinkedHashMap<String, AttributeInfo> attributesInfos                  = null;
    protected List<GroupInfo>                      groupInfos                       = null;
    protected DimensionCodeInfo                    dimensionAtObservation           = null;
    protected DimensionCodeInfo                    timeDimension                    = null;
    private List<ConditionObservationDto>          coverage                         = null;
    private String                                 queryKey                         = null;
    private String                                 datasetVersionId                 = null;
    private List<DimensionCodeInfo>                conditions                       = null; // Input conditions for query
    private Map<String, List<String>>              conditionsMap                    = null; // Input conditions for query transformed into a map
    private RequestParameter                       requestParameter                 = null;
    private ExternalItem                           maintainer                       = null;
    private String                                 sender                           = null;

    public WriterDataCallbackImpl(DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper, String datasetID, String querykey,
            RequestParameter requestParameter, ExternalItem maintainer, String sender) throws Exception {
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.queryKey = querykey;
        this.datasetVersionId = datasetID;
        this.requestParameter = requestParameter;
        this.maintainer = maintainer;
        this.sender = sender;
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
        payloadStructure.setStructureID(datasetVersionId);

        String[] splitItemScheme = UrnUtils.splitUrnWithoutPrefixItemScheme(maintainer.getUrn());

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
        // attributes.add(new IdValuePair("code", "value")); // Nothing attribute at dataset level
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

        Map<String, ObservationExtendedDto> observationsMap = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(datasetVersionId,
                metamac2StatRepoMapper.conditionsToRepositoryList(serieConditions));

        for (Map.Entry<String, ObservationExtendedDto> entry : observationsMap.entrySet()) {
            serie.getObs().add(observationRepositoryToGroupedObservationWriter(entry.getValue(), currentDimensionCodeAtObservation));
        }

        return serie;
    }

    public List<Group> fetchAttributesInGroups() throws Exception {

        List<Group> groups = new LinkedList<Group>();
        Map<String, List<AttributeBasicDto>> transformedAttributesMap = new HashMap<String, List<AttributeBasicDto>>();

        // For all attributes that can be on the message group element
        Iterator<AttributeInfo> it = attributesInfos.values().iterator();
        while (it.hasNext()) {
            AttributeInfo attributeInfo = it.next();
            if (AttachmentLevel.DIMENSION.equals(attributeInfo.getAttachmentLevel()) || AttachmentLevel.GROUP.equals(attributeInfo.getAttachmentLevel())) {
                // Find all instances of attributes with ID, attributeId.
                List<AttributeDto> attributes = datasetRepositoriesServiceFacade.findAttributesWithDimensionAttachmentLevel(datasetVersionId, attributeInfo.getAttributeId(), this.conditionsMap);
                // Transform all fetched attributes in map indexed by key and add to the transformed attributes map
                transformedAttributesMap = ManipulateDataUtils.addTransformAttributesToCurrentTransformedAttributes(transformedAttributesMap, getQueryConditions(), attributes);
            }
        }

        // For all groups definition: generate keys and add attributes
        for (GroupInfo groupInfo : this.groupInfos) {
            buildGroups(groups, groupInfo, transformedAttributesMap, new LinkedList<CodeDimensionDto>(), 0);
        }

        return groups;
    }

    private void buildGroups(List<Group> instanceGroups, GroupInfo groupInfo, Map<String, List<AttributeBasicDto>> transformedAttributesMap, List<CodeDimensionDto> observationkeys, int numDimProcess) {

        if (numDimProcess == groupInfo.getDimensionsInfoList().size()) {
            String key = ManipulateDataUtils.generateOrderedKeyFromCodesDimensions(observationkeys);
            if (transformedAttributesMap.containsKey(key)) {
                Group group = new Group();
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
        return null; // TODO complete
    }

    @Override
    public DimensionCodeInfo getTimeDimension() throws Exception {
        return null; // TODO complete
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo() throws Exception {
        DatasetRepositoryDto datasetRepository = datasetRepositoriesServiceFacade.retrieveDatasetRepository(datasetVersionId);

        // Calculate conditions
        this.conditions = calculateConditionsFromQuery(datasetRepository);
        this.conditionsMap = metamac2StatRepoMapper.conditionsToRepositoryMap(this.conditions);

        // Calculate DimensionAtObservation
        this.dimensionAtObservation = calculateDimenstionAtObservation();

        // Cache dsd Attributes
        this.attributesInfos = new LinkedHashMap<String, AttributeInfo>(); // TODO consumir DSD para coger atributos.
    }

    private DimensionCodeInfo calculateDimenstionAtObservation() throws Exception {
        if (StringUtils.isNotBlank(requestParameter.getDimensionAtObservation())) {
            for (DimensionCodeInfo dimensionCodeInfo : conditions) {
                if (dimensionCodeInfo.getCode().equals(requestParameter.getDimensionAtObservation())) {
                    return dimensionCodeInfo;
                }
            }
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

        throw new Exception("Impossible to determinate the dimension at observation level");
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
                    DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(datasetRepository.getDimensions().get(i), ComponentInfoTypeEnum.DIMENSION); // TODO detectar measure dimension y time
                                                                                                                                                            // dimension, añadir columna de tipo
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

    private void addAllCodesConditionForDimension(DatasetRepositoryDto datasetRepository, int dimensionOrder, List<DimensionCodeInfo> conditions) throws ApplicationException {

        String dimensionID = datasetRepository.getDimensions().get(dimensionOrder);

        ConditionObservationDto conditionObservationDto = retrieveCoverage().get(dimensionOrder);

        DimensionCodeInfo dimensionCodeInfo = new DimensionCodeInfo(dimensionID, ComponentInfoTypeEnum.DIMENSION); // TODO detectar measure dimension y time dimension, añadir columna de tipo nullable
                                                                                                                   // en repository
        for (CodeDimensionDto codeDimensionDto : conditionObservationDto.getCodesDimension()) {
            dimensionCodeInfo.addCode(codeDimensionDto.getCodeDimensionId());
        }
        conditions.add(dimensionCodeInfo);
    }

    private List<ConditionObservationDto> retrieveCoverage() throws ApplicationException {
        if (coverage == null) {
            coverage = datasetRepositoriesServiceFacade.findCodeDimensions(datasetVersionId);
        }
        return coverage;
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
        stringBuilder.append(this.sender).append("_").append(this.datasetVersionId);
        return stringBuilder.toString();
    }
}
