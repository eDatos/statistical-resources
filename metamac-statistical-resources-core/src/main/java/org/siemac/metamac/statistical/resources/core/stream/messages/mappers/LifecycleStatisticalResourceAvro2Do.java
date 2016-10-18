package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;

public class LifecycleStatisticalResourceAvro2Do {

    protected LifecycleStatisticalResourceAvro2Do() {
    }

    public static void fillMetadata(LifecycleStatisticalResourceAvro source, LifeCycleStatisticalResource target) throws MetamacException {
        VersionableStatisticalResourceAvro2Do.fillMetadata(source.getVersionableStatisticalResource(), target);
        target.setCreationDate(source.getCreationDate());
        target.setProductionValidationDate(source.getProductionValidationDate());
        target.setDiffusionValidationDate(source.getDiffusionValidationDate());
        target.setRejectValidationDate(source.getRejectValidationDate());
        target.setProductionValidationDate(source.getProductionValidationDate());
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setProductionValidationUser(source.getProductionValidationUser());
        target.setPublicationUser(source.getPublicationUser());
        target.setLastVersion(source.getLastVersion());
        target.setProcStatus(source.getProcStatus());
        target.setReplacesVersion(RelatedResourceAvro2Do.relatedResourceAvro2Do(source.getReplacesVersion()));
        target.setMaintainer(ExternalItemAvro2Do.externalItemAvro2Do(source.getMaintainer()));
    }

    public static LifeCycleStatisticalResource lifeCycleStatisticalResourceAvro2Do(LifecycleStatisticalResourceAvro source) throws MetamacException {
        LifeCycleStatisticalResource target = new LifeCycleStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
