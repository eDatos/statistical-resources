package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceAvroMapper {

    protected LifecycleStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(LifecycleStatisticalResourceAvro source, LifeCycleStatisticalResource target) throws MetamacException {
        VersionableStatisticalResourceAvroMapper.fillMetadata(source.getVersionableStatisticalResource(), target);
        target.setCreationDate(source.getCreationDate());
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(source.getProductionValidationDate());
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setDiffusionValidationDate(source.getDiffusionValidationDate());
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(source.getRejectValidationDate());
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setPublicationDate(source.getPublicationDate());
        target.setPublicationUser(source.getPublicationUser());
        target.setLastVersion(source.getLastVersion());
        target.setProcStatus(source.getProcStatus());
        target.setReplacesVersion(RelatedResourceAvroMapper.avro2Do(source.getReplacesVersion()));
        target.setMaintainer(ExternalItemAvroMapper.avro2Do(source.getMaintainer()));
    }

    public static LifeCycleStatisticalResource avro2Do(LifecycleStatisticalResourceAvro source) throws MetamacException {
        LifeCycleStatisticalResource target = new LifeCycleStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

    public static LifecycleStatisticalResourceAvro do2Avro(LifeCycleStatisticalResource source) throws MetamacException {
        LifecycleStatisticalResourceAvro target = LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(VersionableStatisticalResourceAvroMapper.do2Avro(source))
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
                .setReplacesVersion(RelatedResourceAvroMapper.do2Avro(source.getReplacesVersion()))
                .setMaintainer(ExternalItemAvroMapper.do2Avro(source.getMaintainer()))
                .build();
        return target;
    }

}
