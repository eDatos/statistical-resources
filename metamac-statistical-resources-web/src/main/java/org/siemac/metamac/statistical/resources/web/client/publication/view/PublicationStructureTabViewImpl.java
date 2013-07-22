package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructurePanel;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationStructureTabViewImpl extends ViewImpl implements PublicationStructureTabView {

    private VLayout                   panel;
    private PublicationStructurePanel structurePanel;

    @Inject
    public PublicationStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);

        structurePanel = new PublicationStructurePanel();

        VLayout publicationStructureLayout = new VLayout();
        publicationStructureLayout.setMargin(15);
        publicationStructureLayout.addMember(new TitleLabel(getConstants().publicationStructure()));
        publicationStructureLayout.addMember(structurePanel);

        panel.addMember(structurePanel);
    }

    @Override
    public void setPublicationVersion(PublicationVersionDto publicationVersionDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPublicationStructure(PublicationStructureDto publicationStructureDto) {
        // TODO Auto-generated method stub
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
