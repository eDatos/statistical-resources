package org.siemac.metamac.statistical.resources.core.utils.predicates;

import org.apache.commons.lang3.StringUtils;
import org.siemac.metamac.core.common.util.predicates.MetamacPredicate;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;

public class CodeDimensionEqualsIdentifierPredicate extends MetamacPredicate<CodeDimension> {

    private final String identifier;

    public CodeDimensionEqualsIdentifierPredicate(String identifier) {
        this.identifier = identifier;
    }

    @Override
    protected boolean eval(CodeDimension obj) {
        return StringUtils.equals(identifier, obj.getIdentifier());
    }
}
