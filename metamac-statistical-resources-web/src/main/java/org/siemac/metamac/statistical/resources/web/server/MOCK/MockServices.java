package org.siemac.metamac.statistical.resources.web.server.MOCK;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.statistical.resources.core.dto.AuditMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiersMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionMetadataDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class MockServices {
    
    private static final String DATASET_URN_PREFIX = "ds-";
    private static final String DATASET_URI_PREFIX = "http://ds-";

    private static Map<String,DatasetDto> datasets;
    private static StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;
    
    private static Logger logger = LoggerFactory.getLogger(MockServices.class);
    
    private static Map<String,DatasetDto> getDatasets() {
        if (datasets == null) {
            datasets = new HashMap<String, DatasetDto>();
            try {
                List<ExternalItemDto> operations = getOperationsList();
                if (operations.size() > 0) {
                    Random randGen = new Random();
                    createDataset("0001", "Dataset 1", "Dataset 1", operations.get(randGen.nextInt(operations.size())));
                    createDataset("0002", "Dataset 2", "Dataset 2", operations.get(randGen.nextInt(operations.size())));
                    createDataset("0003", "Dataset 3", "Dataset 3", operations.get(randGen.nextInt(operations.size())));
                    createDataset("0004", "Dataset 4", "Dataset 4", operations.get(randGen.nextInt(operations.size())));
                    createDataset("0005", "Dataset 5", "Dataset 5", operations.get(randGen.nextInt(operations.size())));
                    createDataset("0006", "Dataset 6", "Dataset 6", operations.get(randGen.nextInt(operations.size())));
                }
            } catch (MetamacWebException e) {
                logger.error("Error en datasets ",e);
            }
        }
        return datasets;
    }
    
    private static void createDataset(String code, String title_es, String title_en, ExternalItemDto operation) {
        Date now = new Date();
        DatasetDto datasetDto = new DatasetDto();
        
        datasetDto.setId(Long.valueOf(datasets.size()+1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);
        datasetDto.setRelatedOperation(operation);
        
        AuditMetadataDto auditMetadata = new AuditMetadataDto();
        auditMetadata.setCreator("ISTAC_ADMIN");
        auditMetadata.setDateCreated(now);
        auditMetadata.setDateLastUpdate(now);
        auditMetadata.setLastUpdateUser("ISTAC_ADMIN");
        datasetDto.setAuditMetadata(auditMetadata);
        
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setCoverageSpatial(new ArrayList<String>());
        contentMetadata.setCoverageSpatialCodes(new ArrayList<String>());
        contentMetadata.setCoverageTemporal(new ArrayList<String>());
        contentMetadata.setCoverageTemporalCodes(new ArrayList<String>());
        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        datasetDto.setContentMetadata(contentMetadata);
        
        IdentifiersMetadataDto identifiersMetadata = new IdentifiersMetadataDto();
        identifiersMetadata.setIdentifier(code);
        identifiersMetadata.setTitle(createIntString(title_es, title_en));
        identifiersMetadata.setUri(DATASET_URI_PREFIX+code);
        identifiersMetadata.setUrn(DATASET_URN_PREFIX+code);
        datasetDto.setIdentifiersMetadata(identifiersMetadata);
        
        LifeCycleMetadataDto lifeCycleMetadata = new LifeCycleMetadataDto();
        lifeCycleMetadata.setCreator("ISTAC_ADMIN");
        lifeCycleMetadata.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        datasetDto.setLifeCycleMetadata(lifeCycleMetadata);
        

        VersionMetadataDto versionMetadata = new VersionMetadataDto();
        versionMetadata.setDateVersion(now);
        versionMetadata.setVersion("01.000");
        datasetDto.setVersionMetadata(versionMetadata);
        
        datasets.put(datasetDto.getIdentifiersMetadata().getUrn(), datasetDto);
    }
    
    private static InternationalStringDto createIntString(String text_es, String text_en) {
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
        if (statisticalOperationsRestInternalFacade == null) {
            statisticalOperationsRestInternalFacade = ApplicationContextProvider.getApplicationContext().getBean(StatisticalOperationsRestInternalFacade.class);
        }
        
        List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>();
        Operations result = statisticalOperationsRestInternalFacade.findOperations(0, Integer.MAX_VALUE, null);
        if (result != null && result.getOperations() != null) {
            for (Resource resource : result.getOperations()) {
                ExternalItemDto externalItemDto = new ExternalItemDto(resource.getId(), resource.getSelfLink(), resource.getUrn(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION,
                        DtoUtils.getInternationalStringDtoFromInternationalString(resource.getTitle()));
                externalItemDtos.add(externalItemDto);
            }
        }
        return externalItemDtos;
    }

    public static List<DatasetDto> findDatasets(String operationUrn, int firstResult, int maxResults) throws MetamacException {
        
        List<DatasetDto> datasetsList = new ArrayList<DatasetDto>();
        for (DatasetDto dataset : getDatasets().values()) {
            if (operationUrn.equals(dataset.getRelatedOperation().getUrn())) {
                datasetsList.add(dataset);
            }
        }
        
        int endIndex = datasetsList.size();
        if (endIndex-firstResult > maxResults) {
            endIndex = firstResult+maxResults;
        }
        return new ArrayList<DatasetDto>(datasetsList.subList(firstResult, endIndex));
    }
}
