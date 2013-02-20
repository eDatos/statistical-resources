package org.siemac.metamac.statistical.resources.core.query.mapper;

import java.util.HashSet;
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
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.exception.QueryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("queryDto2DoMapper")
public class QueryDto2DoMapperImpl extends BaseDto2DoMapperImpl implements QueryDto2DoMapper {

    @Autowired
    private QueryRepository queryRepository;
    
    @Autowired 
    private DatasetVersionRepository datasetVersionRepository;
    
    @Override
    public Query queryDtoToDo(QueryDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        Query target = null;
        if (source.getId() == null) {
            target = new Query();
            target.setLifeCycleStatisticalResource(new LifeCycleStatisticalResource());
        } else {
            try {
                target = queryRepository.findById(source.getId());
            } catch (QueryNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.QUERY_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.QUERY)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        queryDtoToDo(source, target);

        return target;
    }

    private Query queryDtoToDo(QueryDto source, Query target) throws MetamacException {
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
        target.getSelection().addAll(querySelectionDto2Do(source.getSelection(), target.getSelection(), target, ServiceExceptionParameters.QUERY__SELECTION));

        return target;
    }

    private Set<QuerySelectionItem> querySelectionDto2Do(Map<String, Set<String>> source, Set<QuerySelectionItem> target, Query queryTarget, String metadataName) {
        if (source.isEmpty()) {
            return new HashSet<QuerySelectionItem>();
        } else {
            target.clear();
            target = new HashSet<QuerySelectionItem>();
            for (Map.Entry<String, Set<String>> entry : source.entrySet()) {
                target.add(selectionItemDto2Do(entry, queryTarget));
            }
            return target;
        }
    }

    private QuerySelectionItem selectionItemDto2Do(Entry<String, Set<String>> sourceSelectionItem, Query queryTarget) {
        QuerySelectionItem targetSelectionItem = new QuerySelectionItem();
        targetSelectionItem.setQuery(queryTarget);
        targetSelectionItem.setDimension(sourceSelectionItem.getKey());
        if (!sourceSelectionItem.getValue().isEmpty()) {
            for (String sourceCodeItem : sourceSelectionItem.getValue()) {
                CodeItem code = new CodeItem();
                code.setCode(sourceCodeItem);
                code.setQuerySelectionItem(targetSelectionItem);
                targetSelectionItem.addCode(code);
            }
        }
        return targetSelectionItem;
    }
}
