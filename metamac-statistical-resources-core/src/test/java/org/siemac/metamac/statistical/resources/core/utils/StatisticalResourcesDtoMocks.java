package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.StatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;

public class StatisticalResourcesDtoMocks extends MetamacMocks {

    private static final String URI_MOCK = "lorem/ipsum/dolor/sit/amet/" + mockString(5);
    private static final String URN_MOCK = "urn:lorem.ipsum.dolor.infomodel.package.Resource=" + mockString(10);

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
        nameableResourceDto.setTitle(mockInternationalStringDto());
        nameableResourceDto.setDescription(mockInternationalStringDto());

        mockIdentifiableStatisticalResourceDto(nameableResourceDto);
    }

    private static void mockIdentifiableStatisticalResourceDto(IdentifiableStatisticalResourceDto resource) {
        resource.setCode("resource-" + mockString(10));
        resource.setUri(URI_MOCK);
        resource.setUrn(URN_MOCK);

        mockStatisticalResourceDto(resource);
    }

    private static void mockStatisticalResourceDto(StatisticalResourceDto resource) {
        resource.setOperation(mockExternalItemDto(URN_MOCK, TypeExternalArtefactsEnum.STATISTICAL_OPERATION));
    }

}
