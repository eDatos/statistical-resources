package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;
import com.arte.statistic.parser.sdmx.v2_1.domain.DataContainer;
import com.arte.statistic.parser.sdmx.v2_1.domain.Group;
import com.arte.statistic.parser.sdmx.v2_1.domain.IdValuePair;

public interface Metamac2StatRepoMapper {

    /**
     * Convert Datas and Attributes into statistic-dataset-repository-model
     * 
     * @param dataContainerDto
     * @param attributesMap
     * @param dataDtos
     * @param attributeDtos
     * @throws MetamacException
     */
    public void populateDatas(DataContainer dataContainerDto, Map<String, DsdProcessor.DsdAttribute> attributesProcessorMap, List<ObservationExtendedDto> dataDtos, List<AttributeDto> attributeDtos)
            throws MetamacException;

    /**
     * Adds attributes which are grouped into @param second
     * 
     * @param groups
     * @param second
     * @throws MetamacException
     */
    public void processGroupAttribute(List<Group> groups, List<AttributeDto> attributeDtos) throws MetamacException;

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
     * @param dimensionsInfo
     * @param groupDimensionMapInfo
     * @return key of attribute
     * @throws MetamacException
     */
    public String generateAttributeKeyInAttachmentLevel(AttributeDto attributeDto, AttributeRelationshipType attributeRelationship, List<ComponentInfo> dimensionsInfo,
            Map<String, List<ComponentInfo>> groupDimensionMapInfo) throws MetamacException;
}
