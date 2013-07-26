package org.siemac.metamac.statistical.resources.core.task.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.task.utils.JobUtil;

/**
 * Data transfer object for FileDescriptor. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptorBase}.
 */
public class FileDescriptor extends FileDescriptorBase {

    private static final long serialVersionUID = 1L;

    public FileDescriptor() {
    }

    public FileDescriptor(InputStream is, String fileName, DatasetFileFormatEnum datasetFileFormatEnum) throws IOException {
        setFile(JobUtil.cacheFiles(is, datasetFileFormatEnum, fileName));
        setFileName(fileName);
        setDatasetFileFormatEnum(datasetFileFormatEnum);
    }

    public FileDescriptor(File file, String fileName, DatasetFileFormatEnum datasetFileFormatEnum) {
        setFile(file);
        setFileName(fileName);
        setDatasetFileFormatEnum(datasetFileFormatEnum);
    }
}
