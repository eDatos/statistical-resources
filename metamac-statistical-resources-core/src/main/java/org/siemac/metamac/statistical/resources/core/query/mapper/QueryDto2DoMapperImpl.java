package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItemRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.exception.QueryVersionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("queryDto2DoMapper")
public class QueryDto2DoMapperImpl extends BaseDto2DoMapperImpl implements QueryDto2DoMapper {

    @Autowired
    private QueryVersionRepository       queryVersionRepository;

    @Autowired
    private DatasetVersionRepository     datasetVersionRepository;

    @Autowired
    private QuerySelectionItemRepository querySelectionItemRepository;

    @Override
    public QueryVersion queryVersionDtoToDo(QueryDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        QueryVersion target = null;
        if (source.getId() == null) {
            target = new QueryVersion();
            target.setLifeCycleStatisticalResource(new LifeCycleStatisticalResource());
        } else {
            try {
                target = queryVersionRepository.findById(source.getId());
            } catch (QueryVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.QUERY_NOT_FOUND).withMessageParameters(source.getUrn())
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        queryVersionDtoToDo(source, target);

        return target;
    }

    private QueryVersion queryVersionDtoToDo(QueryDto source, QueryVersion target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.QUERY).build();
        }

        // Hierarchy
        lifeCycleStatisticalResourceDtoToDo(source, target.getLifeCycleStatisticalResource(), ServiceExceptionParameters.QUERY);

        // DatasetVersion
        if (source.getDatasetVersion() != null) {
            DatasetVersion datasetVersionTarget = datasetVersionRepository.retrieveByUrn(source.getDatasetVersion());
            target.setDatasetVersion(datasetVersionTarget);
        }

        // Status
        // Not mapped. It's automatically managed.

        // Type
        target.setType(source.getType());

        // Latest Data Number
        target.setLatestDataNumber(source.getLatestDataNumber());

        // Selection
        target.getSelection().addAll(querySelectionDto2Do(source.getSelection(), target.getSelection(), target, ServiceExceptionParameters.QUERY_VERSION__SELECTION));

        return target;
    }

    private List<QuerySelectionItem> querySelectionDto2Do(Map<String, Set<String>> source, List<QuerySelectionItem> target, QueryVersion queryTarget, String metadataName) {
        if (source.isEmpty()) {
            if (!target.isEmpty()) {
                // Delete old entities
                deleteQuerySelectionItemList(target);
            }
            return new ArrayList<QuerySelectionItem>();
        }
        
        if (target.isEmpty()) {
            target = new ArrayList<QuerySelectionItem>();
        }

        
        List<QuerySelectionItem> querySelectionItemEntities = querySelectionItemListDto2Do(source, target, queryTarget);
        target.clear();
        target.addAll(querySelectionItemEntities);
        
        return target;
        
        
    }


    private List<QuerySelectionItem> querySelectionItemListDto2Do(Map<String, Set<String>> source, List<QuerySelectionItem> targets, QueryVersion queryTarget) {
        List<QuerySelectionItem> targetsBefore = targets;
        targets = new ArrayList<QuerySelectionItem>();
        
        for (Map.Entry<String, Set<String>> sourceItem : source.entrySet()) {
            boolean existsBefore = false;
            for (QuerySelectionItem targetItem : targetsBefore) {
                if (sourceItem.getKey().equals(targetItem.getDimension())) {
                    targets.add(querySelectionItemDto2Do(sourceItem, targetItem, queryTarget));
                    existsBefore = true;
                    break;
                }
            }
            
            if (!existsBefore) {
                targets.add(querySelectionItemDto2Do(sourceItem, queryTarget));
            }
        }
        return targets;
    }

    private QuerySelectionItem querySelectionItemDto2Do(Entry<String, Set<String>> sourceItem, QueryVersion queryTarget) {
        QuerySelectionItem target = new QuerySelectionItem();
        querySelectionItemDto2Do(sourceItem, target, queryTarget);
        return target;
    }

    private QuerySelectionItem querySelectionItemDto2Do(Entry<String, Set<String>> sourceItem, QuerySelectionItem targetItem, QueryVersion queryTarget) {
        targetItem.setQuery(queryTarget);
        targetItem.setDimension(sourceItem.getKey());
        
        // Set codes
        List<CodeItem> codeItemsBefore = targetItem.getCodes();
        targetItem.getCodes().clear();
        
        for (String value : sourceItem.getValue()) {
            boolean existsBefore = false;
            for (CodeItem codeItem : codeItemsBefore) {
                if (value.equals(codeItem.getCode())) {
                    targetItem.addCode(codeItemDto2Do(value, codeItem, targetItem));
                    existsBefore = true;
                    break;
                }
            }
            
            if (!existsBefore) {
                targetItem.addCode(codeItemDto2Do(value, targetItem));
            }
        }
        return targetItem;
    }

    private CodeItem codeItemDto2Do(String value, QuerySelectionItem targetItem) {
        CodeItem target = new CodeItem();
        codeItemDto2Do(value, target, targetItem);
        return target;
    }

    private CodeItem codeItemDto2Do(String value, CodeItem target, QuerySelectionItem targetItem) {
        target.setCode(value);
        target.setQuerySelectionItem(targetItem);
        return target;
    }



    private void deleteQuerySelectionItemList(List<QuerySelectionItem> target) {
        for (QuerySelectionItem querySelectionItem : target) {
            querySelectionItemRepository.delete(querySelectionItem);
        }
    }

}
