package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.AttributeInfo.AttachmentLevel;
import org.siemac.metamac.statistical.resources.core.io.domain.DatasetInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo.DsdSdmxExtractor;
import org.siemac.metamac.statistical.resources.core.io.domain.GroupInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.RequestParameter;
import org.siemac.metamac.statistical.resources.core.io.mapper.MetamacSdmx2StatRepoMapper;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;

import com.arte.statistic.dataset.repository.dto.AttributeInstanceBasicDto;
import com.arte.statistic.dataset.repository.dto.AttributeInstanceDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;
import com.arte.statistic.parser.sdmx.v2_1.WriterDataCallback;
import com.arte.statistic.parser.sdmx.v2_1.constants.MappingConstants;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
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
    private String                           sender                           = null;
    private RequestParameter                 requestParameter                 = null;

    // --- For DatasetContext
    private List<DatasetInfo>                datasetInfoCache                 = null;
    private int                              currentDatasetVersionIndex       = -1;

    public WriterDataCallbackImpl(DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade, MetamacSdmx2StatRepoMapper metamac2StatRepoMapper, DsdSdmxExtractor dsdSdmxExtractor,
            PagedResult<DatasetVersion> datasetsToFetch, String querykey, RequestParameter requestParameter, String sender) throws Exception {
        this.datasetRepositoriesServiceFacade = datasetRepositoriesServiceFacade;
        this.metamac2StatRepoMapper = metamac2StatRepoMapper;
        this.queryKey = querykey;
        this.requestParameter = requestParameter;
        this.sender = sender;
        this.dsdSdmxExtractor = dsdSdmxExtractor;

        calculateCacheInfo(datasetsToFetch);
    }

    @Override
    public Header retrieveHeader() throws Exception {
        Header header = new Header();
        header.setId(generateMessageId());
        header.setTest(false);
        header.setPrepared(CoreCommonUtil.jodaDateTime2xsDateTime(new DateTime()));
        header.setSenderID(this.sender);

        // Structures
        for (DatasetInfo datasetInfo : getDatasetInfoCache()) {
            PayloadStructure payloadStructure = new PayloadStructure();
            payloadStructure.setDimensionAtObservation(datasetInfo.getDimensionAtObservation().getCode());
            payloadStructure.setStructureID(datasetInfo.getDatasetVersion().getDatasetRepositoryId());

            String[] splitItemScheme = UrnUtils.splitUrnWithoutPrefixItemScheme(datasetInfo.getDatasetVersion().getSiemacMetadataStatisticalResource().getMaintainer().getUrn());

            StructureReferenceBase structureReferenceBase = new StructureReferenceBase();
            structureReferenceBase.setAgency(splitItemScheme[0]);
            structureReferenceBase.setCode(splitItemScheme[1]);
            structureReferenceBase.setVersionLogic(splitItemScheme[2]);

            payloadStructure.setStructure(structureReferenceBase);

            header.addStructure(payloadStructure);
        }

        return header;
    }

    @Override
    public Set<IdValuePair> retrieveAttributesAtDatasetLevel() throws Exception {
        Set<IdValuePair> attributes = new HashSet<IdValuePair>();

        List<AttributeInstanceDto> attributesFound = datasetRepositoriesServiceFacade.findAttributesInstancesWithDatasetAttachmentLevel(getCurrentDatasetVersion().getDatasetRepositoryId(), null);

        for (AttributeInstanceDto attributeDto : attributesFound) {
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

        Map<String, ObservationExtendedDto> observationsMap = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(getCurrentDatasetInfo().getDatasetVersion()
                .getDatasetRepositoryId(), metamac2StatRepoMapper.conditionsToRepositoryList(serieConditions)); // TODO procesar más de un dataset por mensaje

        for (Map.Entry<String, ObservationExtendedDto> entry : observationsMap.entrySet()) {
            serie.getObs().add(observationRepositoryToGroupedObservationWriter(entry.getValue(), currentDimensionCodeAtObservation));
        }

        return serie;
    }

    @Override
    public List<Group> fetchAttributesInGroups() throws Exception {

        List<Group> groups = new LinkedList<Group>();
        Map<String, List<AttributeInstanceBasicDto>> transformedAttributesMap = new HashMap<String, List<AttributeInstanceBasicDto>>();

        // For all attributes that can be on the message group element
        Iterator<AttributeInfo> it = getCurrentDatasetInfo().getDsdSdmxInfo().getAttributes().values().iterator();
        while (it.hasNext()) {
            AttributeInfo attributeInfo = it.next();
            if (AttachmentLevel.DIMENSION.equals(attributeInfo.getAttachmentLevel()) || AttachmentLevel.GROUP.equals(attributeInfo.getAttachmentLevel())) {
                // TODO: SAN hizo un método para obtener los attr desnormalizados, luego puedo cambiar este código.
                // para obtenerlos así y luego construir la key.

                // Find all instances of attributes with ID, attributeId.
                List<AttributeInstanceDto> attributes = datasetRepositoriesServiceFacade.findAttributesInstancesWithDimensionAttachmentLevel(getCurrentDatasetVersion().getDatasetRepositoryId(),
                        attributeInfo.getAttributeId(), getCurrentDatasetInfo().getConditionsMap());
                // Transform all fetched attributes in map indexed by key and add to the transformed attributes map
                transformedAttributesMap = ManipulateDataUtils.addTransformAttributesToCurrentTransformedAttributes(transformedAttributesMap, getQueryConditions(), attributes);
            }
        }

        // For all groups definition: generate keys and add attributes
        for (GroupInfo groupInfo : getCurrentDatasetInfo().getDsdSdmxInfo().getGroups().values()) {
            buildGroups(groups, groupInfo, transformedAttributesMap, new LinkedList<CodeDimensionDto>(), 0);
        }

        // TODO hay que poner los atrbutos en los grupos que sean, el q diga en groupp o uno de los attachment

        return groups;
    }

    @Override
    public List<DimensionCodeInfo> getQueryConditions() throws Exception {
        return getCurrentDatasetInfo().getConditions();
    }

    @Override
    public DimensionCodeInfo getDimensionAtObservation() throws Exception {
        return getCurrentDatasetInfo().getDimensionAtObservation();
    }

    @Override
    public DimensionCodeInfo getMeasureDimension() throws Exception {
        return getCurrentDatasetInfo().getMeasureDimension();
    }

    @Override
    public DimensionCodeInfo getTimeDimension() throws Exception {
        return getCurrentDatasetInfo().getTimeDimension();
    }

    @Override
    public boolean hasNextDataset() {
        if (currentDatasetVersionIndex >= datasetInfoCache.size() - 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String nextDataset() {
        if (currentDatasetVersionIndex >= datasetInfoCache.size() - 1) {
            throw new NoSuchElementException();
        }
        currentDatasetVersionIndex++;

        return getCurrentDatasetVersion().getDatasetRepositoryId();
    }

    /**************************************************************************
     * PROCESSORS
     **************************************************************************/
    private void calculateCacheInfo(PagedResult<DatasetVersion> datasetsToFetch) throws Exception {

        // Build Data Structure cache
        Map<String, DsdSdmxInfo> dataStructureDefinitionCache = new HashMap<String, DsdSdmxInfo>();
        for (DatasetVersion datasetVersion : datasetsToFetch.getValues()) {
            if (!dataStructureDefinitionCache.containsKey(datasetVersion.getRelatedDsd().getUrn())) {
                dataStructureDefinitionCache.put(datasetVersion.getRelatedDsd().getUrn(), dsdSdmxExtractor.extractDsdInfo(datasetVersion.getRelatedDsd().getUrn()));
            }
        }

        // Build DatasetInfo cache
        List<DatasetInfo> datasetInfoCache = new ArrayList<DatasetInfo>(datasetsToFetch.getValues().size());
        for (DatasetVersion datasetVersion : datasetsToFetch.getValues()) {
            datasetInfoCache.add(new DatasetInfo(datasetRepositoriesServiceFacade, metamac2StatRepoMapper, queryKey, datasetVersion, dataStructureDefinitionCache.get(datasetVersion.getRelatedDsd()
                    .getUrn()), requestParameter));
        }

        this.datasetInfoCache = datasetInfoCache;
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
    private void buildGroups(List<Group> instanceGroups, GroupInfo groupInfo, Map<String, List<AttributeInstanceBasicDto>> transformedAttributesMap, List<CodeDimensionDto> observationkeys,
            int numDimProcess) {

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
                for (AttributeInstanceBasicDto attributeBasicDto : transformedAttributesMap.get(key)) {
                    group.addAttribute(new IdValuePair(attributeBasicDto.getAttributeId(), attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE)));
                }

                instanceGroups.add(group);
                // TODO EXCLUSION SET
            }
        } else {
            // Next dimension to process code
            ComponentInfo dimensionInfo = groupInfo.getDimensionsInfoList().get(numDimProcess);
            List<String> actualConditionsForCurrentDimension = getCurrentDatasetInfo().getConditionsMap().get(dimensionInfo.getCode());

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
        for (AttributeInstanceBasicDto attributeBasicDto : observation.getAttributes()) {
            if (!ManipulateDataUtils.DATA_SOURCE_ID.equals(attributeBasicDto.getAttributeId())) {
                result.addAttribute(new IdValuePair(attributeBasicDto.getAttributeId(), attributeBasicDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE)));
            }
        }

        // TODO Añadir otros attr que en este mensaje van a nivel de observation List<AttributeInstanceBasicDto> attributes = observation.getAttributes();
        return result;
    }

    private String generateMessageId() {
        StringBuilder stringBuilder = new StringBuilder("DATA_");
        stringBuilder.append(this.sender).append("_").append(UUID.randomUUID());
        return stringBuilder.toString();
    }

    private DatasetInfo getCurrentDatasetInfo() {
        return datasetInfoCache.get(currentDatasetVersionIndex);
    }

    private DatasetVersion getCurrentDatasetVersion() {
        return datasetInfoCache.get(currentDatasetVersionIndex).getDatasetVersion();
    }

    private List<DatasetInfo> getDatasetInfoCache() {
        return datasetInfoCache;
    }

}
