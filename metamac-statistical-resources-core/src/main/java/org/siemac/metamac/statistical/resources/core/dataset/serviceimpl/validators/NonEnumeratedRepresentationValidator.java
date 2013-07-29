package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl.validators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.BasicComponentDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.SimpleDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.BasicComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.core.common.util.SdmxTimeUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

public class NonEnumeratedRepresentationValidator {

    public static void checkSimpleComponentTextFormatType(SimpleComponentTextFormatType textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkSimpleDataType(textFormat.getTextType(), textFormat, key, value, exceptions);
    }

    public static void checkReportingYearStartDayTextFormatType(ReportingYearStartDayTextFormatType textFormat, String key, String value, List<MetamacExceptionItem> exceptions)
            throws MetamacException {
        checkSimpleDataType(textFormat.getTextType(), textFormat, key, value, exceptions);
    }

    public static void checkTimeTextFormatType(TimeTextFormatType textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkTimeDataType(textFormat.getTextType(), textFormat, key, value, exceptions);
    }

    public static void checkBasicComponentTextFormatType(BasicComponentTextFormatType textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkBasicComponentDataType(textFormat.getTextType(), textFormat, key, value, exceptions);
    }

    /**************************************************************************
     * PRIVATES
     **************************************************************************/

    private static void checkSimpleDataType(SimpleDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkBasicComponentDataType(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkBasicComponentDataType(BasicComponentDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkFacets(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkTimeDataType(TimeDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkSimpleDataType(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkFacets(DataType dataType, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        switch (dataType) {
            // String
            case STRING:
            case ALPHA:
            case ALPHA_NUMERIC:
                if (textFormat instanceof SimpleComponentTextFormatType) {
                    validateText((SimpleComponentTextFormatType) textFormat, key, value, exceptions);
                } else if (textFormat instanceof BasicComponentTextFormatType) {
                    validateText((BasicComponentTextFormatType) textFormat, key, value, exceptions);
                }
                break;
            // Numeric
            case NUMERIC:
            case BIG_INTEGER:
            case INTEGER:
            case LONG:
            case SHORT:
            case COUNT:
            case DECIMAL:
            case FLOAT:
            case DOUBLE:
            case INCLUSIVE_VALUE_RANGE:
            case EXCLUSIVE_VALUE_RANGE:
            case INCREMENTAL:
                if (textFormat instanceof SimpleComponentTextFormatType) {
                    validateNumeric((SimpleComponentTextFormatType) textFormat, key, value, exceptions);
                } else if (textFormat instanceof BasicComponentTextFormatType) {
                    validateNumeric((BasicComponentTextFormatType) textFormat, key, value, exceptions);
                }
                break;
            // Boolean
            case BOOLEAN:
                break;
            case URI:
                // The same as String
                if (textFormat instanceof SimpleComponentTextFormatType) {
                    validateText((SimpleComponentTextFormatType) textFormat, key, value, exceptions);
                } else if (textFormat instanceof BasicComponentTextFormatType) {
                    validateText((BasicComponentTextFormatType) textFormat, key, value, exceptions);
                }
                break;
            // DateTime
            case OBSERVATIONAL_TIME_PERIOD:
            case STANDARD_TIME_PERIOD:
            case BASIC_TIME_PERIOD:
            case GREGORIAN_TIME_PERIOD:
            case GREGORIAN_YEAR:
            case GREGORIAN_YEAR_MONTH:
            case GREGORIAN_DAY:
            case REPORTING_TIME_PERIOD:
            case REPORTING_YEAR:
            case REPORTING_SEMESTER:
            case REPORTING_TRIMESTER:
            case REPORTING_QUARTER:
            case REPORTING_MONTH:
            case REPORTING_WEEK:
            case REPORTING_DAY:
            case DATE_TIME:
            case TIME_RANGE:
            case MONTH:
            case MONTH_DAY:
            case DAY:
            case TIME:
                // The same as String
                if (textFormat instanceof SimpleComponentTextFormatType) {
                    validateTime((SimpleComponentTextFormatType) textFormat, key, value, exceptions);
                } else if (textFormat instanceof TimeTextFormatType) {
                    validateTime((TimeTextFormatType) textFormat, key, value, exceptions);
                } else if (textFormat instanceof BasicComponentTextFormatType) {
                    validateTime((BasicComponentTextFormatType) textFormat, key, value, exceptions);
                }
                break;
            case DURATION:
                // No supported
                break;
            case XHTML:
                // No supported
                break;
            case KEY_VALUES:
                // No supported
                break;
            case IDENTIFIABLE_REFERENCE:
                // No supported
                break;
            case DATA_SET_REFERENCE:
                // No supported
                break;
            case ATTACHMENT_CONSTRAINT_REFERENCE:
                // No supported
                break;
        }
    }

    /**************************************************************************
     * NUMERIC VALIDATION
     **************************************************************************/

    private static void validateNumeric(SimpleComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        BigDecimal bigDecimalValue = new BigDecimal(value);

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence) => Validation not implemented.

        // endValue: decimal (for numeric sequence) => Validation not implemented.

        // interval: decimal (for numeric sequence) => Validation not implemented.

        // timeInterval: duration => Validation not implemented.

        // startTime: BasicTimePeriod (for time range)

        // endTime: BasicTimePeriod (for time range)

        // minValue: decimal (for numeric range)
        validateMinValue(representation.getMinValue(), key, value, bigDecimalValue, exceptions);

        // maxValue: decimal (for numeric range)
        validateMaxValue(representation.getMaxValue(), key, value, bigDecimalValue, exceptions);

        // decimal: Integer (# of digits to right of decimal point)
        validateDecimal(representation.getTextType().value().value(), representation.getDecimals(), key, value, exceptions);

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual: boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }

    private static void validateNumeric(BasicComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        BigDecimal bigDecimalValue = new BigDecimal(value);

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence) => Validation not implemented.

        // endValue: decimal (for numeric sequence) => Validation not implemented.

        // interval: decimal (for numeric sequence) => Validation not implemented.

        // timeInterval: duration => Validation not implemented.

        // startTime: BasicTimePeriod (for time range)

        // endTime: BasicTimePeriod (for time range)

        // minValue: decimal (for numeric range)
        validateMinValue(representation.getMinValue(), key, value, bigDecimalValue, exceptions);

        // maxValue: decimal (for numeric range)
        validateMaxValue(representation.getMaxValue(), key, value, bigDecimalValue, exceptions);

        // decimal: Integer (# of digits to right of decimal point)
        validateDecimal(representation.getTextType().value(), representation.getDecimals(), key, value, exceptions);

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual: boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }

    /**************************************************************************
     * TEXT VALIDATION
     **************************************************************************/

    private static void validateText(SimpleComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence) => for numeric sequence

        // endValue: decimal (for numeric sequence) => for numeric sequence

        // interval: decimal (for numeric sequence) => for numeric sequence

        // timeInterval: duration => for duration

        // startTime: BasicTimePeriod => for time range

        // endTime: BasicTimePeriod => for time range

        // minValue: decimal => for numeric range

        // maxValue: decimal => for numeric range

        // decimal: Integer (# of digits to right of decimal point) => for numerics

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual: boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }

    private static void validateText(BasicComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence) => for numeric sequence

        // endValue: decimal (for numeric sequence) => for numeric sequence

        // interval: decimal (for numeric sequence) => for numeric sequence

        // timeInterval: duration => for duration

        // startTime: BasicTimePeriod => for time range

        // endTime: BasicTimePeriod => for time range

        // minValue: decimal => for numeric range

        // maxValue: decimal => for numeric range

        // decimal: Integer (# of digits to right of decimal point) => for numerics

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual: boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }

    /**************************************************************************
     * TIME VALIDATION
     **************************************************************************/

    private static void validateTime(SimpleComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // Check valid time format.
        if (!SdmxTimeUtils.isObservationalTimePeriod(value)) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_TEMPORAL_PATTERN, value, key));
            return;
        }

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits) => for numeric or text

        // maxLength: positive integer (# of characters/digits) => for numeric or text

        // starValue: decimal (for numeric sequence) => for numeric sequence

        // endValue: decimal (for numeric sequence) => for numeric sequence

        // interval: decimal (for numeric sequence) => for numeric sequence

        // timeInterval: duration => Validation not implemented.

        // startTime = BasicTimePeriod (for time range)
        // endTime = BasicTimePeriod (for time range)
        validateStartTimesAndEndTimes(representation.getStartTimes(), representation.getEndTimes(), key, value, exceptions);

        // minValue: decimal => for numeric range

        // maxValue: decimal => for numeric range

        // decimal: Integer (# of digits to right of decimal point) => for numeric

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual = boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }
    private static void validateTime(BasicComponentTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // Check valid time format.
        if (!SdmxTimeUtils.isObservationalTimePeriod(value)) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_TEMPORAL_PATTERN, value, key));
            return;
        }

        // isSequence: true | false (indicates a sequentially increasing value) => Validation not implemented.

        // minLength: positive integer (# of characters/digits) => for numeric or text

        // maxLength: positive integer (# of characters/digits) => for numeric or text

        // starValue: decimal (for numeric sequence) => for numeric sequence

        // endValue: decimal (for numeric sequence) => for numeric sequence

        // interval: decimal (for numeric sequence) => for numeric sequence

        // timeInterval: duration => Validation not implemented.

        // startTime = BasicTimePeriod (for time range)
        // endTime = BasicTimePeriod (for time range)
        validateStartTimesAndEndTimes(representation.getStartTimes(), representation.getEndTimes(), key, value, exceptions);

        // minValue: decimal => for numeric range

        // maxValue: decimal => for numeric range

        // decimal: Integer (# of digits to right of decimal point) => for numeric

        // pattern: (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual = boolean (for specifying text can occur in more than one language) => No for SimpleComponentTextFormatType
    }

    private static void validateTime(TimeTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // Check valid time format.
        if (!SdmxTimeUtils.isObservationalTimePeriod(value)) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_TEMPORAL_PATTERN, value, key));
            return;
        }

        // isSequence: true | false (indicates a sequentially increasing value) => No for TimeTextFormatType

        // minLength: positive integer (# of characters/digits) => No for TimeTextFormatType

        // maxLength: positive integer (# of characters/digits) => No for TimeTextFormatType

        // starValue: decimal (for numeric sequence) => No for TimeTextFormatType

        // endValue: decimal (for numeric sequence) => No for TimeTextFormatType

        // interval: decimal (for numeric sequence) => No for TimeTextFormatType

        // timeInterval: duration = No for TimeTextFormatType

        // startTime = BasicTimePeriod (for time range)
        // endTime = BasicTimePeriod (for time range)
        validateStartTimesAndEndTimes(representation.getStartTimes(), representation.getEndTimes(), key, value, exceptions);

        // minValue = decimal >= No for TimeTextFormatType

        // maxValue = decimal => No for TimeTextFormatType

        // decimal = Integer (# of digits to right of decimal point) => No for TimeTextFormatType

        // pattern = (a regular expression, as per W3C XML Schema) => No for TimeTextFormatType

        // isMultiLingual = boolean (for specifying text can occur in more than one language) => No for TimeTextFormatType

    }

    /**************************************************************************
     * VALIDATORS
     **************************************************************************/

    private static void validateMinLength(BigInteger representationMinLength, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (representationMinLength != null && representationMinLength.toString().length() > value.length()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MINLENGTH, value, representationMinLength.toString().length(), key));
        }
    }

    private static void validateMaxLength(BigInteger representationMaxLength, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (representationMaxLength != null && representationMaxLength.toString().length() > value.length()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MAXLENGTH, value, representationMaxLength.toString().length(), key));
        }
    }

    private static void validateMinValue(BigInteger representationMinValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMinValue != null) {
            BigDecimal representationMinValueDecimal = new BigDecimal(representationMinValue);
            if (representationMinValueDecimal.compareTo(bigDecimalValue) == 1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MINVALUE, value, representationMinValueDecimal.toString(), key));
            }
        }
    }

    private static void validateMinValue(BigDecimal representationMinValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMinValue != null) {
            if (representationMinValue.compareTo(bigDecimalValue) == 1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MINVALUE, value, representationMinValue.toString(), key));
            }
        }
    }

    private static void validateMaxValue(BigInteger representationMaxValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMaxValue != null) {
            BigDecimal representationMaxValueDecimal = new BigDecimal(representationMaxValue);
            if (representationMaxValueDecimal.compareTo(bigDecimalValue) == -1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MAXVALUE, value, representationMaxValueDecimal.toString(), key));
            }
        }
    }

    private static void validateMaxValue(BigDecimal representationMaxValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMaxValue != null) {
            if (representationMaxValue.compareTo(bigDecimalValue) == -1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MAXVALUE, value, representationMaxValue.toString(), key));
            }
        }
    }

    private static void validateDecimal(DataType dataType, BigInteger representationDecimals, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (DataType.DECIMAL.equals(dataType) || DataType.FLOAT.equals(dataType) || DataType.DOUBLE.equals(dataType)) {
            String subStrAfterDecimalPoint = StringUtils.substringAfter(value, ".");
            int numberPlacesOfDecimal = (subStrAfterDecimalPoint == null) ? 0 : subStrAfterDecimalPoint.length();
            if (representationDecimals.compareTo(new BigInteger(String.valueOf(numberPlacesOfDecimal))) != 0) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_DECIMAL, value, representationDecimals.toString(), key));
            }
        }
    }

    private static void validatePattern(String representationPattern, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (StringUtils.isNotBlank(representationPattern)) {
            Pattern p = Pattern.compile(representationPattern);
            Matcher m = p.matcher(value);
            if (!m.matches()) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_PATTERN, value, representationPattern, key));
            }
        }
    }

    private static void validateStartTimesAndEndTimes(List<String> representationStartTimes, List<String> representationEndTimes, String key, String value, List<MetamacExceptionItem> exceptions) {
        // startTime: BasicTimePeriod (for time range)
        DateTime[] valueDateTimes = SdmxTimeUtils.calculatePeriodStart(value);
        if (!representationStartTimes.isEmpty()) {
            String flattenRepresentation = CoreCommonUtil.flattenListString(representationStartTimes);
            DateTime[] representationDateTimes = SdmxTimeUtils.calculatePeriodStart(flattenRepresentation);
            if (representationDateTimes == null || representationDateTimes[0] == null || representationDateTimes[0].isAfter(valueDateTimes[0])) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_STARTTIMES, value, flattenRepresentation, key));
            }
        }

        // endTime: BasicTimePeriod (for time range)
        if (!representationEndTimes.isEmpty()) {
            String flattenRepresentation = CoreCommonUtil.flattenListString(representationEndTimes);
            DateTime[] representationDateTimes = SdmxTimeUtils.calculatePeriodStart(flattenRepresentation);
            if (representationDateTimes == null || representationDateTimes[0] == null || representationDateTimes[0].isBefore(valueDateTimes[0])) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_ENDTIMES, value, flattenRepresentation, key));
            }
        }
    }
}
