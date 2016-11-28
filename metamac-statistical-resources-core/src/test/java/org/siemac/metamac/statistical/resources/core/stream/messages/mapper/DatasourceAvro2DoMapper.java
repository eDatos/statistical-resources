package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;

public class DatasourceAvro2DoMapper {

    protected DatasourceAvro2DoMapper() {
    }

    public static Datasource avro2Do(DatasourceAvro source) throws MetamacException {
        Datasource target = new Datasource();
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDateNextUpdate(DateTimeAvro2DoMapper.avro2Do(source.getDateNextUpdate()));
        target.setFilename(source.getFileName());
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvro2DoMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        return target;
    }

}
