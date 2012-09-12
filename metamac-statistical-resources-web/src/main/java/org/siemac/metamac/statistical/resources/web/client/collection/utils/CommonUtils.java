package org.siemac.metamac.statistical.resources.web.client.collection.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.LinkedHashMap;

import org.siemac.metamac.statistical.resources.core.enume.domain.CollectionStructureHierarchyTypeEnum;

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
            for (CollectionStructureHierarchyTypeEnum c : CollectionStructureHierarchyTypeEnum.values()) {
                String value = getCoreMessages().getString(getCoreMessages().collectionStructureHierarchyTypeEnum() + c.getName());
                structureHierarchyTypeHashMap.put(c.toString(), value);
            }
        }
        return structureHierarchyTypeHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchyTitleValidTypesHashMap() {
        if (structureHierarchyTitleValidTypesHashMap == null) {
            structureHierarchyTitleValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchyTitleValidTypesHashMap.put(new String(), new String());
            structureHierarchyTitleValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.CHAPTER.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumCHAPTER());
            structureHierarchyTitleValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumTEXT());
            structureHierarchyTitleValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumURL());
            structureHierarchyTitleValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumDATASET());
            structureHierarchyTitleValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchyTitleValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchyChapterValidTypesHashMap() {
        if (structureHierarchyChapterValidTypesHashMap == null) {
            structureHierarchyChapterValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchyChapterValidTypesHashMap.put(new String(), new String());
            structureHierarchyChapterValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.SUBCHAPTER1.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumSUBCHAPTER1());
            structureHierarchyChapterValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumTEXT());
            structureHierarchyChapterValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumURL());
            structureHierarchyChapterValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumDATASET());
            structureHierarchyChapterValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchyChapterValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchySubChapter1ValidTypesHashMap() {
        if (structureHierarchySubChapter1ValidTypesHashMap == null) {
            structureHierarchySubChapter1ValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchySubChapter1ValidTypesHashMap.put(new String(), new String());
            structureHierarchySubChapter1ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.SUBCHAPTER2.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumSUBCHAPTER2());
            structureHierarchySubChapter1ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumTEXT());
            structureHierarchySubChapter1ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumURL());
            structureHierarchySubChapter1ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumDATASET());
            structureHierarchySubChapter1ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchySubChapter1ValidTypesHashMap;
    }

    public static LinkedHashMap<String, String> getStructureHierarchySubChapter2ValidTypesHashMap() {
        if (structureHierarchySubChapter2ValidTypesHashMap == null) {
            structureHierarchySubChapter2ValidTypesHashMap = new LinkedHashMap<String, String>();
            structureHierarchySubChapter2ValidTypesHashMap.put(new String(), new String());
            structureHierarchySubChapter2ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.TEXT.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumTEXT());
            structureHierarchySubChapter2ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.URL.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumURL());
            structureHierarchySubChapter2ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.DATASET.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumDATASET());
            structureHierarchySubChapter2ValidTypesHashMap.put(CollectionStructureHierarchyTypeEnum.QUERY.toString(), getCoreMessages().collectionStructureHierarchyTypeEnumQUERY());
        }
        return structureHierarchySubChapter2ValidTypesHashMap;
    }

    public static String getStructureHierarchyTypeName(CollectionStructureHierarchyTypeEnum structureHierarchyTypeEnum) {
        return structureHierarchyTypeEnum != null ? getCoreMessages().getString(getCoreMessages().collectionStructureHierarchyTypeEnum() + structureHierarchyTypeEnum.name()) : null;
    }

}
