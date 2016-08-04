package org.siemac.metamac.statistical.resources.web.client.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.io.Serializable;

import org.siemac.metamac.statistical.resources.web.shared.dtos.RangeDto;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCanvasItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class RangeItem extends CustomCanvasItem {

    private VLayout       formLayout;

    private ValuesManager valuesManager;

    public RangeItem(String name, String title, boolean editionMode) {
        super(name, title);
        setCellHeight(10);
        HLayout mainLayout = new HLayout();
        valuesManager = new ValuesManager();

        DynamicForm rangeTextForm = new DynamicForm();
        rangeTextForm.setValuesManager(valuesManager);
        rangeTextForm.setNumCols(4);
        rangeTextForm.setColWidths("15%", "35%", "15%", "35%");
        FormItem fromItem;
        FormItem toItem;
        if (editionMode) {
            fromItem = new CustomTextItem(RangeDS.FROM, getConstants().datasetConstraintRangeFrom());
            toItem = new CustomTextItem(RangeDS.TO, getConstants().datasetConstraintRangeTo());
        } else {
            setTitleStyle("staticFormItemTitle");
            fromItem = new ViewTextItem(RangeDS.FROM, getConstants().datasetConstraintRangeFrom());
            toItem = new ViewTextItem(RangeDS.TO, getConstants().datasetConstraintRangeTo());
        }
        rangeTextForm.setFields(fromItem, toItem);

        formLayout = new VLayout();
        formLayout.addMember(rangeTextForm);
        mainLayout.addMember(formLayout);
        setCanvas(mainLayout);
    }

    public void setValue(RangeDto rangeDto) {
        valuesManager.clearValues();
        if (rangeDto != null) {
            valuesManager.setValue(RangeDS.FROM, rangeDto.getFromValue());
            valuesManager.setValue(RangeDS.TO, rangeDto.getToValue());
        }
    }

    @Override
    public RangeDto getValue() {
        RangeDto rangeDto = new RangeDto();
        rangeDto.setFromValue(valuesManager.getValueAsString(RangeDS.FROM));
        rangeDto.setToValue(valuesManager.getValueAsString(RangeDS.TO));
        return rangeDto;
    }

    private class RangeDS implements Serializable {

        private static final long   serialVersionUID = 1L;
        private static final String FROM             = "range-from";
        private static final String TO               = "range-to";
    }
}
