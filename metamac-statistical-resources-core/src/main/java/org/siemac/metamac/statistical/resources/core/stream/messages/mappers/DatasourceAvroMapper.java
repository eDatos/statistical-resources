package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;

public class DatasourceAvroMapper {

    protected DatasourceAvroMapper() {
    }

    public static DatasourceAvro do2Avro(Datasource source) {
        DatasourceAvro target = DatasourceAvro.newBuilder()
                .setDatasetVersionUrn(source.getDatasetVersion().getLifeCycleStatisticalResource().getUrn())
                .setDateNextUpdate(DatetimeAvroMapper.do2Avro(source.getDateNextUpdate()))
                .setFileName(source.getFilename())
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.do2Avro(source.getIdentifiableStatisticalResource()))
                .setVersion(source.getVersion())
                .build();
        return target;
    }

    public static Datasource avro2Do(DatasourceAvro source) throws MetamacException {
        Datasource target = new Datasource();
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDateNextUpdate(DatetimeAvroMapper.avro2Do(source.getDateNextUpdate()));
        target.setFilename(source.getFileName());
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        target.setVersion(source.getVersion());
        return target;
    }

}
