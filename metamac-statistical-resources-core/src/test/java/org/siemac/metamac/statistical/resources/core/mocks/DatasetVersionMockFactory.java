package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMockFactory extends MockFactory<DatasetVersion> {
    
    public static final String DatasetVersionBasic01                = "DatasetVersionBasic01";

    
    private static Map<String,DatasetVersion> mocks;
    
    static {
        mocks = new HashMap<String, DatasetVersion>();
        mocks.put(DatasetVersionBasic01, StatisticalResourcesDoMocks.mockDatasetVersion());
        
    }

    @Override
    public DatasetVersion getMock(String id) {
        return mocks.get(id);
    }
    

}

