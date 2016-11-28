package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceAvro2DoMapper {

    protected LifecycleStatisticalResourceAvro2DoMapper() {
    }

    public static void fillMetadata(LifecycleStatisticalResourceAvro source, LifeCycleStatisticalResource target) throws MetamacException {
        VersionableStatisticalResourceAvro2DoMapper.fillMetadata(source.getVersionableStatisticalResource(), target);
        target.setCreationDate(DateTimeAvro2DoMapper.avro2Do(source.getCreationDate()));
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(DateTimeAvro2DoMapper.avro2Do(source.getProductionValidationDate()));
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setDiffusionValidationDate(DateTimeAvro2DoMapper.avro2Do(source.getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(DateTimeAvro2DoMapper.avro2Do(source.getRejectValidationDate()));
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setPublicationDate(DateTimeAvro2DoMapper.avro2Do(source.getPublicationDate()));
        target.setPublicationUser(source.getPublicationUser());
        target.setLastVersion(source.getLastVersion());
        target.setProcStatus(ProcStatusEnumAvroDo2Mapper.avro2Do(source.getProcStatus()));
        target.setReplacesVersion(RelatedResourceAvro2DoMapper.avro2Do(source.getReplacesVersion()));
        target.setMaintainer(ExternalItemAvro2DoMapper.avro2Do(source.getMaintainer()));
    }

    public static LifeCycleStatisticalResource avro2Do(LifecycleStatisticalResourceAvro source) throws MetamacException {
        LifeCycleStatisticalResource target = new LifeCycleStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
