package org.siemac.metamac.sdmx.data.rest.external.conf;

import org.siemac.metamac.core.common.constants.shared.ConfigurationConstants;

public class DataConfigurationConstants extends ConfigurationConstants {

    // PROPERTIES SPECIFIED IN THE DATA DIRECTORY

    // DataSource

    public static final String DB_URL                = "metamac.srm.db.url";
    public static final String DB_USERNAME           = "metamac.srm.db.username";
    public static final String DB_PASSWORD           = "metamac.srm.db.password";
    public static final String DB_DIALECT            = "metamac.srm.db.dialect";
    public static final String DB_DRIVER_NAME        = "metamac.srm.db.driver_name";
    public static final String DB_DRIVER_NAME_ORACLE = "oracle.jdbc.OracleDriver";
    public static final String DB_DRIVER_NAME_MSSQL  = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
}
