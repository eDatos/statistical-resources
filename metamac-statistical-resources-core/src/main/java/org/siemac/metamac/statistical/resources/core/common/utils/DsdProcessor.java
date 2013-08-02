package org.siemac.metamac.statistical.resources.core.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.CodelistRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptSchemeRefType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeRelationshipType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.BasicComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.PrimaryMeasureType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.UsageStatusType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeQualifierType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;

public class DsdProcessor {

    private static SrmRestInternalService srmRestInternalService;

    public static SrmRestInternalService getSrmRestInternalService() {
        if (srmRestInternalService == null) {
            srmRestInternalService = ApplicationContextProvider.getApplicationContext().getBean(SrmRestInternalService.class);
        }
        return srmRestInternalService;
    }

    public static List<DsdDimension> getDimensions(DataStructure dsd) throws MetamacException {
        List<DsdDimension> dimensions = new ArrayList<DsdProcessor.DsdDimension>();
        DataStructureComponentsType components = dsd.getDataStructureComponents();
        if (components != null && components.getDimensionList() != null) {
            DimensionListType dimensionList = components.getDimensionList();
            for (Object dimObj : dimensionList.getDimensionsAndMeasureDimensionsAndTimeDimensions()) {
                if (dimObj instanceof Dimension) {
                    dimensions.add(new DsdDimension((Dimension) dimObj));
                } else if (dimObj instanceof MeasureDimensionType) {
                    dimensions.add(new DsdDimension((MeasureDimensionType) dimObj));
                } else if (dimObj instanceof TimeDimensionType) {
                    dimensions.add(new DsdDimension((TimeDimensionType) dimObj));
                } else {
                    throw new IllegalArgumentException("Found a dimension of unknown type " + dimObj);
                }
            }
        }
        return dimensions;
    }

    public static List<DsdAttribute> getAttributes(DataStructure dsd) throws MetamacException {
        List<DsdAttribute> attributes = new ArrayList<DsdAttribute>();
        DataStructureComponentsType components = dsd.getDataStructureComponents();

        if (components != null && components.getAttributeList() != null) {
            AttributeListType attributeList = components.getAttributeList();
            for (Object attrObj : attributeList.getAttributesAndReportingYearStartDaies()) {
                if (attrObj instanceof Attribute) {
                    attributes.add(new DsdAttribute((Attribute) attrObj));
                } else if (attrObj instanceof ReportingYearStartDayType) {
                    attributes.add(new DsdAttribute((ReportingYearStartDayType) attrObj));
                } else {
                    throw new IllegalArgumentException("Found an attribute of unknown type " + attrObj);
                }
            }
        }
        return attributes;
    }

    public abstract static class DsdComponent {

        protected DsdComponentType              type                           = null;
        protected String                        codelistRepresentationUrn      = null;
        protected String                        conceptSchemeRepresentationUrn = null;
        protected String                        conceptIdentityUrn             = null;
        protected SimpleComponentTextFormatType textFormat                     = null;
        protected BasicComponentTextFormatType  textFormatConceptId            = null;

        protected void setConceptIdentityUrn(ConceptReferenceType conceptIdentityRef) {
            if (conceptIdentityRef.getRef() != null) {
                ConceptRefType ref = conceptIdentityRef.getRef();
                conceptIdentityUrn = GeneratorUrnUtils.generateSdmxConceptUrn((String[]) Arrays.asList(ref.getAgencyID()).toArray(), ref.getMaintainableParentID(), ref.getMaintainableParentVersion(),
                        ref.getId());
            } else {
                throw new IllegalArgumentException("Concept identity is required.");
            }
        }

