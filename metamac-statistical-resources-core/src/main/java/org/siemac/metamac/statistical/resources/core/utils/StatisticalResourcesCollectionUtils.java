package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.util.MetamacCollectionUtils;
import org.siemac.metamac.core.common.util.MetamacReflectionUtils;
import org.siemac.metamac.core.common.util.predicates.ObjectEqualsStringFieldPredicate;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.utils.predicates.ExternalItemEqualsUrnPredicate;
import org.siemac.metamac.statistical.resources.core.utils.transformers.ExternalItemToUrnTransformer;
import org.siemac.metamac.statistical.resources.core.utils.transformers.TemporalCodeToTimeCodeTransformer;

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

    public static <T, R> boolean equalsCollectionByField(Collection<T> expected, Collection<R> actual, String expectedFieldName, String actualFieldName) throws Exception {
        if (expected.size() != actual.size()) {
            return false;
        } else {
            for (Object expectedItem : expected) {
                String nameFieldValue = (String) MetamacReflectionUtils.getComplexFieldValue(expectedItem, expectedFieldName);

                if (!isInCollection(actual, new ObjectEqualsStringFieldPredicate(actualFieldName, nameFieldValue))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void temporalCodesToTimeCodes(Collection<TemporalCode> collection, final Collection<String> outputCollection) {
        mapCollection(collection, outputCollection, new TemporalCodeToTimeCodeTransformer());
    }

    public static void externalItemsToUrns(Collection<ExternalItem> collection, final Collection<String> outputCollection) {
        mapCollection(collection, outputCollection, new ExternalItemToUrnTransformer());
    }

    public static List<String> mapExternalItemsToUrnsList(Collection<ExternalItem> collection) {
        List<String> outputCollection = new ArrayList<String>();
        mapCollection(collection, outputCollection, new ExternalItemToUrnTransformer());
        return outputCollection;
    }

    public static <K, V> void addValueToMapValueList(Map<K, List<V>> map, K key, V value) {
        if (map != null) {
            List<V> values = map.get(key);
            if (values == null) {
                values = new ArrayList<V>();
                map.put(key, values);
            }
            values.add(value);
        }
    }
}
