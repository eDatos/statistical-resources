package org.siemac.metamac.statistical.resources.core.utils.dbunit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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

import org.joda.time.DateTime;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockPersisterBase;
import org.siemac.metamac.statistical.resources.core.utils.sql.DBMockPersisterBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

public class DBUnitMockPersister extends DBMockPersisterBase {

    @Autowired
    private DBUnitFacade          dbUnitFacade;

    private static List<String>   tableOrder;

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

    private String createDbUnitFile(List<EntityMetadata> entitiesMetadata, Map<String, Set<String>> dtdMap) {
        BufferedWriter writer = null;
        String filename = null;
        try {
            File file = File.createTempFile("dbunit-", ".tmp.xml");
            filename = file.getAbsolutePath();
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write("<?xml version='1.0' encoding='UTF-8'?>\n");
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

    // private String createDbUnitDataSet(List<EntityMetadata> entitiesMetadata, Map<String, Set<String>> dtdMap) {
    // Map<String, ITable> tableMetadatas = new HashMap<String, ITableMetaData>();
    //
    // List<ITable> tables = new ArrayList<ITable>();
    // for (EntityMetadata entityMetadata : entitiesMetadata) {
    // Set<String> entityAttributes = dtdMap.get(entityMetadata.getTableName());
    //
    // ITableMetaData tableMetadata = tableMetadatas.get(entityMetadata.getTableName());
    // if (tableMetadata == null) {
    // tableMetadata = createTableMetadataUsingAttrs(entityMetadata.getTableName(), entityAttributes);
    // tableMetadatas.put(entityMetadata.getTableName(), tableMetadata);
    // }
    //
    // tables.add(entityMetadata.getDbUnitTable(entityAttributes));
    // }
    //
    // BufferedWriter writer = null;
    // String filename = null;
    // try {
    // File file = File.createTempFile("dbunit-", ".tmp.xml");
    // filename = file.getAbsolutePath();
    // writer = new BufferedWriter(new FileWriter(file));
    // writer.write("<dataset>\n");
    // for (EntityMetadata metadata : entitiesMetadata) {
    // writer.write(metadata.getXmlRepresentation(dtdMap.get(metadata.getTableName())));
    // }
    // writer.write("</dataset>");
    // } catch (Exception e) {
    // throw new RuntimeException("The generated DbUnit file could not be generated ", e);
    // } finally {
    // if (writer != null) {
    // try {
    // writer.close();
    // } catch (Exception e) {
    // // NOTHING
    // }
    // }
    // }
    // return filename;
    // }
    //
    // private ITableMetaData createTableMetadataUsingAttrs(String tableName, Set<String> attributes) {
    // Column[] columns = new Column[attributes.size()];
    // List<String> attrs = new ArrayList<String>(attributes);
    // for (int i = 0; i < attrs.size(); i++) {
    // columns[i] = new Column(attrs.get(i), DataType.NVARCHAR);
    // }
    // return new DefaultTableMetaData(tableName, columns);
    // }

}
