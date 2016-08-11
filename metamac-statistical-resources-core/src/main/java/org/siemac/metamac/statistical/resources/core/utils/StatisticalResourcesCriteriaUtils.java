package org.siemac.metamac.statistical.resources.core.utils;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.SculptorCriteriaConjunction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceProperties.LifeCycleStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResourceProperties.SiemacMetadataStatisticalResourceProperty;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

public class StatisticalResourcesCriteriaUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ConditionalCriteria buildLastPublishedVersionCriteria(Class entityClass, SiemacMetadataStatisticalResourceProperty siemacMetadataStatisticalResourceProperty) {
        Date now = new DateTime().toDate();
        //@formatter:off
        return ConditionalCriteriaBuilder.criteriaFor(entityClass)
                .withProperty(siemacMetadataStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED)
                .and()
                .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(siemacMetadataStatisticalResourceProperty.validFrom(), entityClass)).lessThanOrEqual(now)
                .and()
                    .lbrace()
                        .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(siemacMetadataStatisticalResourceProperty.validTo(), entityClass)).isNull()
                        .or()
                        .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(siemacMetadataStatisticalResourceProperty.validTo(), entityClass)).greaterThan(now)
                    .rbrace().buildSingle();
        //@formatter:on
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ConditionalCriteria buildLastPublishedVersionCriteria(Class entityClass, LifeCycleStatisticalResourceProperty lifeCycleStatisticalResourceProperty) {
        Date now = new DateTime().toDate();
        //@formatter:off
        return ConditionalCriteriaBuilder.criteriaFor(entityClass)
                .withProperty(lifeCycleStatisticalResourceProperty.procStatus()).eq(ProcStatusEnum.PUBLISHED)
                .and()
                .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(lifeCycleStatisticalResourceProperty.validFrom(), entityClass)).lessThanOrEqual(now)
                .and()
                    .lbrace()
                        .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(lifeCycleStatisticalResourceProperty.validTo(), entityClass)).isNull()
                        .or()
                        .withProperty(CriteriaUtils.getDatetimeLeafPropertyEmbedded(lifeCycleStatisticalResourceProperty.validTo(), entityClass)).greaterThan(now)
                    .rbrace()
               .buildSingle();
        //@formatter:on
    }

    @SuppressWarnings("rawtypes")
    public static SculptorPropertyCriteriaBase buildPublishedVisibleCondition(Property procStatusProperty, Property validFromProperty, Class entityClazz) {
        SculptorPropertyCriteria statusPublished = new SculptorPropertyCriteria(procStatusProperty, ProcStatusEnum.PUBLISHED, OperationType.EQ);
        SculptorPropertyCriteria validFromBeforeNow = new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(validFromProperty, entityClazz), new Date(), OperationType.LE);
        return new SculptorCriteriaConjunction(statusPublished, validFromBeforeNow);
    }

    @SuppressWarnings("rawtypes")
    public static SculptorPropertyCriteriaBase buildPublishedNotVisibleCondition(Property procStatusProperty, Property validFromProperty, Class entityClazz) {
        SculptorPropertyCriteria statusPublished = new SculptorPropertyCriteria(procStatusProperty, ProcStatusEnum.PUBLISHED, OperationType.EQ);
        SculptorPropertyCriteria validFromBeforeNow = new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(validFromProperty, entityClazz), new Date(), OperationType.GT);
        return new SculptorCriteriaConjunction(statusPublished, validFromBeforeNow);
    }

}
