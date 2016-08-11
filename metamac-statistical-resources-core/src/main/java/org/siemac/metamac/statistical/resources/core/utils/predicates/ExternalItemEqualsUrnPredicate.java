package org.siemac.metamac.statistical.resources.core.utils.predicates;

import org.apache.commons.lang3.StringUtils;
import org.siemac.metamac.core.common.util.predicates.MetamacPredicate;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;


public class ExternalItemEqualsUrnPredicate extends MetamacPredicate<ExternalItem> {

    private final String urn;

    public ExternalItemEqualsUrnPredicate(String urn) {
        this.urn = urn;
    }

    @Override
    protected boolean eval(ExternalItem obj) {
        return StringUtils.equals(urn, obj.getUrn());
    }
}
