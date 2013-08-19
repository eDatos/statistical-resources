package org.siemac.metamac.statistical.resources.core.io.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.DimensionCodeInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.Group;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;

public interface MetamacSdmx2StatRepoMapper {

    /**
     * Convert Datas and Attributes into statistic-dataset-repository-model
     * 
     * @param dataContainerDto
     * @param attributesMap
     * @param dataDtos
     * @param attributeDtos
     * @param datasourceId
     * @throws MetamacException
     */
    public void populateDatas(DataContainer dataContainerDto, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, List<AttributeDto> attributeDtos,
            String datasourceId) throws MetamacException;

    /**
     * Adds attributes which are grouped into @param second
     * 
     * @param groups
     * @param second
     * @throws MetamacException
     */
    public void processGroupAttribute(List<Group> groups, List<AttributeDto> attributeDtos, Set<String> mandatoryAttributeIdsAtObservationLevel) throws MetamacException;

    /**
     * Convert dataset level attribtues into statistic-dataset-repository-model
     * 
     * @param attributes
     * @return Attributes at dataset level
     * @throws MetamacException
     */
    public void processDatasetAttribute(List<IdValuePair> attributes, List<AttributeDto> attributeDtos) throws MetamacException;

    /**
     * Sets the key of attributes of @param attributeDto according to the DSD
     * 
     * @param ctx
     * @param attributeDto Attribute representation model in repository
     * @param attributeRelationship
     * @return key of attribute
     * @throws MetamacException
     */
    public String generateAttributeKeyInAttachmentLevel(AttributeDto attributeDto, AttributeRelationship attributeRelationship, Map<String, List<ComponentInfo>> groupDimensionMapInfo)
            throws MetamacException;

    /**
     * Convert parser conditions into repository conditions
     * 
     * @param serieConditions
     * @return
     * @throws MetamacException
     */
    public List<ConditionObservationDto> conditionsToRepository(List<DimensionCodeInfo> serieConditions) throws MetamacException;
}
