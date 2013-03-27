package org.siemac.metamac.statistical.resources.web.client.publication.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.LinkedHashMap;

import org.siemac.metamac.statistical.resources.core.enume.domain.PublicationStructureHierarchyTypeEnum;

public class CommonUtils {

    private static LinkedHashMap<String, String> structureHierarchyTypeHashMap                  = null;
    private static LinkedHashMap<String, String> structureHierarchyTitleValidTypesHashMap       = null;
    private static LinkedHashMap<String, String> structureHierarchyChapterValidTypesHashMap     = null;
    private static LinkedHashMap<String, String> structureHierarchySubChapter1ValidTypesHashMap = null;
    private static LinkedHashMap<String, String> structureHierarchySubChapter2ValidTypesHashMap = null;

    public static LinkedHashMap<String, String> getStructureHierarchyTypeHashMap() {
        if (structureHierarchyTypeHashMap == null) {
            structureHierarchyTypeHashMap = new LinkedHashMap<String, String>();
            structureHierarchyTypeHashMap.put(new String(), new String());
            for (PublicationStructureHierarchyTypeEnum c : PublicationStructureHierarchyTypeEnum.values()) {
                String value = getCoreMessages().getString(getCoreMessages().publicationStructureHierarchyTypeEnum() + c.getName());
                structureHierarchyTypeHashMap.put(c.toString(), value);
            }
        }
        return structureHierarchyTypeHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchyTitleValidTypesHashMap() {
        if (structureHierarchyTitleValidTypesHashMap == null) {
            structureHierarchyTitleValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchyTitleValidTypesHashMap.put(new String(), new String());
            structureHierarchyTitleValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.CHAPTER.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumCHAPTER());
            structureHierarchyTitleValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumTEXT());
            structureHierarchyTitleValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumURL());
            structureHierarchyTitleValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumDATASET());
            structureHierarchyTitleValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchyTitleValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchyChapterValidTypesHashMap() {
        if (structureHierarchyChapterValidTypesHashMap == null) {
            structureHierarchyChapterValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchyChapterValidTypesHashMap.put(new String(), new String());
            structureHierarchyChapterValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.SUBCHAPTER1.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumSUBCHAPTER1());
            structureHierarchyChapterValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumTEXT());
            structureHierarchyChapterValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumURL());
            structureHierarchyChapterValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumDATASET());
            structureHierarchyChapterValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchyChapterValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchySubChapter1ValidTypesHashMap() {
        if (structureHierarchySubChapter1ValidTypesHashMap == null) {
            structureHierarchySubChapter1ValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchySubChapter1ValidTypesHashMap.put(new String(), new String());
            structureHierarchySubChapter1ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.SUBCHAPTER2.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumSUBCHAPTER2());
            structureHierarchySubChapter1ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumTEXT());
            structureHierarchySubChapter1ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumURL());
            structureHierarchySubChapter1ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumDATASET());
            structureHierarchySubChapter1ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchySubChapter1ValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchySubChapter2ValidTypesHashMap() {
        if (structureHierarchySubChapter2ValidTypesHashMap == null) {
            structureHierarchySubChapter2ValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchySubChapter2ValidTypesHashMap.put(new String(), new String());
            structureHierarchySubChapter2ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumTEXT());
            structureHierarchySubChapter2ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumURL());
            structureHierarchySubChapter2ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumDATASET());
            structureHierarchySubChapter2ValidTypesHashMap.put(PublicationStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().publicationStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchySubChapter2ValidTypesHashMap;
    }

    public static String getStructureHierarchyTypeName(PublicationStructureHierarchyTypeEnum structureHierarchyTypeEnum) {
        return structureHierarchyTypeEnum != null ? getCoreMessages().getString(getCoreMessages().publicationStructureHierarchyTypeEnum() + structureHierarchyTypeEnum.name()) : null;
    }
}
