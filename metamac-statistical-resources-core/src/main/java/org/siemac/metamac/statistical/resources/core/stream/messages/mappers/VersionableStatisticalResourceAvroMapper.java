package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class VersionableStatisticalResourceAvroMapper {

    protected VersionableStatisticalResourceAvroMapper() {
    }


    public static void fillMetadata(VersionableStatisticalResourceAvro source, VersionableStatisticalResource target) {
        NameableStatisticalResourceAvroMapper.fillMetadata(source.getNameableStatisticalResource(), target);
        target.setNextVersionDate(source.getNextVersionDate());
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());
        target.setVersionRationale(InternationalStringAvroMapper.avro2Do(source.getVersionRationale()));
        target.setNextVersion(NextVersionTypeEnumAvroMapper.avro2Do(source.getNextVersion()));
        target.setVersionLogic(source.getVersionLogic());
    }

    public static VersionableStatisticalResource avro2Do(VersionableStatisticalResourceAvro source) {
        VersionableStatisticalResource target = new VersionableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }


    public static VersionableStatisticalResourceAvro do2Avro(VersionableStatisticalResource source) {
        VersionableStatisticalResourceAvro target = VersionableStatisticalResourceAvro.newBuilder()
                .setNameableStatisticalResource(NameableStatisticalResourceAvroMapper.do2Avro(source))
                .setNextVersion(NextVersionTypeEnumAvroMapper.do2Avro(source.getNextVersion()))
                .setNextVersionDate(source.getNextVersionDate())
                .setValidFrom(source.getValidFrom())
                .setVersionRationale(InternationalStringAvroMapper.do2Avro(source.getVersionRationale()))
                .setValidTo(source.getValidTo())
                .setVersionLogic(source.getVersionLogic())
                .build();

        return target;
    }

}
