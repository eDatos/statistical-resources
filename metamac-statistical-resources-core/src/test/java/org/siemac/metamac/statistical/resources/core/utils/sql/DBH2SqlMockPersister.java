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

public class DBH2SqlMockPersister extends DBSqlMockPersisterBase {

    private static Logger       logger             = LoggerFactory.getLogger(DBH2SqlMockPersister.class);
    private static List<String> tableOrder;
    private static List<String> sequences;
    private static boolean      sequencesRestarted = false;

    @Override
    protected void restartSequences() {
    }

    @Override
    protected void disableReferentialConstraints() {
        for (String tableName : getTableOrder()) {
            jdbcTemplate.execute("ALTER TABLE " + tableName + " set referential_integrity false");
        }
    }

    @Override
    protected void enableReferentialConstraints() throws DataAccessException {
        for (String tableName : getTableOrder()) {
            jdbcTemplate.execute("ALTER TABLE " + tableName + " set referential_integrity true check");
        }
    }

    @Override
    protected TableMetadata transformEntityToTableMetadata(EntityMetadata entity) {
        return new H2SqlTableMetadata(entity);
    }

}
