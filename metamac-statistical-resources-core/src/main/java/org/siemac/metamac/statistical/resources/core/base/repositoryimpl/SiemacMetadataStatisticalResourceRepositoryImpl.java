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

}
