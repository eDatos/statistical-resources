package org.siemac.metamac.statistical.resources.web.client.operation.model.ds;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class OperationDS extends DataSource {

    public static final String IDENTIFIER = "oper-identifier";
    public static final String URI        = "oper-uri";
    public static final String URN        = "oper-urn";
    public static final String TITLE      = "oper-title";

    public static String       DTO        = "oper-dto";

    public OperationDS() {
        DataSourceIntegerField identifier = new DataSourceIntegerField(IDENTIFIER, StatisticalResourcesWeb.getConstants().operationIdentifier());
        identifier.setPrimaryKey(true);
        addField(identifier);
    }

}
