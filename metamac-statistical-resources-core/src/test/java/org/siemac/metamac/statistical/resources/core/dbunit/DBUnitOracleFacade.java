package org.siemac.metamac.statistical.resources.core.dbunit;

import java.io.File;
import java.sql.Connection;
import java.util.Date;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;


public class DBUnitOracleFacade implements DBUnitFacade {
    DataSourceDatabaseTester databaseTester = null;
    
    @Autowired
    DataSource dataSource;

    public void setUpDatabase(File xmlDataFile) throws Exception {
        // Setup database tester
        if (databaseTester == null) {
            Connection connection = dataSource.getConnection();
            try {
                databaseTester = new OracleDataSourceDatabaseTester(dataSource, connection.getMetaData().getUserName());
            } finally {
                connection.close();
            }
        }
     // Setup dbUnit connection
        IDatabaseConnection dbUnitConnection = databaseTester.getConnection();
        try {
            // Create dataset
            ReplacementDataSet dataSetReplacement = new ReplacementDataSet((new FlatXmlDataSetBuilder()).build(xmlDataFile));
            dataSetReplacement.addReplacementObject("[NULL]", null);
            dataSetReplacement.addReplacementObject("[null]", null);
            dataSetReplacement.addReplacementObject("[UNIQUE_SEQUENCE]", (new Date()).getTime());

            // DbUnit inserts and updates rows in the order they are found in your dataset. You must therefore order your tables and rows appropriately in your datasets to prevent foreign keys
            // constraint violation.
            // Since version 2.0, the DatabaseSequenceFilter can now be used to automatically determine the tables order using foreign/exported keys information.
            // NOTE: the test performance is considerably affected when using the DatabaseSequenceFilter strategy
            /*ITableFilter filter = new SequenceTableFilter(getTableNames().toArray(new String[getTableNames().size()]));
            IDataSet dataset = new FilteredDataSet(filter, dataSetReplacement);*/
            
            IDataSet dataset = new ReplacementDataSet(dataSetReplacement);

            // Sometimes DBUnit doesn't erase properly the contents of database (especially when there are related tables). So, we do it manually.
            //initializeDatabase(dbUnitConnection);

            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } finally {
            dbUnitConnection.close();
        }
    }
    
    @Override
    public void cleanDatabase(File xmlDataFile) throws Exception {
        if (databaseTester == null) {
            Connection connection = dataSource.getConnection();
            try {
                databaseTester = new OracleDataSourceDatabaseTester(dataSource, connection.getMetaData().getUserName());
            } finally {
                connection.close();
            }
        }
        // Setup dbUnit connection
        IDatabaseConnection dbUnitConnection = databaseTester.getConnection();
        try {
            // Create dataset
            IDataSet dataset = (new FlatXmlDataSetBuilder()).build(xmlDataFile);

            // Sometimes DBUnit doesn't erase properly the contents of database (especially when there are related tables). So, we do it manually.
            //initializeDatabase(dbUnitConnection);

            databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } finally {
            dbUnitConnection.close();
        }
    }
    
    /*private void initializeDatabase(IDatabaseConnection dbUnitConnection) throws Exception {
        // Remove tables content
        List<String> tablesNamesToRemoveContent = new ArrayList<String>();
        tablesNamesToRemoveContent.addAll(getTableNames());
        Collections.reverse(tablesNamesToRemoveContent);
        if (tablesNamesToRemoveContent != null) {
            for (String tableNameToRemove : tablesNamesToRemoveContent) {
                dbUnitConnection.getConnection().prepareStatement("DELETE FROM " + tableNameToRemove).execute();
            }
        }

        // Restart sequences
        List<String> sequences = getSequencesToRestart();
        if (sequences != null) {
            for (String sequence : sequences) {
                restartSequence(dbUnitConnection, sequence);
            }
        }
    }*/
    
    /**
     * DatasourceTester with support for Oracle data types.
     */
    private class OracleDataSourceDatabaseTester extends DataSourceDatabaseTester {

        public OracleDataSourceDatabaseTester(DataSource dataSource, String schema) {
            super(dataSource, schema);
        }

        @Override
        public IDatabaseConnection getConnection() throws Exception {
            IDatabaseConnection connection = super.getConnection();

            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());

            /*connection.getConfig().setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, new IColumnFilter() {

                Map<String, String> tablePrimaryKeyMap = getTablePrimaryKeys();

                @Override
                public boolean accept(String tableName, Column column) {
                    if (tablePrimaryKeyMap != null && tablePrimaryKeyMap.containsKey(tableName)) {
                        return column.getColumnName().equals(tablePrimaryKeyMap.get(tableName));
                    } else {
                        return column.getColumnName().equalsIgnoreCase("id");
                    }
                }
            });*/
            return connection;
        }
    }
}
