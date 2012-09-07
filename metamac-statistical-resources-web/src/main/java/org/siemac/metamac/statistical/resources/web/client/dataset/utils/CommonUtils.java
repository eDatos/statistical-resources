package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.LinkedHashMap;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;

public class CommonUtils {

    public static String getDatasetProcStatus(DatasetDto datasetDto) {
        return getCoreMessages().getString(getCoreMessages().statisticalResourceProcStatusEnum() + datasetDto.getProcStatus().getName());
    }

    public static LinkedHashMap<String, String> getDatasetTypeHashMap() {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(new String(), new String());
        for (StatisticalResourceTypeEnum c : StatisticalResourceTypeEnum.values()) {
            String value = getCoreMessages().getString(getCoreMessages().statisticalResourceTypeEnum() + c.getName());
            valueMap.put(c.toString(), value);
        }
        return valueMap;
    }

}
