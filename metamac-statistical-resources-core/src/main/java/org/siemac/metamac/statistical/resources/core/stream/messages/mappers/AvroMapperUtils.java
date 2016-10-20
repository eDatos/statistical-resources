package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

public class AvroMapperUtils {

    @Autowired
    private static DatasetVersionRepository datasetVersionRepository;

    @Autowired
    private static DatasetRepository        datasetRepository;

    protected static DatasetVersion retrieveDatasetVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion target = null;
        try {
            target = datasetVersionRepository.retrieveByUrn(datasetVersionUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, "DatasetVersion of Datasource not found");
        }
        return target;
    }

    public static Dataset retrieveDataset(String datasetUrn) throws MetamacException {
        Dataset target = null;
        try {
            target = datasetRepository.retrieveByUrn(datasetUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_NO_DATA);
        }
        return target;
    }

    protected static void setDatasetVersionRepository(DatasetVersionRepository repository) {
        AvroMapperUtils.datasetVersionRepository = repository;
    }

    protected static void setDatasetRepository(DatasetRepository repository) {
        AvroMapperUtils.datasetRepository = repository;
    }

}
