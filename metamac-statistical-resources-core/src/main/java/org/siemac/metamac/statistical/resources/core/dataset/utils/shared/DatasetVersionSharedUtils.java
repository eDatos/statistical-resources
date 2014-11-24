package org.siemac.metamac.statistical.resources.core.dataset.utils.shared;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.siemac.metamac.core.common.util.shared.StringUtils;

public class DatasetVersionSharedUtils {

    private static final String DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER = "#";
    private static final String COMMA                                        = ",";

    public static String dimensionRepresentationMapToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (Entry<String, String> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER).append(entry.getValue()).append(COMMA);
        }
        return StringUtils.removeEnd(builder.toString(), COMMA);
    }

    public static Map<String, String> dimensionRepresentationMapFromString(String mapping) {
        Map<String, String> map = new HashMap<String, String>();
        if (!StringUtils.isBlank(mapping)) {
            String[] pairs = mapping.split(COMMA);
            for (String pair : pairs) {
                String[] splitItem = pair.split(DIMENSION_REPRESENTATION_SEPARATOR_CHARACTER);
                if (splitItem != null && splitItem.length == 2) {
                    map.put(splitItem[0].trim(), splitItem[1].trim());
                }
            }
        }
        return map;
    }
}
