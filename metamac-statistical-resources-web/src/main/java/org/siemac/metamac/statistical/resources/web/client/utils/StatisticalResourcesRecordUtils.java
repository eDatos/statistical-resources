package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
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
import org.siemac.metamac.statistical.resources.web.client.model.record.DsdAttributeInstanceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.DsdAttributeRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.IdentifiableResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.NameableResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.SiemacMetadataRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.TemporalCodeRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.VersionRationaleTypeRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.VersionableResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.ElementLevelTreeNode;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StatisticalResourcesRecordUtils extends RecordUtils {

    //
    // IDENTIFIABLE
    //

    public static IdentifiableResourceRecord getIdentifiableResourceRecord(IdentifiableResourceRecord record, IdentifiableStatisticalResourceDto dto) {
        record.setCode(dto.getCode());
        record.setUrn(dto.getUrn());
        return record;
    }

    //
    // NAMEABLE
    //

    public static NameableResourceRecord getNameableResourceRecord(NameableResourceRecord record, NameableStatisticalResourceDto dto) {
        record = (NameableResourceRecord) getIdentifiableResourceRecord(record, dto);
        record.setTitle(getLocalisedString(dto.getTitle()));
        return record;
    }

    //
    // VERSIONABLE
    //

    public static VersionableResourceRecord getVersionableResourceRecord(VersionableResourceRecord record, VersionableStatisticalResourceDto dto) {
        record = (VersionableResourceRecord) getNameableResourceRecord(record, dto);
        record.setVersionLogic(dto.getVersionLogic());
        return record;
    }

    //
    // LIFECYCLE
    //

    public static LifeCycleResourceRecord getLifeCycleResourceRecord(LifeCycleResourceRecord record, LifeCycleStatisticalResourceDto dto) {
        record = (LifeCycleResourceRecord) getVersionableResourceRecord(record, dto);
        record.setProcStatus(CommonUtils.getProcStatusName(dto));
        record.setCreationDate(DateUtils.getFormattedDate(dto.getCreationDate()));
        record.setPublicationDate(DateUtils.getFormattedDate(dto.getPublicationDate()));
        return record;
    }

    //
    // SIEMAC METADATA
    //

    public static SiemacMetadataRecord getSiemacMetadataRecord(SiemacMetadataRecord record, SiemacMetadataStatisticalResourceDto dto) {
        return (SiemacMetadataRecord) getLifeCycleResourceRecord(record, dto);
    }

    //
    // DATASETS
    //

    public static DatasetRecord getDatasetRecord(DatasetVersionDto datasetDto) {
        DatasetRecord record = (DatasetRecord) getSiemacMetadataRecord(new DatasetRecord(), datasetDto);
        record.setRelatedDSD(datasetDto.getRelatedDsd());
        if (datasetDto.getStatisticOfficiality() != null) {
            record.setStatisticOfficiality(getLocalisedString(datasetDto.getStatisticOfficiality().getDescription()));
        }
        record.setDatasetVersionDto(datasetDto);
        return record;
    }

    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getCode(), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    public static List<DatasetVersionDto> getDatasetVersionDtosFromListGridRecords(ListGridRecord[] records) {
        List<DatasetVersionDto> datasetVersionDtos = new ArrayList<DatasetVersionDto>();
        if (records != null) {
            for (ListGridRecord record : records) {
                DatasetRecord datasetRecord = (DatasetRecord) record;
                datasetVersionDtos.add(datasetRecord.getDatasetVersionDto());
            }
        }
        return datasetVersionDtos;
    }

    //
    // PUBLICATIONS
    //

    public static PublicationRecord getPublicationRecord(PublicationVersionDto publicationDto) {
        PublicationRecord record = (PublicationRecord) getSiemacMetadataRecord(new PublicationRecord(), publicationDto);
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

    public static List<PublicationVersionDto> getPublicationVersionDtosFromListGridRecords(ListGridRecord[] records) {
        List<PublicationVersionDto> publicationVersionDtos = new ArrayList<PublicationVersionDto>();
        if (records != null) {
            for (ListGridRecord record : records) {
                PublicationRecord datasetRecord = (PublicationRecord) record;
                publicationVersionDtos.add(datasetRecord.getPublicationVersionDto());
            }
        }
        return publicationVersionDtos;
    }

    //
    // QUERIES
    //

    public static QueryRecord getQueryRecord(QueryVersionDto queryDto) {
        QueryRecord record = (QueryRecord) getLifeCycleResourceRecord(new QueryRecord(), queryDto);
        record.setRelatedDataset(queryDto.getRelatedDatasetVersion());
        record.setStatus(CommonUtils.getQueryStatusName(queryDto));
        record.setType(CommonUtils.getQueryTypeName(queryDto));
        record.setQueryDto(queryDto);
        return record;
    }

    public static List<QueryVersionDto> getQueryVersionDtosFromListGridRecords(ListGridRecord[] records) {
        List<QueryVersionDto> queryVersionDtos = new ArrayList<QueryVersionDto>();
        if (records != null) {
            for (ListGridRecord record : records) {
                QueryRecord datasetRecord = (QueryRecord) record;
                queryVersionDtos.add(datasetRecord.getQueryVersionDto());
            }
        }
        return queryVersionDtos;
    }

    // Codes

    public static CodeItemRecord getCodeItemRecord(CodeItemDto codeItemDto) {
        return getCodeItemRecord(null, codeItemDto);
    }

    public static CodeItemRecord getCodeItemRecord(String dimensionId, CodeItemDto codeItemDto) {
        CodeItemRecord record = new CodeItemRecord();
        record.setCode(codeItemDto.getCode());
        record.setTitle(codeItemDto.getTitle());
        record.setDimensionId(dimensionId);
        record.setCodeItemDto(codeItemDto);
        return record;
    }

    public static CodeItemRecord[] getCodeItemRecords(List<CodeItemDto> codeItemDtos) {
        CodeItemRecord[] records = new CodeItemRecord[codeItemDtos.size()];
        for (int i = 0; i < codeItemDtos.size(); i++) {
            records[i] = getCodeItemRecord(codeItemDtos.get(i));
        }
        return records;
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

    //
    // DSD ATTRIBUTES
    //

    public static DsdAttributeRecord getDsdAttributeRecord(DsdAttributeDto dsdAttributeDto) {
        DsdAttributeRecord record = new DsdAttributeRecord();
        record.setIdentifier(dsdAttributeDto.getIdentifier());
        record.setRelationshipType(CommonUtils.getAttributeRelationshipTypeName(dsdAttributeDto.getAttributeRelationship().getRelationshipType()));
        record.setDsdAttributeDto(dsdAttributeDto);
        return record;
    }

    public static DsdAttributeRecord[] getDsdAttributeRecords(List<DsdAttributeDto> dsdAttributeDtos) {
        DsdAttributeRecord[] records = new DsdAttributeRecord[dsdAttributeDtos.size()];
        for (int i = 0; i < dsdAttributeDtos.size(); i++) {
            records[i] = getDsdAttributeRecord(dsdAttributeDtos.get(i));
        }
        return records;
    }

    //
    // DSD ATTRIBUTE INSTANCES
    //

    public static DsdAttributeInstanceRecord getDsdAttributeInstanceRecord(DsdAttributeInstanceDto dsdAttributeInstanceDto) {
        DsdAttributeInstanceRecord record = new DsdAttributeInstanceRecord();
        if (dsdAttributeInstanceDto.getValue() != null) {
            if (dsdAttributeInstanceDto.getValue().getExternalItemValue() != null) {
                record.setExternalItemValue(dsdAttributeInstanceDto.getValue().getExternalItemValue());
            } else {
                record.setStringValue(dsdAttributeInstanceDto.getValue().getStringValue());
            }
        }
        record.setDsdAttributeInstaceDto(dsdAttributeInstanceDto);
        return record;
    }

    public static DsdAttributeInstanceRecord[] getDsdAttributeInstanceRecords(List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        DsdAttributeInstanceRecord[] records = new DsdAttributeInstanceRecord[dsdAttributeInstanceDtos.size()];
        for (int i = 0; i < dsdAttributeInstanceDtos.size(); i++) {
            records[i] = getDsdAttributeInstanceRecord(dsdAttributeInstanceDtos.get(i));
        }
        return records;
    }
}
