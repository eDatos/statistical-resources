package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

public class CommonUtils {

    private static String                               metamacPortalBaseUrl;
    private static Map<String, StatisticOfficialityDto> statisticOfficialitiesMap;

    // -----------------------------------------------------------------------------------------
    // PROC STATUS
    // -----------------------------------------------------------------------------------------

    public static String getProcStatusName(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        return lifeCycleStatisticalResourceDto != null ? getProcStatusName(lifeCycleStatisticalResourceDto.getProcStatus()) : null;
    }

    public static String getProcStatusName(ProcStatusEnum procStatusEnum) {
        return procStatusEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceProcStatusEnum() + procStatusEnum.getName()) : null;
    }

    public static LinkedHashMap<String, String> getProcStatusHashMap() {
        LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
        valueMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
        for (ProcStatusEnum procStatusEnum : ProcStatusEnum.values()) {
            valueMap.put(procStatusEnum.name(), getProcStatusName(procStatusEnum));
        }
        return valueMap;
    }

    public static ProcStatusEnum getProcStatusEnum(String procStatusName) {
        if (!StringUtils.isBlank(procStatusName)) {
            try {
                return ProcStatusEnum.valueOf(procStatusName);
            } catch (Exception e) {
            }
        }
        return null;
    }

    // -----------------------------------------------------------------------------------------
    // QUERY TYPE
    // -----------------------------------------------------------------------------------------

    public static String getQueryTypeName(QueryVersionDto queryDto) {
        return queryDto != null && queryDto.getType() != null ? getQueryTypeName(queryDto.getType()) : null;
    }

    public static QueryTypeEnum getQueryTypeEnum(String value) {
        if (!StringUtils.isBlank(value)) {
            try {
                return QueryTypeEnum.valueOf(value);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static LinkedHashMap<String, String> getQueryTypeHashMap() {
        LinkedHashMap<String, String> queryTypeHashMap = new LinkedHashMap<String, String>();
        queryTypeHashMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
        for (QueryTypeEnum queryTypeEnum : QueryTypeEnum.values()) {
            queryTypeHashMap.put(queryTypeEnum.name(), getQueryTypeName(queryTypeEnum));
        }
        return queryTypeHashMap;
    }

    private static String getQueryTypeName(QueryTypeEnum queryType) {
        return queryType != null ? getCoreMessages().getString(getCoreMessages().queryTypeEnum() + queryType.getName()) : null;
    }

    // -----------------------------------------------------------------------------------------
    // QUERY STATUS
    // -----------------------------------------------------------------------------------------

    public static LinkedHashMap<String, String> getQueryStatusHashMap() {
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<String, String>();
        hashMap.put(StringUtils.EMPTY, StringUtils.EMPTY);
        for (QueryStatusEnum queryStatusEnum : QueryStatusEnum.values()) {
            hashMap.put(queryStatusEnum.name(), getQueryStatusName(queryStatusEnum));
        }
        return hashMap;
    }

    public static QueryStatusEnum getQueryStatusEnum(String value) {
        if (!StringUtils.isBlank(value)) {
            try {
                return QueryStatusEnum.valueOf(value);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static String getQueryStatusName(QueryVersionDto queryDto) {
        return queryDto != null ? getQueryStatusName(queryDto.getStatus()) : null;
    }

    public static String getQueryStatusName(QueryStatusEnum queryStatusEnum) {
        return queryStatusEnum != null ? getCoreMessages().getString(getCoreMessages().queryStatusEnum() + queryStatusEnum.name()) : null;
    }

    // -----------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE TYPE
    // -----------------------------------------------------------------------------------------

    public static LinkedHashMap<String, String> getStatisticalResourceTypeHashMap() {
        LinkedHashMap<String, String> statisticalResourceTypeHashMap = new LinkedHashMap<String, String>();
        statisticalResourceTypeHashMap.put(new String(), new String());
        for (StatisticalResourceTypeEnum c : StatisticalResourceTypeEnum.values()) {
            String value = getCoreMessages().getString(getCoreMessages().statisticalResourceTypeEnum() + c.getName());
            statisticalResourceTypeHashMap.put(c.toString(), value);
        }
        return statisticalResourceTypeHashMap;
    }

    public static LinkedHashMap<String, String> getStatisticalResourceTypeThatCanBeAddIntoACubeHashMap() {
        LinkedHashMap<String, String> statisticalResourceTypeHashMap = new LinkedHashMap<String, String>();
        statisticalResourceTypeHashMap.put(StatisticalResourceTypeEnum.DATASET.name(), getCoreMessages().statisticalResourceTypeEnumDATASET());
        statisticalResourceTypeHashMap.put(StatisticalResourceTypeEnum.QUERY.name(), getCoreMessages().statisticalResourceTypeEnumQUERY());
        return statisticalResourceTypeHashMap;
    }

    public static String getStatisticalResourceTypeName(StatisticalResourceTypeEnum statisticalResourceTypeEnum) {
        return statisticalResourceTypeEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceTypeEnum() + statisticalResourceTypeEnum.name()) : null;
    }

    // -----------------------------------------------------------------------------------------
    // STATISTICAL RESOURCE VERSION RATIONALE TYPE
    // -----------------------------------------------------------------------------------------

    public static LinkedHashMap<String, String> getVersionRationaleTypeHashMap() {
        LinkedHashMap<String, String> statisticalResourceVersionRationaleTypeHashMap = new LinkedHashMap<String, String>();
        statisticalResourceVersionRationaleTypeHashMap.put(new String(), new String());
        for (VersionRationaleTypeEnum v : VersionRationaleTypeEnum.values()) {
            statisticalResourceVersionRationaleTypeHashMap.put(v.toString(), getVersionRationaleTypeName(v));
        }
        return statisticalResourceVersionRationaleTypeHashMap;
    }

    public static String getVersionRationaleTypeName(VersionRationaleTypeEnum versionRationaleType) {
        return versionRationaleType != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceVersionRationaleTypeEnum() + versionRationaleType.name()) : null;
    }

    public static String getStatisticalResourceVersionRationaleTypeNames(List<VersionRationaleTypeDto> versionRationaleTypeDtos) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < versionRationaleTypeDtos.size(); i++) {
            builder.append(i != 0 ? ",  " : StringUtils.EMPTY).append(getVersionRationaleTypeName(versionRationaleTypeDtos.get(i).getValue()));
        }
        return builder.toString();
    }

    public static List<VersionRationaleTypeDto> getVersionRationaleTypeValues() {
        List<VersionRationaleTypeDto> versionRationaleTypeDtos = new ArrayList<VersionRationaleTypeDto>();
        for (VersionRationaleTypeEnum value : VersionRationaleTypeEnum.values()) {
            VersionRationaleTypeDto versionRationaleTypeDto = new VersionRationaleTypeDto();
            versionRationaleTypeDto.setValue(value);
            versionRationaleTypeDtos.add(versionRationaleTypeDto);
        }
        return versionRationaleTypeDtos;
    }

    // -----------------------------------------------------------------------------------------
    // VERSION TYPE
    // -----------------------------------------------------------------------------------------

    public static LinkedHashMap<String, String> getVersionTypeHashMap() {
        LinkedHashMap<String, String> versionTypeHashMap = new LinkedHashMap<String, String>();
        versionTypeHashMap.put(new String(), new String());
        for (VersionTypeEnum v : VersionTypeEnum.values()) {
            versionTypeHashMap.put(v.toString(), getVersionTypeName(v));
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
        LinkedHashMap<String, String> statisticalResourceNextVersionHashMap = new LinkedHashMap<String, String>();
        statisticalResourceNextVersionHashMap.put(new String(), new String());
        for (NextVersionTypeEnum s : NextVersionTypeEnum.values()) {
            statisticalResourceNextVersionHashMap.put(s.name(), getStatisticalResourceNextVersionName(s));
        }
        return statisticalResourceNextVersionHashMap;
    }

    public static String getStatisticalResourceNextVersionName(NextVersionTypeEnum statisticalResourceNextVersionEnum) {
        return statisticalResourceNextVersionEnum != null ? getCoreMessages().getString(getCoreMessages().statisticalResourceNextVersionEnum() + statisticalResourceNextVersionEnum.name()) : null;
    }

    public static NextVersionTypeEnum getNextVersionTypeEnum(String nextVersionTypeName) {
        if (!StringUtils.isBlank(nextVersionTypeName)) {
            try {
                return NextVersionTypeEnum.valueOf(nextVersionTypeName);
            } catch (Exception e) {
            }
        }
        return null;
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

    public static LinkedHashMap<String, String> getStatisticOfficialityHashMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put(new String(), new String());
        if (statisticOfficialitiesMap != null) {
            for (Entry<String, StatisticOfficialityDto> entry : statisticOfficialitiesMap.entrySet()) {
                map.put(entry.getKey(), getStatisticOfficialityName(entry.getValue()));
            }
        }
        return map;
    }

    public static StatisticOfficialityDto getStatisticOfficialityByIdentifier(String identifier) {
        return statisticOfficialitiesMap.get(identifier);
    }

    public static void setStatisticOfficialities(List<StatisticOfficialityDto> statisticOfficialities) {
        CommonUtils.statisticOfficialitiesMap = new HashMap<String, StatisticOfficialityDto>();
        for (StatisticOfficialityDto officiality : statisticOfficialities) {
            CommonUtils.statisticOfficialitiesMap.put(officiality.getIdentifier(), officiality);
        }
    }

    // -----------------------------------------------------------------------------------------
    // TEMPORAL CODES
    // -----------------------------------------------------------------------------------------

    public static String getTemporalCodeName(TemporalCodeDto dto) {
        if (dto != null) {
            return CommonWebUtils.getElementName(dto.getIdentifier(), dto.getTitle());
        }
        return StringUtils.EMPTY;
    }

    public static String getTemporalCodesNames(List<TemporalCodeDto> dtos) {
        if (dtos != null) {
            List<String> names = new ArrayList<String>();
            for (TemporalCodeDto dto : dtos) {
                names.add(getTemporalCodeName(dto));
            }
            return CommonWebUtils.getStringListToString(names);
        }
        return StringUtils.EMPTY;
    }

    // -----------------------------------------------------------------------------------------
    // METAMAC PORTAL BASE URL
    // -----------------------------------------------------------------------------------------

    public static void setMetamacPortalBaseUrl(String baseUrl) {
        metamacPortalBaseUrl = baseUrl;
    }

    public static String getMetamacPortalBaseUrl() {
        return metamacPortalBaseUrl;
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

    //
    // COMMON UTILITIES
    //

    public static boolean isResourceInProductionValidationOrGreaterProcStatus(ProcStatusEnum procStatus) {
        return ProcStatusEnum.PRODUCTION_VALIDATION.equals(procStatus) || ProcStatusEnum.DIFFUSION_VALIDATION.equals(procStatus) || ProcStatusEnum.PUBLISHED.equals(procStatus);
    }

    public static boolean isUrnFromSelectedStatisticalOperation(String urn) {
        if (StatisticalResourcesDefaults.selectedStatisticalOperation != null) {
            if (!StringUtils.isBlank(urn)) {
                return StringUtils.equals(urn, StatisticalResourcesDefaults.selectedStatisticalOperation.getUrn());
            }
        }
        return false;
    }

    public static String generateStatisticalOperationUrn(String operationCode) {
        return UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
    }

    public static String generateDatasetUrn(String datasetIdentifier) {
        return UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetIdentifier);
    }
}
