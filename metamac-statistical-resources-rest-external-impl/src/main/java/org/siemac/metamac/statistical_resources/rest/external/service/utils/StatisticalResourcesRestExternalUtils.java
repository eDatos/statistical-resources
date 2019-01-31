package org.siemac.metamac.statistical_resources.rest.external.service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import org.joda.time.DateTime;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatisticalResourcesRestExternalUtils extends StatisticalResourcesRestApiExternalUtils {

    private static final String SPACE = " ";
    private static final String PLUS  = "+";
    
    private StatisticalResourcesRestExternalUtils() {

    }

    private static final Logger  logger               = LoggerFactory.getLogger(StatisticalResourcesRestExternalUtils.class);

    private static final Pattern patternDataSeparator = Pattern.compile(" \\| ");

    /**
     * Throws response error, logging exception
     */
    public static RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static Set<String> parseFieldsParameter(String fieldsParam) {
        Set<String> showFields = new HashSet<>();
        if (fieldsParam != null) {
            Set<String> validFields = new HashSet<>();
            validFields.add(StatisticalResourcesRestExternalConstants.FIELD_EXCLUDE_METADATA);
            validFields.add(StatisticalResourcesRestExternalConstants.FIELD_EXCLUDE_DATA);
            validFields.add(StatisticalResourcesRestExternalConstants.FIELD_INCLUDE_DIMENSION_DESCRIPTION);
            List<String> fieldList = Arrays.asList(fieldsParam.split(","));
            List<String> parsedFieldList = new ArrayList<>();
            for (String field : fieldList) {
                if (field.startsWith(SPACE)) {
                    parsedFieldList.add(field.replaceFirst(SPACE, PLUS));
                } else {
                    parsedFieldList.add(field);
                }
            }
            for (String value : validFields) {
                if (parsedFieldList.contains(value)) {
                    showFields.add(value);
                }
            }
        }
        return showFields;
    }

    public static boolean containsField(Set<String> fields, String field) {
        return fields != null && fields.contains(field);
    }

    public static boolean hasField(String fields, String field) {
        return fields != null && fields.contains(field);
    }

    public static String escapeValueToData(String value) {
        if (value == null) {
            return null;
        }
        return patternDataSeparator.matcher(value).replaceAll("\\\\ | \\\\");
    }

    public static DateTime isDateAfterNowSetNull(DateTime checkValidTo) {
        if (checkValidTo == null || checkValidTo.isAfterNow()) {
            return null;
        } else {
            return checkValidTo;
        }
    }
}
