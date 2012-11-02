package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.ArrayList;
import java.util.List;



public abstract class MockFactory<Model> {
    
    public abstract Model getMock(String id);
    
    public List<Model> getMocks(String... ids) {
        List<Model> list = new ArrayList<Model>();
        for (String id : ids) {
            list.add(getMock(id));
        }
        return list;
    }
    
}
