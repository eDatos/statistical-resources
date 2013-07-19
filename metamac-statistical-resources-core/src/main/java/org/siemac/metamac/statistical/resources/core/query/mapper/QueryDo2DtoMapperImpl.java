package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

@org.springframework.stereotype.Component("queryDo2DtoMapper")
public class QueryDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements QueryDo2DtoMapper {


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
    public List<QueryVersionDto> queryVersionDoListToDtoList(List<QueryVersion> sources) throws MetamacException {
        List<QueryVersionDto> targets = new ArrayList<QueryVersionDto>();
        for (QueryVersion source : sources) {
            targets.add(queryVersionDoToDto(source));
        }
        return targets;
    }

    private QueryVersionDto queryVersionDoToDto(QueryVersion source, QueryVersionDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        lifeCycleStatisticalResourceDoToDto(source.getLifeCycleStatisticalResource(), target);
        
        // DatasetVersion
        if (source.getDatasetVersion() != null) {
            target.setRelatedDatasetVersion(lifecycleStatisticalResourceDoToRelatedResourceDto(source.getDatasetVersion().getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION));
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
        target.setUuid(source.getUuid());
        target.setVersion(source.getVersion());

        return target;
    }

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
}
