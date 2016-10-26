package org.siemac.metamac.rest.statistical_resources.v1_0.mockito;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;

public class FindQueriesByRelatedDatasetUrnMatcher extends ConditionalCriteriasMatcher {

    protected static MockitoMockConfig mockitoMockConfig;

    public FindQueriesByRelatedDatasetUrnMatcher() {
        mockitoMockConfig = ApplicationContextProvider.getApplicationContext().getBean(MockitoMockConfig.class);
    }

    @Override
    public boolean matches(Object actual) {
        if (!mockitoMockConfig.isApplyArgumentMatcher()) {
            return true;
        }

        List<ConditionalCriteria> expected = new ArrayList<ConditionalCriteria>();

        // default order
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).orderBy(QueryVersionProperties.lifeCycleStatisticalResource().code()).ascending().buildSingle());

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            addConditionsForInternalApi(expected);
        } else {
            addConditionsForExternalApi(expected);
        }

        // distinc root
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).distinctRoot().buildSingle());

        // maintainer
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().maintainer().codeNested()).eq("agency1")
                .buildSingle());

        // Compare
        return super.matches(expected, actual);
    }

    protected void addConditionsForInternalApi(List<ConditionalCriteria> expected) {
        //@formatter:off
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class)
            .lbrace()
                .withProperty(QueryVersionProperties.dataset().versions().siemacMetadataStatisticalResource().lastVersion()).eq(Boolean.TRUE)
                .and()
                .withProperty(QueryVersionProperties.dataset().versions().siemacMetadataStatisticalResource().urn()).eq("URN_TEST")
            .rbrace()
            .or()
            .withProperty(QueryVersionProperties.fixedDatasetVersion().siemacMetadataStatisticalResource().urn()).eq("URN_TEST")
            .buildSingle()
        );
        //@formatter:on
    }

    protected void addConditionsForExternalApi(List<ConditionalCriteria> expected) {
        DateTime now = new DateTime();
        //@formatter:off
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class)
              .withProperty(QueryVersionProperties.dataset().versions().siemacMetadataStatisticalResource().urn()).eq("URN_TEST")
              .or()
              .withProperty(QueryVersionProperties.fixedDatasetVersion().siemacMetadataStatisticalResource().urn()).eq("URN_TEST")
              .buildSingle()
        );
        
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class)
              .lbrace()
                  .withProperty(QueryVersionProperties.lifeCycleStatisticalResource().validTo()).greaterThan(now)
                  .or()
                  .withProperty(QueryVersionProperties.lifeCycleStatisticalResource().validTo()).isNull()
              .rbrace()
              .and()
              .lbrace()
                  .withProperty(QueryVersionProperties.dataset().versions().siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED.toString())
              .rbrace()
            .buildSingle()
        );
        //@formatter:on
    }
}