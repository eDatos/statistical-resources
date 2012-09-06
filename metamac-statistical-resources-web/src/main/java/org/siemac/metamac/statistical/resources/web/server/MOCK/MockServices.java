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
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.dto.AuditMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiersMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.VersionMetadataDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceFormatEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.server.rest.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.web.common.client.utils.UrnUtils;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class MockServices {
    
    private static final String DATASET_URI_PREFIX = "http://siemac.metamac/datasets/";

    private static Map<String,DatasetDto> datasets;
    private static StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;
    
    private static Logger logger = LoggerFactory.getLogger(MockServices.class);
    
    
    public static DatasetDto createDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (datasetDto.getIdentifiersMetadata() != null) {
            String identifier = datasetDto.getIdentifiersMetadata().getIdentifier();
            String datasetUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, identifier);
            if (getDatasets().containsKey(datasetUrn)) {
                throw new MetamacException(ServiceExceptionType.DATASET_ALREADY_EXIST_IDENTIFIER_DUPLICATED,identifier);
            }
        } else {
            throw new MetamacException(CommonServiceExceptionType.METADATA_REQUIRED,ServiceExceptionParameters.IDENTIFIER);
        }
        
        Date now = new Date();
        
        datasetDto.setId(Long.valueOf(getDatasets().size()+1));
        datasetDto.setUuid(UUID.randomUUID().toString());
        datasetDto.setVersion(1L);
        
        AuditMetadataDto auditMetadata = new AuditMetadataDto();
        auditMetadata.setCreator(ctx.getUserId());
        auditMetadata.setDateCreated(now);
        auditMetadata.setDateLastUpdate(now);
        auditMetadata.setLastUpdateUser(ctx.getUserId());
        datasetDto.setAuditMetadata(auditMetadata);
        
        ContentMetadataDto contentMetadata = new ContentMetadataDto();
        contentMetadata.setCoverageSpatial(new ArrayList<String>());
        contentMetadata.setCoverageSpatialCodes(new ArrayList<String>());
        contentMetadata.setCoverageTemporal(new ArrayList<String>());
        contentMetadata.setCoverageTemporalCodes(new ArrayList<String>());
        contentMetadata.setFormat(StatisticalResourceFormatEnum.DS);
        datasetDto.setContentMetadata(contentMetadata);
        
        IdentifiersMetadataDto identifiersMetadata = datasetDto.getIdentifiersMetadata();
        identifiersMetadata.setUri(DATASET_URI_PREFIX+identifiersMetadata.getIdentifier());
        identifiersMetadata.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX,identifiersMetadata.getIdentifier()));
        datasetDto.setIdentifiersMetadata(identifiersMetadata);
        
        LifeCycleMetadataDto lifeCycleMetadata = new LifeCycleMetadataDto();
        lifeCycleMetadata.setCreator(ctx.getUserId());
        lifeCycleMetadata.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        datasetDto.setLifeCycleMetadata(lifeCycleMetadata);
        

        VersionMetadataDto versionMetadata = new VersionMetadataDto();
        versionMetadata.setDateVersion(now);
        versionMetadata.setVersion("01.000");
        datasetDto.setVersionMetadata(versionMetadata);
        
        getDatasets().put(datasetDto.getIdentifiersMetadata().getUrn(), datasetDto);
        return datasetDto;
    }
    
    public static DatasetDto retrieveDataset(ServiceContext ctx, String datasetUrn) throws MetamacException {
        DatasetDto dataset = getDatasets().get(datasetUrn);
        if (dataset != null) {
            return dataset;
        } else {
            throw new MetamacException(ServiceExceptionType.DATASET_NOT_FOUND,datasetUrn);
        }
    }
    
    public static DatasetDto saveDataset(ServiceContext ctx, DatasetDto datasetDto) throws MetamacException {
        if (datasetDto.getUuid() == null || datasetDto.getIdentifiersMetadata() == null || getDatasets().containsKey(datasetDto.getIdentifiersMetadata().getUrn())) {
            throw new MetamacException(CommonServiceExceptionType.UNKNOWN);
        }
        DatasetDto oldDataset = getDatasets().get(datasetDto.getIdentifiersMetadata().getUrn());
        
        if (!oldDataset.getOperationUrn().equals(datasetDto.getOperationUrn())) {
            throw new MetamacException(CommonServiceExceptionType.METADATA_UNMODIFIABLE,ServiceExceptionParameters.DATASET_OPERATION);
        }
        
        Date now = new Date();
        datasetDto.getAuditMetadata().setDateLastUpdate(now);
        datasetDto.getAuditMetadata().setLastUpdateUser(ctx.getUserId());
        
        datasetDto.setVersion(datasetDto.getVersion()+1);
        
        getDatasets().put(datasetDto.getIdentifiersMetadata().getUrn(), datasetDto);
        
        return datasetDto;
    }
    
    public static List<DatasetDto> findDatasets(String operationUrn, int firstResult, int maxResults) throws MetamacException {
        
        List<DatasetDto> datasetsList = new ArrayList<DatasetDto>();
        for (DatasetDto dataset : getDatasets().values()) {
            if (operationUrn.equals(dataset.getOperationUrn())) {
                datasetsList.add(dataset);
            }
        }
        
        int endIndex = datasetsList.size();
        if (endIndex-firstResult > maxResults) {
            endIndex = firstResult+maxResults;
        }
        return new ArrayList<DatasetDto>(datasetsList.subList(firstResult, endIndex));
    }
    
    
    
    
    private static Map<String,DatasetDto> getDatasets() {
        if (datasets == null) {
            datasets = new HashMap<String, DatasetDto>();
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
        datasetDto.setOperationUrn(operation.getUrn());
        
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
        identifiersMetadata.setUrn(UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX,code));
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
    
    
    public static StatisticalOperationsRestInternalFacade getStatisticalOperationsRestInternalFacade() {
        if (statisticalOperationsRestInternalFacade == null) {
            statisticalOperationsRestInternalFacade = ApplicationContextProvider.getApplicationContext().getBean(StatisticalOperationsRestInternalFacade.class);
        }
        return statisticalOperationsRestInternalFacade;
    }
    
    private static List<ExternalItemDto> getOperationsList() throws MetamacWebException {
        List<ExternalItemDto> externalItemDtos = getStatisticalOperationsRestInternalFacade().findOperations(0, Integer.MAX_VALUE, null);
        return externalItemDtos;
    }


}
