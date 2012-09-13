package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.LinkedHashMap;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionTypeEnum;

public class CommonUtils {

    private static LinkedHashMap<String, String> statisticalResourceTypeHashMap                 = null;
    private static LinkedHashMap<String, String> statisticalResourceVersionRationaleTypeHashMap = null;
    private static LinkedHashMap<String, String> statisticalResourceFormatHashMap               = null;
    private static LinkedHashMap<String, String> versionTypeHashMap                             = null;

    public static String getProcStatusName(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        return lifeCycleStatisticalResourceDto != null
                ? getCoreMessages().getString(getCoreMessages().statisticalResourceProcStatusEnum() + lifeCycleStatisticalResourceDto.getProcStatus().getName())
                : null;
    }

    public static LinkedHashMap<String, String> getStatisticalResourceTypeHashMap() {
        if (statisticalResourceTypeHashMap == null) {
            statisticalResourceTypeHashMap = new LinkedHashMap<String, String>();
            statisticalResourceTypeHashMap.put(new String(), new String());
            for (StatisticalResourceTypeEnum c : StatisticalResourceTypeEnum.values()) {
                String value = getCoreMessages().getString(getCoreMessages().statisticalResourceTypeEnum() + c.getName());
                statisticalResourceTypeHashMap.put(c.toString(), value);
            }
        }
        return statisticalResourceTypeHashMap;
    }

    public static String getStatisticalResourceTypeName(StatisticalResourceTypeEnum statisticalResourceTypeEnum) {
        return statisticalResourceTypeEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceTypeEnum() + statisticalResourceTypeEnum.name()) : null;
    }

    public static LinkedHashMap<String, String> getStatisticalResourceVersionRationaleTypeHashMap() {
        if (statisticalResourceVersionRationaleTypeHashMap == null) {
            statisticalResourceVersionRationaleTypeHashMap = new LinkedHashMap<String, String>();
            statisticalResourceVersionRationaleTypeHashMap.put(new String(), new String());
            for (StatisticalResourceVersionRationaleTypeEnum v : StatisticalResourceVersionRationaleTypeEnum.values()) {
                statisticalResourceVersionRationaleTypeHashMap.put(v.toString(), getStatisticalResourceVersionRationaleTypeName(v));
            }
        }
        return statisticalResourceVersionRationaleTypeHashMap;
    }

    public static String getStatisticalResourceVersionRationaleTypeName(StatisticalResourceVersionRationaleTypeEnum versionRationaleType) {
        return versionRationaleType != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceVersionRationaleTypeEnum() + versionRationaleType.name()) : null;
    }

    public static LinkedHashMap<String, String> getStatisticalResourceFormatHashMap() {
        if (statisticalResourceFormatHashMap == null) {
            statisticalResourceFormatHashMap = new LinkedHashMap<String, String>();
            statisticalResourceFormatHashMap.put(new String(), new String());
            for (StatisticalResourceFormatEnum f : StatisticalResourceFormatEnum.values()) {
                statisticalResourceFormatHashMap.put(f.toString(), getStatisticalResourceFormatName(f));
            }
        }
        return statisticalResourceFormatHashMap;
    }

    public static String getStatisticalResourceFormatName(StatisticalResourceFormatEnum statisticalResourceFormatEnum) {
        return statisticalResourceFormatEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceFormatEnum() + statisticalResourceFormatEnum.name()) : null;
    }

    public static LinkedHashMap<String, String> getVersionTypeHashMap() {
        if (versionTypeHashMap == null) {
            versionTypeHashMap = new LinkedHashMap<String, String>();
            versionTypeHashMap.put(new String(), new String());
            for (VersionTypeEnum v : VersionTypeEnum.values()) {
                versionTypeHashMap.put(v.toString(), getVersionTypeName(v));
            }
        }
        return versionTypeHashMap;
    }

    public static String getVersionTypeName(VersionTypeEnum versionTypeEnum) {
        return versionTypeEnum != null ? getCoreMessages().getString(getCoreMessages().versionTypeEnum() + versionTypeEnum.name()) : null;
    }

}
