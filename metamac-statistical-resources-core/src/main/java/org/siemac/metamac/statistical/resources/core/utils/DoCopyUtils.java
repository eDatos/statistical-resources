package org.siemac.metamac.statistical.resources.core.utils;
//package org.siemac.metamac.statistical.resources.core.serviceimpl.utils;
//
//import static com.arte.statistic.sdmx.srm.core.common.service.utils.DoCopyUtils.copy;
//import static com.arte.statistic.sdmx.srm.core.common.service.utils.DoCopyUtils.copyItem;
//import static com.arte.statistic.sdmx.srm.core.common.service.utils.DoCopyUtils.copyItemSchemeVersion;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.arte.statistic.sdmx.srm.core.base.domain.Item;
//import com.arte.statistic.sdmx.srm.core.concept.domain.Concept;
//import com.arte.statistic.sdmx.srm.core.concept.domain.ConceptSchemeVersion;
//
//public class DoCopyUtils {
//
//    /**
//     * Create a new {@link ConceptSchemeVersion} copying values from a source. Do not copy children
//     */
//    public static ConceptSchemeVersion copyConceptSchemeVersion(ConceptSchemeVersion source) {
//        ConceptSchemeVersion target = new ConceptSchemeVersion();
//        copyConceptSchemeVersion(source, target);
//        return target;
//    }
//
//    /**
//     * Copy values from a {@link ConceptSchemeVersion}
//     */
//    public static void copyConceptSchemeVersion(ConceptSchemeVersion source, ConceptSchemeVersion target) {
//        copyItemSchemeVersion(source, target);
//    }
//
//    /**
//     * Copy children hierarchy from a {@link ConceptSchemeVersion}
//     */
//    public static List<Concept> copyConcepts(ConceptSchemeVersion conceptSchemeVersion) {
//        List<Item> sources = conceptSchemeVersion.getItemsFirstLevel();
//        List<Concept> targets = new ArrayList<Concept>();
//        for (Item source : sources) {
//            Concept target = copyConceptWithChildren((Concept) source);
//            targets.add(target);
//        }
//        return targets;
//    }
//
//    /**
//     * Create a new {@link Concept} copying values from a source with children
//     */
//    private static Concept copyConceptWithChildren(Concept source) {
//
//        Concept target = copyConcept(source);
//
//        // Children
//        for (Item childrenSource : source.getChildren()) {
//            Concept childrenTarget = copyConceptWithChildren((Concept) childrenSource);
//            childrenTarget.setParent(target);
//            target.addChildren(childrenTarget);
//        }
//
//        return target;
//    }
//
//    /**
//     * Create a new {@link Concept} copying values from a source. Do not copy children
//     */
//    public static Concept copyConcept(Concept source) {
//        Concept target = new Concept();
//        target.setCoreRepresentation(copy(source.getCoreRepresentation()));
//
//        copyItem(source, target);
//        return target;
//    }
//
//    /**
//     * Copy values from a {@link Concept}
//     */
//    public static void copyConcept(Concept source, Concept target) {
//        copyItem(source, target);
//    }
//
//}