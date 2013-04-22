package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.domain.AbstractDomainObject;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ReflectionUtils;


public class DBSqlMockPersister extends DBMockPersisterBase {

    private static Logger logger  = LoggerFactory.getLogger(DBSqlMockPersister.class);
    private static List<String>   tableOrder;
    
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

        final List<EntityMetadata> mocksMetadata = getMocksMetadata(mocks);
        
        logger.info("Persisting mocks ...");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                disableReferentialConstraints();
                try {
                    cleanDatabase();
                    populateDatabase(mocksMetadata);
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
    
    private void cleanDatabase() {
        List<String> tables = getTableOrder();
        String[] statements = new String[tables.size()];
        int i = 0;
        for (int j = tables.size()-1; j >= 0; j--) {
            statements[i++] = "Delete from "+tables.get(j);
        }
        jdbcTemplate.batchUpdate(statements);
    }
    
    private void populateDatabase(List<EntityMetadata> entityMetadatas) {
        List<String> statements = new ArrayList<String>();
        for (EntityMetadata entityMetadata : entityMetadatas) {
            String sqlStatement = buildSqlSentenceForEntity(entityMetadata); 
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
    
    private String buildSqlSentenceForEntity(EntityMetadata metadata) {
        StringBuilder builder = new StringBuilder();
        
        Map<String,String> columnValues = new HashMap<String,String>();        
        for (String propName : metadata.getAllAttributesAndRelations()) {
            Object value = metadata.getColumnValue(propName);
            if (value != null) {
                addColumnsForAttributes(columnValues, propName, value);
            }
        }
        
        List<String> columnNamesNotNull = new ArrayList<String>(columnValues.keySet());
        if (columnNamesNotNull.size() > 0) {
            List<String> values = new ArrayList<String>();
            for (String column : columnNamesNotNull) {
                values.add(columnValues.get(column));
            }
            builder.append("INSERT INTO ").append(metadata.getTableName())
            .append("(")
            .append(StringUtils.join(columnNamesNotNull,","))
            .append(") VALUES")
            .append("(")
            .append(StringUtils.join(values,","))
            .append(")");
            return builder.toString();
        }
        return null;
    }

    
    
    private void addColumnsForAttributes(Map<String, String> columnValues, String propName, Object value) {
        if (value != null) {
            if (value instanceof DateTime) {
                DateTime datetime = (DateTime)value;
                columnValues.put(propName, "TO_TIMESTAMP('"+datetime.toString("yyyy-MM-dd HH:mm:ss.SSS")+"','YYYY-MM-DD HH24:MI:SS.FF')");
                columnValues.put(propName+"_TZ", "'"+datetime.getZone().toString()+"'");
            } else if (value instanceof Number) {
                columnValues.put(propName, ((Number)value).toString());
            } else if (value instanceof Enum){
                Method method = ReflectionUtils.findMethod(value.getClass(), "getValue");
                if (method != null) {
                    try {
                        columnValues.put(propName, "'"+method.invoke(value)+"'");
                    } catch (Exception e) {
                        columnValues.put(propName, "'"+value.toString()+"'");
                    }
                } else {
                    columnValues.put(propName, "'"+value.toString()+"'");
                }
            } else {
                columnValues.put(propName, "'"+value.toString()+"'");
            }
        }
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


}
