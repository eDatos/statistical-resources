package org.siemac.metamac.statistical.resources.core.utils.dbunit;

import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.core.common.utils.TableMetadata;
import org.springframework.util.ReflectionUtils;


public class DBUnitTableMetadata extends TableMetadata {
    
    
    public DBUnitTableMetadata(EntityMetadata entityMetadata) {
        super(entityMetadata);
    }
    
    @Override
    protected void addColumnsForProperty(String propName, Object value) {
        if (value != null) {
            if (value instanceof DateTime) {
                DateTime datetime = (DateTime)value;
                columnValues.put(propName, datetime.toString("yyyy-MM-dd HH:mm:ss.SSS"));
                columnValues.put(propName+"_TZ", datetime.getZone().toString());
            } else if (value instanceof Number) {
                columnValues.put(propName, ((Number)value).toString());
            } else if (value instanceof Enum){
                Method method = ReflectionUtils.findMethod(value.getClass(), "getValue");
                if (method != null) {
                    try {
                        columnValues.put(propName, method.invoke(value).toString());
                    } catch (Exception e) {
                        columnValues.put(propName, value.toString());
                    }
                } else {
                    columnValues.put(propName, value.toString());
                }
            } else {
                columnValues.put(propName, value.toString());
            }
        }
    }

}
