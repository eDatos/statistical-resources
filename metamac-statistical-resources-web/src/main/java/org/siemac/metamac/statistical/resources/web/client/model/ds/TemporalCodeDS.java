package org.siemac.metamac.statistical.resources.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class TemporalCodeDS extends DataSource {

    public static final String CODE  = "code";
    public static final String TITLE = "title";

    public static final String DTO   = "dto";

    public TemporalCodeDS() {
        DataSourceTextField code = new DataSourceTextField(CODE);
        code.setPrimaryKey(true);
        addField(code);
    }

}
