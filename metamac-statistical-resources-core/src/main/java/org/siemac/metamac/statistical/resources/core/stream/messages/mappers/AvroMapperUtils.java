package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.lang.reflect.InvocationTargetException;

import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang.ClassUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

public class AvroMapperUtils {

    protected static final String AVRO = "Avro";

    protected static final String           AVRO_MAPPERS_PACKAGE     = "org.siemac.metamac.statistical.resources.core.stream.messages.mappers";

    private static final String             MAPPER_CLASS_NAME_ENDING = "Mapper";

    private static final String             AVRO_TO_DO_METHOD_NAME   = "avro2Do";

    private static final String             DO_TO_AVRO_METHOD_NAME   = "do2Avro";

    @Autowired
    private static DatasetVersionRepository datasetVersionRepository;

    @Autowired
    private static DatasetRepository        datasetRepository;

    protected static DatasetVersion retrieveDatasetVersion(String datasetVersionUrn) throws MetamacException {
        DatasetVersion target = null;
        try {
            target = datasetVersionRepository.retrieveByUrn(datasetVersionUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_VERSION_NOT_FOUND, "DatasetVersion of Datasource not found");
        }
        return target;
    }

    public static Dataset retrieveDataset(String datasetUrn) throws MetamacException {
        Dataset target = null;
        try {
            target = datasetRepository.retrieveByUrn(datasetUrn);
        } catch (MetamacException e) {
            throw new MetamacException(ServiceExceptionType.DATASET_NO_DATA);
        }
        return target;
    }

    protected static void setDatasetVersionRepository(DatasetVersionRepository repository) {
        AvroMapperUtils.datasetVersionRepository = repository;
    }

    protected static void setDatasetRepository(DatasetRepository repository) {
        AvroMapperUtils.datasetRepository = repository;
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
            target = avroMapperClazz.getMethod(methodName, source.getClass()).invoke(null, source);
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

}
