package org.siemac.metamac.statistical.resources.core.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.sdmx.resources.sdmxml.schemas.v2_1.common.ConceptReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.CodededTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.ReportingYearStartDayType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleDataStructureRepresentationType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeQualifierType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;

public class DsdProcessor {

    private static SrmRestInternalService srmRestInternalService;

    public static SrmRestInternalService getSrmRestInternalService() {
        if (srmRestInternalService == null) {
            srmRestInternalService = ApplicationContextProvider.getApplicationContext().getBean(SrmRestInternalService.class);
        }
        return srmRestInternalService;
    }

    public static List<DsdDimension> getDimensions(DataStructure dsd) {
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

    public static List<DsdAttribute> getAttributes(DataStructure dsd) {
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

        protected DsdComponentType      type;
        protected String                codelistRepresentationUrn;
        protected String                conceptSchemeRepresentationUrn;
        protected CodededTextFormatType textFormatType;

        protected void setRepresentationFromLocalRepresentation(SimpleDataStructureRepresentationType localRepresentation) {
            if (localRepresentation.getEnumeration() != null) {
                codelistRepresentationUrn = localRepresentation.getEnumeration().getURN();
            } else {
                textFormatType = localRepresentation.getEnumerationFormat();
            }
        }

        protected void setRepresentationFromConceptIdentity(ConceptReferenceType conceptIdentityRef) {
            Concept concept = srmRestInternalService.retrieveConceptByUrn(conceptIdentityRef.getURN());
            if (concept.getCoreRepresentation() != null) {
                if (concept.getCoreRepresentation().getEnumeration() != null) {
                    codelistRepresentationUrn = concept.getCoreRepresentation().getEnumeration().getURN();
                } else {
                    textFormatType = concept.getCoreRepresentation().getEnumerationFormat();
                }
            } else {
                throw new IllegalArgumentException("Found a dimension with concept identity with core representation null");
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
        
    }

    public static class DsdDimension extends DsdComponent {

        private String             dimensionId;
        private TimeTextFormatType timeTextFormatType;

        public DsdDimension(Dimension dim) {
            dimensionId = dim.getId();
            if (dim.isIsSpatial()) {
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

        }

        public DsdDimension(MeasureDimensionType dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.MEASURE;
            if (dim.getLocalRepresentation() != null) {
                if (dim.getLocalRepresentation().getEnumeration() != null) {
                    codelistRepresentationUrn = dim.getLocalRepresentation().getEnumeration().getURN();
                } else {
                    throw new IllegalArgumentException("Found a dimension with local representation but no codelist");
                }
            } else if (dim.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(dim.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }
        }

        public DsdDimension(TimeDimensionType dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.TEMPORAL;
            if (dim.getLocalRepresentation() != null) {
                timeTextFormatType = dim.getLocalRepresentation().getTextFormat();
            } else if (dim.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(dim.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }
        }

        @Override
        public String getComponentId() {
            return dimensionId;
        }
    }

    public static class DsdAttribute extends DsdComponent {

        private String                              attributeId;
        private ReportingYearStartDayTextFormatType timeTextFormatType;

        public DsdAttribute(Attribute attr) {
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
        }

        public DsdAttribute(ReportingYearStartDayType attr) {
            attributeId = attr.getId();
            type = DsdComponentType.TEMPORAL;

            if (attr.getLocalRepresentation() != null) {
                timeTextFormatType = attr.getLocalRepresentation().getTextFormat();
            } else {
                setRepresentationFromConceptIdentity(attr.getConceptIdentity());
            }
        }

        @Override
        public String getComponentId() {
            return attributeId;
        }
    }

    public static enum DsdComponentType {
        OTHER, SPATIAL, TEMPORAL, MEASURE
    }

}
