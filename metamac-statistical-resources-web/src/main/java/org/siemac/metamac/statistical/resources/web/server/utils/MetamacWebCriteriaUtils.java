package org.siemac.metamac.statistical.resources.web.server.utils;

import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder.OrderTypeEnum;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasDataCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasSchemeCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.base.HasStatisticalOperationCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.base.HasLastVersionCriteria;
import org.siemac.metamac.web.common.shared.criteria.base.HasSimpleCriteria;

public class MetamacWebCriteriaUtils {

    public static MetamacCriteriaRestriction buildMetamacCriteriaFromWebcriteria(MetamacWebCriteria webCriteria) {
        MetamacCriteriaConjunctionRestriction criteria = new MetamacCriteriaConjunctionRestriction();

        if (webCriteria instanceof HasSimpleCriteria) {
            addRestrictionIfExists(criteria, buildSimpleCriteria(webCriteria));
        }

        if (webCriteria instanceof HasStatisticalOperationCriteria) {
            addRestrictionIfExists(criteria, buildStatisticalOperationCriteria((HasStatisticalOperationCriteria) webCriteria));
        }

        if (webCriteria instanceof HasLastVersionCriteria) {
            addRestrictionIfExists(criteria, buildOnlyLastVersionCriteria((HasLastVersionCriteria) webCriteria));
        }
        if (webCriteria instanceof HasDataCriteria) {
            addRestrictionIfExists(criteria, buildMetamacCriteriaDatasetWithData((HasDataCriteria) webCriteria));
        }

        return criteria;
    }

    public static MetamacCriteriaOrder buildMetamacCriteriaLastUpdatedOrder() {
        MetamacCriteriaOrder order = new MetamacCriteriaOrder();
        order.setType(OrderTypeEnum.DESC);
        order.setPropertyName(StatisticalResourcesCriteriaOrderEnum.LAST_UPDATED.name());
        return order;
    }

    public static MetamacCriteriaRestriction buildMetamacCriteriaDatasetWithData(HasDataCriteria criteria) {
        String param = null;
        if (BooleanUtils.isTrue(criteria.getHasData())) {
            return new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.DATA.name(), param, OperationType.IS_NOT_NULL);
        } else if (BooleanUtils.isFalse(criteria.getHasData())) {
            return new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.DATA.name(), param, OperationType.IS_NULL);
        }
        return null;
    }

    // Private methods

    private static void addRestrictionIfExists(MetamacCriteriaConjunctionRestriction criteria, MetamacCriteriaRestriction restriction) {
        if (restriction != null) {
            criteria.getRestrictions().add(restriction);
        }
    }

    private static MetamacCriteriaRestriction buildSimpleCriteria(HasSimpleCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getCriteria())) {
            MetamacCriteriaDisjunctionRestriction criteriaDisjuction = new MetamacCriteriaDisjunctionRestriction();
            criteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.CODE.name(), criteria.getCriteria(), OperationType.ILIKE));
            criteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.URN.name(), criteria.getCriteria(), OperationType.ILIKE));
            criteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.TITLE.name(), criteria.getCriteria(), OperationType.ILIKE));
            return criteriaDisjuction;
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildStatisticalOperationCriteria(HasStatisticalOperationCriteria criteria) {
        if (StringUtils.isNotBlank(criteria.getStatisticalOperationUrn())) {
            return new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.STATISTICAL_OPERATION_URN.name(), criteria.getStatisticalOperationUrn(), OperationType.EQ);
        }
        return null;
    }

    private static MetamacCriteriaRestriction buildOnlyLastVersionCriteria(HasLastVersionCriteria criteria) {
        if (criteria.isOnlyLastVersion()) {
            return new MetamacCriteriaPropertyRestriction(StatisticalResourcesCriteriaPropertyEnum.LAST_VERSION.name(), criteria.isOnlyLastVersion(), OperationType.EQ);
        }
        return null;
    }
}
