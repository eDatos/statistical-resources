package org.siemac.metamac.statistical.resources.core.utils;

import java.util.Collection;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.MetamacCollectionUtils;
import org.siemac.metamac.statistical.resources.core.utils.predicates.ExternalItemEqualsUrnPredicate;


public class StatisticalResourcesCollectionUtils extends MetamacCollectionUtils {

    public static ExternalItem findExternalItem(Collection<ExternalItem> collection, ExternalItem externalItem) {
        return find(collection, new ExternalItemEqualsUrnPredicate(externalItem.getUrn()));
    }
    
    public static ExternalItem findExternalItemByUrn(Collection<ExternalItem> collection, String urn) {
        return find(collection, new ExternalItemEqualsUrnPredicate(urn));
    }
    
    public static boolean isExternalItemInCollection(Collection<ExternalItem> collection, ExternalItem externalItem) {
        return find(collection, new ExternalItemEqualsUrnPredicate(externalItem.getUrn())) != null;
    }
}
