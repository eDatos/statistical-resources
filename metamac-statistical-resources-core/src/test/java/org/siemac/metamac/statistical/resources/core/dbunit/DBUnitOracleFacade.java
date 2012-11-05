package org.siemac.metamac.statistical.resources.core.dbunit;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.configuration.Configuration;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;


public class DBUnitOracleFacade implements DBUnitFacade {
    DataSourceDatabaseTester databaseTester = null;
    
    @Autowired
    DataSource dataSource;
    
    private List<String> sequences;

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
            
            IDataSet dataset = new ReplacementDataSet(dataSetReplacement);

            // Sometimes DBUnit doesn't erase properly the contents of database (especially when there are related tables). So, we do it manually.
            initializeDatabase(dbUnitConnection);

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

            databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } finally {
            dbUnitConnection.close();
        }
    }
    
    private void initializeDatabase(IDatabaseConnection dbUnitConnection) throws Exception {
        // Restart sequences
        List<String> sequences = getSequencesToRestart();
        if (sequences != null) {
            for (String sequence : sequences) {
                restartSequence(dbUnitConnection, sequence);
            }
        }
    }
    
    /**
     * Start the id sequence from a high value to avoid conflicts with test
     * data. You can define the sequence name with {@link #getSequenceName}.
     */
    public static void restartSequence(IDatabaseConnection connection, String sequenceName) throws SQLException {
        if (sequenceName == null) {
            return;
        }
        Connection conn = null;
        java.sql.Statement stmt = null;
        try {
            conn = connection.getConnection();
            stmt = conn.createStatement();
            stmt.execute("DROP SEQUENCE " + sequenceName);
            stmt.execute("CREATE SEQUENCE " + sequenceName + " START WITH 10000000");
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }
    
    protected List<String> getSequencesToRestart() {
        if (sequences == null) {
            try {
                URL url = this.getClass().getResource("/dbunit/oracle.properties");
                Properties prop = new Properties();
                prop.load(new FileInputStream(url.getFile()));
                String sequencesStr = prop.getProperty("sequences");
                sequences = Arrays.asList(sequencesStr.split(","));
            } catch (Exception e) {
                throw new IllegalStateException("Error loading properties which all sequences are specified",e);
            }
        }
        return sequences;
    }
    
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