        protected void setRepresentationFromLocalRepresentation(SimpleDataStructureRepresentationType localRepresentation) {
            if (localRepresentation.getEnumeration() != null) {
                extractCodelistUrnFromRef(localRepresentation.getEnumeration().getRef());
            } else {
                textFormat = localRepresentation.getTextFormat();
            }
        }
        protected void setRepresentationFromConceptIdentity(ConceptReferenceType conceptIdentityRef) throws MetamacException {
            Concept concept = null;
            if (conceptIdentityRef.getRef() != null) {
                ConceptRefType ref = conceptIdentityRef.getRef();
                concept = getSrmRestInternalService()
                        .retrieveConceptByUrn(
                                GeneratorUrnUtils.generateSdmxConceptUrn((String[]) Arrays.asList(ref.getAgencyID()).toArray(), ref.getMaintainableParentID(), ref.getMaintainableParentVersion(),
                                        ref.getId()));
            } else {
                // In metamac, Ref is always present
                throw new RuntimeException("The reference is not present. In Metamac is always present.");
            }

            if (concept.getCoreRepresentation() != null) {
                if (concept.getCoreRepresentation().getEnumeration() != null) {
                    extractCodelistUrnFromRef(concept.getCoreRepresentation().getEnumeration().getRef());

                } else {
                    textFormatConceptId = concept.getCoreRepresentation().getTextFormat();
                }
            } else {
                throw new IllegalArgumentException("Found a dimension with concept identity with core representation null");
            }
        }

        protected void extractCodelistUrnFromRef(CodelistRefType ref) {
            if (ref != null) {
                codelistRepresentationUrn = GeneratorUrnUtils.generateSdmxCodelistUrn((String[]) Arrays.asList(ref.getAgencyID()).toArray(), ref.getId(), ref.getVersion());
            } else {
                // In metamac, Ref is always present
                throw new RuntimeException("The reference is not present. In Metamac is always present.");
            }
        }

        protected void extractConceptSchemeUrnFromRef(ConceptSchemeRefType ref) {
            if (ref != null) {
                conceptSchemeRepresentationUrn = GeneratorUrnUtils.generateSdmxConceptSchemeUrn((String[]) Arrays.asList(ref.getAgencyID()).toArray(), ref.getId(), ref.getVersion());
            } else {
                // In metamac, Ref is always present
                throw new RuntimeException("The reference is not present. In Metamac is always present.");
            }
        }

        public abstract String getComponentId();

        public DsdComponentType getType() {
            return type;
        }

        public String getCodelistRepresentationUrn() {
            return codelistRepresentationUrn;
        }

        public String getConceptSchemeRepresentationUrn() {
            return conceptSchemeRepresentationUrn;
        }

        public SimpleComponentTextFormatType getTextFormatRepresentation() {
            return textFormat;
        }

        public BasicComponentTextFormatType getTextFormatConceptId() {
            return textFormatConceptId;
        }

        public String getEnumeratedRepresentationUrn() {
            if (StringUtils.isNotEmpty(this.codelistRepresentationUrn) && StringUtils.isNotEmpty(this.conceptSchemeRepresentationUrn)) {
                throw new RuntimeException("Two enumerated representation aren't allowed.");
            }
            if (StringUtils.isNotEmpty(this.codelistRepresentationUrn)) {
                return this.codelistRepresentationUrn;
            }
            if (StringUtils.isNotEmpty(this.conceptSchemeRepresentationUrn)) {
                return this.conceptSchemeRepresentationUrn;
            }
            return null;
        }

        public String getConceptIdentityUrn() {
            return conceptIdentityUrn;
        }
    }

    public static class DsdDimension extends DsdComponent {

        private String             dimensionId;
        private TimeTextFormatType timeTextFormatType;

