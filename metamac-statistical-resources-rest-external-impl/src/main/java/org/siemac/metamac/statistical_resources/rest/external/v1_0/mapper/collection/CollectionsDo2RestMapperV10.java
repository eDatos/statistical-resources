package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collection;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public interface CollectionsDo2RestMapperV10 {

    public Collection toCollection(PublicationVersion source, List<String> selectedLanguages) throws Exception;
    public Resource toResource(PublicationVersion source, List<String> selectedLanguages);
}
