package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("queryDo2DtoMapper")
public class QueryDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements QueryDo2DtoMapper {

    @Autowired
    private QueryVersionRepository   queryVersionRepository;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    // ---------------------------------------------------------------------------------------------------------
    // QUERY VERSION
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public RelatedResourceDto queryVersionDoToQueryRelatedResourceDto(QueryVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        RelatedResourceDto target = new RelatedResourceDto();
        queryVersionDoToQueryRelatedResourceDto(source, target);
        return target;
    }

    private RelatedResourceDto queryVersionDoToQueryRelatedResourceDto(QueryVersion source, RelatedResourceDto target) {
        if (source == null) {
            return null;
        }

        // Identity
        target.setId(source.getQuery().getId());
        target.setVersion(source.getQuery().getVersion());

        // Type
        target.setType(TypeRelatedResourceEnum.QUERY);

        // Identifiable Fields
        target.setCode(source.getQuery().getIdentifiableStatisticalResource().getCode());
        target.setCodeNested(null);
        target.setUrn(source.getQuery().getIdentifiableStatisticalResource().getUrn());

        // Nameable Fields
        target.setTitle(internationalStringDoToDto(source.getLifeCycleStatisticalResource().getTitle()));

        return target;
    }

    @Override
    public QueryVersionDto queryVersionDoToDto(QueryVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        QueryVersionDto target = new QueryVersionDto();
        queryVersionDoToDto(source, target);
        return target;
    }

    @Override
    public List<QueryVersionBaseDto> queryVersionDoListToDtoList(List<QueryVersion> sources) throws MetamacException {
        List<QueryVersionBaseDto> targets = new ArrayList<QueryVersionBaseDto>();
        for (QueryVersion source : sources) {
            targets.add(queryVersionDoToBaseDto(source));
        }
        return targets;
    }

    @Override
    public QueryVersionBaseDto queryVersionDoToBaseDto(QueryVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        QueryVersionBaseDto target = new QueryVersionBaseDto();
        queryVersionDoToBaseDto(source, target);
        return target;
    }

    private QueryVersionBaseDto queryVersionDoToBaseDto(QueryVersion source, QueryVersionBaseDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        lifeCycleStatisticalResourceDoToBaseDto(source.getLifeCycleStatisticalResource(), target);

        // DatasetVersion
        DatasetVersion datasetVersion = getCurrentDatasetVersionInQuery(source);
        if (datasetVersion != null) {
            target.setRelatedDatasetVersion(lifecycleStatisticalResourceDoToRelatedResourceDto(datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION));
        }

        // Status
        target.setStatus(source.getStatus());

        // Type
        target.setType(source.getType());

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        return target;
    }
    private QueryVersionDto queryVersionDoToDto(QueryVersion source, QueryVersionDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        lifeCycleStatisticalResourceDoToDto(source.getLifeCycleStatisticalResource(), target);

        // DatasetVersion
        DatasetVersion datasetVersion = getCurrentDatasetVersionInQuery(source);
        if (datasetVersion != null) {
            target.setRelatedDatasetVersion(lifecycleStatisticalResourceDoToRelatedResourceDto(datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION));
        }

        // Status
        target.setStatus(source.getStatus());

        // Type
        target.setType(source.getType());

        // Latest data number
        target.setLatestDataNumber(source.getLatestDataNumber());

        // Selection
        target.setSelection(selectionDo2Dto(source.getSelection(), target));

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        target.setIsReplacedByVersion(relatedResourceDoToDto(source.getLifeCycleStatisticalResource().getIsReplacedByVersion()));

        List<RelatedResourceResult> isPartOf = queryVersionRepository.retrieveIsPartOf(source);
        target.getIsPartOf().clear();
        target.getIsPartOf().addAll(relatedResourceResultCollectionToDtoCollection(isPartOf));

        return target;
    }

    // ---------------------------------------------------------------------------------------------------------
    // SELECTION
    // ---------------------------------------------------------------------------------------------------------

    private Map<String, List<CodeItemDto>> selectionDo2Dto(List<QuerySelectionItem> source, QueryVersionDto target) {
        Map<String, List<CodeItemDto>> result = new HashMap<String, List<CodeItemDto>>();
        for (QuerySelectionItem querySelectionItem : source) {
            List<CodeItemDto> codesResult = new ArrayList<CodeItemDto>();
            for (CodeItem codeItem : querySelectionItem.getCodes()) {
                codesResult.add(codeItemDo2Dto(codeItem));
            }

            result.put(querySelectionItem.getDimension(), codesResult);
        }
        return result;
    }

    private CodeItemDto codeItemDo2Dto(CodeItem source) {
        return new CodeItemDto(source.getCode(), source.getTitle());
    }

    private DatasetVersion getCurrentDatasetVersionInQuery(QueryVersion queryVersion) throws MetamacException {
        if (queryVersion.getFixedDatasetVersion() != null) {
            return queryVersion.getFixedDatasetVersion();
        } else if (queryVersion.getDataset() != null) {
            return datasetVersionRepository.retrieveLastVersion(queryVersion.getDataset().getIdentifiableStatisticalResource().getUrn());
        }
        return null;
    }
}
