package org.siemac.metamac.statistical.resources.core.utils;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceProperties.LifeCycleStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceProperties.SiemacMetadataStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class StatisticalResourcesCriteriaUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ConditionalCriteria buildLastPublishedVersionCriteria(Class entityClass, SiemacMetadataStatisticalResourceProperty siemacMetadataStatisticalResourceProperty) {
        DateTime now = new DateTime();
        //@formatter:off
        return ConditionalCriteriaBuilder.criteriaFor(entityClass).
                withProperty(siemacMetadataStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED).
                and().
                withProperty(siemacMetadataStatisticalResourceProperty.validFrom()).lessThanOrEqual(now).
                and().
                    lbrace().
                        withProperty(siemacMetadataStatisticalResourceProperty.validTo()).isNull().
                        or().
                        withProperty(siemacMetadataStatisticalResourceProperty.validTo()).greaterThan(now).
                    rbrace().distinctRoot().buildSingle();
        //@formatter:on
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ConditionalCriteria buildLastPublishedVersionCriteria(Class entityClass, LifeCycleStatisticalResourceProperty lifeCycleStatisticalResourceProperty) {
        DateTime now = new DateTime();
        //@formatter:off
        return ConditionalCriteriaBuilder.criteriaFor(entityClass).
            withProperty(lifeCycleStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED).
            and().
            withProperty(lifeCycleStatisticalResourceProperty.validFrom()).lessThanOrEqual(now).
            and().
                lbrace().
                    withProperty(lifeCycleStatisticalResourceProperty.validTo()).isNull().
                    or().
                    withProperty(lifeCycleStatisticalResourceProperty.validTo()).greaterThan(now).
                rbrace().distinctRoot().buildSingle();
        //@formatter:on
    }
}
