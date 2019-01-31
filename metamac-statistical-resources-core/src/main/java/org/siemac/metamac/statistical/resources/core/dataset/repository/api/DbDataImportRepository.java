package org.siemac.metamac.statistical.resources.core.dataset.repository.api;

import java.util.List;

import org.joda.time.DateTime;

public interface DbDataImportRepository {

    public static final String BEAN_ID = "dbDataImportRepository";

    public boolean checkTableExists(String tableName);
    public boolean checkTableHasColumn(String tableName, String filterColumnName);

    public List<String[]> getObservations(String tableName, List<String> columnsName, String filterColumnName, DateTime filterValue);

}
