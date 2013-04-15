package org.siemac.metamac.statistical.resources.web.client.query.view.handlers;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;

import com.gwtplatform.mvp.client.UiHandlers;


public interface QueryUiHandlers extends UiHandlers {

    void saveQuery(QueryDto query);
    

}
