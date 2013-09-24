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

public class DBOracleSqlMockPersister extends DBSqlMockPersisterBase {

    private static Logger       logger             = LoggerFactory.getLogger(DBOracleSqlMockPersister.class);
    private static List<String> tableOrder;
    private static List<String> sequences;
    private static boolean      sequencesRestarted = false;

    @Override
    protected void restartSequences() {
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

    @Override
    protected void disableReferentialConstraints() {
        jdbcTemplate.execute("SET CONSTRAINTS ALL DEFERRED");
    }

    @Override
    protected void enableReferentialConstraints() throws DataAccessException {
        jdbcTemplate.execute("SET CONSTRAINTS ALL IMMEDIATE");
    }

    private List<String> getSequencesToRestart() {
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
