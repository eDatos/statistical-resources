package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class DatasetMockFactory extends MockFactory<Dataset> {
    
    public static final String DatasetBasic01                = "DatasetBasic01";

    
    private static Map<String,Dataset> mocks;
    
    static {
        mocks = new HashMap<String, Dataset>();
        mocks.put(DatasetBasic01, StatisticalResourcesDoMocks.mockDataset());
        
    }

    @Override
    public Dataset getMock(String id) {
        return mocks.get(id);
    }
    

}

