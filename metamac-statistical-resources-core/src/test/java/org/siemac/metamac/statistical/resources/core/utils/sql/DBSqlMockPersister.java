package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.io.FileInputStream;
import java.net.URL;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


public class DBSqlMockPersister extends DBMockPersisterBase {

    private static Logger logger  = LoggerFactory.getLogger(DBSqlMockPersister.class);
    private static List<String>   tableOrder;
    private static List<String>   sequences;
    private static boolean sequencesRestarted = false;
    
    private JdbcTemplate jdbcTemplate;
    private TransactionTemplate transactionTemplate;
    
    @Autowired
    public void setDatasource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @Override
    protected void persistMocks(List<Object> mocks) throws Exception {

        List<EntityMetadata> mocksMetadata = getMocksMetadata(mocks);
        
        final List<TableMetadata> tableMetadatas = transformEntityToTableMetadata(mocksMetadata);
        
        restartSequences();
        
        logger.info("Persisting mocks ...");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                disableReferentialConstraints();
                try {
                    cleanDatabase();
                    populateDatabase(tableMetadatas);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException("Error preparing mocks in database ",e);
                } finally {
                    enableReferentialConstraints();
                }
            }
        });
        logger.info("Mocks persisted");
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
        for (int j = tables.size()-1; j >= 0; j--) {
            statements[i++] = "Delete from "+tables.get(j);
        }
        jdbcTemplate.batchUpdate(statements);
    }
    
    private void restartSequences() {
        if (!sequencesRestarted) {
            List<String> sequences = getSequencesToRestart();
            if (sequences != null && sequences.size() > 0) {
                String[] statements = new String[sequences.size()*2];
                int i = 0;
                for (String sequence : sequences) {
                    statements[i++] = "drop sequence "+sequence;
                    statements[i++] = "create sequence "+sequence+" START WITH 10000000";
                }
                jdbcTemplate.batchUpdate(statements);
            }
            sequencesRestarted = true;
        }
    }
    
    private void populateDatabase(List<TableMetadata> tableMetadatas) {
        List<String> statements = new ArrayList<String>();
        for (TableMetadata tableMetadata : tableMetadatas) {
            String sqlStatement = buildSqlStatement(tableMetadata); 
            if (sqlStatement != null) {
                statements.add(sqlStatement);
                logger.debug("Statement: "+sqlStatement);
            }
        }
        if (statements.size() > 0) {
            jdbcTemplate.batchUpdate(statements.toArray(new String[statements.size()]));
        }
    }
    
    private void disableReferentialConstraints() {
        jdbcTemplate.execute("SET CONSTRAINTS ALL DEFERRED");
    }
    
    private void enableReferentialConstraints() {
        jdbcTemplate.execute("SET CONSTRAINTS ALL IMMEDIATE");
    }
    
    
    
    private String buildSqlStatement(TableMetadata metadata) {
        StringBuilder builder = new StringBuilder();
        List<String> columnNames = metadata.getColumnNames();
        if (columnNames.size() > 0) {
            List<String> values = new ArrayList<String>();
            for (String columnName : columnNames) {
                values.add(metadata.getColumnValue(columnName));
            }
            builder.append("INSERT INTO ").append(metadata.getTableName())
            .append("(")
            .append(StringUtils.join(columnNames,","))
            .append(") VALUES")
            .append("(")
            .append(StringUtils.join(values,","))
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
