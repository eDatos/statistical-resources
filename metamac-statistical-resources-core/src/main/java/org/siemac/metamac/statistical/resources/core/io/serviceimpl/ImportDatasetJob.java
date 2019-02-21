package org.siemac.metamac.statistical.resources.core.io.serviceimpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ExceptionHelper;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.enume.task.domain.DatasetFileFormatEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.NoticesRestInternalService;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;
import org.siemac.metamac.statistical.resources.core.task.domain.AlternativeEnumeratedRepresentation;
import org.siemac.metamac.statistical.resources.core.task.domain.FileDescriptor;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;
import org.siemac.metamac.statistical.resources.core.task.serviceapi.TaskServiceFacade;
import org.siemac.metamac.statistical.resources.core.task.utils.JobUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImportDatasetJob implements Job {

    private static Logger      logger                            = LoggerFactory.getLogger(ImportDatasetJob.class);

    public static final String USER                              = "user";
    public static final String FILE_PATHS                        = "filePaths";
    public static final String FILE_NAMES                        = "fileNames";
    public static final String FILE_FORMATS                      = "fileFormats";
    public static final String DATA_STRUCTURE_URN                = "dataStructureUrn";
    public static final String DATASET_URN                       = "datasetUrn";
    public static final String DATASET_VERSION_ID                = "datasetVersionId";
    public static final String ALTERNATIVE_REPRESENTATIONS       = "alternativeRepresentations";
    public static final String STORE_ALTERNATIVE_REPRESENTATIONS = "storeAlternativeRepresentations";
    public static final String DATASET_ID                        = "datasetId";
    public static final String TASK_NAME                         = "taskName";

    private TaskServiceFacade  taskServiceFacade                 = null;

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

        JobDetail jobDetail = context.getJobDetail();

        JobKey jobKey = jobDetail.getKey();

        // Parameters
        JobDataMap data = jobDetail.getJobDataMap();
        String dataStructureUrn = data.getString(DATA_STRUCTURE_URN);
        String filePaths = data.getString(FILE_PATHS);
        String fileNames = data.getString(FILE_NAMES);
        String fileFormats = data.getString(FILE_FORMATS);
        String datasetUrn = data.getString(DATASET_URN);
        String datasetVersionId = data.getString(DATASET_VERSION_ID);
        String alternativeRepresentations = data.getString(ALTERNATIVE_REPRESENTATIONS);
        Boolean storeAlternativeRepresentations = data.getBoolean(STORE_ALTERNATIVE_REPRESENTATIONS);
        String datasetId = data.getString(DATASET_ID);
        String taskName = data.getString(TASK_NAME);
        String user = data.getString(USER);

        // Execution
        ServiceContext serviceContext = new ServiceContext(user, context.getFireInstanceId(), "statistical-resources-core");

        try {
            logger.info("ImportationJob: " + jobKey + " starting at " + new Date());

            TaskInfoDataset taskInfoDataset = new TaskInfoDataset();
            taskInfoDataset.setDataStructureUrn(dataStructureUrn);
            taskInfoDataset.getFiles().addAll(inflateFileDescriptors(filePaths, fileNames, fileFormats));
            taskInfoDataset.setDatasetUrn(datasetUrn);
            taskInfoDataset.setDatasetVersionId(datasetVersionId);
            taskInfoDataset.getAlternativeRepresentations().addAll(inflateAlternativeRepresentations(alternativeRepresentations));
            taskInfoDataset.setStoreAlternativeRepresentations(storeAlternativeRepresentations);

            getTaskServiceFacade().executeImportationTask(serviceContext, taskName, taskInfoDataset);
            logger.info("ImportationJob: " + jobKey + " finished at " + new Date());
            getNoticesRestInternalService().createSuccessBackgroundNotification(user, ServiceNoticeAction.IMPORT_DATASET_JOB, ServiceNoticeMessage.IMPORT_DATASET_JOB_OK, fileNames);
        } catch (Exception e) {
            // Concert parser exception to metamac exception
            MetamacException metamacException = null;
            if (e instanceof MetamacException) {
                metamacException = (MetamacException) e;
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed", e);
            } else if (e instanceof FileNotFoundException) {
                metamacException = MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(ExceptionHelper.excMessage(e)).build();
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed because file not exists", e);
            } else if (e instanceof UnsupportedEncodingException) {
                metamacException = MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.TASKS_ERROR).withMessageParameters(ExceptionHelper.excMessage(e)).build();
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed  has failed due to an unsupported encoding", e);
            }

            try {
                getTaskServiceFacade().markTaskAsFailed(serviceContext, taskName, datasetVersionId, datasetId, metamacException);
                logger.info("ImportationJob: " + jobKey + " marked as error at " + new Date());
                metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORT_DATASET_JOB_ERROR, fileNames));
                getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.IMPORT_DATASET_JOB, metamacException);
            } catch (MetamacException e1) {
                logger.error("ImportationJob: the importation with key " + jobKey.getName() + " has failed and it can't marked as error", e1);
                metamacException.setPrincipalException(new MetamacExceptionItem(ServiceExceptionType.IMPORT_DATASET_JOB_ERROR_AND_CANT_MARK_AS_ERROR, fileNames));
                getNoticesRestInternalService().createErrorBackgroundNotification(user, ServiceNoticeAction.IMPORT_DATASET_JOB, metamacException);
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

    private List<AlternativeEnumeratedRepresentation> inflateAlternativeRepresentations(String alternativeRepresentations) {
        List<AlternativeEnumeratedRepresentation> alternativeRepresentationList = new ArrayList<AlternativeEnumeratedRepresentation>();
        if (!StringUtils.isEmpty(alternativeRepresentations)) {
            String[] pairs = alternativeRepresentations.split("\\" + JobUtil.SERIALIZATION_SEPARATOR);

            for (int i = 0; i < pairs.length; i++) {
                String[] items = pairs[i].split(JobUtil.SERIALIZATION_PAIR_SEPARATOR);
                AlternativeEnumeratedRepresentation alternativeEnumeratedRepresentation = new AlternativeEnumeratedRepresentation();
                alternativeEnumeratedRepresentation.setComponentId(items[0]);
                alternativeEnumeratedRepresentation.setUrn(items[1]);
                alternativeRepresentationList.add(alternativeEnumeratedRepresentation);
            }
        }

        return alternativeRepresentationList;
    }

    private NoticesRestInternalService getNoticesRestInternalService() {
        return (NoticesRestInternalService) ApplicationContextProvider.getApplicationContext().getBean(NoticesRestInternalService.BEAN_ID);
    }
}
