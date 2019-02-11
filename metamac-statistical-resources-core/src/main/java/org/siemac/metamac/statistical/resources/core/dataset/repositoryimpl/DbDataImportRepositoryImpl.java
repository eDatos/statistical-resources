package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.dataset.repository.api.DbDataImportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository(DbDataImportRepository.BEAN_ID)
public class DbDataImportRepositoryImpl implements DbDataImportRepository {

    private static Logger logger = LoggerFactory.getLogger(DbDataImportRepositoryImpl.class);

    private JdbcTemplate  jdbcTemplate;

    @Autowired
    @Qualifier("dataSourceDbDataImportRepository")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String[]> getObservations(String tableName, List<String> columnsName, String filterColumnName, DateTime filterValue) {
        String sqlQuery = buildQuery(tableName, columnsName, filterColumnName, filterValue);
        logger.debug(sqlQuery);

        return jdbcTemplate.query(sqlQuery, new ObservationMapper(columnsName));
    }

    @Override
    public boolean checkTableExists(String tableName) {

        return jdbcTemplate.execute(new ConnectionCallback<Boolean>() {

            @Override
            public Boolean doInConnection(Connection con) throws SQLException {
                boolean found = Boolean.FALSE;

                ResultSet resultSet = con.getMetaData().getTables(null, null, tableName.toLowerCase(), null);

                while (resultSet.next() && !found) {
                    found = StringUtils.equalsIgnoreCase(resultSet.getString("TABLE_NAME"), tableName);
                }
                return found;
            }
        });
    }

    @Override
    public boolean checkTableHasColumn(String tableName, String filterColumnName) {
        return jdbcTemplate.execute(new ConnectionCallback<Boolean>() {

            @Override
            public Boolean doInConnection(Connection con) throws SQLException {
                boolean found = Boolean.FALSE;

                ResultSet resultSet = con.getMetaData().getColumns(null, null, tableName.toLowerCase(), null);

                while (resultSet.next() && !found) {
                    found = StringUtils.equalsIgnoreCase(resultSet.getString("COLUMN_NAME"), filterColumnName);
                }

                return found;
            }
        });
    }

    private String buildQuery(String tableName, List<String> columnsName, String filterColumnName, DateTime filterValue) {
        StringBuilder statement = new StringBuilder("SELECT ").append(getColumnsNameAsString(columnsName)).append(" FROM ").append(tableName);

        if (filterValue != null) {
            statement.append(" WHERE ").append(filterColumnName).append(" >= ").append("to_timestamp('").append(getFilterValueAsString(filterValue)).append("', 'DD-MM-YYYY HH24:MI:SS,MS')");
        }

        return statement.toString();
    }

    private String getColumnsNameAsString(List<String> columnsName) {
        return StringUtils.join(columnsName, ", ");
    }

    private String getFilterValueAsString(DateTime filterValue) {
        return filterValue.toString("dd-MM-yyyy HH:mm:ss,SSS");
    }

    private class ObservationMapper implements RowMapper<String[]> {

        private List<String> columnsName;

        public ObservationMapper(List<String> columnsName) {
            this.columnsName = columnsName;
        }

        @Override
        public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
            List<String> observation = new ArrayList<>();
            for (String s : columnsName) {
                observation.add(rs.getString(s));
            }
            return observation.toArray(new String[0]);
        }
    }
}
