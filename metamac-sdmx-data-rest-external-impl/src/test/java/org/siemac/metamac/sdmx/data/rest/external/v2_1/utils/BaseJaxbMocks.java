package org.siemac.metamac.sdmx.data.rest.external.v2_1.utils;

import static org.siemac.metamac.common.test.utils.MetamacMocks.mockString;

import org.apache.commons.lang.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.AnnotationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.Annotations;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CategoryReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalCodeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalCodeReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalGroupKeyDescriptorRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalGroupKeyDescriptorReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalOrganisationUnitRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalOrganisationUnitReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalPrimaryMeasureRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalPrimaryMeasureReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectTypeCodelistType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.PackageTypeCodelistType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;

public class BaseJaxbMocks {

    public static org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory      structureObjectFactory     = new org.sdmx.resources.sdmxml.schemas.v2_1.structure.ObjectFactory();

    public static org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory         commonObjectFactory        = new org.sdmx.resources.sdmxml.schemas.v2_1.common.ObjectFactory();

    public static org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory        messageObjectFactory       = new org.sdmx.resources.sdmxml.schemas.v2_1.message.ObjectFactory();

    public static org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory messageFooterObjectFactory = new org.sdmx.resources.sdmxml.schemas.v2_1.message.footer.ObjectFactory();

    public static Annotations mockAnnotations() {
        Annotations annotations = commonObjectFactory.createAnnotations();

        annotations.getAnnotations().add(mockAnnotationType());
        annotations.getAnnotations().add(mockAnnotationType());

        return annotations;
    }

    public static AnnotationType mockAnnotationType() {
        AnnotationType annotationType = commonObjectFactory.createAnnotationType();

        annotationType.setAnnotationTitle(mockString(10));
        annotationType.setAnnotationType(mockString(10));
        annotationType.setAnnotationURL(mockString(10));
        annotationType.setId(mockString(10));

        return annotationType;
    }

    public static TextType mockTextType() {
        TextType textType = commonObjectFactory.createTextType();

        textType.setLang(mockString(2));
        textType.setValue(mockString(10));

        return textType;
    }

    public static ObjectReferenceType mockObjectReferenceTypeByURN(String urn) {
        ObjectReferenceType objectReferenceType = commonObjectFactory.createObjectReferenceType();

        objectReferenceType.setURN(urn);

        return objectReferenceType;
    }

    public static ObjectReferenceType mockObjectReferenceTypeByRef(String agencyID, String maintainableParentID, String maintainableParentVersion, String id, String version, String containerID,
            ObjectTypeCodelistType clazz, PackageTypeCodelistType _package) {
        ObjectReferenceType objectReferenceType = commonObjectFactory.createObjectReferenceType();

        ObjectRefType objectRefType = commonObjectFactory.createObjectRefType();
        objectRefType.setAgencyID(agencyID);
        objectRefType.setLocal(Boolean.FALSE);
        objectRefType.setClazz(clazz);
        objectRefType.setPackage(_package);
        objectRefType.setMaintainableParentID(maintainableParentID);
        objectRefType.setMaintainableParentVersion(maintainableParentVersion);
        objectRefType.setId(id);
        objectRefType.setVersion(version);
        objectRefType.setContainerID(containerID);

        objectReferenceType.setRef(objectRefType);

        return objectReferenceType;
    }

    public static CategoryReferenceType mockCategoryReferenceTypeByURN(String urn) {
        CategoryReferenceType categoryReferenceType = commonObjectFactory.createCategoryReferenceType();

        categoryReferenceType.setURN(urn);

        return categoryReferenceType;
    }

    public static CategoryReferenceType mockCategoryReferenceTypeByRef(String agencyID, String maintainableParentID, String maintainableParentVersion, String id, String version, String containerID) {
        CategoryReferenceType categoryReferenceType = commonObjectFactory.createCategoryReferenceType();

        CategoryRefType categoryRefType = commonObjectFactory.createCategoryRefType();
        categoryRefType.setAgencyID(agencyID);
        categoryRefType.setLocal(Boolean.FALSE);
        categoryRefType.setMaintainableParentID(maintainableParentID);
        categoryRefType.setMaintainableParentVersion(maintainableParentVersion);
        categoryRefType.setId(id);

        categoryReferenceType.setRef(categoryRefType);

        return categoryReferenceType;
    }

    public static LocalConceptReferenceType mockLocalConceptReferenceType(String localConceptID) {
        if (StringUtils.isEmpty(localConceptID)) {
            return null;
        }

        LocalConceptReferenceType localConceptReferenceType = commonObjectFactory.createLocalConceptReferenceType();

        LocalConceptRefType localConceptRefType = commonObjectFactory.createLocalConceptRefType();
        localConceptRefType.setId(localConceptID);

        localConceptReferenceType.setRef(localConceptRefType);

        return localConceptReferenceType;
    }

