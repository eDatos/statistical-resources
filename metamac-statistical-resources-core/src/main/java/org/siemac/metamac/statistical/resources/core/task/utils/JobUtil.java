package org.siemac.metamac.statistical.resources.core.task.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.task.serviceimpl.TaskServiceImpl;

public class JobUtil {

    private JobUtil() {
    }

    public static final String SERIALIZATION_SEPARATOR      = "|";
    public static final String SERIALIZATION_PAIR_SEPARATOR = "<>";

    public static File cacheFiles(InputStream is, DatasetFileFormatEnum datasetFileFormatEnum, String fileName) throws IOException {
        OutputStream os = null;
        File file = null;
        try {
            file = File.createTempFile("file_", "_" + datasetFileFormatEnum + ".task");
            file.deleteOnExit();
            os = new FileOutputStream(file);
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
        return file;
    }

    public static String createJobNameForImportationResource(String resourceId) {
        return TaskServiceImpl.PREFIX_JOB_IMPORT_DATA + resourceId;
    }

    public static String createJobNameForDbImportationResource(String resourceId) {
        return TaskServiceImpl.PREFIX_JOB_DB_IMPORT_DATA + resourceId;
    }

    public static String createJobNameForRecoveryImportationResource(String resourceId) {
        return TaskServiceImpl.PREFIX_JOB_RECOVERY_IMPORT_DATA + resourceId;
    }

    public static String createJobNameForDuplicationResource(String resourceId) {
        return TaskServiceImpl.PREFIX_JOB_DUPLICATION_DATA + resourceId;
    }

}
