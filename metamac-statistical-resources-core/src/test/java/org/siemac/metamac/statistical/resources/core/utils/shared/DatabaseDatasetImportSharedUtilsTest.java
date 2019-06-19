package org.siemac.metamac.statistical.resources.core.utils.shared;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class DatabaseDatasetImportSharedUtilsTest {

    @Test
    public void testCheckTableNameLength() {
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameLength(null));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameLength(StringUtils.EMPTY));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameLength(RandomStringUtils.randomAlphabetic(DatabaseDatasetImportSharedUtils.TABLENAME_MIN_LENGTH_PERMITTED)));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameLength(RandomStringUtils.randomAlphabetic(DatabaseDatasetImportSharedUtils.TABLENAME_MAX_LENGTH_PERMITTED)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameLength(RandomStringUtils.randomAlphabetic(DatabaseDatasetImportSharedUtils.TABLENAME_MAX_LENGTH_PERMITTED + 1)));
    }

    @Test
    public void testCheckTableNameFormat() {
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(null));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(StringUtils.EMPTY));

        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1)));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomNumeric(1)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(" "));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(";"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("'"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("\\"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("\""));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("("));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat(")"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("`"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("´"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("/"));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("*"));

        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(20)));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(20)));
        assertTrue(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "_" + RandomStringUtils.randomAlphanumeric(5)));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "_" + RandomStringUtils.randomAlphanumeric(5)));
        assertTrue(DatabaseDatasetImportSharedUtils.checkTableNameFormat("__"));

        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + " " + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + ";" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "'" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils
                .checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "\\" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils
                .checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "\"" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "(" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + ")" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "`" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "´" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "/" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(
                DatabaseDatasetImportSharedUtils.checkTableNameFormat(RandomStringUtils.randomAlphabetic(1) + RandomStringUtils.randomAlphanumeric(5) + "*" + RandomStringUtils.randomAlphanumeric(5)));

        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + " " + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + ";" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "'" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "\\" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "\"" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "(" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + ")" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "`" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "´" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "/" + RandomStringUtils.randomAlphanumeric(5)));
        assertFalse(DatabaseDatasetImportSharedUtils.checkTableNameFormat("_" + RandomStringUtils.randomAlphanumeric(5) + "*" + RandomStringUtils.randomAlphanumeric(5)));

    }

}
