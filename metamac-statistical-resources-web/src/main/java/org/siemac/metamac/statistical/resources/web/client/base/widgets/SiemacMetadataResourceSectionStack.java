package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.model.ds.LifeCycleResourceDS;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class SiemacMetadataResourceSectionStack extends VersionableResourceSectionStack {

    public SiemacMetadataResourceSectionStack(String title) {
        super(title);
    }

    @Override
    protected void setListGridFields(ListGridField... extraFields) {
        List<ListGridField> fieldList = new ArrayList<ListGridField>();
        addPublicationStreamStatusToGrid(fieldList);
        addExtraFieldsToGrid(fieldList, extraFields);
        super.setListGridFields(fieldList.toArray(new ListGridField[fieldList.size()]));
    }

    protected void addPublicationStreamStatusToGrid(List<ListGridField> fieldList) {
        ListGridField publicationStreamStatus = new ListGridField(LifeCycleResourceDS.PUBLICATION_STREAM_STATUS, getConstants().publicationStreamStatus());
        publicationStreamStatus.setType(ListGridFieldType.IMAGE);
        publicationStreamStatus.setAlign(Alignment.CENTER);
        fieldList.add(publicationStreamStatus);
    }

}
