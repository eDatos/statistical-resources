package org.siemac.metamac.statistical.resources.core.query.mapper;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.exception.QueryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("queryDto2DoMapper")
public class QueryDto2DoMapperImpl extends BaseDto2DoMapperImpl implements QueryDto2DoMapper {

    @Autowired
    private QueryRepository               queryRepository;


    @Override
    public Query queryDtoToDo(QueryDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        Query target = null;
        if (source.getId() == null) {
            target = new Query();
            target.setNameableStatisticalResource(new NameableStatisticalResource());
        } else {
            try {
                target = queryRepository.findById(source.getId());
            } catch (QueryNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.QUERY_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.QUERY)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getVersion());
        }

        queryDtoToDo(source, target);

        return target;
    }

    private Query queryDtoToDo(QueryDto source, Query target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.NAMEABLE_RESOURCE).build();
        }

        // Hierarchy
        nameableStatisticalResourceDtoToDo(source, target.getNameableStatisticalResource());
        
        // Non modifiable after creation

        // Attributes modifiable
        // TODO: Dataset

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        // TODO: Add update date
        // target.setUpdateDate(new DateTime());

        return target;

    }
}
