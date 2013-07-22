package org.siemac.metamac.statistical.resources.web.client.publication.model.ds;

import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;

public class PublicationStructureDS extends DataSource {

    public static final String ID             = "str-id";
    public static final String TYPE           = "str-type";
    public static final String TYPE_VIEW      = "str-type-view"; // Not mapped in DTO
    public static final String TYPE_VIEW_NAME = "str-type-name"; // Not mapped in DTO
    public static final String TEXT           = "str-text";
    public static final String TEXT_HTML      = "str-text-html"; // Not mapped in DTO
    public static final String URL            = "str-url";
    public static final String URN            = "str-urn";

    public static final String DTO            = "str-dto";

    public PublicationStructureDS() {
        DataSourceIntegerField identifier = new DataSourceIntegerField(ID, StatisticalResourcesWeb.getConstants().publicationStructureElementIdentifier());
        identifier.setPrimaryKey(true);
        addField(identifier);
    }
}