        public DsdDimension(Dimension dim) throws MetamacException {
            dimensionId = dim.getId();
            if (BooleanUtils.isTrue(dim.isIsSpatial())) {
                type = DsdComponentType.SPATIAL;
            } else {
                type = DsdComponentType.OTHER;
            }

            if (dim.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(dim.getLocalRepresentation());
            } else if (dim.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(dim.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentityUrn(dim.getConceptIdentity());

        }

        public DsdDimension(MeasureDimensionType dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.MEASURE;
            if (dim.getLocalRepresentation() != null) {
                if (dim.getLocalRepresentation().getEnumeration() != null) {
                    extractConceptSchemeUrnFromRef(dim.getLocalRepresentation().getEnumeration().getRef());
                } else {
                    throw new IllegalArgumentException("Found a dimension with local representation but no Concept Scheme");
                }
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentityUrn(dim.getConceptIdentity());
        }

        public DsdDimension(TimeDimensionType dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.TEMPORAL;
            if (dim.getLocalRepresentation() != null) {
                timeTextFormatType = dim.getLocalRepresentation().getTextFormat();
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentityUrn(dim.getConceptIdentity());
        }

        public TimeTextFormatType getTimeTextFormatRepresentation() {
            return timeTextFormatType;
        }

        @Override
        public String getComponentId() {
            return dimensionId;
        }
    }

    public static class DsdAttribute extends DsdComponent {

        private String                              attributeId;
        private ReportingYearStartDayTextFormatType reportingYearStartDayTextFormatType;
        private boolean                             isAttributeAtObservationLevel;
        private AttributeRelationshipType           attributeRelationship;
        private boolean                             isMandatory;

        public DsdAttribute(Attribute attr) throws MetamacException {
            attributeId = attr.getId();
            if (AttributeQualifierType.SPATIAL.equals(attr.getType())) {
                type = DsdComponentType.SPATIAL;
            } else if (AttributeQualifierType.MEASURE.equals(attr.getType())) {
                type = DsdComponentType.MEASURE;
            } else {
                type = DsdComponentType.OTHER;
            }

            if (attr.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(attr.getLocalRepresentation());
            } else {
                setRepresentationFromConceptIdentity(attr.getConceptIdentity());
            }

            setConceptIdentityUrn(attr.getConceptIdentity());

            isAttributeAtObservationLevel = (attr.getAttributeRelationship().getPrimaryMeasure() != null);
            attributeRelationship = attr.getAttributeRelationship();
            isMandatory = UsageStatusType.MANDATORY.equals(attr.getAssignmentStatus());
        }

        public DsdAttribute(ReportingYearStartDayType attr) throws MetamacException {
            attributeId = attr.getId();
            type = DsdComponentType.TEMPORAL;

            if (attr.getLocalRepresentation() != null) {
                reportingYearStartDayTextFormatType = attr.getLocalRepresentation().getTextFormat();
            } else {
                setRepresentationFromConceptIdentity(attr.getConceptIdentity());
            }

            setConceptIdentityUrn(attr.getConceptIdentity());

            isAttributeAtObservationLevel = (attr.getAttributeRelationship().getPrimaryMeasure() != null);
            attributeRelationship = attr.getAttributeRelationship();
            isMandatory = UsageStatusType.MANDATORY.equals(attr.getAssignmentStatus());
        }

        @Override
        public String getComponentId() {
            return attributeId;
        }

        public boolean isAttributeAtObservationLevel() {
            return isAttributeAtObservationLevel;
        }

        public AttributeRelationshipType getAttributeRelationship() {
            return attributeRelationship;
        }

        public ReportingYearStartDayTextFormatType getReportingYearStartDayTextFormatRepresentation() {
            return reportingYearStartDayTextFormatType;
        }

        public boolean isMandatory() {
            return isMandatory;
        }
    }

    public static class DsdPrimaryMeasure extends DsdComponent {

        private String primaryMeasueId = "OBS_VALUE"; // Fixed

        public DsdPrimaryMeasure(PrimaryMeasureType primaryMeasure) throws MetamacException {

            if (primaryMeasure.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(primaryMeasure.getLocalRepresentation());
            } else if (primaryMeasure.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(primaryMeasure.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a primary measure with no representation info ");
            }
            setConceptIdentityUrn(primaryMeasure.getConceptIdentity());
        }

        @Override
        public String getComponentId() {
            return primaryMeasueId;
        }
    }

    public static enum DsdComponentType {
        OTHER, SPATIAL, TEMPORAL, MEASURE
    }

}
