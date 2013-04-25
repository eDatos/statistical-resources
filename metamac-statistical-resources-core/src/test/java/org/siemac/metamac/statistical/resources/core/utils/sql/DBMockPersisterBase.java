package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockPersisterBase;
import org.springframework.util.ReflectionUtils;


public abstract class DBMockPersisterBase extends MockPersisterBase {
    private static final String   SERIALIZE_METHOD_NAME = "serializeDbXml";
    protected static final String URI_MOCK_PREFIX       = "lorem/ipsum/dolor/sit/amet/";
    protected static final String URN_MOCK_PREFIX       = "urn:lorem.ipsum.dolor.infomodel.package.Resource=";
    
    private long                  nextId                = 1L;
    
    protected List<EntityMetadata> getMocksMetadata(List<Object> mocks) throws Exception {
        MapIdenticalKey metadataMatching = new MapIdenticalKey();
        for (Object mock : mocks) {
            resolveDependencies(metadataMatching, mock);
        }
        return metadataMatching.getEntitiesMetadata();
    }

    private EntityMetadata resolveDependencies(MapIdenticalKey metadataMatching, Object mock) throws Exception {

        if (metadataMatching.hasMock(mock)) {
            return metadataMatching.getEntityMetadataForMock(mock);
        } else {
            fillIdentiyAndAuditMetadata(mock);
            List<EntityMetadata> metadatas = getEntityMetadataFromObject(mock);

            Long idMock = getNextId();

            trySetIdOnMockObject(mock, idMock);

            metadataMatching.addProcessingMock(mock);

            EntityMetadata metadataRepresentingMock = metadatas.get(metadatas.size() - 1); // last metadata represents mock

            for (int i = 0; i < metadatas.size(); i++) {
                EntityMetadata metadata = metadatas.get(i);

                Long idMetadata;
                if (metadata == metadataRepresentingMock) {
                    idMetadata = idMock;
                } else {
                    idMetadata = getNextId();
                }

                metadata.setId(idMetadata);

                if (metadata == metadataRepresentingMock) {
                    metadataMatching.setEntityMetadataForMock(mock, metadata);
                } else {
                    metadataMatching.addUnMatchedEntityMetadata(metadata);
                }
                
                resolveDirectDependencies(mock, metadata, metadataMatching);

                resolveJoinTableDependencies(mock, metadata, metadataMatching);


                resolveReverseDependencies(mock, metadata, metadataMatching);
            }
            metadataMatching.removeProcessingMock(mock);
            return metadataRepresentingMock;
        }
    }
    
    public static String getUriMock() {
        return URI_MOCK_PREFIX + MetamacMocks.mockString(5);
    }

    public static String getUrnMock() {
        return URN_MOCK_PREFIX + MetamacMocks.mockString(10);
    }

    
    private Object fillIdentiyAndAuditMetadata(Object mock) {
        fillField(mock, "uuid", UUID.randomUUID().toString());
        fillField(mock, "version", Long.valueOf(0));
        fillFieldIfNull(mock, "urn", getUrnMock());
        fillFieldIfNull(mock, "uri", getUriMock());
        fillField(mock, "createdDate", new DateTime());
        fillField(mock, "createdBy", "user1");
        fillField(mock, "lastUpdated", new DateTime());
        fillField(mock, "lastUpdatedBy", "user2");

        return mock;
    }
    
