package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.BasicComponentDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.DataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.SimpleDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TimeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodeDataType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodededTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
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

    public static void checkCodededTextFormatType(CodeDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkSimpleDataType(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkSimpleDataType(SimpleDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkBasicComponentDataType(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkBasicComponentDataType(BasicComponentDataType type, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        checkDataType(type.value(), textFormat, key, value, exceptions);
    }

    private static void checkTimeDataType(TimeDataType timeDataType, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // TODO time text format validation
        throw new UnsupportedOperationException();
    }

    private static void checkDataType(DataType dataType, Object textFormat, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        switch (dataType) {
            // String
            case STRING:
            case ALPHA:
            case ALPHA_NUMERIC:
                if (textFormat instanceof CodededTextFormatType) {
                    validateText((CodededTextFormatType) textFormat, key, value, exceptions);
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
                if (textFormat instanceof CodededTextFormatType) {
                    validateNumeric((CodededTextFormatType) textFormat, key, value, exceptions);
                }
                break;
            // Boolean
            case BOOLEAN:
                break;
            case URI:
                // The same as String
                if (textFormat instanceof CodededTextFormatType) {
                    validateText((CodededTextFormatType) textFormat, key, value, exceptions);
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
                if (textFormat instanceof CodededTextFormatType) {
                    validateTime((CodededTextFormatType) textFormat, key, value, exceptions);
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

    private static void validateNumeric(CodededTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        BigDecimal bigDecimalValue = new BigDecimal(value);

        // isSequence: true | false (indicates a sequentially increasing value)
        // == Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence)
        // == Validation not implemented.

        // endValue: decimal (for numeric sequence)
        // == Validation not implemented.

        // interval: decimal (for numeric sequence)
        // == Validation not implemented.

        // timeInterval: duration
        // == Validation not implemented.

        // startTime = BasicTimePeriod (for time range)

        // endTime = BasicTimePeriod (for time range)

        // minValue = decimal (for numeric range)
        validateMinValue(representation.getMinValue(), key, value, bigDecimalValue, exceptions);

        // maxValue = decimal (for numeric range)
        validateMaxValue(representation.getMaxValue(), key, value, bigDecimalValue, exceptions);

        // decimal = Integer (# of digits to right of decimal point)
        // == No for CodededTextFormatType

        // pattern = (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual = boolean (for specifying text can occur in more than one language)
        // == No for CodededTextFormatType

    }

    private static void validateText(CodededTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // isSequence: true | false (indicates a sequentially increasing value)

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence)
        // == for numeric sequence

        // endValue: decimal (for numeric sequence)
        // == for numeric sequence

        // interval: decimal (for numeric sequence)
        // == for numeric sequence

        // timeInterval: duration
        // == for duration

        // startTime = BasicTimePeriod
        // == for time range

        // endTime = BasicTimePeriod
        // == for time range

        // minValue = decimal
        // == for numeric range

        // maxValue = decimal
        // == for numeric range

        // decimal = Integer (# of digits to right of decimal point)
        // == No for CodededTextFormatType

        // pattern = (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual = boolean (for specifying text can occur in more than one language)
        // == No for CodededTextFormatType

    }

    private static void validateTime(CodededTextFormatType representation, String key, String value, List<MetamacExceptionItem> exceptions) throws MetamacException {

        // Check valid time format.
        if (!SdmxTimeUtils.isObservationalTimePeriod(value)) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_TEMPORAL_PATTERN, key + ": " + value));
            return;
        }

        // BigDecimal bigDecimalValue = new BigDecimal(value);

        // isSequence: true | false (indicates a sequentially increasing value)
        // == Validation not implemented.

        // minLength: positive integer (# of characters/digits)
        validateMinLength(representation.getMinLength(), key, value, exceptions);

        // maxLength: positive integer (# of characters/digits)
        validateMaxLength(representation.getMaxLength(), key, value, exceptions);

        // starValue: decimal (for numeric sequence)
        // == for numeric sequence

        // endValue: decimal (for numeric sequence)
        // == for numeric sequence

        // interval: decimal (for numeric sequence)
        // == for numeric sequence

        // timeInterval: duration
        // == Validation not implemented.

        // startTime = BasicTimePeriod (for time range)
        if (!representation.getStartTimes().isEmpty()) {
            // TODO validate starTimes, equiere algoritmo de conversión
        }

        // endTime = BasicTimePeriod (for time range)
        if (!representation.getEndTimes().isEmpty()) {
            // TODO validate entimes, requiere algoritmo de conversión
        }

        // minValue = decimal
        // == for numeric range

        // maxValue = decimal
        // == for numeric range

        // decimal = Integer (# of digits to right of decimal point)
        // == No for CodededTextFormatType

        // pattern = (a regular expression, as per W3C XML Schema)
        validatePattern(representation.getPattern(), key, value, exceptions);

        // isMultiLingual = boolean (for specifying text can occur in more than one language)
        // == No for CodededTextFormatType

    }

    private static void validateMinLength(BigInteger representationMinLength, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (representationMinLength != null && representationMinLength.toString().length() > value.length()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MINLENGTH, key + ": " + value, representationMinLength.toString().length()));
        }
    }

    private static void validateMaxLength(BigInteger representationMaxLength, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (representationMaxLength != null && representationMaxLength.toString().length() > value.length()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MAXLENGTH, key + ": " + value, representationMaxLength.toString().length()));
        }
    }

    private static void validateMinValue(BigInteger representationMinValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMinValue != null) {
            BigDecimal representationMinValueDecimal = new BigDecimal(representationMinValue);
            if (representationMinValueDecimal.compareTo(bigDecimalValue) == 1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MINVALUE, key + ": " + value, representationMinValueDecimal.toString()));
            }
        }
    }

    private static void validateMaxValue(BigInteger representationMaxValue, String key, String value, BigDecimal bigDecimalValue, List<MetamacExceptionItem> exceptions) {
        if (representationMaxValue != null) {
            BigDecimal representationMaxValueDecimal = new BigDecimal(representationMaxValue);
            if (representationMaxValueDecimal.compareTo(bigDecimalValue) == -1) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_MAXVALUE, key + ": " + value, representationMaxValueDecimal.toString()));
            }
        }
    }

    protected static void validatePattern(String representationPattern, String key, String value, List<MetamacExceptionItem> exceptions) {
        if (StringUtils.isNotBlank(representationPattern)) {
            Pattern p = Pattern.compile(representationPattern);
            Matcher m = p.matcher(value);
            if (!m.matches()) {
                exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_OBSERVATION_NONENUMERATED_PATTERN, key + ": " + value, representationPattern));
            }
        }
    }
}
