package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

public class AvroMapperUtils {

    @Autowired
    private static DatasetVersionRepository datasetVersionRepository;

    protected static DatasetVersion retrieveDatasetVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion target = null;
        try {
            target = datasetVersionRepository.retrieveLastVersion(datasetVersionUrn);
        } catch (MetamacException e) {
            e.printStackTrace();
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, "DatasetVersion of Datasource not found");
        }
        return target;
    }

    protected static void setDatasetVersionRepository(DatasetVersionRepository repository) {
        AvroMapperUtils.datasetVersionRepository = repository;
    }
}
