package org.siemac.metamac.statistical.resources.core.utils.transformers;

import org.siemac.metamac.core.common.util.transformers.MetamacTransformer;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;

public class CodeItemToCodeStringTransformer extends MetamacTransformer<CodeItem, String> {

    @Override
    public String transformItem(CodeItem item) {
        return item.getCode();
    }
}
