package org.siemac.metamac.statistical.resources.core.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConfigurationConstants;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class StatisticalResourcesConfigurationImpl extends ConfigurationServiceImpl implements StatisticalResourcesConfiguration {

    private Map<KeyDotEnum, String> dotCodeMappingMap = null;

    @Override
    public Map<KeyDotEnum, String> retrieveDotCodeMapping() throws MetamacException {

        if (dotCodeMappingMap == null) {
            Map<KeyDotEnum, String> dotCodeMappingMap = new HashMap<StatisticalResourcesConfiguration.KeyDotEnum, String>(6);
            List<Object> dotCodeMappingList = retrievePropertyList(StatisticalResourcesConfigurationConstants.DOT_CODE_MAPPING, true);

            for (Object item : dotCodeMappingList) {
                String[] splitItem = ((String) item).split("=");
                try {
                    KeyDotEnum key = KeyDotEnum.valueOf(splitItem[0].trim());
                    dotCodeMappingMap.put(key, splitItem[1].trim());
                } catch (IllegalArgumentException e) {
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID)
                            .withMessageParameters(StatisticalResourcesConfigurationConstants.DOT_CODE_MAPPING).build();
                }
            }

            if (dotCodeMappingMap.size() != 6) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_INVALID)
                        .withMessageParameters(StatisticalResourcesConfigurationConstants.DOT_CODE_MAPPING).build();
            }

            this.dotCodeMappingMap = dotCodeMappingMap;
        }

        return dotCodeMappingMap;
    }

    private String retrieveProperty(String propertyName, Boolean required) throws MetamacException {
        String propertyValue = getProperty(propertyName);
        if (required && StringUtils.isEmpty(propertyValue)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_NOT_FOUND).withMessageParameters(propertyName).build();
        }
        return propertyValue;
    }

    private List<Object> retrievePropertyList(String propertyName, Boolean required) throws MetamacException {
        List<Object> propertyValue = getConfig().getList(propertyName);
        if (required && CollectionUtils.isEmpty(propertyValue)) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.CONFIGURATION_PROPERTY_NOT_FOUND).withMessageParameters(propertyName).build();
        }
        return propertyValue;
    }
}
