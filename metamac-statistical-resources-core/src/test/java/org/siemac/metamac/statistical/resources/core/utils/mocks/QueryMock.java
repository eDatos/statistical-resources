package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;


public class QueryMock extends Query {

    private String maintainerCode;
    
    public void setMaintainerCode(String maintainerCode) {
        this.maintainerCode = maintainerCode;
    }
    
    public String getMaintainerCode() {
        return maintainerCode;
    }
    
    @Override
    public IdentifiableStatisticalResource getIdentifiableStatisticalResource() {
        IdentifiableStatisticalResource resource = super.getIdentifiableStatisticalResource();
        if (resource == null) {
            setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }
        return super.getIdentifiableStatisticalResource();
    }
    
    public void setStatisticalOperationCode(String statisticalOperationCode) {
        getIdentifiableStatisticalResource().setStatisticalOperation(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem(statisticalOperationCode));
    }
    
}