    public static LocalOrganisationUnitReferenceType mockLocalOrganisationUnitReferenceType(String localConceptID) {
        if (StringUtils.isEmpty(localConceptID)) {
            return null;
        }

        LocalOrganisationUnitReferenceType localOrganisationUnitReferenceType = commonObjectFactory.createLocalOrganisationUnitReferenceType();

        LocalOrganisationUnitRefType localOrganisationUnitRefType = commonObjectFactory.createLocalOrganisationUnitRefType();
        localOrganisationUnitRefType.setId(localConceptID);

        localOrganisationUnitReferenceType.setRef(localOrganisationUnitRefType);

        return localOrganisationUnitReferenceType;
    }

    public static LocalCodeReferenceType mockLocalCodeReferenceType(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        LocalCodeReferenceType localCodeReferenceType = commonObjectFactory.createLocalCodeReferenceType();

        LocalCodeRefType localCodeRefType = commonObjectFactory.createLocalCodeRefType();
        localCodeRefType.setId(value);

        localCodeReferenceType.setRef(localCodeRefType);

        return localCodeReferenceType;
    }

    public static LocalGroupKeyDescriptorReferenceType mockLocalGroupKeyDescriptorReferenceType(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        LocalGroupKeyDescriptorReferenceType localGroupKeyDescriptorReferenceType = commonObjectFactory.createLocalGroupKeyDescriptorReferenceType();

        LocalGroupKeyDescriptorRefType localGroupKeyDescriptorRefType = commonObjectFactory.createLocalGroupKeyDescriptorRefType();
        localGroupKeyDescriptorRefType.setId(value);

        localGroupKeyDescriptorReferenceType.setRef(localGroupKeyDescriptorRefType);

        return localGroupKeyDescriptorReferenceType;
    }

    public static LocalPrimaryMeasureReferenceType mockLocalPrimaryMeasureReferenceType() {

        LocalPrimaryMeasureReferenceType localPrimaryMeasureReferenceType = commonObjectFactory.createLocalPrimaryMeasureReferenceType();

        LocalPrimaryMeasureRefType localPrimaryMeasureRefType = commonObjectFactory.createLocalPrimaryMeasureRefType();
        localPrimaryMeasureRefType.setId("localPrimaryMeasureReferenceType"); // Fixed

        localPrimaryMeasureReferenceType.setRef(localPrimaryMeasureRefType);

        return localPrimaryMeasureReferenceType;
    }

    public static ConceptReferenceType mockConceptReferenceType(String agencyID, String maintainableParentID, String maintainableParentVersion, String id) {
        ConceptReferenceType conceptReferenceType = commonObjectFactory.createConceptReferenceType();

        ConceptRefType conceptRefType = commonObjectFactory.createConceptRefType();
        conceptRefType.setAgencyID(agencyID);
        conceptRefType.setMaintainableParentID(maintainableParentID);
        conceptRefType.setMaintainableParentVersion(maintainableParentVersion);
        conceptRefType.setId(id);

        conceptReferenceType.setRef(conceptRefType);

        return conceptReferenceType;
    }

    public static ConceptSchemeReferenceType mockConceptSchemeReferenceType(String agencyID, String id, String version) {
        ConceptSchemeReferenceType conceptSchemeReferenceType = commonObjectFactory.createConceptSchemeReferenceType();

        ConceptSchemeRefType conceptSchemeRefType = commonObjectFactory.createConceptSchemeRefType();
        conceptSchemeRefType.setAgencyID(agencyID);
        conceptSchemeRefType.setId(id);
        conceptSchemeRefType.setVersion(version);

        conceptSchemeReferenceType.setRef(conceptSchemeRefType);
        return conceptSchemeReferenceType;
    }

    public static CodelistReferenceType mockCodelistReferenceType(String urn) {
        CodelistReferenceType codelistReferenceType = commonObjectFactory.createCodelistReferenceType();
        codelistReferenceType.setURN(urn);

        return codelistReferenceType;
    }

    public static LocalDimensionReferenceType mockLocalDimensionReferenceType(String dimensionID) {
        LocalDimensionReferenceType localDimensionReferenceType = commonObjectFactory.createLocalDimensionReferenceType();

        LocalDimensionRefType localDimensionRefType = commonObjectFactory.createLocalDimensionRefType();
        localDimensionRefType.setId(dimensionID);

        localDimensionReferenceType.setRef(localDimensionRefType);

        return localDimensionReferenceType;
    }
}
