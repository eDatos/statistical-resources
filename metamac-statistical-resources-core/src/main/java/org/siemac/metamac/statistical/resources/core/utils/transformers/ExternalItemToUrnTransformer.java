package org.siemac.metamac.statistical.resources.core.utils.transformers;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;

public class ExternalItemToUrnTransformer extends MetamacTransformer<ExternalItem, String> {

    @Override
    public String transformItem(ExternalItem item) {
        return item.getUrn();
    }
}
