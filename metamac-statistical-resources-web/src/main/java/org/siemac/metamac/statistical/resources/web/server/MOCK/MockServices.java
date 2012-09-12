package org.siemac.metamac.statistical.resources.web.server.MOCK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.CollectionStructureHierarchyDto;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.CollectionStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockServices {

    private static final String                            DATASET_URI_PREFIX    = "http://siemac.metamac/datasets/";
    private static final String                            COLLECTION_URI_PREFIX = "http://siemac.metamac/collections/";

    private static Map<String, DataSetDto>                 datasets;
    private static Map<String, CollectionDto>              collections;
    private static StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    private static Logger                                  logger                = LoggerFactory.getLogger(MockServices.class);

    //
    // DATASETS
    //

    public static DataSetDto createDataset(ServiceContext ctx, DataSetDto datasetDto) throws MetamacException {
        String identifier = datasetDto.getIdentifier();
        String datasetUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, identifier);
        if (getDatasets().containsKey(datasetUrn)) {
            throw new MetamacException(ServiceExceptionType.DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED, identifier);
        }

        Date now = new Date();

        datasetDto.setId(Long.valueOf(getDatasets().size() + 1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);

        // Audit
        datasetDto.setCreator(ctx.getUserId());
        datasetDto.setDateCreated(now);
        datasetDto.setDateLastUpdate(now);
        datasetDto.setLastUpdateUser(ctx.getUserId());

        // Identifiers
        datasetDto.setUri(DATASET_URI_PREFIX + datasetDto.getIdentifier());
        datasetDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetDto.getIdentifier()));

        // Version
        datasetDto.setVersionDate(now);
        datasetDto.setVersionLogic("01.000");

        // Life cycle
        datasetDto.setCreator(ctx.getUserId());
        datasetDto.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);

        // Content
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setSpatialCoverage(new ArrayList<String>());
        contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        contentMetadata.setTemporalCoverage(new ArrayList<String>());
        contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        datasetDto.setContentMetadata(contentMetadata);

        getDatasets().put(datasetDto.getUrn(), datasetDto);
        return datasetDto;
    }

    public static DataSetDto retrieveDataset(ServiceContext ctx, String datasetUrn) throws MetamacException {
        DataSetDto dataset = getDatasets().get(datasetUrn);
        if (dataset != null) {
            return dataset;
        } else {
            throw new MetamacException(ServiceExceptionType.DATASET_NOT_FOUND, datasetUrn);
        }
    }

    public static DataSetDto updateDataset(ServiceContext ctx, DataSetDto datasetDto) throws MetamacException {
        if (datasetDto.getUuid() == null || getDatasets().containsKey(datasetDto.getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        DataSetDto oldDataset = getDatasets().get(datasetDto.getUrn());

        if (!oldDataset.getOperation().getUrn().equals(datasetDto.getOperation().getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.DATASET_OPERATION);
        }

        Date now = new Date();
        datasetDto.setDateLastUpdate(now);
        datasetDto.setLastUpdateUser(ctx.getUserId());

        datasetDto.setVersion(datasetDto.getVersion() + 1);

        getDatasets().put(datasetDto.getUrn(), datasetDto);

        return datasetDto;
    }

    public static List<DataSetDto> findDatasets(String operationUrn, int firstResult, int maxResults) throws MetamacException {
        List<DataSetDto> datasetsList = new ArrayList<DataSetDto>();
        for (DataSetDto dataset : getDatasets().values()) {
            if (operationUrn.equals(dataset.getOperation().getUrn())) {
                datasetsList.add(dataset);
            }
        }

        int endIndex = datasetsList.size();
        if (endIndex - firstResult > maxResults) {
            endIndex = firstResult + maxResults;
        }
        return new ArrayList<DataSetDto>(datasetsList.subList(firstResult, endIndex));
    }

    private static Map<String, DataSetDto> getDatasets() {
        if (datasets == null) {
            datasets = new HashMap<String, DataSetDto>();
            try {
                List<ExternalItemDto> operations = getOperationsList();
                if (operations.size() > 0) {
                    Random randGen = new Random();
                    createDataset("ds-0001", "Dataset 1", "Dataset 1", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0002", "Dataset 2", "Dataset 2", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0003", "Dataset 3", "Dataset 3", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0004", "Dataset 4", "Dataset 4", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0005", "Dataset 5", "Dataset 5", operations.get(randGen.nextInt(operations.size())));
                    createDataset("ds-0006", "Dataset 6", "Dataset 6", operations.get(randGen.nextInt(operations.size())));
                }
            } catch (MetamacWebException e) {
                logger.error("Error en datasets ", e);
            }
        }
        return datasets;
    }

    private static void createDataset(String code, String title_es, String title_en, ExternalItemDto operation) {
        Date now = new Date();
        DataSetDto datasetDto = new DataSetDto();

        datasetDto.setId(Long.valueOf(datasets.size() + 1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);
        datasetDto.setOperation(operation);

        // Audit
        datasetDto.setCreator("ISTAC_ADMIN");
        datasetDto.setDateCreated(now);
        datasetDto.setDateLastUpdate(now);
        datasetDto.setLastUpdateUser("ISTAC_ADMIN");

        // Identifiers
        datasetDto.setIdentifier(code);
        datasetDto.setTitle(createInternationalString(title_es, title_en));
        datasetDto.setUri(DATASET_URI_PREFIX + code);
        datasetDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, code));

        // Version
        datasetDto.setVersionDate(now);
        datasetDto.setVersionLogic("01.000");

        // Life cycle
        datasetDto.setCreator("ISTAC_ADMIN");
        datasetDto.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);

        // Content
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setSpatialCoverage(new ArrayList<String>());
        contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        contentMetadata.setTemporalCoverage(new ArrayList<String>());
        contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        datasetDto.setContentMetadata(contentMetadata);

        datasets.put(datasetDto.getUrn(), datasetDto);
    }

    //
    // COLLECTIONS
    //

    public static CollectionDto createCollection(ServiceContext ctx, CollectionDto collectionDto) throws MetamacException {
        String identifier = collectionDto.getIdentifier();
        String collectionUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, identifier);
        if (getCollections().containsKey(collectionUrn)) {
            throw new MetamacException(ServiceExceptionType.COLLECTION_ALREADY_EXIST_IDENTIFIER_DUPLICATED, identifier);
        }

        Date now = new Date();

        collectionDto.setId(Long.valueOf(getCollections().size() + 1));
        collectionDto.setUuid(UUID.randomUUID().toString());
        collectionDto.setVersion(1L);

        // Audit
        collectionDto.setCreator(ctx.getUserId());
        collectionDto.setDateCreated(now);
        collectionDto.setDateLastUpdate(now);
        collectionDto.setLastUpdateUser(ctx.getUserId());

        // Identifiers
        collectionDto.setUri(COLLECTION_URI_PREFIX + collectionDto.getIdentifier());
        collectionDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, collectionDto.getIdentifier()));

        // Version
        collectionDto.setVersionDate(now);
        collectionDto.setVersionLogic("01.000");

        // Life cycle
        collectionDto.setCreator(ctx.getUserId());
        collectionDto.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);

        // Content
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setSpatialCoverage(new ArrayList<String>());
        contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        contentMetadata.setTemporalCoverage(new ArrayList<String>());
        contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        collectionDto.setContentMetadata(contentMetadata);

        getCollections().put(collectionDto.getUrn(), collectionDto);
        return collectionDto;
    }

    public static CollectionDto retrieveCollection(ServiceContext ctx, String collectionUrn) throws MetamacException {
        CollectionDto collection = getCollections().get(collectionUrn);
        if (collection != null) {
            return collection;
        } else {
            throw new MetamacException(ServiceExceptionType.COLLECTION_NOT_FOUND, collectionUrn);
        }
    }

    public static CollectionDto updateCollection(ServiceContext ctx, CollectionDto collectionDto) throws MetamacException {
        if (collectionDto.getId() == null) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        CollectionDto oldCollection = getCollections().get(collectionDto.getUrn());

        if (!oldCollection.getOperation().getUrn().equals(collectionDto.getOperation().getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.METADATA_UNMODIFIABLE, ServiceExceptionParameters.COLLECTION_OPERATION);
        }

        Date now = new Date();
        collectionDto.setDateLastUpdate(now);
        collectionDto.setLastUpdateUser(ctx.getUserId());

        collectionDto.setVersion(collectionDto.getVersion() + 1);

        getCollections().put(collectionDto.getUrn(), collectionDto);

        return collectionDto;
    }

    public static List<CollectionDto> findCollections(String operationUrn, int firstResult, int maxResults) throws MetamacException {
        List<CollectionDto> collectionList = new ArrayList<CollectionDto>();
        List<CollectionDto> collectionDtos = new ArrayList<CollectionDto>(getCollections().values());
        for (CollectionDto collection : collectionDtos) {
            if (operationUrn.equals(collection.getOperation().getUrn())) {
                CollectionDto c = collection;
                collectionList.add(c);
            }
        }

        int endIndex = collectionList.size();
        if (endIndex - firstResult > maxResults) {
            endIndex = firstResult + maxResults;
        }
        return new ArrayList<CollectionDto>(collectionList.subList(firstResult, endIndex));
    }

    private static Map<String, CollectionDto> getCollections() {
        if (collections == null) {
            collections = new HashMap<String, CollectionDto>();
            try {
                List<ExternalItemDto> operations = getOperationsList();
                if (operations.size() > 0) {
                    Random randGen = new Random();
                    createCollection("col-0001", "Collection 1", "Collection 1", operations.get(randGen.nextInt(operations.size())));
                    createCollection("col-0002", "Collection 2", "Collection 2", operations.get(randGen.nextInt(operations.size())));
                    createCollection("col-0003", "Collection 3", "Collection 3", operations.get(randGen.nextInt(operations.size())));
                    createCollection("col-0004", "Collection 4", "Collection 4", operations.get(randGen.nextInt(operations.size())));
                    createCollection("col-0005", "Collection 5", "Collection 5", operations.get(randGen.nextInt(operations.size())));
                    createCollection("col-0006", "Collection 6", "Collection 6", operations.get(randGen.nextInt(operations.size())));
                }
            } catch (MetamacWebException e) {
                logger.error("Error en collections ", e);
            }
        }
        return collections;
    }

    private static void createCollection(String code, String title_es, String title_en, ExternalItemDto operation) {
        Date now = new Date();
        CollectionDto collectionDto = new CollectionDto();

        collectionDto.setId(Long.valueOf(collections.size() + 1));
        collectionDto.setUuid(UUID.randomUUID().toString());
        collectionDto.setVersion(1L);
        collectionDto.setOperation(operation);

        // Audit
        collectionDto.setCreator("ISTAC_ADMIN");
        collectionDto.setDateCreated(now);
        collectionDto.setDateLastUpdate(now);
        collectionDto.setLastUpdateUser("ISTAC_ADMIN");

        // Identifiers
        collectionDto.setIdentifier(code);
        collectionDto.setTitle(createInternationalString(title_es, title_en));
        collectionDto.setUri(COLLECTION_URI_PREFIX + code);
        collectionDto.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_COLLECTION_PREFIX, code));

        // Version
        collectionDto.setVersionDate(now);
        collectionDto.setVersionLogic("01.000");

        // Life cycle
        collectionDto.setCreator("ISTAC_ADMIN");
        collectionDto.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);

        // Content
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setLanguage("es");
        contentMetadata.setDescription(createInternationalString(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus"
                        + " et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla "
                        + "consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.", ""));

        contentMetadata.setKeywords(new ArrayList<String>());
        contentMetadata.getKeywords().add("statistic");
        contentMetadata.getKeywords().add("data");
        contentMetadata.getKeywords().add("collection");

        contentMetadata.setSpatialCoverage(new ArrayList<String>());
        contentMetadata.getSpatialCoverage().add("CANARIAS");
        contentMetadata.getSpatialCoverage().add("LANZAROTE");
        contentMetadata.getSpatialCoverage().add("Lanzarote - Este");
        contentMetadata.getSpatialCoverage().add("Lanzarote - Norte");

        contentMetadata.setSpatialCoverageCodes(new ArrayList<String>());
        contentMetadata.getSpatialCoverageCodes().add("ES70");
        contentMetadata.getSpatialCoverageCodes().add("ES708");
        contentMetadata.getSpatialCoverageCodes().add("ES708A01");
        contentMetadata.getSpatialCoverageCodes().add("ES708A02");

        contentMetadata.setTemporalCoverage(new ArrayList<String>());
        contentMetadata.getTemporalCoverage().add("2002 Primer trimestre");
        contentMetadata.getTemporalCoverage().add("2002 Segundo trimestre");
        contentMetadata.getTemporalCoverage().add("2002 Tercer trimestre");

        contentMetadata.setTemporalCoverageCodes(new ArrayList<String>());
        contentMetadata.getTemporalCoverageCodes().add("2002Q1");
        contentMetadata.getTemporalCoverageCodes().add("2002Q2");
        contentMetadata.getTemporalCoverageCodes().add("2002Q3");

        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        contentMetadata.setType(StatisticalResourceTypeEnum.COLLECTION);
        contentMetadata.setCopyrightedDate(new Date());
        collectionDto.setContentMetadata(contentMetadata);

        // Structure
        CollectionStructureHierarchyDto title = new CollectionStructureHierarchyDto();
        title.setType(CollectionStructureHierarchyTypeEnum.TITLE);
        title.setLabel(createInternationalString("Título", "Title"));

        CollectionStructureHierarchyDto chapter1 = new CollectionStructureHierarchyDto();
        chapter1.setType(CollectionStructureHierarchyTypeEnum.CHAPTER);
        chapter1.setLabel(createInternationalString("Capítulo 1", "Chapter 1"));

        CollectionStructureHierarchyDto chapter2 = new CollectionStructureHierarchyDto();
        chapter2.setType(CollectionStructureHierarchyTypeEnum.CHAPTER);
        chapter2.setLabel(createInternationalString("Capítulo 2", "Chapter 2"));

        CollectionStructureHierarchyDto subchapter1 = new CollectionStructureHierarchyDto();
        subchapter1.setType(CollectionStructureHierarchyTypeEnum.SUBCHAPTER1);
        subchapter1.setLabel(createInternationalString("Subcapítulo 1", "Subchapter 1"));

        CollectionStructureHierarchyDto subchapter11 = new CollectionStructureHierarchyDto();
        subchapter11.setType(CollectionStructureHierarchyTypeEnum.SUBCHAPTER2);
        subchapter11.setLabel(createInternationalString("Subcapítulo 11", "Subchapter 11"));

        title.getChildren().add(chapter1);
        title.getChildren().add(chapter2);

        chapter1.getChildren().add(subchapter1);

        subchapter1.getChildren().add(subchapter11);

        collectionDto.setStructure(title);

        collections.put(collectionDto.getUrn(), collectionDto);
    }

    //
    // OTHERS METHODS
    //

    public static StatisticalOperationsRestInternalFacade getStatisticalOperationsRestInternalFacade() {
        if (statisticalOperationsRestInternalFacade == null) {
            statisticalOperationsRestInternalFacade = ApplicationContextProvider.getApplicationContext().getBean(StatisticalOperationsRestInternalFacade.class);
        }
        return statisticalOperationsRestInternalFacade;
    }

    private static InternationalStringDto createInternationalString(String text_es, String text_en) {
        InternationalStringDto intString = new InternationalStringDto();
        {
            LocalisedStringDto loc = new LocalisedStringDto();
            loc.setLabel(text_es);
            loc.setLocale("es");
            intString.addText(loc);
        }
        {
            LocalisedStringDto loc = new LocalisedStringDto();
            loc.setLabel(text_en);
            loc.setLocale("en");
            intString.addText(loc);
        }
        return intString;
    }

    private static List<ExternalItemDto> getOperationsList() throws MetamacWebException {
        List<ExternalItemDto> externalItemDtos = getStatisticalOperationsRestInternalFacade().findOperations(0, Integer.MAX_VALUE, null);
        return externalItemDtos;
    }

}
