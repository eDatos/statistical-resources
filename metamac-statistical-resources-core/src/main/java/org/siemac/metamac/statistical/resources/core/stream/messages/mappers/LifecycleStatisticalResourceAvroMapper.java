package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceAvroMapper {

    protected LifecycleStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(LifecycleStatisticalResourceAvro source, LifeCycleStatisticalResource target) throws MetamacException {
        VersionableStatisticalResourceAvroMapper.fillMetadata(source.getVersionableStatisticalResource(), target);
        target.setCreationDate(DatetimeAvroMapper.avro2Do(source.getCreationDate()));
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(DatetimeAvroMapper.avro2Do(source.getProductionValidationDate()));
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setDiffusionValidationDate(DatetimeAvroMapper.avro2Do(source.getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(DatetimeAvroMapper.avro2Do(source.getRejectValidationDate()));
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setPublicationDate(DatetimeAvroMapper.avro2Do(source.getPublicationDate()));
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
        LifecycleStatisticalResourceAvro target = LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(VersionableStatisticalResourceAvroMapper.do2Avro(source))
                .setCreationDate(DatetimeAvroMapper.do2Avro(source.getCreationDate()))
                .setCreationUser(source.getCreationUser())
                .setProductionValidationDate(DatetimeAvroMapper.do2Avro(source.getProductionValidationDate()))
                .setProductionValidationUser(source.getProductionValidationUser())
                .setDiffusionValidationDate(DatetimeAvroMapper.do2Avro(source.getDiffusionValidationDate()))
                .setDiffusionValidationUser(source.getDiffusionValidationUser())
                .setRejectValidationDate(DatetimeAvroMapper.do2Avro(source.getRejectValidationDate()))
                .setRejectValidationUser(source.getRejectValidationUser())
                .setPublicationDate(DatetimeAvroMapper.do2Avro(source.getPublicationDate()))
                .setPublicationUser(source.getPublicationUser())
                .setLastVersion(source.getLastVersion())
                .setProcStatus(ProcStatusEnumAvroMapper.do2Avro(source.getProcStatus()))
                .setReplacesVersion(RelatedResourceAvroMapper.do2Avro(source.getReplacesVersion()))
                .setMaintainer(ExternalItemAvroMapper.do2Avro(source.getMaintainer()))
                .build();
        return target;
    }

}
