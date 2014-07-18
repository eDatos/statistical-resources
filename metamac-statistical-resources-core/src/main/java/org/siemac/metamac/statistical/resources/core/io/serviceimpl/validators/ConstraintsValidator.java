package org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.time.TimeSdmx;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Key;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPart;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.KeyPartType;

import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;

public class ConstraintsValidator {

    public static boolean checkObservationAgaintsConstraintsKey(ObservationExtendedDto oservation, Key constraintKey, Map<String, CodeHierarchy> codeHierarchyMap) {

        List<KeyPart> keyParts = constraintKey.getKeyParts().getKeyParts();
        int i = 0;
        boolean match = constraintKey.isIncluded();
        for (CodeDimensionDto codeDimensionDto : oservation.getCodesDimension()) {
            // If there are remaining parts
            if (keyParts.size() > i) {
                KeyPart keyPart = keyParts.get(i);
                // If match a dimension (Not wild card)
                if (codeDimensionDto.getDimensionId().equals(keyPart.getIdentifier())) {
                    i++;
                    if (KeyPartType.NORMAL.equals(keyPart.getType())) {
                        if (keyPart.isCascadeValues()) {
                            // Cascade Value
                            match = checkCodeInCascadeHirarchy(codeDimensionDto, keyPart, codeHierarchyMap);
                        } else if (codeDimensionDto.getCodeDimensionId().equals(keyPart.getValue())) {
                            match = true;
                        } else {
                            match = false;
                        }
                    } else if (KeyPartType.TIME_RANGE.equals(keyPart.getType())) {
                        match = checkTimeRangeValueAgaintsConstraint(codeDimensionDto, keyPart);
                    }
                } else {
                    // If not, it is a partial key. It is a wild card.
                    match = true;
                }
            } else {
                // If not, it is a partial key. It is a wild card.
                match = true;
            }
        }

        if (constraintKey.isIncluded()) {
            return match;
        } else {
            return !match;
        }
    }

    private static boolean checkCodeInCascadeHirarchy(CodeDimensionDto codeDimensionDto, KeyPart keyPart, Map<String, CodeHierarchy> codeHierarchyMap) {
        // For all codes. Until one is found
        boolean match = false;
        for (CodeHierarchy codeHierarchy : codeHierarchyMap.values()) {
            // If the actual code
            if (codeHierarchy.getCode().equals(keyPart.getValue())) {
                if (keyPart.getValue().equals(codeDimensionDto.getCodeDimensionId())) {
                    match = true;
                    break; // Only break if match
                } else {
                    // Check in parent
                    match = checkCodeInCascadeHirarchy(codeHierarchy, codeDimensionDto, codeHierarchyMap);
                    if (match) {
                        break; // Only break if match, if not, continue iterating
                    }
                }
            }
        }
        return match;
    }
    private static boolean checkCodeInCascadeHirarchy(CodeHierarchy codeHierarchy, CodeDimensionDto codeDimensionDto, Map<String, CodeHierarchy> codeHierarchyMap) {
        if (codeHierarchy == null) {
            return false;
        }

        if (codeHierarchy.getCode().equals(codeDimensionDto.getCodeDimensionId())) {
            return true;
        } else {
            // Check in parent recursive
            return checkCodeInCascadeHirarchy(codeHierarchy.getParent(), codeDimensionDto, codeHierarchyMap);
        }
    }

    public static boolean checkTimeRangeValueAgaintsConstraint(CodeDimensionDto codeDimensionDto, KeyPart keyPart) {

        if (keyPart.getBeforePeriod() != null) {
            // Is before period
            TimeSdmx obsValue = new TimeSdmx(codeDimensionDto.getCodeDimensionId());
            TimeSdmx ckValue = new TimeSdmx(keyPart.getBeforePeriod());

            if (keyPart.isBeforePeriodInclusive()) {
                if (obsValue.getEndDateTime().isBefore(ckValue.getStartDateTime()) || obsValue.getEndDateTime().equals(ckValue.getStartDateTime())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (obsValue.getEndDateTime().isBefore(ckValue.getStartDateTime())) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (keyPart.getAfterPeriod() != null) {
            // Is after period
            TimeSdmx obsValue = new TimeSdmx(codeDimensionDto.getCodeDimensionId());
            TimeSdmx ckValue = new TimeSdmx(keyPart.getBeforePeriod());

            if (keyPart.isBeforePeriodInclusive()) {
                if (obsValue.getEndDateTime().isBefore(ckValue.getStartDateTime()) || obsValue.getEndDateTime().equals(ckValue.getStartDateTime())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (obsValue.getEndDateTime().isBefore(ckValue.getStartDateTime())) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if (keyPart.getStartPeriod() != null && keyPart.getEndPeriod() != null) {
            // Is range period
            return SdmxTimeUtils.isValidTimeInInterval(codeDimensionDto.getCodeDimensionId(), keyPart.getStartPeriod(), keyPart.isStartPeriodInclusive(), keyPart.getEndPeriod(),
                    keyPart.isEndPeriodInclusive());
        }

        return false;
    }
}
