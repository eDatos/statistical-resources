package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;

public class CategorisationAvroMapper {

    protected CategorisationAvroMapper() {
    }

    public static CategorisationAvro do2Avro(Categorisation source) {
        CategorisationAvro target = CategorisationAvro.newBuilder()
                .setCategory(ExternalItemAvroMapper.do2Avro(source.getCategory()))
                .setCreatedBy(source.getCreatedBy())
                .setCreatedDate(source.getCreatedDate())
                .setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())
                .setLastUpdated(source.getLastUpdated())
                .setLastUpdatedBy(source.getLastUpdatedBy())
                .setMaintainer(ExternalItemAvroMapper.do2Avro(source.getMaintainer()))
                .setValidFromEffective(source.getValidFromEffective())
                .setValidToEffective(source.getValidToEffective())
                .setVersion(source.getVersion())
                .setVersionableStatisticalResource(VersionableStatisticalResourceAvroMapper.do2Avro(source.getVersionableStatisticalResource()))
                .build();
        return target;
    }

    public static Categorisation avro2Do(CategorisationAvro source) throws MetamacException {
        Categorisation target = new Categorisation();
        target.setCategory(ExternalItemAvroMapper.avro2Do(source.getCategory()));
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(source.getCreatedDate());
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setLastUpdated(source.getLastUpdated());
        target.setLastUpdatedBy(source.getLastUpdatedBy());
        target.setMaintainer(ExternalItemAvroMapper.avro2Do(source.getMaintainer()));
        target.setValidFromEffective(source.getValidFromEffective());
        target.setValidToEffective(source.getValidToEffective());
        target.setVersion(source.getVersion());
        target.setVersionableStatisticalResource(VersionableStatisticalResourceAvroMapper.avro2Do(source.getVersionableStatisticalResource()));
        return target;
    }

}
