package org.siemac.metamac.statistical.resources.core.dbunit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.statistical.resources.core.mocks.MockPersisterBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

public class DBUnitMockPersister extends MockPersisterBase {

    private static final String   SERIALIZE_METHOD_NAME = "serializeDbXml";
    protected static final String URI_MOCK_PREFIX       = "lorem/ipsum/dolor/sit/amet/";
    protected static final String URN_MOCK_PREFIX       = "urn:lorem.ipsum.dolor.infomodel.package.Resource=";

    @Autowired
    private DBUnitFacade          dbUnitFacade;

    private long                  nextId                = 1L;

    private static List<String>   tableOrder;

    // Dbunit does not need to sort the entities
    @Override
    protected List<Object> sortMocksBasedOnDependencies(List<Object> mocks) {
        return mocks;
    }

    @Override
    protected void persistMocks(List<Object> mocks) throws Exception {

        List<EntityMetadata> mocksMetadata = getMocksMetadata(mocks);

        mocksMetadata = sortMocksMetadata(mocksMetadata);

        Map<String, Set<String>> dynamicDtd = createDtdFromMetadatas(mocksMetadata);

        String filename = createDbUnitFile(mocksMetadata, dynamicDtd);

        URL url = this.getClass().getResource("/dbunit/EmptyDatabase.xml");
        dbUnitFacade.cleanDatabase(new File(url.getPath()));

        dbUnitFacade.setUpDatabase(new File(filename));
    }

    private List<EntityMetadata> sortMocksMetadata(List<EntityMetadata> mocksMetadatas) {
        List<String> orderedTableNames = getTableOrder();
        List<EntityMetadata> orderedMetadatas = new ArrayList<EntityMetadata>();
        for (String tableName : orderedTableNames) {
            for (EntityMetadata metadata : mocksMetadatas) {
                if (metadata.getTableName().equals(tableName)) {
                    orderedMetadatas.add(metadata);
                }
            }
        }

        return orderedMetadatas;
    }

    private List<String> getTableOrder() {
        if (tableOrder == null) {
            try {
                URL url = this.getClass().getResource("/dbunit/oracle.properties");
                Properties prop = new Properties();
                prop.load(new FileInputStream(url.getFile()));
                String sequencesStr = prop.getProperty("tables");
                tableOrder = Arrays.asList(sequencesStr.split(","));
            } catch (Exception e) {
                throw new IllegalStateException("Error loading properties which all tablenames are specified", e);
            }
        }
        return tableOrder;
    }

    private Map<String, Set<String>> createDtdFromMetadatas(List<EntityMetadata> mocksMetadata) {
        Map<String, Set<String>> dtdMap = new HashMap<String, Set<String>>();
        for (EntityMetadata metadata : mocksMetadata) {
            String tableName = metadata.getTableName();
            if (tableName != null) {
                Set<String> attributes = dtdMap.get(tableName);
                if (attributes == null) {
                    attributes = new HashSet<String>();
                }
                attributes.addAll(metadata.getAllAttributesAndRelations());
                dtdMap.put(tableName, attributes);
            }
        }
        return dtdMap;
    }

    private List<EntityMetadata> getMocksMetadata(List<Object> mocks) throws Exception {
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

                resolveDirectDependencies(mock, metadata, metadataMatching);

                resolveJoinTableDependencies(mock, metadata, metadataMatching);

                if (metadata == metadataRepresentingMock) {
                    metadataMatching.setEntityMetadataForMock(mock, metadata);
                } else {
                    metadataMatching.addUnMatchedEntityMetadata(metadata);
                }

                resolveReverseDependencies(mock, metadata, metadataMatching);
            }
            metadataMatching.removeProcessingMock(mock);
            return metadataRepresentingMock;
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

    private String createDbUnitFile(List<EntityMetadata> entitiesMetadata, Map<String, Set<String>> dtdMap) {
        BufferedWriter writer = null;
        String filename = null;
        try {
            File file = File.createTempFile("dbunit-", ".tmp.xml");
            filename = file.getAbsolutePath();
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("<dataset>\n");
            for (EntityMetadata metadata : entitiesMetadata) {
                writer.write(metadata.getXmlRepresentation(dtdMap.get(metadata.getTableName())));
            }
            writer.write("</dataset>");
        } catch (Exception e) {
            throw new RuntimeException("The generated DbUnit file could not be generated ", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e) {
                    // NOTHING
                }
            }
        }
        return filename;
    }

//    private String createDbUnitDataSet(List<EntityMetadata> entitiesMetadata, Map<String, Set<String>> dtdMap) {
//        Map<String, ITable> tableMetadatas = new HashMap<String, ITableMetaData>();
//
//        List<ITable> tables = new ArrayList<ITable>();
//        for (EntityMetadata entityMetadata : entitiesMetadata) {
//            Set<String> entityAttributes = dtdMap.get(entityMetadata.getTableName());
//
//            ITableMetaData tableMetadata = tableMetadatas.get(entityMetadata.getTableName());
//            if (tableMetadata == null) {
//                tableMetadata = createTableMetadataUsingAttrs(entityMetadata.getTableName(), entityAttributes);
//                tableMetadatas.put(entityMetadata.getTableName(), tableMetadata);
//            }
//
//            tables.add(entityMetadata.getDbUnitTable(entityAttributes));
//        }
//
//        BufferedWriter writer = null;
//        String filename = null;
//        try {
//            File file = File.createTempFile("dbunit-", ".tmp.xml");
//            filename = file.getAbsolutePath();
//            writer = new BufferedWriter(new FileWriter(file));
//            writer.write("<dataset>\n");
//            for (EntityMetadata metadata : entitiesMetadata) {
//                writer.write(metadata.getXmlRepresentation(dtdMap.get(metadata.getTableName())));
//            }
//            writer.write("</dataset>");
//        } catch (Exception e) {
//            throw new RuntimeException("The generated DbUnit file could not be generated ", e);
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (Exception e) {
//                    // NOTHING
//                }
//            }
//        }
//        return filename;
//    }
//
//    private ITableMetaData createTableMetadataUsingAttrs(String tableName, Set<String> attributes) {
//        Column[] columns = new Column[attributes.size()];
//        List<String> attrs = new ArrayList<String>(attributes);
//        for (int i = 0; i < attrs.size(); i++) {
//            columns[i] = new Column(attrs.get(i), DataType.NVARCHAR);
//        }
//        return new DefaultTableMetaData(tableName, columns);
//    }

    private Object fillIdentiyAndAuditMetadata(Object mock) {
        fillField(mock, "uuid", UUID.randomUUID().toString());
        fillField(mock, "version", Long.valueOf(0));
        fillField(mock, "urn", getUrnMock());
        fillField(mock, "uri", getUriMock());
        fillField(mock, "createdDate", new DateTime());
        fillField(mock, "createdBy", "user1");
        fillField(mock, "lastUpdated", new DateTime());
        fillField(mock, "lastUpdatedBy", "user2");

        return mock;
    }

    private static String getUriMock() {
        return URI_MOCK_PREFIX + MetamacMocks.mockString(5);
    }

    private static String getUrnMock() {
        return URN_MOCK_PREFIX + MetamacMocks.mockString(10);
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
