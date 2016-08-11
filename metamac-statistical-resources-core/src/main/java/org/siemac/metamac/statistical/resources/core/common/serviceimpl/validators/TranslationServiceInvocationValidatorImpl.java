package org.siemac.metamac.statistical.resources.core.common.serviceimpl.validators;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class TranslationServiceInvocationValidatorImpl {

    public static void checkTranslateTime(String time, List<MetamacExceptionItem> exceptions) throws org.siemac.metamac.core.common.exception.MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(time, ServiceExceptionParameters.TIME, exceptions);
    }
}
