package org.siemac.metamac.statistical.resources.core.utils.dbunit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.core.common.utils.TableMetadata;
import org.siemac.metamac.statistical.resources.core.utils.sql.DBMockPersisterBase;
import org.springframework.beans.factory.annotation.Autowired;

public class DBUnitMockPersister extends DBMockPersisterBase {

    @Autowired
    private DBUnitFacade          dbUnitFacade;

    private static List<String>   tableOrder;

    @Override
    protected void persistMocks(List<Object> mocks) throws Exception {

        List<EntityMetadata> mocksMetadata = getMocksMetadata(mocks);

        mocksMetadata = sortMocksMetadata(mocksMetadata);
        
        List<TableMetadata> tableMetadatas = transformEntityToTableMetadata(mocksMetadata);

        Map<String, Set<String>> dynamicDtd = inferDtdFromTableMetadatas(tableMetadatas);

        String filename = createDbUnitFile(tableMetadatas, dynamicDtd);

        URL url = this.getClass().getResource("/dbunit/EmptyDatabase.xml");
        
        dbUnitFacade.setUpDatabase(new File(url.getPath()), new File(filename));
    }

    private List<TableMetadata> transformEntityToTableMetadata(List<EntityMetadata> mocksMetadata) {
        List<TableMetadata> transformList = new ArrayList<TableMetadata>();
        for (EntityMetadata entity : mocksMetadata) {
            transformList.add(new DBUnitTableMetadata(entity));
        }
        return transformList;
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

//    private Map<String, Set<String>> createDtdFromMetadatas(List<EntityMetadata> mocksMetadata) {
//        Map<String, Set<String>> dtdMap = new HashMap<String, Set<String>>();
//        for (EntityMetadata metadata : mocksMetadata) {
//            String tableName = metadata.getTableName();
//            if (tableName != null) {
//                Set<String> attributes = dtdMap.get(tableName);
//                if (attributes == null) {
//                    attributes = new HashSet<String>();
//                }
//                attributes.addAll(metadata.getAllAttributesAndRelations());
//                dtdMap.put(tableName, attributes);
//            }
//        }
//        return dtdMap;
//    }
    
    private Map<String, Set<String>> inferDtdFromTableMetadatas(List<TableMetadata> tableMetadata) {
        Map<String, Set<String>> dtdMap = new HashMap<String, Set<String>>();
        for (TableMetadata metadata : tableMetadata) {
            String tableName = metadata.getTableName();
            if (tableName != null) {
                Set<String> attributes = dtdMap.get(tableName);
                if (attributes == null) {
                    attributes = new HashSet<String>();
                }
                attributes.addAll(metadata.getColumnNames());
                dtdMap.put(tableName, attributes);
            }
        }
        return dtdMap;
    }

    private String createDbUnitFile(List<TableMetadata> metadatas, Map<String, Set<String>> dtdMap) {
        BufferedWriter writer = null;
        String filename = null;
        try {
            File file = File.createTempFile("dbunit-", ".tmp.xml");
            filename = file.getAbsolutePath();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
            writer.write("<dataset>\n");
            for (TableMetadata metadata : metadatas) {
                writer.write(getXmlRepresentation(metadata,dtdMap.get(metadata.getTableName())));
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
    
    private String getXmlRepresentation(TableMetadata metadata, Set<String> attributes) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<").append(metadata.getTableName().toUpperCase()).append("\n");
        for (String propName : attributes) {
            String value = metadata.getColumnValue(propName);
            buffer.append(propName).append("=");
            if (value != null) {
                buffer.append("\"").append(value).append("\"\n");
            } else {
                buffer.append("\"[NULL]\"\n");
            }
        }
        buffer.append("/>\n");
        return buffer.toString();
    }
   
}
