package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getRelatedResourceResultsFromRows;

import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
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
    public RelatedResourceResult retrieveResourceThatReplacesThisResourceVersion(Long currentResourceId, TypeRelatedResourceEnum type) {
        String relatedResourceFkColumn = buildRelatedResourceForeignKeyBasedOnType(type);
        //@formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT lifecycle.code, " + 
                "  lifecycle.urn, " + 
                "  operation.code AS oper_code, " + 
                "  operation.urn AS oper_urn, " + 
                "  maintainer.code_nested, " + 
                "  lifecycle.version_logic, " + 
                "  loc.locale, " + 
                "  loc.label " + 
                "FROM tb_stat_resources lifecycle, " + 
                "  tb_external_items operation, " + 
                "  tb_external_items maintainer, " + 
                "  tb_localised_strings loc, " + 
                "  tb_related_resources related " + 
                "WHERE lifecycle.replaces_version_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND lifecycle.stat_operation_fk = operation.ID " + 
                "   AND lifecycle.maintainer_fk = maintainer.ID " + 
                "   AND lifecycle.title_fk = loc.international_string_fk");
        //@formatter:on
        query.setParameter("resourceId", currentResourceId);

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, type);
        return resources.isEmpty() ? null : resources.get(0);
    }

    private String buildRelatedResourceForeignKeyBasedOnType(TypeRelatedResourceEnum type) {
        switch (type) {
            case DATASET_VERSION:
                return "related.dataset_version_fk";
            case QUERY_VERSION:
                return "related.query_version_fk";
            case PUBLICATION_VERSION:
                return "related.publication_version_fk";
            default:
                throw new UnsupportedOperationException("Resource of type " + type + " is not supported");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public RelatedResourceResult retrieveLastPublishedVersionResourceThatReplacesThisResourceVersion(Long currentResourceId, TypeRelatedResourceEnum type) {
        String relatedResourceFkColumn = buildRelatedResourceForeignKeyBasedOnType(type);
        //@formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT lifecycle.code, " + 
                "  lifecycle.urn, " + 
                "  operation.code AS oper_code, " + 
                "  operation.urn AS oper_urn, " + 
                "  maintainer.code_nested, " + 
                "  lifecycle.version_logic, " + 
                "  loc.locale, " + 
                "  loc.label " + 
                "FROM tb_stat_resources lifecycle, " + 
                "  tb_external_items operation, " + 
                "  tb_external_items maintainer, " + 
                "  tb_localised_strings loc, " + 
                "  tb_related_resources related " + 
                "WHERE lifecycle.replaces_version_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND lifecycle.proc_status = :publishedProcStatus " +
                "   AND lifecycle.valid_from <= :now "+
                "   AND (lifecycle.valid_to IS NULL OR lifecycle.valid_to > :now) "+
                "   AND lifecycle.stat_operation_fk = operation.ID " + 
                "   AND lifecycle.maintainer_fk = maintainer.ID " + 
                "   AND lifecycle.title_fk = loc.international_string_fk");
        //@formatter:on
        query.setParameter("resourceId", currentResourceId);
        query.setParameter("publishedProcStatus", ProcStatusEnum.PUBLISHED.name());
        query.setParameter("now", new DateTime().toDate());

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, type);
        return resources.isEmpty() ? null : resources.get(0);
    }
}
