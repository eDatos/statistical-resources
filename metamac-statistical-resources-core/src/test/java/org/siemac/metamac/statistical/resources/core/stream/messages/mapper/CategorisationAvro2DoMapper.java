package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;

public class CategorisationAvro2DoMapper {

    protected CategorisationAvro2DoMapper() {
    }

    public static Categorisation avro2Do(CategorisationAvro source) throws MetamacException {
        Categorisation target = new Categorisation();
        target.setCategory(ExternalItemAvro2DoMapper.avro2Do(source.getCategory()));
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedDate(DateTimeAvro2DoMapper.avro2Do(source.getCreatedDate()));
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setLastUpdated(DateTimeAvro2DoMapper.avro2Do(source.getLastUpdated()));
        target.setLastUpdatedBy(source.getLastUpdatedBy());
        target.setMaintainer(ExternalItemAvro2DoMapper.avro2Do(source.getMaintainer()));
        target.setValidFromEffective(DateTimeAvro2DoMapper.avro2Do(source.getValidFromEffective()));
        target.setValidToEffective(DateTimeAvro2DoMapper.avro2Do(source.getValidToEffective()));
        target.setVersion(source.getVersion());
        target.setVersionableStatisticalResource(VersionableStatisticalResourceAvro2DoMapper.avro2Do(source.getVersionableStatisticalResource()));
        return target;
    }

}
