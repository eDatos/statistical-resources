package org.siemac.metamac.statistical.resources.web.client.collection.model.ds;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class CollectionStructureDS extends DataSource {

    public static final String ID   = "str-id";
    public static final String TYPE = "str-type";
    public static final String TEXT = "str-name";
    public static final String URL  = "str-url";
    public static final String URN  = "str-urn";

    public static final String DTO  = "str-dto";

    public CollectionStructureDS() {
        DataSourceIntegerField identifier = new DataSourceIntegerField(ID, StatisticalResourcesWeb.getConstants().collectionStructureElementIdentifier());
        identifier.setPrimaryKey(true);
        addField(identifier);
    }

}
