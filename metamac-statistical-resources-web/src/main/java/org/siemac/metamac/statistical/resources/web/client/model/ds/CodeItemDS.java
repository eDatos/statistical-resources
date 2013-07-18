package org.siemac.metamac.statistical.resources.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class CodeItemDS extends DataSource {

    public static final String CODE  = "code";
    public static final String URN   = "urn";
    public static final String TITLE = "title";

    public static final String DTO   = "dto";

    public CodeItemDS() {
        DataSourceTextField code = new DataSourceTextField(CODE);
        code.setPrimaryKey(true);
        addField(code);
    }

}