    private Object fillField(Object mock, String fieldName, Object value) {
        try {
            Field field = ReflectionUtils.findField(mock.getClass(), fieldName);
            if (field != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    field.set(mock, value);
                    field.setAccessible(false);
                } else {
                    field.set(mock, value);
                }
            }
            return mock;
        } catch (Exception e) {
            throw new RuntimeException("Error setting " + fieldName + " on mock object ", e);
        }
    }
    
    private Object fillFieldIfNull(Object mock, String fieldName, Object value) {
        try {
            Field field = ReflectionUtils.findField(mock.getClass(), fieldName);
            if (field != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    Object retValue = field.get(mock);
                    if (retValue == null) {
                        field.set(mock, value);
                    }
                    field.setAccessible(false);
                } else {
                    field.set(mock, value);
                }
            }
            return mock;
        } catch (Exception e) {
            throw new RuntimeException("Error setting " + fieldName + " on mock object ", e);
        }
    }

    private void resolveDirectDependencies(Object mock, EntityMetadata metadata, MapIdenticalKey metadataMatching) throws Exception {
        Map<String, Object> references = getObjectReferences(mock, metadata);
        for (String refName : references.keySet()) {
            Object reference = references.get(refName);
            if (reference != null) {
                EntityMetadata refMetadata = resolveDependencies(metadataMatching, reference);
                metadata.setRelationValue(refName, refMetadata.getId());
            } else {
                metadata.setRelationValue(refName, null);
            }
        }
    }

    private void resolveJoinTableDependencies(Object mock, EntityMetadata metadata, MapIdenticalKey metadataMatching) throws Exception {
        Map<String, List<Object>> joinTableReferences = getObjectJoinTablesReferences(mock, metadata);

        for (String joinRefName : joinTableReferences.keySet()) {
            List<Object> joinReferences = joinTableReferences.get(joinRefName);

            for (Object reference : joinReferences) {
                EntityMetadata refMetadata = resolveDependencies(metadataMatching, reference);
                EntityMetadata joinMetadata = metadata.getEntityMetadataForJoinTable(joinRefName, refMetadata.getId());
                metadataMatching.addUnMatchedEntityMetadata(joinMetadata);
            }
        }
    }

    private void resolveReverseDependencies(Object mock, EntityMetadata metadata, MapIdenticalKey metadataMatching) throws Exception {
        Map<String, List<Object>> inverseReferences = getObjectInverseReferences(mock, metadata);

        for (String refName : inverseReferences.keySet()) {
            List<Object> invReference = inverseReferences.get(refName);
            for (Object ref : invReference) {
                if (!metadataMatching.isProcessingMock(ref)) {
                    resolveDependencies(metadataMatching, ref);
                }
            }
        }
    }

    private void trySetIdOnMockObject(Object mock, Long id) {
        try {
            Field field = ReflectionUtils.findField(mock.getClass(), "id");
            if (field != null) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                    field.set(mock, id);
                    field.setAccessible(false);
                } else {
                    field.set(mock, id);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error setting Id on mock object ", e);
        }
    }

    private Long getNextId() {
        return nextId++;
    }

    private Map<String, Object> getObjectReferences(Object mock, EntityMetadata metadata) throws Exception {
        Map<String, Object> references = new HashMap<String, Object>();
        for (String fieldName : metadata.getRelationsNames()) {
            references.put(fieldName, getFieldContent(mock, fieldName));
        }
        return references;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, List<Object>> getObjectInverseReferences(Object mock, EntityMetadata metadata) throws Exception {
        Map<String, List<Object>> references = new HashMap<String, List<Object>>();
        for (String fieldName : metadata.getInverseRelationsNames()) {
            Object value = getFieldContent(mock, fieldName);
            List<Object> values = null;
            if (value instanceof Collection) {
                values = new ArrayList<Object>((Collection) value);
            } else {
                values = Arrays.asList(value);
            }
            references.put(fieldName, values);
        }
        return references;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, List<Object>> getObjectJoinTablesReferences(Object mock, EntityMetadata metadata) throws Exception {
        Map<String, List<Object>> references = new HashMap<String, List<Object>>();
        for (String fieldName : metadata.getJoinTableReferencesNames()) {
            Object value = getFieldContent(mock, fieldName);
            List<Object> values = null;
            if (value instanceof Collection) {
                values = new ArrayList<Object>((Collection) value);
            } else {
                values = Arrays.asList(value);
            }
            references.put(fieldName, values);
        }
        return references;
    }

    private Object getFieldContent(Object mock, String fieldName) throws Exception {
        Field field = ReflectionUtils.findField(mock.getClass(), fieldName);
        Object fieldValue = null;
        if (!field.isAccessible()) {
            field.setAccessible(true);
            fieldValue = field.get(mock);
            field.setAccessible(false);
        } else {
            fieldValue = field.get(mock);
        }
        return fieldValue;
    }

    @SuppressWarnings("unchecked")
    private List<EntityMetadata> getEntityMetadataFromObject(Object mock) {
        try {
            Method method = mock.getClass().getMethod(SERIALIZE_METHOD_NAME);
            Object returnObj = method.invoke(mock);
            return (List<EntityMetadata>) returnObj;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Class " + mock.getClass() + " has not method " + SERIALIZE_METHOD_NAME + ". Method it's needed for correct use of Mocks ", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error calling " + SERIALIZE_METHOD_NAME + " for mock class " + mock.getClass(), e);
        }
    }
    

    
   
    
    private class MapIdenticalKey {

        private List<Object>         mocks;
        private List<EntityMetadata> metadatas;

        private List<EntityMetadata> unMatchedMetadatas;

        private List<Object>         processingMocks;

        public MapIdenticalKey() {
            mocks = new ArrayList<Object>();
            processingMocks = new ArrayList<Object>();
            metadatas = new ArrayList<EntityMetadata>();
            unMatchedMetadatas = new ArrayList<EntityMetadata>();
        }

        public void setEntityMetadataForMock(Object mock, EntityMetadata metadata) {
            mocks.add(mock);
            metadatas.add(metadata);
        }

        public void addUnMatchedEntityMetadata(EntityMetadata metadata) {
            unMatchedMetadatas.add(metadata);
        }

        public void addProcessingMock(Object mock) {
            processingMocks.add(mock);
        }

        public void removeProcessingMock(Object mock) {
            int index = lookUpExactMockIndex(mock, processingMocks);
            if (index >= 0) {
                processingMocks.remove(index);
            }
        }

        public boolean isProcessingMock(Object mock) {
            return lookUpExactMockIndex(mock, processingMocks) >= 0;
        }

        public boolean hasMock(Object mock) {
            return lookUpExactMockIndex(mock, mocks) >= 0;
        }

        private int lookUpExactMockIndex(Object mock, List<Object> list) {
            for (int i = 0; i < list.size(); i++) {
                if (mock == list.get(i)) {
                    return i;
                }
            }
            return -1;
        }

        public EntityMetadata getEntityMetadataForMock(Object mock) {
            int index = lookUpExactMockIndex(mock, mocks);
            if (index >= 0) {
                return metadatas.get(index);
            }
            return null;
        }

        public List<EntityMetadata> getEntitiesMetadata() {
            List<EntityMetadata> list = new ArrayList<EntityMetadata>(metadatas);
            list.addAll(unMatchedMetadatas);
            return list;
        }

    }
}