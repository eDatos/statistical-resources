package org.siemac.metamac.statistical.resources.web.server.utils;

import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
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
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
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
            addRestrictionIfExists(criteria, buildStatisticalOperationCriteria((HasStatisticalOperationCriteria)webCriteria));
        }
        
        if (webCriteria instanceof HasLastVersionCriteria) {
            addRestrictionIfExists(criteria, buildOnlyLastVersionCriteria((HasLastVersionCriteria)webCriteria));
        }
        
        return criteria;
    }
    
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


    // -------------------------------------------------------------------------------------------------------------
    // STATISTICAL OPERATION
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryStatisticalOperation(MetamacWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, OperationCriteriaPropertyRestriction.TITLE, OperationCriteriaPropertyRestriction.ID);
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // STATISTICAL OPERATION INSTANCE
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryStatisticalOperationInstance(MetamacWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, InstanceCriteriaPropertyRestriction.TITLE, InstanceCriteriaPropertyRestriction.ID);
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // DSD
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryDsd(DsdWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            String operationUrn = webCriteria.getStatisticalOperationUrn();
            String dsdCode = webCriteria.getFixedDsdCode();
            boolean onlyLastVersion = webCriteria.isOnlyLastVersion();

            addSimpleCriteria(queryBuilder, webCriteria, DataStructureCriteriaPropertyRestriction.NAME, DataStructureCriteriaPropertyRestriction.ID, DataStructureCriteriaPropertyRestriction.URN);

            if (StringUtils.isNotBlank(dsdCode)) {
                String dsdCodeCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.ID, ComparisonOperator.EQ, dsdCode);
                appendConditionToQuery(queryBuilder, dsdCodeCondition);
            }

            if (StringUtils.isNotBlank(operationUrn)) {
                String statOperCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.ILIKE, operationUrn);
                appendConditionToQuery(queryBuilder, statOperCondition);
            }

            if (onlyLastVersion) {
                String lastVersionCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.LATEST, ComparisonOperator.EQ, onlyLastVersion);
                appendConditionToQuery(queryBuilder, lastVersionCondition);
            }
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // CODE
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryCode(MetamacWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, CodeCriteriaPropertyRestriction.NAME, CodeCriteriaPropertyRestriction.ID, CodeCriteriaPropertyRestriction.URN);
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // CONCEPT SCHEME
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryConceptScheme(MetamacWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, ConceptSchemeCriteriaPropertyRestriction.NAME, ConceptSchemeCriteriaPropertyRestriction.ID, ConceptSchemeCriteriaPropertyRestriction.URN);
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // CONCEPT
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryConcept(ItemSchemeWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, ConceptCriteriaPropertyRestriction.NAME, ConceptCriteriaPropertyRestriction.ID, ConceptCriteriaPropertyRestriction.URN);

            if (webCriteria.getSchemeUrn() != null) {
                String schemeCondition = fieldComparison(ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_URN, ComparisonOperator.EQ, webCriteria.getSchemeUrn());
                appendConditionToQuery(queryBuilder, schemeCondition);
            }
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // ORGANISATION SCHEME
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryOrganisationScheme(MetamacWebCriteria webCriteria, TypeExternalArtefactsEnum type) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, OrganisationSchemeCriteriaPropertyRestriction.NAME, OrganisationSchemeCriteriaPropertyRestriction.ID,
                    OrganisationSchemeCriteriaPropertyRestriction.URN);

        }
        if (type != null) {
            String typeCondition = fieldComparison(OrganisationSchemeCriteriaPropertyRestriction.TYPE, ComparisonOperator.EQ, type);
            appendConditionToQuery(queryBuilder, typeCondition);
        }
        return queryBuilder.toString();
    }

    // -------------------------------------------------------------------------------------------------------------
    // ORGANISATION
    // -------------------------------------------------------------------------------------------------------------

    public static String buildQueryOrganisation(ItemSchemeWebCriteria webCriteria, TypeExternalArtefactsEnum type) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            addSimpleCriteria(queryBuilder, webCriteria, OrganisationCriteriaPropertyRestriction.NAME, OrganisationCriteriaPropertyRestriction.ID, OrganisationCriteriaPropertyRestriction.URN);

            if (webCriteria.getSchemeUrn() != null) {
                String schemeCondition = fieldComparison(OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_URN, ComparisonOperator.EQ, webCriteria.getSchemeUrn());
                appendConditionToQuery(queryBuilder, schemeCondition);
            }
        }
        if (type != null) {
            String typeCondition = fieldComparison(OrganisationCriteriaPropertyRestriction.TYPE, ComparisonOperator.EQ, type);
            appendConditionToQuery(queryBuilder, typeCondition);
        }
        return queryBuilder.toString();
    }

    @SuppressWarnings("rawtypes")
    private static void addSimpleCriteria(StringBuilder queryBuilder, MetamacWebCriteria criteria, Enum... fields) {
        String simpleCriteria = criteria.getCriteria();
        if (StringUtils.isNotBlank(simpleCriteria)) {
            StringBuilder conditionBuilder = new StringBuilder();

            List<String> conditions = new ArrayList<String>();
            for (Enum field : fields) {
                conditions.add(fieldComparison(field, ComparisonOperator.ILIKE, simpleCriteria));
            }

            conditionBuilder.append("(");
            for (int i = 0; i < conditions.size(); i++) {
                if (i > 0) {
                    conditionBuilder.append(" ").append(LogicalOperator.OR).append(" ");
                }
                conditionBuilder.append(conditions.get(i));
            }
            conditionBuilder.append(")");
            appendConditionToQuery(queryBuilder, conditionBuilder.toString());
        }
    }

}
