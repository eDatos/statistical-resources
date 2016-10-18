package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class VersionableStatisticalResourceDo2Avro {

    protected VersionableStatisticalResourceDo2Avro() {
    }

    
    public static VersionableStatisticalResourceAvro versionableStatisticalResourceDo2Avro(VersionableStatisticalResource source) {
        VersionableStatisticalResourceAvro target = VersionableStatisticalResourceAvro.newBuilder()
                .setNameableStatisticalResource(NameableStatisticalResourceDo2Avro.nameableStatisticalResourceDo2Avro(source))
                .setNextVersion(source.getNextVersion())
                .setNextVersionDate(source.getNextVersionDate())
                .setValidFrom(source.getValidFrom())
                .setVersionRationale(InternationalStringDo2Avro.internationalString2Avro(source.getVersionRationale()))
                .setValidTo(source.getValidTo())
                .setVersionLogic(source.getVersionLogic())
                .build();
        
        return target;
    }
}
