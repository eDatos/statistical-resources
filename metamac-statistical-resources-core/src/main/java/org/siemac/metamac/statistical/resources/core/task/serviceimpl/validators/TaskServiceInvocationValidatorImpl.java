package org.siemac.metamac.statistical.resources.core.task.serviceimpl.validators;

import java.io.InputStream;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesValidationUtils;

public class TaskServiceInvocationValidatorImpl {

    public static void checkPlannifyImportationDataset(InputStream inputMessage, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(inputMessage, ServiceExceptionParameters.INPUT_MESSAGE, exceptions);
    }

    public static void checkProcessImportationTask(InputStream inputMessage, String jobKey, List<MetamacExceptionItem> exceptions) throws MetamacException {
        StatisticalResourcesValidationUtils.checkParameterRequired(inputMessage, ServiceExceptionParameters.INPUT_MESSAGE, exceptions);
        StatisticalResourcesValidationUtils.checkParameterRequired(jobKey, ServiceExceptionParameters.JOB_KEY, exceptions);
    }
}
