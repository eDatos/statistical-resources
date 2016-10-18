package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceDo2Avro {

    protected LifecycleStatisticalResourceDo2Avro() {
    }

    public static LifecycleStatisticalResourceAvro lifecycleStatisticalResourceDo2Avro(LifeCycleStatisticalResource source) {
        LifecycleStatisticalResourceAvro target = LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(VersionableStatisticalResourceDo2Avro.versionableStatisticalResourceDo2Avro(source))
                .setCreationDate(source.getCreationDate())
                .setCreationUser(source.getCreationUser())
                .setProductionValidationDate(source.getProductionValidationDate())
                .setProductionValidationUser(source.getProductionValidationUser())
                .setDiffusionValidationDate(source.getDiffusionValidationDate())
                .setDiffusionValidationUser(source.getDiffusionValidationUser())
                .setRejectValidationDate(source.getRejectValidationDate())
                .setRejectValidationUser(source.getRejectValidationUser())
                .setPublicationDate(source.getPublicationDate())
                .setPublicationUser(source.getPublicationUser())
                .setLastVersion(source.getLastVersion())
                .setProcStatus(source.getProcStatus())
                .setReplacesVersion(RelatedResourceDo2Avro.relatedResourceDo2Avro(source.getReplacesVersion()))
                .setMaintainer(ExternalItemDo2Avro.externalItemDo2Avro(source.getMaintainer()))
                .build();
        return target;
    }

}
