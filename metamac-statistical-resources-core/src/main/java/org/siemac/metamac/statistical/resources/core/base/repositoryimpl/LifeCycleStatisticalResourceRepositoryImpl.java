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


}
