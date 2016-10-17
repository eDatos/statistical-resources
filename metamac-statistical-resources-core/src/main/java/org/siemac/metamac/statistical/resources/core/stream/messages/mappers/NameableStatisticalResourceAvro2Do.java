package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceAvro2Do {

    protected NameableStatisticalResourceAvro2Do() {
    }

    public static NameableStatisticalResource nameableStatisticalResourceAvro2Do(NameableStatisticalResourceAvro source) {
        IdentifiableStatisticalResource identifiable = IdentifiableStatisticalResourceAvro2Do.identifiableStatisticalResourceAvro2Do(source.getIdentifiableStatisticalResource());
        NameableStatisticalResource target = new NameableStatisticalResource();

        target.setCode(identifiable.getCode());
        target.setUrn(identifiable.getUrn());

        target.setTitle(InternationalStringAvro2Do.internationalStringAvro2Do(source.getTitle()));
        target.setDescription(InternationalStringAvro2Do.internationalStringAvro2Do(source.getDescription()));
        return target;
    }

}
