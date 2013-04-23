package org.siemac.metamac.core.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.util.ReflectionUtils;


public abstract class TableMetadata {
    private String tableName;
    protected Map<String,String> columnValues;
    
    public TableMetadata(EntityMetadata metadata) {
        columnValues = new HashMap<String, String>();
        tableName = metadata.getTableName();
        for (String propertyName : metadata.getAllPropertiesAndRelations()) {
            Object value = metadata.getPropertyValue(propertyName);
            addColumnsForProperty(propertyName, value);
        }
    }
    
    public String getTableName() {
        return tableName;
    }
    
    
    public String getColumnValue(String columnName) {
        return columnValues.get(columnName);
    }
    
    public List<String> getColumnNames() {
        return new ArrayList<String>(columnValues.keySet());
    }
    
    protected abstract void addColumnsForProperty(String propName, Object value);
    
}
