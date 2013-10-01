package org.siemac.metamac.statistical.resources.core.utils.sql;

import java.lang.reflect.Method;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.utils.EntityMetadata;
import org.siemac.metamac.core.common.utils.TableMetadata;
import org.springframework.util.ReflectionUtils;

public class H2SqlTableMetadata extends TableMetadata {

    public H2SqlTableMetadata(EntityMetadata entityMetadata) {
        super(entityMetadata);
    }

    @Override
    protected void addColumnsForProperty(String propName, Object value) {
        if (value != null) {
            if (value instanceof DateTime) {
                DateTime datetime = (DateTime) value;
                columnValues.put(propName, " PARSEDATETIME('" + datetime.toString("yyyy-MM-dd HH:mm:ss.SSS") + "','yyyy-MM-dd HH:mm:ss.SSS')");
                columnValues.put(propName + "_TZ", "'" + datetime.getZone().toString() + "'");
            } else if (value instanceof Number) {
                columnValues.put(propName, ((Number) value).toString());
            } else if (value instanceof Enum) {
                Method method = ReflectionUtils.findMethod(value.getClass(), "getValue");
                if (method != null) {
                    try {
                        columnValues.put(propName, "'" + method.invoke(value) + "'");
                    } catch (Exception e) {
                        columnValues.put(propName, "'" + value.toString() + "'");
                    }
                } else {
                    columnValues.put(propName, "'" + value.toString() + "'");
                }
            } else if (value instanceof Boolean) {
                columnValues.put(propName, (Boolean) value ? "1" : "0");
            } else {
                columnValues.put(propName, "'" + value.toString() + "'");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("Table: " + getTableName());
        strBuilder.append(" { \n");
        for (String column : getColumnNames()) {
            strBuilder.append("        ").append(column).append(":").append(getColumnValue(column)).append("\n");
        }
        strBuilder.append("}\n");
        return strBuilder.toString();
    }
}
