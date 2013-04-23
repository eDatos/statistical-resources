package org.siemac.metamac.statistical.resources.core.utils.dbunit;

import java.io.File;


public interface DBUnitFacade {
    
    void setUpDatabase(File emptyDatabaseFile, File xmlDataFile) throws Exception;
    
}
