package org.siemac.metamac.rest.statistical_resources.v1_0.mockito;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.test.mockito.ConditionalCriteriasMatcher;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;

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

        // distinc root
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).distinctRoot().buildSingle());

        // maintainer
        expected.add(ConditionalCriteriaBuilder.criteriaFor(QueryVersion.class).withProperty(QueryVersionProperties.lifeCycleStatisticalResource().maintainer().codeNested()).eq("agency1")
                .buildSingle());

        // Compare
        return super.matches(expected, actual);
    }
}