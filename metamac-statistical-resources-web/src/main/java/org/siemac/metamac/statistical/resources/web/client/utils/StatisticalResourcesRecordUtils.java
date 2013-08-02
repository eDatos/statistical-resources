package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.TemporalCodeRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.VersionRationaleTypeRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.ElementLevelTreeNode;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;

public class StatisticalResourcesRecordUtils extends RecordUtils {

    //
    // DATASETS
    //

    public static DatasetRecord getDatasetRecord(DatasetVersionDto datasetDto) {
        DatasetRecord record = new DatasetRecord();
        record.setId(datasetDto.getId());
        record.setCode(datasetDto.getCode());
        record.setTitle(getLocalisedString(datasetDto.getTitle()));
        record.setDescription(getLocalisedString(datasetDto.getDescription()));
        record.setProcStatus(CommonUtils.getProcStatusName(datasetDto));
        record.setVersionLogic(datasetDto.getVersionLogic());
        record.setUrn(datasetDto.getUrn());
        record.setDatasetDto(datasetDto);
        return record;
    }

    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getCode(), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    //
    // PUBLICATIONS
    //

    public static PublicationRecord getPublicationRecord(PublicationVersionDto publicationDto) {
        PublicationRecord record = new PublicationRecord();
        record.setId(publicationDto.getId());
        record.setCode(publicationDto.getCode());
        record.setTitle(getLocalisedString(publicationDto.getTitle()));
        record.setDescription(getLocalisedString(publicationDto.getDescription()));
        record.setProcStatus(CommonUtils.getProcStatusName(publicationDto));
        record.setVersionLogic(publicationDto.getVersionLogic());
        record.setUrn(publicationDto.getUrn());
        record.setPublicationDto(publicationDto);
        return record;
    }

    public static ElementLevelTreeNode getElementLevelNode(ElementLevelDto elementLevelDto) {
        ElementLevelTreeNode elementLevelNode = new ElementLevelTreeNode();
        NameableStatisticalResourceDto element = elementLevelDto.getChapter() != null ? elementLevelDto.getChapter() : elementLevelDto.getCube();
        elementLevelNode.setID(element.getUrn());
        elementLevelNode.setUrn(element.getUrn());
        elementLevelNode.setTitle(InternationalStringUtils.getLocalisedString(element.getTitle()));
        elementLevelNode.setDescription(InternationalStringUtils.getLocalisedString(element.getDescription()));
        if (element instanceof ChapterDto) {
            elementLevelNode.setOrderInLevel(((ChapterDto) element).getOrderInLevel());
            elementLevelNode.setParentChapterUrn(((ChapterDto) element).getParentChapterUrn());
        } else if (element instanceof CubeDto) {
            elementLevelNode.setOrderInLevel(((CubeDto) element).getOrderInLevel());
            elementLevelNode.setParentChapterUrn(((CubeDto) element).getParentChapterUrn());
            elementLevelNode.setIcon(org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources.RESOURCE.treeFile().getURL());
        }
        elementLevelNode.setElementLevelDto(elementLevelDto);
        elementLevelNode.setAttribute(ElementLevelDS.INFO, GlobalResources.RESOURCE.info().getURL());
        return elementLevelNode;
    }

    public static ElementLevelTreeNode getPublicationVersionRootNode(String schemeNodeName, RelatedResourceDto publicationVersion) {
        ElementLevelTreeNode elementLevelTreeNode = new ElementLevelTreeNode();
        elementLevelTreeNode.setID(schemeNodeName);
        elementLevelTreeNode.setTitle(InternationalStringUtils.getLocalisedString(publicationVersion.getTitle()));
        return elementLevelTreeNode;
    }

    //
    // QUERIES
    //

    public static QueryRecord getQueryRecord(QueryVersionDto queryDto) {
        QueryRecord record = new QueryRecord();
        record.setId(queryDto.getId());
        record.setCode(queryDto.getCode());
        record.setTitle(getLocalisedString(queryDto.getTitle()));
        record.setDescription(getLocalisedString(queryDto.getDescription()));
        record.setProcStatus(CommonUtils.getProcStatusName(queryDto));
        record.setVersionLogic(queryDto.getVersionLogic());
        record.setUrn(queryDto.getUrn());
        record.setType(CommonUtils.getQueryTypeName(queryDto));
        record.setQueryDto(queryDto);
        return record;
    }

    // Codes

    public static CodeItemRecord getCodeItemRecord(CodeItemDto codeItemDto) {
        CodeItemRecord record = new CodeItemRecord();
        record.setCode(codeItemDto.getCode());
        record.setTitle(codeItemDto.getTitle());
        record.setCodeItemDto(codeItemDto);
        return record;
    }

    public static TemporalCodeRecord getTemporalCodeRecord(TemporalCodeDto temporalCodeDto) {
        TemporalCodeRecord record = new TemporalCodeRecord();
        record.setCode(temporalCodeDto.getIdentifier());
        record.setTitle(temporalCodeDto.getTitle());
        record.setTemporalCodeDto(temporalCodeDto);
        return record;
    }

    public static List<TemporalCodeRecord> getTemporalCodeRecords(List<TemporalCodeDto> dtos) {
        List<TemporalCodeRecord> records = new ArrayList<TemporalCodeRecord>();
        for (TemporalCodeDto dto : dtos) {
            records.add(getTemporalCodeRecord(dto));
        }
        return records;
    }

    //
    // VERSION RATIONALE TYPES
    //

    public static VersionRationaleTypeRecord getVersionRationaleTypeRecord(VersionRationaleTypeDto versionRationaleTypeDto) {
        VersionRationaleTypeRecord record = new VersionRationaleTypeRecord();
        record.setValue(CommonUtils.getVersionRationaleTypeName(versionRationaleTypeDto.getValue()));
        record.setVersionRationaleTypeDto(versionRationaleTypeDto);
        return record;
    }

    public static VersionRationaleTypeRecord[] getVersionRationaleTypeRecords(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        VersionRationaleTypeRecord[] records = new VersionRationaleTypeRecord[versionRationaleTypeDtos.size()];
        for (int i = 0; i < versionRationaleTypeDtos.size(); i++) {
            records[i] = getVersionRationaleTypeRecord(versionRationaleTypeDtos.get(i));
        }
        return records;
    }
}
