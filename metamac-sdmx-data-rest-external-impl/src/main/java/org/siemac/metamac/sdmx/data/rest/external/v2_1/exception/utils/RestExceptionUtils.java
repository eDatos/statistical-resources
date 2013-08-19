package org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.utils;

import java.text.MessageFormat;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodedStatusMessageType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.exception.RestServiceExceptionType;

// TODO librería común statistic-sdmx-rest?
public class RestExceptionUtils {

    private final static String LANG_EN = "en";

    /**
     * Builds Error with code and parameters
     */
    public static org.sdmx.resources.sdmxml.schemas.v2_1.message.Error getError(RestServiceExceptionType exceptionType, String... parameters) {
        org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error = new org.sdmx.resources.sdmxml.schemas.v2_1.message.Error();
        error.getErrorMessages().add(getCodedStatusMessageType(exceptionType, parameters));
        return error;
    }

    /**
     * Add error (with code and parameters) to an existing message
     */
    public static org.sdmx.resources.sdmxml.schemas.v2_1.message.Error addError(org.sdmx.resources.sdmxml.schemas.v2_1.message.Error error, RestServiceExceptionType exceptionType,
            String... parameters) {
        error.getErrorMessages().add(getCodedStatusMessageType(exceptionType, parameters));
        return error;
    }

    private static CodedStatusMessageType getCodedStatusMessageType(RestServiceExceptionType exceptionType, String... parameters) {
        CodedStatusMessageType codedStatusMessageType = new CodedStatusMessageType();
        codedStatusMessageType.setCode(exceptionType.getCode());
        codedStatusMessageType.getTexts().add(getTextType(exceptionType, parameters));
        return codedStatusMessageType;
    }

    private static TextType getTextType(RestServiceExceptionType exceptionType, String... parameters) {
        TextType textType = new TextType();
        textType.setLang(LANG_EN);
        textType.setValue(MessageFormat.format(exceptionType.getMessageForReasonType(), (Object[]) parameters));
        return textType;
    }
}