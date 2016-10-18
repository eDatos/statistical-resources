package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class VersionableStatisticalResourceAvro2Do {

    protected VersionableStatisticalResourceAvro2Do() {
    }

    public static void fillMetadata(VersionableStatisticalResourceAvro source, VersionableStatisticalResource target) {
        NameableStatisticalResourceAvro2Do.fillMetadata(source.getNameableStatisticalResource(), target);
        target.setNextVersionDate(source.getNextVersionDate());
        target.setValidFrom(source.getValidFrom());
        target.setValidTo(source.getValidTo());
        target.setVersionRationale(InternationalStringAvro2Do.internationalStringAvro2Do(source.getVersionRationale()));
        target.setNextVersion(source.getNextVersion());
        target.setVersionLogic(source.getVersionLogic());
    }

    public static VersionableStatisticalResource versionableStatisticalResourceAvro2Do(VersionableStatisticalResourceAvro source) {
        VersionableStatisticalResource target = new VersionableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }
}
