package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang.ClassUtils;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;

public class AvroMapperUtils {

    protected static final String AVRO = "Avro";

    protected static final String           AVRO_MAPPERS_PACKAGE     = "org.siemac.metamac.statistical.resources.core.stream.messages.mappers";

    private static final String             MAPPER_CLASS_NAME_ENDING = "Mapper";

    private static final String             AVRO_TO_DO_METHOD_NAME   = "avro2Do";

    private static final String             DO_TO_AVRO_METHOD_NAME   = "do2Avro";

    protected static DatasetVersionRepository     datasetVersionRepository;
    protected static DatasetRepository            datasetRepository;
    protected static PublicationVersionRepository publicationVersionRepository;
    protected static PublicationRepository        publicationRepository;
    protected static ConfigurationService         configurationService;

    public static DatasetVersionRepository getDatasetVersionRepository() {
        datasetVersionRepository = ApplicationContextProvider.getApplicationContext().getBean(DatasetVersionRepository.class);
        return datasetVersionRepository;
    }

    protected static DatasetRepository getDatasetRepository() {
        datasetRepository = ApplicationContextProvider.getApplicationContext().getBean(DatasetRepository.class);
        return datasetRepository;
    }

    protected static PublicationRepository getPublicationRepository() {
        publicationRepository = ApplicationContextProvider.getApplicationContext().getBean(PublicationRepository.class);
        return publicationRepository;
    }

    protected static PublicationVersionRepository getPublicationVersionRepository() {
        publicationVersionRepository = ApplicationContextProvider.getApplicationContext().getBean(PublicationVersionRepository.class);
        return publicationVersionRepository;
    }

    protected static ConfigurationService getConfigurationService() {
        configurationService = ApplicationContextProvider.getApplicationContext().getBean(ConfigurationService.class);
        return configurationService;
    }

    protected static DatasetVersion retrieveDatasetVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion target = null;
        try {
            target = getDatasetVersionRepository().retrieveByUrn(datasetVersionUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, "DatasetVersion of Datasource not found");
        }
        return target;
    }

    public static Dataset retrieveDataset(String datasetUrn) throws MetamacException {
        Dataset target = null;
        try {
            target = getDatasetRepository().retrieveByUrn(datasetUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_NO_DATA);
        }
        return target;
    }


    public static PublicationVersion retrievePublicationVersion(String urn) throws MetamacException {
        PublicationVersion publicationVersion = null;
        try {
            publicationVersion = getPublicationVersionRepository().retrieveByUrn(urn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND);
        }
        return publicationVersion;
    }

    // TODO Podrían eliminarse los metodos setter, ya que los getter no hace uso de los campos de la clase
    protected static void setConfiguratinService(ConfigurationService configurationService) {
        AvroMapperUtils.configurationService = configurationService;
    }

    protected static void setDatasetVersionRepository(DatasetVersionRepository repository) {
        AvroMapperUtils.datasetVersionRepository = repository;
    }

    protected static void setDatasetRepository(DatasetRepository repository) {
        AvroMapperUtils.datasetRepository = repository;
    }

    public static void setPublicationVersionRepository(PublicationVersionRepository publicationVersionRepository) {
        AvroMapperUtils.publicationVersionRepository = publicationVersionRepository;
    }

    public static void setPublicationRepository(PublicationRepository publicationRepository) {
        AvroMapperUtils.publicationRepository = publicationRepository;
    }

    public static Object avro2Do(SpecificRecord source) throws MetamacException {
        return genericMapper(source, AVRO_TO_DO_METHOD_NAME);
    }

    public static SpecificRecord do2Avro(Object source) throws MetamacException {
        return (SpecificRecord) genericMapper(source, DO_TO_AVRO_METHOD_NAME);
    }

    protected static Object genericMapper(Object source, String methodName) throws MetamacException {
        Object target = null;
        try {
            Class<?> avroMapperClazz = getAvroMapperClass(source);
            Method method = avroMapperClazz.getMethod(methodName, source.getClass());
            target = method.invoke(null, source);
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new MetamacException();
        }
        return target;
    }

    protected static Class<?> getAvroMapperClass(Object source) throws ClassNotFoundException {
        String avroMapperClazzName = generateMapperClassName(source);
        Class<?> avroMapperClazz = Class.forName(avroMapperClazzName);
        return avroMapperClazz;
    }

    protected static String generateMapperClassName(Object source) {
        String clazzName = source.getClass().getSimpleName();
        StringBuffer avroMapperClazzName = new StringBuffer(AVRO_MAPPERS_PACKAGE).append(ClassUtils.PACKAGE_SEPARATOR).append(clazzName);
        if (!SpecificRecord.class.isAssignableFrom(source.getClass())) {
            avroMapperClazzName.append(AVRO);
        }
        avroMapperClazzName.append(MAPPER_CLASS_NAME_ENDING);
        return avroMapperClazzName.toString();
    }

    public static String getSelfLink(ExternalItem source) throws MetamacException {
        RestMapper restMapper = new RestMapper();
        String statisticalResourcesApiInternalEndpointV10 = getConfigurationService().retrieveStatisticalResourcesInternalApiUrlBase();
        return restMapper.createSelfLink(source, statisticalResourcesApiInternalEndpointV10);
    }

    public static String selfLink2Uri(String url) throws MetamacException {
        String base = getConfigurationService().retrieveStatisticalResourcesInternalApiUrlBase();
        return CoreCommonUtil.externalItemUrlDtoToUrlDo(base, url);
    }


}
