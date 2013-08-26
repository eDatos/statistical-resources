package org.siemac.metamac.statistical.resources.web.client.model.ds;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class CodeItemDS extends DataSource {

    public static final String CODE         = "ci-code";
    public static final String URN          = "ci-urn";
    public static final String TITLE        = "ci-title";
    public static final String DIMENSION_ID = "ci-dim-id";

    public static final String DTO          = "ci-dto";

    public CodeItemDS() {
        DataSourceTextField code = new DataSourceTextField(CODE);
        code.setPrimaryKey(true);
        addField(code);
    }
}
