package org.siemac.metamac.statistical.resources.core.utils.transformers;

import org.siemac.metamac.core.common.util.transformers.MetamacTransformer;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;

public class CodeDimensionToCodeStringTransformer extends MetamacTransformer<CodeDimension, String> {

    @Override
    public String transformItem(CodeDimension item) {
        return item.getIdentifier();
    }
}
