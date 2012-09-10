package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.CollectionClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.CollectionUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.CollectionMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class CollectionViewImpl extends ViewImpl implements CollectionPresenter.CollectionView {

    private CollectionUiHandlers     uiHandlers;

    private VLayout                  panel;
    private CollectionMainFormLayout mainFormLayout;

    private GroupDynamicForm         identifiersForm;
    private GroupDynamicForm         versionForm;
    private GroupDynamicForm         lifeCycleForm;
    private GroupDynamicForm         contentMetadataForm;

    private GroupDynamicForm         identifiersEditionForm;
    private GroupDynamicForm         versionEditionForm;
    private GroupDynamicForm         lifeCycleEditionForm;
    private GroupDynamicForm         contentMetadataEditionForm;

    private CollectionDto            collectionDto;

    @Inject
    public CollectionViewImpl() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        mainFormLayout = new CollectionMainFormLayout(CollectionClientSecurityUtils.canUpdateCollection());
        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        panel.addMember(mainFormLayout);
    }

    @Override
    public void setUiHandlers(CollectionUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == CollectionPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.COLLECTIONS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
                            ((ToolStripButton) canvas[i]).select();
                        }
                    }
                }
                panel.addMember(content, 0);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;

        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(collectionDto.getTitle()));
        mainFormLayout.setViewMode();

        setCollectionViewMode(collectionDto);
        setCollectionEditionMode(collectionDto);
    }

    private void bindMainFormLayoutEvents() {
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();
                identifiersForm.setTranslationsShowed(translationsShowed);
                identifiersEditionForm.setTranslationsShowed(translationsShowed);

                versionForm.setTranslationsShowed(translationsShowed);
                versionEditionForm.setTranslationsShowed(translationsShowed);

                lifeCycleForm.setTranslationsShowed(translationsShowed);
                lifeCycleEditionForm.setTranslationsShowed(translationsShowed);

                contentMetadataForm.setTranslationsShowed(translationsShowed);
                contentMetadataEditionForm.setTranslationsShowed(translationsShowed);
            }
        });

        // Save
        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && versionEditionForm.validate(false) && lifeCycleEditionForm.validate(false) && contentMetadataEditionForm.validate(false)) {
                    uiHandlers.saveCollection(getCollectionDto());
                }
            }
        });
    }

    private void createViewForm() {
        // Identifiers form
        identifiersForm = new GroupDynamicForm(getConstants().collectionIdentifiers());

        // Version form
        versionForm = new GroupDynamicForm(getConstants().collectionVersion());

        // Life cycle form
        lifeCycleForm = new GroupDynamicForm(getConstants().collectionLifeCycle());

        // Content metadata form
        contentMetadataForm = new GroupDynamicForm(getConstants().collectionContentMetadata());

        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(versionForm);
        mainFormLayout.addViewCanvas(lifeCycleForm);
        mainFormLayout.addViewCanvas(contentMetadataForm);
    }

    private void createEditionForm() {
        // Identifiers form
        identifiersEditionForm = new GroupDynamicForm(getConstants().collectionIdentifiers());

        // Version form
        versionEditionForm = new GroupDynamicForm(getConstants().collectionVersion());

        // Life cycle form
        lifeCycleEditionForm = new GroupDynamicForm(getConstants().collectionLifeCycle());

        // Content metadata form
        contentMetadataEditionForm = new GroupDynamicForm(getConstants().collectionContentMetadata());

        mainFormLayout.addEditionCanvas(identifiersEditionForm);
        mainFormLayout.addEditionCanvas(versionEditionForm);
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
        mainFormLayout.addEditionCanvas(contentMetadataEditionForm);
    }

    private void setCollectionViewMode(CollectionDto collectionDto) {

    }

    private void setCollectionEditionMode(CollectionDto collectionDto) {

    }

    private CollectionDto getCollectionDto() {
        return collectionDto;
    }

}
