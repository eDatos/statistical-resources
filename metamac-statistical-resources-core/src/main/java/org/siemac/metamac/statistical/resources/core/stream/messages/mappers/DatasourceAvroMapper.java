package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;
import org.springframework.beans.factory.annotation.Autowired;

public class DatasourceAvroMapper {

    @Autowired
    private static DatasetVersionRepository datasetVersionRepository;

    protected DatasourceAvroMapper() {
    }

    public static DatasourceAvro do2Avro(Datasource source) {
        DatasourceAvro target = DatasourceAvro.newBuilder()
                .setDatasetVersionUrn(source.getDatasetVersion().getLifeCycleStatisticalResource().getUrn())
                .setDateNextUpdate(source.getDateNextUpdate())
                .setFileName(source.getFilename())
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.do2Avro(source.getIdentifiableStatisticalResource()))
                .setVersion(source.getVersion())
                .build();
        return target;
    }

    public static Datasource avro2Do(DatasourceAvro source) throws MetamacException {
        Datasource target = new Datasource();
        target.setDatasetVersion(retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDateNextUpdate(source.getDateNextUpdate());
        target.setFilename(source.getFileName());
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        target.setVersion(source.getVersion());
        return target;
    }

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


    public static void setDatasetVersionRepository(DatasetVersionRepository repository) {
        DatasourceAvroMapper.datasetVersionRepository = repository;
    }
}
