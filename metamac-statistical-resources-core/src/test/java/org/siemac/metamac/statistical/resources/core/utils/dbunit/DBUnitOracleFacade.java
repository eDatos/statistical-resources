package org.siemac.metamac.statistical.resources.core.utils.dbunit;

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

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DBUnitOracleFacade implements DBUnitFacade {

    private Logger              log            = LoggerFactory.getLogger(DBUnitOracleFacade.class);

    DataSourceDatabaseTester    databaseTester = null;

    @Autowired
    DataSource                  dataSource;

    private static List<String> sequences;
    private static List<String> tableNames;

    @Override
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
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(false);
            ReplacementDataSet dataSetReplacement = new ReplacementDataSet(builder.build(xmlDataFile));
            dataSetReplacement.addReplacementObject("[NULL]", null);
            dataSetReplacement.addReplacementObject("[null]", null);
            dataSetReplacement.addReplacementObject("[UNIQUE_SEQUENCE]", (new Date()).getTime());

            /*
             * ITableFilter filter = new DatabaseSequenceFilter(dbUnitConnection);
             * IDataSet dataset = new FilteredDataSet(getTableNamesInsertOrder(), new ReplacementDataSet(dataSetReplacement));
             */
            
//            IDataSet dataset = new ReplacementDataSet(dataSetReplacement);
           
//            IDataSet dataset = new FilteredDataSet(getTableNamesInsertOrder(), new ReplacementDataSet(dataSetReplacement));
            ITableFilter filter = new DatabaseSequenceFilter(dbUnitConnection);
            IDataSet dataset = new FilteredDataSet(filter, dataSetReplacement);

            // Sometimes DBUnit doesn't erase properly the contents of database (especially when there are related tables). So, we do it manually.
            initializeDatabase(dbUnitConnection);

            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } catch (Exception e) {
            log.error("Error in dbunit file " + xmlDataFile.getAbsolutePath(),e);
            throw e;
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
//            IDataSet dataSetReplacement = (new FlatXmlDataSetBuilder()).build(xmlDataFile);
//            ITableFilter filter = new DatabaseSequenceFilter(dbUnitConnection);
//            IDataSet dataset = new FilteredDataSet(filter, dataSetReplacement);
            
            IDataSet dataset = new FilteredDataSet(getTableNamesInsertOrder(),(new FlatXmlDataSetBuilder()).build(xmlDataFile));

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

    protected String[] getTableNamesInsertOrder() {
        if (tableNames == null) {
            try {
                URL url = this.getClass().getResource("/dbunit/oracle.properties");
                Properties prop = new Properties();
                prop.load(new FileInputStream(url.getFile()));
                String sequencesStr = prop.getProperty("tables");
                tableNames = Arrays.asList(sequencesStr.split(","));
            } catch (Exception e) {
                throw new IllegalStateException("Error loading properties which all tablenames are specified", e);
            }
        }
        return (String[]) tableNames.toArray();
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
                throw new IllegalStateException("Error loading properties which all sequences are specified", e);
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

            /*
             * connection.getConfig().setProperty(DatabaseConfig.PROPERTY_PRIMARY_KEY_FILTER, new IColumnFilter() {
             * Map<String, String> tablePrimaryKeyMap = getTablePrimaryKeys();
             * @Override
             * public boolean accept(String tableName, Column column) {
             * if (tablePrimaryKeyMap != null && tablePrimaryKeyMap.containsKey(tableName)) {
             * return column.getColumnName().equals(tablePrimaryKeyMap.get(tableName));
             * } else {
             * return column.getColumnName().equalsIgnoreCase("id");
             * }
             * }
             * });
             */
            return connection;
        }
    }
}
