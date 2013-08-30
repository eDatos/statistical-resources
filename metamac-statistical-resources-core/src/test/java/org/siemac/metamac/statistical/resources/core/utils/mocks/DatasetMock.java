package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;


public class DatasetMock extends Dataset {
    
    
    public void setCode(String code) {
        getIdentifiableStatisticalResource().setCode(code);
    }
    
    @Override
    public IdentifiableStatisticalResource getIdentifiableStatisticalResource() {
        IdentifiableStatisticalResource resource = super.getIdentifiableStatisticalResource();
        if (resource == null) {
            setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }
        return super.getIdentifiableStatisticalResource();
    }
}
