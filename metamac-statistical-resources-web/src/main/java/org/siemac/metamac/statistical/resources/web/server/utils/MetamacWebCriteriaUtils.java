package org.siemac.metamac.statistical.resources.web.server.utils;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaConjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaDisjunctionRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaRestriction;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;

public class MetamacWebCriteriaUtils {

    // -------------------------------------------------------------------------------------------------------------
    // PUBLICATIONS
    // -------------------------------------------------------------------------------------------------------------

    public static MetamacCriteriaRestriction getPublicationCriteriaRestriction(VersionableStatisticalResourceWebCriteria criteria) {
        MetamacCriteriaConjunctionRestriction conjunctionRestriction = new MetamacCriteriaConjunctionRestriction();

        if (criteria != null) {

            // General criteria

            MetamacCriteriaDisjunctionRestriction publicationCriteriaDisjuction = new MetamacCriteriaDisjunctionRestriction();
            if (StringUtils.isNotBlank(criteria.getCriteria())) {
                publicationCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(PublicationCriteriaPropertyEnum.CODE.name(), criteria.getCriteria(), OperationType.ILIKE));
                publicationCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(PublicationCriteriaPropertyEnum.TITLE.name(), criteria.getCriteria(), OperationType.ILIKE));
                publicationCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(PublicationCriteriaPropertyEnum.URN.name(), criteria.getCriteria(), OperationType.ILIKE));
            }
            conjunctionRestriction.getRestrictions().add(publicationCriteriaDisjuction);

            // Specific criteria

            if (StringUtils.isNotBlank(criteria.getStatisticalOperationUrn())) {
                conjunctionRestriction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(PublicationCriteriaPropertyEnum.STATISTICAL_OPERATION_URN.name(), criteria.getStatisticalOperationUrn(), OperationType.EQ));
            }
            
            if (criteria.isOnlyLastVersion()) {
                
            }

        }
        return conjunctionRestriction;
    }

    // -------------------------------------------------------------------------------------------------------------
    // DATASETS
    // -------------------------------------------------------------------------------------------------------------

    public static MetamacCriteriaRestriction getStatisticalResourceCriteriaRestriction(VersionableStatisticalResourceWebCriteria criteria) {
        MetamacCriteriaConjunctionRestriction conjunctionRestriction = new MetamacCriteriaConjunctionRestriction();

        if (criteria != null) {

            // General criteria

            MetamacCriteriaDisjunctionRestriction datasetCriteriaDisjuction = new MetamacCriteriaDisjunctionRestriction();
            if (StringUtils.isNotBlank(criteria.getCriteria())) {
                datasetCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(DatasetCriteriaPropertyEnum.CODE.name(), criteria.getCriteria(), OperationType.ILIKE));
                datasetCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(DatasetCriteriaPropertyEnum.TITLE.name(), criteria.getCriteria(), OperationType.ILIKE));
                datasetCriteriaDisjuction.getRestrictions().add(new MetamacCriteriaPropertyRestriction(DatasetCriteriaPropertyEnum.URN.name(), criteria.getCriteria(), OperationType.ILIKE));
            }
            conjunctionRestriction.getRestrictions().add(datasetCriteriaDisjuction);

            // Specific criteria

            if (StringUtils.isNotBlank(criteria.getStatisticalOperationUrn())) {
                conjunctionRestriction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(DatasetCriteriaPropertyEnum.STATISTICAL_OPERATION_URN.name(), criteria.getStatisticalOperationUrn(), OperationType.EQ));
            }
            
            if (criteria.isOnlyLastVersion()) {
                conjunctionRestriction.getRestrictions().add(
                        new MetamacCriteriaPropertyRestriction(DatasetCriteriaPropertyEnum.LAST_VERSION.name(), criteria.isOnlyLastVersion(), OperationType.EQ));
            }

        }
        return conjunctionRestriction;
    }
}
