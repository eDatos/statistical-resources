package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.constants.shared.SDMXCommonRegExpV2_1;
import org.siemac.metamac.web.common.client.MetamacWebCommon;

import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.Validator;

public class SemanticIdentifiersUtils {

    private SemanticIdentifiersUtils() {
    }

    // Query

    private static boolean isQueryIdentifier(String value) {
        return value != null ? value.matches(SDMXCommonRegExpV2_1.METAMAC_ID) : false;
    }

    public static CustomValidator getQueryIdentifierCustomValidator() {
        CustomValidator validator = new CustomValidator() {

            @Override
            protected boolean condition(Object value) {
                return value != null ? isQueryIdentifier(value.toString()) : false;
            }
        };
        validator.setErrorMessage(MetamacWebCommon.getMessages().errorSemanticIdentifierFormat());
        return validator;
    }

    private static boolean isMultidatasetCubeIdentifierIdentifier(String value) {
        return value != null ? value.matches(SDMXCommonRegExpV2_1.NCNAMEIDTYPE) : false;
    }

    public static Validator getMultidatasetCubeIdentifierValidator() {
        CustomValidator validator = new CustomValidator() {

            @Override
            protected boolean condition(Object value) {
                return value != null ? isMultidatasetCubeIdentifierIdentifier(value.toString()) : false;
            }

        };
        validator.setErrorMessage(MetamacWebCommon.getMessages().errorSemanticIdentifierFormat());
        return validator;
    }
}
