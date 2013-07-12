package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;

import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;

public interface DatasetValidator {

    void checkObservation(ObservationExtendedDto dataDto, List<MetamacExceptionItem> exceptions) throws MetamacException;

}
