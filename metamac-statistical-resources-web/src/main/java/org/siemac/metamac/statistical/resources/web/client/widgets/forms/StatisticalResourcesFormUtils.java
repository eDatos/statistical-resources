package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RelatedResourceBaseLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RelatedResourceBaseListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.utils.FormUtils;

import com.smartgwt.client.widgets.form.fields.FormItem;

public class StatisticalResourcesFormUtils extends FormUtils {

    public static void setExternalItemsValue(FormItem item, List<ExternalItemDto> externalItems) {
        ((ExternalItemListItem) item).setExternalItems(externalItems);
    }

    @SuppressWarnings("unchecked")
    public static void setRelatedResourceValue(FormItem item, RelatedResourceDto value) {
        ((RelatedResourceBaseLinkItem<RelatedResourceDto>) item).setRelatedResource(value);
    }

    @SuppressWarnings("unchecked")
    public static void setRelatedResourcesValue(FormItem item, List<RelatedResourceDto> values) {
        ((RelatedResourceBaseListItem<RelatedResourceDto>) item).setRelatedResources(values);
    }

    public static List<ExternalItemDto> getExternalItemsValue(FormItem item) {
        return ((ExternalItemListItem) item).getExternalItemDtos();
    }

    @SuppressWarnings("unchecked")
    public static List<RelatedResourceDto> getRelatedResourcesValue(FormItem item) {
        return ((RelatedResourceBaseListItem<RelatedResourceDto>) item).getRelatedResourceDtos();
    }

    @SuppressWarnings("unchecked")
    public static RelatedResourceDto getRelatedResourceValue(FormItem item) {
        return ((RelatedResourceBaseLinkItem<RelatedResourceDto>) item).getRelatedResourceDto();
    }

    public static Date getDate(FormItem item) {
        return ((CustomDateItem) item).getValueAsDate();
    }

}
