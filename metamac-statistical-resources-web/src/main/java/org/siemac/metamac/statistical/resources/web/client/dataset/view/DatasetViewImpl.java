package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetMainFormLayout;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetViewImpl extends ViewImpl implements DatasetPresenter.DatasetView {

    private VLayout               panel;

    private DatasetMainFormLayout mainFormLayout;

    private DataSetDto            datasetDto;

    private GroupDynamicForm      identifiersForm;
    private GroupDynamicForm      auditForm;
    private GroupDynamicForm      contentDescriptorsForm;
    private GroupDynamicForm      versioningForm;
    private GroupDynamicForm      lifecycleForm;

    public DatasetViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        mainFormLayout = new DatasetMainFormLayout(DatasetClientSecurityUtils.canUpdateDataset());

        bindMainFormLayoutEvents();
        createViewForm();

        panel.addMember(mainFormLayout);
    }

    private void bindMainFormLayoutEvents() {
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();
                identifiersForm.setTranslationsShowed(translationsShowed);
                versioningForm.setTranslationsShowed(translationsShowed);
                /*
                 * auditForm.setTranslationsShowed(translationsShowed);
                 * contentDescriptorsForm.setTranslationsShowed(translationsShowed);
                 * lifecycleForm.setTranslationsShowed(translationsShowed);
                 */
            }
        });
    }

    private void createViewForm() {
        // Identifiers Form
        identifiersForm = new GroupDynamicForm(getConstants().datasetIdentifiers());
        ViewTextItem identifier = new ViewTextItem(DatasetDS.IDENTIFIER, getConstants().datasetIdentifier());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(DatasetDS.TITLE, getConstants().datasetTitle());
        ViewTextItem uri = new ViewTextItem(DatasetDS.URI, getConstants().datasetUri());
        ViewTextItem urn = new ViewTextItem(DatasetDS.URN, getConstants().datasetUrn());
        identifiersForm.setFields(identifier, title, uri, urn);

        // version
        versioningForm = new GroupDynamicForm(getConstants().datasetVersioning());
        ViewTextItem version = new ViewTextItem(DatasetDS.VERSION_LOGIC, getConstants().datasetVersion());
        ViewTextItem dateVersion = new ViewTextItem(DatasetDS.DATE_VERSION, getConstants().datasetDateVersion());
        ViewTextItem nextVersion = new ViewTextItem(DatasetDS.NEXT_VERSION, getConstants().datasetNextVersion());
        ViewTextItem rationale = new ViewTextItem(DatasetDS.RATIONALE, getConstants().datasetRationale());
        ViewTextItem rationaleType = new ViewTextItem(DatasetDS.RATIONALE_TYPE, getConstants().datasetRationaleType());
        versioningForm.setFields(version, dateVersion, rationaleType, rationale, nextVersion);

        /*
         * // audit
         * auditForm = new GroupDynamicForm(getConstants().datasetAuditMetadata());
         * ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(ConceptSchemeDS.DESCRIPTION, getConstants().conceptSchemeDescription());
         * ViewTextItem partial = new ViewTextItem(ConceptSchemeDS.IS_PARTIAL, getConstants().conceptSchemeIsPartial());
         * ViewTextItem isExternalReference = new ViewTextItem(ConceptSchemeDS.IS_EXTERNAL_REFERENCE, getConstants().conceptSchemeIsExternalReference());
         * auditForm.setFields(description, partial, isExternalReference);
         * // Content descriptors
         * contentDescriptorsForm = new GroupDynamicForm(getConstants().datasetContentDescriptors());
         * ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(ConceptSchemeDS.DESCRIPTION, getConstants().conceptSchemeDescription());
         * ViewTextItem partial = new ViewTextItem(ConceptSchemeDS.IS_PARTIAL, getConstants().conceptSchemeIsPartial());
         * ViewTextItem isExternalReference = new ViewTextItem(ConceptSchemeDS.IS_EXTERNAL_REFERENCE, getConstants().conceptSchemeIsExternalReference());
         * contentDescriptorsForm.setFields(description, partial, isExternalReference);
         * // lifecycle
         * lifecycleForm = new GroupDynamicForm(getConstants().datasetLifecycle());
         * ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(ConceptSchemeDS.DESCRIPTION, getConstants().conceptSchemeDescription());
         * ViewTextItem partial = new ViewTextItem(ConceptSchemeDS.IS_PARTIAL, getConstants().conceptSchemeIsPartial());
         * ViewTextItem isExternalReference = new ViewTextItem(ConceptSchemeDS.IS_EXTERNAL_REFERENCE, getConstants().conceptSchemeIsExternalReference());
         * lifecycleForm.setFields(description, partial, isExternalReference);
         */

        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(versioningForm);
        /*
         * mainFormLayout.addViewCanvas(auditForm);
         * mainFormLayout.addViewCanvas(contentDescriptorsForm);
         * mainFormLayout.addViewCanvas(lifecycleForm);
         */
    }

    public void setDatasetViewMode(DataSetDto datasetDto) {
        // Identifiers
        identifiersForm.setValue(DatasetDS.IDENTIFIER, datasetDto.getIdentifier());
        identifiersForm.setValue(DatasetDS.URI, datasetDto.getUri());
        identifiersForm.setValue(DatasetDS.URN, datasetDto.getUrn());
        identifiersForm.setValue(DatasetDS.TITLE, RecordUtils.getInternationalStringRecord(datasetDto.getTitle()));

        versioningForm.setValue(DatasetDS.VERSION_LOGIC, datasetDto.getVersion());
        versioningForm.setValue(DatasetDS.DATE_VERSION, datasetDto.getDateVersion() != null ? DateUtils.getFormattedDate(datasetDto.getDateVersion()) : StringUtils.EMPTY);
        // TODO change based on values taken from gpe
        versioningForm.setValue(DatasetDS.RATIONALE_TYPE, datasetDto.getRationaleType() != null ? datasetDto.getRationaleType().name() : StringUtils.EMPTY);
        versioningForm.setValue(DatasetDS.RATIONALE, datasetDto.getRationale() != null ? datasetDto.getRationale() : StringUtils.EMPTY);
        versioningForm.setValue(DatasetDS.DATE_VERSION, datasetDto.getDateVersion() != null ? DateUtils.getFormattedDate(datasetDto.getDateVersion()) : StringUtils.EMPTY);
        versioningForm.setValue(DatasetDS.NEXT_VERSION, datasetDto.getNextVersion() != null ? DateUtils.getFormattedDate(datasetDto.getNextVersion()) : StringUtils.EMPTY);
    }

    @Override
    public void setDataset(DataSetDto datasetDto) {
        this.datasetDto = datasetDto;

        String defaultLocalized = InternationalStringUtils.getLocalisedString(datasetDto.getTitle());
        String title = defaultLocalized != null ? defaultLocalized : StringUtils.EMPTY;
        mainFormLayout.setTitleLabelContents(title);

        mainFormLayout.setViewMode();

        setDatasetViewMode(datasetDto);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

}
