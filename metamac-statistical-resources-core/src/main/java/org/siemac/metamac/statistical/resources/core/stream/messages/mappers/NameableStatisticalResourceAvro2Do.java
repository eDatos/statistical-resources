package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceAvro2Do {

    protected NameableStatisticalResourceAvro2Do() {
    }

    public static void fillMetadata(NameableStatisticalResourceAvro source, NameableStatisticalResource target) {
        IdentifiableStatisticalResourceAvro2Do.fillMetadata(source.getIdentifiableStatisticalResource(), target);
        target.setTitle(InternationalStringAvro2Do.internationalStringAvro2Do(source.getTitle()));
        target.setDescription(InternationalStringAvro2Do.internationalStringAvro2Do(source.getDescription()));
    }

    public static NameableStatisticalResource nameableStatisticalResourceAvro2Do(NameableStatisticalResourceAvro source) {
        NameableStatisticalResource target = new NameableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
