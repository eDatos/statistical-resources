package org.siemac.metamac.statistical.resources.core.utils.shared;

public class DatabaseDatasetImportSharedUtils {

    public static final int     TABLENAME_MIN_LENGTH_PERMITTED = 1;
    public static final int     TABLENAME_MAX_LENGTH_PERMITTED = 63;
    private static final String TABLE_NAME_REGULAR_EXPRESSION  = "^[a-zA-Z_][a-zA-Z0-9_]*$";

    protected DatabaseDatasetImportSharedUtils() {
        // NOTHING TO DO HERE
    }

    /**
     * Check table name length: in postgresql, by default, the maximum identifier length is 63, longer names will be truncated
     * 
     * @see https://www.postgresql.org/docs/8.0/sql-syntax.html#SQL-SYNTAX-IDENTIFIERS
     */
    public static boolean checkTableNameLength(String tableName) {
        return (tableName != null && tableName.length() >= TABLENAME_MIN_LENGTH_PERMITTED && tableName.length() <= TABLENAME_MAX_LENGTH_PERMITTED);
    }

    /**
     * Check the table name format: in sql standard must begin with a letter or an underscore, subsequent characters can be letters, underscores or digits (0-9)
     * 
     * @see https://www.postgresql.org/docs/8.0/sql-syntax.html#SQL-SYNTAX-IDENTIFIERS
     */
    public static boolean checkTableNameFormat(String tableName) {
        return (tableName != null && tableName.matches(DatabaseDatasetImportSharedUtils.TABLE_NAME_REGULAR_EXPRESSION));
    }
}
