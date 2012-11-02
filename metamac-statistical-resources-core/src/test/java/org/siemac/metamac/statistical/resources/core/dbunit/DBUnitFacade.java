package org.siemac.metamac.statistical.resources.core.dbunit;

import java.io.File;


public interface DBUnitFacade {
    
    void setUpDatabase(File xmlDataFile) throws Exception;
    
    void cleanDatabase(File xmlDataFile) throws Exception;
}
