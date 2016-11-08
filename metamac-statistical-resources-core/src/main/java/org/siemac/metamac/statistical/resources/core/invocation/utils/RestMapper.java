package org.siemac.metamac.statistical.resources.core.invocation.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.mapper.CommonDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {

    private static final String   API_TYPE_DATASETS         = "datasets";
    private static final String   API_TYPE_COLLECTIONS      = "collections";
    private static final String   API_APIS                  = "apis";
    public static final String API_STATISTICAL_RESOURCES = "statistical-resources-internal";
    protected static final String API_SEPARATOR             = "/";
    protected static final String API_VERSION_NAME          = "v1.0";

    @Autowired
    @Qualifier("commonDto2DoMapper")
    CommonDto2DoMapper dto2DoMapper;

    public List<ExternalItem> buildExternalItemsFromResourcesInternal(List<ResourceInternal> resources) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        for (ResourceInternal resource : resources) {
            externalItems.add(buildExternalItemFromResourceInternal(resource));
        }
        return externalItems;
    }

    public ExternalItem buildExternalItemFromResourceInternal(ResourceInternal resource) throws MetamacException {
        ExternalItem externalItem = new ExternalItem();
        TypeExternalArtefactsEnum type = TypeExternalArtefactsEnum.fromValue(resource.getKind());

        externalItem.setType(type);
        externalItem.setCode(resource.getId());
        externalItem.setCodeNested(resource.getNestedId());
        externalItem.setUri(dto2DoMapper.externalItemApiUrlDtoToDo(type, resource.getSelfLink().getHref()));
        externalItem.setUrn(resource.getUrn());
        externalItem.setUrnProvider(resource.getUrnProvider());
        externalItem.setManagementAppUrl(dto2DoMapper.externalItemWebAppUrlDtoToDo(type, resource.getManagementAppLink()));
        externalItem.setTitle(getInternationalStringFromInternationalStringResource(resource.getName()));
        return externalItem;
    }

    public ExternalItem buildExternalItemFromSrmItemResourceInternal(ItemResourceInternal itemResourceInternal) throws MetamacException {
        return buildExternalItemFromResourceInternal(itemResourceInternal);
    }

    public ExternalItem buildExternalItemFromCode(CodeResourceInternal code) throws MetamacException {
        return buildExternalItemFromSrmItemResourceInternal(code);
    }

    public InternationalString getInternationalStringFromInternationalStringResource(org.siemac.metamac.rest.common.v1_0.domain.InternationalString intString) {
        InternationalString result = new InternationalString();
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString text : intString.getTexts()) {
            LocalisedString localised = new LocalisedString(text.getLang(), text.getValue());
            result.addText(localised);
        }

        return result;
    }

    public List<org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal> generateResourceInternalList(List<HasSiemacMetadata> affectedResources, String statisticalResourcesInternalWebUrlBase,
            String statisticalResourcesApiInternalEndpointV10) {
        List<org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal> list = new ArrayList<org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal>();
        for (HasSiemacMetadata item : affectedResources) {
            org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal resourceInternal = new org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal();
            resourceInternal.setUrn(item.getSiemacMetadataStatisticalResource().getUrn());
            resourceInternal.setKind(getKindByType(item));
            resourceInternal.setName(toRestInternationalString(item.getSiemacMetadataStatisticalResource().getTitle()));
            resourceInternal.setId(item.getLifeCycleStatisticalResource().getCode());
            resourceInternal.setSelfLink(createSelfLink(item, statisticalResourcesApiInternalEndpointV10));
            resourceInternal.setManagementAppLink(createManagementAppLink(item, statisticalResourcesInternalWebUrlBase));
            list.add(resourceInternal);
        }
        return list;
    }

    private String createManagementAppLink(HasSiemacMetadata item, String statisticalResourcesInternalWebUrlBase) {
        InternalWebApplicationNavigation internalWebApplicationNavigation = new InternalWebApplicationNavigation(statisticalResourcesInternalWebUrlBase);
        return buildManagementAppLinkByType(item, internalWebApplicationNavigation);
    }

    private String buildManagementAppLinkByType(HasSiemacMetadata item, InternalWebApplicationNavigation internalWebApplicationNavigation) {
        switch (item.getSiemacMetadataStatisticalResource().getType()) {
            case DATASET:
                return internalWebApplicationNavigation.buildDatasetVersionUrl((DatasetVersion) item);
            case COLLECTION:
                return internalWebApplicationNavigation.buildPublicationVersionUrl((PublicationVersion) item);
            default:
                return "";
        }
    }

    private ResourceLink createSelfLink(HasSiemacMetadata item, String statisticalResourcesApiInternalEndpointV10) {
        StringBuffer selfLinkSB = new StringBuffer()
                .append(getApiKindByType(item))
                .append(API_SEPARATOR).append(item.getLifeCycleStatisticalResource().getMaintainer().getCode()).append(API_SEPARATOR)
                .append(item.getSiemacMetadataStatisticalResource().getCode())
                .append(API_SEPARATOR);
        appendVersionIfNecessary(item, selfLinkSB);
        String selfLink = selfLinkSB.toString();
        return toResourceLink(getKindByType(item), toSiemacMetadataLink(selfLink, statisticalResourcesApiInternalEndpointV10));
    }

    private void appendVersionIfNecessary(HasSiemacMetadata item, StringBuffer selfLinkSB) {
        if (item.getSiemacMetadataStatisticalResource().getType() == StatisticalResourceTypeEnum.DATASET) {
            selfLinkSB.append(item.getSiemacMetadataStatisticalResource().getVersionLogic());
        }
    }

    private ResourceLink toResourceLink(String kindByType, String siemacMetadataLink) {
        ResourceLink link = new ResourceLink();
        link.setKind(kindByType);
        link.setHref(siemacMetadataLink);
        return link;
    }

    private String toSiemacMetadataLink(String code, String statisticalResourcesApiInternalEndpointV10) {
        return RestUtils.createLink(getApiBaseLink(statisticalResourcesApiInternalEndpointV10), code);
    }

    private String getApiBaseLink(String statisticalResourcesApiInternalEndpointV10) {
        return RestUtils.createLink(statisticalResourcesApiInternalEndpointV10, API_SEPARATOR + API_STATISTICAL_RESOURCES + API_SEPARATOR + API_VERSION_NAME);
    }

    private org.siemac.metamac.rest.common.v1_0.domain.InternationalString toRestInternationalString(org.siemac.metamac.statistical.resources.core.common.domain.InternationalString source) {
        org.siemac.metamac.rest.common.v1_0.domain.InternationalString target = new org.siemac.metamac.rest.common.v1_0.domain.InternationalString();
        if (source != null) {
            for (LocalisedString item : source.getTexts()) {
                org.siemac.metamac.rest.common.v1_0.domain.LocalisedString targetString = new org.siemac.metamac.rest.common.v1_0.domain.LocalisedString();
                targetString.setValue(item.getLabel());
                targetString.setLang(item.getLocale());
                target.getTexts().add(targetString);
            }
        }
        return target;
    }

    private String getKindByType(HasSiemacMetadata item) {
        switch (item.getSiemacMetadataStatisticalResource().getType()) {
            case DATASET:
                return TypeExternalArtefactsEnum.DATASET.getValue();
            case COLLECTION:
                return TypeExternalArtefactsEnum.COLLECTION.getValue();
            default:
                return null;
        }
    }

    private String getApiKindByType(HasSiemacMetadata item) {
        switch (item.getSiemacMetadataStatisticalResource().getType()) {
            case DATASET:
                return API_TYPE_DATASETS;
            case COLLECTION:
                return API_TYPE_COLLECTIONS;
            default:
                return null;
        }
    }

}
