package org.siemac.metamac.statistical.resources.web.server.MOCK;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.MetamacCriteria;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

import com.smartgwt.client.docs.Operations;


public class MockServices {
    
    private static final String STATISTICAL_OPERATIONS_URL = "http://rcorrod:8070/metamac-statistical-operations-web";
    private static final String STATISTICAL_OPERATIONS_URN_PREFIX = "";

    private static Map<String,ExternalItemDto> operationsDtos;
    
    static {
        operationsDtos = new HashMap<String, ExternalItemDto>();
        
        createOperation("OPE-1", "Operación 1", "Operation 1");
        createOperation("OPE-2", "Operación 2", "Operation 2");
        createOperation("OPE-3", "Operación 3", "Operation 3");
        createOperation("OPE-4", "Operación 4", "Operation 4");
        createOperation("OPE-5", "Operación 5", "Operation 5");
        createOperation("OPE-6", "Operación 6", "Operation 6");
        createOperation("OPE-7", "Operación 7", "Operation 7");
        createOperation("OPE-8", "Operación 8", "Operation 8");
        createOperation("OPE-9", "Operación 9", "Operation 9");
        createOperation("OPE-10", "Operación 10", "Operation 10");
        createOperation("OPE-11", "Operación 11", "Operation 11");
        
    }
    
    public static void findOperationsByCondition(ServiceContext ctx, MetamacCriteria criteria) {
        
    }
    
    private static ExternalItemDto createOperation(String code, String text_es, String text_en) {
        ExternalItemDto operation = new ExternalItemDto();
        
        operation.setCode(code);
        operation.setId(Long.valueOf(operationsDtos.size()+1));
        operation.setManagementAppUrl(STATISTICAL_OPERATIONS_URL);
        operation.setTitle(createIntString(text_es, text_en));
        operation.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        operation.setUri("URI/"+code);
        operation.setUrn(STATISTICAL_OPERATIONS_URN_PREFIX+code);
        operation.setUuid(UUID.randomUUID().toString());
        operation.setVersion(1L);
        
        operationsDtos.put(operation.getUrn(), operation);
        return operation;
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

    public static Operations findOperations(int firstResult, int maxResults, String operation) {
        return null;
    }
}
