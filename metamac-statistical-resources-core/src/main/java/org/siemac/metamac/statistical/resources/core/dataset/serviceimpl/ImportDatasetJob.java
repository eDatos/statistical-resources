package org.siemac.metamac.statistical.resources.core.dataset.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ExceptionHelper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.siemac.metamac.statistical.resources.core.task.utils.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportDatasetJob implements Job {

    private static Logger      logger             = LoggerFactory.getLogger(ImportDatasetJob.class);

    public static final String USER               = "user";
    public static final String FILE_PATHS         = "filePaths";
    public static final String FILE_NAMES         = "fileNames";
    public static final String FILE_FORMATS       = "fileFormats";
    public static final String DATA_STRUCTURE_URN = "dataStructureUrn";
    public static final String DATASET_VERSION_ID = "datasetVersionId";

    private TaskServiceFacade  taskServiceFacade  = null;

    /**
     * Quartz requires a public empty constructor so that the scheduler can instantiate the class whenever it needs.
     */
    public ImportDatasetJob() {
    }

    public TaskServiceFacade getTaskServiceFacade() {
        if (taskServiceFacade == null) {
            taskServiceFacade = (TaskServiceFacade) ApplicationContextProvider.getApplicationContext().getBean(TaskServiceFacade.BEAN_ID);
        }

        return taskServiceFacade;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey jobKey = context.getJobDetail().getKey();

        // Parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();

        // Execution
        ServiceContext serviceContext = new ServiceContext(data.getString(USER), context.getFireInstanceId(), "metamac-statistical-resources");

        try {
            logger.info("ImportationJob: " + jobKey + " starting at " + new Date());

            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDataStructureUrn(data.getString(DATA_STRUCTURE_URN));
            taskInfoDataset.getFiles().addAll(inflateFileDescriptors(data.getString(FILE_PATHS), data.getString(FILE_NAMES), data.getString(FILE_FORMATS)));
            taskInfoDataset.setDatasetVersionId(data.getString(DATASET_VERSION_ID));

            getTaskServiceFacade().executeImportationTask(serviceContext, jobKey.getName(), taskInfoDataset);
            logger.info("ImportationJob: " + jobKey + " finished at " + new Date());
        } catch (Exception e) {
            // Concert parser exception to metamac exception
            MetamacException throwableMetamacException = null;
            if (e instanceof MetamacException) {
                throwableMetamacException = (MetamacException) e;
            } else {
                throwableMetamacException = MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(ExceptionHelper.excMessage(e))
                        .build();
            }
            // Mark as failed
            try {
                getTaskServiceFacade().markTaskAsFailed(serviceContext, jobKey.getName(), throwableMetamacException);
                logger.info("ImportationJob: " + jobKey + " marked as error at " + new Date());
            } catch (MetamacException e1) {
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed and it can't marked as error", e1);
            }
        }
    }

    private List<FileDescriptor> inflateFileDescriptors(String filePaths, String fileNames, String fileFormats) throws FileNotFoundException, UnsupportedEncodingException {
        List<FileDescriptor> fileDescriptorDtos = new LinkedList<FileDescriptor>();
        String[] files = filePaths.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);
        String[] names = fileNames.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);
        String[] formats = fileFormats.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);

        for (int i = 0; i < files.length; i++) {
            FileDescriptor fileDescriptorDto = new FileDescriptor();
            fileDescriptorDto.setDatasetFileFormatEnum(DatasetFileFormatEnum.valueOf(formats[i]));
            String encoding = StringUtils.isEmpty(System.getProperty("file.encoding")) ? "UTF-8" : System.getProperty("file.encoding");
            fileDescriptorDto.setFileName(URLDecoder.decode(names[i], encoding));
            fileDescriptorDto.setFile(new File(URLDecoder.decode(files[i], encoding)));
            fileDescriptorDtos.add(fileDescriptorDto);
        }

        return fileDescriptorDtos;
    }
}
