package org.siemac.metamac.statistical.resources.core.base.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RelatedResourceResultUtils.getRelatedResourceResultsFromRows;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils.buildRelatedResourceForeignKeyBasedOnType;
import static org.siemac.metamac.statistical.resources.core.base.domain.utils.RepositoryUtils.isLastPublishedVersionConditions;

import java.util.List;

import javax.persistence.Query;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for SiemacMetadataStatisticalResource
 */
@Repository("siemacMetadataStatisticalResourceRepository")
public class SiemacMetadataStatisticalResourceRepositoryImpl extends SiemacMetadataStatisticalResourceRepositoryBase {

    public SiemacMetadataStatisticalResourceRepositoryImpl() {
    }

    @Override
    public String findLastUsedCodeForResourceType(String operationUrn, StatisticalResourceTypeEnum resourceType) {
        //@formatter:off
        String subCodeHql = "substring(resource.code, length(resource.code)-5, length(resource.code))";
        String hql = "select    max(" + subCodeHql + ")" + 
                     "from      SiemacMetadataStatisticalResource as resource " + 
                     "where     (resource.statisticalOperation.urn = :operationUrn) " +
                     "  and     (resource.type = :resourceType) ";
        //@formatter:on

        Query query = getEntityManager().createQuery(hql);
        query.setParameter("operationUrn", operationUrn);
        query.setParameter("resourceType", resourceType);
        String result = (String) query.getSingleResult();
        return result;

    }

    @SuppressWarnings("unchecked")
    @Override
    public RelatedResourceResult retrieveIsReplacedBy(Long currentResourceId, TypeRelatedResourceEnum type) throws MetamacException {
        String relatedResourceFkColumn = buildRelatedResourceForeignKeyBasedOnType(type);
        //@formatter:off
        Query query = getEntityManager().createNativeQuery(
                "SELECT siemac.code, " + 
                "  siemac.urn, " + 
                "  operation.code AS oper_code, " + 
                "  operation.urn AS oper_urn, " + 
                "  maintainer.code_nested, " + 
                "  siemac.version_logic, " + 
                "  loc.locale, " + 
                "  loc.label " + 
                "FROM tb_stat_resources siemac, " + 
                "  tb_external_items operation, " + 
                "  tb_external_items maintainer, " + 
                "  tb_localised_strings loc, " + 
                "  tb_related_resources related " + 
                "WHERE siemac.replaces_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND siemac.stat_operation_fk = operation.ID " + 
                "   AND siemac.maintainer_fk = maintainer.ID " + 
                "   AND siemac.title_fk = loc.international_string_fk");
        //@formatter:on
        query.setParameter("resourceId", currentResourceId);

        List<Object> rows = query.getResultList();
        List<RelatedResourceResult> resources = getRelatedResourceResultsFromRows(rows, type);
        return resources.isEmpty() ? null : resources.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public RelatedResourceResult retrieveIsReplacedByOnlyLastPublished(Long currentResourceId, TypeRelatedResourceEnum type) throws MetamacException {
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
                "WHERE stat.replaces_fk = related.ID " + 
                "   AND "+relatedResourceFkColumn+" = :resourceId " + 
                "   AND "+isLastPublishedVersionConditions + " " +
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
