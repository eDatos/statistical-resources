package org.siemac.metamac.statistical.resources.core.utils.transformers;

import org.apache.commons.collections.Transformer;


public abstract class MetamacTransformer<T extends Object, R extends Object> implements Transformer {

    @SuppressWarnings("unchecked")
    @Override
    public final Object transform(Object input) {
        return transformItem((T)input);
    }
    
    public abstract R transformItem(T item);
}
