package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;

public class StatisticalResourcesDtoMocks {

    private static final String URI_MOCK = "lorem/ipsum/dolor/sit/amet";
    private static final String URN_MOCK = "urn:lorem.ipsum.dolor.infomodel.package.Resource=" + MetamacMocks.mockString(10);

    // -----------------------------------------------------------------
    // QUERY
    // -----------------------------------------------------------------

    public static QueryDto mockQueryDto() {
        QueryDto queryDto = new QueryDto();

        mockNameableStatisticalResorceDto(queryDto);

        return queryDto;
    }

    // -----------------------------------------------------------------
    // BASE HIERARCHY
    // -----------------------------------------------------------------

    private static void mockNameableStatisticalResorceDto(NameableStatisticalResourceDto nameableResourceDto) {
        nameableResourceDto.setTitle(MetamacMocks.mockInternationalString());
        nameableResourceDto.setDescription(MetamacMocks.mockInternationalString());

        mockIdentifiableStatisticalResourceDto(nameableResourceDto);
    }

    private static void mockIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto resource) {
        resource.setCode("resource-" + MetamacMocks.mockString(10));
        resource.setUri(URI_MOCK);
        resource.setUrn(URN_MOCK);

        mockStatisticalResourceDto(resource);
    }

    private static void mockStatisticalResourceDto(StatisticalResourceDto resource) {
        resource.setOperation(MetamacMocks.mockExternalItemDto(URN_MOCK, TypeExternalArtefactsEnum.STATISTICAL_OPERATION));
    }

}
