package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceAvroMapper {

    protected LifecycleStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(LifecycleStatisticalResourceAvro source, LifeCycleStatisticalResource target) throws MetamacException {
        VersionableStatisticalResourceAvroMapper.fillMetadata(source.getVersionableStatisticalResource(), target);
        target.setCreationDate(DateTimeAvroMapper.avro2Do(source.getCreationDate()));
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(DateTimeAvroMapper.avro2Do(source.getProductionValidationDate()));
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setDiffusionValidationDate(DateTimeAvroMapper.avro2Do(source.getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(DateTimeAvroMapper.avro2Do(source.getRejectValidationDate()));
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setPublicationDate(DateTimeAvroMapper.avro2Do(source.getPublicationDate()));
        target.setPublicationUser(source.getPublicationUser());
        target.setLastVersion(source.getLastVersion());
        target.setProcStatus(ProcStatusEnumAvroMapper.avro2Do(source.getProcStatus()));
        target.setReplacesVersion(RelatedResourceAvroMapper.avro2Do(source.getReplacesVersion()));
        target.setMaintainer(ExternalItemAvroMapper.avro2Do(source.getMaintainer()));
    }

    public static LifeCycleStatisticalResource avro2Do(LifecycleStatisticalResourceAvro source) throws MetamacException {
        LifeCycleStatisticalResource target = new LifeCycleStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

    public static LifecycleStatisticalResourceAvro do2Avro(LifeCycleStatisticalResource source) throws MetamacException {
        LifecycleStatisticalResourceAvro target = null;
        if (source != null) {
            target = LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(VersionableStatisticalResourceAvroMapper.do2Avro(source))
                .setCreationDate(DateTimeAvroMapper.do2Avro(source.getCreationDate()))
                .setCreationUser(source.getCreationUser())
                .setProductionValidationDate(DateTimeAvroMapper.do2Avro(source.getProductionValidationDate()))
                .setProductionValidationUser(source.getProductionValidationUser())
                .setDiffusionValidationDate(DateTimeAvroMapper.do2Avro(source.getDiffusionValidationDate()))
                .setDiffusionValidationUser(source.getDiffusionValidationUser())
                .setRejectValidationDate(DateTimeAvroMapper.do2Avro(source.getRejectValidationDate()))
                .setRejectValidationUser(source.getRejectValidationUser())
                .setPublicationDate(DateTimeAvroMapper.do2Avro(source.getPublicationDate()))
                .setPublicationUser(source.getPublicationUser())
                .setLastVersion(source.getLastVersion())
                .setProcStatus(ProcStatusEnumAvroMapper.do2Avro(source.getProcStatus()))
                .setReplacesVersion(RelatedResourceAvroMapper.do2Avro(source.getReplacesVersion()))
                .setIsReplacedByVersion(null/* TODO source.getIsReplacedByVersion() */)
                .setMaintainer(ExternalItemAvroMapper.do2Avro(source.getMaintainer()))
                .build();
        }
        return target;
    }

}
