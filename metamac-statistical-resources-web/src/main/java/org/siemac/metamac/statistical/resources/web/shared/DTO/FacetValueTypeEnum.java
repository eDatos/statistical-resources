package org.siemac.metamac.statistical.resources.web.shared.DTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum FacetValueTypeEnum implements Serializable {
    STRING_FVT("String"),
    ALPHA_FVT("Alpha"),
    ALPHA_NUMERIC_FVT("AlphaNumeric"),
    NUMERIC_FVT("Numeric"),
    BIG_INTEGER_FVT("BigInteger"),
    INTEGER_FVT("Integer"),
    LONG_FVT("Long"),
    SHORT_FVT("Short"),
    DECIMAL_FVT("Decimal"),
    FLOAT_FVT("Float"),
    DOUBLE_FVT("Double"),
    BOOLEAN_FVT("Boolean"),
    URI_FVT("URI"),
    COUNT_FVT("Count"),
    INCLUSIVE_VALUE_RANGE_FVT("InclusiveValueRange"),
    EXCLUSIVE_VALUE_RANGE_FVT("ExclusiveValueRange"),
    INCREMENTAL_FVT("Incremental"),
    OBSERVATIONAL_TIME_PERIOD_FVT("ObservationalTimePeriod"),
    STANDARD_TIME_PERIOD_FVT("StandardTimePeriod"),
    BASIC_TIME_PERIOD_FVT("BasicTimePeriod"),
    GREGORIAN_TIME_PERIOD_FVT("GregorianTimePeriod"),
    GREGORIAN_YEAR_FVT("GregorianYear"),
    GREGORIAN_YEAR_MONTH_FVT("GregorianYearMonth"),
    GREGORIAN_DAY_FVT("GregorianDay"),
    REPORTING_TIME_PERIOD_FVT("ReportingTimePeriod"),
    REPORTING_YEAR_FVT("ReportingYear"),
    REPORTING_SEMESTER_FVT("ReportingSemester"),
    REPORTING_TRIMESTER_FVT("ReportingTrimester"),
    REPORTING_QUARTER_FVT("ReportingQuarter"),
    REPORTING_MONTH_FVT("ReportingMonth"),
    REPORTING_WEEK_FVT("ReportingWeek"),
    REPORTING_DAY_FVT("ReportingDay"),
    DATE_TIME_FVT("DateTime"),
    TIMES_RANGE_FVT("TimeRange"),
    MONTH_FVT("Month"),
    MONTH_DAY_FVT("MonthDay"),
    DAY_FVT("Day"),
    TIME_FVT("Time"),
    DURATION_FVT("Duration"),
    XHTML_FVT("XHTML"),
    KEY_VALUES_FVT("KeyValues"),
    IDENTIFIABLE_REFERENCE_FVT("IdentifiableReference"),
    DATASET_REFERENCE_FVT("DataSetReference"),
    ATTACHMENT_CONSTRAINT_REFERENCE_FVT("AttachmentConstraintReference");

    private static Map<String, FacetValueTypeEnum> identifierMap = new HashMap<String, FacetValueTypeEnum>();

    static {
        for (FacetValueTypeEnum value : FacetValueTypeEnum.values()) {
            identifierMap.put(value.getValue(), value);
        }
    }

    private String                                 value;

    /**
     */
    private FacetValueTypeEnum(String value) {
        this.value = value;
    }

    public static FacetValueTypeEnum fromValue(String value) {
        FacetValueTypeEnum result = identifierMap.get(value);
        if (result == null) {
            throw new IllegalArgumentException("No FacetValueTypeEnum for value: " + value);
        }
        return result;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name();
    }
}
