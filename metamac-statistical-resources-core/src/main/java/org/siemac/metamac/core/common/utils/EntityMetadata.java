package org.siemac.metamac.core.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class EntityMetadata {
    private String tableName;
    private Map<String,String> properties;
    
    private Map<String,String> relationsDBName;
    
    private Map<String,String> inverseRelations; //key: field name, value:joincolumn
    
    private Map<String,JoinTableMetadata> joinTables;
    
    private String discriminatorColumn;
    private boolean isSingleTable;
    
    public EntityMetadata() {
        properties = new HashMap<String, String>();
        relationsDBName = new HashMap<String, String>();
        inverseRelations = new HashMap<String, String>();
        joinTables = new HashMap<String, EntityMetadata.JoinTableMetadata>();
        discriminatorColumn = null;
        isSingleTable = false;
    }
    
    public Set<String> getJoinTableReferencesNames() {
        return joinTables.keySet();
    }
    
    public EntityMetadata getEntityMetadataForJoinTable(String objName, Object value) {
        return joinTables.get(objName).generateEntityMetadata(getId(), value);
    }
    
    public void addJoinTableReference(String objName, String joinTableName, String joinColumnName, String inverseJoinColumnName) {
        joinTables.put(objName, new JoinTableMetadata(joinTableName, joinColumnName, inverseJoinColumnName));
    }
    
    public void embed(String prefix, EntityMetadata metadata) {
        for (String propName : metadata.properties.keySet()) {
            this.properties.put(prefix+"_"+propName, metadata.properties.get(propName));
        }
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public void setColumnValue(String columnName,Object value) {
        if (value != null) {
            properties.put(columnName, value.toString());
        } else {
            properties.put(columnName, null);
        }
    }
    
    public void setRelationValue(String relationName, String id) {
        String dbColumn = relationsDBName.get(relationName);
        setColumnValue(dbColumn, id);
    }
    
    public void addDiscriminatorColumn(String columnName) {
        discriminatorColumn = columnName;
        properties.put(columnName, null);
    }
    
    public void setDiscriminatorColumnValue(String value) {
        properties.put(discriminatorColumn,value);
    }
    
    public boolean hasDiscriminatorColumn() {
        return discriminatorColumn != null;
    }
    
    public String getId() {
        return properties.get("ID");
    }
    
    public void setId(Long id) {
        setColumnValue("ID", id);
    }
    
    public void setSingleTable(boolean singleTable) {
        isSingleTable = singleTable;
    }
    
    public boolean isSingleTable() {
        return isSingleTable;
    }
    
    public void addInverseRelationNameMapping(String objectName, String joinColumn) {
        inverseRelations.put(objectName, joinColumn);
    }
    
    public void addRelationNameMapping(String objectName, String databaseName) {
        relationsDBName.put(objectName, databaseName);
    }
    
    public Set<String> getRelationsNames() {
        return relationsDBName.keySet();
    }
    
    public Set<String> getInverseRelationsNames() {
        return inverseRelations.keySet();
    }
    
    public String getInverseRelationNameJoinColumn(String objName) {
        return inverseRelations.get(objName);
    }
    
    public String getXmlRepresentation() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<").append(tableName.toUpperCase()).append("\n");
        for (String propName : properties.keySet()) {
            String value = properties.get(propName);
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
    
    private class JoinTableMetadata {
        private String tableName;
        private String joinColumnName;
        private String inverseJoinColumnName;
        
        public JoinTableMetadata(String tableName, String joinColumnName, String inverseJoinColumnName) {
            this.inverseJoinColumnName = inverseJoinColumnName;
            this.tableName = tableName;
            this.joinColumnName = joinColumnName;
        }
        
        public EntityMetadata generateEntityMetadata(Object joinColumnValue, Object inverseJoinColumnValue) {
            EntityMetadata metadata = new EntityMetadata();
            metadata.setTableName(tableName);
            metadata.setColumnValue(joinColumnName, joinColumnValue);
            metadata.setColumnValue(inverseJoinColumnName, inverseJoinColumnValue);
            return metadata;
        }
    }
}
