package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.core.common.utils.TableMetadata;
import org.springframework.util.ReflectionUtils;


public class SqlTableMetadata extends TableMetadata {

    
    public SqlTableMetadata(EntityMetadata entityMetadata) {
        super(entityMetadata);
    }
    
    @Override
    protected void addColumnsForProperty(String propName, Object value) {
        if (value != null) {
            if (value instanceof DateTime) {
                DateTime datetime = (DateTime)value;
                columnValues.put(propName, "TO_TIMESTAMP('"+datetime.toString("yyyy-MM-dd HH:mm:ss.SSS")+"','YYYY-MM-DD HH24:MI:SS.FF')");
                columnValues.put(propName+"_TZ", "'"+datetime.getZone().toString()+"'");
            } else if (value instanceof Number) {
                columnValues.put(propName, ((Number)value).toString());
            } else if (value instanceof Enum){
                Method method = ReflectionUtils.findMethod(value.getClass(), "getValue");
                if (method != null) {
                    try {
                        columnValues.put(propName, "'"+method.invoke(value)+"'");
                    } catch (Exception e) {
                        columnValues.put(propName, "'"+value.toString()+"'");
                    }
                } else {
                    columnValues.put(propName, "'"+value.toString()+"'");
                }
            } else {
                columnValues.put(propName, "'"+value.toString()+"'");
            }
        }   
    }
}
