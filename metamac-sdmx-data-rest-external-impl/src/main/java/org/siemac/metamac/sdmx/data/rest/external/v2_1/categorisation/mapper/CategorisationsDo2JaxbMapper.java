package org.siemac.metamac.sdmx.data.rest.external.v2_1.categorisation.mapper;

import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationsType;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;

public interface CategorisationsDo2JaxbMapper extends BaseDo2JaxbMapper {

    public CategorisationsType categorisationDoToJaxb(List<Categorisation> sourceList, boolean asStub);

    public CategorisationType categorisationDoToJaxb(Categorisation source, boolean asStub);

}
