package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.LinkedHashMap;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceNextVersionEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

public class CommonUtils {

    private static LinkedHashMap<String, String> statisticalResourceTypeHashMap                 = null;
    private static LinkedHashMap<String, String> statisticalResourceVersionRationaleTypeHashMap = null;
    // private static LinkedHashMap<String, String> statisticalResourceFormatHashMap = null;
    private static LinkedHashMap<String, String> versionTypeHashMap                             = null;
    private static LinkedHashMap<String, String> statisticalResourceNextVersionHashMap          = null;

    // -----------------------------------------------------------------------------------------
    // PROC STATUS
    // -----------------------------------------------------------------------------------------

    public static String getProcStatusName(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        return lifeCycleStatisticalResourceDto != null
                ? getCoreMessages().getString(getCoreMessages().statisticalResourceProcStatusEnum() + lifeCycleStatisticalResourceDto.getProcStatus().getName())
                : null;
    }

    // -----------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE TYPE
    // -----------------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE VERSION RATIONALE TYPE
    // -----------------------------------------------------------------------------------------

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

    public static String getStatisticalResourceVersionRationaleTypeNames(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < versionRationaleTypeDtos.size(); i++) {
            builder.append(i != 0 ? ",  " : StringUtils.EMPTY).append(getStatisticalResourceVersionRationaleTypeName(versionRationaleTypeDtos.get(i).getValue()));
        }
        return builder.toString();
    }

    // -----------------------------------------------------------------------------------------
    // VERSION TYPE
    // -----------------------------------------------------------------------------------------

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

    // -----------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE NEXT VERSION
    // -----------------------------------------------------------------------------------------

    public static LinkedHashMap<String, String> getStatisticalResourceNextVersionHashMap() {
        if (statisticalResourceNextVersionHashMap == null) {
            statisticalResourceNextVersionHashMap = new LinkedHashMap<String, String>();
            statisticalResourceNextVersionHashMap.put(new String(), new String());
            for (StatisticalResourceNextVersionEnum s : StatisticalResourceNextVersionEnum.values()) {
                statisticalResourceNextVersionHashMap.put(s.name(), getStatisticalResourceNextVersionName(s));
            }
        }
        return statisticalResourceNextVersionHashMap;
    }

    public static String getStatisticalResourceNextVersionName(StatisticalResourceNextVersionEnum statisticalResourceNextVersionEnum) {
        return statisticalResourceNextVersionEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceNextVersionEnum() + statisticalResourceNextVersionEnum.name()) : null;
    }

    // -----------------------------------------------------------------------------------------
    // STATISTIC OFFICIALITY
    // -----------------------------------------------------------------------------------------

    public static String getStatisticOfficialityName(StatisticOfficialityDto statisticOfficialityDto) {
        if (statisticOfficialityDto != null) {
            return CommonWebUtils.getElementName(statisticOfficialityDto.getIdentifier(), statisticOfficialityDto.getDescription());
        }
        return StringUtils.EMPTY;
    }

    // TODO: FORMAT WILL NOT BE USED in STAT REOSURCES
    // public static LinkedHashMap<String, String> getStatisticalResourceFormatHashMap() {
    // if (statisticalResourceFormatHashMap == null) {
    // statisticalResourceFormatHashMap = new LinkedHashMap<String, String>();
    // statisticalResourceFormatHashMap.put(new String(), new String());
    // for (StatisticalResourceFormatEnum f : StatisticalResourceFormatEnum.values()) {
    // statisticalResourceFormatHashMap.put(f.toString(), getStatisticalResourceFormatName(f));
    // }
    // }
    // return statisticalResourceFormatHashMap;
    // }
    //
    // public static String getStatisticalResourceFormatName(StatisticalResourceFormatEnum statisticalResourceFormatEnum) {
    // return statisticalResourceFormatEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceFormatEnum() + statisticalResourceFormatEnum.name()) : null;
    // }
}
