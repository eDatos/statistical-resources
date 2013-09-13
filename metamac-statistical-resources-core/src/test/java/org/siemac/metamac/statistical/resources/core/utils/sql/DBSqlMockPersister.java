package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.core.common.utils.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class DBSqlMockPersister extends DBMockPersisterBase {

    private static Logger       logger             = LoggerFactory.getLogger(DBSqlMockPersister.class);
    private static List<String> tableOrder;
    private static List<String> sequences;
    private static boolean      sequencesRestarted = false;

    private JdbcTemplate        jdbcTemplate;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setDatasource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    protected void persistMocks(List<Object> mocks) throws Exception {

        logger.info("Transforming mocks ... " + mocks.size());
        List<EntityMetadata> mocksMetadata = getMocksMetadata(mocks);

        final List<TableMetadata> tableMetadatas = transformEntityToTableMetadata(mocksMetadata);

        restartSequences();

        logger.info("Persisting mocks ... " + mocks.size());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                long time1 = System.currentTimeMillis();
                disableReferentialConstraints();
                long timeDisable = System.currentTimeMillis();
                logger.info("Disabling constraints time: " + (timeDisable - time1));
                boolean errorInBatchCaught = false;
                try {
                    long timeInit = System.currentTimeMillis();
                    cleanDatabase();
                    long timeAfterClean = System.currentTimeMillis();
                    logger.info("Cleaning database time: " + (timeAfterClean - timeInit));
                    populateDatabase(tableMetadatas);
                    long timeAfterPopulate = System.currentTimeMillis();
                    logger.info("Populate database time: " + (timeAfterPopulate - timeAfterClean));
                } catch (Exception e) {
                    status.setRollbackOnly();
                    errorInBatchCaught = true;
                    throw new RuntimeException("Error preparing mocks in database ", e);
                } finally {
                    try {
                        long time2 = System.currentTimeMillis();
                        enableReferentialConstraints();
                        long time3 = System.currentTimeMillis();
                        logger.info("Enabling constraints time: " + (time3 - time2));
                    } catch (DataAccessException e) {
                        if (!errorInBatchCaught) {
                            throw e;
                        }
                    }
                }
            }
        });
        logger.info("Mocks persisted in " + tableMetadatas.size() + " rows");
    }
    private List<TableMetadata> transformEntityToTableMetadata(List<EntityMetadata> mocksMetadata) {
        List<TableMetadata> transformList = new ArrayList<TableMetadata>();
        for (EntityMetadata entity : mocksMetadata) {
            transformList.add(new SqlTableMetadata(entity));
        }
        return transformList;
    }

    private void cleanDatabase() {
        List<String> tables = getTableOrder();
        String[] statements = new String[tables.size()];
        int i = 0;
        for (int j = tables.size() - 1; j >= 0; j--) {
            String statement = "Delete from " + tables.get(j);
            statements[i++] = statement;
            logger.debug(statement);
        }
        jdbcTemplate.batchUpdate(statements);
    }

    private void analyzeAllTables() {
        List<String> tables = getTableOrder();
        String[] statements = new String[tables.size()];
        int i = 0;
        for (int j = tables.size() - 1; j >= 0; j--) {
            String statement = "analyze table " + tables.get(j) + " estimate STATISTICS";
            statements[i++] = statement;
            logger.debug(statement);
        }
        jdbcTemplate.batchUpdate(statements);

    }

    private void allTablesNologging() {
        List<String> tables = getTableOrder();
        String[] statements = new String[tables.size()];
        int i = 0;
        for (int j = tables.size() - 1; j >= 0; j--) {
            String statement = "alter table " + tables.get(j) + " nologging";
            statements[i++] = statement;
            logger.debug(statement);
        }
        jdbcTemplate.batchUpdate(statements);

    }

    private void restartSequences() {
        if (!sequencesRestarted) {
            List<String> sequences = getSequencesToRestart();
            if (sequences != null && sequences.size() > 0) {
                String[] statements = new String[sequences.size() * 2];
                int i = 0;
                for (String sequence : sequences) {
                    statements[i++] = "drop sequence " + sequence;
                    statements[i++] = "create sequence " + sequence + " START WITH 10000000";
                }
                jdbcTemplate.batchUpdate(statements);
            }
            sequencesRestarted = true;
        }
    }

    private void populateDatabaseBatch(List<TableMetadata> tableMetadatas) {
        List<String> statements = new ArrayList<String>();
        for (TableMetadata tableMetadata : tableMetadatas) {
            logger.trace("Table metadata " + tableMetadata);
            String sqlStatement = buildSqlStatement(tableMetadata);
            if (sqlStatement != null) {
                statements.add(sqlStatement);
                logger.debug("Statement: " + sqlStatement);
            }
        }
        if (statements.size() > 0) {
            jdbcTemplate.batchUpdate(statements.toArray(new String[statements.size()]));
        }
    }

    private void populateDatabase(List<TableMetadata> tableMetadatas) throws Exception {
        List<String> statements = new ArrayList<String>();
        for (TableMetadata tableMetadata : tableMetadatas) {
            logger.trace("Table metadata " + tableMetadata);
            String sqlStatement = buildSqlStatement(tableMetadata);
            try {
                if (sqlStatement != null) {
                    statements.add(sqlStatement);
                    logger.debug("Statement: " + sqlStatement);
                    jdbcTemplate.update(sqlStatement);
                }
            } catch (Exception e) {
                logger.error("Error running statement " + sqlStatement + " ID: " + tableMetadata.getColumnValue("ID"));
                throw e;
            }
        }
    }

    private void disableReferentialConstraints() {
        // _disableAll();
        jdbcTemplate.execute("SET CONSTRAINTS ALL DEFERRED");
    }

    private void _disableAll() {
        List<String> statements = jdbcTemplate.query("SELECT 'alter table '|| table_name || ' disable constraints ' || constraint_name FROM user_constraints where constraint_type = 'R'",
                new RowMapper<String>() {

                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                });
        for (String statement : statements) {
            jdbcTemplate.execute(statement);
        }
    }

    private void enableReferentialConstraints() throws DataAccessException {
        // _enableAll();
        jdbcTemplate.execute("SET CONSTRAINTS ALL IMMEDIATE");
    }

    private void _enableAll() {
        List<String> statements = jdbcTemplate.query("SELECT 'alter table '|| table_name || ' enable constraints ' || constraint_name FROM user_constraints where constraint_type = 'R'",
                new RowMapper<String>() {

                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                });
        for (String statement : statements) {
            jdbcTemplate.execute(statement);
        }
    }

    private List<String> queryIndexesNames() {
        //@formatter:off
        return jdbcTemplate.query(
                "SELECT   distinct index_name " + 
                "FROM     user_ind_columns", 
                new RowMapper<String>() {
        //@formatter:on

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        });
    }

    private String buildSqlStatement(TableMetadata metadata) {
        StringBuilder builder = new StringBuilder();
        List<String> columnNames = metadata.getColumnNames();
        if (columnNames.size() > 0) {
            List<String> values = new ArrayList<String>();
            for (String columnName : columnNames) {
                values.add(metadata.getColumnValue(columnName));
            }
            builder.append("INSERT INTO ").append(metadata.getTableName()).append("(").append(StringUtils.join(columnNames, ",")).append(") VALUES").append("(").append(StringUtils.join(values, ","))
                    .append(")");
            return builder.toString();
        }
        return null;
    }

    private List<String> getTableOrder() {
        if (tableOrder == null) {
            try {
                URL url = this.getClass().getResource("/dbunit/oracle.properties");
                Properties prop = new Properties();
                prop.load(new FileInputStream(url.getFile()));
                String sequencesStr = prop.getProperty("tables");
                tableOrder = Arrays.asList(sequencesStr.split(","));
            } catch (Exception e) {
                throw new IllegalStateException("Error loading properties which all tablenames are specified", e);
            }
        }
        return tableOrder;
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

}
