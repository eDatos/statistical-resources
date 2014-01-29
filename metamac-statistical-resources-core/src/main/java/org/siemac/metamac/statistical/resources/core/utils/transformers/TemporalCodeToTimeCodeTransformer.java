package org.siemac.metamac.statistical.resources.core.utils.transformers;

import org.siemac.metamac.core.common.util.transformers.MetamacTransformer;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;

public class TemporalCodeToTimeCodeTransformer extends MetamacTransformer<TemporalCode, String> {

    @Override
    public String transformItem(TemporalCode item) {
        return item.getIdentifier();
    }
}
