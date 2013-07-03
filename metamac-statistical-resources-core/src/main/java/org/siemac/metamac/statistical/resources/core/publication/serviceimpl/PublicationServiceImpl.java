package org.siemac.metamac.statistical.resources.core.publication.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.components.SiemacStatisticalResourceGeneratedCode;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForCreateResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.utils.FillMetadataForVersioningResourceUtils;
import org.siemac.metamac.statistical.resources.core.base.validators.BaseValidator;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.serviceapi.validators.PublicationServiceInvocationValidator;
import org.siemac.metamac.statistical.resources.core.publication.utils.PublicationVersioningCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of PublicationService.
 */
@Service("publicationService")
public class PublicationServiceImpl extends PublicationServiceImplBase {

    @Autowired
    IdentifiableStatisticalResourceRepository identifiableStatisticalResourceRepository;

    @Autowired
    SiemacStatisticalResourceGeneratedCode    siemacStatisticalResourceGeneratedCode;

    @Autowired
    PublicationServiceInvocationValidator     publicationServiceInvocationValidator;

    private static int                        CODE_MAX_LENGTH = 10;

    public PublicationServiceImpl() {
    }

    @Override
    public PublicationVersion createPublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion, ExternalItem statisticalOperation) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkCreatePublicationVersion(ctx, publicationVersion, statisticalOperation);

        // Create publication
        Publication publication = new Publication();
        publication.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());

        // Fill metadata
        fillMetadataForCreatePublicationVersion(publicationVersion, publication, statisticalOperation, ctx);

        // Save version
        publicationVersion.setPublication(publication);

        // Add version to publication
        assignCodeAndSavePublicationVersion(publication, publicationVersion);
        publicationVersion = getPublicationVersionRepository().retrieveByUrn(publicationVersion.getSiemacMetadataStatisticalResource().getUrn());

        return publicationVersion;
    }

    @Override
    public PublicationVersion updatePublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdatePublicationVersion(ctx, publicationVersion);

        // Check status
        BaseValidator.checkStatisticalResourceCanBeEdited(publicationVersion);

        // TODO RI: Comprobar si el código ha cambiado, si puede cambair y si sigue siendo unico (ver ConceptsServiceImpl.java)
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(publicationVersion.getSiemacMetadataStatisticalResource());
        // TODO RI: Si el codigo ha cambiado debemos actualizar la URN

        publicationVersion = getPublicationVersionRepository().save(publicationVersion);
        return publicationVersion;
    }

    @Override
    public PublicationVersion retrievePublicationVersionByUrn(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationVersionRepository().retrieveByUrn(publicationVersionUrn);
        return publicationVersion;
    }

    @Override
    public PublicationVersion retrieveLatestPublicationVersionByPublicationUrn(ServiceContext ctx, String publicationUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrieveLatestPublicationVersionByPublicationUrn(ctx, publicationUrn);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationVersionRepository().retrieveLastVersion(publicationUrn);
        return publicationVersion;
    }

    @Override
    public PublicationVersion retrieveLatestPublishedPublicationVersionByPublicationUrn(ServiceContext ctx, String publicationUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrieveLatestPublishedPublicationVersionByPublicationUrn(ctx, publicationUrn);

        // Retrieve
        PublicationVersion publicationVersion = getPublicationVersionRepository().retrieveLastPublishedVersion(publicationUrn);
        return publicationVersion;
    }

    @Override
    public List<PublicationVersion> retrievePublicationVersions(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrievePublicationVersions(ctx, publicationVersionUrn);

        // Retrieve
        List<PublicationVersion> publicationVersions = getPublicationVersionRepository().retrieveByUrn(publicationVersionUrn).getPublication().getVersions();

        return publicationVersions;
    }

    @Override
    public PagedResult<PublicationVersion> findPublicationVersionsByCondition(ServiceContext ctx, List<ConditionalCriteria> conditions, PagingParameter pagingParameter) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkFindPublicationVersionsByCondition(ctx, conditions, pagingParameter);

        // Find
        conditions = CriteriaUtils.initConditions(conditions, PublicationVersion.class);
        pagingParameter = CriteriaUtils.initPagingParameter(pagingParameter);

        PagedResult<PublicationVersion> publicationVersionPagedResult = getPublicationVersionRepository().findByCondition(conditions, pagingParameter);
        return publicationVersionPagedResult;
    }

    @Override
    public void deletePublicationVersion(ServiceContext ctx, String publicationVersionUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkDeletePublicationVersion(ctx, publicationVersionUrn);

        // Retrieve version to delete
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, publicationVersionUrn);

        // Check can be deleted
        BaseValidator.checkStatisticalResourceCanBeDeleted(publicationVersion);

        // TODO: Determinar si hay algunas comprobaciones que impiden el borrado

        // TODO RI: Comprobar si hay que eliminar relaciones a otros recursos

        if (VersionUtil.isInitialVersion(publicationVersion.getSiemacMetadataStatisticalResource().getVersionLogic())) {
            Publication publication = publicationVersion.getPublication();
            getPublicationRepository().delete(publication);
        } else {
            // Delete version
            Publication publication = publicationVersion.getPublication();
            publication.getVersions().remove(publicationVersion);
            getPublicationVersionRepository().delete(publicationVersion);
        }
    }

    @Override
    public PublicationVersion versioningPublicationVersion(ServiceContext ctx, String publicationVersionUrnToCopy, VersionTypeEnum versionType) throws MetamacException {
        publicationServiceInvocationValidator.checkVersioningPublicationVersion(ctx, publicationVersionUrnToCopy, versionType);

        // TODO RI: check only published publications can be versioned

        PublicationVersion publicationVersionToCopy = retrievePublicationVersionByUrn(ctx, publicationVersionUrnToCopy);
        PublicationVersion publicationNewVersion = PublicationVersioningCopyUtils.copyPublicationVersion(publicationVersionToCopy);

        FillMetadataForVersioningResourceUtils.fillMetadataForVersioningSiemacResource(ctx, publicationVersionToCopy.getSiemacMetadataStatisticalResource(),
                publicationNewVersion.getSiemacMetadataStatisticalResource(), versionType);

        // PUBLICATION URN
        String[] creator = new String[]{publicationNewVersion.getSiemacMetadataStatisticalResource().getCreator().getCode()};
        publicationNewVersion.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(creator, publicationNewVersion.getSiemacMetadataStatisticalResource().getCode(), publicationNewVersion
                        .getSiemacMetadataStatisticalResource().getVersionLogic()));

        // TODO RI: DATE_NEXT_UPDATE

        publicationNewVersion = getPublicationVersionRepository().save(publicationNewVersion);

        return publicationNewVersion;
    }

    // ------------------------------------------------------------------------
    // CHAPTERS
    // ------------------------------------------------------------------------

    @Override
    public Chapter createChapter(ServiceContext ctx, String publicationVersionUrn, Chapter chapter) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkCreateChapter(ctx, publicationVersionUrn, chapter);
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, publicationVersionUrn);
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Fill metadata for create chapter
        fillMetadataForCreateChapter(ctx, chapter);

        // Create element level
        ElementLevel elementLevel = createChapterElementLevel(ctx, publicationVersion, chapter);

        return elementLevel.getChapter();
    }

    private Chapter fillMetadataForCreateChapter(ServiceContext ctx, Chapter chapter) {
        String code = RandomStringUtils.randomAlphanumeric(CODE_MAX_LENGTH);
        chapter.getNameableStatisticalResource().setCode(code);
        chapter.getNameableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionChapterUrn(code));

        return chapter;
    }

    @Override
    public Chapter updateChapter(ServiceContext ctx, Chapter chapter) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdateChapter(ctx, chapter);
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, chapter.getElementLevel().getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn());
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Save
        return getChapterRepository().save(chapter);
    }

    @Override
    public Chapter updateChapterLocation(ServiceContext ctx, String chapterUrn, String parentChapterUrn, Long orderInLevel) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdateChapterLocation(ctx, chapterUrn, parentChapterUrn, orderInLevel);

        // Update location
        ElementLevel elementLevel = retrieveChapter(ctx, chapterUrn).getElementLevel();
        elementLevel = updateElementLevelLocation(ctx, elementLevel, parentChapterUrn, orderInLevel);

        return elementLevel.getChapter();
    }

    @Override
    public Chapter retrieveChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrieveChapter(ctx, chapterUrn);

        // Retrieve
        Chapter chapter = getChapterRepository().retrieveChapterByUrn(chapterUrn);
        return chapter;

    }

    @Override
    public void deleteChapter(ServiceContext ctx, String chapterUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkDeleteChapter(ctx, chapterUrn);

        // Retrieve
        ElementLevel elementLevel = retrieveChapter(ctx, chapterUrn).getElementLevel();

        // Delete
        deleteElementLevel(ctx, elementLevel);
    }

    // ------------------------------------------------------------------------
    // CUBES
    // ------------------------------------------------------------------------

    @Override
    public Cube createCube(ServiceContext ctx, String publicationVersionUrn, Cube cube) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkCreateCube(ctx, publicationVersionUrn, cube);
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, publicationVersionUrn);
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Fill metadata for create chapter
        fillMetadataForCreateCube(ctx, cube);

        // Create element level
        ElementLevel elementLevel = createCubeElementLevel(ctx, publicationVersion, cube);

        return elementLevel.getCube();
    }

    private Cube fillMetadataForCreateCube(ServiceContext ctx, Cube cube) {
        String code = RandomStringUtils.randomAlphanumeric(CODE_MAX_LENGTH);
        cube.getNameableStatisticalResource().setCode(code);
        cube.getNameableStatisticalResource().setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionCubeUrn(code));

        return cube;
    }

    @Override
    public Cube updateCube(ServiceContext ctx, Cube cube) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdateCube(ctx, cube);
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, cube.getElementLevel().getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn());
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Save
        return getCubeRepository().save(cube);
    }

    @Override
    public Cube updateCubeLocation(ServiceContext ctx, String cubeUrn, String parentChapterUrn, Long orderInLevel) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkUpdateCubeLocation(ctx, cubeUrn, parentChapterUrn, orderInLevel);

        // Update location
        ElementLevel elementLevel = retrieveCube(ctx, cubeUrn).getElementLevel();
        elementLevel = updateElementLevelLocation(ctx, elementLevel, parentChapterUrn, orderInLevel);

        return elementLevel.getCube();
    }

    @Override
    public Cube retrieveCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkRetrieveCube(ctx, cubeUrn);

        // Retrieve
        Cube cube = getCubeRepository().retrieveCubeByUrn(cubeUrn);
        return cube;
    }

    @Override
    public void deleteCube(ServiceContext ctx, String cubeUrn) throws MetamacException {
        // Validations
        publicationServiceInvocationValidator.checkDeleteCube(ctx, cubeUrn);

        // Retrieve
        ElementLevel elementLevel = retrieveCube(ctx, cubeUrn).getElementLevel();

        // Delete
        deleteElementLevel(ctx, elementLevel);
    }

    // ------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------

    private static void fillMetadataForCreatePublicationVersion(PublicationVersion publicationVersion, Publication publication, ExternalItem statisticalOperation, ServiceContext ctx) {
        publicationVersion.setPublication(publication);
        FillMetadataForCreateResourceUtils.fillMetadataForCretateSiemacResource(publicationVersion.getSiemacMetadataStatisticalResource(), statisticalOperation,
                StatisticalResourceTypeEnum.COLLECTION, ctx);
    }

    private synchronized Publication assignCodeAndSavePublicationVersion(Publication publication, PublicationVersion publicationVersion) throws MetamacException {
        String code = siemacStatisticalResourceGeneratedCode.fillGeneratedCodeForCreateSiemacMetadataResource(publicationVersion.getSiemacMetadataStatisticalResource());
        String[] maintainer = new String[]{publicationVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCode()};

        // Fill code and urn for root and version
        publication.getIdentifiableStatisticalResource().setCode(code);
        publication.getIdentifiableStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionUrn(maintainer, publication.getIdentifiableStatisticalResource().getCode()));

        publicationVersion.getSiemacMetadataStatisticalResource().setCode(code);
        publicationVersion.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceCollectionVersionUrn(maintainer, publicationVersion.getSiemacMetadataStatisticalResource().getCode(), publicationVersion
                        .getSiemacMetadataStatisticalResource().getVersionLogic()));

        // Checks
        identifiableStatisticalResourceRepository.checkDuplicatedUrn(publicationVersion.getSiemacMetadataStatisticalResource());

        // Add version to publication
        publication.addVersion(publicationVersion);
        return getPublicationRepository().save(publicationVersion.getPublication());
    }

    private ElementLevel createChapterElementLevel(ServiceContext ctx, PublicationVersion publicationVersion, Chapter chapter) throws MetamacException {
        ElementLevel elementLevel = chapter.getElementLevel();
        elementLevel = createElementLevel(ctx, publicationVersion, elementLevel);
        return elementLevel;
    }

    private ElementLevel createCubeElementLevel(ServiceContext ctx, PublicationVersion publicationVersion, Cube cube) throws MetamacException {
        ElementLevel elementLevel = cube.getElementLevel();
        elementLevel = createElementLevel(ctx, publicationVersion, elementLevel);
        return elementLevel;
    }

    /**
     * Create element level: chapter or cube
     */
    private ElementLevel createElementLevel(ServiceContext ctx, PublicationVersion publicationVersion, ElementLevel elementLevel) throws MetamacException {
        if (elementLevel.getParent() == null) {
            elementLevel = addToFirstLevelInPublicationVersion(ctx, publicationVersion, elementLevel);
        } else {
            elementLevel = addToParentLevel(ctx, publicationVersion, elementLevel);
        }
        return elementLevel;
    }

    private ElementLevel addToFirstLevelInPublicationVersion(ServiceContext ctx, PublicationVersion publicationVersion, ElementLevel elementLevel) throws MetamacException {
        // Create element level
        elementLevel.setParent(null);
        elementLevel.setPublicationVersion(publicationVersion);
        elementLevel.setPublicationVersionFirstLevel(publicationVersion);
        elementLevel = getElementLevelRepository().save(elementLevel);

        // Update publicationVersion adding element
        publicationVersion.addChildrenFirstLevel(elementLevel);
        publicationVersion.addChildrenAllLevel(elementLevel);
        publicationVersion = getPublicationVersionRepository().save(publicationVersion);

        // Check order and update order of other elements in this level
        updatePublicationVersionElementsOrdersInLevelAddingElement(ctx, publicationVersion.getChildrenFirstLevel(), elementLevel);
        return elementLevel;
    }

    private ElementLevel addToParentLevel(ServiceContext ctx, PublicationVersion publicationVersion, ElementLevel elementLevel) throws MetamacException {
        ElementLevel elementLevelParent = elementLevel.getParent();

        // Check chapter parent belongs to publication version
        String publicationVersionUrnOfParentChapter = elementLevelParent.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn();
        if (!publicationVersion.getSiemacMetadataStatisticalResource().getUrn().equals(publicationVersionUrnOfParentChapter)) {
            throw new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION, elementLevelParent.getChapter().getNameableStatisticalResource().getUrn(), publicationVersion
                    .getSiemacMetadataStatisticalResource().getUrn());
        }

        // Create element level in chapter
        elementLevel.setPublicationVersionFirstLevel(null);
        elementLevel.setPublicationVersion(publicationVersion);
        elementLevel.setParent(elementLevelParent);
        elementLevel = getElementLevelRepository().save(elementLevel);

        // Update parent level adding element
        elementLevelParent.addChildren(elementLevel);
        elementLevelParent = updateElementLevel(elementLevelParent);

        // Update order of other elements in this level
        updatePublicationVersionElementsOrdersInLevelAddingElement(ctx, elementLevelParent.getChildren(), elementLevel);

        // Update indicators system adding element to all children
        publicationVersion.addChildrenAllLevel(elementLevel);
        publicationVersion = getPublicationVersionRepository().save(publicationVersion);

        return elementLevel;
    }

    private void updatePublicationVersionElementsOrdersInLevelAddingElement(ServiceContext ctx, List<ElementLevel> elementsAtLevel, ElementLevel elementToAdd) throws MetamacException {

        // Create a set with all possibles orders. At the end of this method, this set must be empty
        Set<Long> orders = new HashSet<Long>();
        for (int i = 1; i <= elementsAtLevel.size(); i++) {
            orders.add(Long.valueOf(i));
        }

        // Update orders
        for (ElementLevel elementInLevel : elementsAtLevel) {
            // it is possible that element is already added to parent and order is already set
            if (elementInLevel.getElementUuid().equals(elementToAdd.getElementUuid())) {
                // nothing
            } else {
                // Update order
                if (elementInLevel.getOrderInLevel() >= elementToAdd.getOrderInLevel()) {
                    elementInLevel.setOrderInLevel(elementInLevel.getOrderInLevel() + 1);
                    updateElementLevel(elementInLevel);
                }
            }

            boolean removed = orders.remove(elementInLevel.getOrderInLevel());
            if (!removed) {
                break; // order incorrect
            }
        }

        // Checks orders
        if (!orders.isEmpty()) {
            if (elementToAdd.isChapter()) {
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__ORDER_IN_LEVEL);
            } else {
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CUBE__ELEMENT_LEVEL__ORDER_IN_LEVEL);
            }
        }
    }

    private ElementLevel updateElementLevel(ElementLevel elementLevel) throws MetamacException {
        return getElementLevelRepository().save(elementLevel);
    }

    private ElementLevel updateElementLevelLocation(ServiceContext ctx, ElementLevel elementLevel, String targetParentChapterUrn, Long orderInLevel) throws MetamacException {
        // Check indicators system proc status
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, elementLevel.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn());
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Change order
        Long orderInLevelBefore = elementLevel.getOrderInLevel();
        elementLevel.setOrderInLevel(orderInLevel);

        // Check if target parent is in publicationVersion
        if (targetParentChapterUrn != null) {
            Chapter targetParentChapter = retrieveChapter(ctx, targetParentChapterUrn);
            checkIfParentChapterIsInTheSamePublicationVersion(elementLevel, targetParentChapter);

            // Check target parent is not children of this dimension (only when element is a dimension)
            if (elementLevel.getChapter() != null) {
                checkChapterIsNotChildren(ctx, elementLevel.getChapter(), targetParentChapter);
            }
        }

        // Set actual parent
        String actualParentChapterUrn = elementLevel.getParentUrn();

        // Check if it's necessary update parent and order or only order
        if ((actualParentChapterUrn == null && targetParentChapterUrn != null) || (actualParentChapterUrn != null && targetParentChapterUrn == null)
                || (actualParentChapterUrn != null && targetParentChapterUrn != null && !targetParentChapterUrn.equals(actualParentChapterUrn))) {
            elementLevel = updateParentAndOrder(ctx, elementLevel, targetParentChapterUrn, publicationVersion, orderInLevelBefore);
        } else {
            elementLevel = updateOnlyOrder(ctx, elementLevel, orderInLevelBefore);
        }

        return elementLevel;
    }

    private ElementLevel updateOnlyOrder(ServiceContext ctx, ElementLevel elementLevel, Long orderInLevelBefore) throws MetamacException {
        // Same parent, only changes order. Check order is correct and update orders
        List<ElementLevel> elementsInLevel = elementLevel.getParent() != null ? elementLevel.getParent().getChildren() : elementLevel.getPublicationVersionFirstLevel().getChildrenFirstLevel();
        updatePublicationVersionElementsOrdersInLevelChangingOrder(ctx, elementsInLevel, elementLevel, orderInLevelBefore, elementLevel.getOrderInLevel());
        elementLevel = updateElementLevel(elementLevel);
        return elementLevel;
    }

    private ElementLevel updateParentAndOrder(ServiceContext ctx, ElementLevel elementLevel, String targetParentChapterUrn, PublicationVersion publicationVersion, Long orderInLevelBefore)
            throws MetamacException {
        ElementLevel parentActual = elementLevel.getParent() != null ? elementLevel.getParent() : null;
        ElementLevel parentTarget = targetParentChapterUrn != null ? retrieveChapter(ctx, targetParentChapterUrn).getElementLevel() : null;

        // Update actual parent dimension or indicators system version, removing dimension
        if (parentActual != null) {
            // Update order of other dimensions
            elementLevel.setParent(null);
            updatePublicationVersionElementsOrdersInLevelRemovingElement(ctx, parentActual.getChildren(), elementLevel, orderInLevelBefore);
        } else {
            // Update order of other dimensions
            elementLevel.setPublicationVersionFirstLevel(null);
            updatePublicationVersionElementsOrdersInLevelRemovingElement(ctx, publicationVersion.getChildrenFirstLevel(), elementLevel, orderInLevelBefore);
        }

        // Update target parent, adding dimension
        List<ElementLevel> elementsInLevel = null;
        if (parentTarget == null) {
            publicationVersion.addChildrenFirstLevel(elementLevel);
            publicationVersion = getPublicationVersionRepository().save(publicationVersion);
            elementsInLevel = publicationVersion.getChildrenFirstLevel();
        } else {
            parentTarget.addChildren(elementLevel);
            updateElementLevel(parentTarget);
            elementsInLevel = parentTarget.getChildren();
        }
        // Check order is correct and update orders
        updatePublicationVersionElementsOrdersInLevelAddingElement(ctx, elementsInLevel, elementLevel);

        // Update dimension, changing parent
        if (parentTarget == null) {
            elementLevel.setPublicationVersionFirstLevel(publicationVersion);
            elementLevel.setParent(null);
        } else {
            elementLevel.setPublicationVersionFirstLevel(null);
            elementLevel.setParent(parentTarget);
        }
        elementLevel = updateElementLevel(elementLevel);
        return elementLevel;
    }

    /**
     * We can not move a chapter to its child
     */
    private void checkChapterIsNotChildren(ServiceContext ctx, Chapter chapter, Chapter parentChapter) throws MetamacException {
        // Set parent
        Chapter chapterParent = null;
        if (parentChapter.getElementLevel().getParent() != null) {
            chapterParent = parentChapter.getElementLevel().getParent().getChapter();
        }

        while (chapterParent != null) {
            if (chapterParent.getNameableStatisticalResource().getUrn().equals(chapter.getNameableStatisticalResource().getUrn())) {
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__PARENT);
            }
            chapterParent = chapterParent.getElementLevel().getParent().getChapter();
        }
    }

    private void checkIfParentChapterIsInTheSamePublicationVersion(ElementLevel elementLevel, Chapter parentChapter) throws MetamacException {
        // Check if chapter is in the publicationVersion
        if (!elementLevel.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn()
                .equals(parentChapter.getElementLevel().getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn())) {
            throw new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND_IN_PUBLICATION_VERSION, parentChapter.getNameableStatisticalResource().getUrn(), elementLevel.getPublicationVersion()
                    .getSiemacMetadataStatisticalResource().getUrn());
        }
    }

    private void updatePublicationVersionElementsOrdersInLevelRemovingElement(ServiceContext ctx, List<ElementLevel> elementsAtLevel, ElementLevel elementToRemove, Long orderBeforeUpdate)
            throws MetamacException {
        for (ElementLevel elementInLevel : elementsAtLevel) {
            if (elementInLevel.getElementUuid().equals(elementToRemove.getElementUuid())) {
                // nothing
            } else if (elementInLevel.getOrderInLevel() > orderBeforeUpdate) {
                elementInLevel.setOrderInLevel(elementInLevel.getOrderInLevel() - 1);
                updateElementLevel(elementInLevel);
            }
        }
    }

    private void updatePublicationVersionElementsOrdersInLevelChangingOrder(ServiceContext ctx, List<ElementLevel> elementsAtLevel, ElementLevel elementToChangeOrder, Long orderBeforeUpdate,
            Long orderAfterUpdate) throws MetamacException {

        // Checks orders
        if (orderAfterUpdate > elementsAtLevel.size()) {
            if (elementToChangeOrder.isChapter()) {
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CHAPTER__ELEMENT_LEVEL__ORDER_IN_LEVEL);
            } else {
                throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, ServiceExceptionParameters.CUBE__ELEMENT_LEVEL__ORDER_IN_LEVEL);
            }
        }

        // Update orders
        for (ElementLevel elementAtLevel : elementsAtLevel) {
            if (elementAtLevel.getElementUuid().equals(elementToChangeOrder.getElementUuid())) {
                continue;
            }
            if (orderAfterUpdate < orderBeforeUpdate) {
                if (elementAtLevel.getOrderInLevel() >= orderAfterUpdate && elementAtLevel.getOrderInLevel() < orderBeforeUpdate) {
                    elementAtLevel.setOrderInLevel(elementAtLevel.getOrderInLevel() + 1);
                    updateElementLevel(elementAtLevel);
                }
            } else if (orderAfterUpdate > orderBeforeUpdate) {
                if (elementAtLevel.getOrderInLevel() > orderBeforeUpdate && elementAtLevel.getOrderInLevel() <= orderAfterUpdate) {
                    elementAtLevel.setOrderInLevel(elementAtLevel.getOrderInLevel() - 1);
                    updateElementLevel(elementAtLevel);
                }
            }
        }
    }

    private void deleteElementLevel(ServiceContext ctx, ElementLevel elementLevel) throws MetamacException {
        // Check indicators system proc status
        PublicationVersion publicationVersion = retrievePublicationVersionByUrn(ctx, elementLevel.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn());
        BaseValidator.checkStatisticalResourceStructureCanBeEdited(publicationVersion);

        // Update orders of other elements in level
        List<ElementLevel> elementsAtLevel = null;
        if (elementLevel.getParent() == null) {
            elementsAtLevel = publicationVersion.getChildrenFirstLevel();
        } else {
            elementsAtLevel = elementLevel.getParent().getChildren();
        }
        elementsAtLevel.remove(elementLevel);
        updatePublicationVersionElementsOrdersInLevelRemovingElement(ctx, elementsAtLevel, elementLevel, elementLevel.getOrderInLevel());

        // Delete
        getElementLevelRepository().delete(elementLevel);
    }
}
