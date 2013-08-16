package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for QueryVersion
 */
@Repository("queryVersionRepository")
public class QueryVersionRepositoryImpl extends QueryVersionRepositoryBase {

    public QueryVersionRepositoryImpl() {
    }

    @Override
    public QueryVersion retrieveByUrn(String urn) throws MetamacException {

        List<ConditionalCriteria> condition = criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().urn()).eq(urn).distinctRoot().build();

        List<QueryVersion> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one query version with urn " + urn);
        }

        return result.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryVersion retrieveLastVersion(String queryUrn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.query().identifiableStatisticalResource().urn())
                .eq(queryUrn).orderBy(CriteriaUtils.getDatetimedLeafProperty(QueryVersionProperties.lifeCycleStatisticalResource().creationDate(), QueryVersion.class)).descending().distinctRoot()
                .build();

        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        // Find
        PagedResult<QueryVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return. We have at least one queryVersion
        if (result.getRowCount() == 0) {
            throw new MetamacException(ServiceExceptionType.QUERY_LAST_VERSION_NOT_FOUND, queryUrn);
        }

        return result.getValues().get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public QueryVersion retrieveLastPublishedVersion(String queryUrn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.query().identifiableStatisticalResource().urn())
                .eq(queryUrn).and().withProperty(QueryVersionProperties.lifeCycleStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).and()
                .withProperty(QueryVersionProperties.lifeCycleStatisticalResource().validFrom()).lessThanOrEqual(new DateTime())
                .orderBy(CriteriaUtils.getDatetimedLeafProperty(QueryVersionProperties.lifeCycleStatisticalResource().creationDate(), QueryVersion.class)).descending().distinctRoot().build();

        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        // Find
        PagedResult<QueryVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return
        if (result.getRowCount() != 0) {
            return result.getValues().get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public List<QueryVersion> findLinkedToDatasetVersion(Long datasetVersionId) {
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.datasetVersion().id()).eq(datasetVersionId).build();
        return findByCondition(conditions);
    }

}
