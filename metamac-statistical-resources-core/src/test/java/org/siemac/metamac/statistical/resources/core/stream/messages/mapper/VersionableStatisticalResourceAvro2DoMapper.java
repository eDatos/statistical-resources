package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class VersionableStatisticalResourceAvro2DoMapper {

    protected VersionableStatisticalResourceAvro2DoMapper() {
    }

    public static void fillMetadata(VersionableStatisticalResourceAvro source, VersionableStatisticalResource target) {
        NameableStatisticalResourceAvro2DoMapper.fillMetadata(source.getNameableStatisticalResource(), target);
        target.setNextVersionDate(DateTimeAvro2DoMapper.avro2Do(source.getNextVersionDate()));
        target.setValidFrom(DateTimeAvro2DoMapper.avro2Do(source.getValidFrom()));
        target.setValidTo(DateTimeAvro2DoMapper.avro2Do(source.getValidTo()));
        target.setVersionRationale(InternationalStringAvro2DoMapper.avro2Do(source.getVersionRationale()));
        target.setNextVersion(NextVersionTypeEnumAvro2DoMapper.avro2Do(source.getNextVersion()));
        target.setVersionLogic(source.getVersionLogic());
        addVersionRationaleTypes(source, target);
    }

    private static void addVersionRationaleTypes(VersionableStatisticalResourceAvro source, VersionableStatisticalResource target) {
        for (VersionRationaleTypeEnumAvro item : source.getVersionRationaleTypes()) {
            target.addVersionRationaleType(new VersionRationaleType(VersionRationaleTypeEnumAvro2DoMapper.avro2Do(item)));
        }
    }

    public static VersionableStatisticalResource avro2Do(VersionableStatisticalResourceAvro source) {
        VersionableStatisticalResource target = new VersionableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
