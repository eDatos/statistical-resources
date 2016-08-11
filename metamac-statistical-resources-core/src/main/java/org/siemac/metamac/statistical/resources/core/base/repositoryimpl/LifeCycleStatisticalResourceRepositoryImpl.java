package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getRelatedResourceResultsFromRows;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils.buildRelatedResourceForeignKeyBasedOnType;

import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for LifeCycleStatisticalResource
 */
@Repository("lifeCycleStatisticalResourceRepository")
public class LifeCycleStatisticalResourceRepositoryImpl extends LifeCycleStatisticalResourceRepositoryBase {

    public LifeCycleStatisticalResourceRepositoryImpl() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public RelatedResourceResult retrieveIsReplacedByVersion(Long currentResourceId, TypeRelatedResourceEnum type) {
        String relatedResourceFkColumn = buildRelatedResourceForeignKeyBasedOnType(type);
        //@formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT stat.code, " + 
                "  stat.urn, " + 
                "  operation.code AS oper_code, " + 
                "  operation.urn AS oper_urn, " + 
                "  maintainer.code_nested, " + 
                "  stat.version_logic, " + 
                "  loc.locale, " + 
                "  loc.label " + 
                "FROM tb_stat_resources stat, " + 
                "  tb_external_items operation, " + 
                "  tb_external_items maintainer, " + 
                "  tb_localised_strings loc, " + 
                "  tb_related_resources related " + 
                "WHERE stat.replaces_version_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND stat.stat_operation_fk = operation.ID " + 
                "   AND stat.maintainer_fk = maintainer.ID " + 
                "   AND stat.title_fk = loc.international_string_fk");
        //@formatter:on
        query.setParameter("resourceId", currentResourceId);

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, type);
        return resources.isEmpty() ? null : resources.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RelatedResourceResult retrieveIsReplacedByVersionOnlyLastPublished(Long currentResourceId, TypeRelatedResourceEnum type) {
        String relatedResourceFkColumn = buildRelatedResourceForeignKeyBasedOnType(type);
        //@formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT stat.code, " + 
                "  stat.urn, " + 
                "  operation.code AS oper_code, " + 
                "  operation.urn AS oper_urn, " + 
                "  maintainer.code_nested, " + 
                "  stat.version_logic, " + 
                "  loc.locale, " + 
                "  loc.label " + 
                "FROM tb_stat_resources stat, " + 
                "  tb_external_items operation, " + 
                "  tb_external_items maintainer, " + 
                "  tb_localised_strings loc, " + 
                "  tb_related_resources related " + 
                "WHERE stat.replaces_version_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND "+RepositoryUtils.isLastPublishedVersionConditions+" "+
                "   AND stat.stat_operation_fk = operation.ID " + 
                "   AND stat.maintainer_fk = maintainer.ID " + 
                "   AND stat.title_fk = loc.international_string_fk");
        //@formatter:on
        query.setParameter("resourceId", currentResourceId);
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, type);
        return resources.isEmpty() ? null : resources.get(0);
    }
}
