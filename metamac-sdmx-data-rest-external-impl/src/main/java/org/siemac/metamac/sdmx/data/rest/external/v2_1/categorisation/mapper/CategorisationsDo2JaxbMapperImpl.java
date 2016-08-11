package org.siemac.metamac.sdmx.data.rest.external.v2_1.categorisation.mapper;

import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CategorisationsType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.CoreCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.sdmx.data.rest.external.conf.DataConfiguration;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.RestExternalConstants;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.base.mapper.BaseDo2JaxbMapperImpl;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.common.mapper.CommonDo2JaxbMapper;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.service.utils.UriCalculator;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("categorisationsDo2JaxbMapper")
public class CategorisationsDo2JaxbMapperImpl extends BaseDo2JaxbMapperImpl implements CategorisationsDo2JaxbMapper {

    private static Logger       logger              = LoggerFactory.getLogger(CategorisationsDo2JaxbMapperImpl.class);

    @Autowired
    private CommonDo2JaxbMapper commonDo2JaxbMapper;

    @Autowired
    private DataConfiguration   dataConfiguration;

    private static final String PATH_CATEGORISATION = "categorisation";

    @Override
    public CategorisationsType categorisationDoToJaxb(List<Categorisation> sourceList, boolean asStub) {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }

        CategorisationsType categorisationsType = getStructureObjectFactory().createCategorisationsType();

        for (Categorisation sourceCategorisation : sourceList) {
            CategorisationType categorisationType = categorisationDoToJaxb(sourceCategorisation, asStub);
            if (categorisationType != null) {
                categorisationsType.getCategorisations().add(categorisationType);
            }
        }

        return categorisationsType;
    }

    @Override
    public CategorisationType categorisationDoToJaxb(Categorisation source, boolean asStub) {
        if (source == null) {
            return null;
        }

        CategorisationType categorisationType = getStructureObjectFactory().createCategorisationType();
        categorisationDoToJaxb(source, categorisationType, asStub);

        return categorisationType;
    }

    private void categorisationDoToJaxb(Categorisation source, CategorisationType categorisationType, boolean asStub) {
        if (source == null) {
            return;
        }

        // Name
        categorisationType.getNames().addAll(commonDo2JaxbMapper.internationalStringDoToJaxb(source.getVersionableStatisticalResource().getTitle()));

        // Id (Required)
        categorisationType.setId(source.getVersionableStatisticalResource().getCode());

        // AgencyID (Required)
        categorisationType.setAgencyID(source.getMaintainer().getCodeNested());

        // Version
        categorisationType.setVersion(source.getVersionableStatisticalResource().getVersionLogic());

        ObjectReferenceType sourceReference = commonDo2JaxbMapper.datasetVersionReferenceToJaxb(source.getDatasetVersion());
        CategoryReferenceType targetReference = commonDo2JaxbMapper.categoryReferenceTypeDoToJaxb(source.getCategory());

        if (!asStub) {
            // Annotations: Not supported

            // Description: Not supported

            // IsFinal
            categorisationType.setIsFinal(Boolean.TRUE);

            // IsExternalReference

            // ValidFrom
            categorisationType.setValidFrom(CoreCommonUtil.transformDateTimeToDate(source.getValidFromEffective()));

            // ValidTo
            categorisationType.setValidTo(CoreCommonUtil.transformDateTimeToDate(source.getValidToEffective()));

            // Urn
            categorisationType.setUrn(source.getVersionableStatisticalResource().getUrn());

            // Uri
            try {
                categorisationType.setUri(UriCalculator.calculateUriForCategorisation(source,
                        RestUtils.createLink(dataConfiguration.retrieveSdmxRegistryApiUrlBase(), RestExternalConstants.API_VERSION_2_1), PATH_CATEGORISATION));
            } catch (MetamacException e) {
                logger.error("Impossible to calculate URI", e);
            }

            // ServiceURL

            // StructureURL

            // Source
            categorisationType.setSource(sourceReference);

            // Target
            categorisationType.setTarget(targetReference);
        }
    }
}
