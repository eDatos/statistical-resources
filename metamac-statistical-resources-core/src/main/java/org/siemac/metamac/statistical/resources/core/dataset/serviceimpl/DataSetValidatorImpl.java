package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;

import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.parser.sdmx.v2_1.ManipulateDataCallback;

public class DataSetValidatorImpl implements DatasetValidator {

    private ManipulateDataCallback manipulateDataCallback = null;

    // Cache info
    // private int numDimensions = 0;

    public DataSetValidatorImpl(ManipulateDataCallback manipulateDataCallback) {
        manipulateDataCallback = manipulateDataCallback;
    }

    @Override
    public void checkObservation(ObservationExtendedDto dataDto, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // Number of dimensions
        // if (manipulateDataCallback.retrieveDimensionsInfo().s != dataDto.getCodesDimension().size()) {
        // TODO excepción
        // }

    }

    private void calculateCacheDataForValidation() {

        // boolean errorInDataStructure = false;
        // errorInDataStructure = errorInDataStructure || (this.dataStructure == null);
        //
        // DataStructureComponentsType dataStructureComponents = this.dataStructure.getDataStructureComponents();
        // errorInDataStructure = errorInDataStructure || (dataStructureComponents == null);
        //
        // DimensionListType dimensionList = dataStructureComponents.getDimensionList();
        // errorInDataStructure = errorInDataStructure || (dimensionList == null);
        //
        // // Num dimensions
        // this.numDimensions = dimensionList.getDimensionsAndMeasureDimensionsAndTimeDimensions().size();
        //
        // if (errorInDataStructure) {
        // // throw exception, el DSD no es válido no se puede llevar a cabo la validación
        // }
    }
}
