package org.siemac.metamac.statistical.resources.core.utils;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.MetamacCollectionUtils;
import org.siemac.metamac.statistical.resources.core.utils.predicates.ExternalItemEqualsUrnPredicate;
import org.siemac.metamac.statistical.resources.core.utils.transformers.MetamacTransformer;


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

    //TODO: Move to common
    public static <T,R> Collection<T> mapCollection(Collection<T> collection, final Collection outputCollection, MetamacTransformer<T,R> transformer) {
        return CollectionUtils.collect(collection, transformer, outputCollection);
    }
    
    
}
