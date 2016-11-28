package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasourceAvro;

public class DatasourceDo2AvroMapper {

    protected DatasourceDo2AvroMapper() {
    }

    public static DatasourceAvro do2Avro(Datasource source) {
        DatasourceAvro target = DatasourceAvro.newBuilder().setDatasetVersionUrn(source.getDatasetVersion().getLifeCycleStatisticalResource().getUrn())
                .setDateNextUpdate(DateTimeDo2AvroMapper.do2Avro(source.getDateNextUpdate())).setFileName(source.getFilename())
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceDo2AvroMapper.do2Avro(source.getIdentifiableStatisticalResource())).build();
        return target;
    }

}
