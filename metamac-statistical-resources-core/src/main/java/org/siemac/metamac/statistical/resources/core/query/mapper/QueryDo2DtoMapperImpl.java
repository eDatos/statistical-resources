package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;

@org.springframework.stereotype.Component("queryDo2DtoMapper")
public class QueryDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements QueryDo2DtoMapper {


    @Override
    public QueryDto queryDoToDto(Query source) {
        if (source == null) {
            return null;
        }
        QueryDto target = new QueryDto();
        queryDoToDto(source, target);
        return target;
    }
    
    @Override
    public List<QueryDto> queryDoListToDtoList(List<Query> sources) {
        List<QueryDto> targets = new ArrayList<QueryDto>();
        for (Query source : sources) {
            targets.add(queryDoToDto(source));
        }
        return targets;
    }

    private QueryDto queryDoToDto(Query source, QueryDto target) {
        if (source == null) {
            return null;
        }

        // Hierarchy
        lifeCycleStatisticalResourceDoToDto(source.getLifeCycleStatisticalResource(), target);
        
        // DatasetVersion
        if (source.getDatasetVersion() != null) {
            target.setDatasetVersion(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn());
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

    private Map<String, Set<String>> selectionDo2Dto(Set<QuerySelectionItem> source, QueryDto target) {
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
        for (QuerySelectionItem querySelectionItem : source) {
            Set<String> codesResult = new HashSet<String>();
            for (CodeItem codeItem : querySelectionItem.getCodes()) {
                codesResult.add(codeItem.getCode());
            }
            
            result.put(querySelectionItem.getDimension(), codesResult);
        }
        return result;
    }
}
