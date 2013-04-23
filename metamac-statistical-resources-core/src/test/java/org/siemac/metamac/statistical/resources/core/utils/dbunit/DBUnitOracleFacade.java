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
import javax.transaction.xa.XAException;

import oracle.jdbc.driver.T4CXAConnection;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DefaultOperationListener;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.filter.ITableFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.OracleDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.operation.TransactionOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class DBUnitOracleFacade implements DBUnitFacade {

    private Logger              log            = LoggerFactory.getLogger(DBUnitOracleFacade.class);

    DataSourceDatabaseTester    databaseTester = null;

    private DataSource          dataSource;
    private TransactionTemplate transactionTemplate;

    private static List<String> sequences;
    private static List<String> tableNames;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }
    
    @Override
    public void setUpDatabase(final File emptyDatabaseFile, final File xmlDataFile) throws Exception {
        try {
           transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        cleanDatabase(emptyDatabaseFile);
                        setUpDatabase(xmlDataFile);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                    }
                }
            });
        } finally {
            DataSourceUtils.getConnection(dataSource).close();
        }
    }

    //  CAUTION: the connection must be closed outside
    private void setUpDatabase(File xmlDataFile) throws Exception {
        // Setup database tester
        if (databaseTester == null) {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            databaseTester = new OracleDataSourceDatabaseTester(dataSource, connection.getMetaData().getUserName());
            databaseTester.setOperationListener(new TransactionalOperationListener());
        }
        try {
            // Create dataset
            FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
            builder.setColumnSensing(false);
            ReplacementDataSet dataSetReplacement = new ReplacementDataSet(builder.build(xmlDataFile));
            dataSetReplacement.addReplacementObject("[NULL]", null);
            dataSetReplacement.addReplacementObject("[null]", null);
            dataSetReplacement.addReplacementObject("[UNIQUE_SEQUENCE]", (new Date()).getTime());


            IDataSet dataset = new FilteredDataSet(getTableNamesInsertOrder(), new ReplacementDataSet(dataSetReplacement));

            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } catch (Exception e) {
            log.error("Error in dbunit file " + xmlDataFile.getAbsolutePath(), e);
            throw e;
        }
    }

    // CAUTION: the connection must be closed outside
    public void cleanDatabase(File xmlDataFile) throws Exception {
        initializeDatabase();
        
        if (databaseTester == null) {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            databaseTester = new OracleDataSourceDatabaseTester(dataSource, connection.getMetaData().getUserName());
            databaseTester.setOperationListener(new TransactionalOperationListener());
        }

        IDataSet dataset = new FilteredDataSet(getTableNamesInsertOrder(), (new FlatXmlDataSetBuilder()).build(xmlDataFile));
        try {
            databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
            databaseTester.setTearDownOperation(DatabaseOperation.NONE);
            databaseTester.setDataSet(dataset);
            databaseTester.onSetup();
        } catch (Exception e) {
            log.error("Error in dbunit file " + xmlDataFile.getAbsolutePath(), e);
            throw e;
        }
    }

    private void initializeDatabase() throws Exception {
        // Restart sequences
        List<String> sequences = getSequencesToRestart();
        if (sequences != null) {
            for (String sequence : sequences) {
                restartSequence(sequence);
            }
        }
    }

    /**
     * Start the id sequence from a high value to avoid conflicts with test
     * data. You can define the sequence name with {@link #getSequenceName}.
     */
    public void restartSequence(String sequenceName) throws SQLException {
        if (sequenceName == null) {
            return;
        }
        Connection conn = null;
        java.sql.Statement stmt = null;
        try {
            conn = DataSourceUtils.getConnection(dataSource);
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
            IDatabaseConnection connection = new DatabaseConnection(DataSourceUtils.getConnection(dataSource));
            
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new OracleDataTypeFactory());
            connection.getConnection().prepareStatement("SET CONSTRAINTS ALL DEFERRED").execute();
            return connection;
        }
    }
    
    // Avoid to close connection at the end of the operation, this makes sense because we're going to execute operations
    // inside transactions.
    private class TransactionalOperationListener extends DefaultOperationListener {
        
        @Override
        public void operationSetUpFinished(IDatabaseConnection connection) {
            // LEAVE CONNECTION OPEN
        }

        @Override
        public void operationTearDownFinished(IDatabaseConnection connection) {
            // LEAVE CONNECTION OPEN
        }
        
    }
    
  
}
